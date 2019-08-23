package suanfa_formLiu;

/**
 * @Author: 2p
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 * @Date: 2019-08-23 18:00
 */
public class Day823Jump {
    public static void main(String[] args) {
        int sum=5;
        int res=getJumpTimes(sum);
        System.out.println(res);
    }

    private static int getJumpTimes(int sum) {
        if(sum==1){
            return 1;
        }
        if(sum==2){
            return 2;
        }
        return getJumpTimes(sum-1)+getJumpTimes(sum-2);
    }
}
