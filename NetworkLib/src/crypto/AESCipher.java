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
package crypto;

/**
 * @author Kaz Voeten
 */
public final class AESCipher {

    private static final AES pCipher;

    public static final short nVersion = 188;
    private static final byte[] aKey = new byte[]{
        (byte) 0x29, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0xF6, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x18, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x5E, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x61, (byte) 0x00, (byte) 0x00, (byte) 0x00
    };

    public static final int uSeqBase = (short) ((((0xFFFF - nVersion) >> 8) & 0xFF) | (((0xFFFF - nVersion) << 8) & 0xFF00));

    static {
        pCipher = new AES(aKey);
    }

    public static byte[] Crypt(byte[] aData, int pSrc) {
        byte[] pdwKey = new byte[]{
            (byte) (pSrc & 0xFF), (byte) ((pSrc >> 8) & 0xFF), (byte) ((pSrc >> 16) & 0xFF), (byte) ((pSrc >> 24) & 0xFF)
        };
        return AES.Crypt(pCipher, aData, pdwKey);
    }
}
