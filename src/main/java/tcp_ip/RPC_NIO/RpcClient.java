package tcp_ip.RPC_NIO;


import tcp_ip.RPCself.RpcUtil;

import java.net.InetSocketAddress;

/**
 * Created by 2P on 19-1-8.
 */
public class RpcClient {
    public static void main(String[] args) {

        ServiceTest res = RpcUtilByNio.getServiceByRpc(ServiceTest.class, new InetSocketAddress("localhost", 8898));
        System.out.println(res.sayHello("wangyan"));
        System.out.println(res.addInt(2,4));

    }
}
