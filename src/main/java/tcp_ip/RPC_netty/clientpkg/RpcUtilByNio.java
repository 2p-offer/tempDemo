package tcp_ip.RPC_netty.clientpkg;


import tcp_ip.RPC_netty.beans.MessageCallBack;
import tcp_ip.RPC_netty.beans.MessageRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by 2P on 19-1-4.
 */
public class RpcUtilByNio<T> {

    public static <T> T getServiceByRpc(final Class<?> inter) {
        Object res = Proxy.newProxyInstance(inter.getClassLoader(), new Class[]{inter}, (Object proxy, Method method, Object[] args) -> {
            MessageRequest request = new MessageRequest();
            //创建请求体
            request.setMessageId(UUID.randomUUID().toString());
            request.setClassName(method.getDeclaringClass().getName());
            request.setMethodName(method.getName());
            request.setTypeParameters(method.getParameterTypes());
            request.setParameters(args);
            //发送请求
            ServerLoader instance = ServerLoader.getInstance();
            ClientHandler messageSendHandler = instance.getMessageSendHandler();
            MessageCallBack callBack = messageSendHandler.sendRequest(request);
            return callBack.start();
        });
        return (T) res;
    }
}

