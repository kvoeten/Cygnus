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

/**
 *
 * @author Kaz Voeten
 */
public class Curse {

    private final String[] curses;

    public Curse(int size) {
        this.curses = new String[size];
    }

    public void add(String curse, int index) {
        this.curses[index] = curse;
    }

    public boolean contains(String curse) {
        for (String curseWord : curses) {
            if (curseWord.equals(curse)) {
                return true;
            }
        }
        return false;
    }

    public void print() {
        String words = "";
        for (String curse : curses) {
            words += curse + ", ";
        }
        System.out.println(words);
    }
}
