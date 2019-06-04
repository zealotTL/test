package group.zealot.test.shell;

import java.io.*;
import java.util.List;

public class Main {
    /**
     * 执行sh脚本 并传参（ shell脚本内用$0 $1... 表示）
     */
    public static void main(String[] args) throws InterruptedException {
        String a1 = ".";
        System.out.println(a1.equals("."));
        System.out.println(a1.equals(".."));

//        String username = "test123";
//        String password = "test123";
//        String sh = "sh /home/ideal/test.sh " + username + password;
//        String result = exec(sh);
//        System.out.println(result);
//        System.out.println("0".equals(result));
    }

    public static String[] toStrings(List<String> list) {
        String[] strs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strs[i] = list.get(i);
        }
        return strs;
    }

    public static String exec(String sh) throws InterruptedException {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            pro = runTime.exec(sh);
            pro.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                returnString = returnString + line + "\n";
            }
            input.close();
            output.close();
            pro.destroy();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnString;
    }


}
    
