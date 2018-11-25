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
package login.packet;

/**
 * @author Kaz Voeten
 */
public class LoopBackPacket {

    public static final short AliveAck = 0,
            AccountInformation = 1,
            WorldInformation = 2,
            ChannelInformation = 3,
            CheckDuplicatedIDResponse = 4,
            OnCreateCharacterResponse = 5,
            SetSPWResult = 6,
            BlockList = 7,
            DeleteCharacterResult = 8;
}
