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
public class Item {

    //WZ props
    public int nItemID;
    public int nPrice = 1; //shop price (not in item packets, just in WZ/ shop packets)
    public int tradeAvailable = 0;
    public int charmEXP = 0;
    public boolean accountSharable = false;
    public boolean bossReward = false;
    public boolean notSale = false;
    public boolean expireOnLogout;
    public boolean isCash = false; //just for easier item sorting.
    public InventoryType type;

    public Item(int nItemID) {
        this.nItemID = nItemID;
    }
}
