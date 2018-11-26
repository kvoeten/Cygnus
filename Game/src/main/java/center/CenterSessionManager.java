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
package center;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

import center.packet.LoopBackPacket;
import net.packet.InPacket;
import net.packet.OutPacket;

import server.Configuration;

/**
 * @author Kaz Voeten
 */
public class CenterSessionManager extends ChannelInboundHandlerAdapter {

    public static CenterSocket pSession;
    private static final Random rand = new Random();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        CenterSocket pClient = new CenterSocket(ch, 0, 0);
        ch.attr(CenterSocket.SESSION_KEY).set(pClient);
        pClient.bEncryptData = false;
        pSession = pClient;

        System.out.printf("[Debug] Connected to Center Server at adress: %s%n", pClient.GetIP());

        OutPacket oPacket = new OutPacket(LoopBackPacket.GameServerInformation);
        oPacket.EncodeByte(Configuration.CHANNEL_ID);
        oPacket.EncodeInt(Configuration.MAXIMUM_CONNECTIONS);
        oPacket.EncodeInt(Configuration.PORT);
        pClient.SendPacket(oPacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        CenterSocket pClient = (CenterSocket) ch.attr(CenterSocket.SESSION_KEY).get();
        pSession = null;

        pClient.Close();
        System.out.println("[Debug] Disconnected from the Center Server");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object in) {
        Channel ch = ctx.channel();

        CenterSocket pClient = (CenterSocket) ch.attr(CenterSocket.SESSION_KEY).get();
        InPacket iPacket = (InPacket) in;

        pClient.ProcessPacket(iPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        t.printStackTrace();
        CenterSocket client = (CenterSocket) ctx.channel().attr(CenterSocket.SESSION_KEY).get();
        if (client != null) {
            client.Close();
        }
    }
}
