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
package server.center;

import io.netty.channel.Channel;
import net.InPacket;

import net.Socket;
import server.center.packet.Center;
import server.center.packet.CenterPacket;
import util.HexUtils;

/**
 *
 * @author Kaz Voeten
 */
public class CenterSocket extends Socket {

    public int nWorldID;

    public CenterSocket(Channel channel, int uSeqSend, int uSeqRcv) {
        super(channel, uSeqSend, uSeqRcv);
    }

    public void ProcessPacket(InPacket iPacket) {
        short nPacketID = iPacket.DecodeShort();
        switch (nPacketID) {
            case CenterPacket.OnAddBlock:
                Center.OnAddBlock(iPacket);
                break;
            case CenterPacket.OnSetSPW:
                SendPacket(Center.OnSetPIC(iPacket));
                break;
            case CenterPacket.OnSetState:
                SendPacket(Center.SetState(iPacket));
                break;
            case CenterPacket.OnReceiveAvatarData:
                Center.OnReceiveAvatarData(iPacket);
                break;
            default:
                System.out.println("[DEBUG] Received unhandled Center packet. nPacketID: "
                        + nPacketID + ". Data: "
                        + HexUtils.ToHex(iPacket.DecodeBuffer(iPacket.GetLength())));
        }
    }
}
