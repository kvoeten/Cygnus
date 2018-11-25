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

/**
 *
 * @author Kaz Voeten
 */
public class EquipItem extends Item {

    public GW_ItemSlotEquipBase eqStats = new GW_ItemSlotEquipBase();
    //WZDATA for checks
    public int reqSTR = 0;
    public int reqDEX = 0;
    public int reqINT = 0;
    public int reqLUK = 0;
    public int reqJob = 0;
    public int reqLevel = 0;
    public int setItemID = 0;
    public int bitsSlot = 0;
    public boolean equipTradeBlock = false;
    public boolean epicItem = false;
    public boolean canLevel = false;
    public boolean royalSpecial = false;
    public boolean android = false;

    public String islot;
    public String vslot;
    public String afterImage;

    public EquipItem(int nItemID) {
        super(nItemID);
    }
}
