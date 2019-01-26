package tcp_ip.RPC_netty.clientpkg;



import org.apache.log4j.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 2P on 19-1-24.
 */
public class ServerLoader {

    private volatile static ServerLoader rpcServerLoader;
    private final Logger LOGGER= Logger.getLogger(this.getClass());

    private ClientHandler clientHandler = null;

    //等待Netty服务端链路建立通知信号
    private Lock lock = new ReentrantLock();
    private Condition signal = lock.newCondition();

    private ServerLoader() {
    }

    public static ServerLoader getInstance() {
        if (rpcServerLoader == null) {
            synchronized (ServerLoader.class) {
                if (rpcServerLoader == null) {
                    System.out.println("try to new ServerLoader..");
                    rpcServerLoader = new ServerLoader();
                }
            }
        }
        return rpcServerLoader;
    }

    public void setMessageSendHandler(ClientHandler clientHandler) {
        try {
            lock.lock();
            this.clientHandler = clientHandler;
            //唤醒所有等待客户端RPC线程
//            LOGGER.info("try to signalAll()...lock is:"+lock.toString()+"and handler is:"+this.clientHandler);
            signal.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public ClientHandler getMessageSendHandler() throws InterruptedException {
        try {
            lock.lock();
            //Netty服务端链路没有建立完毕之前，先挂起等待
            if ( clientHandler == null) {
//                LOGGER.info(this.toString()+" try to await()..."+lock.toString());
                signal.await();
//                LOGGER.info("notify..,and handler is :"+clientHandler);
            }
            return clientHandler;
        } finally {
            lock.unlock();
        }
    }

    public void unLoad() {
        clientHandler.close();
    }

}