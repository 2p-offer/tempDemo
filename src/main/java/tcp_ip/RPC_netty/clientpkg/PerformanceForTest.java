package tcp_ip.RPC_netty.clientpkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tcp_ip.RPC_netty.beans.ServiceTest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 2P on 19-2-1.
 */
public class PerformanceForTest implements Runnable {

    CountDownLatch finish;

    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());

    public PerformanceForTest(CountDownLatch finish) {
        this.finish = finish;
    }

    @Override
    public void run() {
        try {
            ServiceTest res = RpcUtilByNio.getServiceByRpc(ServiceTest.class);

            System.out.println(res.sayHello("wangyan") + ".....");
            finish.countDown();
        } catch (Exception e) {
            LOGGER.error("task run error:"+e.getMessage());
            e.printStackTrace();
        }
    }
}
