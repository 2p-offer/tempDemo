package JdkDemo.en_decrypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by 2P on 19-1-30.
 */
public class Decrypt {

    public static String decode(byte[] tag) throws Exception {

        byte[] decode = Base64.getDecoder().decode(tag);
        //秘钥;(AES 的必要长度为 16/24/32    des长度为8 bytes)
        String secret="wangyang";
        //向量参数字节(DES 为8字节,AES为16bytes)
        byte[] iv="abcdefgh".getBytes();
        IvParameterSpec ivSpec=new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec=new SecretKeySpec(secret.getBytes(),"DES");
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivSpec);
        byte[] bytes = cipher.doFinal(decode);
        return new String(bytes);
    }

}
