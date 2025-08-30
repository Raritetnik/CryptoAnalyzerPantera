package com.javarush.kuzmin.Controller;

import com.javarush.kuzmin.Model.Cipher;
import com.javarush.kuzmin.Model.MenuManager;
import com.javarush.kuzmin.Model.Validator;
import com.javarush.kuzmin.Model.FileManager;
import com.javarush.kuzmin.config.Alphabet;

import java.util.Scanner;

public class ConsoleController {

    public static void main(String[] args) {
        // This one has 79 / (with uppercase 112) chars, so it's harder to brute force...
        MenuManager menu = new MenuManager(Cipher.concat(
                Alphabet.RU_LOWERCASE.getChars()
                , Alphabet.SYMBOLS.getChars())
            );
        // This one has 41 chars, so it's easier to brute force...
        // MenuManager menu = new MenuManager(Alphabet.DEFAULT.getChars());

        boolean executeApplication = true;
        while(executeApplication) {
            int selectedOption = menu.displayConsoleMenu();
            switch(selectedOption){
                case 1:
                    menu.encryption();
                    break;
                case 2:
                    menu.decryption();
                    break;
                case 3:
                    menu.bruteForce();
                    break;
                case 4:
                    menu.analyzer();
                    break;
                case 0:
                    executeApplication = false;
                    break;
                default:
                    System.out.println("Something went wrong...");
                    break;
            }
        }
    }
}
