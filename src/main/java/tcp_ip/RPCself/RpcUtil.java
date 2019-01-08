package tcp_ip.RPCself;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by 2P on 19-1-4.
 */
public class RpcUtil<T> {
    public static <T> T getServiceByRpc(final Class<?> inter, final InetSocketAddress address) {

        return (T) Proxy.newProxyInstance(inter.getClassLoader(), new Class[]{inter}, (Object proxy, Method
        method, Object[]args) -> {

            //使用socket 与服务端进行远程通信,(传递序列化数据)
            Socket socket = null;
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;
            try {
                socket = new Socket();
                socket.connect(address);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeUTF(inter.getName());
                outputStream.writeUTF(method.getName());
                outputStream.writeObject(method.getParameterTypes());
                outputStream.writeObject(args);
                //Thread.sleep(10000);
                System.out.println("---outputStream,write finish"+method.getName());
                inputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("socket getInputStream finish");
                return inputStream.readObject();

            } finally {
                socket.close();
                outputStream.close();
                inputStream.close();
            }
        });
    }
}
