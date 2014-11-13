package gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Traitements
{
	private static Traitements			instance;
	private PropertyChangeSupport		changeur;

	// private Vector <Personne> service;
	private Traitements ()
	{
		this.changeur = new PropertyChangeSupport (this);
		
	}

	public void ajouteEcouteur (PropertyChangeListener aEcouteur)
	{
		this.changeur.addPropertyChangeListener (aEcouteur);
	}

	public void ajouteEcouteur (String aCause, PropertyChangeListener aEcouteur)
	{
		this.changeur.addPropertyChangeListener (aCause, aEcouteur);
	}
	
	public void supprimeEcouteur (PropertyChangeListener aEcouteur)
	{
		this.changeur.removePropertyChangeListener (aEcouteur);
	}

	public void paramCourse ()
	{
		this.changeur.firePropertyChange ("paramCourse", null, null);
	}

	public void depart ()
	{
		this.changeur.firePropertyChange ("depart", null, null);
	}
	public void arret ()
	{
		this.changeur.firePropertyChange ("arret", null, null);
	}
	public void actualise ()
	{
		this.changeur.firePropertyChange ("actualise", null, null);
	}
	public void editer ()
	{
		this.changeur.firePropertyChange ("editer", null, null);
	}
	public void quitter ()
	{
		this.changeur.firePropertyChange ("quitter", null, null);
	}
	public void manu ()
	{
		this.changeur.firePropertyChange ("manu", null, null);
	}
	public void supprimer ()
	{
		this.changeur.firePropertyChange ("supprimer", null, null);
	}
	public void enregistrer ()
	{
		this.changeur.firePropertyChange ("enregistrer", null, null);
	}
	
	public static Traitements getInstance ()
	{
		if (Traitements.instance == null)
			Traitements.instance = new Traitements ();
		return Traitements.instance;
	}
}
