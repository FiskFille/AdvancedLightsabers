package fiskfille.utils.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.fiskmods.lightsabers.Lightsabers;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class FileHelper
{
    public static URLConnection createConnection(String url) throws MalformedURLException, IOException
    {
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        return connection;
    }

    public static boolean isURL(String path)
    {
        return path.startsWith("http://") || path.startsWith("https://") || path.startsWith("www.");
    }

    public static List<String> read(String path) throws IOException
    {
        return IOUtils.readLines(Lightsabers.class.getResourceAsStream(path));
    }

    public static List<String> read(File file) throws IOException
    {
        return read(new FileReader(file));
    }

    public static List<String> read(URL url) throws IOException
    {
        return read(new InputStreamReader(url.openStream()));
    }

    public static List<String> read(URLConnection connection) throws IOException
    {
        return read(new InputStreamReader(connection.getInputStream()));
    }

    public static List<String> read(Reader reader) throws IOException
    {
        BufferedReader in = new BufferedReader(reader);
        List<String> list = Lists.newArrayList();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
        {
            list.add(currentLine);
        }

        in.close();

        return list;
    }

    public static <T> T readJson(Gson gson, Class<T> type, String path) throws IOException
    {
        return gson.fromJson(flatten(read(path)), type);
    }

    public static <T> T readJson(Gson gson, Class<T> type, File file) throws IOException
    {
        if (!file.isFile() || !file.getName().endsWith(".json"))
        {
            return null;
        }

        return readJson(gson, type, new FileReader(file));
    }

    public static <T> T readJson(Gson gson, Class<T> type, URL url) throws IOException
    {
        return readJson(gson, type, new InputStreamReader(url.openStream()));
    }

    public static <T> T readJson(Gson gson, Class<T> type, URLConnection connection) throws IOException
    {
        return readJson(gson, type, new InputStreamReader(connection.getInputStream()));
    }

    public static <T> T readJson(Gson gson, Class<T> type, Reader reader) throws IOException
    {
        return gson.fromJson(flatten(read(reader)), type);
    }

    public static String flatten(List<String> list)
    {
        String s = "";

        for (String s1 : list)
        {
            s += s1 + "\n";
        }

        return s;
    }
}
