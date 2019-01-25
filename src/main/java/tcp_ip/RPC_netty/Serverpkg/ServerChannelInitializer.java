package tcp_ip.RPC_netty.Serverpkg;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import tcp_ip.RPC_netty.commonUtils.ZKUtils;

/**
 * Created by 2P on 19-1-24.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    ZKUtils zkUtils;
    final public static int MESSAGE_LENGTH = 4;

    public ServerChannelInitializer(ZKUtils zkUtils) {
        this.zkUtils=zkUtils;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, ServerChannelInitializer.MESSAGE_LENGTH, 0, ServerChannelInitializer.MESSAGE_LENGTH));
        //回填补充ObjectDecoder消息报文头
        pipeline.addLast(new LengthFieldPrepender(ServerChannelInitializer.MESSAGE_LENGTH));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        pipeline.addLast(new ServerHandler(zkUtils));
    }
}
