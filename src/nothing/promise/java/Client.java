package nothing.promise.java;

import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Created by 宸笙 on 2019/1/30.
 */
public class Client {

    public static void main(String[] args) {


        new Helper<Integer>()
                .doInAsync(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        Thread.sleep(4000);
                        System.out.println("after handle");
                        return 1024;
                    }
                })
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) {
                        System.out.println("pre :" + integer);
                        return integer * 2;
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        System.out.println("pre :" + integer);
                        return integer.toString();
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        System.out.println("pre :" + s);
                        return s + "!!!";
                    }
                })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        System.out.println("pre :" + s);
                        return s.length();
                    }
                })
                .then(new DataCall<Integer>() {
                    @Override
                    public void onCall(Integer data) {
                        System.out.println("downstream of data :" + data);
                    }
                });

    }
}
