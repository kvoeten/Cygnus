/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.packet;

/**
 *
 * @author kaz_v
 */
public enum Jobs {
    Beginner(0),
    Warrior(100),
    Fighter(110),
    Crusader(111),
    Hero(112),
    Page(120),
    WhiteKnight(121),
    Paladin(122),
    Spearman(130),
    DragonKnight(131),
    DarkKnight(132),
    Magician(200),
    FirePoisonWizard(210),
    FirePoisonMage(211),
    FirePoisonArchMage(212),
    IceLightningWizard(220),
    IceLightningMage(221),
    IceLightningArchMage(222),
    Cleric(230),
    Priest(231),
    Bishop(232),
    Bowman(300),
    Hunter(310),
    Ranger(311),
    Bowmaster(312),
    Crossbowman(320),
    Sniper(321),
    Marksman(322),
    Thief(400),
    Assassin(410),
    Hermit(411),
    Nightlord(412),
    Bandit(420),
    ChiefBandit(421),
    Shadower(422),
    BladeRecruit(430),
    BladeAcolyte(431),
    BladeSpecialist(432),
    BladeLord(433),
    BladeMaster(434),
    Pirate(500),
    Brawler(510),
    Marauder(511),
    Buccaneer(512),
    Gunslinger(520),
    Outlaw(521),
    Corsair(522),
    CannonShooter(501),
    Connoneer(530),
    CannonBlaster(531),
    CannonMaster(532),
    Zett(508),
    Zett_2(570),
    Zett_3(571),
    Zett_4(572),
    Manager(800),
    GM(900),
    SuperGM(910),
    Noblesse(1000),
    DawnWarrior(1100),
    DawnWarrior_2(1110),
    DawnWarrior_3(1111),
    DawnWarrior_4(1112),
    BlazeWizard(1200),
    BlazeWizard_2(1210),
    BlazeWizard_3(1211),
    BlazeWizard_4(1212),
    WindArcher(1300),
    WindArcher_2(1310),
    WindArcher_3(1311),
    WindArcher_4(1312),
    NightWalker(1400),
    NightWalker_2(1410),
    NightWalker_3(1411),
    NightWalker_4(1412),
    ThunderBreaker(1500),
    ThunderBreaker_2(1510),
    ThunderBreaker_3(1511),
    ThunderBreaker_4(1512),
    Legend(2000),
    Aran(2100),
    Aran_2(2110),
    Aran_3(2111),
    Aran_4(2112),
    Evan(2200),
    Evan_1(2210),
    Evan_2(2211),
    Evan_3(2212),
    Evan_4(2213),
    Evan_5(2214),
    Evan_6(2215),
    Evan_7(2216),
    Evan_8(2217),
    Evan_9(2218),
    Evan_10(2219),
    Mercedes(2002),
    Mercedes_1(2300),
    Mercedes_2(2310),
    Mercedes_3(2311),
    Mercedes_4(2312),
    Phantom(2003),
    Phantom_1(2400),
    Phantom_2(2410),
    Phantom_3(2411),
    Phantom_4(2412),
    Luminous(2004),
    Luminous_1(2700),
    Luminous_2(2710),
    Luminous_3(2711),
    Luminous_4(2712),
    Shade(2005),
    Shade_1(2500),
    Shade_2(2510),
    Shade_3(2511),
    Shade_4(2512),
    DemonSlayer(3001),
    DemonSlayer_1(3100),
    DemonSlayer_2(3110),
    DemonSlayer_3(3111),
    DemonSlayer_4(3112),
    DemonAvenger_1(3101),
    DemonAvenger_2(3120),
    DemonAvenger_3(3121),
    DemonAvenger_4(3122),
    Citizen(3000),
    BattleMage_1(3200),
    BattleMage_2(3210),
    BattleMage_3(3211),
    BattleMage_4(3212),
    WildHunter_1(3300),
    WildHunter_2(3310),
    WildHunter_3(3311),
    WildHunter_4(3312),
    Mechanic_1(3500),
    Mechanic_2(3510),
    Mechanic_3(3511),
    Mechanic_4(3512),
    Xenon(3002),
    Xenon_1(3600),
    Xenon_2(3610),
    Xenon_3(3611),
    Xenon_4(3612),
    Xenon_5(3613),
    Hayato(4001),
    Hayato_1(4100),
    Hayato_2(4110),
    Hayato_3(4111),
    Hayato_4(4112),
    Kanna(4002),
    Kanna_1(4200),
    Kanna_2(4210),
    Kanna_3(4211),
    Kanna_4(4212),
    NamelessWarden(5000),
    Mihile_1(5100),
    Mihile_2(5110),
    Mihile_3(5111),
    Mihile_4(5112),
    Kaiser(6000),
    Kaiser_1(6100),
    Kaiser_2(6110),
    Kaiser_3(6111),
    Kaiser_4(6112),
    AngelicBuster(6001),
    AngelicBuster_1(6500),
    AngelicBuster_2(6510),
    AngelicBuster_3(6511),
    AngelicBuster_4(6512),
    ADDITIONAL_SKILLS(9000),
    ADDITIONAL_SKILLS1(9100),
    ADDITIONAL_SKILLS2(9200),
    ADDITIONAL_SKILLS3(9201),
    ADDITIONAL_SKILLS4(9202),
    ADDITIONAL_SKILLS5(9203),
    ADDITIONAL_SKILLS6(9204),
    ADDITIONAL_SKILLS7(9500),
    Zero_0(10000), // This job is not used, for client reference only, zero starts at 10112
    Zero_1(10100), // This job is not used, for client reference only, zero starts at 10112
    Zero_2(10110), // This job is not used, for client reference only, zero starts at 10112
    Zero_3(10111), // This job is not used, for client reference only, zero starts at 10112
    Zero(10112),
    BeastTamer_0(11200), // same as zero
    BeastTamer_1(11210),
    BeastTamer_2(11211),
    BeastTamer(11212),
    PinkBean(13000),
    PinkBean_1(13100),
    Kinesis(14000),
    Kinesis_1(14200),
    Kinesis_2(14210),
    Kinesis_3(14211),
    Kinesis_4(14212);

    private int nJobID;

    private Jobs(int nJobID) {
        this.nJobID = nJobID;
    }

    public int getID() {
        return this.nJobID;
    }

    public Jobs getByID(int nJob) {
        for (Jobs job : Jobs.values()) {
            if (job.getID() == nJob) {
                return job;
            }
        }
        return null;
    }

    public static boolean isLegalBeginnerJob(int nJob) {
        return nJob == 0
                || nJob == 1000
                || nJob == 2000
                || nJob == 2001
                || nJob == 2002
                || nJob == 2003
                || nJob == 2004
                || nJob == 2005
                || nJob == 3000
                || nJob == 3001
                || nJob == 3002
                || nJob == 4001
                || nJob == 4002
                || nJob == 5000
                || nJob == 6000
                || nJob == 6001
                || nJob == 10112
                || nJob == 11212
                || nJob == 14000;
    }

}
