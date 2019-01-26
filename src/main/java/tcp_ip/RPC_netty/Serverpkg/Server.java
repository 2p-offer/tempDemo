package tcp_ip.RPC_netty.Serverpkg;

import java.io.IOException;

/**
 * Created by 2P on 19-1-4.
 */
public interface Server {
    void register(String serverName, Class impl) throws Exception;

    void start() throws IOException;


}
