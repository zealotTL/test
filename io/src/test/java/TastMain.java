import group.zealot.test.io.Run;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zealot
 * @date 2020/6/5 20:18
 */
public class TastMain {
    public static void main(String[] args) throws Exception {
        dealFile("C:\\Users\\ZEALOT\\Desktop\\CMP.csv");
        dealFile("C:\\Users\\ZEALOT\\Desktop\\DCP.csv");
    }

    private static void dealFile(String filePath) throws Exception {
        File oldFile = new File(filePath);
        if (!oldFile.exists()) {
            throw new Exception("文件不存在" + filePath);
        }

        BufferedReader reader = new BufferedReader(new FileReader(oldFile));
        reader.readLine();
        String line;
        HashMap<String, HashMap<String, Object>> map = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] lines = line.split(",");
            if (lines.length != 3) {
                throw new Exception("数据异常：" + line);
            }
            String custName = lines[0];
            String province = lines[1];
            int size = new BigDecimal(lines[2]).intValue();
            HashMap<String, Object> cust = getCust(map, custName);
            cust.put(province, new BigDecimal(size).intValue());
            if (!cust.containsKey("count")) {
                cust.put("count", 0);
            }
            cust.put("count", ((Integer) cust.get("count")) + size);
            cust.put("custName", custName);
        }
        List<HashMap<String, Object>> list = new ArrayList<>();
        map.forEach((key, value) -> list.add(value));
        list.sort(Comparator.comparingInt((item) -> (Integer) item.get("count")));
        Collections.reverse(list);

        File newFile = new File(filePath + ".new");
        if (newFile.exists()) {
            newFile.delete();
            newFile = new File(filePath + ".new");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        writer.write("客户名称,开卡总数,开卡省份:数量......");
        list.forEach(item -> {
            try {
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String custName = (String) item.remove("custName");
            Integer count = (Integer) item.remove("count");
            try {
                writer.write(custName + "," + count + ",");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            item.forEach((key, value) -> {
                try {
                    writer.write(key + ":" + value + ",");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        writer.flush();
        writer.close();
    }

    private static HashMap<String, Object> getCust(HashMap<String, HashMap<String, Object>> map, String custName) {
        if (!map.containsKey(custName)) {
            map.put(custName, new HashMap<>());
        }
        return map.get(custName);
    }
}
