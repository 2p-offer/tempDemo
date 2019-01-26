package tcp_ip.RPC_netty.clientpkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tcp_ip.RPC_netty.beans.ServiceTest;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 2P on 19-1-26.
 */
public class PerformanceTestThread implements Runnable {

    CountDownLatch single;
    CountDownLatch finish;

    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());

    public PerformanceTestThread(CountDownLatch single, CountDownLatch finish) {
        this.single = single;
        this.finish = finish;
    }

    @Override
    public void run() {
        try {
            single.await();

            ServiceTest res = RpcUtilByNio.getServiceByRpc(ServiceTest.class);

            System.out.println(res.sayHello("wangyan") + ".....");
            finish.countDown();
        } catch (InterruptedException e) {
            LOGGER.error("task run error:"+e.getMessage());
            e.printStackTrace();
        }
    }
}
