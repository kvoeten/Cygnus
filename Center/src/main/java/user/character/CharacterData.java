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
package user.character;

import java.util.Date;

import net.packet.OutPacket;
import net.packet.FileTime;
import wz.item.GW_ItemSlotBase;
import wz.item.GW_ItemSlotEquip;
import wz.item.ItemSlotIndex;

/**
 * @author Kaz Voeten
 */
public class CharacterData {

    public static final long dbCharFlag = 0xFFFFFFFFFFFFFFFFL;
    public final AvatarData pAvatar;

    public GW_ItemSlotBase[][] aaItemSlot = new GW_ItemSlotBase[5][60];//60 slots per inv for now
    public GW_ItemSlotEquip[] aEquipped = new GW_ItemSlotEquip[ItemSlotIndex.FP_END]; //nPos <= Highest BodyPart.
    // public VirtualEquipInventory VEInventory = new VirtualEquipInventory();

    public int[][] aStealMemory = new int[5][4];
    public byte nCombatOrders = 0;
    public long nMoney = 0;
    public int nSlotHyper = 0;
    //GW_MonsterBattleRankInfo
    //BagData
    //MONSTERLIFE_INVITEINFO
    //GW_CoupleRecord
    //GW_FriendRecord
    //GW_MarriageRecord
    //GW_Core
    //GW_WildHunterInfo
    //GW_CharacterPotentialSkill
    //BuyLimitData
    //GW_ExpConsumeItem

    public CharacterData LoadFromDB(AvatarData pAvatar) {
        CharacterData pRet = new CharacterData(pAvatar);
        //TODO: Load from db.
        return pRet;
    }

    public static void Create(AvatarData pAvatar) {

    }

    public CharacterData(AvatarData pAvatar) {
        this.pAvatar = pAvatar;
    }

    public boolean IncreaseInventorySize(int nType, int nSize) {
        if (aaItemSlot[nType].length > 128) {
            return false;
        }

        int nIncrementedSize = aaItemSlot[nType].length + nSize;
        if (nIncrementedSize > 128) {
            nIncrementedSize = 128;
        }

        GW_ItemSlotBase[] aItemSlot = new GW_ItemSlotBase[nIncrementedSize];
        System.arraycopy(aaItemSlot[nType], 0, aItemSlot, 0, aaItemSlot[nType].length);
        aaItemSlot[nType] = aItemSlot;
        return true;
    }

    public void Encode(OutPacket oPacket) {
        oPacket.EncodeLong(dbCharFlag);
        oPacket.EncodeByte(nCombatOrders);

        for (int i = 0; i < 3; i++) {
            oPacket.EncodeInt(-20);//aPetActiveSkillCoolTime
        }

        oPacket.EncodeByte(0); //if > 0 nPvPExp
        oPacket.EncodeByte(0); //if > 0 weird loop
        oPacket.EncodeInt(0); //if > 0 encode some filetime/willexp bs.
        oPacket.EncodeByte(0); // (if byte > 0, decode a byte and an int. if int > 0, decodebuffer. after the loop, decode an int and if int > 0, decode buffer)

        if ((dbCharFlag & 1) != 0) {
            pAvatar.pCharacterStat.Encode(oPacket);
            oPacket.EncodeByte(0); //GW_FriendRecord.nCapacity

            oPacket.EncodeByte(0); // if > 0 encode string (Bless of fairy origin sCharacterName)
            oPacket.EncodeByte(0); // if > 0 encode string (Bless of empress origin sCharacterName)
            oPacket.EncodeByte(0); // if > 0 encode string (Ult explorer questinfoex)
        }

        if ((dbCharFlag & 2) != 0) {
            oPacket.EncodeLong(nMoney);
        }

        if ((dbCharFlag & 8) != 0) {
            oPacket.EncodeInt(0);//if > 0 encode GW_ExpConsumeItem (int nItemID, int nMinLevel, int nMaxLevel, long nRemainExp64)
            oPacket.EncodeInt(0);//Monsterbattle stuff?

            for (int i = 0; i < aaItemSlot.length; ++i) {
                oPacket.EncodeByte(aaItemSlot[i].length); // Inventory sizes (eq, use, setup, etc, cash)
            }

            (new FileTime(false)).Encode(oPacket); //Pendant slot acquired date

            oPacket.EncodeByte(0); //

            int nSlot = ItemSlotIndex.BP_CAP;
            do { //Equipped (normal)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.BP_STICKER);
            oPacket.EncodeShort(0x00);

            do { //Equipped (cash) (need to figure out the range for this)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= 1000);
            oPacket.EncodeShort(0x00);

            for (int i = 0; i < aaItemSlot[0].length; i++) { //Equip inventory
                oPacket.EncodeShort(i);
                aaItemSlot[0][i].RawEncode(oPacket);
            }
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.DP_BASE;
            do { //Equipped (evan)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.DP_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.MP_BASE;
            do { //Equipped (mechanic)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.MP_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.AP_BASE;
            do { //Equipped (android)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.AP_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.DU_BASE;
            do { //Equipped (dressup)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.DU_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.BITS_BASE;
            do { //Equipped (bits)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.BITS_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.ZERO_BASE;
            do { //Equipped (zero)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.ZERO_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.MBP_BASE;
            do { //Equipped (Monster Battle)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.MBP_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.AS_BASE;
            do { //Equipped (Arcane Symbol)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.AS_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.TP_BASE;
            do { //Equipped (Totem)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.TP_END);
            oPacket.EncodeShort(0x00);

            nSlot = ItemSlotIndex.FP_BASE;
            do { //Equipped (Fox Man/ Haku)
                if (aEquipped[nSlot] != null) {
                    oPacket.EncodeShort(aEquipped[nSlot].nSlot);
                    aEquipped[nSlot].RawEncode(oPacket);
                }
            } while (nSlot <= ItemSlotIndex.FP_END);
            oPacket.EncodeShort(0x00);

            oPacket.EncodeShort(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeShort(0);
            oPacket.EncodeShort(0);

            for (int i = 0; i < aaItemSlot[1].length; i++) { //Use inventory
                oPacket.EncodeByte(i);
                aaItemSlot[1][i].RawEncode(oPacket);
            }
            oPacket.EncodeByte(0x00);

            for (int i = 0; i < aaItemSlot[2].length; i++) { //Setup inventory
                oPacket.EncodeByte(i);
                aaItemSlot[2][i].RawEncode(oPacket);
            }
            oPacket.EncodeByte(0x00);

            for (int i = 0; i < aaItemSlot[3].length; i++) { //Etc inventory
                oPacket.EncodeByte(i);
                aaItemSlot[3][i].RawEncode(oPacket);
            }
            oPacket.EncodeByte(0x00);

            for (int i = 0; i < aaItemSlot[4].length; i++) { //Cash inventory
                oPacket.EncodeByte(i);
                aaItemSlot[4][i].RawEncode(oPacket);
            }
            oPacket.EncodeByte(0x00);

            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeByte(0);
        }

        if ((dbCharFlag & 0x100) != 0) { //Skill Info
            oPacket.EncodeBool(false);
        }

        if ((dbCharFlag & 0x8000) != 0) { //Cooldown Info
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x200) != 0) { //Quest (started) Info
            oPacket.EncodeBool(true);
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x4000) != 0) { //Quest (finished) Info
            oPacket.EncodeBool(true);
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x400) != 0) { //Minigame Record
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x800) != 0) { //Ring Information
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x1000) != 0) { //Tele rocks info
            for (int i = 0; i < 5; i++) {
                oPacket.EncodeInt(0);
            }
            for (int i = 0; i < 10; i++) {
                oPacket.EncodeInt(0);
            }
            for (int i = 0; i < 13; i++) {
                oPacket.EncodeInt(0);
            }
            for (int i = 0; i < 13; i++) {
                oPacket.EncodeInt(0);
            }
        }

        if ((dbCharFlag & 0x20000) != 0) { //
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x10000) != 0) { // Monster Book Info
            oPacket.EncodeBool(false);
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x100000) != 0) { // Monster Book Set
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x200000) != 0) { // 
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x400000) != 0) { // Familiars
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x80000) != 0) { // QuestInfoEx
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x2000) != 0) { // 
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x40000) != 0) { // new year info
            oPacket.EncodeShort(0);
        }

        oPacket.EncodeByte(0);

        if ((dbCharFlag & 0x1000) != 0) { // 
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x200000) != 0) { // Jaguar info
            if (GW_CharacterStat.IsResistanceArchor(pAvatar.pCharacterStat.nJob)) {
                oPacket.EncodeInt(0);
                for (int i = 0; i < 5; i++) {
                    oPacket.EncodeInt(0);
                }
            }
        }

        if ((dbCharFlag & 0x800) != 0) { // Zero info
            if (GW_CharacterStat.IsZeroJob(pAvatar.pCharacterStat.nJob)) {
                pAvatar.pZeroInfo.Encode(oPacket);
            }
        }

        if ((dbCharFlag & 0x4000000) != 0) { // GW_NpcShopBuyLimit
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x20000000) != 0) { //Selected Stolen Skills
            for (int i = 0; i < 5; i++) {
                int nMaxSteal = 4;
                if (i == 3) {
                    nMaxSteal = 3;
                } else if (i > 3) {
                    nMaxSteal = 2;
                }
                for (int j = 0; j < nMaxSteal; j++) {
                    oPacket.EncodeInt(aStealMemory[i][j]);
                }
            }
        }

        if ((dbCharFlag & 0x10000000) != 0) { //Stolen skills
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x80000000) != 0) { // Ability info
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x10000) != 0) { // GW_SoulCollection
            oPacket.EncodeShort(0);
        }

        oPacket.EncodeInt(0);
        oPacket.EncodeByte(0);

        if ((dbCharFlag & 1) != 0) { // Honor Info
            oPacket.EncodeInt(0); //level
            oPacket.EncodeInt(0); //exp
        }

        if ((dbCharFlag & 2) != 0) { // 
            oPacket.EncodeBool(true);
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 4) != 0) { // 
            oPacket.EncodeByte(0);
        }

        if ((dbCharFlag & 8) != 0) { // GW_DressupInfo
            oPacket.EncodeInt(0); //face
            oPacket.EncodeInt(0); //hair
            oPacket.EncodeInt(0); //suit
            oPacket.EncodeByte(0); //?
            oPacket.EncodeInt(0); //mixbasehaircolor
            oPacket.EncodeInt(0); //mixaddhaircolor
            oPacket.EncodeInt(0); //mixhairbaseprob
        }

        if ((dbCharFlag & 0x20000) != 0) { // 
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            (new FileTime(false)).Encode(oPacket);
            oPacket.EncodeString("");
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x10) != 0) { // Evo Info
            oPacket.EncodeShort(0);
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x20) != 0) { // 
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x80) != 0) { // Cube item info
            oPacket.EncodeByte(0);
        }

        if ((dbCharFlag & 0x400) != 0) { // 
            oPacket.EncodeInt(0);
            (new FileTime(false)).Encode(oPacket);
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x20000) != 0) { // RunnerGameRecord
            oPacket.EncodeInt(pAvatar.pCharacterStat.dwCharacterID);
            oPacket.EncodeInt(0); //nLastScore
            oPacket.EncodeInt(0); //nHighScore
            oPacket.EncodeInt(0); //nRunnerPoint
            (new FileTime(false)).Encode(oPacket);
            oPacket.EncodeInt(10); //nTotalLeft
        }

        if ((dbCharFlag & 0x80000) != 0) { // 
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeLong(0);
        }

        oPacket.EncodeShort(0); // mCollectionRecord.size

        if ((dbCharFlag & 0x40000) != 0) { // mNXRecord
            oPacket.EncodeShort(0);
        }

        oPacket.EncodeByte(0);
        oPacket.EncodeInt(0);

        if ((dbCharFlag & 0x200000) != 0) { // vMatrix
            oPacket.EncodeInt(0);//size
        }

        if ((dbCharFlag & 0x400000) != 0) { // 
            oPacket.EncodeInt(0);
        }

        if ((dbCharFlag & 0x4000000) != 0) { // 
            oPacket.EncodeByte(0);
            oPacket.EncodeShort(0);
            oPacket.EncodeShort(0);
        }

        if ((dbCharFlag & 0x8000000) != 0) { // 
            oPacket.EncodeByte(0);
        }

        if ((dbCharFlag & 0x10000000) != 0) { // 
            oPacket.EncodeInt(0); //sCharacterName[10] // seems wrong
            oPacket.EncodeInt(0); // nSkin
        }

        if ((dbCharFlag & 0x2000) != 0) { // core aura
            oPacket.EncodeInt(0); //id
            oPacket.EncodeInt(pAvatar.pCharacterStat.dwCharacterID);
            oPacket.EncodeInt(0); //Skill Level
            oPacket.EncodeInt(0); //Expiry Timer
            oPacket.EncodeInt(0); //
            oPacket.EncodeInt(0); //att
            oPacket.EncodeInt(0); //dex
            oPacket.EncodeInt(0); //luk
            oPacket.EncodeInt(0); //matt
            oPacket.EncodeInt(0); //int
            oPacket.EncodeInt(0); //str
            oPacket.EncodeInt(0); //
            oPacket.EncodeInt(0); //max
            oPacket.EncodeInt(0); //
            oPacket.EncodeInt(0); //
            (new FileTime(new Date())).Encode(oPacket);
            oPacket.EncodeByte(0);
            oPacket.EncodeByte(1);
        }

        if ((dbCharFlag & 0x80000) != 0) { //
            oPacket.EncodeShort(0);
        }

        //Red Leaf Info
        oPacket.EncodeInt(pAvatar.nAccountID);
        oPacket.EncodeInt(pAvatar.pCharacterStat.dwCharacterID);
        oPacket.EncodeInt(4);
        oPacket.EncodeInt(0);
        for (int i = 0; i < 4; i++) {
            oPacket.EncodeInt(9410165 + i);
            oPacket.EncodeInt(0); //nFriendShipPoints
        }

        if ((dbCharFlag & 0x200) != 0) { //
            oPacket.EncodeBool(false);
        }

        if ((dbCharFlag & 0x80000) != 0) {//
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeInt(0);
            oPacket.EncodeShort(0);
            oPacket.EncodeShort(0);
        }
    }
}
