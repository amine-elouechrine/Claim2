package org.example.Vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceManager {
    public static String readTextFile(String path) {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = ResourceManager.class.getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("Resource not found: " + path);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading resource: " + e.getMessage());
        }

        return content.toString();
    }

    public static void main(String[] args) {
        // Exemple d'utilisation de readTextFile
        String fileContent = readTextFile("/fileNames.txt");
        System.out.println(fileContent);
    }
}
