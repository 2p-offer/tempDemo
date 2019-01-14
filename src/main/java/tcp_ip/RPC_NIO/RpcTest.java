package tcp_ip.RPC_NIO;

import tcp_ip.RPCself.RpcUtil;

import java.net.InetSocketAddress;

/**
 * Created by 2P on 19-1-4.
 */
public class RpcTest {
    public static void main(String[] args) {
        int port=8898;
        //起一个线程来启动服务端,其实完全可以再起一个main函数.一样的
        new Thread(()->{
            try {
                Server server=new ServerImpl(port);
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
        ServiceTest res = RpcUtilByNio.getServiceByRpc(ServiceTest.class, new InetSocketAddress("localhost", port));
        System.out.println(res.sayHello("wangyan"));
        System.out.println(res.addInt(2,4));


    }
}
