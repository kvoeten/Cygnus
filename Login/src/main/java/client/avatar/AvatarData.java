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

import net.packet.InPacket;
import net.packet.OutPacket;

/**
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
}
