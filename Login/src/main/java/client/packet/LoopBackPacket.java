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
public class LoopBackPacket {

    public static final short CheckPasswordResult = 0,
            WorldInformation = 1,
            LastConnectedWorld = 2,
            RecommendWorldMessage = 3,
            SetClientKey = 4,
            SetPhysicalWorldID = 5,
            SelectWorldResult = 6,
            SelectCharacterResult = 7,
            AccountInfoResult = 8,
            CreateMapleAccountResult = 9,
            CheckDuplicatedIDResult = 10,
            CreateCharacterResult = 11,
            DeleteCharacterResult = 12,
            ReservedDeleteCharacterResult = 13,
            ReservedDeleteCharactercancelResult = 14,
            RenameCharacterResult = 15,
            SetCharacterID = 16,
            MigrateCommand = 17,
            OnAliveReq = 18,
            PingCheckResult_ClientToGame = 20,
            AuthenCodeChanged = 21,
            AuthenMessage = 22,
            SecurityPacket = 23,
            UnknownClientSocketPacket = 24,
            PrivateServerPacket = 25,
            ChangeSPWResult = 31,
            CheckSPWExistResult = 32,
            CheckWebLoginEmailID = 33,
            CheckCrcResult = 34,
            AlbaRequestResult = 35,
            CheckAliveAck = 37, //v188
            ApplyHotFix = 40, //v188
            UserLimitResult = 43,//v188
            JOB_ORDER = 45,
            NMCOResult = 47,
            BeginCharacterData = 75,
            InventoryOperation = 75,
            InventoryGrow = 76,
            StatChanged = 77,
            TemporaryStatSet = 78,
            TemporaryStatReset = 79,
            ForcedStatSet = 80,
            ForcedStatReset = 81,
            ChangeSkillRecordResult = 82,
            ChangeStealMemoryResult = 83,
            UserDamageFallingCheck = 84,
            PersonalShopBuyCheck = 85,
            MobDropMesoPickup = 86,
            BreakTimeFieldEnter = 87,
            RuneActSuccess = 88,
            ResultStealSkillList = 89,
            SkillUseResult = 90,
            ExclRequest = 91,
            GivePopularityResult = 92,
            Message = 93,
            MemoResult = 94,
            MapTransferResult = 95,
            AntiMacroResult = 96,
            AntiMacroBombResult = 97,
            InitialQuizStart = 98,
            ClaimResult = 99,
            SetClaimSvrAvailableTime = 100,
            ClaimSvrStatusChanged = 101,
            StarPlanetUserCount = 102,
            SetTamingMobInfo = 103,
            QuestClear = 104,
            EntrustedShopCheckResult = 105,
            SkillLearnItemResult = 106,
            SkillResetItemResult = 107,
            AbilityResetItemResult = 108,
            ExpConsumeResetItemResult = 109,
            ExpItemGetResult = 110,
            CharSlotIncItemResult = 111,
            CharRenameItemResult = 112,
            GatherItemResult = 113,
            SortItemResult = 114,
            RemoteShopOpenResult = 115,
            PetDeadMessage = 116,
            CharacterInfo = 117,
            PartyResult = 118,
            PartyMemberCandidateResult = 119,
            UrusPartyMemberCandidateResult = 120,
            PartyCandidateResult = 121,
            UrusPartyResult = 122,
            IntrusionFriendCandidateResult = 123,
            IntrusionLobbyCandidateResult = 124,
            ExpeditionRequest = 125,
            ExpedtionResult = 126,
            FriendResult = 127,
            LoadAccountIDOfCharacterFriendResult = 128,
            GuildRequest = 129,
            GuildResult = 130,
            AllianceResult = 131,
            TownPortal = 132,
            BrodcastMsg = 133,
            IncubatorResult = 134,
            IncubatorHotItemResult = 135,
            ShopScannerResult = 136,
            ShopLinkResult = 137,
            MarriageRequest = 138,
            MarriageResult = 139,
            WeddingGiftResult = 140,
            NotifyMarriedPartnerMapTransfer = 141,
            CashPetFoodResult = 142,
            CashPetPickUpOffResult = 143,
            CashPetSkillSettingResult = 144,
            CashLookChangeResult = 145,
            CashPetDyeingResult = 146,
            SetWeekEventMessage = 147,
            SetPotionDiscountRate = 148,
            BridleMobCatchFail = 149,
            ImitatedNPCData = 150,
            ImitatedNPCDisableInfo = 151,
            LimitedNPCDisableInfo = 152,
            MonsterBookSetCard = 153,
            MonsterBookSetCover = 154,
            HourChanged = 155,
            MiniMapOnOff = 156,
            ConsultAuthkeyUpdate = 157,
            ClassCompetitionAuthkeyUpdate = 158,
            WebBoardAuthkeyUpdate = 159,
            SessionValue = 160,
            PartyValue = 161,
            FieldSetVariable = 162,
            FieldValue = 163,
            BonusExpRateChanged = 164,
            NotifyLevelUp = 165,
            NotifyWedding = 166,
            NotifyJobChange = 167,
            SetBuyEquipExt = 168,
            SetPassenserRequest = 169,
            ScriptProgressMessageBySoul = 170,
            ScriptProgressMessage = 171,
            ScriptProgressItemMessage = 172,
            SetStaticScreenMessage = 174,
            OffStaticScreenMessage = 175,
            WeatherEffectNotice = 176,
            WeatherEffectNoticeY = 177,
            ProgressMessageFont = 178,
            DataCRCCheckFailed = 179,
            ShowSlotMessage = 180,
            WildHunterInfo = 181,
            ZeroInfo = 182,
            ZeroWP = 183,
            ZeroInfoSubHP = 184,
            OpenUICreatePremiumAdventurer = 185,
            FieldSetEnterSuccessed = 186,
            ResultInstanceTable = 187,
            CoolTimeSet = 188,
            ItemPotChange = 189,
            ItemCoolTimeChange = 190,
            SetAdDisplayInfo = 191,
            SetAdDisplayStatus = 192,
            SetSonOfLinkedSkillResult = 193,
            SetMapleStyeInfo = 197,
            SetBuyLimitCount = 198,
            ResetBuyLimitcount = 199,
            UpdateUIEventListInfo = 200,
            DojangRanking = 201,
            DefenseGameResponse_Shop = 202,
            DefenseGameResponse_Inventory = 203,
            ShutdownMessage = 204,
            ResultSetStealSkill = 205,
            SlashCommand = 206,
            StartNavigationRequest = 207,
            FuncKeySetByScript = 208,
            CharacterPotentialSet = 209,
            CharacterPotentialReset = 210,
            CharacterHonorExp = 211,
            ReadyForRespawn = 212,
            ReadyForRespawnByPoint = 213,
            OpenReadyForRespawnUI = 214,
            CharacterHonorGift = 215,
            CrossHunterCompleteResult = 216,
            CrossHunterShopResult = 217,
            SetCashItemNotice = 219,
            SetSpecialCashItem = 220,
            ShowEventNotice = 221,
            BoardGameResult = 222,
            YutGameResult = 223,
            ValuePackResult = 224,
            UserUseNaviFlyingResult = 225,
            MapleStyleResult = 226,
            OpenWeddingEx = 227,
            BingoResult = 228,
            BingoCassandraResult = 229,
            UpdateVIPGrade = 230,
            MesoRangerResult = 231,
            SetMaplePoint = 232,
            SetAdditionalCashInfo = 233,
            SetMiracleTime = 234,
            HyperSkillResetResult = 235,
            GetServerTime = 236,
            GetCharacterPosition = 237,
            SetFixDamageForTest = 238,
            ReturnEffectConfirm = 241,
            ReturnEffectModified = 242,
            WhiteAdditionalCubeResult = 243,
            BlackCubeResult = 244,
            MemorialCubeResult = 245,
            MemorialCubeModified = 246,
            DressUpInfoModified = 247,
            ResetOnStateForOnOffSkill = 249,
            SetOffStateForOnOffSkill = 250,
            IssueReloginCookie = 251,
            AvatarPackTest = 252,
            EvolvingResult = 253,
            ActionBarResult = 254,
            GuildContentResult = 255,
            GuildSearchResult = 256,
            BufferFlyResult = 257,
            HalloweenCandyRankingResult = 258,
            GetRewardResult = 259,
            Mentoring = 260,
            GetLotteryResult = 261,
            CheckProcess = 262,
            CompleteNpcSpeechSuccess = 263,
            CompleteSpecialCheckSuccess = 264,
            SetAccountInfo = 265,
            SetGachaponFeverTime = 266,
            AvatarMegaphoneRes = 267,
            AvatarMegaphoneUpdateMessage = 268,
            AvatarMegaphoneClearMessage = 269,
            RequestEventList = 270,
            LikePoint = 271,
            SignErrorAck = 272,
            AskAfterErrorAck = 273,
            EventNameTagInfo = 274,
            GiveEventNameTag = 275,
            JobFreeChangeResult = 276,
            EventLotteryOpen = 277,
            EventLotteryResult = 278,
            InvasionSupportSet = 279,
            InvasionSupportAttackResult = 280,
            InvasionSupportBossKill = 281,
            InvasionSupportSetttingResult = 282,
            InvasionElapsedTime = 283,
            InvasionSystemMsg = 284,
            InvasionBossKeyChange = 285,
            ScreenMsg = 286,
            TradeBlockForSnapshot = 287,
            LimitGoodsNoticeResult = 288,
            MonsterBattle = 289,
            MonsterBattleCombat = 290,
            UniverseBossPossible = 291,
            UniverseBossImpossible = 292,
            CashShopPreviewInfo = 293,
            ChangeSoulCollectionResult = 294,
            SelectSoulCollectionResult = 295,
            UserMasterPieceResult = 296,
            PendantSlotIncResult = 297,
            BossArenaMatchSucess = 298,
            BossArenaMatchFail = 299,
            BossArenaMatchRequestDone = 300,
            UserSoulMatching = 301,
            Catapult_UpgradeSkill = 302,
            Catapult_ResetSkill = 303,
            PartyQuestRankingResult = 304,
            CoordinationContestInfo = 305,
            WorldTransferResult = 306,
            TrunkSlotIncItemResult = 307,
            EliteMobWorldMapNotice = 308,
            RandomPortalWorldMapNotice = 309,
            WorldTransferHelperNotify = 310,
            EquipmentEnchantDisplay = 311,
            TopTowerRankResult = 312,
            FriendTowerRankResult = 313,
            TowerResultUIOpen = 314,
            MannequinResult = 315,
            IronBoxEvent = 316,
            CreateKoreanJumpingGame = 317,
            CreateSwingGame = 318,
            UserUpdateMapleTVShowTime = 319,
            ReturnToTitle = 320,
            ReturnToCharacterSelect = 321,
            FlameWizardFlameWalkEffect = 322,
            lameWizardFlareBlink = 323,
            SummonedAvatarSync = 324,
            CashShopEventInfo = 325,
            BlackList = 326,
            UIOpenTest = 327,
            BlackListView = 328,
            ScrollUpgradeFeverTime = 329,
            TextEquipInfo = 330,
            TextEquipUIOpen = 331,
            UIStarPlanetMiniGameResult = 332,
            UIStarPlanetTrendShop = 333,
            UIStarPlanetQueue = 334,
            UIStarPlanetQueueErr = 335,
            StarPlanetRoundInfo = 336,
            StarPlanetResult = 337,
            BackSpeedCtrl = 338,
            SetMazeArea = 339,
            CharacterBurning = 340,
            BattleStatCoreInfo = 341,
            BattleStatCoreAck = 342,
            GachaponRewardTestResult = 343,
            MasterPieceTestRewardResult = 344,
            RoyalStyleTestRewardResult = 345,
            BeautyCouponTestRewardResult = 346,
            NickSkillExpired = 347,
            RandomMissionResult = 348,
            TresureResult = 349,
            TresureJumpHigh = 350,
            ItemCollection_SetFlag = 351,
            ItemCollection_CheckComplete = 352,
            ItemCollection_SendCollectionList = 353,
            ToadsHammerRequestResult = 354,
            HyperStatSkillResetResult = 355,
            InventoryOperationResult = 356,
            GetSavedUrusSkill = 357,
            SetRolePlayingCharacterInfo = 358,
            MVPAlarm = 359,
            MonsterCollecion_CompleteReward_Result = 360,
            UserTowerChairSettingResult = 361,
            NeedClientResponse = 362,
            CharacterModified = 363,
            TradeKingShopItem = 365,
            TradeKingShopRes = 366,
            PlatFormarEnterResult = 368,
            PlatFormarOxyzen = 369,
            VMatrixUpdate = 372,
            NodeStoneResult = 373,
            NodeShardResult = 375,
            UserGenderResult = 399,
            GuildBBSResult = 400,
            MonsterBookCardData = 407,
            GMPolice = 414,
            CommerciResult = 429,
            MagicWheelResult = 434,
            RewardResult = 435,
            RemoveDamageSkinResult = 440,
            MacroSysDataInit = 453,
            EndCharacterData = 453,
            BeginStage = 452,
            SetField = 452,
            SetAuctionField = 453,
            SetFarmField = 454,
            SetCashShop = 456,
            EndStage = 456,
            BeginField = 457,
            TransferFieldReqIgnored = 457,
            TransferChannelReqIgnored = 458,
            TransferPvpReqIgnored = 459,
            FieldSpecificData = 460,
            GroupMessage = 461,
            FieldUniverseMessage = 462,
            Whisper = 463,
            MobSummonItemUseResult = 464,
            FieldEffect = 465,
            BlowWeather = 473,
            PlayJukeBox = 474,
            AdminResult = 475,
            Quiz = 476,
            Desc = 477,
            Clock = 479,
            ContiMove = 480,
            ContiState = 481,
            SetQuestClear = 482,
            SetQuestTime = 483,
            SetObjectState = 484,
            DestroyClock = 485,
            ShowArenaResult = 486,
            StalkResult = 487,
            MassacreIncGauge = 488,
            MassacreResult = 489,
            QuickslotMappedInit = 490,
            FootHoldMove = 491,
            CorrectFootHoldMove = 492,
            DynamicObjMove = 493,
            DynamicObjShowHide = 494,
            DynamicObjUrusSync = 495,
            FieldKillCount = 496,
            SmartMobNoticeMsg = 497,
            MobPhaseChange = 498,
            MobZoneChange = 499,
            MobOrderFromSvr = 500,
            PvPStatusResult = 501,
            InGameCurNodeEventEnd = 502,
            ForceAtomCreate = 503,
            SetAchieveRate = 505,
            SetQuickMoveInfo = 506,
            ChangeAswanSiegeWeaponGauge = 507,
            ObtacleAtomCreate = 508,
            ObtacleAtomClear = 509,
            Box2dFootHoldCreate = 510,
            DebuffObjOn = 511,
            FieldCreateFallingCatcher = 512,
            MobChaseEffectSet = 513,
            MesoExchangeResult = 514,
            SetMirrorDungeonInfo = 515,
            SetIntrusion = 516,
            CannotDropForTradeBlock = 517,
            FootHoldOnOff = 518,
            LadderRopeOnOff = 519,
            MomentAreaOnOff = 520,
            MomentAreaOnOffAll = 521,
            ChatLetClientConnect = 522,
            ChatInduceClientConnect = 523,
            CoordinationContestResult = 524,
            EliteState = 525,
            PlaySound = 526,
            StackEventGauge = 527,
            SetUnionField = 528,
            MTalkOfflineAccountFriendsNameResult = 529,
            StarPlanetBurningTimeInfo = 530,
            PublicShareStateValue = 532,
            FunctionTempBlock = 533,
            StatusBar = 534,
            FieldSkillDelay = 535,
            FieldWeather_Add = 536,
            FieldWeather_Remove = 537,
            FieldWeather_Msg = 538,
            AddWreckage = 539,
            DeleteWreckage = 540,
            CreateMirrorImage = 541,
            FuntionFootholdMan = 542,
            BeginUserPool = 546,
            UserEnterField = 546,
            UserLeaveField = 547,
            BeginUserCommon = 548,
            UserChat = 548,
            UserADBoard = 549,
            UserMiniRoomBalloon = 550,
            UserConsumeItemEffect = 551,
            UserItemUpgradeEffect = 552,
            UserItemEventUpgradeEffect = 553,
            UserItemSkillSocketUpgradeEffect = 554,
            UserItemSkillOptionUpgradeEffect = 555,
            UserItemReleaseEffect = 556,
            UserItemUnreleaseEffect = 557,
            UserItemLuckyItemEffect = 558,
            UserItemMemorialCubeEffect = 559,
            UserItemAdditionalUnReleaseEffect = 560,
            UserItemAdditionalSlotExtendEffect = 561,
            UserItemFireWorksEffect = 562,
            UserItemOptionChangeEffect = 563,
            UserItemRedCubeResult = 565,
            UserHitByUser = 566,
            UserDotByUser = 567,
            UserResetAllDot = 568,
            UserDamageByUser = 569,
            UserTeslaTriangle = 570,
            UserFollowCharacter = 571,
            UserShowPQReward = 572,
            UserSetOneTimeAction = 573,
            UserMakingSkillResult = 574,
            UserMakingMeisterSkillEff = 575,
            UserGatherResult = 576,
            UserExplode = 577,
            UserHitByCounter = 578,
            PyramidLethalAttack = 579,
            UserMixerResult = 580,
            UserWaitQueueReponse = 581,
            UserCategoryEventNameTag = 582,
            UserSetDamageSkin = 583,
            UserSetDamageSkinPremium = 584,
            UserSoulEffect = 585,
            UserSitResult = 586,
            UserStarPlanetPointInfo = 587,
            UserStarPlanetAvatarLookSet = 588,
            UserTossedBySkill = 589,
            UserBattleAttackHit = 590,
            UserBattleUserHitByMob = 591,
            UserFreezeHotEventInfo = 592,
            UserEventBestFriendInfo = 593,
            UserSetReapeatOneTimeAction = 594,
            UserSetReplaceMoveAction = 595,
            UserItemInGameCubeResult = 596,
            UserBattlePvPTemporaryStat = 597,
            UserSetActiveEmoticonItem = 598,
            UserCreatePsychicLock = 599,
            UserRecreatePathPsychicLock = 600,
            UserReleasePsychicLock = 601,
            UserReleasePsychicLockMob = 602,
            UserCreatePsychicArea = 603,
            UserReleasePsychicArea = 604,
            UserRWZeroBunkerMobBind = 605,
            UserBeastFormWingOnOff = 606,
            UserMesoChairAddMeso = 607,
            UserRefreshNameTagMark = 608,
            UserStigmaEffect = 611,
            BeginPet = 617,
            PetActivated = 617,
            PetMove = 618,
            PetAction = 619,
            PetNameChanged = 620,
            PetLoadExceptionList = 622,
            PetHueChanged = 625,
            PetModified = 626,
            PetActionCommand = 627,
            EndPet = 627,
            BeginDragon = 628,
            DragonEnterField = 628,
            DragonMove = 629,
            DragonVanish_Script = 630,
            DragonLeaveField = 631,
            EndDragon = 631,
            BeginAndroid = 632,
            AndroidEnterField = 632,
            AndroidMove = 633,
            AndroidActionSet = 634,
            AndroidModified = 635,
            AndroidLeaveField = 636,
            EndAndroid = 636,
            BeginFoxMan = 637,
            FoxManEnterField = 637,
            FoxManMove = 638,
            FoxManExclResult = 639,
            FoxManShowChangeEffect = 640,
            FoxManModified = 641,
            FoxManLeaveField = 642,
            EndFoxMan = 642,
            BeginSkillPet = 643,
            SkillPetMove = 644,
            SkillPetAction = 645,
            SkillPetState = 646,
            SkillPetNameChanged = 647,
            SkillPetLoadExceptionList = 648,
            SkillPetTransferField = 649,
            EndSkillPet = 650,
            BeginFamiliar = 661,
            FamiliarEnterField = 661,
            FamiliarMove = 662,
            FamiliarAction = 663,
            FamiliarAttack = 664,
            FamiliarNameResult = 665,
            FamiliarTransferField = 666,
            FamiliarFatigueResult = 667,
            EndFamiliar = 667,
            EndUserCommon = 671,
            BeginUserRemote = 672,
            UserMove = 672,
            UserMeleeAttack = 673,
            UserShootAttack = 674,
            UserMagicAttack = 675,
            UserBodyAttack = 676,
            UserSkillPrepare = 677,
            UserMovingShootAttackPrepare = 678,
            UserSkillCancel = 679,
            UserHit = 680,
            UserEmotion = 681,
            AndroidEmotion = 682,
            UserSetActiveEffectItem = 683,
            UserSetActiveMonkeyEffectItem = 684,
            UserSetActiveNickItem = 685,
            UserSetDefaultWingItem = 686,
            UserSetKaiserTransformItem = 687,
            UserSetCustomRiding = 688,
            UserShowUpgradeTombEffect = 689,
            UserSetActivePortableChair = 690,
            UserAvatarModified = 691,
            UserEffectRemote = 692,
            UserTemporaryStatSet = 693,
            UserTemporaryStatReset = 694,
            UserHP = 695,
            UserGuildNameChanged = 696,
            UserGuildMarkChanged = 697,
            UserPvPTeamChanged = 698,
            GatherActionSet = 699,
            UpdatePvPHPTag = 700,
            UserEvanDragonGlide = 701,
            UserKeyDownAreaMove = 702,
            UserLaserInfo = 703,
            UserKaiserColorOrMorphChange = 704,
            UserDestroyGranade = 705,
            UserSetItemAction = 706,
            ZeroTag = 707,
            UserIntrusionEffect = 708,
            ZeroLastAssistState = 709,
            UserSetMoveGrenade = 710,
            SetCustomizeEffect = 711,
            RuneStoneAction = 712,
            KinesisPsychicEnergyShieldEffect = 713,
            DragonAction = 714,
            DragonBreathEarthEffect = 715,
            ReleaseRWGrab = 716,
            RWMultiChargeCancelRequest = 717,
            ScouterMaxDamageUpdate = 718,
            StigmaDeliveryResponse = 719,
            EndUserRemote = 726,
            BeginUserLocal = 718,
            UserEmotionLocal = 718,
            AndroidEmotionLocal = 719,
            UserEffectLocal = 720,
            UserTeleport = 721,
            MesoGiveSuceeded = 723,
            MesoGiveFailed = 724,
            UserQuestResult = 732,
            NotifyHPDecByField = 733,
            UserPetSkillChanged = 734,
            UserBalloonMsg = 735,
            PlayEventSound = 736,
            PlayMinigameSound = 737,
            UserMakerResult = 738,
            UserOpenConsultBoard = 739,
            UserOpenClassCompetitionPage = 740,
            UserOpenUI = 741,
            UserCloseUI = 742,
            UserOpenUIWithOption = 743,
            UserOpenWebUI = 744,
            UserSetDirectionMode = 745,
            UserSetInGameDirectionMode = 746,
            UserSetStandAloneMode = 747,
            UserHireTutor = 746,
            UserTutorMsg = 747,
            HireTutorById = 750,
            UserSetPartner = 751,
            UserSetPartnerAction = 752,
            UserSetPartnerForceFlip = 753,
            UserSwitchRP = 754,
            ModCombo = 755,
            IncComboByComboRecharge = 756,
            SetRadioSchedule = 757,
            UserOpenSkillGuide = 758,
            UserNoticeMsg = 759,
            UserChatMsg = 760,
            UserSetUtilDlg = 761,
            UserBuffzoneEffect = 762,
            UserTimeBombAttack = 763,
            UserExplosionAttack = 764,
            UserPassiveMove = 765,
            UserFollowCharacterFailed = 766,
            UserRequestExJablin = 767,
            CreateNewCharacterResultPremiumAdventurer = 768,
            GatherRequestResult = 769,
            RuneStoneUseAck = 770,
            UserBagItemUseResult = 771,
            RandomTeleportKey = 772,
            SetGagePoint = 773,
            UserInGameDirectionEvent = 774,
            MedalReissueResult = 775,
            DodgeSkillReady = 777,
            RemoveMicroBuffSkill = 778,
            UserPlayMovieClip = 779,
            RewardMobListResult = 780,
            IncJudgementStack = 781,
            IncCharmByCashPRMsg = 782,
            SetBuffProtector = 783,
            ChangeLarknessStack = 784,
            DetonateBomb = 785,
            AggroRankInfo = 786,
            DeathCountInfo = 787,
            IndividualDeathCountInfo = 788,
            UserSetDressUpState = 789,
            UserSeverAckMobZoneStateChange = 790,
            SummonEventRank = 791,
            SummonEventReward = 792,
            UserRandomEmotion = 793,
            UserFlipTheCoinEnabled = 794,
            UserTrickOrTreatResult = 795,
            UserGiantPetBuff = 796,
            UserB2BodyResult = 797,
            SetDead = 798,
            OpenUIOnDead = 799,
            ExpiredNotice = 800,
            UserLotteryItemUIOpen = 801,
            UserRouletteStart = 802,
            UserSitOnTimeCapsule = 803,
            UserSitOnDummyChair = 804,
            UserGoMonsterFarm = 805,
            UserMonsterLifeInviteItemResult = 806,
            PhotoGetResult = 807,
            UserFinalAttackRequest = 808,
            UserSetGun = 809,
            UserSetAmmo = 810,
            UserCreateGun = 811,
            UserClearGun = 812,
            UserShootAttackInFPSMode = 813,
            MirrorDungeonEnterFail = 814,
            MirrorDungeonUnitCleared = 815,
            MirrorDungeonDetail = 816,
            MirrorDungeonRecord = 817,
            UserOpenURL = 818,
            ZeroCombatRecovery = 819,
            MirrorStudyUIOpen = 820,
            SkillCooltimeReduce = 821,
            MirrorReadingUIOpen = 822,
            UserControlMobSkillPushAck = 823,
            ZeroLevelUpAlram = 824,
            UserControlMobSkillPopAck = 825,
            UserControlMobSkillFail = 826,
            SummonedForceRemove = 827,
            UserRespawn = 828,
            UserControlMobSkillForcedPopAck = 829,
            IsUniverse = 830,
            PortalGroupAck = 831,
            SetMovable = 832,
            UserControlMobSkillPushCoolTime = 833,
            MoveParticleEff = 834,
            UserDoActiveEventSkillByScript = 835,
            SetStatusBarJobNameBlur = 836,
            RuneStoneSkillAck = 837,
            RuneStoneOverTime = 838,
            MoveToContents_CannotMigrate = 839,
            PlayAmbientSound = 841,
            StopAmbientSound = 842,
            FlameWizardElementFlameSummon = 843,
            UserCameraMode = 844,
            SpotlightToCharacter = 845,
            CheckBossPartyByScriptDone = 846,
            FreeLookChangeUIOpen = 847,
            FreeLookChangeSuccess = 848,
            SetGrayBackGround = 849,
            GetNpcCurrentAction = 850,
            CameraRotation = 851,
            CameraSwitch = 852,
            CameraCtrlMsg = 853,
            UserSetFieldFloating = 854,
            AddPopupSay = 855,
            RemovePopupSay = 856,
            JaguarSkill = 857,
            NpcActionLayerRelmove = 858,
            UserClientResolutionRequest = 859,
            UserBonusAttackRequest = 860,
            UserRandAreaAttackRequest = 861,
            JaguarActive = 862,
            SkillCooltimeSetM = 863,
            SetCarryReactorInfo = 864,
            ReactorSkillUseRequest = 865,
            OpenBattlePvPChampSelectUI = 866,
            BattlePvPItemDropSound = 867,
            SetPointForMiniGame = 868,
            PlantPotClickResult = 869,
            PlantPotEffect = 870,
            UserFixDamage = 871,
            RoyalGuardAttack = 872,
            DoActivePsychicArea = 873,
            UserEnterFieldPsychicInfo = 874,
            UserLeaveFieldPsychicInfo = 875,
            TouchMeStateResult = 876,
            UrusFieldScoreUpdate = 877,
            UrusReusltUIOpen = 878,
            UrusNoMoreLife = 879,
            RegisterTeleport = 880,
            UserCreateAreaDotInfo = 882,
            SetSlownDown = 885,
            RegisterExtraSkill = 886,
            ResWarriorLiftMobInfo = 887,
            UserRenameResult = 888,
            UserDamageSkinSaveResult = 889,
            UserLocalStigmaStackDuration = 890,
            FamiliarRegister = 903,// Version 187
            SalonResult = 942,
            ModHayatoCombo = 946,
            SkillCooltimeSet = 962,
            EndUserLocal = 962,
            EndUserPool = 963,
            BeginSummoned = 963,
            SummonedEnterField = 963,
            SummonedLeaveField = 965,
            SummonedMove = 966,
            SummonedAttack = 967,
            SummonedAttackPvP = 968,
            SummonedSetReference = 969,
            SummonedSkill = 970,
            SummonedSkillPvP = 972,
            SummonedHPTagUpdate = 973,
            SummonedAttackDone = 974,
            SummonedSetAbleResist = 975,
            SummonedAction = 976,
            SummonedAssistAttackRequest = 977,
            SummonedAttackActive = 978,
            SummonedBeholderRevengeAttack = 979,
            SummonedHit = 986,
            EndSummoned = 986,
            BeginMobPool = 987,
            MobEnterField = 987,
            MobLeaveField = 988,
            MobChangeController = 989,
            MobSetAfterAttack = 990,
            MobBlockAttack = 991,
            UrusOnlyMobSkill = 992,
            BeginMob = 993,
            MobMove = 993,
            MobCtrlAck = 994,
            MobCtrlHint = 995,
            MobStatSet = 996,
            MobStatReset = 997,
            MobSuspendReset = 998,
            MobAffected = 999,
            MobDamaged = 1000,
            MobSpecialEffectBySkill = 1003,
            MobHPChange = 1004,
            MobCrcKeyChanged = 1005,
            MobCrcDataRequest = 1006,
            MobHPIndicator = 1007,
            MobCatchEffect = 1008,
            MobStealEffect = 1009,
            MobEffectByItem = 1010,
            MobSpeaking = 1011,
            MobMessaging = 1012,
            MobSkillDelay = 1013,
            MobRequestResultEscortInfo = 1014,
            MobEscortStopEndPermmision = 1015,
            MobEscortStopByScript = 1016,
            MobEscortStopSay = 1017,
            MobEscortReturnBefore = 1018,
            MobNextAttack = 1019,
            MobTeleport = 1021,
            MobForcedAction = 1022,
            MobForcedSkillAction = 1023,
            MobTimeResist = 1025,
            MobAllKill = 1026,
            MobAttackBlock = 1027,
            MobAttackPriority = 1028,
            MobAttackTimeInfo = 1029,
            MobDamageShareInfoToLocal = 1030,
            MobDamageShareInfoToRemote = 1031,
            BreakDownTimeZoneTimeOut = 1032,
            MoveAreaSet = 1033,
            MobDoSkillByHit = 1034,
            CastingBarSkill = 1035,
            MobFlyTarget = 1036,
            BounceAttackSkill = 1037,
            MobAffectedAreaByHit = 1038,
            MobLtRbDamageSkill = 1039,
            MobSummonSubBodySkill = 1040,
            MobLaserControl = 1041,
            MobScale = 1045,
            MobSpecialAction = 1046,
            MobPartSystem = 1047,
            MobForceChase = 1048,
            MobHangOver = 1049,
            MobHangOverRelease = 1050,
            MobDeadFPSMode = 1051,
            MobAirHit = 1052,
            MobDemianDelayedAttackCreate = 1053,
            RegisterMobZone = 1054,
            UnregisterMobZone = 1055,
            SetNextTargetFromSvr = 1056,
            MobAttackedByMob = 1057,
            EndMob = 1068,
            EndMobPool = 1069,
            BeginNpcPool = 1071,
            NpcEnterField = 1071,
            NpcLeaveField = 1072,
            NpcEnterFieldForQuickMove = 1073,
            NpcChangeController = 1074,
            BeginNpc = 1078,
            NpcMove = 1078,
            NpcUpdateLimitedInfo = 1079,
            NpcShowQuizScore = 1080,
            NpcShowQuizScoreAni = 1081,
            ForceMoveByScript = 1082,
            ForceFlipByScript = 1083,
            NpcEmotion = 1085,
            NpcCharacterBaseAction = 1086,
            NpcViewOrHide = 1087,
            NpcPresentTimeSet = 1089,
            NpcSpecialActionReset = 1090,
            NpcSetScreenInfo = 1091,
            NpcLocalRepeatEffect = 1092,
            NpcSetNoticeBoardInfo = 1093,
            NpcSpecialAction = 1094,
            BeginNpcTemplate = 1096,
            NpcSetScript = 1096,
            EndNpcTemplate = 1096,
            EndNpcPool = 1097,
            BeginEmployeePool = 1098,
            EmployeeEnterField = 1098,
            EmployeeLeaveField = 1099,
            EmployeeMiniRoomBalloon = 1100,
            EndEmployeePool = 1100,
            BeginDropPool = 1101,
            DropEnterField = 1101,
            DropLeaveField = 1103,
            EndDropPool = 1103,
            BeginMessageBoxPool = 1104,
            CreateMessageBoxFailed = 1104,
            MessageBoxEnterField = 1105,
            MessageBoxLeaveField = 1106,
            EndMessageBoxPool = 1106,
            BeginAffectedAreaPool = 1107,
            AffectedAreaCreated = 1107,
            MobSkillInstalledFire = 1108,
            AffectedAreaRemoved = 1109,
            EndAffectedAreaPool = 1109,
            BeginTownPortalPool = 1110,
            TownPortalCreated = 1110,
            TownPortalRemoved = 1111,
            EndTownPortalPool = 1111,
            BeginRandomPortalPool = 1112,
            RandomPortalCreated = 1112,
            RandomPortalTryEnterRequest = 1113,
            RandomPortalRemoved = 1114,
            EndRandomPortalPool = 1114,
            BeginOpenGatePool = 1115,
            OpenGateCreated = 1115,
            OpenGateClose = 1116,
            OpenGateRemoved = 1117,
            EndOpenGatePool = 1117,
            BeginReactorPool = 1118,
            ReactorChangeState = 1118,
            ReactorMove = 1119,
            ReactorEnterField = 1120,
            ReactorStateReset = 1121,
            ReactorOwnerInfo = 1122,
            ReactorRemove = 1123,
            ReactorLeaveField = 1124,
            EndReactorPool = 1124,
            BeginFishingZonePool = 1125,
            FishingInfo = 1125,
            FishingReward = 1126,
            FishingZoneInfo = 1127,
            EndFishingZonePool = 1127,
            BeginPersonalMapObject = 1128,
            DecomposerEnterField = 1128,
            DecomposerLeaveField = 1129,
            EndPersonalMapObject = 1129,
            RuneEnterField = 1206,// Version 187
            RuneLeaveField = 1207,// Version 187
            BeginScript = 1410,
            ScriptMessage = 1410,
            EndScript = 1410,
            BeginShop = 1411,
            OpenShopDlg = 1411,
            ShopResult = 1412,
            EndShop = 1412,
            GachaperriotRequest = 1413,
            GachaperriotResult = 1414,
            TrunkResult = 1437,
            BeginStoreBank = 1438,
            StoreBankGetAllResult = 1438,
            StoreBankResult = 1439,
            EndStoreBank = 1439,
            RPSGame = 1440,
            GSRPSGame = 1441,
            StarPlanet_GSRPSGame = 1442,
            Messenger = 1443,
            MiniRoom = 1444,
            SetCashShopInitialItem = 1445,
            TryMigrateCashShop = 1446,
            BeginFuncKeyMapped = 1576,
            FuncKeyMappedInit = 1576,
            PetConsumeItemInit = 1577,
            PetConsumeMPItemInit = 1578,
            PetConsumeCureItemInit = 1579,
            PetBuff = 1580,
            EndFuncKeyMapped = 1580,
            BeginGoldenHammer = 1581,
            GoldenHammerResult = 1582,
            EndGoldenHammer = 1583,
            BeginEgoEquip = 1598,
            EgoEquipGaugeCompleteReturn = 1599,
            EgoEquipCreateUpgradeItemCostInfo = 1600,
            EgoEquipCheckUpgradeItemResult = 1601,
            EgoEquipItemUpgradeEffect = 1602,
            EndEgoEquip = 1603,
            BeginInheritance = 1604,
            InheritanceInfo = 1605,
            InheritanceComplete = 1606,
            EndInheritance = 1607,
            BeginMirrorReading = 1608,
            MirrorReadingSelectBookResult = 1609,
            EndMirrorReading = 1610,
            BeginFieldAttackObjPool = 1611,
            FieldAttackObjCreate = 1611,
            FieldAttackObjRemoveBySingleKey = 1612,
            FieldAttackObjRemoveByList = 1613,
            FieldAttackObjRemoveAll = 1614,
            FieldAttackObjStart = 1615,
            FieldAttackObjSetAttack = 1616,
            FieldAttackObjSetOwner = 1617,
            FieldAttackObjResetOwner = 1618,
            FieldAttackObjPushAct = 1629,
            EndFieldAttackObjPool = 1620,
            BeginBattleRecord = 1622,
            BattleRecordDotDamageInfo = 1623,
            BattleRecordKillDamageInfo = 1624,
            BattleRecordBattleDamageInfo = 1625,
            BattleRecordRequestResult = 1626,
            BattleRecordAggroInfo = 1627,
            BattleRecordSkillDamageLog = 1628,
            EndBattleRecord = 1629,
            SocketCreateResult = 1632,
            ViciousHammerResult = 1634;
}