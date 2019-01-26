package tcp_ip.RPC_netty.beans;

/**
 * Created by 2P on 19-1-4.
 */
public class ServiceTestImpl implements ServiceTest {

    private static final long serialVersionUID = -219988432063763456L;
    @Override
    public String sayHello(String name) {
        return "hello "+name;
    }
//
//    @Override
//    public int addInt(int a, int b) {
//        return a+b;
//    }
//
//    @Override
//    public String toString() {
//        return super.toString();
//    }
}
