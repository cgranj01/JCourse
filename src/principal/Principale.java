
package principal;

import gui.FenetrePrincipale;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import utilitaires.ConfigurationChangeListner;

import connexionBase.ConnexionDBrec;

public class Principale
{
	public static void main (String [] args)
	{
		try
		{
			UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName ());
			ConnexionDBrec.getInstance ();
			ConfigurationChangeListner listner = new ConfigurationChangeListner ("config.properties");
			new Thread (listner).start ();
			// FluxAsciiEvenement.getInstance ();
			new FenetrePrincipale ();
		}

		catch (IllegalAccessException | ClassNotFoundException | InstantiationException
				| UnsupportedLookAndFeelException e)
		{
			// handle exception
		}

	}
}
