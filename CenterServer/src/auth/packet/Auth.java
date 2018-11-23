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
package auth.packet;

import user.account.Account;
import login.packet.Login;
import net.InPacket;
import net.OutPacket;
import user.UserStorage;
import user.character.AvatarData;

/**
 *
 * @author Kaz Voeten
 */
public class Auth {

    public static OutPacket Ban(int nType, String sReason, int nAccountID, String sIP, String sMAC, String sHWID, long uBanDuration) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.AddBlock);
        oPacket.EncodeInt(nType);
        oPacket.EncodeString(sReason);
        oPacket.EncodeLong(uBanDuration);
        switch (nType) {
            case 0:
                oPacket.EncodeString(sIP);
                break;
            case 1:
                oPacket.EncodeInt(nAccountID);
                break;
            case 2:
                oPacket.EncodeInt(nAccountID);
                oPacket.EncodeString(sIP);
                break;
            case 3:
                oPacket.EncodeString(sIP);
                oPacket.EncodeString(sMAC);
                oPacket.EncodeString(sHWID);
                break;
            case 4:
                oPacket.EncodeInt(nAccountID);
                oPacket.EncodeString(sIP);
                oPacket.EncodeString(sMAC);
                oPacket.EncodeString(sHWID);
                break;
        }
        return oPacket;
    }

    public static OutPacket SetSPW(int nAccountID, String sSPW) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.SetSPWResult);
        oPacket.EncodeInt(nAccountID);
        oPacket.EncodeString(sSPW);
        return oPacket;
    }
    
    public static OutPacket SetState(int nAccountID, byte nState) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.SetState);
        oPacket.EncodeInt(nAccountID);
        oPacket.EncodeByte(nState);
        return oPacket;
    }

    public static void OnUpdateSPWResult(InPacket iPacket) {
        int nAccountID = iPacket.DecodeInt();
        String sSecondPassword = iPacket.DecodeString();
        boolean bSuccess = iPacket.DecodeBool();

        if (bSuccess) {
            UserStorage.GetStorage().Lock();
            try {
                bSuccess = false;
                Account pAccount = UserStorage.GetStorage().GetByID(nAccountID);
                if (pAccount != null) {
                    bSuccess = true;
                    pAccount.sSecondPW = sSecondPassword;
                }
            } finally {
                UserStorage.GetStorage().Unlock();
            }
        }

        Login.OnSetSPWResult(nAccountID, bSuccess);
    }

    public static OutPacket OnSendAvatarData(AvatarData pAvatar) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.SendAvatarData);
        oPacket.EncodeInt(pAvatar.pCharacterStat.dwCharacterID);
        oPacket.EncodeInt(pAvatar.nAccountID);
        oPacket.EncodeInt(pAvatar.nWorld);
        oPacket.EncodeInt(pAvatar.nOverallRank);
        oPacket.EncodeInt(pAvatar.nOverallRankMove);
        oPacket.EncodeInt(pAvatar.nRank);
        oPacket.EncodeInt(pAvatar.nRankMove);
        
        oPacket.EncodeInt(pAvatar.pCharacterStat.nLevel);
        oPacket.EncodeInt(pAvatar.pCharacterStat.nJob);
        oPacket.EncodeLong(pAvatar.pCharacterStat.nExp64);
        oPacket.EncodeInt(pAvatar.pCharacterStat.nPop);
        oPacket.EncodeInt(pAvatar.pCharacterStat.nGender);
        oPacket.EncodeInt(pAvatar.pCharacterStat.nSkin);
        oPacket.EncodeInt(pAvatar.pCharacterStat.nHair);
        oPacket.EncodeInt(pAvatar.pCharacterStat.nFace);
        
        oPacket.EncodeString(pAvatar.pCharacterStat.sCharacterName);
        
        pAvatar.pAvatarLook.anEquip.forEach((nPos, nItemID) -> {
            oPacket.EncodeByte(nPos);
            oPacket.EncodeInt(nItemID);
        });
        
        oPacket.EncodeByte(0xFF);
        return oPacket;
    }
}
