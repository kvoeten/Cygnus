/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import common.AttackElem;
import java.util.ArrayList;
import java.util.List;
import net.packet.InPacket;

/**
 *
 * @author Kaz
 */
public class Mob {

    public int dwTemplateID;
    public int dwLinkedTemplateID;
    public int dwMonsterBookID;
    public String sName;
    public int nSpecies;
    public int nMoveAbility;
    public boolean bRegenAction;
    public boolean bBodyAttack;
    public boolean bBoss;
    public int nLevel;
    public long nMaxHP;
    public int nMaxMP;
    public int nSpeed;
    public int nFlySpeed;
    public int nChaseSpeed;
    public int nPAD;
    public int nPDD;
    public int nMAD;
    public int nMDD;
    public int nACC;
    public int nEVA;
    public long nEXP;
    public int nPushedDamage;
    public int nHPRecovery;
    public int nMPRecovery;
    public boolean bUndead;
    public float nFs;
    public boolean bSelfDestruction;
    public boolean bFirstSelfDestruction;
    public int nSelfDestructionHP;
    public boolean bPickUpDrop;
    public boolean bHasPublicDrop;
    public boolean bHasExplosiveDrop;
    public boolean bFirstAttack;
    public boolean bInvincible;
    public boolean bDisable;
    public boolean bNoFlip;
    public boolean bNotAttack;
    public int nGetCP;
    public int nFixedDamage;
    public int nWeaponID;
    public boolean bAngerGauge;
    public int nChargeCount;
    public boolean bHPgaugeHide;
    public int nHPTagColor;
    public int nHPTagBgColor;
    public boolean bChase;
    public boolean bDamagedByMob;
    public int nDropItemPeriod;
    public int nBanType;
    public String sBanMsg;
    public int nAttackCount;
    public int nSkillCount;
    public int nHitCount;
    public int nDieCount;
    public int bHideHP;
    public int bHideName;
    public int bHideLevel;
    public int nEscortType;
    public int nChatBalloon;
    public int nWidth;
    public int nCategory;
    public boolean bCannotEvade;
    public int nDeadBuff;
    public int tRemoveAfter;
    public boolean bRemoveQuest;
    public boolean bDoNotRemove;
    public boolean bOnlyNormalAttack;
    public boolean bCantPassByTeleport;
    public final List<Integer> aDamagedElemAttr;
    public final List<Integer> adwReviveTemplateID;
    public final List<Integer> aDamagedBySelectedMob;
    public final List<Integer> aDamagedBySelectedSkill;
    public final List<MobAttackInfo> aAttackInfo;
    public final List<MobSkillInfo> aSkillInfo;
    public final List<MobBanMap> aBanMap;
    public MobSelfDestruction selfDestructionInfo;

    public Mob() {
        this.sName = "NONAME";
        this.sBanMsg = "";
        this.aDamagedElemAttr = new ArrayList<>();
        this.adwReviveTemplateID = new ArrayList<>();
        this.aDamagedBySelectedMob = new ArrayList<>();
        this.aDamagedBySelectedSkill = new ArrayList<>();
        this.aAttackInfo = new ArrayList<>();
        this.aSkillInfo = new ArrayList<>();
        this.aBanMap = new ArrayList<>();
        this.selfDestructionInfo = new MobSelfDestruction();

        int nCount = AttackElem.Count;
        for (int i = 0; i < nCount; i++) {
            this.aDamagedElemAttr.add(i, 0);
        }
    }

    public static Mob Decode(InPacket iPacket) {
        Mob pEntity = new Mob();
        pEntity.dwTemplateID = iPacket.DecodeInt();
        pEntity.dwLinkedTemplateID = iPacket.DecodeInt();
        pEntity.dwMonsterBookID = iPacket.DecodeInt();
        pEntity.sName = iPacket.DecodeString();
        pEntity.nSpecies = iPacket.DecodeInt();
        pEntity.nMoveAbility = iPacket.DecodeInt();
        pEntity.bRegenAction = iPacket.DecodeBool();
        pEntity.bBodyAttack = iPacket.DecodeBool();
        pEntity.bBoss = iPacket.DecodeBool();
        pEntity.nLevel = iPacket.DecodeInt();
        pEntity.nMaxHP = iPacket.DecodeLong();
        pEntity.nMaxMP = iPacket.DecodeInt();
        pEntity.nSpeed = iPacket.DecodeInt();
        pEntity.nFlySpeed = iPacket.DecodeInt();
        pEntity.nChaseSpeed = iPacket.DecodeInt();
        pEntity.nPAD = iPacket.DecodeInt();
        pEntity.nPDD = iPacket.DecodeInt();
        pEntity.nMAD = iPacket.DecodeInt();
        pEntity.nMDD = iPacket.DecodeInt();
        pEntity.nACC = iPacket.DecodeInt();
        pEntity.nEVA = iPacket.DecodeInt();
        pEntity.nEXP = iPacket.DecodeLong();
        pEntity.nPushedDamage = iPacket.DecodeInt();
        pEntity.nHPRecovery = iPacket.DecodeInt();
        pEntity.nMPRecovery = iPacket.DecodeInt();
        pEntity.bUndead = iPacket.DecodeBool();
        pEntity.nFs = iPacket.DecodeFloat();
        pEntity.bSelfDestruction = iPacket.DecodeBool();
        pEntity.bFirstSelfDestruction = iPacket.DecodeBool();
        pEntity.nSelfDestructionHP = iPacket.DecodeInt();
        pEntity.bPickUpDrop = iPacket.DecodeBool();
        pEntity.bHasPublicDrop = iPacket.DecodeBool();
        pEntity.bHasExplosiveDrop = iPacket.DecodeBool();
        pEntity.bFirstAttack = iPacket.DecodeBool();
        pEntity.bInvincible = iPacket.DecodeBool();
        pEntity.bDisable = iPacket.DecodeBool();
        pEntity.bNoFlip = iPacket.DecodeBool();
        pEntity.bNotAttack = iPacket.DecodeBool();
        pEntity.nGetCP = iPacket.DecodeInt();
        pEntity.nFixedDamage = iPacket.DecodeInt();
        pEntity.nWeaponID = iPacket.DecodeInt();
        pEntity.bAngerGauge = iPacket.DecodeBool();
        pEntity.nChargeCount = iPacket.DecodeInt();
        pEntity.bHPgaugeHide = iPacket.DecodeBool();
        pEntity.nHPTagColor = iPacket.DecodeInt();
        pEntity.nHPTagBgColor = iPacket.DecodeInt();
        pEntity.bChase = iPacket.DecodeBool();
        pEntity.bDamagedByMob = iPacket.DecodeBool();
        pEntity.nDropItemPeriod = iPacket.DecodeInt();
        pEntity.nBanType = iPacket.DecodeInt();
        pEntity.sBanMsg = iPacket.DecodeString();
        pEntity.nAttackCount = iPacket.DecodeInt();
        pEntity.nSkillCount = iPacket.DecodeInt();
        pEntity.nHitCount = iPacket.DecodeInt();
        pEntity.nDieCount = iPacket.DecodeInt();
        pEntity.bHideHP = iPacket.DecodeInt();
        pEntity.bHideName = iPacket.DecodeInt();
        pEntity.bHideLevel = iPacket.DecodeInt();
        pEntity.nEscortType = iPacket.DecodeInt();
        pEntity.nChatBalloon = iPacket.DecodeInt();
        pEntity.nWidth = iPacket.DecodeInt();
        pEntity.nCategory = iPacket.DecodeInt();
        pEntity.bCannotEvade = iPacket.DecodeBool();
        pEntity.nDeadBuff = iPacket.DecodeInt();
        pEntity.tRemoveAfter = iPacket.DecodeInt();
        pEntity.bRemoveQuest = iPacket.DecodeBool();
        pEntity.bDoNotRemove = iPacket.DecodeBool();
        pEntity.bOnlyNormalAttack = iPacket.DecodeBool();
        pEntity.bCantPassByTeleport = iPacket.DecodeBool();

        int nCount = AttackElem.Count;
        for (int i = 0; i < nCount; i++) {
            pEntity.aDamagedElemAttr.add(i, 0);
        }

        nCount = iPacket.DecodeInt();
        for (int i = 0; i < nCount; i++) {
            pEntity.adwReviveTemplateID.add(i, 0);
        }

        nCount = iPacket.DecodeInt();
        for (int i = 0; i < nCount; i++) {
            pEntity.aDamagedBySelectedMob.add(i, 0);
        }

        nCount = iPacket.DecodeInt();
        for (int i = 0; i < nCount; i++) {
            pEntity.aDamagedBySelectedSkill.add(i, 0);
        }

        nCount = iPacket.DecodeInt();
        for (int i = 0; i < nCount; i++) {
            MobAttackInfo pAttackInfo = new MobAttackInfo();

            pAttackInfo.nType = iPacket.DecodeInt();
            pAttackInfo.nConMP = iPacket.DecodeInt();
            pAttackInfo.bMagicAttack = iPacket.DecodeBool();
            pAttackInfo.bDeadlyAttack = iPacket.DecodeBool();
            pAttackInfo.nMPBurn = iPacket.DecodeInt();
            pAttackInfo.nDisease = iPacket.DecodeInt();
            pAttackInfo.nSkillLevel = iPacket.DecodeInt();
            pAttackInfo.bKnockBack = iPacket.DecodeBool();
            pAttackInfo.nPAD = iPacket.DecodeInt();
            pAttackInfo.nMagicElemAttr = iPacket.DecodeInt();
            pAttackInfo.bInactive = iPacket.DecodeBool();
            pAttackInfo.nBulletNumber = iPacket.DecodeInt();
            pAttackInfo.bJumpAttack = iPacket.DecodeBool();
            pAttackInfo.nBulletSpeed = iPacket.DecodeInt();
            pAttackInfo.bTremble = iPacket.DecodeBool();
            pAttackInfo.bHitAttach = iPacket.DecodeBool();
            pAttackInfo.tEffectAfter = iPacket.DecodeInt();
            pAttackInfo.tAttackAfter = iPacket.DecodeInt();
            pAttackInfo.bDoFirst = iPacket.DecodeBool();
            pAttackInfo.bFacingAttatch = iPacket.DecodeBool();
            pAttackInfo.tRandDelayAttack = iPacket.DecodeInt();
            pAttackInfo.bRush = iPacket.DecodeBool();
            pAttackInfo.bSpeicalAttack = iPacket.DecodeBool();

            pEntity.aAttackInfo.add(i, pAttackInfo);
        }

        nCount = iPacket.DecodeInt();
        for (int i = 0; i < nCount; i++) {
            MobSkillInfo pSkillInfo = new MobSkillInfo();

            pSkillInfo.nSkillID = iPacket.DecodeInt();
            pSkillInfo.nSLV = iPacket.DecodeInt();
            pSkillInfo.nAction = iPacket.DecodeInt();
            pSkillInfo.tEffectAfter = iPacket.DecodeInt();

            pEntity.aSkillInfo.add(i, pSkillInfo);
        }

        nCount = iPacket.DecodeInt();
        for (int i = 0; i < nCount; i++) {
            MobBanMap pBanMap = new MobBanMap();

            pBanMap.dwFieldID = iPacket.DecodeInt();
            pBanMap.sPortalName = iPacket.DecodeString();

            pEntity.aBanMap.add(i, pBanMap);
        }

        if (pEntity.bSelfDestruction) {
            pEntity.selfDestructionInfo.bFirstAttack = iPacket.DecodeBool();
            pEntity.selfDestructionInfo.nActionType = iPacket.DecodeInt();
            pEntity.selfDestructionInfo.nBearHP = iPacket.DecodeInt();
            pEntity.tRemoveAfter = iPacket.DecodeInt();
        } else {
            pEntity.selfDestructionInfo = null;
        }

        return pEntity;
    }

    public static class MobAttackInfo {

        public int nType;
        public int nConMP;
        public boolean bMagicAttack;
        public boolean bDeadlyAttack;
        public int nMPBurn;
        public int nDisease;
        public int nSkillLevel;
        public boolean bKnockBack;
        public int nPAD;
        public int nMagicElemAttr;
        public boolean bInactive;
        public int nBulletNumber;
        public boolean bJumpAttack;
        public int nBulletSpeed;
        public boolean bTremble;
        public boolean bHitAttach;
        public int tEffectAfter;
        public int tAttackAfter;
        public boolean bDoFirst;
        public boolean bFacingAttatch;
        public int tRandDelayAttack;
        public boolean bRush;
        public boolean bSpeicalAttack;
    }

    public static class MobSkillInfo {

        public int nSkillID;
        public int nSLV;
        public int nAction;
        public int tEffectAfter;
    }

    public static class MobBanMap {

        public static final int None = 0;
        public static final int Collision = 1;
        public static final int UserAttack = 2;
        public static final int MobSkill = -1;
        
        public int dwFieldID;
        public String sPortalName;

        public MobBanMap() {
            this.sPortalName = "";
        }
    }

    public static class MobSelfDestruction {
        
        public static final int NoBomb = 0x0;
        public static final int HP = 0x1;
        public static final int FirstAttack = 0x2;
        public static final int TimeAttack = 0x4;

        public int nActionType;
        public int nBearHP;
        public boolean bFirstAttack;
        public int tRemoveAfter;

    }

    public static class MobQuestCountGroup {

        public final int dwTemplateID;
        public final List<Integer> aInfo;

        public MobQuestCountGroup(int dwTemplateID) {
            this.dwTemplateID = dwTemplateID;
            this.aInfo = new ArrayList<>();
        }
    }
}
