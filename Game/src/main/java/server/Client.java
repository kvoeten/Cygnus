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
import center.CenterSessionManager;
import net.PacketDecoder;
import net.PacketEncoder;

/**
 * @author Kaz Voeten
 */
public class Client extends Thread {

    private Channel c;
    private EventLoopGroup workerGroup;
    private static Client instance;

    @Override
    public void run() {
        workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new PacketDecoder(), new CenterSessionManager(), new PacketEncoder());
                }
            });

            ChannelFuture f = b.connect(Configuration.CENTER_SERVER_IP, Configuration.CENTER_SERVER_PORT);
            c = f.channel();
            f.sync();
            c.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            System.out.println("[Info] Disconnected from Center Server.");
        }
    }

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void shutdown(boolean planned) {
        ChannelFuture sf = c.close();
        sf.awaitUninterruptibly();
    }

    public static void main(String[] args) {
        Server.getInstance().start();
        Client.getInstance().start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Server.getInstance().shutdown(false);
                Client.getInstance().shutdown(false);
            }
        });
    }
}
