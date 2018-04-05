package com.pegajmx.core;

import java.util.Set;

import javax.management.ObjectName;

public class ListMBeans {
	public static void main(String[] args) {
		try  
		{  
			String host = "atlihdpegaads04";  
			int port = 9005;  // management-native port  
//			String urlString ="service:jmx:remoting-jmx://" + host + ":" + port;
			String urlString = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
			System.out.println("\n\n\t****  urlString: "+urlString);;  
			javax.management.remote.JMXServiceURL serviceURL = new javax.management.remote.JMXServiceURL(urlString);  

			java.util.Map map = new java.util.HashMap();  
			String[] credentials = new String[] { "controlRole", "R&D" };  
			map.put("jmx.remote.credentials", credentials);  
			javax.management.remote.JMXConnector jmxConnector = javax.management.remote.JMXConnectorFactory.connect(serviceURL, map);  
			javax.management.MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();  

			// Longer exercise to walk through all domains, all names, and operations within  
			int count = connection.getMBeanCount();  
			System.out.println("Total MBean count:" + count);  
			// Get domain list  
			String[] domains = connection.getDomains();  
			for (String domain: domains)  
			  System.out.println("Domain: " + domain);  

			// Get list of object names  
			Set<javax.management.ObjectName> names = connection.queryNames(null,null);  
			for (javax.management.ObjectName name:names)  
			{  
			  if (name.getDomain().contains("com.pega"))  
			  {  
				 System.out.println("ObjectName: " + name);  
				javax.management.MBeanInfo info = connection.getMBeanInfo(name);  
				for (javax.management.MBeanOperationInfo operation : info.getOperations()) {  
					 System.out.println("  operation: "+operation.getName());  
					 // invoking an operation for NodeManagement mBean  
					 if (operation.getName().equals("getNodes"))  
					   {  
						 System.out.println("invoking: " + name + "operation: " + operation.getName());  
						 System.out.println(connection.invoke(name, operation.getName(),new Object[0],new String[0]));  
					 }  
			   }           
			  }  
			}  


			jmxConnector.close();  
		} catch (Exception e) {  
			System.out.println("Caught exception: " + e);  
		} 
	}
}
