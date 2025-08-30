package com.javarush.kuzmin.Model;

import com.javarush.kuzmin.config.Alphabet;
import com.javarush.kuzmin.config.Color;

import java.util.Arrays;
import java.util.Scanner;

public class MenuManager {
    private final char[] ALPHABET;
    private final Cipher _cipher;
    private final Scanner _scanner;

    private String _filePath;
    private int _shift;


    public MenuManager(char[] alphabet){
        this._scanner = new Scanner(System.in);
        this.ALPHABET = alphabet;
        this._cipher = new Cipher(ALPHABET);
    }

    public void encryption(){
        do {
            System.out.print("Enter the path of the file to encrypt: ");
            this._filePath = _scanner.next();
            if (!Validator.isFileExists(this._filePath)) {
                System.out.println("❌ File not found. Please try again.\n");
            }
        } while(!Validator.isFileExists(this._filePath));
        System.out.println("✅ File found: " + this._filePath + "\n");

        do {
            System.out.print("Enter cipher key (less then "+this.ALPHABET.length+"):");
            try{
                this._shift = this._scanner.nextInt();
                if (Validator.isValidKey(this._shift, this.ALPHABET)) {
                    System.out.println("✅ Valid key entered: " + this._shift + "\n");
                } else {
                    System.out.println("❌ Invalid key. Please enter a valid number within range.\n");
                }
            } catch(Exception e){
                System.out.println("Input error: please enter a number only.\n");
            }
        } while(!Validator.isValidKey(this._shift, this.ALPHABET));

        // Everything is valid, execute process
        String outputPath  = "text/kuzmin/encrypted.txt";
        String textToEncrypt = FileManager.readFile(this._filePath);
        String encryptedText = this._cipher.encrypt(textToEncrypt, this._shift);
        FileManager.writeFile(encryptedText, outputPath);
        System.out.println(Color.ANSI_GREEN.getCode()+"Encryption complete!");
        System.out.println(Color.ANSI_GREEN.getCode()+"Encrypted file saved to: " + outputPath);
        System.out.println(Color.ANSI_GREEN.getCode()+"========================\n"+Color.ANSI_WHITE.getCode());
    }

    public void decryption(){
        do {
            System.out.print("Enter the path of the file to decrypt:");
            this._filePath = _scanner.next();
            if (!Validator.isFileExists(this._filePath)) {
                System.out.println("❌ File not found. Please try again.\n");
            }
        } while(!Validator.isFileExists(this._filePath));
        System.out.println("✅ File found: " + this._filePath + "\n");

        do {
            System.out.print("Enter the cipher key used for encryption (number):");
            try{
                this._shift = this._scanner.nextInt();
                if (Validator.isValidKey(this._shift, this.ALPHABET)) {
                    System.out.println("✅ Valid key entered: " + this._shift);
                } else {
                    System.out.println("❌ Invalid key. Please enter a valid number.");
                }
            } catch(Exception e){
                System.out.println("Input error: please enter a number only.\n");
            }
        } while(!Validator.isValidKey(this._shift, this.ALPHABET));

        // Everything is valid, execute process
        String outputPath  = "text/kuzmin/decrypted.txt";
        String textToDecrypt = FileManager.readFile(this._filePath);
        String decryptedText  = this._cipher.decrypt(textToDecrypt, this._shift);
        FileManager.writeFile(decryptedText , outputPath);
        System.out.println(Color.ANSI_GREEN.getCode()+"Decryption complete!");
        System.out.println(Color.ANSI_GREEN.getCode()+"Decrypted file saved to: " + outputPath);
        System.out.println(Color.ANSI_GREEN.getCode()+"========================\n"+Color.ANSI_WHITE.getCode());
    }

    public void bruteForce(){
        do {
            System.out.print("Enter the path of the file to brute force: ");
            this._filePath = _scanner.next();
            if (!Validator.isFileExists(this._filePath)) {
                System.out.println("❌ File not found. Please try again.\n");
            }
        } while(!Validator.isFileExists(this._filePath));
        System.out.println("✅ File found: " + this._filePath + "\n");

        String[] possibleWords = {"вас", "видимо", "отменили"};
        do {
            System.out.println("Enter the search words to look for in the text, separated by , (commas)");
            System.out.println("Example: или,да,нет");
            System.out.print("List of words: ");

            String input = _scanner.next().trim();
            possibleWords = Arrays.stream(input.split(","))
                    // .map(String::trim) // Убрать пробелы в каждом слове или пустые слова
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);

            if (possibleWords.length == 0) {
                System.out.println("❌ You must enter at least one word. Please try again.\n");
            }

        } while (possibleWords.length == 0);
        BruteForce bf = new BruteForce(this.ALPHABET, this._cipher);
        String textToBrute = FileManager.readFile(this._filePath);
        long startTimer = System.currentTimeMillis();
        BruteForce.ResultModel bruteForcedResult = bf.decryptByBruteForce(textToBrute, possibleWords);
        long endTimer = System.currentTimeMillis();
        String outputPath  = "text/kuzmin/brute-forced.txt";
        FileManager.writeFile(bruteForcedResult.getBruteForcedText(), outputPath);
        System.out.println("Brute force complete in " + (endTimer - startTimer) + "ms! " +
                "The cipher key is "+bruteForcedResult.getPossibleShift());
        System.out.println(Color.ANSI_GREEN.getCode()+"Decrypted file saved to: " + outputPath);
        System.out.println(Color.ANSI_GREEN.getCode()+"========================\n"+Color.ANSI_WHITE.getCode());
    }

    public void analyzer(){
        String pathEncryptFile = "";
        do {
            System.out.print("Enter the path of the encrypted file: ");
            pathEncryptFile = _scanner.next();
            if (!Validator.isFileExists(pathEncryptFile)) {
                System.out.println("❌ File not found. Please try again.\n");
            }
        } while(!Validator.isFileExists(pathEncryptFile));
        System.out.println("✅ File found: " + pathEncryptFile + "\n");

        String pathDictFile = "";
        do {
            System.out.print("Enter the path of the dictionary file: ");
            pathDictFile = _scanner.next();
            if (!Validator.isFileExists(pathDictFile)) {
                System.out.println("❌ File not found. Please try again.\n");
            }
        } while(!Validator.isFileExists(pathDictFile));
        System.out.println("✅ File found: " + pathDictFile + "\n");

        String textToDecrypt = FileManager.readFile(pathEncryptFile);
        String dictText = FileManager.readFile(pathDictFile);

        String statsOutputPath = "text/kuzmin/statistics.txt";
        String decryptOutputPath = "text/kuzmin/decrypted_with_statistics.txt";
        Analyzer analyzer = new Analyzer(this.ALPHABET, textToDecrypt, dictText);
        Analyzer.Result analyzeFile = analyzer.analyzeFiles();
        FileManager.writeFile(analyzeFile.getAnalize(), statsOutputPath);

        String decryptedText  = this._cipher.decrypt(textToDecrypt, analyzeFile.getBestShift());
        FileManager.writeFile(decryptedText , decryptOutputPath);
        System.out.println(Color.ANSI_GREEN.getCode()+"Decryption with analyze is complete!");
        System.out.println(Color.ANSI_GREEN.getCode()+"Statistics file saved to: " + statsOutputPath);
        System.out.println(Color.ANSI_GREEN.getCode()+"Decrypted file saved to: " + decryptOutputPath);
        System.out.println(Color.ANSI_GREEN.getCode()+"========================\n"+Color.ANSI_WHITE.getCode());


    }

    public int displayConsoleMenu(){
        System.out.println(Color.ANSI_BLUE.getCode()+"======== Menu ========");
        System.out.println(Color.ANSI_BLUE.getCode()+"1. Encrypt file");
        System.out.println(Color.ANSI_BLUE.getCode()+"2. Decrypt file");
        System.out.println(Color.ANSI_BLUE.getCode()+"3. Brute-force");
        System.out.println(Color.ANSI_BLUE.getCode()+"4. Analyze");
        System.out.println(Color.ANSI_BLUE.getCode()+"0. Exit Application");
        System.out.println(Color.ANSI_BLUE.getCode()+"======================");
        int selectedOption = -1;
        do{
            try {
                System.out.print(Color.ANSI_WHITE.getCode()+"Option:");
                selectedOption = this._scanner.nextInt();
            } catch (Exception e) {
                System.out.println(Color.ANSI_CYAN.getCode()+"Please enter the number between 0 and 4 for option selection..."+Color.ANSI_WHITE.getCode());
            }
        } while(!(selectedOption >= 0 && selectedOption <= 4));
        return selectedOption;
    }
}
