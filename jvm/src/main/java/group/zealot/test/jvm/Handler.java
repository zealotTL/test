package group.zealot.test.jvm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Handler implements InvocationHandler, MyInteger {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getName());
        System.out.println(method.getName());
        sys(args);
//        if (method.getName().equals("set")) {
//            System.out.println("do set");
//            this.set((Integer) args[0]);
//            return null;
//        } else {
//            if (method.getName().equals("get")) {
//                System.out.println("do get");
//                return this.get();
//            }
//        }
        return null;
    }

    private void sys(Object[] args) {
        if (args == null) {
            System.out.println("args is null");
        } else {
            for (Object o : args) {
                System.out.print(" | ");
                System.out.print(o.getClass().getName());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Handler handler = new Handler();
        Class[] classes = new Class[1];
        classes[0] = MyInteger.class;
        MyInteger proxy = (MyInteger) Proxy.newProxyInstance(handler.getClass().getClassLoader(), classes, handler);
        proxy.getInteger();
        System.out.println("=====");
        proxy.set(1);
        System.out.println("=====");
        proxy.getInt();
    }

    public Integer getInteger() {
        return null;
    }

    public int getInt() {
        return 0;
    }

    public void set(int i) {

    }
}

interface MyInteger {
    Integer getInteger();

    int getInt();

    void set(int i);
}
