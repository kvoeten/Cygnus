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
package login;

import io.netty.channel.Channel;
import login.packet.Login;
import login.packet.LoginPacket;
import net.InPacket;

import net.Socket;
import server.APIFactory;
import util.HexUtils;

/**
 *
 * @author Kaz Voeten
 */
public class LoginServerSocket extends Socket {
    
    public LoginServerSocket(Channel channel, int uSeqSend, int uSeqRcv) {
        super(channel, uSeqSend, uSeqRcv);
    }

    public void ProcessPacket(InPacket iPacket) {
        short nPacketID = iPacket.DecodeShort();
        switch (nPacketID) {
            case LoginPacket.OnProcessLogin:
                APIFactory.RequestAccount(this, iPacket);
                break;
            case LoginPacket.OnCheckDuplicateID:
                Login.OnCheckDuplicateID(this, iPacket);
                break;
            case LoginPacket.OnCreateNewCharacter:
                Login.OnCreateNewCharacter(this, iPacket);
                break;
            case LoginPacket.OnBan:
                Login.OnBan(this, iPacket);
                break;
            case LoginPacket.OnChangeCharacterLocation:
                Login.OnChangeCharacterLocation(this, iPacket);
                break;
            case LoginPacket.OnSetSPW:
                Login.OnSetSPW(this, iPacket);
                break;
            case LoginPacket.OnSetState:
                Login.OnSetState(this, iPacket);
                break;
            case LoginPacket.OnDeleteCharacter:
                Login.OnDeleteCharacter(this, iPacket);
                break;
            default:
                System.out.println("[DEBUG] Received unhandled Login packet. nPacketID: "
                        + nPacketID + ". Data: "
                        + HexUtils.ToHex(iPacket.DecodeBuffer(iPacket.GetLength())));
        }
    }
}
