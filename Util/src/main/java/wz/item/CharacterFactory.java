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
package wz.item;

import bin.WzBinaryReader;

/**
 *
 * @author Kaz Voeten does some of the equip stat flags wrong, has to be revised for accuracy.
 */
public class CharacterFactory {
    //TODO: Hair, Face, Familiar, PetEquip, TamingMob
    //Note: Hair (and probs face) has weird AF vSlot values. 

    public CharacterFactory() {}

    public void loadBinaryEquips(String wzFolder) {
        WzBinaryReader pReader = new WzBinaryReader(wzFolder);
        int children = 18;
        for (int i = 0; i < children; i++) {
            parseBasicEquipCategory(pReader);
        }
    }

    private void parseBasicEquipCategory(WzBinaryReader pReader) {
        long items = pReader.DecodeLong();
        for (long i = 0; i < items; i++) {

            int itemID = pReader.DecodeInt();
            EquipItem equip = new EquipItem(itemID);
            equip.type = InventoryType.Equip;

            equip.isCash = pReader.DecodeBool();
            equip.accountSharable = pReader.DecodeBool();
            equip.bossReward = pReader.DecodeBool();
            equip.equipTradeBlock = pReader.DecodeBool();
            equip.epicItem = pReader.DecodeBool();
            equip.canLevel = pReader.DecodeBool();
            equip.notSale = pReader.DecodeBool();
            equip.expireOnLogout = pReader.DecodeBool();
            equip.royalSpecial = pReader.DecodeBool();
            equip.android = pReader.DecodeBool();

            equip.islot = pReader.DecodeString();
            equip.vslot = pReader.DecodeString();
            equip.afterImage = pReader.DecodeString();

            equip.reqSTR = pReader.DecodeInt();
            equip.reqDEX = pReader.DecodeInt();
            equip.reqINT = pReader.DecodeInt();
            equip.reqLUK = pReader.DecodeInt();
            equip.reqJob = pReader.DecodeInt();
            equip.reqLevel = pReader.DecodeInt();
            equip.nPrice = pReader.DecodeInt();
            equip.setItemID = pReader.DecodeInt();

            int value;
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niPAD, value); //base attack?
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMAD, value); //base attack?
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMAD, value); //inc attack?
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niPAD, value); //inc attack?
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.iReduceReq, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nRUC, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nDurability, value);
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nDurabilityMax, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niACC, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMaxHP, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMaxMP, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niSTR, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niDEX, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niINT, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niLUK, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niPDD, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMDD, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niEVA, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niSpeed, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niJump, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niLUK, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nBDR, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMDR, value);
            }
            value = pReader.DecodeInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nDamR, value);
            }

            equip.tradeAvailable = pReader.DecodeInt();
            equip.charmEXP = pReader.DecodeInt();
            equip.bitsSlot = pReader.DecodeInt();

            Item.mItemData.put(itemID, equip);
        }
    }
}
