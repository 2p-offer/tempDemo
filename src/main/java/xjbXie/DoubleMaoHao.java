package xjbXie;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * java 8 下 ::  的使用,表示一种引用,比如 静态方法的引用,构造器的引用
 * Created by 2P on 19-3-28.
 */
public class DoubleMaoHao {
    public static String hh="hhe";
    public String haha="haha";
    public static void sayHello(int s){
        System.out.println(s+",say: hello");
    }

    public static String sayHi(String s){
        return s+" hi";
    }

    public static String s="wangyan";

    public static void main(String[] args) {
//        Consumer<Integer> methodParam =DoubleMaoHao::sayHello;
//        methodParam.accept(1);


        //报错,accept 返回void
//        Consumer<String> methodParam =DoubleMaoHao::sayHi;
//        System.out.println(methodParam.accept("wy"))
        DoubleMaoHaoSon s=new DoubleMaoHaoSon();
        List<Integer> list=new ArrayList();
        list.add(1);
        list.add(2);
        s.sayFather(list);

    }

}
