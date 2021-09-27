package chapter2;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * https://blog.csdn.net/LONG_Yi_1994/article/details/83143123
 *璇存槑:
 *璋冪敤浜唖uspend鏂规硶锛屽皢t0绾跨▼鎸傝捣锛屼絾鏄嚭鐜扮殑闂鏄紝t0.suspend鏂规硶涔嬪悗鐨勪唬鐮佷笉鎵ц浜嗭紝鎼炰簡鍗婂ぉ缁堜簬鐭ラ亾涓轰粈涔堜簡锛�
 *鍥犱负鍦╰0閲岄潰浣跨敤浜哠ystem.out.println鏂规硶浜嗭紝鏌ョ湅println鏂规硶鐨勬簮浠ｇ爜鍙戠幇浠栨槸涓悓姝ョ殑鏂规硶锛屽姞閿佷簡锛岃繖涓攣鏄摢涓憿锛�
 *瀵瑰氨鏄疨rintStream,鍦∕ain涓殑printCurrentAliveThread鏂规硶涓敤鍒颁簡System.out.println鏂规硶锛屾墦鏂偣鎵嶇煡閬�
 *鎼炰簡鍗婂ぉ闃诲鍦ㄨ繖閲屼簡锛屽洜涓烘垜浠煡閬搒uspend鏂规硶鏄笉閲婃斁閿佺殑锛屾墍浠ュ鑷翠細闃诲鍦╬rintln鏂规硶涓紝浣嗘槸鏈変竴涓墠鎻愭槸t0绾跨▼鍜宮ain绾跨▼
 *鐨刾rintln鏂规硶涓嬁鍒扮殑鏄悓涓�涓攣锛岃繖鏃跺�欏湪鐪嬩竴涓婼ystem.out鍙橀噺鏃朵竴涓猻tatic PrintStream锛岃繖鏃跺�欏氨鏄庝簡浜嗭紝鍥犱负鏄痵tatic
 *鎵�浠ュ璞℃槸鐩稿悓鐨勶紝杩欎袱涓嚎绋嬫嬁鍒扮殑System.out鏄悓涓�涓璞★紝鎵�浠ヨ繖涓や釜绾跨▼涔熸槸鎷垮埌鐨勬槸鐩稿悓鐨勯攣鐨勩��
 *
 */


public class SuspendDemo1 {

    public static void main(String[] args) {
        try {
            //瀹氫箟绾跨▼
            Thread t0 = new Thread() {
                public void run() {
                    try {
                        for(long i=0;i<1000*1000*10;i++){
                            System.out.println(i);
                        }
                        System.out.println("thread death");
                    } catch (Throwable ex) {
                        System.out.println("Caught in run: " + ex);
                        ex.printStackTrace();
                    }
                }
            };
            //寮�鍚嚎绋�
            t0.start();
            //绛夊緟2s涔嬪悗鎸傝捣绾跨▼t0
            Thread.sleep(2*1000);
            //鎸傝捣绾跨▼
            t0.suspend();
            //鎵撳嵃褰撳墠鐨勬墍鏈夌嚎绋�
            printCurrentAliveThread();
            //绛夊緟2s涔嬪悗鎭㈠绾跨▼
            Thread.sleep(2*1000);
            //澶嶆椿绾跨▼
            t0.resume();

        } catch (Throwable t) {
            System.out.println("Caught in main: " + t);
            t.printStackTrace();
        }


    }

    /**
     * 鎵撳嵃褰撳墠绾跨▼
     */
    public static void printCurrentAliveThread(){
        Map<Thread, StackTraceElement[]> maps = Thread.getAllStackTraces();
        Set<Entry<Thread, StackTraceElement[]>> set = maps.entrySet();
        Iterator<Entry<Thread,StackTraceElement[]>> iterator = set.iterator();
        System.out.println("System Alive Thread List:");
        System.out.println("-------------------------");
        while(iterator.hasNext()){
            System.out.println("AliveThread_Name:"+iterator.next().getKey().getName());
        }
        System.out.println("-------------------------");
    }

}