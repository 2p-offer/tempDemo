package treadSelf.wait_notify_countDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 2P on 18-12-13.
 */
public class waitNotify {
    private volatile static List<String> list = new ArrayList<>();

    public void addList() {
        list.add("wangyan adding...");
    }

    public int size() {
        return list.size();
    }

//    public static void main(String[] args) {
//        final waitNotify wn = new waitNotify();
//
//
//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                wn.addList();
//                System.out.println("t1.add list....");
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//
//        Thread t2 = new Thread(() -> {
//            while (true) {
//                if (wn.size() == 5) {
//                    System.out.println("t2 捕获  size=5");
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        t2.start();
//        t1.start();
//    }
    //================================================================================

//    public static void main(String[] args) {
//        final waitNotify wn = new waitNotify();
//
//        final Object lock = new Object();
//
//        Thread t1 = new Thread(() -> {
//            synchronized (lock) {
//                for (int i = 0; i < 10; i++) {
//                    wn.addList();
//                    System.out.println("t1.add list....");
//                    try {
//                        Thread.sleep(200);
//                        if (wn.size() == 5) {
//                            System.out.println("t1 发出通知");
//                            lock.notify();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        });
//
//        Thread t2 = new Thread(() -> {
//            synchronized (lock) {
//                if (wn.size() != 5) {
//                    try {
//                        lock.wait();
//                        System.out.println("t2 捕获 size!=5");
//                        Thread.sleep(500);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("t2 bhuo   size =5");
//                    throw new RuntimeException();
//                }
//            }
//        });
//        t2.start();
//        t1.start();
//    }

    //====================================================================================================
    public static void main(String[] args) {
        final waitNotify wn = new waitNotify();

        final CountDownLatch countDownLatch = new CountDownLatch(1);


        Thread t1 = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    wn.addList();
                    System.out.println("t1.add list....");
                    try {
                        Thread.sleep(200);
                        if (wn.size() == 5) {
                            System.out.println("t1 发出通知");
                            countDownLatch.countDown();

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        });

        Thread t2 = new Thread(() -> {
                if (wn.size() != 5) {
                    try {
                        countDownLatch.await();
                        System.out.println("t2 捕获 size!=5");
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("t2 bhuo   size =5");
                    throw new RuntimeException();
                }
        });
        t2.start();
        t1.start();
    }

}
