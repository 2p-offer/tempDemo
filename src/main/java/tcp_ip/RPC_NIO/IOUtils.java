package tcp_ip.RPC_NIO;

import org.omg.CORBA.OBJ_ADAPTER;

import java.io.*;

/**
 * Created by 2P on 19-1-11.
 */
public class IOUtils {
    public static  byte[] Obj2Bytes(Object impl) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream obj = new ObjectOutputStream(baos);
        obj.writeObject(impl);
        bytes = baos.toByteArray();
        baos.close();
        obj.close();
        return bytes;
    }

    public static Object byte2Obj(byte[] bytes) throws Exception {
        ByteArrayInputStream bais =new ByteArrayInputStream(bytes);
        ObjectInputStream ois =new ObjectInputStream(bais);
        Object o = ois.readObject();
        bais.close();
        ois.close();
        return o;
    }

    public static void main(String[] args) throws Exception {
        ServiceTest serviceTest=new ServiceTestImpl();
        byte[] bytes = IOUtils.Obj2Bytes(serviceTest);
        ServiceTest serviceTest1 = (ServiceTest)IOUtils.byte2Obj(bytes);
        System.out.println(serviceTest1.sayHello("we"));

    }


}
