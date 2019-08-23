package suanfa_formLiu;

/**
 * @Author: 2p
 * 水仙花
 * @Date: 2019-08-21 16:41
 */
public class Day821ShuiXianHua {
    public static void main(String[] args) {
        for(int i=100;i<1000;i++){
            int ge=i%10;
            int shi=(i/10)%10;
            int bai=i/100;
            if(Math.pow(ge,3)+Math.pow(shi,3)+Math.pow(bai,3)==i){
                System.out.println(i);
            }
        }

    }
}
