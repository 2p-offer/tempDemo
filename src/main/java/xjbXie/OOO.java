package xjbXie;

/**
 * Created by 2P on 19-1-30.
 */
public class OOO {

    public static void main(String[] args) {
//        String s1 = "34567890qwer你好tyuiopasdfghjkl;";
//        int i = s1.getBytes().length;
//        if (i > 24) {
//            System.out.println(i + "  大于24");
//            String substring = s1.substring(i - 24);
//            System.out.println(substring+"   substring."+substring.getBytes().length+"   "+substring.length());
//        }
//        System.out.println("end...");

        byte[] iv = new byte[]{93, 81, 122, 33, 47, 50, 17, 103};
        String s=new String(iv);
        System.out.println(s);
    }
}
