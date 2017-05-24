package com.lushidea.zipArchiveCreator.utils;

import org.ini4j.Wini;

import java.io.File;

/**
 * Created by lu.shi on 24/05/2017.
 * <p>This is a collection of operations of ini file</p>
 */
public class IniUtils {
    private static String filePath = "app.ini";
    public static final String CONFIG_SECTION = "Config";
    public static final String FOLDER_KEY = "folder";

    /**
     * Save the value in the file
     * @param section
     * @param key
     * @param value
     * @throws Exception
     */
    public static void save(String section, String key, Object value) throws Exception{
        //check if file exist, if not ,create a new file
        FileUtils.creatFileIfNotExist(filePath);
        Wini ini = new Wini(new File(filePath));
        ini.put(section, key, value);
        ini.store();
    }

    /**
     * Return a value based on the section and key
     * @param section
     * @param key
     * @param cls The value's class
     * @return value if exist, if doesn't exist, return null
     * @throws Exception
     */
    public static Object read(String section, String key, Class cls) throws Exception{
        //check if file exist, if not ,create a new file
        FileUtils.creatFileIfNotExist(filePath);
        Wini ini = new Wini(new File(filePath));
        return ini.get(section, key, cls);
    }
}
