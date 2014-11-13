
package principal;

import gui.FenetrePrincipale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.swing.JOptionPane;

import utilitaires.ApplicationConfiguration;

public class Horloge extends Thread
{
	private FenetrePrincipale			fp;
	private SimpleDateFormat			stimef			= new SimpleDateFormat ("HH:mm:ss");
	private SimpleDateFormat			shorlf			= new SimpleDateFormat ("HH:mm:ss");
	private SimpleDateFormat			sdatef			= new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
	private Long						heureFin;
	private Long						heureDépart;
	private Long						tempsCourse;
	private ApplicationConfiguration	appConf;
	private String						courseEnCours	= null;	

	public Horloge (FenetrePrincipale aFp)
	{
		this.fp = aFp;

		this.stimef.setTimeZone (TimeZone.getTimeZone ("GMT"));
		this.sdatef.setTimeZone (TimeZone.getTimeZone ("Europe/Paris"));

		this.appConf = ApplicationConfiguration.getInstance ();
	}

	public void controle ()
	{
		try
		{
			this.tempsCourse = this.stimef.parse (this.appConf.getConfiguration ("tempscourse")).getTime ();
			this.heureDépart = this.sdatef.parse (this.appConf.getConfiguration ("heuredepart")).getTime();
			//this.heureFin = this.sdatef.parse (this.appConf.getConfiguration ("heurefin")).getTime ();
			this.courseEnCours = this.appConf.getConfiguration ("etat");
			System.out.println (this.courseEnCours);
		}
		catch (ParseException e)
		{
			System.out.println (e.getMessage ());
		}
	}

	public void run ()
	{
		while (true)
		{
			try
			{
				this.controle ();
				Long heureCourante = System.currentTimeMillis ();
				this.fp.setHeureCourante (this.shorlf.format (heureCourante));
				this.heureFin = this.heureDépart + this.tempsCourse;
				long tempsRestant = 1000 + this.heureFin - heureCourante;
				System.out.println (tempsRestant);
				if (tempsRestant <=0) tempsRestant = 0;

				if (this.courseEnCours.equals ("ON"))
				{
					this.fp.setLblEtatCourse (this.fp.getIion ());
					this.fp.setLblTempsRestant (this.stimef.format (tempsRestant));
					
					if (tempsRestant == 0)
					{
						JOptionPane.showMessageDialog (null, "Course terminée ! Bravo !");
						this.appConf.setConfiguration ("etat", "OFF");
						this.appConf.save ("config.properties");
						this.fp.setLblEtatCourse (this.fp.getIioff ());
					}

				}
				else
				{
					this.fp.setLblEtatCourse (this.fp.getIioff ());
					this.fp.setLblTempsRestant (this.stimef.format (tempsRestant = 0));
				}

				synchronized (this)
				{
					Thread.sleep (970);
				}

			}
			catch (InterruptedException e)
			{
				System.err.println (e.getMessage ());
			}
		}
	}

	public Long getHeureDépart ()
	{
		return heureDépart;
	}

	public void setHeureDépart (Long heureDépart)
	{
		this.heureDépart = heureDépart;
	}

}
