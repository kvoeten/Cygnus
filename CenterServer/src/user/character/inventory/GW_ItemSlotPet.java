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
package user.character.inventory;

import net.OutPacket;

/**
 *
 * @author Kaz Voeten
 */
public class GW_ItemSlotPet extends GW_ItemSlotBase {

    public String sPetName = "";
    public byte nLevel = 1;
    public short nTameness = 1;
    public byte nRepleteness = 0;
    public long dateDead = 0;
    public short nPetAttribute = 0;
    public short usPetSkill = 0;
    public int nRemainLife = 0;
    public int nAttribute = 0;
    public byte nActiveStat = 0;
    public int nAutoBuffSkill = 0;
    public int nPetHue = 0;
    public short nGiantRate = 0;

    public GW_ItemSlotPet(int nItemID) {
        super(nItemID, 3);
    }

    @Override
    public void RawEncode(OutPacket oPacket) {
        super.RawEncode(oPacket);
        oPacket.EncodeString(sPetName, 13);
        oPacket.EncodeByte(nLevel);
        oPacket.EncodeShort(nTameness);
        oPacket.EncodeByte(nRepleteness);
        oPacket.EncodeLong(dateDead);
        oPacket.EncodeShort(nPetAttribute);
        oPacket.EncodeShort(usPetSkill);
        oPacket.EncodeInt(nRemainLife);
        oPacket.EncodeInt(nAttribute);
        oPacket.EncodeByte(nActiveStat);
        oPacket.EncodeInt(nAutoBuffSkill);
        oPacket.EncodeInt(nPetHue);
        oPacket.EncodeShort(nGiantRate);
    }

}
