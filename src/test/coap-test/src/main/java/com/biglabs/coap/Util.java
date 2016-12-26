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
        Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(path))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }

            scanner.close();

        } catch (Exception e) {
            return new String[0];
        }

        return result.toArray(new String[0]);
    }
}
