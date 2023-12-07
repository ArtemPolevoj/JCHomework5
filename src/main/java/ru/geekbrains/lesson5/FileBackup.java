package ru.geekbrains.lesson5;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileBackup {

    //region Поля для теста метода testWalkFileTree
    static String newDirec = "./testWalk";
    static File direcCopy = new File(newDirec);
    //endregion

    public static void main(String[] args) {
        // createCopyFiles(".", "./backup");
        testWalkFileTree(".");

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
    // Попробовал такой подход
    // интересно, но данный метод копирует абсолютно все файлы, даже те которые
    // пропускает метод createCopyFiles.

    static void testWalkFileTree(String dirReading) {
        Vizit vizit = new Vizit();
        try {
            if (!new File(newDirec).exists()) {
                direcCopy = Files.createDirectory(Path.of(newDirec)).toFile();
            }

            Files.walkFileTree(Path.of(dirReading), vizit);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Vizit extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
            File temp = new File(direcCopy.getName() + "/" + file.getFileName().toString());
            if (!temp.exists()){
                Files.copy(file, temp.toPath());
            }
            return CONTINUE;
        }
    }

}