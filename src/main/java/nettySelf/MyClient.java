package nettySelf;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import java.nio.charset.Charset;

/**
 * Created by 2P on 19-1-23.
 */
public class MyClient {
    // 要请求的服务器的ip地址
    private String ip;
    // 服务器的端口
    private int port;

    public MyClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    // 请求端主题
    public void action() throws InterruptedException {

        EventLoopGroup bossGroup = new OioEventLoopGroup();

        Bootstrap bs = new Bootstrap();

        bs.group(bossGroup)
                .channel(OioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 处理来自服务端的响应信息
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });

        // 客户端开启
        ChannelFuture cf = bs.connect(ip, port).sync();

        String reqStr = "我是客户端请求==================================================================================================";

        // 发送客户端的请求
        cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(Charset.forName("utf-8"))));
        cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(Charset.forName("utf-8"))));
        cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(Charset.forName("utf-8"))));
        cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(Charset.forName("utf-8"))));
        cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(Charset.forName("utf-8"))));

        cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(Charset.forName("utf-8"))));
        cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(Charset.forName("utf-8"))));
        cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(Charset.forName("utf-8"))));
        // 等待直到连接中断
        cf.channel().closeFuture().sync();
        Thread.sleep(2000);

    }

    public static void main(String[] args) throws Exception {

        new MyClient("10.10.12.159",9909).action();
    }
}
