package tcp_ip.RPCself;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 2P on 19-1-4.
 */
public class ServerImpl implements Server {

    //使用线程池,多个客户端rpc请求时,不会阻塞.可以并发处理
    public static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static ZooKeeper zk;
    //使用zk之前,用的是hashMap
//    private static final HashMap<String, Class> serviceRegistry = new HashMap();
    public ZKUtils zkUtils;
    public int port;

    public ServerImpl(int port) {
        zkUtils = new ZKUtils();
        this.port = port;

    }

    private void init() {
        try {
            zk = zkUtils.connect(CommonConfig.zkHost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(String serverName, Class impl) throws Exception {
        init();
        byte[] data = createData(impl);
        Stat exists = zk.exists(CommonConfig.path + serverName, true);
        //存在节点,更新.不存在创建
        if (exists != null) {
            zk.delete(CommonConfig.path + serverName, zk.exists(CommonConfig.path + serverName, true).getVersion());
        }
        zk.create(CommonConfig.path + serverName, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    //对象序列化存到zookeeper,最差劲的序列化方式,用OutPutStream,
    // TODO 可以使用Nio,最好弄弄netty的编解码.
    private byte[] createData(Class impl) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream obj = new ObjectOutputStream(baos);
        obj.writeObject(impl);
        bytes = baos.toByteArray();
        baos.close();
        obj.close();
        return bytes;
    }

    @Override
    public void start() throws IOException {
        ServerSocket client = new ServerSocket();
//        socket启动,绑定端口
        client.bind(new InetSocketAddress(port));
        //TODO 死循环不是一个好的解决方案

        while (true) {
            //服务端监听客户端socket是否连接,accept()方法会监听是否有连接,有连接就处理,没连接阻塞.所以要使用多线程,实现同时处理多个请求
            Socket accept = client.accept();
            executor.execute(new ServiceTask(accept));

        }
    }

    private class ServiceTask implements Runnable {

        public Socket client;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            ObjectInputStream inputStream;
            ObjectOutputStream outputStream;
            try {
                inputStream = new ObjectInputStream(client.getInputStream());
                String serverName = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class<?>[] types = (Class<?>[]) inputStream.readObject();
                Object[] parameters = (Object[]) inputStream.readObject();
                Class aClass = zkUtils.getData(serverName);
                if (aClass == null) {
                    return;
                }
                //要是模拟多个客户端,看效果,可以打开下面,服务端同时sleep多个客户端请求,直观表达出正在处理多个客户端请求
                //System.out.println("try to sleep");
                //Thread.sleep(20000);

                //zk得到的序列化数据,反射执行
                Method methods = aClass.getMethod(methodName, types);
                Object invoke = methods.invoke(aClass.newInstance(), parameters);
                System.out.println("method invoke finish " + methodName);
                //执行结果序列化返回到客户端
                outputStream = new ObjectOutputStream(client.getOutputStream());
                outputStream.writeObject(invoke);
                System.out.println("server outputStream,write finish");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}


