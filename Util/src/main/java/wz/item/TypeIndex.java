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
 * @author Kaz Voeten
 */
public enum TypeIndex {

    Install(0),
    Consume(1),
    Etc(2),
    Equip(3),
    Cash(4);
    private final int type;

    private TypeIndex(int type) {
        this.type = type;
    }

    public int GetType() {
        return this.type;
    }

    public static void GetItemTypeFromTypeIndex(int ti) {
        int result;
        switch (ti) {
            case 1:
                result = 4;
                break;
            case 2:
                result = 8;
                break;
            case 3:
                result = 16;
                break;
            case 4:
                result = 32;
                break;
            case 5:
                result = 64;
                break;
            default:
                result = 0;
                break;
        }
    }

}
