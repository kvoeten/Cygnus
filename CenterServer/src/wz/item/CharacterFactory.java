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

import user.character.inventory.ItemSlotIndex;
import user.character.inventory.GW_ItemSlotEquipBase;
import io.BinaryReader;
import io.BinaryWriter;
import java.io.IOException;
import java.nio.file.Paths;
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
        BinaryReader input = new BinaryReader(wzFolder + "Character.bin");
        int children = 18;
        for (int i = 0; i < children; i++) {
            parseBasicEquipCategory(input);
        }
    }

    private void parseBasicEquipCategory(BinaryReader input) {
        long items = input.ReadLong();
        for (long i = 0; i < items; i++) {

            int itemID = input.ReadInt();
            EquipItem equip = new EquipItem(itemID);
            equip.type = InventoryType.Equip;

            equip.isCash = input.ReadBool();
            equip.accountSharable = input.ReadBool();
            equip.bossReward = input.ReadBool();
            equip.equipTradeBlock = input.ReadBool();
            equip.epicItem = input.ReadBool();
            equip.canLevel = input.ReadBool();
            equip.notSale = input.ReadBool();
            equip.expireOnLogout = input.ReadBool();
            equip.royalSpecial = input.ReadBool();
            equip.android = input.ReadBool();

            equip.islot = input.ReadString();
            equip.vslot = input.ReadString();
            equip.afterImage = input.ReadString();

            equip.reqSTR = input.ReadInt();
            equip.reqDEX = input.ReadInt();
            equip.reqINT = input.ReadInt();
            equip.reqLUK = input.ReadInt();
            equip.reqJob = input.ReadInt();
            equip.reqLevel = input.ReadInt();
            equip.nPrice = input.ReadInt();
            equip.setItemID = input.ReadInt();

            int value;
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niPAD, value); //base attack?
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMAD, value); //base attack?
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMAD, value); //inc attack?
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niPAD, value); //inc attack?
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.iReduceReq, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nRUC, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nDurability, value);
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nDurabilityMax, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niACC, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMaxHP, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMaxMP, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niSTR, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niDEX, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niINT, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niLUK, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niPDD, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMDD, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niEVA, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niSpeed, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niJump, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niLUK, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nBDR, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.niMDR, value);
            }
            value = input.ReadInt();
            if (value > 0) {
                equip.eqStats.mStats.put(GW_ItemSlotEquipBase.Flags.nDamR, value);
            }

            equip.tradeAvailable = input.ReadInt();
            equip.charmEXP = input.ReadInt();
            equip.bitsSlot = input.ReadInt();

            ItemFactory.mItemData.put(itemID, equip);
        }
    }

    public void dumpBinaryEquips(String wzFolder, byte[] key) {
        try {
            BinaryWriter writer = new BinaryWriter(wzFolder + "Character.bin");
            WzMappedInputStream in = new WzMappedInputStream(Paths.get("wz", "Character.wz"));
            WzFile equipWZ = new WzFile("Character.wz", (short) version);
            in.setKey(key);
            in.setHash(version);
            equipWZ.parse(in);

            //Dump data in following order
            dumpBasicEquipCategory(equipWZ.getChild("Accessory"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Android"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("ArcaneForce"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Bits"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Cap"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Cape"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Coat"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Dragon"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Glove"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Longcoat"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Mechanic"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("MonsterBook"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Pants"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Ring"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Shield"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Shoes"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Totem"), writer);
            dumpBasicEquipCategory(equipWZ.getChild("Weapon"), writer);

            try {
                writer.WriteFile();
            } catch (Exception ex) {
                System.out.println("Couldn't finalize bin file.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void dumpBasicEquipCategory(WzObject<?, ?> category, BinaryWriter writer) {
        writer.WriteLong((int) category.getChildren().size());
        for (WzObject<?, ?> item : category) {

            int itemID = Integer.parseInt(item.getName().replace(".img", ""));
            writer.WriteInt(itemID);

            item = item.getChild("info");

            writer.WriteBool(WzDataTool.getBoolean(item, "cash", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "accountShareable", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "bossReward", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "equipTradeBlock", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "epicItem", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "canLevel", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "notSale", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "expireOnLogout", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "royalSpecial", false));
            writer.WriteBool(WzDataTool.getBoolean(item, "android", false));

            writer.WriteString(WzDataTool.getString(item, "islot", "null"));
            writer.WriteString(WzDataTool.getString(item, "vslot", "null"));
            writer.WriteString(WzDataTool.getString(item, "afterImage", ""));

            writer.WriteInt(WzDataTool.getInteger(item, "reqSTR", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "reqDEX", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "reqINT", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "reqLUK", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "reqJob", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "reqLevel", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "price", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "setItemID", 0));

            writer.WriteInt(WzDataTool.getInteger(item, "attack", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "attackSpeed", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incMAD", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incPAD", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "reduceReq", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "tuc", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "durability", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incACC", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incMHP", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incMMP", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incSTR", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incDEX", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incINT", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incLUK", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incPDD", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incMDD", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incEVA", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incSpeed", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incJump", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incLUK", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incBDR", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incMDR", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "incDamR", 0));

            writer.WriteInt(WzDataTool.getInteger(item, "tradeAvailable", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "charmEXP", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "bitsSlot", 0));
        }
    }
}
