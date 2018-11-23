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
package wz;

import java.io.File;
import wz.etc.ETCFactory;
import wz.common.WzTool;
import wz.common.WzVersion;
import wz.item.CharacterFactory;
import wz.item.ItemFactory;
import wz.util.AES;

/**
 *
 * @author Kaz Voeten
 */
public class MapleDataFactory {
    
    private static final AES aes = new AES();
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
        final File fEtc = new File(System.getProperty("wz") + "Etc.bin");
        final File fCharacter = new File(System.getProperty("wz") + "Character.bin");
        final File fItem = new File(System.getProperty("wz") + "Item.bin");
        
        if (!fEtc.exists() || !fCharacter.exists() || !fItem.exists()) {
            System.out.println("[Info] Binary wz data not found. A new dump will be attempted.");
            final File fEtcWZ = new File(System.getProperty("wz") + "Etc.wz");
            final File fCharacterWZ = new File(System.getProperty("wz") + "Character.wz");
            final File fItemWZ = new File(System.getProperty("wz") + "Item.wz");
            if (!fEtcWZ.exists() || !fCharacterWZ.exists() || !fItemWZ.exists()) {
                System.out.println("[Info] WZ files not found. Ensure Character.wz/Item.wz/Etc.wz are present.");
                System.exit(0);
            } else {
                Dump();
                System.exit(0);
            }
        } else {
            Load();
        }
        
    }
    
    private void Load() {
        Long time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Etc.bin data.");
        pETCFactory.LoadBinary(System.getProperty("wz"));
        System.out.println("[Info] Parsed Etc.bin data in " + (System.currentTimeMillis() - time) + "ms.");
        
        time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Character.bin data.");
        pCharacterFactory.loadBinaryEquips(System.getProperty("wz"));
        int nSize = ItemFactory.mItemData.size();
        System.out.println("[Info] Parsed " + nSize + " Character.bin data entries in " + (System.currentTimeMillis() - time) + "ms.");
        
        time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Item.bin data.");
        pItemFactory.loadBinaryItems(System.getProperty("wz"));
        System.out.println("[Info] Parsed " + (ItemFactory.mItemData.size() - nSize) + " Item.bin data entries in " + (System.currentTimeMillis() - time) + "ms.");
    }
    
    private void Dump() {
        Long time = System.currentTimeMillis();
        System.out.println("[Info] Dumping binary Etc.wz data.");
        pETCFactory.DumpBinary(System.getProperty("wz"), aKey);
        System.out.println("[Info] Dumped Etc.wz data in " + (System.currentTimeMillis() - time) + "ms.");
        
        time = System.currentTimeMillis();
        System.out.println("[Info] Dumping binary Character.wz data.");
        pCharacterFactory.dumpBinaryEquips(System.getProperty("wz"), aKey);
        System.out.println("[Info] Dumped Character.wz data in " + (System.currentTimeMillis() - time) + "ms.");
        
        time = System.currentTimeMillis();
        System.out.println("[Info] Dumping binary Item.wz data.");
        pItemFactory.dumpBinaryItems(System.getProperty("wz"), aKey);
        System.out.println("[Info] Dumped Item.wz data in " + (System.currentTimeMillis() - time) + "ms.");
    }
}
