package tcp_ip.RPCself;


import java.net.InetSocketAddress;

/**
 * Created by 2P on 19-1-8.
 */
public class RpcTest2 {
    public static void main(String[] args) {
        for(int i=0;i<500;i++) {
            ServiceTest res = RpcUtil.getServiceByRpc(ServiceTest.class, new InetSocketAddress("localhost", 8898));
            System.out.println(res.sayHello("wangyan"));
            System.out.println(res.sayOne(1));
        }

    }
}
