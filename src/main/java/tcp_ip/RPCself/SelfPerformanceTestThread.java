package tcp_ip.RPCself;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 2P on 19-1-26.
 */
public class SelfPerformanceTestThread implements Runnable {
    CountDownLatch single;
    CountDownLatch finish;

    public SelfPerformanceTestThread(CountDownLatch single, CountDownLatch finish) {
        this.single = single;
        this.finish = finish;
    }

    @Override
    public void run() {
        try {
            single.await();

            ServiceTest res = RpcUtil.getServiceByRpc(ServiceTest.class, new InetSocketAddress("localhost", 8898));
            System.out.println(res.sayHello("wangyan"));
            finish.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
