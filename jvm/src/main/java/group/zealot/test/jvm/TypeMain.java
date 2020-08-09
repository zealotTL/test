package group.zealot.test.jvm;

public class TypeMain {
    static double db = 0.0D;
    static float fl;
    static char ch;
    static byte by;

    /**
     * byte ------- -------- -------- 11111111
     * short ------ -------- 11111111 11111111
     * int 11111111 11111111 11111111 11111111
     * char ------- -------- 11111111 11111111
     */
    public static void main(String[] args) {
        System.out.println(db);
        System.out.println(fl);
        System.out.println(ch);
        System.out.println(by);
        {
            byte by = 1;
            int i = (int) by;
            System.out.println(by);
            System.out.println(i);
        }
        {
            int i = 128; // 00000000 00000000 00000001 11111111
            System.out.println(Integer.toBinaryString(i));
            byte by = (byte) i;// 11111111
            System.out.println(Integer.toBinaryString(by));
            System.out.println(by);
            System.out.println(i);
        }
        {
            System.out.println("==4==");
            System.out.println(Integer.toBinaryString(-128));
            System.out.println(Integer.toBinaryString(-127));
            System.out.println(Integer.toBinaryString(-1));
            System.out.println(Integer.toBinaryString(0));
            System.out.println(Integer.toBinaryString(1));
            System.out.println(Integer.toBinaryString(127));
            System.out.println(Integer.toBinaryString(128));
        }
    }
}
