package utilitaires;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager
{
	private String		configFilePath;
	private Properties	properties	= new Properties ();
	private boolean		isXML;

	public ConfigurationManager (String configFilePath, boolean isXML) throws IOException
	{
		this.configFilePath = configFilePath;
		this.isXML = isXML;
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream (configFilePath);
			if (isXML)
			{
				properties.loadFromXML (fis);
			}
			else
			{
				properties.load (fis);
			}
		}
		catch (FileNotFoundException ex)
		{
			// creates the configuration file and set default properties
			setDefaults ();
			save ();
		}
		finally
		{
			if (fis != null)
			{
				fis.close ();
			}
		}
	}

	private void setDefaults ()
	{
//		properties.put ("host", "localhost");
//		properties.put ("port", "1521");
//		properties.put ("user", "root");
//		properties.put ("pass", "ssap");
	}

	public void save () throws IOException
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream (configFilePath);
			if (isXML)
			{
				properties.storeToXML (fos, "SettingsApplication");
			}
			else
			{
				properties.store (fos, "SettingsApplication");
			}
		}
		finally
		{
			if (fos != null)
			{
				fos.close ();
			}
		}
	}

	public String getProperty (String key)
	{
		return properties.getProperty (key);
	}

	public String getProperty (String key, String defaultValue)
	{
		return properties.getProperty (key, defaultValue);
	}

	public void setProperty (String key, String value)
	{
		properties.setProperty (key, value);
	}
}
