
package gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import principal.Horloge;
import utilitaires.Tools;
import fluxascii.FluxAsciiEvenement;

@SuppressWarnings("serial")
public class FenetrePrincipale extends JFrame
		implements
			WindowListener,
			PropertyChangeListener,
			WindowStateListener,
			ActionListener
{
	private BarreDeMenu					barreDeMenu;
	private Traitements					traitements			= Traitements.getInstance ();
	private MiseAJourGUI				majGUI;
	private JLabel						infoCourante		= new JLabel (" ");
	private JLabel						lblTempsRestant		= new JLabel (" ");
	//private JLabel						lblMeilleurTour		= new JLabel (" ");
	private JLabel						lblEtatCourse		= new JLabel ();
	private JLabel						heureCourante		= new JLabel ();
	private TrayIcon					trayIcon;
	private Image						image;
	private ImageIcon					iion;
	private ImageIcon					iioff;
	private SystemTray					tray;
	private JLabel						lblbadgecourant		= new JLabel ("  0  ");
	private JLabel						lblbadgedernier		= new JLabel ("  0  ");
	private JScrollPane					defileurClassement	= new JScrollPane ();
	private JScrollPane					defileurSuivi		= new JScrollPane ();

	public FenetrePrincipale ()
	{
		super ("JCourse");
		Image imageON = new ImageIcon (FenetrePrincipale.class.getResource ("/images/cerclevert64.png")).getImage ();
		Image imageOFF = new ImageIcon (FenetrePrincipale.class.getResource ("/images/cerclerouge64.png")).getImage ();
		this.iion= new ImageIcon (imageON);
		this.iioff= new ImageIcon (imageOFF);
		
		
		Horloge h = new Horloge (this);
		h.start ();
		
		this.majGUI = MiseAJourGUI.getInstance (this);

		this.initialisationInterface ();
		this.traitements.ajouteEcouteur (this.barreDeMenu);
		this.traitements.ajouteEcouteur (this);
		this.infoCourante.setHorizontalAlignment (SwingConstants.LEFT);

		this.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener (this);
		this.initialisationInterface ();
		this.pack ();
		this.setLocationRelativeTo (null);
		this.setVisible (true);
		
		
		new FluxAsciiEvenement ();		
	}

	private void constructionTrayIcon ()
	{
		PopupMenu popup = new PopupMenu ();
		MenuItem MIrouvrir = new MenuItem ("Ouvrir");
		popup.add (MIrouvrir);
		MIrouvrir.addActionListener (this);
		MIrouvrir.setActionCommand ("rouvrir");
		if (SystemTray.isSupported ())
		{
			this.tray = SystemTray.getSystemTray ();
			this.trayIcon = new TrayIcon (this.image, "JMaitre", popup);
			this.trayIcon.setImageAutoSize (true);
		}

	}

	private void initialisationInterface ()
	{
		this.image = new ImageIcon (FenetrePrincipale.class.getResource ("/images/icone_course.jpg")).getImage ();
		this.setIconImage (image);
		this.barreDeMenu = new BarreDeMenu ();
		this.setJMenuBar (this.barreDeMenu);
		this.constructionTrayIcon ();
		this.add (this.barreDeMenu.getBarreOutils (), BorderLayout.PAGE_START);

		Tools.faitBordureEtTexte (this.defileurClassement, "Classement");
		Tools.faitBordureEtTexte (this.defileurSuivi, "Suivi passage");

		Tools.faitBordureEtTexte (this.infoCourante, "Information du lecteur de badge");
		Tools.faitBordureEtTexte (this.heureCourante, "Horloge");
		//Tools.faitBordureEtTexte (this.lblMeilleurTour, "Meilleur Tour en course");
		Tools.faitBordureEtTexte (this.lblTempsRestant, "Temps course restant");
		Tools.faitBordureEtTexte (this.lblbadgecourant, "Dernier Badge Lu");
		Tools.faitBordureEtTexte (this.lblbadgedernier, "Dernier Badge enregistré");
		Tools.faitBordureEtTexte (this.lblEtatCourse, "Etat course");

		this.infoCourante.setFont (new Font ("Tahoma", Font.BOLD, 14));
		this.heureCourante.setFont (new Font ("Tahoma", Font.BOLD, 24));
		this.lblTempsRestant.setFont (new Font ("Tahoma", Font.BOLD, 24));
		//this.lblMeilleurTour.setFont (new Font ("Tahoma", Font.BOLD, 20));

		this.lblbadgecourant.setFont (new Font ("Tahoma", Font.BOLD, 70));
		this.lblbadgedernier.setFont (new Font ("Tahoma", Font.BOLD, 70));

		this.lblbadgecourant.setHorizontalAlignment (JLabel.CENTER);
		this.lblbadgedernier.setHorizontalAlignment (JLabel.CENTER);
		this.lblTempsRestant.setHorizontalAlignment (JLabel.CENTER);
		//this.lblMeilleurTour.setHorizontalAlignment (JLabel.CENTER);
		this.lblEtatCourse.setHorizontalAlignment (JLabel.CENTER);

		this.heureCourante.setHorizontalAlignment (JLabel.CENTER);

		JPanel panPrincipal = new JPanel (new GridLayout (2, 1));
		JPanel panEst = new JPanel (new GridLayout (5, 1));
		panEst.setPreferredSize (new Dimension(180,1000));

		panPrincipal.add (this.defileurClassement);
		panPrincipal.add (this.defileurSuivi);

		panEst.add (this.lblEtatCourse);
		panEst.add (this.lblbadgecourant);
		panEst.add (this.lblbadgedernier);
		panEst.add (this.heureCourante);
		panEst.add (this.lblTempsRestant);
		//panEst.add (this.lblMeilleurTour);
		

		this.add (panPrincipal, BorderLayout.CENTER);
		this.add (panEst, BorderLayout.EAST);
		this.infoCourante.setPreferredSize (new Dimension (1000, 50));
		this.add (this.infoCourante, BorderLayout.SOUTH);
		this.setExtendedState (MAXIMIZED_BOTH);
		this.setResizable (true);
	}

	public void confirmationQuitter ()
	{
		int reponse = JOptionPane.showConfirmDialog (null, "Voulez vous vraiment quitter l'application ?",
				"Fermeture application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (reponse == JOptionPane.YES_OPTION)
		{
			System.exit (0);
		}
	}

	@Override
	public void windowActivated (WindowEvent arg0)
	{

	}

	@Override
	public void windowClosed (WindowEvent arg0)
	{

	}

	@Override
	public void windowClosing (WindowEvent arg0)
	{
		this.confirmationQuitter ();
	}

	@Override
	public void windowDeactivated (WindowEvent arg0)
	{

	}

	@Override
	public void windowDeiconified (WindowEvent arg0)
	{

	}

	@Override
	public void windowIconified (WindowEvent e)
	{
		try
		{
			this.tray.add (trayIcon);
		}
		catch (AWTException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace ();
		}
		this.setVisible (false);
	}

	@Override
	public void windowOpened (WindowEvent arg0)
	{

	}

	@Override
	public void propertyChange (PropertyChangeEvent evt)
	{
		String cause = evt.getPropertyName ();

		if (cause.equals ("supprimer"))
		{
			new DialogSupprTour ();
			return;
		}

		if (cause.equals ("manu"))
		{
			new DialogPassageManu ();
			return;
		}

		if (cause.equals ("depart"))
		{
			this.majGUI.departCourse ();
			return;
		}

		if (cause.equals ("editer"))
		{
			new DialogEditerEquipe ();
		}

		if (cause.equals ("quitter"))
		{
			this.confirmationQuitter ();
		}
		if (cause.equals ("paramCourse"))
		{
			new DialogParamCourse ();
		}

	}

	public JLabel getInfoCourante ()
	{
		return infoCourante;
	}

	public void setInfoCourante (String aInfoCourante)
	{
		this.infoCourante.setText (aInfoCourante);
	}

	@Override
	public void windowStateChanged (WindowEvent e)
	{

	}

	@Override
	public void actionPerformed (ActionEvent aAction)
	{
		String cause = aAction.getActionCommand ();
		if ("rouvrir".equals (cause))
		{
			this.tray.remove (this.trayIcon);
			this.setExtendedState (JFrame.NORMAL);
			this.setVisible (true);
		}
	}

	public BarreDeMenu getBarreDeMenu ()
	{
		return barreDeMenu;
	}

	public void setBarreDeMenu (BarreDeMenu barreDeMenu)
	{
		this.barreDeMenu = barreDeMenu;
	}

	public JLabel getLblbadgecourant ()
	{
		return lblbadgecourant;
	}

	public void setLblbadgecourant (String aLblbadgecourant)
	{
		this.lblbadgecourant.setText (aLblbadgecourant);
	}

	public JLabel getLblbadgedernier ()
	{
		return lblbadgedernier;
	}

	public void setLblbadgedernier (String aLblbadgedernier)
	{
		this.lblbadgedernier.setText (aLblbadgedernier);
	}

	public JScrollPane getDefileurClassement ()
	{
		return defileurClassement;
	}

	public void setDefileurClassement (JScrollPane defileurClassement)
	{
		this.defileurClassement = defileurClassement;
	}

	public JScrollPane getDefileurSuivi ()
	{
		return defileurSuivi;
	}

	public void setDefileurSuivi (JScrollPane defileurSuivi)
	{
		this.defileurSuivi = defileurSuivi;
	}

	public JLabel getHeureCourante ()
	{
		return heureCourante;
	}

	public void setHeureCourante (String aHeureCourante)
	{
		this.heureCourante.setText (aHeureCourante);
	}

	public JLabel getLblTempsRestant ()
	{
		return lblTempsRestant;
	}

	public void setLblTempsRestant (String alblTempsRestant)
	{
		this.lblTempsRestant.setText (alblTempsRestant);
	}

//	public JLabel getLblMeilleurTour ()
//	{
//		return lblMeilleurTour;
//	}
//
//	public void setLblMeilleurTour (String lblMeilleurTour)
//	{
//		this.lblMeilleurTour.setText (lblMeilleurTour);
//	}

	public JLabel getLblEtatCourse ()
	{
		return lblEtatCourse;
	}

	public void setLblEtatCourse (Icon aIconEtatCourse)
	{
		this.lblEtatCourse.setIcon (aIconEtatCourse);
	}

	public ImageIcon getIion ()
	{
		return iion;
	}

	public void setIion (ImageIcon iion)
	{
		this.iion = iion;
	}

	public ImageIcon getIioff ()
	{
		return iioff;
	}

	public void setIioff (ImageIcon iioff)
	{
		this.iioff = iioff;
	}
}
