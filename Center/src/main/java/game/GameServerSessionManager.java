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
package game;

import game.packet.LoopBackPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;

import login.packet.Login;
import net.packet.InPacket;
import net.packet.OutPacket;
import server.Configuration;


/**
 * @author Kaz Voeten
 */
public class GameServerSessionManager extends ChannelInboundHandlerAdapter {

    public static ArrayList<GameServerSocket> aSessions = new ArrayList<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        int uSeqSend = 0;
        int uSeqRecv = 0;

        GameServerSocket pClient = new GameServerSocket(ch, uSeqSend, uSeqRecv);
        pClient.bEncryptData = false;
        ch.attr(GameServerSocket.SESSION_KEY).set(pClient);
        aSessions.add(pClient);

        OutPacket oPacket = new OutPacket(LoopBackPacket.WorldInformation);
        oPacket.EncodeInt(Configuration.GetConfig().WORLD_ID);
        oPacket.EncodeString(Configuration.GetConfig().WORLD_NAME);

        System.out.printf("[Debug] GameServer connected! IP: %s nChannelID: %s%n", pClient.GetIP(), pClient.nChannelID);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        GameServerSocket pClient = (GameServerSocket) ch.attr(GameServerSocket.SESSION_KEY).get();
        aSessions.remove(pClient);
        Login.GameServerInformation();
        System.out.printf("[Debug] GameServer disconnected! IP: %s nChannelID: %s%n", pClient.GetIP(), pClient.nChannelID);

        pClient.Close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object in) {
        Channel ch = ctx.channel();

        GameServerSocket pClient = (GameServerSocket) ch.attr(GameServerSocket.SESSION_KEY).get();
        InPacket iPacket = (InPacket) in;

        pClient.ProcessPacket(iPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        GameServerSocket client = (GameServerSocket) ctx.channel().attr(GameServerSocket.SESSION_KEY).get();
        if (client != null) {
            client.Close();
        }
    }
}
