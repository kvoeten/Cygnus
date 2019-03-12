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
package wz.etc;

/**
 *
 * @author Novak
 */
public class ForbiddenName {

    private final String[] names;

    public ForbiddenName(int size) {
        this.names = new String[size];
    }

    public void add(String name, int index) {
        this.names[index] = name;
    }

    public boolean contains(String name) {
        for (String word : names) {
            if (word.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void print() {
        String words = "";
        for (String name : names) {
            words += name + ", ";
        }
        System.out.println(words);
    }
}
