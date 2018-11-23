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
import login.LoginSessionManager;
import net.PacketDecoder;
import net.PacketEncoder;

/**
 *
 * @author Kaz Voeten
 */
public class LoginServerClient extends Thread {

    private Channel pChannel;
    private EventLoopGroup workerGroup;
    private static LoginServerClient instance;

    @Override
    public void run() {
        connect();
    }

    private void connect() {
        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap pBootStrap = new Bootstrap();
            pBootStrap.group(workerGroup);
            pBootStrap.channel(NioSocketChannel.class);
            pBootStrap.option(ChannelOption.SO_KEEPALIVE, true);
            pBootStrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new PacketDecoder(), new LoginSessionManager(), new PacketEncoder());
                }
            });

            ChannelFuture pChannelFuture = pBootStrap.connect(Configuration.LOGIN_SERVER_IP, Configuration.LOGIN_SERVER_PORT);
            pChannel = pChannelFuture.channel();
            pChannelFuture.sync();
            pChannel.closeFuture().sync();
        } catch (Exception ex) {
            Logger.getLogger(LoginServerClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            workerGroup.shutdownGracefully();
            System.out.println("[Error] Unable to connect to the Login Server. Retrying in 30 seconds..");
            try {
                Thread.sleep(30 * 1000);
                connect();
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginServerClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static LoginServerClient getInstance() {
        if (instance == null) {
            instance = new LoginServerClient();
        }
        return instance;
    }

    public void shutdown(boolean planned) {
        ChannelFuture sf = pChannel.close();
        sf.awaitUninterruptibly();
    }
}
