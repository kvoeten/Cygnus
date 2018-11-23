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
import java.io.File;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kaz Voeten
 */
public class BinaryReader {

    private final ByteBuf pBuff;
    byte[] aData, aIV;

    public BinaryReader(String sFile) {
        final File pFile = new File(sFile);
        if (!pFile.exists()) {
            throw new RuntimeException(String.format("File %s does not exist!", sFile));
        }

        try {
            byte[] aFileData = Files.readAllBytes(pFile.toPath());

            //Get IV
            this.aIV = new byte[16];
            System.arraycopy(aFileData, 0, aIV, 0, aIV.length);
            FileCrypto fcCrypto = new FileCrypto(aIV);

            //Trim IV off of data and decrypt data
            this.aData = new byte[aFileData.length - aIV.length];
            System.arraycopy(aFileData, aIV.length, aData, 0, aData.length);
            aData = fcCrypto.Decrypt(aData);

            this.pBuff = Unpooled.buffer(aData.length).order(ByteOrder.LITTLE_ENDIAN).writeBytes(aData);

        } catch (Exception ex) {
            Logger.getLogger(BinaryReader.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(String.format("File %s couldn't be read!", sFile));
        }
    }

    public final byte ReadByte() {
        return (byte) pBuff.readByte();
    }

    public final boolean ReadBool() {
        return ReadByte() != 0;
    }

    public final int ReadInt() {
        return pBuff.readInt();
    }

    public final short ReadShort() {
        return pBuff.readShort();
    }

    public final long ReadLong() {
        return pBuff.readLong();
    }

    public final String ReadAsciiString(final int n) {
        final char ret[] = new char[n];
        for (int x = 0; x < n; x++) {
            ret[x] = (char) ReadByte();
        }
        return new String(ret);
    }

    public final String ReadString() {
        return ReadAsciiString(ReadShort());
    }

    public final Point ReadPos() {
        final int x = ReadShort();
        final int y = ReadShort();
        return new Point(x, y);
    }

    public final byte[] Read(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ReadByte();
        }
        return data;
    }
}
