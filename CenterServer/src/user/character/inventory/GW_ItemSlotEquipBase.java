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
package user.character.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import net.OutPacket;

/**
 *
 * @author Kaz Voeten
 */
public class GW_ItemSlotEquipBase {

    public HashMap<Flags, Integer> mStats;

    public GW_ItemSlotEquipBase() {
        this.mStats = new HashMap<>();
    }

    public void Encode(OutPacket oPacket) { //GW_ItemSlotEquipBaseEncode
        int[] aSendBuff = new int[]{0, 0}; //2 ints these days
        ArrayList<Pair<Integer, Integer>> liItemStats = new ArrayList<>();

        if (mStats.containsKey(Flags.nRUC)) {
            aSendBuff[0] |= Flags.nRUC.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nRUC), 1));
        }

        if (mStats.containsKey(Flags.nCUC)) {
            aSendBuff[0] |= Flags.nCUC.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nCUC), 1));
        }

        if (mStats.containsKey(Flags.niSTR)) {
            aSendBuff[0] |= Flags.niSTR.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niSTR), 2));
        }

        if (mStats.containsKey(Flags.niDEX)) {
            aSendBuff[0] |= Flags.niDEX.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niDEX), 2));
        }

        if (mStats.containsKey(Flags.niINT)) {
            aSendBuff[0] |= Flags.niINT.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niINT), 2));
        }

        if (mStats.containsKey(Flags.niLUK)) {
            aSendBuff[0] |= Flags.niLUK.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niLUK), 2));
        }

        if (mStats.containsKey(Flags.niMaxHP)) {
            aSendBuff[0] |= Flags.niMaxHP.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niMaxHP), 2));
        }

        if (mStats.containsKey(Flags.niMaxMP)) {
            aSendBuff[0] |= Flags.niMaxMP.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niMaxMP), 2));
        }

        if (mStats.containsKey(Flags.niPAD)) {
            aSendBuff[0] |= Flags.niPAD.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niPAD), 2));
        }

        if (mStats.containsKey(Flags.niMAD)) {
            aSendBuff[0] |= Flags.niMAD.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niMAD), 2));
        }

        if (mStats.containsKey(Flags.niPDD)) {
            aSendBuff[0] |= Flags.niPDD.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niPDD), 2));
        }

        if (mStats.containsKey(Flags.niMDD)) {
            aSendBuff[0] |= Flags.niMDD.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niMDD), 2));
        }

        if (mStats.containsKey(Flags.niACC)) {
            aSendBuff[0] |= Flags.niACC.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niACC), 2));
        }

        if (mStats.containsKey(Flags.niEVA)) {
            aSendBuff[0] |= Flags.niEVA.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niEVA), 2));
        }

        if (mStats.containsKey(Flags.niCraft)) {
            aSendBuff[0] |= Flags.niCraft.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niCraft), 2));
        }

        if (mStats.containsKey(Flags.niSpeed)) {
            aSendBuff[0] |= Flags.niSpeed.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niSpeed), 2));
        }

        if (mStats.containsKey(Flags.niJump)) {
            aSendBuff[0] |= Flags.niJump.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niJump), 2));
        }

        if (mStats.containsKey(Flags.nAttribute)) {
            aSendBuff[0] |= Flags.nAttribute.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nAttribute), 2));
        }

        if (mStats.containsKey(Flags.nLevelUpType)) {
            aSendBuff[0] |= Flags.nLevelUpType.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nLevelUpType), 1));
        }

        if (mStats.containsKey(Flags.nLevel)) {
            aSendBuff[0] |= Flags.nLevel.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nLevel), 1));
        }

        if (mStats.containsKey(Flags.nEXP64)) {
            aSendBuff[0] |= Flags.nEXP64.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nEXP64), 8));
        }

        if (mStats.containsKey(Flags.nDurability)) {
            aSendBuff[0] |= Flags.nDurability.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nDurability), 4));
        }

        if (mStats.containsKey(Flags.nIUC)) {
            aSendBuff[0] |= Flags.nIUC.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nIUC), 4));
        }

        if (mStats.containsKey(Flags.niPVPDamage)) {
            aSendBuff[0] |= Flags.niPVPDamage.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niPVPDamage), 2));
        }

        if (mStats.containsKey(Flags.niIncReq)) {
            aSendBuff[0] |= Flags.niIncReq.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niIncReq), 1));
        }

        if (mStats.containsKey(Flags.nGrowthEnchant)) {
            aSendBuff[0] |= Flags.nGrowthEnchant.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nGrowthEnchant), 1));
        }

        if (mStats.containsKey(Flags.nPsEnchant)) {
            aSendBuff[0] |= Flags.nPsEnchant.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nPsEnchant), 1));
        }

        if (mStats.containsKey(Flags.nBDR)) {
            aSendBuff[0] |= Flags.nBDR.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nBDR), 1));
        }

        if (mStats.containsKey(Flags.niMDR)) {
            aSendBuff[0] |= Flags.niMDR.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.niMDR), 1));
        }

        oPacket.EncodeInt(aSendBuff[0]);
        for (Pair<Integer, Integer> stat : liItemStats) {
            if (stat.getValue() == 1) {
                oPacket.EncodeByte(stat.getKey().byteValue());
            } else if (stat.getValue() == 2) {
                oPacket.EncodeShort(stat.getKey().shortValue());
            } else if (stat.getValue() == 4) {
                oPacket.EncodeInt(stat.getKey());
            } else if (stat.getValue() == 8) {
                oPacket.EncodeLong(stat.getKey());
            }
        }
        liItemStats.clear();

        if (mStats.containsKey(Flags.nDamR)) {
            aSendBuff[1] |= Flags.nDamR.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nDamR), 1));
        }

        if (mStats.containsKey(Flags.nStatR)) {
            aSendBuff[1] |= Flags.nStatR.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nStatR), 1));
        }

        if (mStats.containsKey(Flags.nCuttable)) {
            aSendBuff[1] |= Flags.nCuttable.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nCuttable), 1));
        }

        if (mStats.containsKey(Flags.nExGradeOption)) {
            aSendBuff[1] |= Flags.nExGradeOption.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nExGradeOption), 8));
        }

        if (mStats.containsKey(Flags.nItemState)) {
            aSendBuff[1] |= Flags.nItemState.getValue();
            liItemStats.add(new Pair<>(mStats.get(Flags.nItemState), 4));
        }

        oPacket.EncodeInt(aSendBuff[1]);
        for (Pair<Integer, Integer> stat : liItemStats) {
            if (stat.getValue() == 1) {
                oPacket.EncodeByte(stat.getKey().byteValue());
            } else if (stat.getValue() == 2) {
                oPacket.EncodeShort(stat.getKey().shortValue());
            } else if (stat.getValue() == 4) {
                oPacket.EncodeInt(stat.getKey());
            } else if (stat.getValue() == 8) {
                oPacket.EncodeLong(stat.getKey());
            }
        }
    }

    public static enum Flags {
        nRUC(0x01), //scroll slots?
        nCUC(0x02), //level ?
        niSTR(0x04),
        niDEX(0x08),
        niINT(0x10),
        niLUK(0x20),
        niMaxHP(0x40),
        niMaxMP(0x80),
        niPAD(0x100),
        niMAD(0x200),
        niPDD(0x400),
        niMDD(0x800),
        niACC(0x1000),
        niEVA(0x2000),
        niCraft(0x4000),
        niSpeed(0x8000),
        niJump(0x10000),
        nAttribute(0x20000),
        nLevelUpType(0x40000),
        nLevel(0x80000),
        nEXP64(0x100000),
        nDurability(0x200000),
        nIUC(0x400000),
        niPVPDamage(0x800000),
        iReduceReq(0x1000000),
        nSpecialAttribute(0x2000000),
        nDurabilityMax(0x4000000),
        niIncReq(0x8000000),
        nGrowthEnchant(0x10000000),
        nPsEnchant(0x20000000),
        nBDR(0x40000000),
        niMDR(0x80000000),
        //Move to second mask int
        nDamR(0x1),
        nStatR(0x2),
        nCuttable(0x4),
        nExGradeOption(0x8),
        nItemState(0x10);
        private final int flag;

        private Flags(int flag) {
            this.flag = flag;
        }

        public int getValue() {
            return this.flag;
        }

    }
}
