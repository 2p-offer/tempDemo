package tcp_ip.RPC_netty.commonUtils;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 2P on 19-1-7.
 */
public class ZKUtils {

    private static Object lock = new Object();
    private ZooKeeper zoo;
    final CountDownLatch connectedSignal = new CountDownLatch(1);

    public ZooKeeper connect(String host) throws Exception {
        //单例实现
        if(zoo==null){
            synchronized (lock){
                if(zoo==null){
                    zoo = new ZooKeeper(host, 5000, (WatchedEvent we) -> {
                        if (we.getState() == KeeperState.SyncConnected) {
                            connectedSignal.countDown();
                        }
                    });
                }
            }
        }
        connectedSignal.await();
        return zoo;

    }

    public void close() throws InterruptedException {
        zoo.close();
    }



    public Class getData(String serverName) throws Exception {
        String path= CommonConfig.path+serverName;
        byte[] data = zoo.getData(path, true, zoo.exists(path, true));
        ByteArrayInputStream bais =new ByteArrayInputStream(data);
        ObjectInputStream ois =new ObjectInputStream(bais);
        Object o = ois.readObject();
        bais.close();
        ois.close();
        return (Class)o;

    }

    public Object getDataObj(String serverName) throws Exception {
        String path= CommonConfig.path+serverName;
        byte[] data = zoo.getData(path, true, zoo.exists(path, true));
        ByteArrayInputStream bais =new ByteArrayInputStream(data);
        ObjectInputStream ois =new ObjectInputStream(bais);
        Object o = ois.readObject();
        bais.close();
        ois.close();
        return o;
    }
}
