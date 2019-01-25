package tcp_ip.RPC_NIO;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 2P on 19-1-4.
 */
public class ServerImpl implements Server {

    //使用线程池,多个客户端rpc请求时,可以并发处理
    public static ExecutorService executor = Executors.newFixedThreadPool(128);
    public static ZooKeeper zk;
    //使用zk之前,用的是hashMap
//    private static final HashMap<String, Class> serviceRegistry = new HashMap();
    public ZKUtils zkUtils;
    public int port;


    private Selector selector;

    ServerSocketChannel serverSocketChannel;

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
        byte[] data = IOUtils.Obj2Bytes(impl);
        Stat exists = zk.exists(CommonConfig.path + serverName, true);
        //存在节点,更新.不存在创建
        if (exists != null) {
            zk.delete(CommonConfig.path + serverName, zk.exists(CommonConfig.path + serverName, true).getVersion());
        }
        zk.create(CommonConfig.path + serverName, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    @Override
    public void start() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        //通道设置非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", port));
        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            System.out.println("server start finish,and listen...");
            selector.select();
            System.out.println("select. finish.");
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                if (key.isAcceptable()) {
                    System.out.println("try to accept..");
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    System.out.println("try to read..");
                    //加上这个interestOps(0),表示这个key及其对应的chanel,不会在对任何事件感兴趣,
                    //如果不加,会一直监听到事件,导致一次客户端请求,并发处理.
                    //和  key.cancel() 效果类似,但是据说cancel 不好
                    key.interestOps(0);
                    executor.execute(new ServiceTask((SocketChannel) key.channel()));
//                    try {
//                        handleData((SocketChannel) key.channel());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
                ite.remove();
            }
        }
    }

    private class ServiceTask implements Runnable {

        public SocketChannel client;

        public ServiceTask(SocketChannel client) {
            this.client = client;
        }

        @Override
        public void run() {

            try {
                String serverName = "";
                String methodName = "";
                Class<?>[] types = null;
                Object[] parameters = null;
                int sum = 0;

                while (sum < 4) {
                    byte[] dlByte = new byte[10];
                    ByteBuffer dataLenth = ByteBuffer.wrap(dlByte);
                    int read = client.read(dataLenth);
                    System.out.println(new String(dlByte));
                    if (read != -1) {
                        //这个判断的添加,会存在一个很大的问题,..   但是不添加,会读空数据,下去处理,报错.
                        if(read==0){
                            continue;
                        }
                        if (sum == 0) {
                            int anInt = Integer.parseInt(new String(dlByte));
                            System.out.println("try to get " + anInt + " byte datas,for serverName ");
                            byte[] data = new byte[anInt];
                            ByteBuffer allocate = ByteBuffer.wrap(data);
                            client.read(allocate);System.out.println(Arrays.toString(data));
                            serverName = new String(data);
                        } else if (sum == 1) {
                            //Thread.sleep(1);
                            int anInt2 = Integer.parseInt(new String(dlByte));
                            System.out.println("try to get " + anInt2 + " byte datas,for methodName");
                            byte[] data = new byte[anInt2];
                            ByteBuffer allocate2 = ByteBuffer.wrap(data);
                            client.read(allocate2);System.out.println(Arrays.toString(data));
                            methodName = new String(data);
                        } else if (sum == 2) {
                            //Thread.sleep(1);
                            int anInt3 = Integer.parseInt(new String(dlByte));
                            System.out.println("try to get " + anInt3 + " byte datas,for types");
                            byte[] data = new byte[anInt3];
                            System.out.println(data.length+" bytes to type");
                            ByteBuffer allocate3 = ByteBuffer.wrap(data);
                            client.read(allocate3);System.out.println(Arrays.toString(data));
                            types = (Class<?>[]) IOUtils.byte2Obj(data);
                        } else if (sum == 3) {
                            //Thread.sleep(1);
                            int anInt4 = Integer.parseInt(new String(dlByte));
                            System.out.println("try to get " + anInt4 + " byte datas,for parameters");
                            byte[] data = new byte[anInt4];
                            ByteBuffer allocate4 = ByteBuffer.wrap(data);
                            client.read(allocate4);System.out.println(Arrays.toString(data));
                            parameters = (Object[]) IOUtils.byte2Obj(data);
                        }
                        sum++;
                    }
                    dataLenth.clear();
                }
//                数据验证
//                if (StringUtils.isEmpty(serverName)) {
//                    System.out.println("================ bad reques serverName is ''");
//                    return;
//                }
//                if (StringUtils.isEmpty(methodName)) {
//                    System.out.println("================ bad reques methodName is ''");
//                    return;
//                }
                Class aClass = zkUtils.getData(serverName);
                if (aClass == null) {
                    return;
                }
//                zk得到的序列化数据,反射执行
                Method methods = aClass.getMethod(methodName, types);
                Object invoke = methods.invoke(aClass.newInstance(), parameters);
                System.out.println("method invoke finish " + methodName);
//                将执行结果转byte数组传递
                byte[] bytes = IOUtils.Obj2Bytes(invoke);
//                执行结果序列化返回到客户端
                client.write(ByteBuffer.wrap(bytes));
                System.out.println("server outputStream,write finish");
                client.close();

            } catch (Exception e) {
                e.printStackTrace();

                try {
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    client.read(allocate);

                    System.out.println("-----------------------"+Arrays.toString(allocate.array()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //单线程使用
    public void handleData(SocketChannel client) throws Exception {
        String serverName = "";
        String methodName = "";
        Class<?>[] types = null;
        Object[] parameters = null;
        int sum = 0;
        byte[] dlByte = new byte[1024];
        ByteBuffer databuffer = ByteBuffer.wrap(dlByte);
        client.read(databuffer);

        System.out.println(Arrays.toString(dlByte));
        //执行结果序列化返回到客户端
//        client.write(ByteBuffer.wrap(bytes));
        System.out.println("server outputStream,write finish");
        client.close();
    }


}


