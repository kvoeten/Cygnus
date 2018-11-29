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

import java.nio.file.Paths;
import java.util.HashMap;

import binary.WzBinaryReader;
import binary.WzBinaryWriter;
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
            WzBinaryWriter pWriter = new WzBinaryWriter(wzFolder + "/Item.binary");
            WzMappedInputStream in = new WzMappedInputStream(Paths.get(wzFolder, "/Item.wz"));
            WzFile itemWZ = new WzFile("/Item.wz", (short) version);
            in.setKey(key);
            in.setHash(version);
            itemWZ.parse(in);

            //Dump data in following order
            dumpItems(itemWZ, pWriter);
            //dumpPets(equipWZ, writer);

            try {
                pWriter.Write();
            } catch (Exception ex) {
                System.out.println("Couldn't finalize binary file.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void dumpBasicItemInfo(WzObject<?, ?> item, WzBinaryWriter pWriter) {
        pWriter.EncodeBool(WzDataTool.getBoolean(item, "cash", false));
        pWriter.EncodeBool(WzDataTool.getBoolean(item, "notConsume", false));
        pWriter.EncodeBool(WzDataTool.getBoolean(item, "noMoveToLocker", false));
        pWriter.EncodeBool(WzDataTool.getBoolean(item, "accountSharable", false));
        pWriter.EncodeBool(WzDataTool.getBoolean(item, "notSale", false));
        pWriter.EncodeBool(WzDataTool.getBoolean(item, "only", false));
        pWriter.EncodeBool(WzDataTool.getBoolean(item, "timeLimited", false));

        pWriter.EncodeInt(WzDataTool.getInteger(item, "dressUpgrade", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "skillEffectID", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "slotMax", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "levelVariation", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "sharedStatCostGrade", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "karma", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "rate", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "addTime", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "maxDays", -1));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "minusLevel", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "reqLevel", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "recoveryHP", 0));
        pWriter.EncodeInt(WzDataTool.getInteger(item, "recoveryMP", 0));

        WzObject<?, ?> shareBlockedJobsList = item.getChild("cantAccountSharable");
        if (shareBlockedJobsList != null) {
            shareBlockedJobsList = shareBlockedJobsList.getChild("job");
        }
        WzObject<?, ?> gainExpLevelLimits = item.getChild("exp");
        WzObject<?, ?> levelupWarning = item.getChild("LvUpWarning");

        if (shareBlockedJobsList != null) {
            pWriter.EncodeBool(true);
            pWriter.EncodeInt(shareBlockedJobsList.getChildren().size());
            for (WzObject<?, ?> job : shareBlockedJobsList) {
                pWriter.EncodeInt(Integer.parseInt(job.getName().replace(".img", "")));
            }
        } else {
            pWriter.EncodeBool(false);
        }

        if (gainExpLevelLimits != null) {
            pWriter.EncodeBool(true);
            pWriter.EncodeInt(WzDataTool.getInteger(item, "minLev", 0));
            pWriter.EncodeInt(WzDataTool.getInteger(item, "maxLev", 0));
        } else {
            pWriter.EncodeBool(false);
        }

        if (levelupWarning != null) {
            pWriter.EncodeBool(true);
            pWriter.EncodeInt(levelupWarning.getChildren().size());
            for (WzObject<?, ?> warning : levelupWarning) {
                pWriter.EncodeInt(Integer.parseInt(warning.getName().replace(".img", "")));
                pWriter.EncodeString(WzDataTool.getString(levelupWarning, warning.getName(), ""));
            }
        } else {
            pWriter.EncodeBool(false);
        }
    }

    private void dumpItems(WzFile equipWZ, WzBinaryWriter pWriter) {
        WzObject<?, ?> cash = equipWZ.getChild("Cash");
        WzObject<?, ?> consume = equipWZ.getChild("Consume");
        WzObject<?, ?> etc = equipWZ.getChild("Etc");
        WzObject<?, ?> install = equipWZ.getChild("Install");

        for (WzObject<?, ?> items : cash.getChildren().values()) {
            pWriter.EncodeLong(items.getChildren().size());
            for (WzObject<?, ?> item : items.getChildren().values()) {
                int itemID = Integer.parseInt(item.getName().replace(".img", ""));
                pWriter.EncodeInt(itemID);//itemID
                dumpBasicItemInfo(item.getChild("info"), pWriter);
            }
        }

        for (WzObject<?, ?> items : consume.getChildren().values()) {
            pWriter.EncodeLong(items.getChildren().size());
            for (WzObject<?, ?> item : items.getChildren().values()) {
                int itemID = Integer.parseInt(item.getName().replace(".img", ""));
                pWriter.EncodeInt(itemID);//itemID
                dumpBasicItemInfo(item.getChild("info"), pWriter);
            }
        }

        for (WzObject<?, ?> items : etc.getChildren().values()) {
            pWriter.EncodeLong(items.getChildren().size());
            for (WzObject<?, ?> item : items.getChildren().values()) {
                int itemID = Integer.parseInt(item.getName().replace(".img", ""));
                pWriter.EncodeInt(itemID);//itemID
                dumpBasicItemInfo(item.getChild("info"), pWriter);
            }
        }

        for (WzObject<?, ?> items : install.getChildren().values()) {
            pWriter.EncodeLong(items.getChildren().size());
            for (WzObject<?, ?> item : items.getChildren().values()) {
                int itemID = Integer.parseInt(item.getName().replace(".img", ""));
                pWriter.EncodeInt(itemID);//itemID
                dumpBasicItemInfo(item.getChild("info"), pWriter);
            }
        }

    }

    public void loadBinaryItems(String wzFolder) {
        WzBinaryReader pReader = new WzBinaryReader(wzFolder);
        parseItems(pReader);
        //parsePets(input);
    }

    private void parseItems(WzBinaryReader pReader) {
        for (int j = 0; j < 4; j++) {

            long entries;
            entries = pReader.DecodeLong();

            for (long i = 0; i < entries; i++) {

                int itemID = pReader.DecodeInt();
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

                item.isCash = pReader.DecodeBool();
                item.notConsume = pReader.DecodeBool();
                item.noMoveToLocker = pReader.DecodeBool();
                item.accountSharable = pReader.DecodeBool();
                item.notSale = pReader.DecodeBool();
                item.only = pReader.DecodeBool();
                item.timeLimited = pReader.DecodeBool();

                item.dressUpgrade = pReader.DecodeInt();
                item.skillEffectID = pReader.DecodeInt();
                item.nSlotMax = (short) pReader.DecodeInt();
                item.levelVariation = pReader.DecodeInt();
                item.sharedStatCostGrade = pReader.DecodeInt();
                item.karma = pReader.DecodeInt();
                item.nRate = (short) pReader.DecodeInt();
                item.addTime = pReader.DecodeInt();
                item.maxDays = pReader.DecodeInt();
                item.minusLevel = pReader.DecodeInt();
                item.reqLevel = pReader.DecodeInt();
                item.recoveryHP = pReader.DecodeInt();
                item.recoveryMP = pReader.DecodeInt();

                if (pReader.DecodeBool()) {
                    int numBlocks = pReader.DecodeInt();
                    for (int k = 0; k < numBlocks; k++) {
                        item.shareBlockedJobs.add(pReader.DecodeInt());
                    }
                }

                if (pReader.DecodeBool()) {
                    item.expGainMinLev = pReader.DecodeInt();
                    item.expGainMaxLev = pReader.DecodeInt();
                }

                if (pReader.DecodeBool()) {
                    int numBlocks = pReader.DecodeInt();
                    for (int k = 0; k < numBlocks; k++) {
                        item.useExpWarningAtLevel.put(pReader.DecodeInt(), pReader.DecodeString());
                    }
                }

                this.mItemData.put(itemID, item);
            }
        }
    }

    private void dumpPets(WzFile equipWZ, WzBinaryWriter writer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void parsePets(WzBinaryReader pReader) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
