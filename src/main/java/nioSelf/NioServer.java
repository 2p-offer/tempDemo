package nioSelf;

import tcp_ip.RPC_NIO.IOUtils;
import tcp_ip.RPC_NIO.ServiceTest;
import tcp_ip.RPC_NIO.ServiceTestImpl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 2P on 19-1-11.
 */
public class NioServer {

    private Selector selector;

    ServerSocketChannel serverSocketChannel;

    public void initServer(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        //通道设置非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", port));
        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server started succeed!");
    }

    public void listen() throws Exception {

        while (true) {
            selector.select();
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                if (key.isAcceptable()) {
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    recvAndReply(key);
                }
                ite.remove();
            }
        }
    }

    public void recvAndReply(SelectionKey key) throws Exception {
        SocketChannel channel = (SocketChannel) key.channel();

        //T1> 穿插特定字符,服务端接收后再次分割
//            String msg = new String(buffer.array()).trim();
//            String[] split = msg.split("_P_");
//            for(int x=0;x<split.length;x++){
//                System.out.println("NIO server received message =  " + split[x]);
//                System.out.println("NIO server reply =  " +split[x]);
//            }
//            channel.write(ByteBuffer.wrap((msg).getBytes()));

        //T2> 自定义协议,数据头存在数据长度;
        List<ByteBuffer> byteBufferList = new ArrayList<>();
        int tmp = 0;
        byte[] numbs = new byte[10];
        ByteBuffer numBuffer = ByteBuffer.wrap(numbs);
        while (tmp < 2) {
            int read = channel.read(numBuffer);
            if (read != -1) {
                if(tmp==0) {
                    int anInt = Integer.parseInt(new String(numbs));
                    System.out.println("try to get " + anInt + " byte datas");
                    ByteBuffer allocate = ByteBuffer.allocate(anInt);
                    channel.read(allocate);
                    byteBufferList.add(allocate);
                }else{
                    int anInt = Integer.parseInt(new String(numbs));
                    System.out.println("try to get " + anInt + " byte datas");
                    byte[] b=new byte[anInt];
                    ByteBuffer allocate = ByteBuffer.wrap(b);
                    channel.read(allocate);
                    ServiceTest s= (ServiceTestImpl) IOUtils.byte2Obj(b);
                    System.out.println(s.sayHello("wangyan"));
                }
                tmp++;
            }
            numBuffer.clear();
        }
        for (ByteBuffer bf : byteBufferList) {
            bf.clear();
            channel.write(bf);
        }
        channel.close();
    }

}
