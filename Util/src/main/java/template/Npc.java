/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.packet.InPacket;
import net.packet.OutPacket;

/**
 *
 * @author Kaz
 */
public class Npc {

    public int dwTemplateID;
    public String sName;
    public String sScript;
    public boolean bMove;
    public final List<Act> aAct;
    public final List<ScriptInfo> lScriptInfo;
    public final List<Reg> lReg;
    public int nTrunkCost_Get;
    public int nTrunkCost_Put;
    public boolean bStoreBank;
    public boolean bParcel;
    public boolean bGuildRank;
    public boolean bRPSGame;
    public int nWeddingWishList;

    public Npc() {
        this.sName = "NONAME";
        this.sScript = "";
        this.aAct = new ArrayList<>();
        this.lScriptInfo = new ArrayList<>();
        this.lReg = new ArrayList<>();
    }

    public Npc Decode(InPacket iPacket) {
        Npc pEntity = new Npc();
        pEntity.dwTemplateID = iPacket.DecodeInt();
        pEntity.sName = iPacket.DecodeString();
        pEntity.sScript = iPacket.DecodeString();
        pEntity.bMove = iPacket.DecodeBool();

        int nSize = iPacket.DecodeShort();
        for (int i = 0; i < nSize; ++i) {
            ScriptInfo pScriptInfo = new ScriptInfo();
            pScriptInfo.sScript = iPacket.DecodeString();
            pScriptInfo.tStartDate = iPacket.DecodeInt();
            pScriptInfo.tEndDate = iPacket.DecodeInt();
            lScriptInfo.add(i, pScriptInfo);
            
            Calendar pCalendar = Calendar.getInstance();
            int tDate = pCalendar.get(Calendar.DATE) + 100 
                    * (Calendar.MONTH + 100 * Calendar.YEAR);
            
            if (pScriptInfo.tStartDate <= tDate
                    && pScriptInfo.tEndDate >= tDate) {
                pEntity.sScript = pScriptInfo.sScript;
            }
        }

        nSize = iPacket.DecodeShort();
        for (int i = 0; i < nSize; ++i) {
            Reg pReg = new Reg();
            pReg.sName = iPacket.DecodeString();
            pReg.sVal = iPacket.DecodeString();
            pReg.nVal = iPacket.DecodeInt();
            lReg.add(i, pReg);
        }
        
        pEntity.nTrunkCost_Get = iPacket.DecodeInt();
        pEntity.nTrunkCost_Put = iPacket.DecodeInt();
        pEntity.bStoreBank = iPacket.DecodeBool();
        pEntity.bParcel = iPacket.DecodeBool();
        pEntity.bGuildRank = iPacket.DecodeBool();
        pEntity.bRPSGame = iPacket.DecodeBool();
        pEntity.nWeddingWishList = iPacket.DecodeInt();
        return pEntity;
    }

    //TODO
    public static class Act {
        public Act() {
        }
    }

    public class ScriptInfo {

        public String sScript;
        public int tStartDate;
        public int tEndDate;

        public ScriptInfo() {
            this.sScript = "";
        }

        public void Encode(OutPacket oPacket) {
            oPacket.EncodeString(sScript);
            oPacket.EncodeInt(tStartDate);
            oPacket.EncodeInt(tEndDate);
        }

    }

    public static class Reg {

        public String sName;
        public String sVal;
        public int nVal;

        public Reg() {
            this.sName = "";
            this.sVal = null;
            this.nVal = 0;
        }
    }
}
