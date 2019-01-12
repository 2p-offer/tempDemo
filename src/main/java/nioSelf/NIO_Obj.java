package nioSelf;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by 2P on 19-1-11.
 */
public class NIO_Obj {

    int INT_LENGTH = 4;

    /**
     *
     * <p>[机 能] 发送Object </p>
     *
     * @param obj 要发送的Object
     * @return 发送字节数
     * @throws IOException
     */
    public int writeObject(Object obj) throws IOException {
        ByteBuffer byteBuf = null;
        byteBuf = ByteBuffer.wrap(serializer(obj));
        return writeObject(byteBuf);
    }

    /**
     *
     * <p>[机 能] 发送Object </p>
     *
     * @param byteBuf ByteBuffer类型Object
     * @return 发送字节数
     * @throws IOException
     */
    public int writeObject(ByteBuffer byteBuf) throws IOException {
        byteBuf.rewind();
        int len = byteBuf.limit();
        ByteBuffer outBuf = ByteBuffer.allocate(INT_LENGTH + len);
        outBuf.putInt(len);
        outBuf.put(byteBuf);
        outBuf.flip();
//发送内容为： Object长度 + Object自身
        return write(outBuf);
    }

    private int write(ByteBuffer outBuf) {
        return 0;
    }

    /**
     *
     * <p>[机 能] 接收Object </p>
     *
     * @return 读取的Object
     * @throws IOException
     */
    public Object readObject() throws IOException {
        Object readObj = null;
        int bytes = -1;
        int bytesTotal = 0;
        ByteBuffer bufLen = null;
        ByteBuffer bufTemp = null;
        ByteBuffer bufMsg = null;

        /* 读取Object的长度 */
        bufLen = ByteBuffer.allocateDirect(INT_LENGTH);
        bufTemp = ByteBuffer.allocateDirect(INT_LENGTH);
        bytes = -1;
        bytesTotal = 0;
        /* 读取INT_LENGTH个字节作为Object的长度 */
        while (0 < (bytes = read(bufTemp))) {
            bytesTotal += bytes;
            bufTemp.flip();
            bufLen.put(bufTemp);
            if (bytesTotal < INT_LENGTH) {
// 长度读取未完
                bufTemp = ByteBuffer.allocateDirect(INT_LENGTH - bytesTotal);
            } else {
                break;
            }
        }
        if (0 == bytesTotal) {
            return null;
        }
        if (bytesTotal < INT_LENGTH) {
// 读取到的字节数少于约定的字节数，作为通信ERROR处理
            throw new IOException("Object长度读取出错。目前读取字节数："
                    + bytesTotal);
        }

        int len = 0;
        try {
            bufLen.flip();
            len = bufLen.getInt();
        } catch (Exception e) {
            throw new IOException("读取到的字节非Object长度");
        }
        if (0 == len) {
            return null;
        }

        /* 读取Object自身 */
        bufTemp = ByteBuffer.allocate(len);
        bufMsg = ByteBuffer.allocate(len);
        bytes = -1;
        bytesTotal = 0;
        /* 读取len（前面取到的长度）个字节作为Object自身 */
        while (0 < (bytes = read(bufTemp))) {
            bytesTotal += bytes;
            bufTemp.flip();
            bufMsg.put(bufTemp);
            if (bytesTotal < len) {
// Object自身读取未完了
                bufTemp = ByteBuffer.allocateDirect(len - bytesTotal);
            } else {
                break;
            }
        }
        if (bytesTotal < len) {
// 读取的字节数少于约定的字节数，作为通信ERROR处理
            throw new IOException("Object读取出错");
        }

        try {
            bufMsg.flip();
            byte[] buf = new byte[bufMsg.limit()];

            bufMsg.get(buf);
            readObj = deSerializer(buf);
        } catch (IOException ioe) {
// Object转换出错
            throw ioe;
        } catch (ClassNotFoundException cnfe) {
            throw new IOException("ByteBuffer到Object转换出错(ClassNotFoundException)");
        }

        return readObj;
    }

    private int read(ByteBuffer bufTemp) {
        return 0;
    }

    /**
     * <p>[机 能] 将Object转换成byte数组 </p>
     *
     * @param obj 需要转换的Object
     * @return 转换后的byte数组
     * @throws IOException
     */
    public static byte[] serializer(Object obj) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);

            oos.writeObject(obj);
            oos.flush();

            return baos.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    /**
     * <p>[机 能] 将byte数组转换成Object </p>
     *
     * @param org 需要转换的byte数组
     * @return 转换后的Object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object deSerializer(byte[] org) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(org));
        return ois.readObject();
    }

    public static void main(String[] args) {

        //test wrap==========================
//        ByteBuffer buffer = ByteBuffer.allocate(102400);
//        byte[] bytes = new byte[32];
//        System.out.println(buffer);
//        buffer = ByteBuffer.wrap(bytes);
//        System.out.println(buffer);
//
//        buffer = ByteBuffer.wrap(bytes, 10, 10);
//        System.out.println(buffer);
    }
}
