package com.javarush.kuzmin.Model;

import java.io.File;

public class Validator {
    public static boolean isValidKey(int key, char[] alphabet) {
        return alphabet.length > key;
    }
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
