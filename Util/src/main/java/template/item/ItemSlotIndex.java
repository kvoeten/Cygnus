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

import java.util.ArrayList;

/**
 * @author Kaz Voeten
 */
public class ItemSlotIndex {
    //TODO: Get in-game and identify the actual fucking values. (a lot is missing/wrong)
    //TODO: Virtual values (idk what they are yet so rip my life)

    public static final int BP_HAIR = 0; //Body Part
    public static final int BP_CAP = 1;
    public static final int BP_FACEACC = 2;
    public static final int BP_EYEACC = 3;
    public static final int BP_EARACC = 4;
    public static final int BP_CLOTHES = 5;
    public static final int BP_PANTS = 6;
    public static final int BP_SHOES = 7;
    public static final int BP_GLOVES = 8;
    public static final int BP_CAPE = 9;
    public static final int BP_SHIELD = 10;
    public static final int BP_WEAPON = 11;
    public static final int BP_RING1 = 12;
    public static final int BP_RING2 = 13;
    public static final int BP_PETWEAR = 14;
    public static final int BP_RING3 = 15;
    public static final int BP_RING4 = 16;
    public static final int BP_PENDANT = 17;
    public static final int BP_TAMINGMOB = 18;
    public static final int BP_SADDLE = 19;
    public static final int BP_MOBEQUIP = 20;
    public static final int BP_MEDAL = 21;
    public static final int BP_BELT = 22;
    public static final int BP_SHOULDER = 23;
    public static final int BP_PETWEAR2 = 24;
    public static final int BP_PETWEAR3 = 25;
    public static final int BP_CHARMACC = 26;
    public static final int BP_ANDROID = 27;
    public static final int BP_MACHINEHEART = 28;
    public static final int BP_BADGE = 29;
    public static final int BP_EMBLEM = 30;
    public static final int BP_EXT_0 = 31;
    public static final int BP_EXT_PENDANT1 = 31;
    public static final int BP_EXT_1 = 32;
    public static final int BP_EXT_2 = 33;
    public static final int BP_EXT_3 = 34;
    public static final int BP_EXT_4 = 35;
    public static final int BP_EXT_5 = 36;
    public static final int BP_EXT_6 = 37;
    public static final int BP_COUNT = 31;
    public static final int BP_EXT_END = 37;
    public static final int BP_EXT_COUNT = 7;
    public static final int BP_EXCOUNT = 32;
    public static final int BP_STICKER = 100;

    public static final int kIdxPetConsumeHPItem = 200;
    public static final int kIdxPetConsumeMPItem = 201;

    public static final int DP_BASE = 1000; //Dragon Part
    public static final int DP_CAP = 1000;
    public static final int DP_PENDANT = 1001;
    public static final int DP_WING = 1002;
    public static final int DP_SHOES = 1003;
    public static final int DP_END = 1004;
    public static final int DP_COUNT = 4;

    public static final int MP_BASE = 1100; //Mechanic Part
    public static final int MP_ENGINE = 1100;
    public static final int MP_ARM = 1101;
    public static final int MP_LEG = 1102;
    public static final int MP_FRAME = 1103;
    public static final int MP_TRANSISTER = 1104;
    public static final int MP_END = 1105;
    public static final int MP_COUNT = 5;

    public static final int AP_BASE = 1200; //Android Part
    public static final int AP_CAP = 1200;
    public static final int AP_CAPE = 1201;
    public static final int AP_FACEACC = 1202;
    public static final int AP_CLOTHES = 1203;
    public static final int AP_PANTS = 1204;
    public static final int AP_SHOES = 1205;
    public static final int AP_GLOVES = 1206;
    public static final int AP_END = 1207;
    public static final int AP_COUNT = 7;

    public static final int DU_BASE = 1300; //Dressup
    public static final int DU_CAP = 1300;
    public static final int DU_CAPE = 1301;
    public static final int DU_FACEACC = 1302;
    public static final int DU_CLOTHES = 1303;
    public static final int DU_GLOVES = 1304;
    public static final int DU_END = 1305;
    public static final int DU_COUNT = 5;

    public static final int BITS_BASE = 1400; //Bits
    public static final int BITS_END = 1425;
    public static final int BITS_COUNT = 25;

    public static final int ZERO_BASE = 1500; //Zero
    public static final int ZERO_EYEACC = 1500;
    public static final int ZERO_CAP = 1501;
    public static final int ZERO_FACEACC = 1502;
    public static final int ZERO_EARACC = 1503;
    public static final int ZERO_CAPE = 1504;
    public static final int ZERO_CLOTHES = 1505;
    public static final int ZERO_GLOVES = 1506;
    public static final int ZERO_WEAPON = 1507;
    public static final int ZERO_PANTS = 1508;
    public static final int ZERO_SHOES = 1509;
    public static final int ZERO_RING1 = 1510;
    public static final int ZERO_RING2 = 1511;
    public static final int ZERO_END = 1512;
    public static final int ZERO_COUNT = 12;

    public static final int AS_BASE = 1600; //Arcane Symbol
    public static final int AS_END = 1602;
    public static final int AS_COUNT = 2;

    public static final int TP_BASE = 5000; //Totem Part
    public static final int TP_END = 5003;
    public static final int TP_COUNT = 3;

    public static final int MBP_BASE = 5100; //Monster Battle Part
    public static final int MBP_CAP = 5100;
    public static final int MBP_CAPE = 5101;
    public static final int MBP_CLOTHES = 5102;
    public static final int MBP_GLOVES = 5103;
    public static final int MBP_SHOES = 5104;
    public static final int MBP_WEAPON = 5105;
    public static final int MBP_END = 5106;
    public static final int MBP_COUNT = 6;

    public static final int FP_BASE = 5200; //Fox Person (haku)
    public static final int FP_WEAPON = 5200;
    public static final int FP_END = 5201;
    public static final int FP_COUNT = 1;

    public static final int NBP_DRAGON = 0; //Non Body Part
    public static final int NBP_MECHANIC = 1;
    public static final int NBP_ANDROID = 2;
    public static final int NBP_DRESSUP = 3;
    public static final int NBP_BITS = 4;
    public static final int NBP_ZERO = 5;
    public static final int NBP_MBP = 6;
    public static final int NBP_NO = 7;
    public static final int NBP_COUNT = 64;
    public static final int SLOT_INDEX_NOT_DEFINE = 50000;

    public static boolean Is_NonBodyPart(int nPOS) {
        return (nPOS - DP_BASE) < DP_COUNT
                || (nPOS - MP_BASE) < MP_COUNT
                || (nPOS - AP_BASE) < AP_COUNT
                || (nPOS - DU_BASE) < DU_COUNT
                || (nPOS - BITS_BASE) < BITS_COUNT
                || (nPOS - ZERO_BASE) < ZERO_COUNT
                || (nPOS - MBP_BASE) < MBP_COUNT;
    }

    //TODO: Currently KMS! Doesn't fit all GMS possibilities!
    public static boolean IsCorrectBodyPart(int nItemID, int nBodyPart, int nGender, boolean bRealEquip) {
        int nGenderFromID = GetGenderFromID(nItemID);
        if (nItemID / 10000 == 119
                || nItemID / 10000 == 168
                || nGender == 2
                || nGenderFromID == 2
                || nGenderFromID == nGender) {
            switch (nItemID / 10000) {
                case 100:
                    if (nBodyPart == BP_CAP || nBodyPart == AP_CAP || nBodyPart == DU_CAP) {
                        return true;
                    }
                    return nBodyPart == ZERO_CAP;
                case 101:
                    if (nBodyPart == BP_FACEACC || nBodyPart == AP_FACEACC || nBodyPart == DU_FACEACC) {
                        return true;
                    }
                    return nBodyPart == ZERO_FACEACC;
                case 102:
                    if (nBodyPart == BP_EYEACC) {
                        return true;
                    }
                    return nBodyPart == ZERO_EYEACC;
                case 103:
                    if (nBodyPart == BP_EARACC) {
                        return true;
                    }
                    return nBodyPart == ZERO_EARACC;
                case 104:
                case 105:
                    if (nBodyPart == BP_CLOTHES || nBodyPart == AP_CLOTHES) {
                        return true;
                    }
                    return nBodyPart == ZERO_CLOTHES;
                case 106:
                    if (nBodyPart == BP_PANTS || nBodyPart == AP_PANTS) {
                        return true;
                    }
                    return nBodyPart == ZERO_PANTS;
                case 107:
                    if (nBodyPart == BP_SHOES || nBodyPart == AP_SHOES) {
                        return true;
                    }
                    return nBodyPart == ZERO_SHOES;
                case 108:
                    if (nBodyPart == BP_GLOVES || nBodyPart == AP_GLOVES || nBodyPart == DU_GLOVES) {
                        return true;
                    }
                    return nBodyPart == ZERO_GLOVES;
                case 109:
                case 134:
                case 135:
                    return nBodyPart == BP_SHIELD;
                case 156:
                    if (!bRealEquip) {
                        return nBodyPart == BP_SHIELD;
                    }
                    if (nBodyPart != BP_WEAPON) {
                        return nBodyPart == BP_SHIELD;
                    }
                    return true;
                case 110:
                    if (nBodyPart == BP_CAPE || nBodyPart == AP_CAPE || nBodyPart == DU_CAPE) {
                        return true;
                    }
                    return nBodyPart == ZERO_CAPE;
                case 111:
                    if (nBodyPart == BP_RING1 || nBodyPart == BP_RING2 || nBodyPart == BP_RING3 || nBodyPart == BP_RING4 || nBodyPart == ZERO_RING1) {
                        return true;
                    }
                    return nBodyPart == ZERO_RING2;
                case 112:
                    if (nBodyPart == BP_PENDANT) {
                        return true;
                    }
                    return nBodyPart == BP_EXT_PENDANT1;
                case 113:
                    return nBodyPart == BP_BELT;
                case 114:
                    return nBodyPart == BP_MEDAL;
                case 115:
                    return nBodyPart == BP_SHOULDER;
                case 116:
                    return nBodyPart == BP_CHARMACC;
                case 118:
                    return nBodyPart == BP_BADGE;
                case 119:
                    return nBodyPart == BP_EMBLEM;
                case 165:
                    return nBodyPart == MP_TRANSISTER;
                case 166:
                    return nBodyPart == BP_ANDROID;
                case 167:
                    if (nBodyPart == BP_MACHINEHEART) {
                        return true;
                    }
                    return nBodyPart == BP_EMBLEM;
                case 161:
                    return nBodyPart == MP_ENGINE;
                case 162:
                    return nBodyPart == MP_ARM;
                case 163:
                    return nBodyPart == MP_LEG;
                case 164:
                    return nBodyPart == MP_FRAME;
                case 168:
                    return (nBodyPart - BITS_BASE) < BITS_COUNT;
                case 184:
                    return nBodyPart == MBP_CAP;
                case 185:
                    return nBodyPart == MBP_CLOTHES;
                case 186:
                    return nBodyPart == MBP_GLOVES;
                case 187:
                    return nBodyPart == MBP_SHOES;
                case 188:
                    return nBodyPart == MBP_CAPE;
                case 189:
                    return nBodyPart == MBP_WEAPON;
                case 190:
                    return nBodyPart == BP_TAMINGMOB;
                case 191:
                    return nBodyPart == BP_SADDLE;
                case 192:
                    return nBodyPart == BP_MOBEQUIP;
                case 194:
                    return nBodyPart == DP_CAP;
                case 195:
                    return nBodyPart == DP_PENDANT;
                case 196:
                    return nBodyPart == DP_WING;
                case 197:
                    return nBodyPart == DP_SHOES;
                case 180:
                    if (nBodyPart == BP_PETWEAR || nBodyPart == BP_PETWEAR2) {
                        return true;
                    }
                    return nBodyPart == BP_PETWEAR3;
                default:
                    if (!GetWeaponType(nItemID) && nItemID / 100000 != 16 && nItemID / 100000 != 17) {
                        return false;
                    }
                    if (nBodyPart == BP_WEAPON) {
                        return true;
                    }
                    return nBodyPart == ZERO_WEAPON;
            }
        } else {
            return false;
        }
    }

    public static boolean GetWeaponType(int nItemID) {
        int result = 0;
        if (nItemID / 1000000 != 1) {
            return false;
        }
        result = nItemID / 10000 % 100;
        return result != 0;
    }

    public static int GetGenderFromID(int nItemID) {
        int result;
        if (nItemID / 1000000 != 1 && nItemID / 10000 != 254 || nItemID / 10000 == 119 || nItemID / 10000 == 168) {
            result = 2;
        } else {
            switch (nItemID / 1000 % 10) {
                case 0:
                    result = 0;
                    break;
                case 1:
                    result = 1;
                    break;
                default:
                    result = 2;
            }
        }
        return result;
    }

    public static int GetBodyPartFromItem(int nItemID, int nGender) {
        ArrayList<Integer> aBodyPart = GetBodyPartArrayFromItem(nItemID, nGender);
        if (aBodyPart.isEmpty()) {
            return SLOT_INDEX_NOT_DEFINE;
        }
        return aBodyPart.get(0);
    }

    public static ArrayList<Integer> GetBodyPartArrayFromItem(int nItemID, int nGender) {
        ArrayList<Integer> aBodyPart = new ArrayList<>();
        int nGenderFromID = GetGenderFromID(nItemID);
        if (nItemID / 10000 == 119
                || nItemID / 10000 == 168
                || nGender == 2
                || nGenderFromID == 2
                || nGenderFromID == nGender) {
            switch (nItemID / 10000) {
                case 100:
                    aBodyPart.add(BP_CAP);
                    aBodyPart.add(AP_CAP);
                    aBodyPart.add(DU_CAP);
                    aBodyPart.add(ZERO_CAP);
                    break;
                case 101:
                    aBodyPart.add(BP_FACEACC);
                    aBodyPart.add(AP_FACEACC);
                    aBodyPart.add(DU_FACEACC);
                    aBodyPart.add(ZERO_FACEACC);
                    break;
                case 102:
                    aBodyPart.add(BP_EYEACC);
                    aBodyPart.add(ZERO_EYEACC);
                    break;
                case 103:
                    aBodyPart.add(BP_EARACC);
                    aBodyPart.add(ZERO_EARACC);
                    break;
                case 104:
                case 105:
                    aBodyPart.add(BP_CLOTHES);
                    aBodyPart.add(AP_CLOTHES);
                    aBodyPart.add(ZERO_CLOTHES);
                    break;
                case 106:
                    aBodyPart.add(BP_PANTS);
                    aBodyPart.add(AP_PANTS);
                    aBodyPart.add(ZERO_PANTS);
                    break;
                case 107:
                    aBodyPart.add(BP_SHOES);
                    aBodyPart.add(AP_SHOES);
                    aBodyPart.add(ZERO_SHOES);
                    break;
                case 108:
                    aBodyPart.add(BP_GLOVES);
                    aBodyPart.add(AP_GLOVES);
                    aBodyPart.add(ZERO_GLOVES);
                    break;
                case 109:
                case 134:
                case 135:
                    //case 156:
                    aBodyPart.add(BP_SHIELD);
                    break;
                case 110:
                    aBodyPart.add(BP_CAPE);
                    aBodyPart.add(AP_CAPE);
                    aBodyPart.add(DU_CAPE);
                    aBodyPart.add(ZERO_CAPE);
                    break;
                case 111:
                    aBodyPart.add(BP_RING1);
                    aBodyPart.add(BP_RING2);
                    aBodyPart.add(BP_RING3);
                    aBodyPart.add(BP_RING4);
                    aBodyPart.add(ZERO_RING1);
                    aBodyPart.add(ZERO_RING2);
                    break;
                case 112:
                    aBodyPart.add(BP_PENDANT);
                    aBodyPart.add(BP_EXT_PENDANT1);
                    break;
                case 113:
                    aBodyPart.add(BP_BELT);
                    break;
                case 114:
                    aBodyPart.add(BP_MEDAL);
                    break;
                case 115:
                    aBodyPart.add(BP_SHOULDER);
                    break;
                case 116:
                    aBodyPart.add(BP_CHARMACC);
                    break;
                case 118:
                    aBodyPart.add(BP_BADGE);
                    break;
                case 165:
                    aBodyPart.add(MP_TRANSISTER);
                    break;
                case 161:
                    aBodyPart.add(MP_ENGINE);
                    break;
                case 162:
                    aBodyPart.add(MP_ARM);
                    break;
                case 163:
                    aBodyPart.add(MP_LEG);
                    break;
                case 164:
                    aBodyPart.add(MP_FRAME);
                    break;
                case 166:
                    aBodyPart.add(BP_ANDROID);
                    break;
                case 167:
                    aBodyPart.add(BP_MACHINEHEART);
                    break;
                case 119:
                    aBodyPart.add(BP_EMBLEM);
                    break;
                case 168:
                    int v10 = BITS_BASE;
                    do {
                        aBodyPart.add(v10++);
                    } while (v10 < BITS_COUNT);
                    break;
                case 190:
                    aBodyPart.add(BP_TAMINGMOB);
                    break;
                case 191:
                    aBodyPart.add(BP_SADDLE);
                    break;
                case 192:
                    aBodyPart.add(BP_MOBEQUIP);
                    break;
                case 194:
                    aBodyPart.add(DP_CAP);
                    break;
                case 195:
                    aBodyPart.add(DP_PENDANT);
                    break;
                case 196:
                    aBodyPart.add(DP_WING);
                    break;
                case 197:
                    aBodyPart.add(DP_SHOES);
                    break;
                case 184:
                    aBodyPart.add(MBP_CAP);
                    break;
                case 185:
                    aBodyPart.add(MBP_CLOTHES);
                    break;
                case 186:
                    aBodyPart.add(MBP_GLOVES);
                    break;
                case 187:
                    aBodyPart.add(MBP_SHOES);
                    break;
                case 188:
                    aBodyPart.add(MBP_CAPE);
                    break;
                case 189:
                    aBodyPart.add(MBP_WEAPON);
                    break;
                case 180:
                    aBodyPart.add(BP_PETWEAR);
                    aBodyPart.add(BP_PETWEAR2);
                    aBodyPart.add(BP_PETWEAR3);
                    break;
                default:
                    if (GetWeaponType(nItemID)
                            || nItemID / 100000 == 15 //Could be shield, but is also zero wep.
                            || nItemID / 100000 == 16
                            || nItemID / 100000 == 17) {
                        aBodyPart.add(BP_WEAPON);
                        aBodyPart.add(ZERO_WEAPON);
                    }
                    break;
            }
            //IDA has more, but honestly this should do it. They have dressup checks and stuff, but they way i handle that this is fine.
        }
        return aBodyPart;
    }
}
