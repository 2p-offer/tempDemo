package nettySelf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

/**
 * Created by 2P on 19-1-23.
 */
public class ClientHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf bb = (ByteBuf)msg;
            byte[] respByte = new byte[bb.readableBytes()];
            bb.readBytes(respByte);
            String respStr = new String(respByte, Charset.forName("utf-8"));
            System.err.println("client--收到响应：" + respStr);

            // 直接转成对象
//          handlerObject(ctx, msg);

        } finally{
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);

        }
    }
}
