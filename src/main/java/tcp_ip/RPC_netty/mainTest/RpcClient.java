package tcp_ip.RPC_netty.mainTest;


import org.apache.commons.lang.time.StopWatch;
import tcp_ip.RPC_netty.clientpkg.PerformanceTestThread;
import tcp_ip.RPC_netty.clientpkg.ServerConnectionTask;
import tcp_ip.RPC_netty.clientpkg.ServerLoader;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 2P on 19-1-8.
 */
public class RpcClient {
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        int times=10000;
        CountDownLatch single=new CountDownLatch(1);
        CountDownLatch finish=new CountDownLatch(times);
        ServerLoader loader = ServerLoader.getInstance();
        final InetSocketAddress localhost = new InetSocketAddress("localhost", 8898);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new ServerConnectionTask(localhost, loader));
        executorService.shutdown();
        for (int i = 0; i < times; i++) {
            PerformanceTestThread performanceTestThread=new PerformanceTestThread(single,finish);
            new Thread(performanceTestThread).start();
        }
        single.countDown();
        finish.await();
        stopWatch.stop();
        System.out.println(times+"次并发执行,共花时:"+stopWatch.getTime());
    }
}
