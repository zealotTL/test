package group.zealot.test.io;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Run {

    public static URI toURI(URL url) throws URISyntaxException {
        return url.toURI();
    }

    public static Path toPath(URL url) throws URISyntaxException {
        return toPath(toURI(url));
    }

    public static Path toPath(URI uri) {
        return Paths.get(uri);
    }

    public static InputStream toInputStream(URL url, OpenOption... options) throws URISyntaxException, IOException {
        return toInputStream(toURI(url), options);
    }

    public static InputStream toInputStream(URI uri, OpenOption... options) throws IOException {
        return toInputStream(toPath(uri), options);
    }

    public static InputStream toInputStream(Path path, OpenOption... options) throws IOException {
        return Files.newInputStream(path, options);
    }

    public static OutputStream toOutputStream(URL url, OpenOption... options) throws URISyntaxException, IOException {
        return toOutputStream(toURI(url), options);
    }

    public static OutputStream toOutputStream(URI uri, OpenOption... options) throws IOException {
        return toOutputStream(toPath(uri), options);
    }

    public static OutputStream toOutputStream(Path path, OpenOption... options) throws IOException {
        return Files.newOutputStream(path, options);
    }

    public static BufferedReader toBufferedReader(Path path, Charset charset) throws IOException {
        return Files.newBufferedReader(path, charset);
    }

    public static BufferedReader toBufferedReader(URL url, Charset charset) throws IOException, URISyntaxException {
        return toBufferedReader(toURI(url), charset);
    }

    public static BufferedReader toBufferedReader(URI uri, Charset charset) throws IOException {
        return toBufferedReader(toPath(uri), charset);
    }

    public static void loggerByte(InputStream inputStream) throws IOException {
        int i;
        while ((i = inputStream.read()) != -1) {
            System.out.print(i + ",");
        }
    }

    public static void loggerChar(BufferedReader bufferedReader) throws IOException {
        char[] chars = new char[64];
        while (bufferedReader.read(chars) != -1) {
            print(chars);
        }
    }

    static void print(byte[] os) {
        for (byte o : os) {
            System.out.print(o + " ");
        }
    }

    static void print(char[] os) {
        for (char o : os) {
            System.out.print(o);
        }
    }
}
