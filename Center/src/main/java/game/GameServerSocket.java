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
package game;

import game.packet.Game;
import game.packet.GamePacket;
import io.netty.channel.Channel;
import login.packet.Login;
import net.SocketMode;
import net.packet.InPacket;

import net.Socket;

/**
 * @author Kaz Voeten
 */
public class GameServerSocket extends Socket {

    public byte nChannelID;
    public int nMaxUsers;
    public int nPort;
    //public HashMap<Integer, User> mUsers = new HashMap<>();

    public GameServerSocket(Channel channel, int uSeqSend, int uSeqRcv) {
        super(SocketMode.SERVER, channel, uSeqSend, uSeqRcv);
    }

    public void ProcessPacket(InPacket iPacket) {
        short nPacketID = iPacket.DecodeShort();
        switch (nPacketID) {
            case GamePacket.GameServerInformation:
                this.nChannelID = iPacket.DecodeByte();
                this.nMaxUsers = iPacket.DecodeInt();
                this.nPort = iPacket.DecodeInt();
                Login.GameServerInformation();
                System.out.println("[Info] Registered GameServer with channel id: " + nChannelID);
                break;
            case GamePacket.SetState:
                //TODO: API HTTP SETSTATE
                break;
            case GamePacket.MigrationRequest:
                Game.OnMigrationRequest(this, iPacket);
                break;
            default:
                System.out.println("[DEBUG] Received unhandled Game packet. nPacketID: "
                        + nPacketID + ". Data: "
                        + iPacket.toString());
        }
    }
}
