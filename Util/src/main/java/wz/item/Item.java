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

import java.util.HashMap;

import bin.WzBinaryReader;

/**
 *
 * @author Kaz Voeten
 */
public class Item {

    public static final HashMap<Integer, ItemBase> mItemData = new HashMap<>();

    public Item() {}
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

    private void parsePets(WzBinaryReader pReader) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
