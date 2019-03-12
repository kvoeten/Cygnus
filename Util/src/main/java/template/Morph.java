/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import net.packet.InPacket;

/**
 *
 * @author Kaz
 */
public class Morph {

    public int dwTemplateID;
    public String sName;
    public int nMovability;
    public int nSpeed;
    public int nJump;
    public float dFs;
    public float nSwim;

    public Morph() {
        this.sName = "NONAME";
    }

    public Morph Decode(InPacket iPacket) {
        Morph pEntity = new Morph();
        pEntity.dwTemplateID = iPacket.DecodeInt();
        pEntity.sName = iPacket.DecodeString();
        pEntity.nMovability = iPacket.DecodeInt();
        pEntity.nSpeed = iPacket.DecodeInt();
        pEntity.nJump = iPacket.DecodeInt();
        pEntity.dFs = iPacket.DecodeFloat();
        pEntity.nSwim = iPacket.DecodeFloat();
        return pEntity;
    }
}
