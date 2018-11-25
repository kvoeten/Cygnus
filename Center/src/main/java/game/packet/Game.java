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
package game.packet;

import user.account.Account;
import user.character.AvatarData;
import user.character.CharacterData;
import game.GameServerSocket;
import net.packet.InPacket;
import net.packet.OutPacket;
import user.Transition;
import user.UserStorage;

/**
 * @author Kaz Voeten
 */
public class Game {

    public static void OnMigrationRequest(GameServerSocket pSocket, InPacket iPacket) {
        long nSessionID = iPacket.DecodeLong();
        int dwCharacterID = iPacket.DecodeInt();
        String sIP = iPacket.DecodeString();

        UserStorage.GetStorage().Lock();
        try {
            if (UserStorage.GetStorage().mTransitions.containsKey(dwCharacterID)) {
                Transition pTransition = UserStorage.GetStorage().mTransitions.get(dwCharacterID);
                if (!pTransition.sIP.equals(sIP)) {
                    UserStorage.GetStorage().mTransitions.remove(dwCharacterID); //Impossible for user to change IP's mid-transition.
                    return;
                }

                Account pAccount = pTransition.pAccount;
                AvatarData pAvatar = pAccount.GetAvatar(dwCharacterID);
                if (pAvatar == null) {
                    return; //Character registered for transition wasn't loaded into the account.
                }

                pAccount.pCharacterData = new CharacterData(pAvatar);

                OutPacket oPacket = new OutPacket(LoopBackPacket.MigrationResult);
                oPacket.EncodeLong(nSessionID);
                oPacket.EncodeBool(true);
                pAccount.Encode(oPacket);
                pAccount.pCharacterData.Encode(oPacket);
                pSocket.SendPacket(oPacket);

            } else {
                OutPacket oPacket = new OutPacket(LoopBackPacket.MigrationResult);
                oPacket.EncodeLong(nSessionID);
                oPacket.EncodeBool(false);
                pSocket.SendPacket(oPacket);
            }
        } finally {
            UserStorage.GetStorage().Unlock();
        }

    }
}
