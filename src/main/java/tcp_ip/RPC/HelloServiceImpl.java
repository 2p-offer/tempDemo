package tcp_ip.RPC;

/**
 * Created by 2P on 19-1-4.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }
}
