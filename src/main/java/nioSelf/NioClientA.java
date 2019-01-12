package nioSelf;

import tcp_ip.RPC_NIO.CommonUtil;
import tcp_ip.RPC_NIO.IOUtils;
import tcp_ip.RPC_NIO.ServiceTest;
import tcp_ip.RPC_NIO.ServiceTestImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by 2P on 19-1-11.
 */
public class NioClientA {
    SocketChannel channel;

    public void initClient(String host, int port) throws IOException {
        //构造socket连接
        InetSocketAddress servAddr = new InetSocketAddress(host, port);

        this.channel = SocketChannel.open(servAddr);
    }

    public void sendAndRecv(String words) throws IOException {

        System.out.println("Client sending: " + words);


//        channel.write(buffer);
//        //T1> 穿插特定字符,服务端接收整体后,再次分割处理
//        ByteBuffer bu2 = ByteBuffer.wrap(new String("_P_第二次发送").getBytes());
//        channel.write(bu2);
//        buffer.clear();
//        bu2.clear();
//        channel.read(buffer);
//        channel.read(bu2);
//        System.out.println("Client received: " + new String(buffer.array()).trim());
//        System.out.println("Client received: " + new String(bu2.array()).trim());
//        channel.close();


        //T2> 自定义一个协议,数据前,先读取指定位数的数据,作为数据长度
        String msg2 = "I'm the second one for you ";
        byte[] b1 = words.getBytes();
        String strNum1 = CommonUtil.frontCompWithZore(b1.length, 10);
        ByteBuffer buffern1 = ByteBuffer.wrap(strNum1.getBytes());
        ByteBuffer buffer1 = ByteBuffer.wrap(b1);

        ServiceTest serviceTest=new ServiceTestImpl();
        byte[] b2 = IOUtils.Obj2Bytes(serviceTest);
        String strNum2 = CommonUtil.frontCompWithZore(b2.length, 10);
        ByteBuffer buffern2 = ByteBuffer.wrap(strNum2.getBytes());
        ByteBuffer buffer2 = ByteBuffer.wrap(b2);


        channel.write(buffern1);
        channel.write(buffer1);
        channel.write(buffern2);
        channel.write(buffer2);
        byte[] bytes = new byte[1024];
        byte[] bytes2 = new byte[1024];
        channel.read(ByteBuffer.wrap(bytes));
        channel.read(ByteBuffer.wrap(bytes2));
        String s=new String(bytes);
        String s2=new String(bytes2);
        System.out.println("client receives1:"+s);
        System.out.println("client receives2222:"+s2);


    }

}
