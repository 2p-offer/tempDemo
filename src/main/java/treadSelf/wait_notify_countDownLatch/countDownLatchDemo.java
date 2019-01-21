package treadSelf.wait_notify_countDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 2P on 19-1-18.
 */
public class countDownLatchDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(countDownLatchDemo.class);

    public static void main(String[] args) {

        for (int i = 0; i < 2; i++) {

            new Thread(() -> {
                while(true){
                    CountDownLatch countDownLatch = new CountDownLatch(2);
                    Map<String, String> map = new ConcurrentHashMap<>();

                    try {
                        new Thread(() -> {
                            for (int j = 0; j < 2; j++) {
                                try {
                                    map.put(String.valueOf(j), "1");
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("t1.1 countDown()");
                                    countDownLatch.countDown();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        System.out.println("t1,await()");
                        System.out.println(countDownLatch.toString());
                        countDownLatch.await();
                        if (map.size() == 2) {
                            System.out.println(map.size()+"t.sizee======2  ");
                        } else {
                            System.out.println(map.size()+"t.size !!!!!!!2  ");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }).start();

        }

    }
}
