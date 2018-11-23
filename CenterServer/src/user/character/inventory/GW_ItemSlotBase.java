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
public class GW_ItemSlotBase {

    int nItemID;

    public GW_CashItemOption cashOpt;
    public int dateExpireLow = 0;
    public int dateExpireHigh = 0;
    public int nBagIndex = -1; //pos in inventory.
    public int nPos = 0; //pos when equipped, if available
    public byte type;

    public GW_ItemSlotBase(int nItemID, int type) {
        this.nItemID = nItemID;
        this.type = (byte) type;
    }

    public void RawEncode(OutPacket oPacket) {
        oPacket.EncodeByte(type);
        oPacket.EncodeInt(nItemID);
        oPacket.EncodeBool(cashOpt != null);
        if (cashOpt != null) {
            oPacket.EncodeInt(cashOpt.liCashItemSNLow);
            oPacket.EncodeInt(cashOpt.liCashItemSNHigh);
        }
        oPacket.EncodeInt(dateExpireLow);
        oPacket.EncodeInt(dateExpireHigh);
        oPacket.EncodeInt(nBagIndex);
    }
}
