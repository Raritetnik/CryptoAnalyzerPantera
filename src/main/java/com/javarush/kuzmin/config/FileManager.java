package com.javarush.kuzmin.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    public static String readFile(String filePath) {
        StringBuilder textBuild = new StringBuilder();
        Path fileStream = Paths.get(filePath);
        try(BufferedReader reader = Files.newBufferedReader(fileStream, StandardCharsets.UTF_8)){
            String line;
            while ((line = reader.readLine()) != null) {
                textBuild.append(line+"\n");
            }
            System.out.println("Successful read from "+filePath+" with UTF-8 encoding.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return textBuild.toString();
    }
    public static void writeFile(String content, String filePath) {
        Path fileStream = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(fileStream, StandardCharsets.UTF_8)) {
            writer.write(content);
            System.out.println("Successfully wrote to "+filePath+" with UTF-8 encoding.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

}
