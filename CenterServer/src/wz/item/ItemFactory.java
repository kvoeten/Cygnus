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

import io.BinaryReader;
import io.BinaryWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import wz.WzFile;
import wz.WzObject;
import wz.common.WzDataTool;
import wz.io.WzMappedInputStream;

/**
 *
 * @author Kaz Voeten
 */
public class ItemFactory {

    public static final HashMap<Integer, Item> mItemData = new HashMap<>();
    private final byte[] key;
    private final int version;

    public ItemFactory(byte[] key, int version) {
        this.key = key;
        this.version = version;
    }

    public void dumpBinaryItems(String wzFolder, byte[] key) {
        try {
            BinaryWriter writer = new BinaryWriter(wzFolder + "Item.bin");
            WzMappedInputStream in = new WzMappedInputStream(Paths.get("wz", "Item.wz"));
            WzFile itemWZ = new WzFile("Item.wz", (short) version);
            in.setKey(key);
            in.setHash(version);
            itemWZ.parse(in);

            //Dump data in following order
            dumpItems(itemWZ, writer);
            //dumpPets(equipWZ, writer);

            try {
                writer.WriteFile();
            } catch (Exception ex) {
                System.out.println("Couldn't finalize bin file.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void dumpBasicItemInfo(WzObject<?, ?> item, BinaryWriter writer) {
        writer.WriteBool(WzDataTool.getBoolean(item, "cash", false));
        writer.WriteBool(WzDataTool.getBoolean(item, "notConsume", false));
        writer.WriteBool(WzDataTool.getBoolean(item, "noMoveToLocker", false));
        writer.WriteBool(WzDataTool.getBoolean(item, "accountSharable", false));
        writer.WriteBool(WzDataTool.getBoolean(item, "notSale", false));
        writer.WriteBool(WzDataTool.getBoolean(item, "only", false));
        writer.WriteBool(WzDataTool.getBoolean(item, "timeLimited", false));

        writer.WriteInt(WzDataTool.getInteger(item, "dressUpgrade", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "skillEffectID", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "slotMax", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "levelVariation", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "sharedStatCostGrade", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "karma", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "rate", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "addTime", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "maxDays", -1));
        writer.WriteInt(WzDataTool.getInteger(item, "minusLevel", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "reqLevel", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "recoveryHP", 0));
        writer.WriteInt(WzDataTool.getInteger(item, "recoveryMP", 0));

        WzObject<?, ?> shareBlockedJobsList = item.getChild("cantAccountSharable");
        if (shareBlockedJobsList != null) {
            shareBlockedJobsList = shareBlockedJobsList.getChild("job");
        }
        WzObject<?, ?> gainExpLevelLimits = item.getChild("exp");
        WzObject<?, ?> levelupWarning = item.getChild("LvUpWarning");

        if (shareBlockedJobsList != null) {
            writer.WriteBool(true);
            writer.WriteInt(shareBlockedJobsList.getChildren().size());
            for (WzObject<?, ?> job : shareBlockedJobsList) {
                writer.WriteInt(Integer.parseInt(job.getName().replace(".img", "")));
            }
        } else {
            writer.WriteBool(false);
        }

        if (gainExpLevelLimits != null) {
            writer.WriteBool(true);
            writer.WriteInt(WzDataTool.getInteger(item, "minLev", 0));
            writer.WriteInt(WzDataTool.getInteger(item, "maxLev", 0));
        } else {
            writer.WriteBool(false);
        }

        if (levelupWarning != null) {
            writer.WriteBool(true);
            writer.WriteInt(levelupWarning.getChildren().size());
            for (WzObject<?, ?> warning : levelupWarning) {
                writer.WriteInt(Integer.parseInt(warning.getName().replace(".img", "")));
                writer.WriteString(WzDataTool.getString(levelupWarning, warning.getName(), ""));
            }
        } else {
            writer.WriteBool(false);
        }
    }

    private void dumpItems(WzFile equipWZ, BinaryWriter writer) {
        WzObject<?, ?> cash = equipWZ.getChild("Cash");
        WzObject<?, ?> consume = equipWZ.getChild("Consume");
        WzObject<?, ?> etc = equipWZ.getChild("Etc");
        WzObject<?, ?> install = equipWZ.getChild("Install");

        for (WzObject<?, ?> items : cash.getChildren().values()) {
            writer.WriteLong(items.getChildren().size());
            for (WzObject<?, ?> item : items.getChildren().values()) {
                int itemID = Integer.parseInt(item.getName().replace(".img", ""));
                writer.WriteInt(itemID);//itemID
                dumpBasicItemInfo(item.getChild("info"), writer);
            }
        }

        for (WzObject<?, ?> items : consume.getChildren().values()) {
            writer.WriteLong(items.getChildren().size());
            for (WzObject<?, ?> item : items.getChildren().values()) {
                int itemID = Integer.parseInt(item.getName().replace(".img", ""));
                writer.WriteInt(itemID);//itemID
                dumpBasicItemInfo(item.getChild("info"), writer);
            }
        }

        for (WzObject<?, ?> items : etc.getChildren().values()) {
            writer.WriteLong(items.getChildren().size());
            for (WzObject<?, ?> item : items.getChildren().values()) {
                int itemID = Integer.parseInt(item.getName().replace(".img", ""));
                writer.WriteInt(itemID);//itemID
                dumpBasicItemInfo(item.getChild("info"), writer);
            }
        }

        for (WzObject<?, ?> items : install.getChildren().values()) {
            writer.WriteLong(items.getChildren().size());
            for (WzObject<?, ?> item : items.getChildren().values()) {
                int itemID = Integer.parseInt(item.getName().replace(".img", ""));
                writer.WriteInt(itemID);//itemID
                dumpBasicItemInfo(item.getChild("info"), writer);
            }
        }

    }

    public void loadBinaryItems(String wzFolder) {
        BinaryReader input = new BinaryReader(wzFolder + "Item.bin");
        parseItems(input);
        //parsePets(input);
    }

    private void parseItems(BinaryReader input) {
        for (int j = 0; j < 4; j++) {

            long entries;
            entries = input.ReadLong();

            for (long i = 0; i < entries; i++) {

                int itemID = input.ReadInt();
                BundleItem item = new BundleItem(itemID);
                switch (j) {
                    case 0:
                        item.type = InventoryType.Cash;
                        break;
                    case 1:
                        item.type = InventoryType.Consume;
                        break;
                    case 2:
                        item.type = InventoryType.Etc;
                        break;
                    case 3:
                        item.type = InventoryType.Install;
                        break;
                }

                item.isCash = input.ReadBool();
                item.notConsume = input.ReadBool();
                item.noMoveToLocker = input.ReadBool();
                item.accountSharable = input.ReadBool();
                item.notSale = input.ReadBool();
                item.only = input.ReadBool();
                item.timeLimited = input.ReadBool();

                item.dressUpgrade = input.ReadInt();
                item.skillEffectID = input.ReadInt();
                item.nSlotMax = (short) input.ReadInt();
                item.levelVariation = input.ReadInt();
                item.sharedStatCostGrade = input.ReadInt();
                item.karma = input.ReadInt();
                item.nRate = (short) input.ReadInt();
                item.addTime = input.ReadInt();
                item.maxDays = input.ReadInt();
                item.minusLevel = input.ReadInt();
                item.reqLevel = input.ReadInt();
                item.recoveryHP = input.ReadInt();
                item.recoveryMP = input.ReadInt();

                if (input.ReadBool()) {
                    int numBlocks = input.ReadInt();
                    for (int k = 0; k < numBlocks; k++) {
                        item.shareBlockedJobs.add(input.ReadInt());
                    }
                }

                if (input.ReadBool()) {
                    item.expGainMinLev = input.ReadInt();
                    item.expGainMaxLev = input.ReadInt();
                }

                if (input.ReadBool()) {
                    int numBlocks = input.ReadInt();
                    for (int k = 0; k < numBlocks; k++) {
                        item.useExpWarningAtLevel.put(input.ReadInt(), input.ReadString());
                    }
                }

                this.mItemData.put(itemID, item);
            }
        }
    }

    private void dumpPets(WzFile equipWZ, BinaryWriter writer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void parsePets(BinaryReader input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
