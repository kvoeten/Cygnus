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

import center.CenterSessionManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.PacketDecoder;
import net.PacketEncoder;

/**
 *
 * @author Kaz Voeten
 */
public class CenterConnection extends Thread {

    private ServerBootstrap sb;
    private Channel serverChannel;
    private static CenterConnection pInstance;
    private EventLoopGroup bossGroup, workerGroup;

    @Override
    public void run() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        sb = new ServerBootstrap();

        sb.group(bossGroup, workerGroup);
        sb.channel(NioServerSocketChannel.class);
        sb.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel c) throws Exception {
                c.pipeline().addLast(new PacketDecoder(), new CenterSessionManager(), new PacketEncoder());
            }
        });

        sb.childOption(ChannelOption.TCP_NODELAY, true);
        sb.childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            ChannelFuture f = sb.bind(Configuration.CENTER_SERVER_PORT).sync();
            serverChannel = f.channel();
            System.out.printf("[Info] CenterSocket has been bound to port %s.%n", Configuration.CENTER_SERVER_PORT);
            serverChannel.closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.printf("[Info] CenterSocket has been unbound from port %s.%n", Configuration.CENTER_SERVER_PORT);
        }
    }

    public void Shutdown(boolean bPlanned) {
        /*
        for (World w : activeWorlds.values()) {
            w.write(PacketCreator.getShutdown(planned));
        }
         */

        ChannelFuture f = serverChannel.close();
        f.awaitUninterruptibly();
    }

    public static CenterConnection GetInstance() {
        if (pInstance == null) {
            pInstance = new CenterConnection();
        }
        return pInstance;
    }
}
