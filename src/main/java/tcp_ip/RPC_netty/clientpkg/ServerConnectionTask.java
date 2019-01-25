package tcp_ip.RPC_netty.clientpkg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * Created by 2P on 19-1-25.
 */
public class ServerConnectionTask implements Runnable {
    SocketAddress address;
    ServerLoader loader;
    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());

    public ServerConnectionTask(SocketAddress address,ServerLoader loader){
        this.address=address;
        this.loader=loader;
    }
    @Override
    public void run() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        //创建连接
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ClientChannelInit());

        ChannelFuture channelFuture = b.connect(address);
        channelFuture.addListener((final ChannelFuture cf) -> {
            if (cf.isSuccess()) {
                LOGGER.info("channel connect success...");
                Channel channel = cf.channel();
                ChannelPipeline pipeline = channel.pipeline();

                ClientHandler handler = pipeline.get(ClientHandler.class);
                LOGGER.info(loader.toString()+":loader  connect..");
                ServerConnectionTask.this.loader.setMessageSendHandler(handler);
                LOGGER.info("set MessageSendHandler success...");
            }
        });
    }


}
