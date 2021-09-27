package chapter3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 13 on 2017/5/5.
 */
public class ReenterLockCondition implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {

        try {
            lock.lock();
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void main(String args[]) throws InterruptedException {
        ReenterLockCondition reenterLockCondition = new ReenterLockCondition();
        Thread thread1 = new Thread(reenterLockCondition);
        thread1.start();
        System.out.println("睡眠2秒钟");
        Thread.sleep(2000);
        lock.lock();
        condition.signal();
        System.out.println("虽然signal了，但是还没有释放锁，休眠2秒");
        Thread.sleep(2000);
        //必须unlokc了，await的线程才能重新获得锁！
        lock.unlock();
        System.out.println("signal后现在unlock了，main线程休眠2秒");
        Thread.sleep(2000);
        System.out.println("main线程休眠2秒结束");

    }
}