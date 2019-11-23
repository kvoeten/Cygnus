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

import wz.etc.Etc;
import wz.item.CharacterFactory;
import wz.item.Item;

/**
 *
 * @author Kaz Voeten
 */
public class MapleDataFactory {

    public final Etc pETCFactory;
    private final CharacterFactory pCharacterFactory;
    public final Item pItemFactory;
    
    public MapleDataFactory() {
        pETCFactory = new Etc();
        pCharacterFactory = new CharacterFactory();
        pItemFactory = new Item();
    }

    public void Initialize() {
        try {
            final File fEtc = new File(this.getClass().getClassLoader().getResource("Etc.binary").getFile());
            final File fCharacter = new File(this.getClass().getClassLoader().getResource("Character.binary").getFile());
            final File fItem = new File(this.getClass().getClassLoader().getResource("Item.binary").getFile());
            Load(fEtc.getCanonicalPath(), fCharacter.getCanonicalPath(), fItem.getCanonicalPath());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("[Info] Binary wz data not found, attempting to dump from WZ.");
        }
    }

    private void Load(String sETC, String sCharacter, String sItem) {
        Long time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Etc.binary data.");
        pETCFactory.LoadBinary(sETC);
        System.out.println("[Info] Parsed Etc.binary data in " + (System.currentTimeMillis() - time) + "ms.");

        time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Character.binary data.");
        pCharacterFactory.loadBinaryEquips(sCharacter);
        int nSize = Item.mItemData.size();
        System.out.println("[Info] Parsed " + nSize + " Character.binary data entries in " + (System.currentTimeMillis() - time) + "ms.");

        time = System.currentTimeMillis();
        System.out.println("[Info] Parsing binary Item.binary data.");
        pItemFactory.loadBinaryItems(sItem);
        System.out.println("[Info] Parsed " + (Item.mItemData.size() - nSize) + " Item.binary data entries in " + (System.currentTimeMillis() - time) + "ms.");
    }
}
