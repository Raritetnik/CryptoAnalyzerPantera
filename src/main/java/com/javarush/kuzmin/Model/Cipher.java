package com.javarush.kuzmin.Model;

import java.util.Arrays;

public class Cipher {
    private char[] _alphabet;
    public Cipher(char[] alphabet) {
        this._alphabet = alphabet;
    }


    public String encrypt(String inputText, int shift) {
        StringBuilder outputText = new StringBuilder();
        String[] fileLines = inputText.split("\\R");
        for(var line: fileLines){
            for(char ch: line.toLowerCase().toCharArray()){
                int position = Arrays.binarySearch(this._alphabet, ch);
                if(position >= 0){
                    try {
                        outputText.append(this._alphabet[position+shift]);
                    } catch(ArrayIndexOutOfBoundsException e) {
                        int reposition = (position+shift) % this._alphabet.length;
                        if(reposition >= 0){
                            outputText.append(this._alphabet[reposition]);
                        } else {
                            outputText.append(ch);
                        }
                    } catch(Exception e) {
                        System.out.println("Unknown error: "+ e.getMessage());
                    }
                } else {
                    outputText.append(ch);
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
                int position = Arrays.binarySearch(this._alphabet, ch);
                try {
                    outputText.append(this._alphabet[position-shift]);
                } catch(ArrayIndexOutOfBoundsException e) {
                    int reposition = this._alphabet.length - (shift - position);
                    if(reposition >= 0 && position >= 0){
                        outputText.append(this._alphabet[reposition]);
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

    public static char[] concat(char[]... arrays) {
        int length = 0;
        for (char[] arr : arrays) {
            length += arr.length;
        }

        char[] result = new char[length];
        int pos = 0;
        for (char[] arr : arrays) {
            System.arraycopy(arr, 0, result, pos, arr.length);
            pos += arr.length;
        }
        Arrays.sort(result);
        return result;
    }
}
