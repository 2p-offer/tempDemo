package tcp_ip.RPC_netty.clientpkg;


import tcp_ip.RPC_netty.ServiceTest;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 2P on 19-1-8.
 */
public class RpcClient {
    public static void main(String[] args) {

        ServerLoader loader= ServerLoader.getInstance();
        final InetSocketAddress localhost = new InetSocketAddress("localhost", 8898);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new ServerConnectionTask(localhost,loader));
        executorService.shutdown();


        ServiceTest res = RpcUtilByNio.getServiceByRpc(ServiceTest.class);

        System.out.println(res.sayHello("wangyan")+".....");

    }
}
