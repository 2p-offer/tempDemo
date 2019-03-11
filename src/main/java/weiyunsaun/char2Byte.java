package weiyunsaun;

/**
 * Created by 2P on 19-3-6.
 */
public class char2Byte {
    public static void main(String[] args) {
        char c='我';
        byte[] bytes = charToByte(c);
        System.out.println(new String(bytes));
    }
    static byte[] charToByte(char c){
        byte[] b=new byte[2];
        b[0]=(byte)((c&0XFF00)>>8);
        b[1]=(byte)(c&0XFF);
        return b;
    }
}
