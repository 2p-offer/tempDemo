package tcp_ip.RPC_netty.beans;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 2P on 19-1-24.
 */
public class MessageCallBack {
    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());
    private MessageRequest request;
    private MessageResponse response;
    private Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();

    public MessageCallBack(MessageRequest request) {
        this.request = request;
    }

    public Object start() throws InterruptedException {
        try {
            lock.lock();
            finish.await(20*1000, TimeUnit.MILLISECONDS);
            if (this.response != null) {
                return this.response.getResult();
            } else {
                System.out.println("response is null");
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public void over(MessageResponse reponse) {
        try {
            lock.lock();
            this.response = reponse;
            LOGGER.info("client over ,response:"+reponse);
            finish.signal();
        } finally {
            lock.unlock();
        }
    }
}