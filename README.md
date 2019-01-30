# PromiseInJava
implement promise in java

## Bad smell of callback--callback hell

## Inspired by promise in js

## Implement

## Async data flow 

## Easy to use 

```java
            Executor executor = Executors.newSingleThreadExecutor();
            new Helper<Integer>()
                    .doInAsync(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            Thread.sleep(4000);
                            System.out.println("after handle");
                            return 1024;
                        }
                    },executor)
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
                    .then(new Helper.DataCall<Integer>() {
                        @Override
                        public void onCall(Integer data) {
                            System.out.println("下游data：" + data);
                        }
                    });

```
