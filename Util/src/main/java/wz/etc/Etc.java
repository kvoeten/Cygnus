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
package wz.etc;

import bin.WzBinaryReader;

/**
 *
 * @author Novak
 */
public class Etc {
    
    private ForbiddenName ForbiddenNames;
    private Curse CurseWords;

    public Etc() {}

    public ForbiddenName GetForbiddenNames() {
        return this.ForbiddenNames;
    }

    public Curse GetCurseWords() {
        return this.CurseWords;
    }

    public boolean IsLegalName(String id) {
        return !CurseWords.contains(id) && !ForbiddenNames.contains(id);
    }

    public void LoadBinary(String sFolder) {
        WzBinaryReader pReader = new WzBinaryReader(sFolder);
        //Parse data in following order
        ParseNames(pReader);
        ParseCurses(pReader);
    }

    public void ParseNames(WzBinaryReader pInBuff) {
        int size = pInBuff.DecodeShort();
        this.ForbiddenNames = new ForbiddenName(size);
        for (int i = 0; i < size; i++) {
            ForbiddenNames.add(pInBuff.DecodeString(), i);
        }
    }

    public void ParseCurses(WzBinaryReader pInBuff) {
        int size = pInBuff.DecodeShort();
        this.CurseWords = new Curse(size);
        for (int i = 0; i < size; i++) {
            CurseWords.add(pInBuff.DecodeString(), i);
        }
    }
}
