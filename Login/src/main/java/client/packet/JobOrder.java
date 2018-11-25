package client.packet;


import net.packet.OutPacket;

public class JobOrder {

    public static final boolean bEnabled = true; //enable race select
    public static final byte nOrder = 6;

    public static void Encode(OutPacket oPacket) {
        oPacket.EncodeBool(bEnabled);
        oPacket.EncodeByte(nOrder);
        for (Categories cat : Categories.values()) {
            oPacket.EncodeBool(cat.IsEnabled());
            oPacket.EncodeShort(cat.GetGender());
        }
    }

    public static boolean IsJobCategory(int jobid, int category) {
        return jobid / 100 % 10 == category; // divide by 100 and get last digit
    }

    public static enum Categories {

        Resistance(0, true, 2),
        Adventurer(1, true, 2),
        Cygnus(2, true, 2),
        Aran(3, true, 2),
        Evan(4, true, 2),
        Mercedes(5, true, 1),
        Demon(6, true, 0),
        Phantom(7, true, 0),
        DualBlade(8, true, 2),
        Mihile(9, true, 2),
        Luminous(10, true, 0),
        Kaiser(11, true, 0),
        AngelicBuster(12, true, 1),
        Cannoneer(13, true, 2),
        Xenon(14, true, 2),
        Zero(15, true, 2),
        Shade(16, true, 0),
        Jett(17, true, 2),
        Hayato(18, true, 0),
        Kanna(19, true, 1),
        BeastTamer(20, true, 1),
        PinkBean(21, true, 0),
        Kinesis(22, true, 0);

        private final int nCategory;
        private boolean bEnabled;
        private int nGender;

        private Categories(int nCategory, boolean bEnabled, int nGender) {
            this.nCategory = nCategory;
            this.bEnabled = bEnabled;
            this.nGender = nGender;
        }

        public int GetJobCategory() {
            return nCategory;
        }

        public boolean IsEnabled() {
            return bEnabled;
        }

        public void Disable() {
            this.bEnabled = false;
        }

        public void Enable() {
            this.bEnabled = false;
        }

        public short GetGender() {
            return (short) this.nGender;
        }

        public void SetGender(int nGender) {
            this.nGender = nGender;
        }

        public int GetValue() {
            return this.nCategory;
        }

        public static Categories GetByID(int nCategory) {
            for (Categories pCategory : Categories.values()) {
                if (pCategory.GetValue() == nCategory) {
                    return pCategory;
                }
            }
            return null;
        }
    }
}
