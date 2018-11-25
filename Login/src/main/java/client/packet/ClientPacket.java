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

/**
 * @author Kaz Voeten
 */
public class ClientPacket {

    public static final short DummyCode = 100,// Version 188
            BeginSocket = 101,// Version 188
            SecurityPacket = 102,// Version 188
            PermissionRequest = 103,// Version 188
            LoginBasicInfo = 104,// Version 188
            CheckLoginAuthInfo = 105,// Version 188
            SelectWorld = 106,// Version 188
            CheckSPWRequest = 107,// Version 188
            SelectCharacter = 108,// Version 188
            CheckSPWExistRequest = 109,// Version 188
            MigrateIn = 110,// Version 188
            WorldInfoLogoutRequest = 114,// Version 188
            WorldInfoForShiningRequest = 115,// Version 188
            CheckDuplicatedID = 116,// Version 188
            LogoutWorld = 117,// Version 188
            PermissionRequest_Fake = 118,// Version 188
            CheckLoginAuthInfo_Fake = 119,// Version 188
            CreateMapleAccount_Fake = 120,// Version 188
            SelectAccount_Fake = 121,// Version 188
            SelectWorld_Fake = 122,// Version 188
            SelectCharacter_Fake = 123,// Version 188
            CreateNewCharacter_Fake = 124,// Version 188
            CreateNewCharacter = 125,// Version 188
            CreateNewCharacterInCS = 126,// Version 188
            CreateNewCharacter_PremiumAdventurer = 127,// Version 188
            DeleteCharacter = 128,// Version 188
            ReservedDeleteCharacterConfirm = 129,// Version 188
            ReservedDeleteCharacterCancel = 130,// Version 188
            RenameCharacter = 131,// Version 188
            AliveAck_Fake = 132,// Version 188
            ExceptionLog = 133,// Version 188
            PrivateServerPacket = 134,// Version 188
            ResetLoginStateOnCheckOTP = 135,// Version 188
            AlbaRequest = 142,// Version 188
            UpdateLoginCookie = 143,// Version 188
            CheckCenterAndGameAreConnected = 144,// Version 188
            ResponseToCheckAliveAck_Fake = 145,// Version 188
            CreateMapleAccount = 146,// Version 188
            AliveAck = 151,// Version 188
            ResponseToCheckAliveAck = 152,// Version 188
            ClientDumpLog = 153,// Version 188
            CrcErrorLog = 154,// Version 188
            PerformanceInfoProvidedConsent = 155,// Version 188
            CheckHotfix = 156,// Version 188
            ClientLoadingState = 158,// Version 188
            ChangeCharacterLocation = 164,// Version 188
            UserLimitRequest = 167,// Version 188
            WorldInfoRequest = 171,// Version 188
            SetSPW = 176,// Version 188
            ChangeSPWRequest = 180,// Version 188
            NMCORequest = 182,// Version 188
            MapLogin = 183, // Version 188
            EndSocket = 184,// Version
            CharacterBurning = 537; //needed but officially not in this scope lol

    /*
     * Enum version of the Recv packet ID's for fetching the name in debug messages.
     */
    public enum ClientPacketEnum {
        DummyCode(100),// Version 188
        BeginSocket(101),// Version 188
        SecurityPacket(102),// Version 188
        PermissionRequest(103),// Version 188
        LoginBasicInfo(104),// Version 188
        CheckLoginAuthInfo(105),// Version 188
        SelectWorld(106),// Version 188
        CheckSPWRequest(107),// Version 188
        SelectCharacter(108),// Version 188
        CheckSPWExistRequest(109),// Version 188
        MigrateIn(110),// Version 188
        WorldInfoLogoutRequest(114),// Version 188
        WorldInfoForShiningRequest(115),// Version 188
        CheckDuplicatedID(116),// Version 188
        LogoutWorld(117),// Version 188
        PermissionRequest_Fake(118),// Version 188
        CheckLoginAuthInfo_Fake(119),// Version 188
        CreateMapleAccount_Fake(120),// Version 188
        SelectAccount_Fake(121),// Version 188
        SelectWorld_Fake(122),// Version 188
        SelectCharacter_Fake(123),// Version 188
        CreateNewCharacter_Fake(124),// Version 188
        CreateNewCharacter(125),// Version 188
        CreateNewCharacterInCS(126),// Version 188
        CreateNewCharacter_PremiumAdventurer(127),// Version 188
        DeleteCharacter(128),// Version 188
        ReservedDeleteCharacterConfirm(129),// Version 188
        ReservedDeleteCharacterCancel(130),// Version 188
        RenameCharacter(131),// Version 188
        AliveAck_Fake(132),// Version 188
        ExceptionLog(133),// Version 188
        PrivateServerPacket(134),// Version 188
        ResetLoginStateOnCheckOTP(135),// Version 188
        AlbaRequest(142),// Version 188
        UpdateLoginCookie(143),// Version 188
        CheckCenterAndGameAreConnected(144),// Version 188
        ResponseToCheckAliveAck_Fake(145),// Version 188
        CreateMapleAccount(146),// Version 188
        AliveAck(151),// Version 188
        ResponseToCheckAliveAck(152),// Version 188
        ClientDumpLog(153),// Version 188
        CrcErrorLog(154),// Version 188
        PerformanceInfoProvidedConsent(155),// Version 188
        CheckHotfix(156),// Version 188
        UnknownSpam(158),// Version 188
        ChangeCharacterLocation(164),// Version 188
        UserLimitRequest(167),// Version 188
        WorldInfoRequest(171),// Version 188
        SetSPW(176),// Version 188
        ChangeSPWRequest(180),// Version 188
        NMCORequest(182),// Version 188
        MapLogin(183), // Version 188
        EndSocket(184),// Version 188
        CharacterBurning(537);

        private short nPacketID;

        private ClientPacketEnum(int nPacketID) {
            this.nPacketID = (short) nPacketID;
        }

        public static String GetName(int nPacketID) {
            for (ClientPacketEnum ClientPacket : ClientPacketEnum.values()) {
                if (ClientPacket.nPacketID == nPacketID) {
                    return ClientPacket.name();
                }
            }
            return "UNDEFINED";
        }
    }
}
