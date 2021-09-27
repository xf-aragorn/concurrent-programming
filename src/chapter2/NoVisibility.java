package chapter2;

public class NoVisibility {
    private static /*volatile*/ boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                //杩欓噷鏄惁鎵цyield()锛岀粨鏋滄槸涓嶄竴鏍风殑锛�
                // 鎵цyield()锛�64浣岼DK涓嬶紝寰幆浼氶��鍑�
                Thread.yield();
                //娌℃湁yield()锛�64浣岼DK锛氫細涓�鐩存墽琛岀┖寰幆锛屼笉浼氶��鍑猴紒锛侊紒main绾跨▼閲屽ready鐨勬敼鍔ㄥ湪杩欓噷涓嶅彲瑙侊紒 浣嗗鏋渞eady澹版槑涓簐olatile锛屼細鍗虫椂閫�鍑�
                //; 娌℃湁浠讳綍閫昏緫锛屾墽琛岀┖寰幆
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