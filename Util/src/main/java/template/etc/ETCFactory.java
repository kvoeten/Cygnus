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
package template.etc;

import bin.WzBinaryReader;
import bin.WzBinaryWriter;
import java.nio.file.Paths;
import wz.WzFile;
import wz.WzObject;
import wz.common.WzDataTool;
import wz.io.WzMappedInputStream;

/**
 *
 * @author Novak
 */
public class ETCFactory {
    
    private ForbiddenName ForbiddenNames;
    private Curse CurseWords;
    private final byte[] key;
    private final int version;

    public ETCFactory(byte[] key, int version) {
        this.key = key;
        this.version = version;
    }

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

    public void DumpBinary(String sFolder, byte[] aKey) {
        try {
            WzBinaryWriter pOutBuff = new WzBinaryWriter(sFolder + "/Etc.bin");
            WzMappedInputStream pStream = new WzMappedInputStream(Paths.get(sFolder, "/Etc.wz"));
            WzFile pFile = new WzFile("Etc.wz", (short) version);
            pStream.setKey(aKey);
            pStream.setHash(version);
            pFile.parse(pStream);

            //Dump data in following order
            DumpNames(pFile, pOutBuff);
            DumpCurses(pFile, pOutBuff);

            pOutBuff.Write();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void DumpNames(WzFile pFile, WzBinaryWriter pOutBuff) {
        WzObject<?, ?> forbiddenNames = pFile.getChild("ForbiddenName.img");

        pOutBuff.EncodeShort((int) forbiddenNames.getChildren().size());
        for (WzObject<?, ?> name : forbiddenNames) {
            pOutBuff.EncodeString(WzDataTool.getString(forbiddenNames, name.getName(), "admin"));
        }
    }

    private void DumpCurses(WzFile pFile, WzBinaryWriter pOutBuff) {
        WzObject<?, ?> curses = pFile.getChild("Curse.img").getChild("BlackList");

        pOutBuff.EncodeShort((int) curses.getChildren().size());
        for (WzObject<?, ?> name : curses) {
            pOutBuff.EncodeString(WzDataTool.getString(curses, name.getName(), "fuck").toLowerCase().replace("[s]", ""));
        }
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
