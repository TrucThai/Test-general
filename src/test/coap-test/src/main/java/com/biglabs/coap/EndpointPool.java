package com.biglabs.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.network.CoapEndpoint;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.*;

/**
 * Created by lavalamp on 05/01/2017.
 */
public class EndpointPool {

    private static final int QUEUE_SIZE = createEffectiveQueueSize();
    private final BlockingQueue<CoapEndpoint> queue = new ArrayBlockingQueue(QUEUE_SIZE);
    private URI uri  ;

    public static  final EndpointPool INSTANCE = new EndpointPool();

    private EndpointPool ()  {
        try {
            uri = new URI("coap://192.168.1.131:5683/raw");
            System.out.println("Initializing CoapClientPool with Queue Size " + QUEUE_SIZE + "...");
            for (int i = 0; i < QUEUE_SIZE; i++) {
                CoapEndpoint endpoint = createCoapEndpoint();
                queue.add(endpoint);
            }
            System.out.println("Create CoapClientPool with Queue Size " + QUEUE_SIZE + ".");
        }catch (Exception ex){
            System.err.println(ex);
        }
    }

    private  CoapEndpoint createCoapEndpoint() throws IOException {
        CoapEndpoint endpoint = new CoapEndpoint();
        endpoint.setExecutor(createEffectiveExecutor());
        if(!endpoint.isStarted()){
            endpoint.start();
        }
        return endpoint;
    }


    public void post(String payload) throws InterruptedException, IOException {
        CoapEndpoint client = null;
        try {
            client = queue.poll(50, TimeUnit.MILLISECONDS);
            if (client != null) {
                Request request = Request.newPost();
                request.setTimedOut(true);
                request.setAcknowledged(false);
                request.getOptions().setContentFormat(MediaTypeRegistry.TEXT_PLAIN);
                request.setScheme(CoAP.COAP_URI_SCHEME);
                request.setObserveCancel();
                request.setURI(uri);
                request.setConfirmable(false);
                request.setPayload(payload);
                request.setType(CoAP.Type.NON);
                client.sendRequest(request);
                Profiler.INSTANCE.CoapCounter.markSuccess();
            } else {
                System.err.println("Cannot get client from Pool.");
                Profiler.INSTANCE.CoapCounter.markError();
            }
        }finally {
            if(client!=null){
                client.clear();
                queue.add(client);

            }
        }
    }

    private static int createEffectiveQueueSize(){
        return Runtime.getRuntime().availableProcessors() * 2;
    }

    private static ScheduledExecutorService createEffectiveExecutor(){
        return Executors.newScheduledThreadPool(2);
    }
}
