package com.cbsinc.cms.eureka;

import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClientConfig;

/**
 * Sample Eureka service that registers with Eureka to receive and process requests.
 * This example just receives one request and exits once it receives the request after processing it.
 *
 */

public class CMSEurekaService {

	private static ApplicationInfoManager applicationInfoManager;
    private static EurekaClient eurekaClient;
    private static ResourceBundle setupResources = null;

    public static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        if (applicationInfoManager == null) {
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        }

        return applicationInfoManager;
    }

    public static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {

		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");

		String eureka = System.getenv("EUREKA");
		if(eureka == null || eureka.trim().isEmpty())
		{
			eureka = setupResources.getString("eureka.client").trim();
		}
    	if (eurekaClient == null && eureka.equalsIgnoreCase("true")) {
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        }

        return eurekaClient;
    }


    public static void main(String[] args) {





        DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
        EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

        CMSServiceBase cmsServiceBase = new CMSServiceBase(applicationInfoManager, eurekaClient, configInstance);
        try {
            cmsServiceBase.start();
        } finally {
            // the stop calls shutdown on eurekaClient
            cmsServiceBase.stop();
        }
    }

}
