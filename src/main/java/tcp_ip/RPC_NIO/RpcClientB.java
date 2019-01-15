package tcp_ip.RPC_NIO;

import java.net.InetSocketAddress;

/**
 * Created by 2P on 19-1-14.
 */
public class RpcClientB {
    public static void main(String[] args) {
        for(int i=0;i<50;i++) {
            ServiceTest res = RpcUtilByNio.getServiceByRpc(ServiceTest.class, new InetSocketAddress("localhost", 8898));
            System.out.println(res.sayHello("yanwangyan"));
//            System.out.println(res.addInt(3, 5));
        }
    }
}


