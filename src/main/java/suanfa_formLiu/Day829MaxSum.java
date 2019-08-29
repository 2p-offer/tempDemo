package suanfa_formLiu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: 2p
 * 在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。
 * 但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？例如:{6,-3,-2,7,-15,1,2,2},
 * 连续子向量的最大和为8(从第0个开始,到第3个为止)。
 * 给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是1)
 * @Date: 2019-08-29 10:50
 */
public class Day829MaxSum {
    public static void main(String[] args) {
        Day829MaxSum sum=new Day829MaxSum();
        int[] tag={6,-3,-2,7,-1,1,2,2};
//        int i = sum.FindGreatestSumOfSubArray(tag);
        int maxSum = getMaxSum(tag);
        System.out.println(maxSum);
    }

    private static int getMaxSum(int[] tag){
        List<Integer> container=new ArrayList<>();
        int tmp=tag[0];
        container.add(tmp);
        for(int i=1;i<tag.length;i++){
            tmp=tmp+tag[i];
            container.add(tmp);
        }
        Collections.sort(container);
        return container.get(container.size()-1);
    }

    private static List<String> getAllSum(int[] tag, int i) {
        int tmp=tag[i];
        while(tag[i+1]<tag[i]&&i<tag.length-1){
            tmp=tmp+tag[i+1];
        }
        return null;
    }

    public int FindGreatestSumOfSubArray(int[] array) {
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<array.length;i++){
            int sum=0;
            for(int j=i;j<array.length;j++){
                sum+=array[j];
                list.add(sum);
            }
        }
        if(list==null) return 0;
        Collections.sort(list);
        return list.get(list.size()-1);
    }
}
