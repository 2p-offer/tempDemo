package treadSelf.queue;

import java.util.LinkedList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 2P on 18-12-14.
 */
public class QueueSimple {

    //主容器
    private LinkedList<Object> list=new LinkedList<>();

    //计数器
    private AtomicInteger count=new AtomicInteger();

    //最小值
    private final int minCount=0;

    //最大值
    private final int maxCount;

    //锁
    public Object lock =new Object();

    public QueueSimple(int maxCount){
        this.maxCount=maxCount;
    }

    public int getSize(){
        return count.get();
    }

    public void put(Object obj){

        synchronized (lock){
            //如果当前数量和最大值相等.等待数量减少
            while(count.get()>=maxCount){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(obj);
            count.incrementAndGet();
            lock.notify();
            System.out.println("新添加了一个元素:"+obj.toString());
        }
    }

    public Object get(){
        Object res=null;
        synchronized (lock){
            while(count.get()<=minCount){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            res = list.removeFirst();
            count.decrementAndGet();
            lock.notify();

        }
        return res;
    }

    public static void main(String[] args) {
        final QueueSimple queueSimple=new QueueSimple(3);
        queueSimple.put("123");
        queueSimple.put("456");
        queueSimple.put("789");
        Thread t1=new Thread(()->{
           queueSimple.put("123");
        });
        t1.start();
        Thread t2=new Thread(()->{
            Object o = queueSimple.get();
            System.out.println("删除元素:"+o.toString());
        });

        try {
            TimeUnit.SECONDS.sleep(2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        System.out.println(queueSimple.getSize());
    }

}
