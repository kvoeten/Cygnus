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
import client.ClientSessionManager;
import client.packet.Login;
import io.netty.channel.Channel;

import java.util.ArrayList;

import net.SocketMode;
import net.packet.InPacket;
import net.Socket;

/**
 * @author Kaz Voeten
 */
public class CenterSocket extends Socket {

    public int nWorldID = -1;
    public String sWorldName = "Null", sMessage;
    public byte nState;
    public short nExp, nDrop;
    public boolean bCreateChar;
    public ArrayList<GameServer> aChannels = new ArrayList<>();

    public CenterSocket(Channel channel, int uSeqSend, int uSeqRcv) {
        super(SocketMode.SERVER, channel, uSeqSend, uSeqRcv);
    }

    public void ProcessPacket(InPacket iPacket) {
        int nPacketID = iPacket.DecodeShort();
        long nSessionID;
        switch (nPacketID) {
            case CenterPacket.WorldInformation:
                this.nWorldID = iPacket.DecodeInt();
                this.sWorldName = iPacket.DecodeString();
                this.sMessage = iPacket.DecodeString();
                this.nState = iPacket.DecodeByte();
                this.nExp = iPacket.DecodeShort();
                this.nDrop = iPacket.DecodeShort();
                this.bCreateChar = iPacket.DecodeBool();
                System.out.println("[Info] Registered world : " + sWorldName + ".");
                break;
            case CenterPacket.ChannelInformation:
                aChannels.clear();
                System.out.println("[Info] Cleared channel cache.");
                byte nSize = iPacket.DecodeByte();
                for (int i = 0; i < nSize; ++i) {
                    aChannels.add(GameServer.Decode(iPacket));
                    System.out.println("[Info] Registered GameServer with nChannelID "
                            + aChannels.get(i).nChannelID + " to world " + this.sWorldName + ".");
                }
                break;
            case CenterPacket.AccountInformation:
                Center.OnAccountInformation(iPacket, iPacket.DecodeLong());
                break;
            case CenterPacket.CheckDuplicatedIDResponse:
                nSessionID = iPacket.DecodeLong();
                ClientSessionManager.GetSessionByID(nSessionID).SendPacket(Login.DuplicateIDResponse(
                        iPacket.DecodeString(),
                        iPacket.DecodeBool()
                ));
                break;
            case CenterPacket.OnCreateCharacterResponse:
                nSessionID = iPacket.DecodeLong();
                Login.OnCreateNewCharacterResult(ClientSessionManager.GetSessionByID(nSessionID), iPacket);
                break;
            case CenterPacket.OnSetSPWResult:
                Login.OnSetSPWResult(iPacket);
                break;
            case CenterPacket.OnDeleteCharacterResult:
                Center.OnDeleteCharacterResult(iPacket);
                break;
            default:
                System.out.println("[DEBUG] Received unhandled Center packet. nPacketID: "
                        + nPacketID + ". Data: "
                        + iPacket.toString());
                break;
        }
    }
}
