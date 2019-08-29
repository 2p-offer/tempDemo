package suanfa_formLiu;

/**
 * @Author: 2p
 * 各种排序算法
 * @Date: 2019-08-22 16:13
 */
public class Day822PaiXu {
    public static void main(String[] args) {
        int[] ta = new int[]{1, 3, 1, 4, 2, 6, 5, 7};
//        int[] ta = maoPao(ta);
//        int[] ta = select(ta);
        quickSort(ta, 0, ta.length - 1);
        for (int h = 0; h < ta.length; h++) {
            System.out.println(ta[h]);
        }

    }

    public static int[] maoPao(int[] tmp) {
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp.length - 1 - i; j++) {
                if (tmp[j] > tmp[j + 1]) {
                    int itmp = tmp[j];
                    tmp[j] = tmp[j + 1];
                    tmp[j + 1] = itmp;
                }
            }
        }
        return tmp;
    }

    public static void quickSort(int[] arr, int low, int high) {

        int i,j,tmp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        tmp=arr[i];
        while(i<j){
            while(i<j&&arr[j]>=tmp){
                j--;
            }
            while(i<j&&arr[i]<=tmp){
                i++;
            }
            if(i<j){
                t=arr[i];
                arr[i]=arr[j];
                arr[j]=t;
            }
        }
        arr[low]=arr[i];
        arr[i]=tmp;

        quickSort(arr,low,i-1);
        quickSort(arr,i+1,high);


//        int i, j, tmp, t;
//        if (low > high) {
//            return;
//        }
//        j = high;
//        i = low;
//        tmp = arr[low];
//        while (i < j) {
//            while (arr[j] >= tmp && i < j) {
//                j--;
//            }
//            while (arr[i] <= tmp && i < j) {
//                i++;
//            }
//
//            if (i < j) {
//                t = arr[i];
//                arr[i] = arr[j];
//                arr[j] = t;
//            }
//        }
//        arr[low] = arr[i];
//        arr[i] = tmp;
//
//        quickSort(arr, low, j - 1);
//        quickSort(arr, i + 1, high);
    }


    public static int[] select(int[] tag) {
        int position = 0;
        for (int i = 0; i < tag.length; i++) {
            position = i;
            for (int j = i + 1; j < tag.length; j++) {
                if (tag[j] < tag[position]) {
                    position = j;
                }
            }
            int tmp = tag[i];
            tag[i] = tag[position];
            tag[position] = tmp;
        }
        return tag;
    }

}
