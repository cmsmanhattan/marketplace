package com.cbsinc.cms.eureka;

import org.apache.log4j.Logger;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.EurekaClient;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * An example service (that can be initialized in a variety of ways) that registers with eureka
 * and listens for REST calls on port 8001.
 */
//@Singleton
public class CMSServiceBase {

	private static final Logger log = Logger.getLogger(CMSServiceBase.class);

	private ApplicationInfoManager applicationInfoManager = null;
    private EurekaClient eurekaClient = null;
    private DynamicPropertyFactory configInstance = null;
    private Thread thread = null ;
    private Thread clientThread = null ;

    //@Inject
    public CMSServiceBase(ApplicationInfoManager applicationInfoManager,
                              EurekaClient eurekaClient,
                              DynamicPropertyFactory configInstance) {
        this.applicationInfoManager = applicationInfoManager;
        this.eurekaClient = eurekaClient;
        this.configInstance = configInstance;
    }

    //@PostConstruct
    public void start() {

    	if( thread != null && thread.isAlive()) return ;
    	thread = new Thread(() -> {
	        // A good practice is to register as STARTING and only change status to UP
	        // after the service is ready to receive traffic
	        System.out.println("Registering service to eureka with STARTING status");
	        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

	        System.out.println("Simulating service initialization by sleeping for 2 seconds...");
	        try {
	            Thread.sleep(2000);
	        } catch (InterruptedException e) {
	            // FIX: the interrupt was swallowed ("// Nothing"). Clearing the flag
	            // without acting on it means a shutdown request to this thread is
	            // lost and the thread keeps running while the container tries to
	            // stop, which shows up as a hung undeploy. Restore the flag and let
	            // the caller see it.
	            Thread.currentThread().interrupt();
	            return;
	        }

	        // Now we change our status to UP
	        System.out.println("Done sleeping, now changing status to UP");
	        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
	        waitForRegistrationWithEureka(eurekaClient);
	        System.out.println("Service started and ready to process requests..");

    	} );

    	thread.setName("EurikaServiceRegitration");
    	thread.start();
    }

    public  void sendRequestToServiceUsingEureka() {
        // initialize the client
        // this is the vip address for the example service to talk to as defined in conf/sample-eureka-service.properties
    	if( clientThread != null && clientThread.isAlive()) return ;
    	clientThread = new Thread(() -> {
        String vipAddress = System.getenv("eureka.vipAddress");
        if(vipAddress == null || vipAddress.isBlank())
        vipAddress = System.getProperty("eureka.vipAddress");

        InstanceInfo nextServerInfo = null;
        try {
            //nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);

         	for (int i = 0; (nextServerInfo == null) && (i < 50); i++) {
         		Thread.sleep(5000);
         		try {
         			nextServerInfo =  eurekaClient.getNextServerFromEureka(vipAddress, false);
			        } catch (RuntimeException e) {
			        	e.printStackTrace();
			        }
         	}

        } catch (Exception e) {
            // FIX: this was System.exit(-1). This code runs on a background thread
            // started from ServletSiteEvent.init(), i.e. during webapp startup, so
            // a Eureka registry that was slow or unreachable terminated the entire
            // Tomcat JVM - taking down every other application deployed in it.
            // A discovery failure is not a reason to kill the process; the service
            // simply has no peer to talk to yet.
            log.error("Cannot get an instance of the service to talk to from Eureka"
                    + " (vipAddress=" + vipAddress + "). Discovery is disabled for this run.", e);
            return;
        }

        // FIX (NPE): the loop above swallows RuntimeException on each attempt, so
        // when Eureka never answers it exits normally with nextServerInfo still
        // null and no exception is thrown. The next line then dereferenced it and
        // failed with NullPointerException instead of the intended message.
        if (nextServerInfo == null) {
            log.error("Eureka returned no instance for vipAddress=" + vipAddress
                    + " after 50 attempts. Giving up without contacting a peer.");
            return;
        }

        System.out.println("Found an instance of example service to talk to from eureka: "
                + nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());

        System.out.println("healthCheckUrl: " + nextServerInfo.getHealthCheckUrl());
        System.out.println("override: " + nextServerInfo.getOverriddenStatus());

        Socket s = new Socket();
        int serverPort = nextServerInfo.getPort();
        try {
            s.connect(new InetSocketAddress(nextServerInfo.getHostName(), serverPort));
        } catch (IOException e) {
            System.err.println("Could not connect to the server :"
                    + nextServerInfo.getHostName() + " at port " + serverPort);
        } catch (Exception e) {
            System.err.println("Could not connect to the server :"
                    + nextServerInfo.getHostName() + " at port " + serverPort + "due to Exception " + e);
        }
        try {

            System.out.println("Connected to server. Sending a sample http get request: " );
            PrintStream out = new PrintStream(s.getOutputStream());
            out.println("GET / HTTP/1.1");
            out.println("Host: (link unavailable)");
            out.println("Connection: close");

            System.out.println("Waiting for server response..");
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String str = rd.readLine();
            if (str != null) {
                System.out.println("Received response from server: " + str);
                System.out.println("Exiting the client. Demo over..");
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	} );

    	clientThread.setName("EurikaClientPing");
    	clientThread.start();
    }

    //@PreDestroy
    public void stop() {
        if (eurekaClient != null) {
            System.out.println("Shutting down server. Demo over.");
            eurekaClient.shutdown();
        }
        if(thread != null) thread.interrupt();
        if(clientThread != null) clientThread.interrupt();
    }

    private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
        // my vip address to listen on
    	String vipAddress = System.getenv("eureka.vipAddress");
        if(vipAddress == null || vipAddress.isBlank())
        	vipAddress = configInstance.getStringProperty("eureka.vipAddress", "localhost").get();
        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            } catch (Throwable e) {
                System.out.println("Waiting ... verifying service registration with eureka ...");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void processRequest(final Socket s) {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = rd.readLine();
            if (line != null) {
                System.out.println("Received a request from the example client: " + line);
            }
            String response = "BAR " + new Date();
            System.out.println("Sending the response to the client: " + response);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(response);

        } catch (Throwable e) {
            System.err.println("Error processing requests");
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
