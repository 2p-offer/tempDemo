package suanfa_formLiu;

/**
 * @Author: 2p
 *  对于一个字符串，请设计一个高效算法，找到第一次重复出现的字符。
 *  给定一个字符串(不一定全为字母)A及它的长度n。请返回第一个重复出现的字符。保证字符串中有重复字符，字符串的长度小于等于500。
 *
 *  本地测试没有问题，在线编辑，测试不通过。
 * @Date: 2019-08-29 11:16
 */
public class Day829GetFirstRepeat {
    public static void main(String[] args) {        
        String s="qywyer23tdd";
        Day829GetFirstRepeat day829GetFirstRepeat=new Day829GetFirstRepeat();
        char firstRepeat = day829GetFirstRepeat.findFirstRepeat(s, s.length());
        System.out.println(firstRepeat);
    }

    public char findFirstRepeat(String A, int n) {
       for(int i=0;i<A.length();i++){
           int i1 = A.indexOf(A.charAt(i), i + 1);
           if(i1>0){
               return A.charAt(i);
           }
       }
       return 0;
    }
}
