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
 * @author Novak
 */
public class AvatarData {

    public int nAccountID;
    public int nWorld;
    public int nCharlistPos;
    public int nRank = 0;
    public int nRankMove = 0;
    public int nOverallRank = 0;
    public int nOverallRankMove = 0;
    public GW_CharacterStat pCharacterStat;
    public AvatarLook pAvatarLook;
    public ZeroInfo pZeroInfo = new ZeroInfo();
    public static final String DEFAULT_KEYMAP;

    public AvatarData(int nAccountID) {
        this.nAccountID = nAccountID;
    }

    public void Encode(OutPacket oPacket, boolean bRank) {
        pCharacterStat.Encode(oPacket);
        oPacket.EncodeInt(0); //new, idk
        pAvatarLook.Encode(oPacket);
        if (GW_CharacterStat.IsZeroJob(pCharacterStat.nJob)) {
            pAvatarLook.Encode(oPacket, pZeroInfo);
        }
        oPacket.EncodeBool(false);//m_abOnFamily ?
        oPacket.EncodeBool(nRank != 0 && bRank);
        if (nRank != 0 && bRank) {
            oPacket.EncodeInt(nRank);
            oPacket.EncodeInt(nRankMove);
            oPacket.EncodeInt(nOverallRank);
            oPacket.EncodeInt(nOverallRankMove);
        }
    }

    public static AvatarData Decode(int nAccountID, InPacket iPacket) {
        AvatarData ret = new AvatarData(nAccountID);
        ret.pCharacterStat = GW_CharacterStat.Decode(iPacket);
        iPacket.DecodeInt(); //?
        ret.pAvatarLook = AvatarLook.Decode(ret.pCharacterStat.dwCharacterID, iPacket);
        if (GW_CharacterStat.IsZeroJob(ret.pCharacterStat.nJob)) {
            ret.pZeroInfo = ZeroInfo.Decode(iPacket);
        }
        iPacket.DecodeBool();
        if (iPacket.DecodeBool()) {
            ret.nRank = iPacket.DecodeInt();
            ret.nRankMove = iPacket.DecodeInt();
            ret.nOverallRank = iPacket.DecodeInt();
            ret.nOverallRankMove = iPacket.DecodeInt();
        }
        return ret;
    }

    public void SetSkin(int nSkin) {
        pCharacterStat.nSkin = (byte) nSkin;
        pAvatarLook.nSkin = (byte) nSkin;
    }

    public void SetGender(byte nGender) {
        pCharacterStat.nGender = nGender;
        pAvatarLook.nGender = nGender;
    }

    public void SetFace(int nFace) {
        pCharacterStat.nFace = nFace;
        pAvatarLook.nFace = nFace;
    }

    public void SetHair(int nHair) {
        pCharacterStat.nHair = nHair;
        pAvatarLook.nHair = nHair;
    }

    static {
        StringBuilder map = new StringBuilder();
        map.append("2,4,10;");
        map.append("3,4,12;");
        map.append("4,4,13;");
        map.append("5,4,18;");
        map.append("6,4,24;");
        map.append("7,4,21;");
        map.append("16,4,8;");
        map.append("17,4,5;");
        map.append("18,4,0;");
        map.append("19,4,4;");
        map.append("23,4,1;");
        map.append("24,4,25;");
        map.append("25,4,19;");
        map.append("26,4,14;");
        map.append("27,4,15;");
        map.append("29,5,52;");
        map.append("31,4,2;");
        map.append("33,4,26;");
        map.append("34,4,17;");
        map.append("35,4,11;");
        map.append("37,4,3;");
        map.append("38,4,20;");
        map.append("39,4,27;");
        map.append("40,4,16;");
        map.append("41,4,23;");
        map.append("43,4,9;");
        map.append("44,5,50;");
        map.append("45,5,51;");
        map.append("46,4,6;");
        map.append("48,4,22;");
        map.append("50,4,7;");
        map.append("56,5,53;");
        map.append("57,5,54;");
        map.append("59,6,100;");
        map.append("60,6,101;");
        map.append("61,6,102;");
        map.append("62,6,103;");
        map.append("63,6,104;");
        map.append("64,6,105;");
        map.append("65,6,106;");
        DEFAULT_KEYMAP = map.toString();
    }
}
