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
package client.packet;

import net.packet.LoopBackPacket;
import center.CenterSessionManager;
import center.packet.Center;
import client.ClientSocket;
import net.packet.InPacket;
import net.packet.OutPacket;


/**
 * @author Kaz Voeten
 */
public class Login {

    public static OutPacket AliveReq() {
        OutPacket oPacket = new OutPacket(LoopBackPacket.AliveReq);
        return oPacket;
    }

    public static void OnMigrateIn(ClientSocket pSocket, InPacket iPacket) {
        int nWorldID = iPacket.DecodeInt(); //nPhysicalWorldID
        if (CenterSessionManager.pSession.nWorldID != nWorldID) {
            pSocket.Close(); //World ID Mismatch.
        }
        pSocket.dwCharacterID = iPacket.DecodeInt(); //dwCharacterID
        pSocket.aMachineID = iPacket.DecodeBuffer(0x10); //MachineID
        iPacket.DecodeShort(); //CSecurityClient.usSeq
        iPacket.DecodeLong();//CSecurityClient.ulArgument

        CenterSessionManager.pSession.SendPacket(Center.MigrationRequest(pSocket.nSessionID, pSocket.dwCharacterID, pSocket.GetIP()));
    }

}
