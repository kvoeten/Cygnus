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
package client.avatar;

import net.InPacket;
import net.OutPacket;

/**
 *
 * @author Kaz Voeten
 */
public class ZeroInfo {

    public static final short Flag = -1; //unsigned _int16
    public int nSubHP = 6910;
    public int nSubMP = 100;
    public int nSubSkin = 0;
    public int nSubHair = 37623;
    public int nSubFace = 21290;
    public int nSubMHP = 6910;
    public int nSubMMP = 100;
    public int dbcharZeroLinkCashPart = 0;
    public int nMixBaseHairColor = -1;
    public int nMixAddHairColor = 0;
    public int nMixHairBaseProb = 0;
    public boolean bIsBeta = false;
    public int nLapis = 1572000;
    public int nLazuli = 1562000;

    public ZeroInfo() {
    }

    public void Encode(OutPacket oPacket) {
        if ((Flag & 1) > 0) {
            oPacket.EncodeBool(bIsBeta);
        }
        if ((Flag & 2) > 0) {
            oPacket.EncodeInt(nSubHP);
        }
        if ((Flag & 4) > 0) {
            oPacket.EncodeInt(nSubMP);
        }
        if ((Flag & 8) > 0) {
            oPacket.EncodeByte(nSubSkin);
        }
        if ((Flag & 0x10) > 0) {
            oPacket.EncodeInt(nSubHair);
        }
        if ((Flag & 0x20) > 0) {
            oPacket.EncodeInt(nSubFace);
        }
        if ((Flag & 0x40) > 0) {
            oPacket.EncodeInt(nSubMHP);
        }
        if ((Flag & 0x80) > 0) {
            oPacket.EncodeInt(nSubMMP);
        }
        if ((Flag & 0x100) > 0) {
            oPacket.EncodeInt(dbcharZeroLinkCashPart);
        }
        if ((Flag & 0x200) > 0) {
            oPacket.EncodeInt(nMixBaseHairColor);
            oPacket.EncodeInt(nMixAddHairColor);
            oPacket.EncodeInt(nMixHairBaseProb);
        }
    }

    public static ZeroInfo Decode(InPacket iPacket) {
        ZeroInfo ret = new ZeroInfo();
        if ((Flag & 1) > 0) {
            ret.bIsBeta = iPacket.DecodeBool();
        }
        if ((Flag & 2) > 0) {
            ret.nSubHP = iPacket.DecodeInt();
        }
        if ((Flag & 4) > 0) {
            ret.nSubMP = iPacket.DecodeInt();
        }
        if ((Flag & 8) > 0) {
            ret.nSubSkin = iPacket.DecodeByte();
        }
        if ((Flag & 0x10) > 0) {
            ret.nSubHair = iPacket.DecodeInt();
        }
        if ((Flag & 0x20) > 0) {
            ret.nSubFace = iPacket.DecodeInt();
        }
        if ((Flag & 0x40) > 0) {
            ret.nSubMHP = iPacket.DecodeInt();
        }
        if ((Flag & 0x80) > 0) {
            ret.nSubMMP = iPacket.DecodeInt();
        }
        if ((Flag & 0x100) > 0) {
            ret.dbcharZeroLinkCashPart = iPacket.DecodeInt();
        }
        if ((Flag & 0x200) > 0) {
            ret.nMixBaseHairColor = iPacket.DecodeInt();
            ret.nMixAddHairColor = iPacket.DecodeInt();
            ret.nMixHairBaseProb = iPacket.DecodeInt();
        }
        return ret;
    }
}
