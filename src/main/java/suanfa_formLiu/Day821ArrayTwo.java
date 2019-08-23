package suanfa_formLiu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: 2p
 * @Date: 2019-08-21 17:16
 */
public class Day821ArrayTwo {

    public static void main(String[] args) {

        Day821ArrayTwo day821SuShu=new Day821ArrayTwo();
        int[] array={1,2,3,3,1,4};
        int[] num1=new int[1];
        int[] num2=new int[1];
        day821SuShu.FindNumsAppearOnce(array,num1,num2);
    }

    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        Set<Integer> cacheSet=new HashSet<>();
        for(int i=0;i<array.length;i++){
            if(cacheSet.contains(array[i])){
                cacheSet.remove(array[i]);
            }else{
                cacheSet.add(array[i]);
            }
        }


        Iterator<Integer> iterator = cacheSet.iterator();
        num1[0]=iterator.next();
        num2[0]=iterator.next();
        System.out.println(num1[0]+":"+num2[0]);

    }
}
