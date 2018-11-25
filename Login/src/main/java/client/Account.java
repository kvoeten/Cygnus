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
package client;

/**
 * @author Kaz Voeten
 */

import java.util.LinkedList;
import java.util.List;

import net.packet.InPacket;
import client.avatar.AvatarData;

public class Account {

    public final int nAccountID, nCharSlots;
    public final long nSessionID;
    public String sAccountName, sIP, sSPW, sHWID, sMAC;
    public final byte nState, nGender, nAdmin;
    public List<AvatarData> aAvatarData = new LinkedList<>();

    private Account(int nAccountID, long nSessionID, String sAccountName, String sIP, String sPIC,
                    byte nState, byte nGender, byte nAdmin, int nCharSlots) {
        this.nAccountID = nAccountID;
        this.nSessionID = nSessionID;
        this.sAccountName = sAccountName;
        this.sIP = sIP;
        this.sSPW = sPIC;
        this.nState = nState;
        this.nGender = nGender;
        this.nAdmin = nAdmin;
        this.nCharSlots = nCharSlots;
    }

    public static Account Decode(InPacket iPacket) {
        Account pRet = new Account(
                iPacket.DecodeInt(),
                iPacket.DecodeLong(),
                iPacket.DecodeString(),
                iPacket.DecodeString(),
                iPacket.DecodeString(),
                iPacket.DecodeByte(),
                iPacket.DecodeByte(),
                iPacket.DecodeByte(),
                iPacket.DecodeInt()
        );
        return pRet;
    }

    public void VerifyWhitelisted(ClientSocket pSocket) {
        //TODO: API
    }
}
