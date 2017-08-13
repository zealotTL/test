package name.taolei.zealot.test.json;

import org.apache.shiro.session.mgt.SimpleSession;

public class Main {

    public static void main(String[] args) {
        SimpleSession session = new SimpleSession();
        session.setId("111");

        System.out.println("序列化前：");
        System.out.println("ID:" + session.getId());
        String str1 = JacksonUtil.serialize(session);
        System.out.println(str1);
        SimpleSession sessionNew = (SimpleSession) JacksonUtil.deSerialize(str1);
        System.out.println("ID:" + sessionNew.getId());
    }
}
