package suanfa_formLiu;

/**
 * @Author: 2p
 * @Date: 2019-08-26 18:11
 */
public class Day826MiddleSearch {
    public static void main(String[] args) {

        int[] tag={1,2,3,4,5,6,7,8,10,11};
        int i=11;
        Day826MiddleSearch day826MiddleSearch=new Day826MiddleSearch();
        int res = day826MiddleSearch.getRes(tag, i);
        System.out.println(res);
        if(res!=-1){
            System.out.println(tag[res]==i);
        }
    }


    public int getRes(int[] tag,int i){
        int low=0;
        int high=tag.length-1;
        while(low<=high){
            int middle=(low+high)/2;
            if(tag[middle]==i){
                return middle;
            }
            if(tag[middle]>i){
                high=middle-1;
            }
            if(tag[middle]<i){
                low=middle+1;
            }
        }
        return -1;

    }
}
