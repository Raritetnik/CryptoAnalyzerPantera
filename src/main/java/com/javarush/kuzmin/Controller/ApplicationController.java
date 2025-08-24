package com.javarush.kuzmin.Controller;

import com.javarush.kuzmin.Model.Cipher;
import com.javarush.kuzmin.config.FileManager;

import java.io.File;
import java.util.Arrays;

public class ApplicationController {
    private static final char[] alphabetRu = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    private static int shift = 1;
    private static Cipher cipher;

    public static void main(String[] args) {
        preConfiguration();


        String textToEncrypt = FileManager.readFile("text/text.txt");
        String encryptedText = cipher.encrypt(textToEncrypt, shift);
        FileManager.writeFile(encryptedText, "text/kuzmin_encrypted.txt");
        String decrypredText = cipher.decrypt(encryptedText, shift);
        FileManager.writeFile(decrypredText, "text/kuzmin_dencrypted.txt");
    }

    public static void preConfiguration() {
        Arrays.sort(alphabetRu); // Sorted for binary search (effective)
        cipher = new Cipher(alphabetRu);
        shift = 2;
    }
}
