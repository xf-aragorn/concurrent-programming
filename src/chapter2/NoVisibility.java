package chapter2;

public class NoVisibility {
    private static /*volatile*/ boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                //这里是否执行yield()，结果是不一样的：
                // 执行yield()，64位JDK下，循环会退出
                Thread.yield();
                //没有yield()，64位JDK：会一直执行空循环，不会退出！！！main线程里对ready的改动在这里不可见！ 但如果ready声明为volatile，会即时退出
                //; 没有任何逻辑，执行空循环
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number = 42;
        ready = true;
        Thread.sleep(1000);
    }
}
