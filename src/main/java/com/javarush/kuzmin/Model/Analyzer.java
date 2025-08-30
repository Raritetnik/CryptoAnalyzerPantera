package com.javarush.kuzmin.Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Analyzer {
    private char _mostRepetitiveLetterEncrypt;
    private String _mostRepetitiveWordEncrypt;

    private char _mostRepetitiveLetterOrig;
    private String _mostRepetitiveWordOrig;

    private final String _encrypt;
    private final String _dict;
    private final char[] _alphabet;

    public Analyzer(char[] alphabet, String encrypt, String dict){
        this._alphabet = alphabet;
        this._encrypt = encrypt;
        this._dict = dict;
    }

    public Result analyzeFiles(){
        StringBuilder sb = new StringBuilder();
        var encryptCharsFrequencies = this.getFrequencies(this._encrypt);
        var dictCharsFrequencies = this.getFrequencies(this._dict);
        sb.append(String.format("%-10s | %-25s | %-25s%n",
                "Character", "Representative Freq (/1000)", "Encrypted Freq (/1000)"));
        sb.append("-----------------------------------------------------------------------\n");
        for (char ch : this._alphabet) {
            sb.append(String.format("%-10s | %-25.3f | %-25.3f%n",
                    (ch == ' ' ? "[space]" : String.valueOf(ch)),
                    dictCharsFrequencies.get(ch), encryptCharsFrequencies.get(ch)));
        }
        int bestShift = findBestShift(dictCharsFrequencies, encryptCharsFrequencies);
        return new Result(sb.toString(), encryptCharsFrequencies, dictCharsFrequencies, bestShift);
    }

    public Map<Character, Double> getFrequencies(String text) {
        Map<Character, Integer> count = new HashMap<>();
        int total = 0;
        for (char ch : text.toCharArray()) {
            int position = Arrays.binarySearch(this._alphabet, ch);
            if (position >= 0) {
                count.put(ch, count.getOrDefault(ch, 0) + 1);
                total++;
            }
        }
        Map<Character, Double> freq = new HashMap<>();
        for (char ch : this._alphabet) {
            freq.put(ch, count.getOrDefault(ch, 0) / (double) total);
        }
        return freq;
    }

    // Поиск оптимального сдвига -- самое сложное
    private int findBestShift(Map<Character, Double> rep, Map<Character, Double> enc) {
        int n = this._alphabet.length;
        double bestScore = Double.MAX_VALUE;
        int bestShift = 0;

        for (int shift = 0; shift < n; shift++) {
            double score = 0.0;
            for (int i = 0; i < n; i++) {
                char c = this._alphabet[i];
                char shifted = this._alphabet[(i + shift) % n];
                double diff = rep.get(c) - enc.get(shifted);
                score += diff * diff; // сумма квадратов отклонений
            }
            if (score < bestScore) {
                bestScore = score;
                bestShift = shift;
            }
        }
        return bestShift;
    }


    public static class Result {
        private final String _analize;
        private final Map<Character, Double> _encryptCharsFreq;
        private final Map<Character, Double> _dictCharsFreq;
        private final int _bestShift;

        public Result(String analize, Map<Character, Double> encryptCharsFreq, Map<Character, Double> dictCharsFreq, int bestShift){
            this._analize = analize;
            this._dictCharsFreq = dictCharsFreq;
            this._encryptCharsFreq = encryptCharsFreq;
            this._bestShift = bestShift;
        }
        public String getAnalize() {
            return this._analize;
        }

        public Map<Character, Double> getEncryptCharsFreq(){
            return this._encryptCharsFreq;
        }

        public Map<Character, Double> getDictCharsFreq(){
            return this._dictCharsFreq;
        }

        public int getBestShift() {
            return _bestShift;
        }
    }


}
