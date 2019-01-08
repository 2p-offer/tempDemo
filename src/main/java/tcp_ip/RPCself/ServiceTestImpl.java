package tcp_ip.RPCself;

/**
 * Created by 2P on 19-1-4.
 */
public class ServiceTestImpl implements ServiceTest {
    @Override
    public String sayHello(String name) {
        return "hello "+name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
