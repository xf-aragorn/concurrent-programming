package chapter1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class ThreadStatusDemo {

        /**
         * 每个线程执行的轮次
         */
        public static final long MAX_TURN = 5;

        /**
         * 线程编号
         */
        static int threadSeqNumber = 0;

        /**
         * 全局的静态线程列表
         */
        static List<Thread> threadList = new ArrayList<>();

        /**
         * 输出静态线程列表中，每个线程的状态
         */
        private static void printThreadStatus() {
            for (Thread thread : threadList) {
                System.out.println(thread.getName() + "状态为： " + thread.getState());
            }
        }

        /**
         * 向全局的静态线程列表加入线程
         */
        private static void addStatusThread(Thread thread) {
            threadList.add(thread);
        }

        public static void sleepMilliSeconds(int millisecond) {
            LockSupport.parkNanos(millisecond * 1000L * 1000L);
        }

        static class StatusDemoThread extends Thread {
            public StatusDemoThread() {
                super("statusPrintThread" + (++threadSeqNumber));
                //将自己加入到全局的静态线程列表
                addStatusThread(this);
            }

            @Override
            public void run() {
                System.out.println(getName() + ", 状态为：" + getState());
                for (int turn = 0; turn < MAX_TURN; turn++) {
                    // 线程睡眠
                    sleepMilliSeconds(500);
                    // 输出所有线程的状态
                    printThreadStatus();
                }
                System.out.println(getName() + "- 运行结束。");
            }
        }

        public static void main(String[] args) throws IOException {
            // 将 main 线程加入全局列表
            addStatusThread(Thread.currentThread());
            // 新建三条线程，这些线程在构造器中会将自己加入全局列表
            Thread sThread1 = new StatusDemoThread();
            System.out.println((sThread1.getName() + "- 状态为" + sThread1.getState()));
            Thread sThread2 = new StatusDemoThread();
            System.out.println((sThread2.getName() + "- 状态为" + sThread1.getState()));
            Thread sThread3 = new StatusDemoThread();
            System.out.println((sThread3.getName() + "- 状态为" + sThread1.getState()));

            //启动第一个线程
            sThread1.start();
            //等待 500ms 启动第二个线程
            sleepMilliSeconds(500);
            sThread2.start();

            //等待 500ms 启动第三个线程
            sleepMilliSeconds(500);
            sThread3.start();

            sleepMilliSeconds(1000);

        }



    }

