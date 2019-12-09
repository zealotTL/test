package group.zealot.test.shell;

/**
 * @author zealot
 * @date 2019/11/18 21:40
 */
public class A {
    int num = 0;

    class B {
        void add() {
            num++;
        }
    }

    public static void main(String[] args) {
        A a = new A();
        B b1 = a.new B();
        B b2 = a.new B();
        b1.add();
        b2.add();
        System.out.println(a.num);
    }
}
