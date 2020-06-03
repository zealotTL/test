package group.zealot.test.thread.dayOld;

import group.zealot.test.thread.MyThreadPoolManager;

public class TestFutureCanel {
    public static void main(String[] ags) throws Exception {
        MyThreadPoolManager in = MyThreadPoolManager.getInstance();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                boolean fg = false;
                while (!fg) {
                    System.out.println(i++);
                }

            }
        });
        thread.start();

        System.out.println();

//        thread.stop();
        thread.interrupt();

        System.exit(0);
    }


}
