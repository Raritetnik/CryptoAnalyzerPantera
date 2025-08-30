package com.javarush.kuzmin.Model;

import java.util.Arrays;

public class BruteForce {
    private final char[] _alphabet;
    private final Cipher _cipher;
    private final int _minPossibleMatches;


    public BruteForce(char[] alphabet, Cipher cipher){
        this._alphabet = alphabet;
        this._cipher = cipher;
        this._minPossibleMatches = 3;
    }

    public BruteForce(char[] alphabet, Cipher cipher, int minPossibleMatches){
        this._alphabet = alphabet;
        this._cipher = cipher;
        this._minPossibleMatches = minPossibleMatches;
    }

    public ResultModel decryptByBruteForce(String textToBrute, String[] possibleWords) {
        String bruteForcedText = "";
        int cipherKey = -1;
        for(int possibleShift = 0; possibleShift < this._alphabet.length; possibleShift++){
            bruteForcedText = this._cipher.decrypt(textToBrute, possibleShift);
            boolean canBeCorrectShift = false;
            int matchCounter = 0;
            for(String possibleWord: possibleWords){
                canBeCorrectShift = Arrays.stream(bruteForcedText.split(" "))
                        .anyMatch(x -> x.equalsIgnoreCase(possibleWord));
                if(canBeCorrectShift) matchCounter++;
            }
            if(matchCounter >= this._minPossibleMatches){
                cipherKey = possibleShift;
                return new ResultModel(cipherKey, bruteForcedText);
            }
        }
        return new ResultModel(cipherKey, bruteForcedText);
    }

    public class ResultModel{
        private int _possibleShift;
        private String _bruteForcedText;

        public ResultModel(int possibleShift, String bruteForcedText){
            this._bruteForcedText = bruteForcedText;
            this._possibleShift = possibleShift;
        }
        public int getPossibleShift() {
            return _possibleShift;
        }

        public String getBruteForcedText() {
            return _bruteForcedText;
        }
    }
}
