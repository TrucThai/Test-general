package com.biglabs.spark;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class OutProcessLauncher {
    public static void main(String[] args) throws Exception {
        Map<String, String> env = new HashMap<>();
        env.put("SPARK_PRINT_LAUNCH_COMMAND", "1");
        Process  spark = new SparkLauncher(env)
                .setAppResource("/opt/iot/jobserver/spark-test-all.jar")
                .setMainClass("com.biglabs.spark.JavaSparkPi")
                .setMaster("spark://192.168.1.131:7077")
                .setSparkHome("/home/lavalamp/antt/spark-2.0.0-bin-hadoop2.7")
                .setConf(SparkLauncher.DRIVER_MEMORY, "2g")
                .addSparkArg("--verbose")
                .launch();
        // Use handle API to monitor / control application.
        InputStreamReaderRunnable inputStreamReaderRunnable = new InputStreamReaderRunnable(spark.getInputStream(), "input");
        Thread inputThread = new Thread(inputStreamReaderRunnable, "LogStreamReader input");
        inputThread.start();

        InputStreamReaderRunnable errorStreamReaderRunnable = new InputStreamReaderRunnable(spark.getErrorStream(), "error");
        Thread errorThread = new Thread(errorStreamReaderRunnable, "LogStreamReader error");
        errorThread.start();

        System.out.println("Waiting for finish...");
        int exitCode = spark.waitFor();
        System.out.println("Finished! Exit code:" + exitCode);
    }

    public static class InputStreamReaderRunnable implements Runnable {

        private BufferedReader reader;

        private String name;

        public InputStreamReaderRunnable(InputStream is, String name) {
            this.reader = new BufferedReader(new InputStreamReader(is));
            this.name = name;
        }

        public void run() {
            System.out.println("InputStream " + name + ":");
            try {
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
