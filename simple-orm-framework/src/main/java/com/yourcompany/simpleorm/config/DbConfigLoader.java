package com.yourcompany.simpleorm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfigLoader {
	private static final String PROPERTIES_FILE = "db.properties";
	private static final Properties properties = new Properties();
	
	static
	{
		try(InputStream input = DbConfigLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE))
		{
			if(input == null)
			{
				System.err.println("Error: Unable to find" + PROPERTIES_FILE + "in the classpath");
				throw new RuntimeException("Database configuration file '" + PROPERTIES_FILE + "' not found.");
			}
			 properties.load(input);
	         System.out.println("Database configuration loaded successfully from " + PROPERTIES_FILE);
		}catch(IOException e)
		{
			System.err.println("ERROR: Failed to load database configuration from " + PROPERTIES_FILE);
			throw new RuntimeException("Failed to load database configuration", e);
		}
	}
	public static String getDbUrl() 
	{
		String url = properties.getProperty("db.url");
		if(url == null || url.trim().isEmpty())
		{
			throw new RuntimeException("Property 'db.url' not found or is empty in" + PROPERTIES_FILE);
			
		}
		return url;
	}
	public static String getDbUser() 
	{
		String user = properties.getProperty("db.user");
		if(user == null || user.trim().isEmpty())
		{
			throw new RuntimeException("Property 'db.user' not found or is empty in" + PROPERTIES_FILE);
		}
		return user;
	}
	public static String getDbPassword() 
	{
		String password = properties.getProperty("db.password");
		if(password == null)
		{
			throw new RuntimeException("Property 'db.password' not found or is empty in" + PROPERTIES_FILE);
			
		}
		return password;
	}
	private DbConfigLoader() {};
	
}
