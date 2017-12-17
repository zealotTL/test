package name.taolei.zealot.test.enumClass;

/**
 * Hello world!
 *
 */
public class EnumMain {
    public static void main(String[] args) {
        E e = E.A;
        e.sys();
        System.out.println(e.a = "123123");
        Enum e1 = e;
    }
}

enum E {
    A {
        public String a = "asdfadsf";
        
        public void sys() {
            System.out.println("A");
        }
    },
    B {
        public void sys() {
            System.out.println("B");
        }
    },
    C {
        public void sys() {
            System.out.println("C");
        }
    };
    public String a;
    
    abstract public void sys();
}
