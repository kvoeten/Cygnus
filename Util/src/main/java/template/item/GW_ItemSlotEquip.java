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

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.packet.OutPacket;

/**
 * @author Kaz Voeten
 */
public class GW_ItemSlotEquip extends GW_ItemSlotBase {

    public GW_ItemSlotEquipBase eqBase = new GW_ItemSlotEquipBase();
    public int nPrevBonusExpRate = -1;
    public String sTitle = "";
    public boolean vSlot = false;
    public int nSlot = -1;

    //filetime
    public int ftEquippedLow = 0;
    public int ftEquippedHigh = 0;

    //uid for tracking
    public long liSN = 0;

    public int nGrade = 0; //potential grade
    public int nCHUC = 0; //enhancement grade
    public short nOption1 = 0; //pot line 1
    public short nOption2 = 0; //pot line 2
    public short nOption3 = 0; //pot line 3
    public short nOption4 = 0; //pot bonues line 1
    public short nOption5 = 0; //fusion anvil
    public short nOption6 = 0; //pot bonues line 2
    public short nOption7 = 0; //pot bonues line 3
    public short nSocketState = 0;
    public short nSocket1 = 0;
    public short nSocket2 = 0;
    public short nSocket3 = 0;
    public short nSoulOptionID = 0;
    public short nSoulSocketID = 0;
    public short nSoulOption = 0;

    private GW_ItemSlotEquip(int nItemID) {
        super(nItemID, 1);
        EquipItem pBase = (EquipItem) ItemFactory.mItemData.get(nItemID);
        if (pBase == null || pBase.type != InventoryType.Equip) {
            return;
        }
        pBase.eqStats.mStats.forEach((nFlag, nValue) -> this.eqBase.mStats.put(nFlag, nValue));
    }

    public static GW_ItemSlotEquip Create(int dwCharacterID, int nItemID) {
        GW_ItemSlotEquip pRet = new GW_ItemSlotEquip(nItemID);
        try (Connection con = Database.GetConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO gw_itemslotequip (dwCharacterID, nItemID)  Values (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dwCharacterID);
            ps.setInt(2, nItemID);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Equip failed, no rows affected.");
            }

            ResultSet rs = ps.getGeneratedKeys();

            while (rs.next()) {
                pRet.liSN = rs.getLong("liSN");
            }

            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pRet;
    }

    public void Save(int dwCharacterID) {
        try (Connection con = Database.GetConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE gw_itemslotequip SET dwCharacterID = ?, nItemID = ?, nPos = ?,"
                     + "dateExpireLow = ?, dateExpireHigh = ?, nBagIndex = ?, nPrevBonusExpRate = ?, sTitle = ?, vSlot = ?, nSlot = ?,"
                     + "ftEquippedLow = ?, ftEquippedHigh = ?, nGrade = ?, nCHUC = ?, nOption1 = ?, nOption2 = ?, nOption3 = ?,"
                     + "nOption4 = ?, nOption5 = ?, nOption6 = ?, nOption7 = ?, nSocketState = ?, nSocket1 = ?, nSocket2 = ?,"
                     + "nSocket3 = ?, nSoulOptionID = ?, nSoulSocketID = ?, nSoulOption = ?, nRUC = ?, nCUC = ?, niSTR = ?, niDEX = ?,"
                     + "niINT = ?, niLUK = ?, niMaxHP = ?, niMaxMP = ?, niPAD = ?, niMAD = ?, niPDD = ?,niMDD = ?, niACC = ?, niEVA = ?,"
                     + "niCraft = ?, niSpeed = ?, niJump = ?, nAttribute = ?, nLevelUpType = ?, nLevel = ?, nEXP64 = ?, nDurability = ?,"
                     + "nIUC = ?, niPVPDamage = ?, iReduceReq = ?, nSpecialAttribute = ?, nDurabilityMax = ?, niIncReq = ?, nGrowthEnchant = ?,"
                     + "nPsEnchant = ?, nBDR = ?, niMDR = ?, nDamR = ?, nStatR = ?, nCuttable = ?, nExGradeOption = ?, nItemState = ? "
                     + "WHERE liSN = ?")) {

            int nEditedRows = Database.Excecute(con, ps, dwCharacterID, nItemID, nPos, dateExpireLow, dateExpireHigh, nBagIndex, nPrevBonusExpRate, sTitle,
                    vSlot, nSlot, ftEquippedLow, ftEquippedHigh, nGrade, nCHUC, nOption1, nOption2, nOption3, nOption4, nOption6, nOption6,
                    nOption7, nSocketState, nSocket1, nSocket2, nSocket3, nSoulOptionID, nSoulSocketID, nSoulOption,
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nRUC, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nCUC, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niSTR, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niDEX, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niINT, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niLUK, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niMaxHP, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niMaxMP, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niPAD, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niMAD, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niPDD, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niMDD, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niACC, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niEVA, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niCraft, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niSpeed, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niJump, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nAttribute, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nLevelUpType, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nLevel, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nEXP64, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nDurability, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nIUC, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niPVPDamage, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.iReduceReq, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nSpecialAttribute, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nDurabilityMax, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niIncReq, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nGrowthEnchant, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nPsEnchant, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nBDR, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.niMDR, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nDamR, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nStatR, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nCuttable, 0), eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nExGradeOption, 0),
                    eqBase.mStats.getOrDefault(GW_ItemSlotEquipBase.Flags.nItemState, 0), liSN
            );

            if (nEditedRows == 0) {
                throw new SQLException("Saving Equip failed, no rows affected.");
            }

            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void RawEncode(OutPacket oPacket) {
        super.RawEncode(oPacket);
        eqBase.Encode(oPacket);

        oPacket.EncodeString(sTitle);

        oPacket.EncodeByte(nGrade);
        oPacket.EncodeByte(nCHUC);
        oPacket.EncodeShort(nOption1);
        oPacket.EncodeShort(nOption2);
        oPacket.EncodeShort(nOption3);
        oPacket.EncodeShort(nOption4);
        oPacket.EncodeShort(nOption6);
        oPacket.EncodeShort(nOption7);
        oPacket.EncodeShort(nOption5);

        oPacket.EncodeShort(nSocketState);
        oPacket.EncodeShort(nSocket1);
        oPacket.EncodeShort(nSocket2);
        oPacket.EncodeShort(nSocket3);

        if (cashOpt != null) {
            oPacket.EncodeInt(cashOpt.liCashItemSNLow);
            oPacket.EncodeInt(cashOpt.liCashItemSNHigh);
        }

        oPacket.EncodeInt(ftEquippedLow);
        oPacket.EncodeInt(ftEquippedHigh);

        oPacket.EncodeInt(nPrevBonusExpRate);

        cashOpt.Encode(oPacket);

        oPacket.EncodeShort(nSoulOptionID);
        oPacket.EncodeShort(nSoulSocketID);
        oPacket.EncodeShort(nSoulOption);
    }
}
