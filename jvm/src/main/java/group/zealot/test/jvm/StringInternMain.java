package group.zealot.test.jvm;

public class StringInternMain {
    
    public static void main(String[] args) {
        stringIntern();
    }
    
    public static void stringIntern() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        String str2 = "java";//new StringBuilder("ja").append("va").toString();
        
        System.out.println(str1.intern() == str1);
        System.out.println(str2.intern() == str2);
    }
}
