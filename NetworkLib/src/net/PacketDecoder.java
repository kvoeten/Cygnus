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

import crypto.AESCipher;
import crypto.IGCipher;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 *
 * @author Kaz Voeten
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> out) throws Exception {
        Socket pSocket = chc.channel().attr(Socket.SESSION_KEY).get();
        if (pSocket != null) {
            int uSeqRcv = pSocket.uSeqRcv;

            if (pSocket.nDecodeLen == -1) {
                if (in.readableBytes() < 4) {
                    return;
                }

                int uRawSeq = in.readShortLE();
                int uDataLen = in.readShortLE();
                if (pSocket.bEncryptData) {
                    uDataLen ^= uRawSeq;
                }

                if (uDataLen > 0x50000) {
                    System.out.println("Recv packet length overflow.");
                    return;
                }

                short uSeqBase = (short) ((uSeqRcv >> 16) ^ uRawSeq);
                if (pSocket.bEncryptData && uSeqBase != AESCipher.nVersion) {
                    if (pSocket.pSocketMode == SocketMode.CLIENT) {
                        pSocket.Close();
                        return;
                    }
                }

                pSocket.nDecodeLen = uDataLen;
            }

            if (in.readableBytes() >= pSocket.nDecodeLen) {
                byte[] aData = new byte[pSocket.nDecodeLen];
                in.readBytes(aData);

                if (pSocket.bEncryptData) {
                    AESCipher.Crypt(aData, uSeqRcv);
                }

                pSocket.uSeqRcv = IGCipher.InnoHash(uSeqRcv, 4, 0);
                pSocket.nDecodeLen = -1;

                out.add(new InPacket(aData));
            }
        }
    }
}
