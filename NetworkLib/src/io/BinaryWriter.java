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
package io;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Kaz Voeten
 */
public class BinaryWriter {

    private static final Charset ASCII = Charset.forName("US-ASCII");
    private final ByteBuf pBuff;
    private final FileOutputStream fos;
    private final Random rand;
    private final byte[] aIV;

    public BinaryWriter(String path) throws FileNotFoundException, IOException {
        this.rand = new Random();
        File fOutput = new File(path);
        fOutput.createNewFile();
        this.fos = new FileOutputStream(path, false);
        this.pBuff = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
        this.aIV = new byte[16];
        rand.nextBytes(aIV);
    }

    public void WriteFile() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        FileCrypto fCrypto = new FileCrypto(aIV);
        byte[] aData = new byte[pBuff.readableBytes()];
        pBuff.readBytes(aData);
        aData = fCrypto.Encrypt(aData);
        byte[] aFile = new byte[aData.length + aIV.length];
        System.arraycopy(aIV, 0, aFile, 0, aIV.length);
        System.arraycopy(aData, 0, aFile, aIV.length - 1, aData.length);
        fos.write(aFile);
    }

    public void Write(byte[] aData) {
        for (int x = 0; x < aData.length; x++) {
            WriteByte(aData[x]);
        }
    }

    public void WriteByte(int nValue) {
        pBuff.writeByte(nValue);
    }

    public void WriteBool(boolean bValue) {
        WriteByte(bValue ? 1 : 0);
    }

    public final void WriteShort(int nValue) {
        pBuff.writeShort(nValue);
    }

    public final void WriteInt(int nValue) {
        pBuff.writeInt(nValue);
    }

    public final void WriteString(String sData) {
        byte[] aData = sData.getBytes(ASCII);
        WriteShort((short) aData.length);
        Write(aData);
    }

    public final void WritePos(Point pPoint) {
        WriteShort(pPoint.x);
        WriteShort(pPoint.y);
    }

    public final void WriteRect(Rectangle pRect) {
        WriteInt(pRect.x);
        WriteInt(pRect.y);
        WriteInt(pRect.x + pRect.width);
        WriteInt(pRect.y + pRect.height);
    }

    public final void WriteLong(long nValue) {
        pBuff.writeLong(nValue);
    }

}
