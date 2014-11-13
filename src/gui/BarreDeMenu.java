
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class BarreDeMenu extends JMenuBar implements PropertyChangeListener, ActionListener
{
	private Traitements			traitements		= Traitements.getInstance ();
	private JMenu				menuFichier		= new JMenu ("Fichier");
	private JMenu				menuParametres	= new JMenu ("Parametres");
	private JMenu				menuModeManuel	= new JMenu ("Mode Manuel");
	private JToolBar			barreOutils		= new JToolBar ();
	//private FenetrePrincipale	fenetrePrincipale;
	//private DialogParamCourse	paramCourse;

	private AbstractAction		actionQuitter;
	private AbstractAction		actionEditer;
	private AbstractAction		actionParamCourse;
	private AbstractAction		actionActualiserParam;
	private AbstractAction		actionManu;
	private AbstractAction		actionSupressionTour;
	private AbstractAction		actionDepart;
	private AbstractAction		actionArreter;
	private AbstractAction		actionEnregistrer;

	public BarreDeMenu ()
	{
		//this.fenetrePrincipale = aFp;
		this.menuFichier.setMnemonic (KeyEvent.VK_F);
		this.menuParametres.setMnemonic (KeyEvent.VK_P);
		this.initialiseActions ();
		//this.menuFichier.add (this.actionEditer);
		//this.menuFichier.addSeparator ();
		this.menuFichier.add (this.actionQuitter);

		//this.menuParametres.add (this.actionActualiserParam);
		this.menuParametres.add (this.actionParamCourse);
		this.menuParametres.add (this.actionEditer);

		this.menuModeManuel.add (this.actionManu);
		this.menuModeManuel.add (this.actionSupressionTour);
		this.add (this.menuFichier);
		this.add (this.menuParametres);
		this.add (this.menuModeManuel);
	}

	public JToolBar getBarreOutils ()
	{
		this.barreOutils.add (this.actionDepart);
		this.barreOutils.addSeparator ();
		this.barreOutils.add (this.actionManu);
		this.barreOutils.addSeparator ();
		this.barreOutils.add (this.actionQuitter);
		this.barreOutils.setRollover (true);
		this.barreOutils.setFloatable (false);
		return this.barreOutils;
	}

	private void initialiseActions ()
	{
		this.traitements.ajouteEcouteur (this);

		this.actionQuitter = new ActionsCourantes ("Quitter", "Terminer cette application", "quitter",
				"Permet de quitter cette application", KeyStroke.getKeyStroke (KeyEvent.VK_Q, InputEvent.CTRL_MASK),
				KeyEvent.VK_Q, "fermer16.png", "fermer32.png", this);
		
		this.actionEditer = new ActionsCourantes ("Equipes", "Edition des équipes", "editer",
				"Permet de créer ou modifier les équipes", KeyStroke.getKeyStroke (KeyEvent.VK_E, InputEvent.CTRL_MASK),
				KeyEvent.VK_Q, "parametres16.png", "parametres32.png", this);
		
		this.actionSupressionTour = new ActionsCourantes ("Supprimer", "Supprimer un tour", "supprimer",
				"Permet de supprimer un tour à une équipe", KeyStroke.getKeyStroke (KeyEvent.VK_D, InputEvent.CTRL_MASK),
				KeyEvent.VK_Q, "supprimer_petit.png", "supprimer_petit.png", this);

		this.actionDepart = new ActionsCourantes ("Depart", "Depart Course", "depart", "Depart course",
				KeyStroke.getKeyStroke (KeyEvent.VK_S, InputEvent.CTRL_MASK), KeyEvent.VK_E, "lecture16.png",
				"lecture32.png", this);
		
		this.actionArreter = new ActionsCourantes ("Arreter", "Arret Course", "arret", "Arret course",
				KeyStroke.getKeyStroke (KeyEvent.VK_E, InputEvent.CTRL_MASK), KeyEvent.VK_E, "stop16.png",
				"stop32.png", this);

		this.actionManu = new ActionsCourantes ("Mode Manuel", "Déclenchement Manuel", "manu", "Mode Manuel",
				KeyStroke.getKeyStroke (KeyEvent.VK_M, InputEvent.CTRL_MASK), KeyEvent.VK_N, "horloge16.png",
				"horloge32.png", this);

		this.actionActualiserParam = new ActionsCourantes ("Actualiser", "Actualiser les paramtres de courses", "actualise",
				"Paramètre du port COM", KeyStroke.getKeyStroke (KeyEvent.VK_A, InputEvent.CTRL_MASK), KeyEvent.VK_S,
				"parametres16.png", "parametres32.png", this);
		
		this.actionParamCourse = new ActionsCourantes ("Parametre course", "Reglages de course", "paramCourse",
				"Parametres courses", KeyStroke.getKeyStroke (KeyEvent.VK_P, InputEvent.CTRL_MASK), KeyEvent.VK_S,
				"parametres16.png", "parametres32.png", this);
		
		this.actionEnregistrer = new ActionsCourantes ("Enregistrer", "Enregistrer les parametres", "enregistrer",
				"Enregistrer les parametres", KeyStroke.getKeyStroke (KeyEvent.VK_S, InputEvent.CTRL_MASK), KeyEvent.VK_S,
				"parametres16.png", "parametres32.png", this);
	}

	@Override
	public void propertyChange (PropertyChangeEvent evt)
	{

	}


	@Override
	public void actionPerformed (ActionEvent aAction)
	{
		
	}

	public AbstractAction getActionQuitter ()
	{
		return actionQuitter;
	}

	public void setActionQuitter (AbstractAction actionQuitter)
	{
		this.actionQuitter = actionQuitter;
	}

	public AbstractAction getActionEditer ()
	{
		return actionEditer;
	}

	public void setActionEditer (AbstractAction actionEditer)
	{
		this.actionEditer = actionEditer;
	}

	public AbstractAction getActionParamCourse ()
	{
		return actionParamCourse;
	}

	public void setActionParamCourse (AbstractAction actionParamCourse)
	{
		this.actionParamCourse = actionParamCourse;
	}

	public AbstractAction getActionManu ()
	{
		return actionManu;
	}

	public void setActionManu (AbstractAction actionManu)
	{
		this.actionManu = actionManu;
	}

	public AbstractAction getActionSupressionTour ()
	{
		return actionSupressionTour;
	}

	public void setActionSupressionTour (AbstractAction actionSupressionTour)
	{
		this.actionSupressionTour = actionSupressionTour;
	}

	public AbstractAction getActionDepart ()
	{
		return actionDepart;
	}

	public void setActionDepart (AbstractAction actionDepart)
	{
		this.actionDepart = actionDepart;
	}

	public AbstractAction getActionActualiserParam ()
	{
		return actionActualiserParam;
	}

	public void setActionActualiserParam (AbstractAction actionActualiserParam)
	{
		this.actionActualiserParam = actionActualiserParam;
	}

	public AbstractAction getActionEnregistrer ()
	{
		return actionEnregistrer;
	}

	public void setActionEnregistrer (AbstractAction actionEnregistrer)
	{
		this.actionEnregistrer = actionEnregistrer;
	}

	public AbstractAction getActionArreter ()
	{
		return actionArreter;
	}

	public void setActionArreter (AbstractAction actionArreter)
	{
		this.actionArreter = actionArreter;
	}
}