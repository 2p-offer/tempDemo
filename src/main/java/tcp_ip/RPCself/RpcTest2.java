package tcp_ip.RPCself;


import org.apache.commons.lang.time.StopWatch;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 2P on 19-1-8.
 */
public class RpcTest2 {
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        int times=1000;
        CountDownLatch single=new CountDownLatch(1);
        CountDownLatch finish=new CountDownLatch(times);

        for(int i=0;i<times;i++) {
            new Thread(new SelfPerformanceTestThread(single,finish)).start();
        }

        single.countDown();
        finish.await();
        stopWatch.stop();
        System.out.println(times+"次并发执行,共花时:"+stopWatch.getTime());

    }
}
