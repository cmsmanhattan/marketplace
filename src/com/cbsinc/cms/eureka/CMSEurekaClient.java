package com.cbsinc.cms.eureka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

public class CMSEurekaClient {


	private static CMSEurekaClient cmsEurekaClient = new CMSEurekaClient() ;
	private ApplicationInfoManager applicationInfoManager;
    private  EurekaClient eurekaClient;
    private Thread thread = null ;

    private CMSEurekaClient() {

	}


    public static CMSEurekaClient getInstance()
    {
		return cmsEurekaClient;

    }

    public  synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        if (applicationInfoManager == null) {
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        }

        return applicationInfoManager;
    }

    public  synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        if (eurekaClient == null) {
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        }

        return eurekaClient;
    }


    public  void sendRequestToServiceUsingEureka(EurekaClient eurekaClient) {
        // initialize the client
        // this is the vip address for the example service to talk to as defined in conf/sample-eureka-service.properties
    	if(thread.isAlive()) return ;
    	thread = new Thread(() -> {
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
            System.err.println("Cannot get an instance of example service to talk to from eureka");
            System.exit(-1);
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
            String request = "FOO " + new Date();
            System.out.println("Connected to server. Sending a sample request: " + request);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(request);

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

    	thread.setName("EurikaServiceRegitration");
    	thread.start();
    }

    public  void sendRequestToServiceUsingEureka(EurekaClient eurekaClient, String vipAddress) {
        // initialize the client
        // this is the vip address for the example service to talk to as defined in conf/sample-eureka-service.properties


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
            System.err.println("Cannot get an instance of example service to talk to from eureka");
            System.exit(-1);
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
            String request = "FOO " + new Date();
            System.out.println("Connected to server. Sending a sample request: " + request);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(request);

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
    }


    public void shutdown()
    {
    	 // shutdown the client
        eurekaClient.shutdown();
        if(thread != null) thread.interrupt();
    }


    public static void main(String[] args) {
    	CMSEurekaClient sampleClient = CMSEurekaClient.getInstance() ;

        // create the client
        ApplicationInfoManager applicationInfoManager = sampleClient.initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
        EurekaClient client = sampleClient.initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

        // use the client
        sampleClient.sendRequestToServiceUsingEureka(client,"DESKTOP-K6ASFDM");


        // shutdown the client
        sampleClient.shutdown();
    }

}
