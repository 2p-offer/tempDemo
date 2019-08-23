package suanfa_formLiu;

import java.util.*;

/**
 * @Author: 2p
 * <p>
 * 随机给一个整数，输出比它小的所有质数
 * <p>
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
 * @Date: 2019-08-21 16:50
 */
public class Day821SuShu {
    public static void main(String[] args) {

        int number = 911;
        getSuShu(number);

//        Day821SuShu day821SuShu=new Day821SuShu();
//        int[] array={1,2,3,3,1,4};
//        int[] num1=new int[1];
//        int[] num2=new int[1];
//        day821SuShu.FindNumsAppearOnce(array,num1,num2);

    }

//    private static void getSuShu(int number){
//        List<String> list =new ArrayList<String>();
//        for(int i=2; i<=number;i++){
//            list.add(Integer.toString(i));
//            for(int j=2;j<i;j++) {
//                int mo = i % j;
//                if (mo == 0) {
//                    if (list.contains(Integer.toString(i))) {
//                        list.remove(Integer.toString(i));
//                    }
//                }
//            }
//        }
//
//        for(int i =0;i<list.size();i++){
//            System.out.println(list.get(i));
//        }
//    }

    private static void getSuShu(int num) {
        List<String> res=new ArrayList<>();
        for (int i = 2; i <= num; i++) {
            res.add(String.valueOf(i));
            for(int j=2;j<i/2+1;j++){
                if(i%j==0){
                    res.remove(String.valueOf(i));
                }
            }

        }
        System.out.println(res.size());
    }
}
