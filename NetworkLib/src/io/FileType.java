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
package io;

/**
 *
 * @author Kaz Voeten
 */
public enum FileType {
    Character(0),
    Effect(1),
    Etc(2),
    Item(3),
    Map(4),
    Mob(5),
    Morph(6),
    Npc(7),
    Quest(8),
    Reactor(9),
    Skill(10),
    Sound(11),
    String(12),
    TamingMob(13),
    UI(14);

    private final int nType;

    private FileType(int type) {
        this.nType = type;
    }

    public int GetValue() {
        return this.nType;
    }
}
