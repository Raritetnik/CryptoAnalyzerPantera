package com.javarush.kuzmin.Model;

import java.util.Arrays;

public class Cipher {
    private char[] alphabet;
    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
    }


    public String encrypt(String inputText, int shift) {
        StringBuilder outputText = new StringBuilder("");
        String[] fileLines = inputText.split("\\R");
        for(var line: fileLines){
            for(char ch: line.toLowerCase().toCharArray()){
                int position = Arrays.binarySearch(this.alphabet, ch);
                try {
                    outputText.append(this.alphabet[position+shift]);
                } catch(ArrayIndexOutOfBoundsException e) {
                    int reposition = (position+shift) % this.alphabet.length;
                    if(reposition >= 0){
                        outputText.append(this.alphabet[reposition]);
                    } else {
                        outputText.append(ch);
                    }
                } catch(Exception e) {
                    System.out.println("Unknown error: "+ e.getMessage());
                }
            }
            outputText.append("\n");
        }
        return outputText.toString();
    }
    public String decrypt(String encryptedText, int shift) {
        StringBuilder outputText = new StringBuilder("");
        String[] fileLines = encryptedText.split("\\R");
        for(var line: fileLines){
            for(char ch: line.toLowerCase().toCharArray()){
                int position = Arrays.binarySearch(this.alphabet, ch);
                try {
                    outputText.append(this.alphabet[position-shift]);
                } catch(ArrayIndexOutOfBoundsException e) {
                    int reposition = this.alphabet.length - (shift - position);
                    if(reposition >= 0 && position >= 0){
                        outputText.append(this.alphabet[reposition]);
                    } else {
                        outputText.append(ch);
                    }
                } catch(Exception e) {
                    System.out.println("Unknown error: "+ e.getMessage());
                }
            }
            outputText.append("\n");
        }
        return outputText.toString();
    }
}
