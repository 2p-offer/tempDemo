package nettySelf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 * Created by 2P on 19-1-23.
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf bb = (ByteBuf)msg;
            byte[] respByte = new byte[bb.readableBytes()];
            bb.readBytes(respByte);
            String respStr = new String(respByte, Charset.forName("utf-8"));
            System.out.println("client--收到响应：" + respStr);
        } finally{
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete====");
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    // 出现异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("channelRead:: error====");
        ctx.close();
    }
}
