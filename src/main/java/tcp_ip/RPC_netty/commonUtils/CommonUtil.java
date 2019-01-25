package tcp_ip.RPC_netty.commonUtils;

/**
 * Created by 2P on 19-1-12.
 */
public class CommonUtil {

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    /**
     * 数字前面补充零
     * @param sourceDate  原始数字
     * @param formatLength  结果长度
     * @return
     */
    public static String frontCompWithZore(int sourceDate, int formatLength) {

        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }


    public static void main(String[] args) {
//        int sou=123;
//        String s = CommonUtil.frontCompWithZore(sou, 10);
//        Integer integer = Integer.valueOf(s);
//        System.out.println(s);
//        System.out.println(integer);


//        byte[] bytes="123".getBytes();
//        byte[] bytes1="2222".getBytes();
//        byte[] bytes2 = CommonUtil.addBytes(bytes, bytes1);
//        String s=new String(bytes2);
//        System.out.println();

        Integer i=Integer.MAX_VALUE;
        System.out.println(i);
        byte[] bytes = i.toString().getBytes();
        System.out.println(bytes.length);


    }
}
