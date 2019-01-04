package treadSelf.queue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 优先级队列
 * Created by 2P on 18-12-19.
 */
public class PriorityBlockingQueueTest {

    public static void main(String[] args) {
        PriorityBlockingQueue<MyTask> priorityBlockingQueue=new PriorityBlockingQueue();
        MyTask myTask1=new MyTask();
        myTask1.setId(3);
        myTask1.setName("3--");
        priorityBlockingQueue.add(myTask1);
        MyTask myTask2=new MyTask();
        myTask2.setId(1);
        myTask2.setName("1--");
        priorityBlockingQueue.add(myTask2);
        MyTask myTask3=new MyTask();
        myTask3.setId(2);
        myTask3.setName("2--");
        priorityBlockingQueue.add(myTask3);

        System.out.println("before--"+priorityBlockingQueue);
        try {
            System.out.println(priorityBlockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after--"+priorityBlockingQueue);

    }

}
