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
package center;

import center.packet.Center;
import center.packet.CenterPacket;
import io.netty.channel.Channel;
import net.SocketMode;
import net.packet.InPacket;

import net.Socket;

/**
 * @author Kaz Voeten
 */
public class CenterSocket extends Socket {
    public int nWorldID;
    public String sWorldName;

    public CenterSocket(Channel channel, int uSeqSend, int uSeqRcv) {
        super(SocketMode.SERVER, channel, uSeqSend, uSeqRcv);
    }

    public void ProcessPacket(InPacket iPacket) {
        int nPacketID = iPacket.DecodeShort();
        switch (nPacketID) {
            case CenterPacket.WorldInformation:
                this.nWorldID = iPacket.DecodeInt();
                this.sWorldName = iPacket.DecodeString();
                System.out.println(String.format("[Info] Registered on world %s.", sWorldName));
                return;
            case CenterPacket.MigrationResult:
                Center.OnMigrationResult(this, iPacket);
                break;
            default:
                System.out.println("[DEBUG] Received unhandled Center packet. nPacketID: "
                        + nPacketID + ". Data: "
                        + iPacket.toString());
        }
    }
}
