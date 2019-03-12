/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import java.util.ArrayList;
import java.util.List;
import util.Range;

/**
 *
 * @author Kaz
 */
public class Pet {

    public int dwTemplateID;
    public String sName;
    public int nMoveAbility;
    public int nHungry;
    public int nNameTag;
    public int nChatBalloon;
    public boolean bPickUpItem;
    public boolean bConsumeHP;
    public boolean bConsumeMP;
    public boolean bSweepForDrop;
    public boolean bLongRange;
    public boolean bIgnorePickup;
    public boolean bRecall;
    public boolean bAutoSpeaking;
    public boolean bAutoReact;
    public boolean bInterActByUserAction;
    public boolean bCanEvol;
    public int nEvolItemID;
    public int nEvolReqPetLvl;
    public int nEvolReqTameness;
    public final List<PetEvolData> aPED;
    public float fEvolProbSum;
    public final List<String> absAction;
    public final List<Interaction> aInteraction;
    public final List<FoodReaction> aFoodReaction;

    public Pet() {
        this.sName = "NONAME";
        this.aPED = new ArrayList<>();
        this.absAction = new ArrayList<>();
        this.aInteraction = new ArrayList<>();
        this.aFoodReaction = new ArrayList<>();
    }

    public static class PetEvolData {
        public int dwPetTemplateID;
        public float fProb;
    }

    public static class Action {

        public String bsAction;
        public final List<String> absSpeak;

        public Action() {
            this.bsAction = "";
            this.absSpeak = new ArrayList<>();
        }
    }

    /*
     * Reaction in pet wz is stuff like f1, 
     * string is in String.wz->PetDialog.img->id->f1_X
     * fX_f = Food fail responses
     * cX_s = Food success responses
     */
    public static class FoodReaction {

        public Range rgLevel;
        public final List<Action> actSuccess;
        public final List<Action> actFail;

        public FoodReaction() {
            this.rgLevel = new Range(0, 256);
            this.actSuccess = new ArrayList<>();
            this.actFail = new ArrayList<>();
        }
    }

    /*
     * Reaction in pet wz is stuff like c1, 
     * string is in String.wz->PetDialog.img->id->c1
     * cX = Command
     * cX_fX = Command fail responses
     * cX_sX = Command success responses
     */
    public static class Interaction {

        public Range rgLevel;
        public int nFriendnessInc;
        public int nProb;
        public final List<String> asCommand;
        public final List<Action> actSuccess;
        public final List<Action> actFail;

        public Interaction() {
            this.rgLevel = new Range(0, 256);
            this.asCommand = new ArrayList<>();
            this.actSuccess = new ArrayList<>();
            this.actFail = new ArrayList<>();
        }
    }
}
