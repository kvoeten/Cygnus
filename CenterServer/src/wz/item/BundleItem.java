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

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Kaz Voeten
 */
public class BundleItem extends Item {

    //WZ_DATA
    public short nSlotMax = 1;
    public short nRate = 1; //presumably consumption rate?
    public boolean notConsume = false; //doesn't disappear when consumed
    public boolean noMoveToLocker = false;
    public int skillEffectID = 0; //in addition to the base skill number?
    public int dressUpgrade = 0; //new ab dress id?
    public int levelVariation = 0;
    public int sharedStatCostGrade = 0;
    public int useExpGainMinLevel;
    public int useExpGainMaxLevel;
    public boolean only = false; //lolwat
    public boolean timeLimited = false; //must be consumed before time
    public int minusLevel = 0;
    public int addTime = 0;
    public int maxDays = -1;
    public int reqLevel = 0;
    public int karma = 0;
    public int recoveryHP = 0;
    public int recoveryMP = 0;
    public int expGainMinLev = 0;
    public int expGainMaxLev = 255;
    public ArrayList<Integer> shareBlockedJobs = new ArrayList<>();
    public HashMap<Integer, String> useExpWarningAtLevel = new HashMap<>();

    public BundleItem(int nItemID) {
        super(nItemID);
    }
}
