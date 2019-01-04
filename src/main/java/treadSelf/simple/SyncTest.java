package treadSelf.simple;

/**
 * Created by 2P on 18-12-12.
 */
public class SyncTest {
    public int i = 10;

    public synchronized void whileTrue1(String threadName) {
        int i = 0;
        while (true) {
            System.out.println(threadName + ".111.." + i++);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //whileTrue1 加了锁,whileTrue2 没加, 线程一获得了对象的锁并不能阻止另一个线程调用没有加锁同一个对象下的whileTrue2
    //whileTrue1 加了锁,whileTrue2 也加了.线程一获得了对象锁,线程二调用同样加锁的whileTrue2 会阻塞.
    public  void whileTrue2(String threadName) {
//    public synchronized   void whileTrue2(String threadName) {
        int i = 0;
        while (true) {
            System.out.println(threadName + ".2222.." + i++);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        //对象锁,同一个对象,才会同步.实例化俩对象,就没有关系了

        SyncTest syncTest = new SyncTest();
        Thread t1 = new Thread(() -> {
            syncTest.whileTrue1("t1");
        }, "t1");
        Thread t2 = new Thread(() -> {
            syncTest.whileTrue2("t2");
        }, "t2");
        t2.start();
        t1.start();


    }

}
