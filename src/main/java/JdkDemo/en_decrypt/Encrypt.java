package JdkDemo.en_decrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by 2P on 19-1-30.
 */
public class Encrypt {

    /**
     *
     * @param tag          //待加密的字符串
     * @return
     * @throws Exception
     */
    public static byte[] encoder(String tag) throws Exception{
        //秘钥;(AES 的必要长度为 16/24/32    des长度为8 bytes)
        String secret="wangyang";
        //向量参数字节(DES 为8字节,AES为16bytes)
        byte[] iv="abcdefgh".getBytes();
        System.out.println(iv.length);
        //base64一层加tag;
        //初始化向量参数
        IvParameterSpec ivParameterSpec=new IvParameterSpec(iv);
        //初始化加密类()
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        SecretKeySpec keyDes = new SecretKeySpec(secret.getBytes(), "DES");
        cipher.init(Cipher.ENCRYPT_MODE,keyDes,ivParameterSpec);
        byte[] bytes = cipher.doFinal(tag.getBytes());
        byte[] enSecret = Base64.getEncoder().encode(bytes);
        return enSecret;
    }

}
