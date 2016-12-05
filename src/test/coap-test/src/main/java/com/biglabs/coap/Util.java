package com.biglabs.coap;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
    public static String[] loadFromResource(String path) throws IOException {
        Resource body = new ClassPathResource(path);
        List<String> result = new ArrayList<>();
        File file = body.getFile();
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }

            scanner.close();

        } catch (IOException e) {
            return new String[0];
        }

        return result.toArray(new String[0]);
    }
}
