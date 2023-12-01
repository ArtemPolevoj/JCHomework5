package ru.geekbrains.lesson5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackup {
    public static void main(String[] args) {
        createCopyFiles(".", "./backup");
    }

    /**
     * Метод копировамия файлов
     *
     * @param dirReading Директория чтения
     * @param newDir     Директория для копирования
     */
    static void createCopyFiles(String dirReading, String newDir) {
        File directory = new File(dirReading);
        File dirCopy = new File(newDir);
        try {
            if (!new File(newDir).exists()) {
                dirCopy = Files.createDirectory(Path.of(newDir)).toFile();
            }
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        File newFile = new File(dirCopy.getName() +
                                "\\" + file.getName());
                        if (!newFile.exists()) {
                            // Files.copy(file.toPath(), newFile.toPath());
                            try (OutputStream outputStream =
                                         new BufferedOutputStream(
                                                 new FileOutputStream(newFile))) {
                                int c;
                                try (InputStream inputStream =
                                             new BufferedInputStream(
                                                     new FileInputStream(file))) {
                                    while ((c = inputStream.read()) != -1) {
                                        outputStream.write(c);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
