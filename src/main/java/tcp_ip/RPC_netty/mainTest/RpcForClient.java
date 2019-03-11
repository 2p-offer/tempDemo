package tcp_ip.RPC_netty.mainTest;

import org.apache.commons.lang.time.StopWatch;
import sun.security.provider.MD5;
import tcp_ip.RPC_netty.clientpkg.PerformanceForTest;
import tcp_ip.RPC_netty.clientpkg.PerformanceTestThread;
import tcp_ip.RPC_netty.clientpkg.ServerConnectionTask;
import tcp_ip.RPC_netty.clientpkg.ServerLoader;

import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 2P on 19-2-1.
 */
public class RpcForClient {
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        int times=8000;
        CountDownLatch finish=new CountDownLatch(times);
        ServerLoader loader = ServerLoader.getInstance();
        final InetSocketAddress localhost = new InetSocketAddress("localhost", 8898);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new ServerConnectionTask(localhost, loader));
        executorService.shutdown();
        for (int i = 0; i < times; i++) {
            PerformanceForTest performanceTestThread=new PerformanceForTest(finish);
            new Thread(performanceTestThread).start();
        }
        finish.await();
        stopWatch.stop();
        System.out.println(times+"次并发执行,共花时:"+stopWatch.getTime());

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
