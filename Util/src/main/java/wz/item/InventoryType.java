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

/**
 *
 * @author Kaz Voeten
 */
public enum InventoryType {

    Install(0),
    Consume(1),
    Etc(2),
    Equip(3),
    Cash(4);
    private int type;

    private InventoryType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

}
