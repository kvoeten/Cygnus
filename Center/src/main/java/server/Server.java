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

import auth.RankingWorker;
import database.Database;
import game.GameServerSessionManager;
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
import wz.MapleDataFactory;

/**
 * @author Kaz Voeten
 */
public class Server extends Thread {

    private static Server instance;
    private ServerBootstrap sb;
    private Channel serverChannel;
    private EventLoopGroup bossGroup, workerGroup;
    public MapleDataFactory pDataFactory = new MapleDataFactory();

    @Override
    public void run() {
        Long time = System.currentTimeMillis();
        System.out.println("[Info] Booting up CenterServer for world " + Configuration.GetConfig().WORLD_NAME);

        pDataFactory.Initialize();
        (new RankingWorker()).start();

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        sb = new ServerBootstrap();

        sb.group(bossGroup, workerGroup);
        sb.channel(NioServerSocketChannel.class);
        sb.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel c) throws Exception {
                c.pipeline().addLast(new PacketDecoder(), new GameServerSessionManager(), new PacketEncoder());
            }
        });

        sb.childOption(ChannelOption.TCP_NODELAY, true);
        sb.childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            ChannelFuture cf = sb.bind(Configuration.GetConfig().GAME_SERVER_PORT).sync();
            serverChannel = cf.channel();
            System.out.printf("[Info] CenterServer has been bound to port %s.%n", Configuration.GetConfig().GAME_SERVER_PORT);
            System.out.println("[Info] CenterServer completely initialized within: " + (System.currentTimeMillis() - time) + "ms.");
            serverChannel.closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.printf("[Info] CenterServer has been unbound from port %s.%n", Configuration.GetConfig().GAME_SERVER_PORT);
        }
    }

    public void shutdown(boolean planned) {
        ChannelFuture sf = serverChannel.close();
        sf.awaitUninterruptibly();
    }

    public static Server GetInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }
}
