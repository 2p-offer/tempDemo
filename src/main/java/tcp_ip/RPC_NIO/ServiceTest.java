package tcp_ip.RPC_NIO;

import java.io.Serializable;

/**
 * Created by 2P on 19-1-4.
 */
public interface ServiceTest extends Serializable {
    String sayHello(String name);
}
