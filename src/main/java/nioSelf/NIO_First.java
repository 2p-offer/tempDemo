package nioSelf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by 2P on 19-1-11.
 */
public class NIO_First {

    private Charset charset = Charset.forName("GBK");// 创建GBK字符集
    private SocketChannel channel;

    public void readHTMLContent() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(
                    "www.baidu.com", 80);
            //T1:打开连接
            channel = SocketChannel.open(socketAddress);
            //T2:发送请求，使用GBK编码
            channel.write(charset.encode("GET " + "/ HTTP/1.1" + "\r\n\r\n"));
            //T3:读取数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);// 创建1024字节的缓冲
            while (channel.read(buffer) != -1) {
                //读写状态改变
                buffer.flip();
                //解码
                System.out.println(charset.decode(buffer));

                buffer.clear();// 清空缓冲
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        new NIO_First().readHTMLContent();
    }
}


