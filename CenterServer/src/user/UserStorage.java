/*
 * Copyright (C) 2018 Kaz Voeten
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package user;

import user.account.Account;
import auth.AuthServerSessionManager;
import auth.AuthServerSocket;
import auth.packet.Auth;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Kaz Voeten
 */
public class UserStorage {

    private static UserStorage pStorage;
    public final HashMap<String, Long> mReservedCharacterNames;
    public final HashMap<Integer, Transition> mTransitions;
    public final HashMap<Long, Account> mAccountStorage;
    private final ReentrantLock m_Lock;

    public UserStorage() {
        this.mReservedCharacterNames = new HashMap<>();
        this.mAccountStorage = new HashMap<>();
        this.mTransitions = new HashMap<>();
        this.m_Lock = new ReentrantLock();
    }

    public Account GetByID(int nAccountID) {
        for (Account pAccount : mAccountStorage.values()) {
            if (pAccount.nAccountID == nAccountID) {
                return pAccount;
            }
        }
        return null;
    }

    public void Lock() {
        this.m_Lock.lock();
    }

    public void Unlock() {
        this.m_Lock.unlock();
    }

    public static UserStorage GetStorage() {
        if (pStorage == null) {
            pStorage = new UserStorage();
        }
        return pStorage;
    }

    public static void RegisterTransition(long nSessionID, int nAccountID, int dwCharacterID) {
        AuthServerSocket pAuthSocket = AuthServerSessionManager.pSession;
        if (pAuthSocket == null) {
            return;
        }

        UserStorage.GetStorage().Lock();
        try {
            if (UserStorage.GetStorage().mAccountStorage.containsKey(nSessionID)) {
                Account pAccount = UserStorage.GetStorage().mAccountStorage.get(nSessionID);
                UserStorage.GetStorage().mAccountStorage.remove(nSessionID);

                if (pAccount.nAccountID != nAccountID) {
                    pAuthSocket.SendPacket(Auth.SetState(nAccountID, (byte) 0));
                    return;
                }

                Transition pTransition = new Transition();
                pTransition.nSessionID = nSessionID;
                pTransition.dwCharacterID = dwCharacterID;
                pTransition.pAccount = pAccount;

                UserStorage.GetStorage().mTransitions.put(dwCharacterID, pTransition);
                pAuthSocket.SendPacket(Auth.SetState(nAccountID, (byte) 2));
            }
        } finally {
            UserStorage.GetStorage().Unlock();
        }
    }

}
