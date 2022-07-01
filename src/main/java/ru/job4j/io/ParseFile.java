package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile implements GetContent {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }
    @Override
    public String content(Predicate<Character> predicate, File file) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (predicate.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}