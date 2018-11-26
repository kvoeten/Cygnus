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
package client;

import client.packet.ClientPacket;
import client.packet.Login;
import client.packet.LoopBackPacket;
import crypto.TripleDESCipher;
import io.netty.channel.Channel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.SocketMode;
import net.packet.InPacket;
import net.packet.OutPacket;

import net.Socket;

/**
 * @author Kaz Voeten
 */
public class ClientSocket extends Socket {

    public ScheduledFuture<?> PingTask;
    public long nSessionID = -1;
    private long nLastAliveAck = -1;
    public int nWorldID = -1;
    public Account pAccount;
    public int dwCharacterID;
    public byte[] aMachineID;

    public ClientSocket(Channel channel, int uSeqSend, int uSeqRcv) {
        super(SocketMode.CLIENT, channel, uSeqSend, uSeqRcv);
    }

    public void ProcessPacket(InPacket iPacket) {
        int nPacketID = iPacket.DecodeShort();
        if (nCryptoMode == 2) {
            if (mEncryptedPacketID.containsKey(nPacketID)) {
                nPacketID = mEncryptedPacketID.get(nPacketID);
            }
        }

        switch (nPacketID) {
            case ClientPacket.AliveAck:
            case ClientPacket.ResponseToCheckAliveAck:
                this.nLastAliveAck = System.currentTimeMillis();
                break;
            case ClientPacket.MigrateIn:
                Login.OnMigrateIn(this, iPacket);
                break;
            default:
                System.out.println("[DEBUG] Received unhandled Client packet. nPacketID: "
                        + nPacketID + ". Data: "
                        + iPacket.toString());
        }
    }

    public void AliveReq() {
        if (nLastAliveAck == -1 || nLastAliveAck + (20 * 1000) > System.currentTimeMillis()) {
            SendPacket(Login.AliveReq());
        } else {
            this.Close();
        }
    }

    public void EncInit() {
        nCryptoMode = 2;
        String sBuffer = "";
        byte[] aKey = TripleDESCipher.GenKey("", aMachineID); //pCharacterData.pAvatar.pCharacterStat.sCharacterName
        pCipher = new TripleDESCipher(aKey);

        List<Integer> aUtilizedRandom = new ArrayList<>();
        for (int i = ClientPacket.BeginUser; i < ClientPacket.COUNT; i++) {
            int nRandom = ThreadLocalRandom.current().nextInt(ClientPacket.BeginUser, 9999);
            while (aUtilizedRandom.contains(nRandom)) {
                nRandom = ThreadLocalRandom.current().nextInt(ClientPacket.BeginUser, 9999);
            }
            aUtilizedRandom.add(nRandom);
            mEncryptedPacketID.put(nRandom, i);
            String sPacketID = String.format("%04d", nRandom);
            sBuffer += sPacketID;
        }

        OutPacket oPacket = new OutPacket(LoopBackPacket.EncryptPacketID);
        oPacket.EncodeInt(4);
        pCipher.Encode(oPacket, sBuffer);
        SendPacket(oPacket);
    }
}
