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
package server.center;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.InPacket;

/**
 *
 * @author Kaz Voeten
 */
public class CenterSessionManager extends ChannelInboundHandlerAdapter {

    public static ArrayList<CenterSocket> aCenterSessions = new ArrayList<>();

    public static CenterSocket GetByWorldID(int nWorldID) {
        for (CenterSocket pSocket : aCenterSessions) {
            if (pSocket.nWorldID == nWorldID) {
                return pSocket;
            }
        }
        return null;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        CenterSocket pCenter = new CenterSocket(ch, 0, 0);
        pCenter.bEncryptData = false;
        ch.attr(CenterSocket.SESSION_KEY).set(pCenter);
        aCenterSessions.add(pCenter);

        System.out.printf("[Debug] Center Server connected with %s%n", pCenter.GetIP());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        CenterSocket pCenter = (CenterSocket) ch.attr(CenterSocket.SESSION_KEY).get();
        aCenterSessions.remove(pCenter);
        pCenter.Close();

        System.out.printf("[Debug] Closed Center Server session with %s.%n", pCenter.GetIP());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object in) {
        Channel ch = ctx.channel();
        CenterSocket pCenter = (CenterSocket) ch.attr(CenterSocket.SESSION_KEY).get();
        InPacket iPacket = (InPacket) in;
        try {
            pCenter.ProcessPacket(iPacket);
        } catch(Exception ex) {
            Logger.getLogger(CenterSessionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        t.printStackTrace();
        CenterSocket pCenter = (CenterSocket) ctx.channel().attr(CenterSocket.SESSION_KEY).get();
        if (pCenter != null) {
            pCenter.Close();
        }
    }
}
