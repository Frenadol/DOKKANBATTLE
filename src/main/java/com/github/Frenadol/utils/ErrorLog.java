package com.github.Frenadol.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLog {
    /**
     * Logs an exception message to the error log file with a timestamp.
     * @param e The exception to be logged.
     */
    public static void fileRead(Exception e) {
        String rutaArchivo = "./src/main/java/com/github/Frenadol/utils/ErrorLogs.txt";
        try {
            FileWriter fileWriter = new FileWriter(rutaArchivo, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = now.format(formatter);

            bufferedWriter.write(formatDateTime + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Logs a custom message to the error log file with a timestamp.
     * @param message The message to be logged.
     */
    public static void logMessage(String message) {
        String rutaArchivo = "./src/main/java/com/github/Frenadol/utils/ErrorLogs.txt";
        try {
            FileWriter fileWriter = new FileWriter(rutaArchivo, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = now.format(formatter);

            bufferedWriter.write(formatDateTime + " - " + message);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}