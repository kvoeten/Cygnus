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
package net.packet;

import java.util.Date;

/**
 * @author Kaz Voeten
 */
public class FileTime {

    public int ftHighTime, ftLowTime;

    public FileTime(boolean bMaxTime) {
        long u64BitLong = bMaxTime ? 150842304000000000L : 94354848000000000L; //Max: 1/1/2079, Min: 1/1/1900
        this.ftHighTime = (int) (u64BitLong >> 32);
        this.ftLowTime = (int) u64BitLong;
    }

    public void Encode(OutPacket oPacket) {
        oPacket.EncodeInt(ftHighTime);
        oPacket.EncodeInt(ftLowTime);
    }

    public FileTime(Date pDate) {
        long u64BitLong = pDate.getTime();

        u64BitLong += 11644473600000L; //add time between 1601 and 1970
        u64BitLong *= 10000L; //multiply time to nanoseconds

        this.ftHighTime = (int) (u64BitLong >> 32);
        this.ftLowTime = (int) u64BitLong;
    }

    public static Date GetDate(int ftHighTime, int ftLowTime) {

        long u64BitLong = (long) ftHighTime << 32 | ftLowTime & 0xffffffffL;

        u64BitLong /= 10000L; //devide time to ms from ns
        u64BitLong -= 11644473600000L; //remove time between 1601 and 1970

        return new Date(u64BitLong);
    }
}
