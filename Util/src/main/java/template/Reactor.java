/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kaz
 */
public class Reactor {
    public int dwTemplateID;
    public int nStateCount;
    public int tHitDelay;
    public int nQuestID;
    public int nReqHitCount;
    public boolean bNotHitable;
    public boolean bActivateByTouch;
    public boolean bRemoveInFieldSet;
    public String sActionPath;//Server-side
    public final List<StateInfo> aStateInfo;
    
    public Reactor() {
        this.sActionPath = "";
        this.aStateInfo = new ArrayList<>();
    }
    
    /**
     * CReactorTemplate::STATEINFO
     * 
     * @author Eric
    */
    public static class StateInfo {
        public int tHitDelay;
        public int tTimeout;
        public boolean bRepeat;
        public final List<ReactorEventInfo> aReactorEventInfo;
        
        public StateInfo() {
            this.aReactorEventInfo = new ArrayList<>();
        }
    }
    
    /**
     * CReactorTemplate::REACTOREVENTINFO
     * 
     * @author Eric
    */
    public static class ReactorEventInfo {
        public int nType;
        public int tHitDelay;
        public int nStateToBe;
        public Rectangle rcCheckArea;
        public final List<Integer> aArgs;
        public final List<Integer> aActiveSkillID;
        
        public ReactorEventInfo() {
            this.rcCheckArea = new Rectangle();
            this.aArgs = new ArrayList<>();
            this.aActiveSkillID = new ArrayList<>();
        }
    }
}
