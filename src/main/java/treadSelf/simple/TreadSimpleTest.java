package treadSelf.simple;

/**
 * Created by 2P on 18-12-11.
 */
public class TreadSimpleTest {

    public  static  int count=0;

    public synchronized static void addCount(String threadName){
        count++;
        System.out.println(threadName+count);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(() -> {
           addCount("thread");
        });
        Thread thread2=new Thread(() -> {
            addCount("thread2");
        });
        thread.start();
        thread2.start();
    }
}
