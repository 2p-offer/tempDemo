//package futureSelf;
//
//import java.util.concurrent.*;
//
///**
// * Created by 2P on 19-3-12.
// */
//public class SimpleTest {
//    public static void main(String[] args) {
//
//        //TODO futre 用过,不能用完就忘了啊
////你用过啊!.你狠狠地用过一次啊,storage项目,存储kafka消费数据,分别至es,mysql,hbase.做到事务统一,所以要拿结果,你用过啊!!
//        FutureTask futureTask=new FutureTask(()->{
//            return null;
//        });
//        Future future=new Future() {
//            @Override
//            public boolean cancel(boolean mayInterruptIfRunning) {
//                return false;
//            }
//
//            @Override
//            public boolean isCancelled() {
//                return false;
//            }
//
//            @Override
//            public boolean isDone() {
//                return false;
//            }
//
//            @Override
//            public Object get() throws InterruptedException, ExecutionException {
//                return null;
//            }
//
//            @Override
//            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
//                return null;
//            }
//        };
//
//        ScheduledFuture scheduledFuture=new ScheduledFuture() {
//            @Override
//            public long getDelay(TimeUnit unit) {
//                return 0;
//            }
//
//            @Override
//            public int compareTo(Delayed o) {
//                return 0;
//            }
//
//            @Override
//            public boolean cancel(boolean mayInterruptIfRunning) {
//                return false;
//            }
//
//            @Override
//            public boolean isCancelled() {
//                return false;
//            }
//
//            @Override
//            public boolean isDone() {
//                return false;
//            }
//
//            @Override
//            public Object get() throws InterruptedException, ExecutionException {
//                return null;
//            }
//
//            @Override
//            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
//                return null;
//            }
//        };
//    }
//}
