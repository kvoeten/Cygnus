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
package client;

import center.CenterSessionManager;
import center.CenterSocket;
import center.packet.Center;
import client.packet.Login;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.InPacket;
import net.OutPacket;
import server.APIFactory;

import server.Configuration;

/**
 *
 * @author Kaz Voeten
 */
public class ClientSessionManager extends ChannelInboundHandlerAdapter {

    public static long nSessionID = 0;
    public static ArrayList<ClientSocket> aSessions = new ArrayList<>();
    private static final Random rand = new Random();

    public static ClientSocket GetSessionByID(long nSessionID) {
        for (ClientSocket pSocket : aSessions) {
            if (pSocket.nSessionID == nSessionID) {
                return pSocket;
            }
        }
        return null;
    }

    public static ClientSocket GetSessionByAccountID(int nAccountID) {
        for (ClientSocket pSocket : aSessions) {
            if (pSocket.pAccount != null && pSocket.pAccount.nAccountID == nAccountID) {
                return pSocket;
            }
        }
        return null;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        int uSeqSend = rand.nextInt();
        int uSeqRecv = rand.nextInt();

        ClientSocket pClient = new ClientSocket(ch, uSeqSend, uSeqRecv);
        System.out.printf("[Debug] Opened session asfdaswith %s%n", pClient.GetIP());
        if (APIFactory.mIPBan.containsKey(pClient.GetIP())) {
            System.out.printf("[Debug] Rejected session with banned IP: %s%n", pClient.GetIP());
            pClient.Close();
            return;
        }

        OutPacket oPacket = new OutPacket((short) 0x0F);
        oPacket.EncodeShort(Configuration.MAPLE_VERSION);
        oPacket.EncodeString(Configuration.BUILD_VERSION);
        oPacket.EncodeInt(uSeqRecv);
        oPacket.EncodeInt(uSeqSend);
        oPacket.EncodeByte(Configuration.SERVER_TYPE);
        oPacket.EncodeByte(0);
        pClient.SendPacket(oPacket);

        ch.attr(ClientSocket.SESSION_KEY).set(pClient);

        pClient.PingTask = ctx.channel().eventLoop().scheduleAtFixedRate(()
                -> pClient.AliveReq(), 15, 15, TimeUnit.SECONDS);

        aSessions.add(pClient);
        pClient.nSessionID = nSessionID++;

        System.out.printf("[Debug] Opened session with %s%n", pClient.GetIP());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();

        ClientSocket pClient = (ClientSocket) ch.attr(ClientSocket.SESSION_KEY).get();
        aSessions.remove(pClient);

        if (pClient.PingTask != null) {
            pClient.PingTask.cancel(true);
        }

        if (pClient.nWorldID > 0 && pClient.pAccount != null) { //Set account state to 0 on auth server.
            CenterSocket pSocket = CenterSessionManager.pSession;
            if (pSocket != null) {
                pSocket.SendPacket(Center.SetState(pClient.nSessionID, 0, pClient.pAccount.nAccountID, 0));
            }
        }

        pClient.Close();
        System.out.printf("[Debug] Closed session with %s.%n", pClient.GetIP());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object in) {
        Channel ch = ctx.channel();

        ClientSocket pClient = (ClientSocket) ch.attr(ClientSocket.SESSION_KEY).get();
        InPacket iPacket = (InPacket) in;

        try {
            pClient.ProcessPacket(iPacket);
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "[Warning] Error handling ClientPacket!", ex);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        Channel ch = ctx.channel();

        ClientSocket pClient = (ClientSocket) ch.attr(ClientSocket.SESSION_KEY).get();
        aSessions.remove(pClient);

        if (pClient.PingTask != null) {
            pClient.PingTask.cancel(true);
        }

        if (pClient.nWorldID > 0 && pClient.pAccount != null) { //Set account state to 0 on auth server.
            CenterSocket pSocket = CenterSessionManager.pSession;
            if (pSocket != null) {
                pSocket.SendPacket(Center.SetState(pClient.nSessionID, 0, pClient.pAccount.nAccountID, 0));
            }
        }

        pClient.Close();
        System.out.printf("[Debug] Closed session with %s.%n", pClient.GetIP());
        Logger.getLogger(ClientSessionManager.class.getName()).log(Level.SEVERE, t.getMessage());
    }
}
