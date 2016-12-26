package com.biglabs.coap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Random;
import java.util.logging.Level;

import org.eclipse.californium.core.CaliforniumLogger;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.network.Endpoint;
import org.eclipse.californium.scandium.ScandiumLogger;
/*
  Sample args:  POST coap://localhost:5683
 */
public class CoapClientApp {
    static {
        CaliforniumLogger.initialize();
        CaliforniumLogger.setLevel(Level.WARNING);

        ScandiumLogger.initialize();
        ScandiumLogger.setLevel(Level.FINER);
    }

    // indices of command line parameters
    private static final int IDX_METHOD          = 0;
    private static final int IDX_URI             = 1;
    private static final int IDX_PAYLOAD         = 2;

    // exit codes for runtime errors
    private static final int ERR_MISSING_METHOD  = 1;
    private static final int ERR_UNKNOWN_METHOD  = 2;
    private static final int ERR_MISSING_URI     = 3;
    private static final int ERR_BAD_URI         = 4;
    private static final int ERR_REQUEST_FAILED  = 5;
    private static final int ERR_RESPONSE_FAILED = 6;


    // initialize parameters
    static String method = null;
    static URI uri = null;
    static String payload = "";
    static boolean loop = false;

    static double startValue = 0;
    static double baseTemp = 32.0;
    static double basePresure = 1000;
    static double baseLevel = 10;
    static double baseVoltage = 220;

    static String[] powerDevices;
    static String[] tempDevices;

    /*
     * Main method of this client.
     */
    public static void main(String[] args) throws IOException, GeneralSecurityException, URISyntaxException, InterruptedException {

        // display help if no parameters specified
        if (args.length < 2) {
            throw new IOException("Argurment is missing");
        }

        method = args[0].toUpperCase();
        uri = new URI(args[1]);

        powerDevices = Util.loadFromResource("power_device.conf");
        tempDevices = Util.loadFromResource("temp_device.conf");

        while (true) {
            payload = genPayload();
            send(method, uri, payload);
            //Thread.sleep(10);
        }
    }

    private static int random(int min, int max){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static String genPayload(){
        double sinValue = Math.sin(startValue);
        startValue += 1;
        String device = "";
        String metric = "";
        double value = 0;

        int metricType = random(0,1);
        switch (metricType){
            case 0:
                baseTemp += sinValue;
                if(baseTemp <= 0){
                    baseTemp = 32;
                }
                device = tempDevices[random(0,99)];
                metric = "TEMPERATURE";
                value = baseTemp;
                break;
            case 1:
                baseVoltage += sinValue * 0.65;
                if (baseVoltage <= 0)
                baseVoltage = 220;
                if (baseVoltage >= 225)
                baseVoltage = 220;
                device = powerDevices[random(0,99)];
                metric = "POWER_CONSUMPTION";
                value = baseVoltage;
                break;
        }

        return device + " " + metric + " double " + value + " " + System.currentTimeMillis();
    }

    private static void send(String method, URI uri, String payload){
        try {
            Request request = newRequest(method);
            request.setURI(uri);
            request.setPayload(payload);
            request.getOptions().setContentFormat(MediaTypeRegistry.TEXT_PLAIN);
            request.send();
            //request.waitForResponse();
        } catch(Exception e){
            System.err.println("Failed to send request: " + e);
        }
    }

    /*
     * Instantiates a new request based on a string describing a method.
     *
     * @return A new request object, or null if method not recognized
     */
    private static Request newRequest(String method) {
        if (method.equals("GET")) {
            return Request.newGet();
        } else if (method.equals("POST")) {
            return Request.newPost();
        } else if (method.equals("PUT")) {
            return Request.newPut();
        } else if (method.equals("DELETE")) {
            return Request.newDelete();
        } else if (method.equals("DISCOVER")) {
            return Request.newGet();
        } else if (method.equals("OBSERVE")) {
            Request request = Request.newGet();
            request.setObserve();
            loop = true;
            return request;
        } else {
            System.err.println("Unknown method: " + method);
            System.exit(ERR_UNKNOWN_METHOD);
            return null;
        }
    }
}
