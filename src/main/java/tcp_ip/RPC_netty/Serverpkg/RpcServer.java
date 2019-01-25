package tcp_ip.RPC_netty.Serverpkg;

import tcp_ip.RPC_netty.Server;
import tcp_ip.RPC_netty.ServiceTest;
import tcp_ip.RPC_netty.ServiceTestImpl;

/**
 * Created by 2P on 19-1-4.
 */
public class RpcServer {
    public static void main(String[] args) {
        int port = 8898;
        new Thread(() -> {
            try {
                Server server = new ServerImpl(port);
                server.register(ServiceTest.class.getName(), ServiceTestImpl.class);
                server.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            //换成使用zookeeper进行服务注册发现之后,服务端启动变慢,sleep3秒,保证服务端启动之后,客户端再请求.
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
