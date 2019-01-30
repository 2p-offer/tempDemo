package JdkDemo.en_decrypt;

/**
 * Created by 2P on 19-1-30.
 */
public class MuiltTest {
    public static void main(String[] args) {
        try {
            String tag="wangyan.123";
            System.out.println("before en_de:"+tag);
            byte[] encoder = Encrypt.encoder(tag);
            System.out.println(new String(encoder));
            String decode = Decrypt.decode(encoder);
            if(tag.equals(decode)) {
                System.out.println("after en_de:" + decode);
            }
            System.out.println("test  end________");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
