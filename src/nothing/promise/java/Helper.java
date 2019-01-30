package nothing.promise.java;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * Created by 宸笙 on 2019/1/30.
 */
public class Helper<T> {
    private T value;
    private CountDownLatch latch = new CountDownLatch(1);
    private Executor defaultExecutor = Executors.newSingleThreadExecutor();

    /**
     * do async(timeConsuming) operation
     * @param callable
     * @return
     */
    public Helper<T> doInAsync(Callable<T> callable){
        return doInAsync(callable,defaultExecutor);
    }

    /**
     * do async(timeConsuming) operation
     * @param callable
     * @param executor
     * @return self will be easy to implement chain call
     */
    public Helper<T> doInAsync(final Callable<T> callable,Executor executor){

        try {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Helper.this.value  = callable.call();
                        // handle out of sync between producer and consumer by use @CountDownLatch
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * one of transforms api of handle async data flow : map
     * such as from Helper<Integer> to Helper<String>
     * @param function since Java8
     * @param <V> what type you want to convert
     * @return
     */
    public <V>Helper<V> map(Function<T,V> function){
        try {
            latch.await();
            Helper<V> dest = new Helper<V>();
            final V apply = function.apply(Helper.this.value);
            dest.doInAsync(new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return apply;
                }
            });
            return dest;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * after rounds of process data finally notify data to observer
     * @param dataCall
     * @return
     */
    public Helper<T> then(DataCall<T> dataCall){
        // response to point that handle out of sync between producer and consumer by use @CountDownLatch
        try {
            latch.await();
            dataCall.onCall(Helper.this.value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }


}
