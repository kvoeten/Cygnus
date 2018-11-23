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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import net.InPacket;
import net.OutPacket;

/**
 *
 * @author Kaz Voeten
 */
public class GW_CharacterStat {

    public final int dwCharacterID;
    public int dwCharacterIDForLog;
    public int dwWorldIDForLog;
    public String sCharacterName;
    public byte nGender = 0;
    public int nSkin = 0;
    public int nFace = 0;
    public int nHair = 0;
    public byte nMixBaseHairColor = -1;
    public byte nMixAddHairColor = 0;
    public byte nMixHairBaseProb = 0;
    public byte nLevel = 1;
    public short nJob = 0;
    public short nSTR = 4;
    public short nDEX = 4;
    public short nINT = 4;
    public short nLUK = 4;
    public int nHP = 10;
    public int nMHP = 10;
    public int nMP = 10;
    public int nMMP = 10;
    public short nAP = 0;
    public int[] aSP = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};//10
    public long nExp64 = 0;
    public int nPop = 0;
    public int nWP = 0;
    public int dwPosMap = 0;
    public byte nPortal = 0;
    public short nSubJob = 0;
    public int nDefFaceAcc = 0;
    public short nFatigue = 0;
    public int nLastFatigureUpdateTime = 0;
    public int nCharismaEXP = 0;
    public int nInsightExp = 0;
    public int nWillExp = 0;
    public int nCraftExp = 0;
    public int nSenseExp = 0;
    public int nCharmExp = 0;
    public String DayLimit = "";
    public int nPvPExp = 0;
    public byte nPVPGrade = 0;
    public int nPvpPoint = 0;
    public byte nPvpModeLevel = 5;
    public byte nPvpModeType = 6;
    public int nEventPoint = 0;
    //CHARACTER CARDS HERE, MAKE A MAP USING CHARCARD CLASS
    public int ftLastLogoutTimeHigh = 0; //make class with dwhighttime/dwlowtime pls
    public int ftLastLogoutTimeLow = 0;
    public boolean bBurning = false;

    public GW_CharacterStat(int dwCharacterID) {
        this.dwCharacterID = dwCharacterID;
    }

    public void Encode(OutPacket oPacket) {
        oPacket.EncodeInt(dwCharacterID);
        oPacket.EncodeInt(dwCharacterIDForLog);
        oPacket.EncodeInt(dwWorldIDForLog);
        oPacket.EncodeString(sCharacterName, 13);
        oPacket.EncodeByte(nGender);
        oPacket.EncodeByte((byte) nSkin);
        oPacket.EncodeInt(nFace);
        oPacket.EncodeInt(nHair);
        oPacket.EncodeByte(nMixBaseHairColor);
        oPacket.EncodeByte(nMixAddHairColor);
        oPacket.EncodeByte(nMixHairBaseProb);
        oPacket.EncodeByte(nLevel);
        oPacket.EncodeShort(nJob);
        oPacket.EncodeShort(nSTR);
        oPacket.EncodeShort(nDEX);
        oPacket.EncodeShort(nINT);
        oPacket.EncodeShort(nLUK);
        oPacket.EncodeInt(nHP);
        oPacket.EncodeInt(nMHP);
        oPacket.EncodeInt(nMP);
        oPacket.EncodeInt(nMMP);
        oPacket.EncodeShort(nAP);

        if (IsExtendSPJob(nJob)) {
            EncodeExtendSp(oPacket);
        } else {
            oPacket.EncodeShort(aSP[0]);
        }

        oPacket.EncodeLong(nExp64);
        oPacket.EncodeInt(nPop);
        oPacket.EncodeInt(nWP);
        oPacket.EncodeInt(0); //Gach exp?? - not in KMST
        oPacket.EncodeInt(dwPosMap);
        oPacket.EncodeByte(nPortal);
        oPacket.EncodeInt(0);//playtime in some srcs, idfk, not in kmst
        oPacket.EncodeShort(nSubJob);

        if (nJob / 100 == 31 || nJob == 3001 || nJob / 100 == 36 || nJob == 3002 || nJob / 100 == 112 || nJob == 11000) {
            oPacket.EncodeInt(nDefFaceAcc);
        }

        oPacket.EncodeShort(nFatigue); //Became Short
        oPacket.EncodeInt(nLastFatigureUpdateTime);
        oPacket.EncodeInt(nCharismaEXP);
        oPacket.EncodeInt(nInsightExp);
        oPacket.EncodeInt(nWillExp);
        oPacket.EncodeInt(nCraftExp);
        oPacket.EncodeInt(nSenseExp);
        oPacket.EncodeInt(nCharmExp);
        oPacket.EncodeString(DayLimit, 21);
        oPacket.EncodeInt(nPvPExp);
        oPacket.EncodeByte(nPVPGrade);
        oPacket.EncodeInt(nPvpPoint);
        oPacket.EncodeByte(nPvpModeLevel);
        oPacket.EncodeByte(nPvpModeType);
        oPacket.EncodeInt(nEventPoint);//kmst is byte

        //CharacterCard Decode here...
        for (int i = 0; i < 9; i++) {
            oPacket.EncodeInt(0).EncodeByte(0).EncodeInt(0);
        }

        oPacket.EncodeInt(ftLastLogoutTimeHigh);
        oPacket.EncodeInt(ftLastLogoutTimeLow);

        //Legion?
        oPacket.EncodeLong(0);
        oPacket.EncodeLong(0);
        oPacket.EncodeInt(0);
        oPacket.EncodeInt(0);
        oPacket.EncodeInt(0);
        oPacket.EncodeBool(bBurning);
        oPacket.EncodeInt(0);
        oPacket.EncodeInt(0);
    }

    public static GW_CharacterStat Decode(InPacket iPacket) {
        GW_CharacterStat ret = new GW_CharacterStat(iPacket.DecodeInt());
        ret.dwCharacterIDForLog = iPacket.DecodeInt();
        ret.dwWorldIDForLog = iPacket.DecodeInt();
        ret.sCharacterName = iPacket.DecodeString(13);
        ret.nGender = iPacket.DecodeByte();
        ret.nSkin = iPacket.DecodeByte();
        ret.nFace = iPacket.DecodeInt();
        ret.nHair = iPacket.DecodeInt();
        ret.nMixBaseHairColor = iPacket.DecodeByte();
        ret.nMixAddHairColor = iPacket.DecodeByte();
        ret.nMixHairBaseProb = iPacket.DecodeByte();
        ret.nLevel = iPacket.DecodeByte();
        ret.nJob = iPacket.DecodeShort();
        ret.nSTR = iPacket.DecodeShort();
        ret.nDEX = iPacket.DecodeShort();
        ret.nINT = iPacket.DecodeShort();
        ret.nLUK = iPacket.DecodeShort();
        ret.nHP = iPacket.DecodeInt();
        ret.nMHP = iPacket.DecodeInt();
        ret.nMP = iPacket.DecodeInt();
        ret.nMMP = iPacket.DecodeInt();
        ret.nAP = iPacket.DecodeShort();

        if (IsExtendSPJob(ret.nJob)) {
            byte nSize = iPacket.DecodeByte();
            for (int i = 0; i < nSize; i++) {
                iPacket.DecodeByte();
                ret.aSP[i] = iPacket.DecodeInt();
            }
        } else {
            ret.aSP[0] = iPacket.DecodeShort();
        }

        ret.nExp64 = iPacket.DecodeLong();
        ret.nPop = iPacket.DecodeInt();
        ret.nWP = iPacket.DecodeInt();
        iPacket.DecodeInt();
        ret.dwPosMap = iPacket.DecodeInt();
        ret.nPortal = iPacket.DecodeByte();
        iPacket.DecodeInt();
        ret.nSubJob = iPacket.DecodeShort();

        if (ret.nJob / 100 == 31 || ret.nJob == 3001 || ret.nJob / 100 == 36 || ret.nJob == 3002 || ret.nJob / 100 == 112 || ret.nJob == 11000) {
            ret.nDefFaceAcc = iPacket.DecodeInt();
        }

        ret.nFatigue = iPacket.DecodeShort();
        ret.nLastFatigureUpdateTime = iPacket.DecodeInt();
        ret.nCharismaEXP = iPacket.DecodeInt();
        ret.nInsightExp = iPacket.DecodeInt();
        ret.nWillExp = iPacket.DecodeInt();
        ret.nCraftExp = iPacket.DecodeInt();
        ret.nSenseExp = iPacket.DecodeInt();
        ret.nCharmExp = iPacket.DecodeInt();
        ret.DayLimit = iPacket.DecodeString(21);
        ret.nPvPExp = iPacket.DecodeInt();
        ret.nPVPGrade = iPacket.DecodeByte();
        ret.nPvpPoint = iPacket.DecodeInt();
        ret.nPvpModeLevel = iPacket.DecodeByte();
        ret.nPvpModeType = iPacket.DecodeByte();
        ret.nEventPoint = iPacket.DecodeInt();

        for (int i = 0; i < 9; i++) {
            iPacket.DecodeInt();
            iPacket.DecodeByte();
            iPacket.DecodeInt();
        }

        ret.ftLastLogoutTimeHigh = iPacket.DecodeInt();
        ret.ftLastLogoutTimeLow = iPacket.DecodeInt();

        //Legion?
        iPacket.DecodeLong();
        iPacket.DecodeLong();
        iPacket.DecodeInt();
        iPacket.DecodeInt();
        iPacket.DecodeInt();
        ret.bBurning = iPacket.DecodeBool();
        iPacket.DecodeInt();
        iPacket.DecodeInt();
        return ret;
    }

    private void EncodeExtendSp(OutPacket oPacket) {
        int nSize = 0;
        for (int i = 0; i < aSP.length; i++) {
            if (aSP[i] > 0) {
                nSize++;
            }
        }
        oPacket.EncodeByte(nSize);
        for (int i = 0; i < aSP.length; i++) {
            if (aSP[i] > 0) {
                oPacket.EncodeByte(i + 1);
                oPacket.EncodeInt(aSP[i]);
            }
        }
    }

    public static boolean IsExtendSPJob(int nJob) {
        return !IsBeastJob(nJob) && !IsPinkBeanJob(nJob); //prefer this over how client does it lol
    }

    public static boolean IsAdventurerWarrior(int nJob) {
        return nJob == 100 || nJob == 110 || nJob == 111 || nJob == 112 || nJob == 120 || nJob == 121 || nJob == 122 || nJob == 130 || nJob == 131 || nJob == 132;
    }

    public static boolean IsAdventurerMage(int nJob) {
        return nJob == 200 || nJob == 210 || nJob == 211 || nJob == 212 || nJob == 220 || nJob == 221 || nJob == 222 || nJob == 230 || nJob == 231 || nJob == 232;
    }

    public static boolean IsAdventurerArchor(int nJob) { //nice type nexon
        return nJob == 300 || nJob == 310 || nJob == 311 || nJob == 312 || nJob == 320 || nJob == 321 || nJob == 322;
    }

    public static boolean IsAdventurerRogue(int nJob) {
        return nJob == 400 || nJob == 420 || nJob == 421 || nJob == 422 || nJob == 410 || nJob == 411 || nJob == 412 || nJob / 10 == 43;
    }

    public static boolean IsAdventurerPirate(int nJob) {
        return nJob == 500 || nJob == 510 || nJob == 511 || nJob == 512 || nJob == 520 || nJob == 521 || nJob == 522 || IsCannonShooter(nJob);
    }

    public static boolean IsCannonShooter(int nJob) {
        return nJob / 10 == 53 || nJob == 501;
    }

    public static boolean IsCygnusJob(int nJob) {
        return nJob / 1000 == 1;
    }

    public static boolean IsResistanceJob(int nJob) {
        return nJob / 1000 == 3;
    }

    public static boolean IsEvanJob(int nJob) {
        return nJob / 100 == 22 || nJob == 2001;
    }

    public static boolean IsMercedesJob(int nJob) {
        return nJob / 100 == 23 || nJob == 2002;
    }

    public static boolean IsPhantomJob(int nJob) {
        return nJob / 100 == 24 || nJob == 2003;
    }

    public static boolean IsLeaderJob(int nJob) {
        return nJob / 1000 == 5;
    }

    public static boolean IsLuminousJob(int nJob) {
        return nJob / 100 == 27 || nJob == 2004;
    }

    public static boolean IsDragonbornJob(int nJob) {
        return nJob / 1000 == 6;
    }

    public static boolean IsZeroJob(int nJob) {
        return nJob == 10000 || nJob == 10100 || nJob == 10110 || nJob == 10111 || nJob == 10112;
    }

    public static boolean IsHiddenJob(int nJob) {
        return nJob / 100 == 25 || nJob == 2005;
    }

    public static boolean IsAranJob(int nJob) {
        return nJob / 100 == 21 || nJob == 2000;
    }

    public static boolean IsZettJob(int nJob) {
        return nJob / 100 == 57 || nJob == 508;
    }

    public static boolean IsKinesisJob(int nJob) {
        return nJob == 1400 || nJob == 14200 || nJob == 14210 || nJob == 14211 || nJob == 14212;
    }

    public static boolean IsAngelicJob(int nJob) {
        return nJob / 100 == 65;
    }

    public static boolean IsBeastJob(int nJob) {
        return nJob / 100 == 112 || nJob == 11000;
    }

    public static boolean IsPinkBeanJob(int nJob) {
        return nJob == 1310;
    }
}
