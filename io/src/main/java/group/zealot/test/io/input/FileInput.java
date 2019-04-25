package group.zealot.test.io.input;

import group.zealot.test.io.Run;

import java.io.*;
import java.nio.file.Files;

public class FileInput {
    public static void main(String[] args) throws Exception {
        try (InputStream in = Files.newInputStream(Run.path)) {
            DataInput dataInput = new DataInputStream(in);
            int i;
            while ((i = dataInput.readInt()) != -1){
                System.out.println(i);
            }
        }
    }
}
