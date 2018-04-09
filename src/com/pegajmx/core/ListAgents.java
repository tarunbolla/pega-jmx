package com.pegajmx.core;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.management.ObjectName;

public class ListAgents {
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

			ObjectName mbeanName = new ObjectName("com.pega.PegaRULES:type=web,name=com.pega.pegarules.management.internal.AgentManagement,id=\"dbaf34ac704df86c8ca832d1a839123f\"");
			String content = connection.invoke(mbeanName, "AgentStatus" ,new Object[0],new String[0]).toString();
			jmxConnector.close();
			
			File file = new File("output.txt");
			DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
			stream.write(content.getBytes());
			stream.close();
		} catch (Exception e) {  
			System.out.println("Caught exception: " + e);  
		} 
	}
}
