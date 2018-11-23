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
package auth;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.InPacket;

/**
 *
 * @author Kaz Voeten
 */
public class AuthServerSessionManager extends ChannelInboundHandlerAdapter {

    public static AuthServerSocket pSession = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        AuthServerSocket pClient = new AuthServerSocket(ch, 0, 0);
        pClient.bEncryptData = false;
        ch.attr(AuthServerSocket.SESSION_KEY).set(pClient);
        pSession = pClient;

        System.out.printf("[Debug] Connected to Auth Server at adress: %s%n", pClient.GetIP());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        AuthServerSocket pClient = (AuthServerSocket) ch.attr(AuthServerSocket.SESSION_KEY).get();
        pSession = null;

        pClient.Close();
        System.out.println("[Debug] Disconnected from the Auth Server");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object in) {
        Channel ch = ctx.channel();
        AuthServerSocket pClient = (AuthServerSocket) ch.attr(AuthServerSocket.SESSION_KEY).get();
        InPacket iPacket = (InPacket) in;
        pClient.ProcessPacket(iPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        Logger.getLogger(AuthServerSessionManager.class.getName()).log(Level.SEVERE, null, t);
        AuthServerSocket client = (AuthServerSocket) ctx.channel().attr(AuthServerSocket.SESSION_KEY).get();
        if (client != null) {
            client.Close();
        }
    }
}
