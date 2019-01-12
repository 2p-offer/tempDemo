package nioSelf;

import java.io.IOException;

/**
 * Created by 2P on 19-1-11.
 */
public class NioTest {

    public static void main(String[] args) throws Exception {

        Thread tserver = new Thread(() -> {
            try {
                NioServer nioServer = new NioServer();
                nioServer.initServer(7999);
                nioServer.listen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tserver.start();
        Thread.sleep(3000);


        NioClientA nioClientA = new NioClientA();
        //连接server
        nioClientA.initClient("localhost",7999);
        nioClientA.sendAndRecv("I'm try to send msg");

    }
}
