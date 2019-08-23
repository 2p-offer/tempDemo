package suanfa_formLiu;

/**
 * @Author: 2p
 *
请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 * @Date: 2019-08-23 17:54
 */
public class Day823TransKongGe {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("we are the one");
        String res = getRes(sb);
        System.out.println(res);
    }

    private static String getRes(StringBuffer sb) {
        char c;
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < sb.length(); i++) {
            c = sb.charAt(i);
            if (c == ' ') {
                res.append("%20");
            } else {
                res.append(c);
            }

        }
        return res.toString();
    }
}
