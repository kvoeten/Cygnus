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

import center.CenterSocket;
import client.ClientSessionManager;
import client.ClientSocket;

import net.packet.InPacket;
import net.packet.OutPacket;

/**
 * @author Kaz Voeten
 */
public class Center {

    public static OutPacket SetState(long nSessionID, int nState, int nAccountID, int dwCharacterID) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.SetState);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeByte(nState);
        oPacket.EncodeInt(nAccountID);
        oPacket.EncodeInt(dwCharacterID);
        return oPacket;
    }

    public static OutPacket MigrationRequest(long nSessionID, int dwCharacterID, String sIP) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.MigrationRequest);
        oPacket.EncodeLong(nSessionID);
        oPacket.EncodeInt(dwCharacterID);
        oPacket.EncodeString(sIP);
        return oPacket;
    }

    public static void OnMigrationResult(CenterSocket pSocket, InPacket iPacket) {
        long nSessionID = iPacket.DecodeLong();

        ClientSocket pClient = ClientSessionManager.GetSessionByID(nSessionID);

        if (!iPacket.DecodeBool()) {
            pClient.Close();
            return;
        }

        pClient.EncInit(); //Switch Crypto mode

        //TODO: Handling...
    }
}
