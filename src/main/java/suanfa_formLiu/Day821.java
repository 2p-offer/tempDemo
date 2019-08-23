package suanfa_formLiu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 2p
 * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示
 * @Date: 2019-08-21 11:43
 */
public class Day821 {

    public static void main(String[] args) {
        //1     1
        //2     10
        //3     11
        //4     100
        //5     101
        //6     110
        //7     111
        //8     1000
        //9     1001

        int tag=11111;
//        List<String> res=new ArrayList<>();
//        res=getNum(tag,res);
//        System.out.println(res);
//        System.out.println(numberOfOne2(-4));
        System.out.println(numberOfOne1(-4));
        Day821 day821=new Day821();

    }

    private static List<String> getNum(int tag, List<String> res) {

        int chushu=tag/2;
        int yushu=tag%2;
        if(yushu==1){
            res.add("1");
        }else {
            res.add("0");
        }
        if(chushu==0){
            return res;
        }
        res=getNum(chushu,res);
        return res;


    }

    public static int numberOfOne1(int n) {
        int count = 0;
        String str = Integer.toBinaryString(n);

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                count++;
            }
        }

        return count;
    }

    public static int numberOfOne2(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }
}
