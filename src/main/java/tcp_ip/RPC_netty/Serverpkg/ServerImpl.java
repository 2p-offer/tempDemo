package tcp_ip.RPC_netty.Serverpkg;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tcp_ip.RPC_netty.*;
import tcp_ip.RPC_netty.commonUtils.CommonConfig;
import tcp_ip.RPC_netty.commonUtils.IOUtils;
import tcp_ip.RPC_netty.commonUtils.ZKUtils;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by 2P on 19-1-4.
 */
public class ServerImpl implements Server {

    public static ExecutorService executor = Executors.newFixedThreadPool(128);
    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());

    public static ZooKeeper zk;
    public static ZKUtils zkUtils;
    public int port;

    public ServerImpl(int port) {
        zkUtils = new ZKUtils();
        this.port = port;
    }

    private void init() {
        try {
            zk = zkUtils.connect(CommonConfig.zkHost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(String serverName, Class impl) throws Exception {
        init();
        byte[] data = IOUtils.Obj2Bytes(impl);
        Stat exists = zk.exists(CommonConfig.path + serverName, true);
        //存在节点,更新.不存在创建
        if (exists != null) {
            zk.delete(CommonConfig.path + serverName, zk.exists(CommonConfig.path + serverName, true).getVersion());
        }
        zk.create(CommonConfig.path + serverName, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    @Override
    public void start() {
        LOGGER.info("start---");
        ThreadFactory threadRpcFactory = new NamedThreadFactory("NettyRPC ThreadFactory");

        //方法返回到Java虚拟机的可用的处理器数量
        int parallel = Runtime.getRuntime().availableProcessors() * 2;

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup(parallel, threadRpcFactory, SelectorProvider.provider());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer(zkUtils))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind("localhost", port).sync();
            LOGGER.info("Netty RPC Server start success port:%d\n");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.info("Server start error"+e.getMessage());
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}


