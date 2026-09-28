package com.cbsinc.cms.services.jmx;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class HostAliase {

	public void AddAlias(String alias) {
		try {
			System.out.println("\nCreate an RMI connector client and " + "connect it to the RMI connector server");
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.1.33:9015/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
			System.out.println("\nGet an MBeanServerConnection");
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			ObjectName dynMBeanName = new ObjectName("Catalina:type=Host,host=www.siteoneclick.com");
			Object[] arrOjb = { alias };
			String[] signature = new String[] { "java.lang.String" };
			System.out.println("\nInvoke addAlias() in Dynamic MBean...");
			mbsc.invoke(dynMBeanName, "addAlias", arrOjb, signature);
			System.out.println("\nClose the connection to the server");
			jmxc.close();

			System.out.println("\nBye! Bye!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeAlias(String alias) {
		try {
			System.out.println("\nCreate an RMI connector client and " + "connect it to the RMI connector server");
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.1.33:9015/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
			System.out.println("\nGet an MBeanServerConnection");
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			ObjectName dynMBeanName = new ObjectName("Catalina:type=Host,host=www.siteoneclick.com");
			Object[] arrOjb = { alias };
			String[] signature = new String[] { "java.lang.String" };
			System.out.println("\nInvoke addAlias() in Dynamic MBean...");
			mbsc.invoke(dynMBeanName, "removeAlias", arrOjb, signature);
			System.out.println("\nClose the connection to the server");
			jmxc.close();

			System.out.println("\nBye! Bye!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] findAliases() {
		String[] array = new String[0];
		try {
			System.out.println("\nCreate an RMI connector client and " + "connect it to the RMI connector server");
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.1.33:9015/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
			System.out.println("\nGet an MBeanServerConnection");
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			ObjectName dynMBeanName = new ObjectName("Catalina:type=Host,host=www.siteoneclick.com");
			System.out.println("\nInvoke addAlias() in Dynamic MBean...");
			Object obj = mbsc.invoke(dynMBeanName, "findAliases", null, null);
			array = (String[]) obj;
			System.out.println("\nClose the connection to the server");
			jmxc.close();

			System.out.println("\nBye! Bye!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public static void main(String[] args) {
		HostAliase hostAliase = new HostAliase();
		// hostAliase.removeAlias("www.irr.bz");
		hostAliase.AddAlias("www.irr.bz");
		String[] d = hostAliase.findAliases();
		for (int i = 0; i < d.length; i++) {
			System.out.println(d[i]);
		}
		// System.out.println(hostAliase.findAliases()) ;
	}

}
