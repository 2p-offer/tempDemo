package tcp_ip.RPC_NIO;


import tcp_ip.RPCself.RpcUtil;

import java.net.InetSocketAddress;

/**
 * Created by 2P on 19-1-8.
 */
public class RpcTest2 {
    public static void main(String[] args) {
        //用来模拟多个客户端请求,确保线程池是有用的.
        ServiceTest res2 = RpcUtil.getServiceByRpc(ServiceTest.class, new InetSocketAddress("localhost", 8898));
        System.out.println(res2.sayHello("wy"));

    }
}
