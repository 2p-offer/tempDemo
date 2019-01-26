package tcp_ip.RPC_netty.Serverpkg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tcp_ip.RPC_netty.beans.MessageRequest;
import tcp_ip.RPC_netty.beans.MessageResponse;
import tcp_ip.RPC_netty.commonUtils.ZKUtils;

import java.util.concurrent.Executors;

/**
 * Created by 2P on 19-1-24.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());

    ZKUtils zkUtils;
    public ServerHandler(ZKUtils zkUtils) {
        this.zkUtils=zkUtils;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRequest request = (MessageRequest) msg;
        MessageResponse response = new MessageResponse();
        ServerReceiveTask recvTask = new ServerReceiveTask(request, response, zkUtils, ctx);
        //按说不应阻塞nio线程,其他所有处理交给线程池,
//        LOGGER.info("server receive...");
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()).execute(recvTask);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
//        LOGGER.info("channelRegistered..");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        LOGGER.info("channelActive...");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //网络有异常要关闭通道
        ctx.close();
    }
}
