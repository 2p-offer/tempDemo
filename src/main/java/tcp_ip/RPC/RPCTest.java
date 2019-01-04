package tcp_ip.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by 2P on 19-1-4.
 */
public class RPCTest {
    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            try {
                Server serviceServer = new ServiceCenter(8088);
                serviceServer.register(HelloService.class, HelloServiceImpl.class);
                serviceServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        HelloService service = RPCClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8088));
        System.out.println(service.sayHi("test"));
    }
}
