package chapter2;

public class GoodSuspend {

    static Object lock = new Object();

    static class ChangeObjectThread extends Thread {

        volatile boolean suspendMe = false;

        public void suspendMe(){
            this.suspendMe = true;
        }

        public void resumeMe(){
            suspendMe = false;
            synchronized (this){

                notify();
            }
        }

        @Override
        public void run() {

            while (true){
                //如果suspendMe为true，调用wait方法阻塞当前线成；否则，就执行业务逻辑
                synchronized (this) {
                    while (suspendMe) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    synchronized (lock) {
                        System.out.println("in ChangeObjectThread!");
                    }

                    Thread.yield();
                }
            }
        }
    }

    static class ReadObjectThread extends Thread{

        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    System.out.println("in ReadObjectLock!");
                    Thread.yield();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ChangeObjectThread t1 = new ChangeObjectThread();
        ReadObjectThread t2 = new ReadObjectThread();
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t1.suspendMe();
        System.out.println("=====================》suspend t1 2 second");
        Thread.sleep(2000);
        System.out.println("resume t1");
        t1.resumeMe();
    }
}


