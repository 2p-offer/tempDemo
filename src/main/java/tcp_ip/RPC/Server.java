package tcp_ip.RPC;

import java.io.IOException;

/**
 * Created by 2P on 19-1-4.
 */
public interface Server {
    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    public int getPort();
}
