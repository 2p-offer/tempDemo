package tcp_ip.RPC_netty.clientpkg;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by 2P on 19-1-25.
 */
public class ClientChannelInit extends ChannelInitializer<SocketChannel> {


    final public static int MESSAGE_LENGTH = 4;

    protected void initChannel(SocketChannel socketChannel){
        ChannelPipeline pipeline = socketChannel.pipeline();
        //半包解码器LengthFieldBasedFrameDecoder
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, ClientChannelInit.MESSAGE_LENGTH, 0, ClientChannelInit.MESSAGE_LENGTH));
        //回填补充ObjectDecoder消息报文头
        pipeline.addLast(new LengthFieldPrepender(ClientChannelInit.MESSAGE_LENGTH));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        pipeline.addLast(new ClientHandler());
    }

}