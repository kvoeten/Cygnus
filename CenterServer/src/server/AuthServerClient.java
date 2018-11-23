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
package server;

import auth.AuthServerSessionManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.PacketDecoder;
import net.PacketEncoder;

/**
 *
 * @author Kaz Voeten
 */
public class AuthServerClient extends Thread {

    private Channel c;
    private EventLoopGroup workerGroup;
    private static AuthServerClient instance;
    private int nReconnectAttempts = 0;

    @Override
    public void run() {
        connect();
    }

    private void connect() {
        workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new PacketDecoder(), new AuthServerSessionManager(), new PacketEncoder());
                }
            });

            ChannelFuture f = b.connect(Configuration.AUTH_SERVER_IP, Configuration.AUTH_SERVER_PORT);
            c = f.channel();
            f.sync();
            c.closeFuture().sync();
        } catch (Exception ex) {
            Logger.getLogger(AuthServerClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            workerGroup.shutdownGracefully();
            if (nReconnectAttempts++ > 20) {
                System.out.println("[Fatal] Unable to connect to the Auth Server after 20 attempts.");
                System.exit(1);
            }
            System.out.println("[Error] Unable to connect to the Auth Server. Retrying in 5 seconds..");
            try {
                Thread.sleep(5 * 1000);
                connect();
            } catch (InterruptedException ex) {
                Logger.getLogger(AuthServerClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static AuthServerClient getInstance() {
        if (instance == null) {
            instance = new AuthServerClient();
        }
        return instance;
    }

    public void shutdown(boolean planned) {
        ChannelFuture sf = c.close();
        sf.awaitUninterruptibly();
    }
}
