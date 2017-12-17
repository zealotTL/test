package name.taolei.zealot.test.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.shiro.codec.Base64;

import java.io.*;

public class JacksonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object o) {
        String str = null;
        try {
            str = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static <E> E toObject(String json, Class<E> type) {
        E e = null;
        try {
            e = mapper.readValue(json, type);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return e;
    }

    public static String serialize(Object obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        String str = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            byte[] bytes = bos.toByteArray();
            str = Base64.encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return str;
    }

    public static Object deSerialize(String str) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        Object o = null;
        try {
            bis = new ByteArrayInputStream(Base64.decode(str));
            ois = new ObjectInputStream(bis);
            o = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return o;
    }
}
