package treadSelf.subAndMain;
/**
 * Created by 2P on 18-12-12.
 */
public class SubAndMain {
    public static void main(String[] args) {

        Sub sub = new Sub();
        SubAndMain subAndMain=new SubAndMain();
        Thread t1 = new Thread(() -> {
            try {
                sub.subOut();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                sub.subOut();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
    static class Main {
        int i = 10;
        public synchronized  void mainOut() throws InterruptedException {
            i--;
            System.out.println("main out :" + i);
            Thread.sleep(100);
        }
    }
    static class Sub extends Main {
        public synchronized void subOut() throws InterruptedException {
            while (i > 0) {
                i--;
                System.out.println("sub out :" + i);
                Thread.sleep(100);
                mainOut();
            }
        }
    }
}
