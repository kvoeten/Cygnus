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
package net;

import crypto.TripleDESCipher;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import net.packet.OutPacket;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kaz Voeten
 */
public class Socket {

    public static final AttributeKey<Socket> SESSION_KEY = AttributeKey.valueOf("Session");
    private final ReentrantLock Lock = new ReentrantLock(true);
    protected final Channel pChannel;
    public final SocketMode pSocketMode;
    public Map<Integer, Integer> mEncryptedPacketID = new LinkedHashMap<>();
    public TripleDESCipher pCipher;
    public int uSeqSend, uSeqRcv, nCryptoMode = 1, nDecodeLen = -1;
    public boolean bEncryptData = true;

    public Socket(SocketMode pSocketMode, Channel channel, int uSeqSend, int uSeqRcv) {
        this.pSocketMode = pSocketMode;
        this.pChannel = channel;
        this.uSeqSend = uSeqSend;
        this.uSeqRcv = uSeqRcv;
    }

    public void SendPacket(OutPacket msg) {
        pChannel.writeAndFlush(msg);
    }

    public void Close() {
        pChannel.close();
    }

    public String GetIP() {
        return pChannel.remoteAddress().toString().split(":")[0].substring(1);
    }

    public int GetPort() {
        return Integer.parseInt(pChannel.localAddress().toString().split(":")[1]);
    }

    public void Lock() {
        this.Lock.lock();
    }

    public void Unlock() {
        this.Lock.unlock();
    }
}