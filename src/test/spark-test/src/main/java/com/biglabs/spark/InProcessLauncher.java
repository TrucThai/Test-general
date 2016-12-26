package com.biglabs.spark;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.util.HashMap;
import java.util.Map;

public class InProcessLauncher {
    public static void main(String[] args) throws Exception {
        Map<String, String> env = new HashMap<>();
        env.put("SPARK_PRINT_LAUNCH_COMMAND", "1");
        SparkAppHandle spark = new SparkLauncher(env)
                .addJar("/opt/iot/jobserver/spark-test-all.jar")
                .setAppResource("/opt/iot/jobserver/spark-test-all.jar")
                .setMainClass("com.biglabs.spark.JavaSparkPi")
                .setMaster("spark://192.168.1.131:7077")
                .setSparkHome("/home/lavalamp/antt/spark-2.0.0-bin-hadoop2.7")
                .setConf(SparkLauncher.DRIVER_MEMORY, "2g")
                .addSparkArg("--verbose")
                .startApplication(new SparkAppHandle.Listener() {
                    @Override
                    public void stateChanged(SparkAppHandle handle) {
                        System.out.println(handle.getState());
                    }

                    @Override
                    public void infoChanged(SparkAppHandle handle) {
                        System.out.println(handle.getAppId());
                    }
                });

        System.out.println("Waiting for finish...");
        Thread.sleep(60 * 1000);
        System.out.println("Finished! Exit code:");
    }
}
