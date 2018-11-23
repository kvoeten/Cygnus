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
package auth;

import auth.packet.Auth;
import auth.packet.AuthPacket;
import io.netty.channel.Channel;
import net.InPacket;

import net.Socket;
import util.HexUtils;

/**
 *
 * @author Kaz Voeten
 */
public class AuthServerSocket extends Socket {

    public AuthServerSocket(Channel channel, int uSeqSend, int uSeqRcv) {
        super(channel, uSeqSend, uSeqRcv);
    }

    public void ProcessPacket(InPacket iPacket) {
        short nPacketID = iPacket.DecodeShort();
        switch (nPacketID) {
            case AuthPacket.OnUpdateSPWResult:
                Auth.OnUpdateSPWResult(iPacket);
                break;
            default:
                System.out.println("[DEBUG] Received unhandled Auth packet. nPacketID: "
                        + nPacketID + ". Data: "
                        + HexUtils.ToHex(iPacket.DecodeBuffer(iPacket.GetLength())));
        }
    }
}
