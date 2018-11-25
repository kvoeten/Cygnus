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
package net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.awt.Point;
import java.awt.Rectangle;
import java.nio.charset.Charset;

/**
 * @author Kaz Voeten
 */
public class OutPacket {

    public short nPacketID;
    private final ByteBuf pSendBuff;
    private static final Charset ASCII = Charset.forName("US-ASCII");

    public OutPacket(short nPacketID) {
        this.pSendBuff = Unpooled.buffer();
        this.nPacketID = nPacketID;
        pSendBuff.writeShortLE(nPacketID);
    }

    public OutPacket(byte[] aData) {
        this.pSendBuff = Unpooled.buffer();
        pSendBuff.writeBytes(aData);
    }

    public final OutPacket EncodeByte(int nValue) {
        pSendBuff.writeByte(nValue);
        return this;
    }

    public final OutPacket EncodeBuffer(byte[] aData) {
        pSendBuff.writeBytes(aData);
        return this;
    }

    public final OutPacket EncodeBool(boolean bData) {
        return EncodeByte(bData ? 1 : 0);
    }

    public final OutPacket EncodeShort(int nValue) {
        pSendBuff.writeShortLE(nValue);
        return this;
    }

    public final OutPacket EncodeShort(short nValue) {
        pSendBuff.writeShortLE(nValue);
        return this;
    }

    public final OutPacket EncodeChar(char cValue) {
        pSendBuff.writeChar(cValue);
        return this;
    }

    public final OutPacket EncodeInt(int nValue) {
        pSendBuff.writeIntLE(nValue);
        return this;
    }

    public final OutPacket EncodeFloat(float nValue) {
        pSendBuff.writeFloatLE(nValue);
        return this;
    }

    public final OutPacket EncodeLong(long nValue) {
        pSendBuff.writeLongLE(nValue);
        return this;
    }

    public final OutPacket EncodeDouble(double nValue) {
        pSendBuff.writeDoubleLE(nValue);
        return this;
    }

    public final OutPacket EncodeString(String sData, int nLen) {
        byte[] aStringBuffer = sData.getBytes(ASCII);
        byte[] aFillBuffer = new byte[nLen - aStringBuffer.length];
        return EncodeBuffer(aStringBuffer).EncodeBuffer(aFillBuffer);
    }

    public final OutPacket EncodeString(String sData) {
        return EncodeShort(sData.length()).EncodeBuffer(sData.getBytes(ASCII));
    }

    public final OutPacket EncodeBuffer(String sData) {
        EncodeBuffer(sData.getBytes(ASCII));
        return this;
    }

    public final OutPacket Fill(int nValue, int nLenth) {
        for (int i = 0; i < nLenth; i++) {
            EncodeByte(nValue);
        }
        return this;
    }

    public final OutPacket EncodePosition(Point pData) {
        return EncodeShort(pData.x).EncodeShort(pData.y);
    }

    public final OutPacket EncodeRectangle(Rectangle rData) {
        return EncodeInt(rData.x).EncodeInt(rData.y)
                .EncodeInt(rData.x + rData.width).EncodeInt(rData.y + rData.height);
    }

    public int GetLength() {
        return pSendBuff.readableBytes();
    }

    @Override
    public final String toString() {
        byte[] aData = new byte[pSendBuff.readableBytes()];
        int nReaderIndex = pSendBuff.readerIndex();
        pSendBuff.getBytes(nReaderIndex, aData);
        pSendBuff.readerIndex(nReaderIndex);
        return ByteBufUtil.hexDump(aData);
    }

    public byte[] GetData() {
        byte[] aData = new byte[pSendBuff.readableBytes()];
        pSendBuff.getBytes(pSendBuff.readerIndex(), aData);
        return aData;
    }

    public OutPacket Clone() {
        byte[] aData = new byte[pSendBuff.readableBytes()];
        pSendBuff.getBytes(pSendBuff.readerIndex(), aData);
        return new OutPacket(aData);
    }
}
