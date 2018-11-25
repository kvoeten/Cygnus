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
package template.item;

import bin.WzBinaryReader;
import java.io.IOException;
import java.nio.file.Paths;

import bin.WzBinaryWriter;
import wz.WzFile;
import wz.WzObject;
import wz.common.WzDataTool;
import wz.io.WzMappedInputStream;

/**
 *
 * @author Kaz Voeten does some of the equip stat flags wrong, has to be revised for accuracy.
 */
public class CharacterFactory {
    //TODO: Hair, Face, Familiar, PetEquip, TamingMob
    //Note: Hair (and probs face) has weird AF vSlot values. 

    private final byte[] key;
    private final int version;

    public CharacterFactory(byte[] key, int version) {
        this.key = key;
        this.version = version;
    }

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

            ItemFactory.mItemData.put(itemID, equip);
        }
    }

    public void dumpBinaryEquips(String wzFolder, byte[] key) {
        try {
            WzBinaryWriter pWriter = new WzBinaryWriter(wzFolder + "/Character.bin");
            WzMappedInputStream in = new WzMappedInputStream(Paths.get(wzFolder, "/Character.wz"));
            WzFile equipWZ = new WzFile("/Character.wz", (short) version);
            in.setKey(key);
            in.setHash(version);
            equipWZ.parse(in);

            //Dump data in following order
            dumpBasicEquipCategory(equipWZ.getChild("Accessory"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Android"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("ArcaneForce"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Bits"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Cap"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Cape"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Coat"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Dragon"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Glove"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Longcoat"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Mechanic"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("MonsterBook"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Pants"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Ring"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Shield"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Shoes"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Totem"), pWriter);
            dumpBasicEquipCategory(equipWZ.getChild("Weapon"), pWriter);

            pWriter.Write();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void dumpBasicEquipCategory(WzObject<?, ?> category, WzBinaryWriter pWriter) {
        pWriter.EncodeLong((int) category.getChildren().size());
        for (WzObject<?, ?> item : category) {

            int itemID = Integer.parseInt(item.getName().replace(".img", ""));
            pWriter.EncodeInt(itemID);

            item = item.getChild("info");

            pWriter.EncodeBool(WzDataTool.getBoolean(item, "cash", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "accountShareable", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "bossReward", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "equipTradeBlock", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "epicItem", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "canLevel", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "notSale", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "expireOnLogout", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "royalSpecial", false));
            pWriter.EncodeBool(WzDataTool.getBoolean(item, "android", false));

            pWriter.EncodeString(WzDataTool.getString(item, "islot", "null"));
            pWriter.EncodeString(WzDataTool.getString(item, "vslot", "null"));
            pWriter.EncodeString(WzDataTool.getString(item, "afterImage", ""));

            pWriter.EncodeInt(WzDataTool.getInteger(item, "reqSTR", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "reqDEX", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "reqINT", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "reqLUK", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "reqJob", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "reqLevel", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "price", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "setItemID", 0));

            pWriter.EncodeInt(WzDataTool.getInteger(item, "attack", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "attackSpeed", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incMAD", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incPAD", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "reduceReq", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "tuc", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "durability", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incACC", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incMHP", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incMMP", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incSTR", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incDEX", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incINT", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incLUK", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incPDD", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incMDD", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incEVA", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incSpeed", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incJump", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incLUK", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incBDR", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incMDR", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "incDamR", 0));

            pWriter.EncodeInt(WzDataTool.getInteger(item, "tradeAvailable", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "charmEXP", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "bitsSlot", 0));
        }
    }
}
