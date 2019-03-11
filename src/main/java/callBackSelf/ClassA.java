package callBackSelf;

/**
 * Created by 2P on 19-3-11.
 */
public class ClassA implements MyCallBack
{

    public static void main(String[] args) {
        ClassA a=new ClassA();
        ClassB.tryReceive("wangyan",a);
        ClassB.tryReceive("",a);
    }
    @Override
    public void onSuccess(String s) {
        System.out.println(s+" receive success");
    }

    @Override
    public void onError(String s) {
        System.out.println(s+" receive error");

    }
}
