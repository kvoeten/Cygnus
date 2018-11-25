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
package center.packet;

import center.CenterSessionManager;
import center.CenterSocket;
import client.Account;
import client.ClientSessionManager;
import client.ClientSocket;
import client.avatar.AvatarData;
import client.packet.Login;

import java.util.Date;
import java.util.List;

import net.packet.InPacket;
import net.packet.OutPacket;

/**
 * @author Kaz Voeten
 */
public class Center {

    public static OutPacket ProcessLogin(long nSessionID, String sToken, String sIP) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.ProcessLogin);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeString(sToken);
        oPacket.EncodeString(sIP);
        return oPacket;
    }

    public static OutPacket CheckDuplicatedID(long nSessionID, String sCharacterName) {

        OutPacket oPacket = new OutPacket(LoopBackPacket.CheckDuplicateID);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeString(sCharacterName);
        return oPacket;
    }

    public static OutPacket CreateNewCharacter(long nSessionID, int nCharlistPosition, byte[] aData) {

        OutPacket oPacket = new OutPacket(LoopBackPacket.CreateNewCharacter);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeInt(nCharlistPosition);
        oPacket.EncodeBuffer(aData);
        return oPacket;
    }

    public static OutPacket SetSecondPW(long nSessionID, int nAccountID, String sSPW) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.SetSecondPW);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeInt(nAccountID);
        oPacket.EncodeString(sSPW);
        return oPacket;
    }

    public static OutPacket SetState(long nSessionID, int nState, int nAccountID, int dwCharacterID) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.SetState);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeByte(nState);
        oPacket.EncodeInt(nAccountID);
        oPacket.EncodeInt(dwCharacterID);
        return oPacket;
    }

    public static void OnAccountInformation(InPacket iPacket, long nSessionID) {
        ClientSocket pSocket = ClientSessionManager.GetSessionByID(nSessionID);

        boolean bSuccess = iPacket.DecodeBool();
        if (!bSuccess) {
            Login.AccountInfoResult(iPacket.DecodeBool() ? 3 : 6, null);
            return;
        }

        Account pAccount = Account.Decode(iPacket);

        if (pAccount.nState > 0) {
            pSocket.SendPacket(Login.AccountInfoResult(7, null));
            return;
        }

        pSocket.pAccount = pAccount;

        for (int i = iPacket.DecodeByte(); i > 0; --i) {
            int nCharListPosition = iPacket.DecodeInt();
            AvatarData pAvatar = AvatarData.Decode(pSocket.pAccount.nAccountID, iPacket);
            pAvatar.nCharlistPos = nCharListPosition;
            pSocket.pAccount.aAvatarData.add(pAvatar);
        }

        pSocket.SendPacket(Login.AccountInfoResult(0x00, pSocket.pAccount));
        pSocket.SendPacket(Login.SelectWorldResult(pSocket, false));
    }

    /**
     * Sends a ban request to the Center Server, which will add it to the Database in the AUTH server. ALL login server related bans last a
     * default time of 30 days. Third strike is permanent.
     *
     * @param pSocket      - Connected client socket being banned
     * @param nType        - ban type. (0 = IP, 1 = Account, 2 = IP/Account, 3 = IP/MAC/HWID, 4 = Account/IP/MAC/HWID)
     * @param sReason
     * @param uBanDuration
     */
    public static void Ban(ClientSocket pSocket, int nType, String sReason, long uBanDuration) {
        CenterSocket pCenterSocket = CenterSessionManager.GetByWorldID(pSocket.nWorldID);

        //TODO: BANNING

        pSocket.Close();
    }

    public static OutPacket ChangeCharacterLocation(long nSessionID, List<AvatarData> aAvatarData) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.ChangeCharacterLocation);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeInt(aAvatarData.size());
        for (AvatarData pAvatar : aAvatarData) {
            oPacket.EncodeInt(pAvatar.pCharacterStat.dwCharacterID);
        }
        return oPacket;
    }

    public static OutPacket DeleteCharacter(long nSessionID, int dwCharacterID) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.DeleteCharacter);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeInt(dwCharacterID);
        return oPacket;
    }

    public static void OnDeleteCharacterResult(InPacket iPacket) {
        long nSessionID = iPacket.DecodeLong();
        int dwCharacterID = iPacket.DecodeInt();
        boolean bDeleted = iPacket.DecodeBool();

        ClientSocket pSocket = ClientSessionManager.GetSessionByID(nSessionID);
        if (pSocket == null || pSocket.pAccount == null) {
            return;
        }

        pSocket.Lock();
        try {
            if (bDeleted) {
                pSocket.pAccount.aAvatarData.clear();
                byte nSize = iPacket.DecodeByte();
                for (int i = 0; i < nSize; i++) {
                    AvatarData pAvatar = AvatarData.Decode(pSocket.pAccount.nAccountID, iPacket);
                    pAvatar.nCharlistPos = i + 1;
                    pSocket.pAccount.aAvatarData.add(pAvatar);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            pSocket.Unlock();
        }

        pSocket.SendPacket(Login.DeleteCharacterResult(dwCharacterID, bDeleted ? (byte) 0 : (byte) 10)); //16 is unconfirmed, but need to find the byte for failed request
    }
}
