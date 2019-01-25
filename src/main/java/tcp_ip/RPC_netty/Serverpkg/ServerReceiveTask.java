package tcp_ip.RPC_netty.Serverpkg;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.beanutils.MethodUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tcp_ip.RPC_netty.beans.MessageRequest;
import tcp_ip.RPC_netty.beans.MessageResponse;
import tcp_ip.RPC_netty.commonUtils.ZKUtils;

import java.lang.reflect.Method;

/**
 * Created by 2P on 19-1-24.
 */
public class ServerReceiveTask implements Runnable {
    private MessageRequest request;
    private MessageResponse response;
    private ChannelHandlerContext ctx;
    private ZKUtils zkUtils;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    ServerReceiveTask(MessageRequest request, MessageResponse response, ZKUtils zkUtils, ChannelHandlerContext ctx) {
        this.request = request;
        this.response = response;
        this.zkUtils = zkUtils;
        this.ctx = ctx;
    }

    public void run() {
        response.setMessageId(request.getMessageId());
        try {
            Object result = reflect(request);

            response.setResult(result);
        } catch (Throwable t) {
            response.setError(t.toString());
            LOGGER.error("RPC Server invoke error!\n");
        } finally {
            ctx.writeAndFlush(response).addListener((ChannelFuture channelFuture) -> {
                LOGGER.info("RPC Server Send message-id respone:" + request.getMessageId());
            });
        }
    }

    private Object reflect(MessageRequest request) throws Throwable {
        String className = request.getClassName();
        Class aClass = zkUtils.getData(className);
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?>[] types = request.getTypeParameters();
        if (aClass == null) {
            return null;
        }
//                zk得到的序列化数据,反射执行
        Method methods = aClass.getMethod(methodName, types);
        Object invoke = methods.invoke(aClass.newInstance(), parameters);
        LOGGER.info("server invoke result:" + invoke);

        return invoke;
    }

    public MessageResponse getResponse() {
        return response;
    }

    public MessageRequest getRequest() {
        return request;
    }

    public void setRequest(MessageRequest request) {
        this.request = request;
    }

}
