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
package client.packet;

import center.CenterSessionManager;
import center.CenterSocket;
import center.GameServer;
import center.packet.Center;
import client.Account;
import client.ClientSessionManager;
import client.ClientSocket;

import java.util.Collections;
import java.util.List;

import net.packet.FileTime;
import net.packet.InPacket;
import net.packet.OutPacket;

import client.avatar.AvatarData;
import crypto.BCrypt;

import java.util.Date;
import net.packet.LoopBackPacket;

/**
 * @author Kaz Voeten
 */
public class Login {

    public static OutPacket AliveReq() {
        OutPacket oPacket = new OutPacket(LoopBackPacket.CheckAliveAck);
        return oPacket;
    }

    public static OutPacket LastConnectedWorld(int nWorldID) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.LatestConnectedWorld);
        oPacket.EncodeInt(nWorldID);
        return oPacket;
    }

    public static OutPacket RecommendWorldMessage(int nWorldID) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.RecommendWorldMessage);
        oPacket.EncodeByte(1);
        oPacket.EncodeInt(nWorldID);
        oPacket.EncodeString("The greatest world for starting anew!");
        return oPacket;
    }

    public static OutPacket UserLimitResult(int nStatus) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.UserLimitResult);
        oPacket.EncodeShort(nStatus);
        return oPacket;
    }

    public static OutPacket CharacterBurning(byte nType, int dwCharacterID) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.CharacterBurning);
        oPacket.EncodeByte(nType);
        oPacket.EncodeInt(dwCharacterID);
        return oPacket;
    }

    public static OutPacket SelectWorldResult() {
        OutPacket oPacket = new OutPacket(LoopBackPacket.SelectWorldResult);
        oPacket.EncodeBool(true);
        return oPacket;
    }

    public static OutPacket DuplicateIDResponse(String sName, boolean bTaken) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.CheckDuplicatedIDResult);
        oPacket.EncodeString(sName);
        oPacket.EncodeBool(bTaken);
        return oPacket;
    }

    public static OutPacket SecurityPacket() {
        OutPacket oPacket = new OutPacket(LoopBackPacket.SecurityPacket);
        oPacket.EncodeByte(0x01); //0x04 to request response.
        return oPacket;
    }

    public static OutPacket AuthenMessage() {
        OutPacket oPacket = new OutPacket(LoopBackPacket.AuthenMessage);
        oPacket.EncodeInt(0);
        oPacket.EncodeByte(0);
        return oPacket;
    }

    public static OutPacket ApplyHotFix() {
        OutPacket oPacket = new OutPacket(LoopBackPacket.ApplyHotfix);
        oPacket.EncodeBool(true);
        return oPacket;
    }

    public static OutPacket NCMOResult() {
        OutPacket oPacket = new OutPacket(LoopBackPacket.NMCOResult);
        oPacket.EncodeBool(true);
        return oPacket;
    }

    public static OutPacket PrivateServerPacket(int dwCurrentThreadID) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.PrivateServerPacket);
        oPacket.EncodeInt(dwCurrentThreadID ^ LoopBackPacket.PrivateServerPacket);
        return oPacket;
    }

    public static OutPacket JobOrder() {
        OutPacket oPacket = new OutPacket(LoopBackPacket.JOB_ORDER);
        JobOrder.Encode(oPacket);
        return oPacket;
    }

    public static OutPacket CreateNewCharacterResult(AvatarData pAvatar, boolean bSuccess) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.CreateNewCharacterResult);
        oPacket.EncodeBool(!bSuccess);
        if (bSuccess) {
            pAvatar.Encode(oPacket, false);
        }
        return oPacket;
    }

    public static OutPacket DeleteCharacterResult(int dwCharacterID, byte nResult) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.DeleteCharacterResult);
        oPacket.EncodeInt(dwCharacterID);
        oPacket.EncodeByte(nResult);
        return oPacket;
    }

    public static OutPacket SelectCharacterResult(int nResult, int dwCharacterID, int nAccountID, GameServer pGameServer) {

        OutPacket oPacket = new OutPacket(LoopBackPacket.SelectCharacterResult);

        oPacket.EncodeByte(nResult); //39 = OpenAlbaUI //3 = blocked ID
        oPacket.EncodeByte(0); //?

        byte[] NEXON_IP = new byte[]{(byte) 8, (byte) 31, (byte) 99, (byte) 141};
        oPacket.EncodeBuffer(NEXON_IP); //For debug client method. Can be changed to actual IP after removing IP checks: pGameServer.aIP
        oPacket.EncodeShort(pGameServer.nPort);

        oPacket.EncodeInt(0); //uChatIP
        oPacket.EncodeShort(0); //uChatPort

        oPacket.EncodeInt(nAccountID); //...?
        oPacket.EncodeInt(dwCharacterID); //dwCharacterID

        oPacket.EncodeBool(false); //bAuthenCode
        oPacket.EncodeInt(0); //ulArgument

        oPacket.EncodeBool(false); //
        oPacket.EncodeInt(0);

        oPacket.EncodeByte(0);
        oPacket.EncodeLong(0); //ftShutdown

        return oPacket;
    }

    public static void OnWorldInformationRequest(ClientSocket pClient) {
        CenterSessionManager.aCenterSessions.forEach((pWorld) -> {

            OutPacket oPacket = new OutPacket(LoopBackPacket.WorldInformation);

            oPacket.EncodeByte(pWorld.nWorldID);
            oPacket.EncodeString(pWorld.sWorldName);
            oPacket.EncodeByte(pWorld.nState);
            oPacket.EncodeString(pWorld.sMessage);
            oPacket.EncodeBool(pWorld.bCreateChar);

            oPacket.EncodeByte(pWorld.aChannels.size());
            pWorld.aChannels.forEach((pChannel) -> {
                oPacket.EncodeString(pWorld.sWorldName + "-" + pChannel.nChannelID);
                oPacket.EncodeInt(0);//pChannel.nGaugePx
                oPacket.EncodeByte(pWorld.nWorldID);
                oPacket.EncodeByte(pChannel.nChannelID - 1);
                oPacket.EncodeByte(0);//bIsAdultChannel
            });

            oPacket.EncodeShort(0); //Balloons lel
            oPacket.EncodeInt(0);
            oPacket.EncodeByte(0);

            pClient.SendPacket(oPacket);
        });

        OutPacket oPacket = new OutPacket(LoopBackPacket.WorldInformation);
        oPacket.EncodeByte(0xFF).EncodeByte(0).EncodeByte(0).EncodeByte(0);
        pClient.SendPacket(oPacket);
        pClient.SendPacket(LastConnectedWorld(CenterSessionManager.aCenterSessions.get(0).nWorldID));
        pClient.SendPacket((new OutPacket(LoopBackPacket.AliveReq)));
    }

    public static void OnSelectWorld(ClientSocket pSocket, InPacket iPacket) {
        if (!iPacket.DecodeBool()) {
            return;
        }

        String sToken = iPacket.DecodeString();
        iPacket.Skip(21);//I have no idea wtf this is lol.
        pSocket.nWorldID = iPacket.DecodeByte();
        pSocket.nChannelID = iPacket.DecodeByte();

        if (CenterSessionManager.GetByWorldID(pSocket.nWorldID).aChannels.get(pSocket.nChannelID) == null) {
            pSocket.Close();
            return;
        }

        CenterSessionManager.GetByWorldID(pSocket.nWorldID).SendPacket(Center.ProcessLogin(pSocket.nSessionID, sToken, pSocket.GetIP()));
    }

    public static OutPacket AccountInfoResult(int nResult, Account pAccount) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.AccountInfoResult);

        oPacket.EncodeByte(nResult);

        if (nResult == 0x00) {
            oPacket.EncodeInt(pAccount.nAccountID);
            oPacket.EncodeByte(0); //nGender
            oPacket.EncodeByte(pAccount.nAdmin); //nGradeCode
            oPacket.EncodeInt(pAccount.nAdmin * 64); //nGrade
            oPacket.EncodeInt(0); //nVIPGrade
            oPacket.EncodeByte(pAccount.nAdmin); // (removed in v191)
            oPacket.EncodeString(pAccount.sAccountName);
            oPacket.EncodeByte(0); //nPurchaseExp
            oPacket.EncodeByte(0); //nChatBlockReason
            oPacket.EncodeLong(0); //dtChatUnblockDate
            oPacket.EncodeString(pAccount.sAccountName);
            oPacket.EncodeLong(0); //dtSessionCreated
            oPacket.EncodeInt(3);
            oPacket.EncodeLong(pAccount.nSessionID);
            oPacket.EncodeString("");//unk
            JobOrder.Encode(oPacket);
            oPacket.EncodeBool(false); //Make view world button shining?
            oPacket.EncodeInt(-1); //Has to do with that shining button, so worldID?
        }

        return oPacket;
    }

    public static OutPacket SelectWorldResult(ClientSocket pSocket, boolean bIsEditedList) {
        List<AvatarData> aAvatarData = pSocket.pAccount.aAvatarData;

        OutPacket oPacket = new OutPacket(LoopBackPacket.SelectWorldResult);

        byte nMode = 0;
        oPacket.EncodeByte(nMode);
        if (nMode == 61) {
            boolean SendOTPForWebLaunching = false;
            oPacket.EncodeBool(SendOTPForWebLaunching);
        }
        oPacket.EncodeString(pSocket.nWorldID == 45 ? "reboot" : "normal");//topkek
        oPacket.EncodeInt(pSocket.nWorldID);// worldID?
        oPacket.EncodeBool(false);//burning event blocked
        oPacket.EncodeInt(0);//nReservedCharacters, follwed by ReserverCharacterData

        FileTime ftServerTime = new FileTime(new Date());
        ftServerTime.Encode(oPacket);

        //ReservedChar loops here after Long for time.
        oPacket.EncodeBool(bIsEditedList); //bIsEditedList for after you reorganize the charlist
        Collections.sort(aAvatarData, (AvatarData o1, AvatarData o2) -> o1.nCharlistPos - o2.nCharlistPos);

        oPacket.EncodeInt(aAvatarData.size()); //Order of appearance 
        for (AvatarData pAvatar : aAvatarData) {
            oPacket.EncodeInt(pAvatar.pCharacterStat.dwCharacterID);
        }

        oPacket.EncodeByte((byte) aAvatarData.size());
        for (AvatarData pAvatar : aAvatarData) {
            pAvatar.Encode(oPacket, false); //no ranking for now.
        }

        oPacket.EncodeBool(!pSocket.pAccount.sSPW.isEmpty());
        oPacket.EncodeBool(false); //bQuerrySSNOnCreateNewCharacter
        oPacket.EncodeInt(pSocket.pAccount.nCharSlots);
        oPacket.EncodeInt(0);//amount of chars bought with CS coupons? nBuyCharCount
        oPacket.EncodeInt(-1);//event new char job (maybe can be used for pinkbean)

        ftServerTime.Encode(oPacket);

        oPacket.EncodeByte((byte) 0); //enables the name change UI. value is count of names allowed to change
        oPacket.EncodeByte((byte) 0); //idk what this is.
        oPacket.EncodeBool(false); //based on world ID so might be reboot related | pSocket.nWorldID == 45
        oPacket.EncodeInt(0);
        oPacket.EncodeInt(0);

        return oPacket;
    }

    public static void OnCheckDuplicatedID(ClientSocket pSocket, InPacket iPacket) {
        CenterSessionManager.GetByWorldID(pSocket.nWorldID).SendPacket(Center.CheckDuplicatedID(pSocket.nSessionID, iPacket.DecodeString()));
    }

    public static void OnCreateNewCharacter(ClientSocket pSocket, InPacket iPacket) {
        if (pSocket.pAccount == null) {
            pSocket.Close();
            return;
        }

        if (pSocket.pAccount.aAvatarData.size() + 1 > pSocket.pAccount.nCharSlots) {
            pSocket.SendPacket(CreateNewCharacterResult(null, false));
            return;
        }

        CenterSessionManager.GetByWorldID(pSocket.nWorldID).SendPacket(Center.CreateNewCharacter(
                pSocket.nSessionID,
                pSocket.pAccount.aAvatarData.size() + 1,
                iPacket.DecodeBuffer(iPacket.GetLength()))
        );
    }

    public static void OnClientDumpLog(InPacket iPacket) {
        if (iPacket.GetLength() < 8) {
            System.out.println(iPacket.DecodeString(iPacket.GetLength()));
        } else {
            int nCallType = iPacket.DecodeShort();
            int dwErrorCode = iPacket.DecodeInt();
            int dwBackupBufferSize = iPacket.DecodeShort();
            if (dwBackupBufferSize > 4096) {
                return;
            }

            short uRawSeq = iPacket.DecodeShort();
            short uDataLen = iPacket.DecodeShort();
            short nClientPacket = iPacket.DecodeShort();

            String sData = iPacket.toString();
            System.out.println(String.format("[Debug] Report type: %s \r\n\t"
                            + "dwErrorCode: %d, dwBackupBufferSize: %d , uRawSeq: %s, uDataLen: %s\r\n\t"
                            + "Account: %s \r\n\t"
                            + "nClientPacket: %s | %s \r\n\t"
                            + "Data: %s",
                    nCallType, dwErrorCode, dwBackupBufferSize, uRawSeq,
                    uDataLen, "null", nClientPacket, "0x" + Integer.toHexString(nClientPacket), sData
            ));
        }
    }

    public static void OnCreateNewCharacterResult(ClientSocket pSocket, InPacket iPacket) {
        boolean bSuccess = iPacket.DecodeBool();
        AvatarData pAvatar = null;
        if (bSuccess) {
            pAvatar = AvatarData.Decode(pSocket.pAccount.nAccountID, iPacket);
            pSocket.pAccount.aAvatarData.add(pAvatar);
        }
        pSocket.SendPacket(CreateNewCharacterResult(pAvatar, bSuccess));
    }

    public static void OnSetSPW(ClientSocket pSocket, InPacket iPacket) {
        Account pAccount = pSocket.pAccount;

        if (pAccount == null || !iPacket.DecodeBool() || !iPacket.DecodeBool()) {
            Center.Ban(pSocket, 0, "[LoginServer] Packet Editing: An account wasn't loaded so the request is impossible in OnSetSPW.", 1000 * 60 * 60 * 12);
            return;
        }

        if (!pAccount.sSPW.isEmpty() || !pAccount.sSPW.equals("")) {
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: Second Password is already set in OnSetSPW.", 1000 * 60 * 60 * 12);
            return;
        }

        int nCharacterID = iPacket.DecodeInt();
        boolean bOwned = false; //Ensures character belongs to account/session.
        for (AvatarData pAvatar : pSocket.pAccount.aAvatarData) {
            if (pAvatar.pCharacterStat.dwCharacterID == nCharacterID) {
                bOwned = true;
                break;
            }
        }

        if (!bOwned) {
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: Avatar does not belong to account/session in OnSetSPW.", 1000 * 60 * 60 * 24 * 30);
            return;
        }

        pAccount.sMAC = iPacket.DecodeString();
        pAccount.sHWID = iPacket.DecodeString();
        String sSPW = iPacket.DecodeString();

        if (sSPW.length() < 6 || sSPW.length() > 13 || !sSPW.matches("[0-9]+")) {
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: SPW is of improper length or non-numeric in OnSetSPW.", 1000 * 60 * 60 * 12);
            return;
        }

        pAccount.sSPW = BCrypt.hashpw(sSPW, BCrypt.gensalt());
        pAccount.VerifyWhitelisted(pSocket);

        CenterSocket pCenterSocket = CenterSessionManager.GetByWorldID(pSocket.nWorldID);
        pCenterSocket.SendPacket(Center.SetSecondPW(pSocket.nSessionID, pAccount.nAccountID, pAccount.sSPW));
    }

    public static void OnSelectCharacter(ClientSocket pSocket, InPacket iPacket) {
        Account pAccount = pSocket.pAccount;

        if (pAccount == null) {
            Center.Ban(pSocket, 0, "[LoginServer] Packet Editing: An account wasn't loaded so the request is impossible in OnSelectCharacter.", 1000 * 60 * 60 * 12);
            return;
        }

        if (!pSocket.bSecondPW) {
            pSocket.SendPacket(ChangeSPWResult((byte) 6)); //Unable to process request.
            return;
        }

        int nCharacterID = iPacket.DecodeInt();
        boolean bOwned = false; //Ensures character belongs to account/session.
        for (AvatarData pAvatar : pSocket.pAccount.aAvatarData) {
            if (pAvatar.pCharacterStat.dwCharacterID == nCharacterID) {
                bOwned = true;
                break;
            }
        }

        if (!bOwned) {
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: Avatar does not belong to account/session in OnSetSPW.", 1000 * 60 * 60 * 24 * 30);
            return;
        }

        CenterSocket pCenterSocket = CenterSessionManager.GetByWorldID(pSocket.nWorldID);
        pCenterSocket.SendPacket(Center.SetState(pSocket.nSessionID, (byte) 2, pAccount.nAccountID, nCharacterID));
        pSocket.SendPacket(Login.SelectCharacterResult(0, nCharacterID, pAccount.nAccountID, pCenterSocket.aChannels.get(pSocket.nChannelID)));
        System.out.println("Sent ServerIP");
    }

    public static void OnChangeSPW(ClientSocket pSocket, InPacket iPacket) {
        Account pAccount = pSocket.pAccount;

        if (pAccount == null) {
            Center.Ban(pSocket, 0, "[LoginServer] Packet Editing: An account wasn't loaded so the request is impossible in OnChangeSPW.", 1000 * 60 * 60 * 12);
            return;
        }

        if (pAccount.sSPW.isEmpty() || pAccount.sSPW.equals("")) {
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: Second Password hasn't been set yet in OnChangeSPW.", 1000 * 60 * 60 * 12);
            return;
        }

        String sSecondPassword = iPacket.DecodeString();
        String sNewPassword = iPacket.DecodeString();

        if (!BCrypt.checkpw(sSecondPassword, pAccount.sSPW)) {
            pSocket.SendPacket(ChangeSPWResult((byte) 0x14)); //Wrong Password
            return;
        }

        if (sNewPassword.length() < 6 || sNewPassword.length() > 13 || !sNewPassword.matches("[0-9]+")) {
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: SPW is of improper length or non-numeric in OnSetSPW.", 1000 * 60 * 60 * 12);
            return;
        }

        pAccount.sSPW = BCrypt.hashpw(sNewPassword, BCrypt.gensalt());
        pAccount.VerifyWhitelisted(pSocket);

        CenterSocket pCenterSocket = CenterSessionManager.GetByWorldID(pSocket.nWorldID);
        pCenterSocket.SendPacket(Center.SetSecondPW(pSocket.nSessionID, pAccount.nAccountID, pAccount.sSPW));
    }

    public static void OnCheckSPW(ClientSocket pSocket, InPacket iPacket) {
        pSocket.Lock();
        try {
            Account pAccount = pSocket.pAccount;

            if (pAccount == null) {
                Center.Ban(pSocket, 0, "[LoginServer] Packet Editing: An account wasn't loaded so the request is impossible in OnCheckSPW.", 1000 * 60 * 60 * 12);
                return;
            }

            if (pAccount.sSPW.isEmpty() || pAccount.sSPW.equals("")) {
                Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: Second Password hasn't been set yet in OnCheckSPW.", 1000 * 60 * 60 * 12);
                return;
            }

            String sSecondPassword = iPacket.DecodeString();

            int nCharacterID = iPacket.DecodeInt();
            boolean bOwned = false; //Ensures character belongs to account/session.
            for (AvatarData pAvatar : pSocket.pAccount.aAvatarData) {
                if (pAvatar.pCharacterStat.dwCharacterID == nCharacterID) {
                    bOwned = true;
                    break;
                }
            }

            if (!bOwned) {
                Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: Avatar does not belong to account/session in OnSetSPW.", 1000 * 60 * 60 * 24 * 30);
                return;
            }

            iPacket.DecodeBool(); //idk
            pAccount.sMAC = iPacket.DecodeString();
            pAccount.sHWID = iPacket.DecodeString();

            if (!BCrypt.checkpw(sSecondPassword, pAccount.sSPW)) {
                pSocket.SendPacket(ChangeSPWResult((byte) 0x14)); //Wrong Password
            } else {
                pSocket.bSecondPW = true;
            }
        } finally {
            pSocket.Unlock();
        }
    }

    public static void OnChangeCharacterLocation(ClientSocket pSocket, InPacket iPacket) {
        if (pSocket.pAccount == null) {
            Center.Ban(pSocket, 0, "[LoginServer] Packet Editing: An account wasn't loaded so the request is impossible in OnChangeCharacterLocation.", 1000 * 60 * 60 * 12);
            return;
        }

        if (iPacket.DecodeInt() != pSocket.pAccount.nAccountID) {
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: AccountID mismatch in ChangeCharacterLocation.", 1000 * 60 * 60 * 24 * 30);
        }

        if (iPacket.DecodeBool()) {
            int nCharacters = iPacket.DecodeInt();
            if (nCharacters != pSocket.pAccount.aAvatarData.size()) {
                Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: AvatarCount mismatch in ChangeCharacterLocation.", 1000 * 60 * 60 * 12);
            }

            for (int i = 1; i < nCharacters; i++) {
                AvatarData pAvatarToEdit = null;
                int dwCharacterID = iPacket.DecodeInt();
                for (AvatarData pAvatar : pSocket.pAccount.aAvatarData) {
                    if (pAvatar.pCharacterStat.dwCharacterID == dwCharacterID) {
                        pAvatarToEdit = pAvatar;
                        break;
                    }
                }
                if (pAvatarToEdit == null) {
                    Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: dwCharacterID doesn't belong to nAccountID in ChangeCharacterLocation", 1000 * 60 * 60 * 24 * 30);
                } else {
                    pAvatarToEdit.nCharlistPos = i;
                }
            }

            List<AvatarData> aAvatarData = pSocket.pAccount.aAvatarData;
            Collections.sort(aAvatarData, (AvatarData o1, AvatarData o2) -> o1.nCharlistPos - o2.nCharlistPos);
            CenterSessionManager.GetByWorldID(pSocket.nWorldID).SendPacket(Center.ChangeCharacterLocation(pSocket.nSessionID, aAvatarData));
        }
    }

    public static OutPacket ChangeSPWResult(byte mode) {
        OutPacket oPacket = new OutPacket(LoopBackPacket.ChangeSPWResult);
        oPacket.EncodeByte(mode);
        return oPacket;
    }

    public static void OnSetSPWResult(InPacket iPacket) {
        int nAccountID = iPacket.DecodeInt();
        boolean bSuccess = iPacket.DecodeBool();
        ClientSocket pSocket = ClientSessionManager.GetSessionByAccountID(nAccountID);

        if (pSocket == null) {
            return;
        }

        if (bSuccess) {
            pSocket.SendPacket(ChangeSPWResult((byte) 0x00)); //PIC activated succesfully.
        } else {
            pSocket.pAccount.sSPW = "";
            pSocket.SendPacket(ChangeSPWResult((byte) 6)); //Unable to process request.
        }
    }

    public static void OnDeleteCharacter(ClientSocket pSocket, InPacket iPacket) {
        String sSecondPW = iPacket.DecodeString();
        int dwCharacterID = iPacket.DecodeInt();

        Account pAccount = pSocket.pAccount;

        if (pAccount == null) {
            Center.Ban(pSocket, 0, "[LoginServer] Packet Editing: An account wasn't loaded so the request is impossible in OnDeleteCharacter.", 1000 * 60 * 60 * 12);
            return;
        }

        if (pAccount.sSPW.isEmpty() || pAccount.sSPW.equals("")) {
            System.out.println("Passowrd is null.");
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: Second Password hasn't been set yet in DeleteCharacter.", 1000 * 60 * 12);
            return;
        }

        if (!BCrypt.checkpw(sSecondPW, pAccount.sSPW)) {
            pSocket.SendPacket(DeleteCharacterResult(dwCharacterID, (byte) 20)); //Wrong Password
            return;
        }

        boolean bOwned = false; //Ensures character belongs to account/session.
        for (AvatarData pAvatar : pSocket.pAccount.aAvatarData) {
            if (pAvatar.pCharacterStat.dwCharacterID == dwCharacterID) {
                bOwned = true;
                break;
            }
        }

        if (!bOwned) {
            Center.Ban(pSocket, 2, "[LoginServer] Packet Editing: Avatar does not belong to account/session in DeleteCharacter.", 1000 * 60 * 24 * 30);
            return;
        }

        CenterSocket pCenterSocket = CenterSessionManager.GetByWorldID(pSocket.nWorldID);
        pCenterSocket.SendPacket(Center.DeleteCharacter(pSocket.nSessionID, dwCharacterID));
    }

    /**
     * @author Eric Smith
     */
    public class Balloon {

        public int nX;
        public int nY;
        public String sMessage;

        public Balloon(String sMessage, int nX, int nY) {
            this.sMessage = sMessage;
            this.nX = nX;
            this.nY = nY;
        }
    }
}
