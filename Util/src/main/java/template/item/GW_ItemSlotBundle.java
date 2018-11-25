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

import net.packet.OutPacket;

/**
 * @author Kaz Voeten
 */
public class GW_ItemSlotBundle extends GW_ItemSlotBase {

    public short nNumber = 1; //count
    public short nAttribute = 0;
    public String sTitle = "";

    public GW_ItemSlotBundle(int nItemID) {
        super(nItemID, 2);
    }

    @Override
    public void RawEncode(OutPacket oPacket) {
        super.RawEncode(oPacket);
        oPacket.EncodeShort(nNumber);
        oPacket.EncodeString(sTitle);
        oPacket.EncodeShort(nAttribute);
        int v6 = nItemID / 100000;
        if (v6 == 207 || v6 == 233) {
            oPacket.EncodeInt(cashOpt.liCashItemSNLow);
            oPacket.EncodeInt(cashOpt.liCashItemSNHigh);
        }
    }

}
