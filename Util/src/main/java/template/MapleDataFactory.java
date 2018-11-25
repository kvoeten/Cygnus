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
package template;

import java.io.File;

import template.etc.ETCFactory;
import wz.common.WzTool;
import wz.common.WzVersion;
import template.item.CharacterFactory;
import template.item.ItemFactory;

/**
 *
 * @author Kaz Voeten
 */
public class MapleDataFactory {

    private static final byte[] aKey = WzTool.generateKey(WzVersion.BMS);
    private final int nVersion = 188;
    
    public final ETCFactory pETCFactory;
    private final CharacterFactory pCharacterFactory;
    public final ItemFactory pItemFactory;
    
    public MapleDataFactory() {
        pETCFactory = new ETCFactory(aKey, nVersion);
        pCharacterFactory = new CharacterFactory(aKey, nVersion);
        pItemFactory = new ItemFactory(aKey, nVersion);
    }

    public void Initialize() {
        try {
            final File fEtc = new File(this.getClass().getClassLoader().getResource("Etc.bin").getFile());
            final File fCharacter = new File(this.getClass().getClassLoader().getResource("Character.bin").getFile());
            final File fItem = new File(this.getClass().getClassLoader().getResource("Item.bin").getFile());
            Load(fEtc.getCanonicalPath(), fCharacter.getCanonicalPath(), fItem.getCanonicalPath());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("[Info] Binary wz data not found, attempting to dump from WZ.");
            try {
                final File fEtc = new File(this.getClass().getClassLoader().getResource("Etc.wz").getFile());
                final File fCharacter = new File(this.getClass().getClassLoader().getResource("Character.wz").getFile());
                final File fItem = new File(this.getClass().getClassLoader().getResource("Item.wz").getFile());
                Dump(fEtc.getParent(), fCharacter.getParent(), fItem.getParent());
            } catch (Exception e) {
                System.out.println("[Info] Dump Failed, WZ files weren't found!!");
                System.exit(1);
            }
        }
    }

    private void Load(String sETC, String sCharacter, String sItem) {
        Long time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Etc.bin data.");
        pETCFactory.LoadBinary(sETC);
        System.out.println("[Info] Parsed Etc.bin data in " + (System.currentTimeMillis() - time) + "ms.");

        time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Character.bin data.");
        pCharacterFactory.loadBinaryEquips(sCharacter);
        int nSize = ItemFactory.mItemData.size();
        System.out.println("[Info] Parsed " + nSize + " Character.bin data entries in " + (System.currentTimeMillis() - time) + "ms.");

        time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Item.bin data.");
        pItemFactory.loadBinaryItems(sItem);
        System.out.println("[Info] Parsed " + (ItemFactory.mItemData.size() - nSize) + " Item.bin data entries in " + (System.currentTimeMillis() - time) + "ms.");
    }

    private void Dump(String sETC, String sCharacter, String sItem) {
        Long time = System.currentTimeMillis();
        System.out.println("[Info] Dumping binary Etc.wz data: " + sETC);
        pETCFactory.DumpBinary(sETC, aKey);
        System.out.println("[Info] Dumped Etc.wz data in " + (System.currentTimeMillis() - time) + "ms.");

        time = System.currentTimeMillis();
        System.out.println("[Info] Dumping binary Character.wz data.");
        pCharacterFactory.dumpBinaryEquips(sCharacter, aKey);
        System.out.println("[Info] Dumped Character.wz data in " + (System.currentTimeMillis() - time) + "ms.");

        time = System.currentTimeMillis();
        System.out.println("[Info] Dumping binary Item.wz data.");
        pItemFactory.dumpBinaryItems(sItem, aKey);
        System.out.println("[Info] Dumped Item.wz data in " + (System.currentTimeMillis() - time) + "ms.");
    }
}
