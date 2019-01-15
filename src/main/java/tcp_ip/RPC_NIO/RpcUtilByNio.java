package tcp_ip.RPC_NIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Created by 2P on 19-1-4.
 */
public class RpcUtilByNio<T> {
    public static <T> T getServiceByRpc(final Class<?> inter, final InetSocketAddress address) {
        Object socket_getInputStream_finish = Proxy.newProxyInstance(inter.getClassLoader(), new Class[]{inter}, (Object proxy, Method
                method, Object[] args) -> {
            SocketChannel channel=null;
            try {
                System.out.println("try to open SocketChannel..");
                channel = SocketChannel.open(address);

                byte[] serverNameByte = inter.getName().getBytes();
                String strNum1 = CommonUtil.frontCompWithZore(serverNameByte.length, 10);
                ByteBuffer bnServerName = ByteBuffer.wrap(strNum1.getBytes());
                ByteBuffer bfServerName = ByteBuffer.wrap(serverNameByte);

                byte[] methodNamebyte = method.getName().getBytes();
                String strNum2 = CommonUtil.frontCompWithZore(methodNamebyte.length, 10);
                ByteBuffer bnmethodName = ByteBuffer.wrap(strNum2.getBytes());
                ByteBuffer bfmethodName = ByteBuffer.wrap(methodNamebyte);

                byte[] typebyte = IOUtils.Obj2Bytes(method.getParameterTypes());
                String strNum3 = CommonUtil.frontCompWithZore(typebyte.length, 10);
                ByteBuffer bnType = ByteBuffer.wrap(strNum3.getBytes());
                ByteBuffer bfType = ByteBuffer.wrap(typebyte);

                byte[] paramsbyte = IOUtils.Obj2Bytes(args);
                String strNum4 = CommonUtil.frontCompWithZore(paramsbyte.length, 10);
                ByteBuffer bnParams = ByteBuffer.wrap(strNum4.getBytes());
                ByteBuffer bfParams = ByteBuffer.wrap(paramsbyte);

                System.out.println(Arrays.toString(serverNameByte)+" "+Arrays.toString(methodNamebyte)+" "+Arrays.toString(typebyte)+" "+Arrays.toString(paramsbyte));
                channel.write(bnServerName);
                channel.write(bfServerName);
                channel.write(bnmethodName);
                channel.write(bfmethodName);
                channel.write(bnType);
                channel.write(bfType);
                channel.write(bnParams);
                channel.write(bfParams);


                System.out.println("channel write finsh..");

                System.out.println("client try to receive data");
                byte[] data=new byte[1024];
                ByteBuffer allocate = ByteBuffer.wrap(data);
                channel.read(allocate);
                return IOUtils.byte2Obj(data);
            } finally {
                if(channel!=null){
                    channel.close();
                }
            }
        });
        return (T)socket_getInputStream_finish;
    }

  
}
