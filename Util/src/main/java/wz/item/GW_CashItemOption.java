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

import net.packet.OutPacket;

/**
 * @author Kaz Voeten
 */
public class GW_CashItemOption {

    public int liCashItemSNLow = 0;
    public int liCashItemSNHigh = 0;
    public int nGrade = 0;
    public int ftEquippedLow = 0;
    public int ftEquippedHigh = 0;
    public int[] aCashStats = new int[]{0, 0, 0};

    public GW_CashItemOption() {
    }

    public void Encode(OutPacket oPacket) {
        oPacket.EncodeInt(liCashItemSNLow);
        oPacket.EncodeInt(liCashItemSNHigh);

        oPacket.EncodeInt(ftEquippedLow);
        oPacket.EncodeInt(ftEquippedHigh);

        oPacket.EncodeInt(nGrade);
        for (int i = 0; i < 3; i++) {
            oPacket.EncodeInt(aCashStats[i]);
        }
    }
}
