
package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import utilitaires.Tools;
import connexionBase.ConnexionDBrec;

@SuppressWarnings("serial")
public class DialogEditerEquipe extends JDialog implements ActionListener, ItemListener
{
	private JFileChooser		filechooser			= new JFileChooser ("C:\\Users\\Christophe_\\Pictures");
	private JTextField			saisieDescription	= new JTextField (10);
	private JButton				valider				= new JButton ("Enregistrer", new ImageIcon (
															DialogEditerEquipe.class
																	.getResource ("/images/valider_petit.png")));
	private JComboBox<String>	listeBadge			= new JComboBox<String> ();
	private ConnexionDBrec		cdb					= ConnexionDBrec.getInstance ();
	// private String item;
	private JLabel				lblInfoValidation	= new JLabel (" ");
	private JButton				supprimer			= new JButton ("Supprimer", new ImageIcon (
															DialogEditerEquipe.class
																	.getResource ("/images/supprimer_petit.png")));
	private JPanel				panPrinc			= new JPanel ();
	private JLabel				lblNomFichier		= new JLabel ();
	private FileInputStream		fis;
	private int					len;
	private final JLabel		lblBadge			= new JLabel ("Badge");
	private final JLabel		lblNomDeLequipe		= new JLabel ("Nom de l'equipe");
	private JButton				chargement			= new JButton ("Chargement image...");
	private boolean 			logo;

	public DialogEditerEquipe ()
	{
		this.setTitle ("Edition des equipes");
		getContentPane ().setLayout (new FlowLayout (FlowLayout.CENTER, 5, 5));
		Tools.faitBordureEtTexte (this.panPrinc, "Edition des équipes");
		this.getContentPane ().add (panPrinc);
		GridBagLayout gbl_panPrinc = new GridBagLayout ();
		gbl_panPrinc.columnWidths = new int [] {126, 10, 69, 0};
		gbl_panPrinc.rowHeights = new int [] {35, 33, 60, 36, 25, 0};
		gbl_panPrinc.columnWeights = new double [] {0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panPrinc.rowWeights = new double [] {0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panPrinc.setLayout (gbl_panPrinc);

		GridBagConstraints gbc_lblBadge = new GridBagConstraints ();
		gbc_lblBadge.anchor = GridBagConstraints.EAST;
		gbc_lblBadge.insets = new Insets (0, 0, 5, 5);
		gbc_lblBadge.gridx = 0;
		gbc_lblBadge.gridy = 0;
		panPrinc.add (lblBadge, gbc_lblBadge);
		this.listeBadge.setEditable (true);
		
		GridBagConstraints gbc_listeBadge = new GridBagConstraints ();
		gbc_listeBadge.anchor = GridBagConstraints.WEST;
		gbc_listeBadge.insets = new Insets (0, 0, 5, 5);
		gbc_listeBadge.gridx = 1;
		gbc_listeBadge.gridy = 0;
		this.panPrinc.add (listeBadge, gbc_listeBadge);
				
		this.listeBadge.addItemListener (this);

		GridBagConstraints gbc_lblNomDeLequipe = new GridBagConstraints ();
		gbc_lblNomDeLequipe.insets = new Insets (0, 0, 5, 5);
		gbc_lblNomDeLequipe.anchor = GridBagConstraints.EAST;
		gbc_lblNomDeLequipe.gridx = 0;
		gbc_lblNomDeLequipe.gridy = 1;
		this.panPrinc.add (lblNomDeLequipe, gbc_lblNomDeLequipe);
		
		GridBagConstraints gbc_button = new GridBagConstraints ();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets (0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 2;
		this.chargement.setActionCommand ("chargement");
		this.chargement.addActionListener (this);
		GridBagConstraints gbc_saisieDescription = new GridBagConstraints ();
		gbc_saisieDescription.gridwidth = 2;
		gbc_saisieDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_saisieDescription.insets = new Insets(0, 0, 5, 5);
		gbc_saisieDescription.gridx = 1;
		gbc_saisieDescription.gridy = 1;
		this.panPrinc.add (this.saisieDescription, gbc_saisieDescription);
		this.panPrinc.add (this.chargement, gbc_button);

		GridBagConstraints gbc_lblNomFichier = new GridBagConstraints ();
		gbc_lblNomFichier.anchor = GridBagConstraints.WEST;
		gbc_lblNomFichier.gridwidth = 2;
		gbc_lblNomFichier.insets = new Insets(0, 0, 5, 0);
		gbc_lblNomFichier.gridx = 1;
		gbc_lblNomFichier.gridy = 2;
		lblNomFichier.setText ("  Choisir une image");
		lblNomFichier.setHorizontalAlignment (SwingConstants.RIGHT);
		panPrinc.add (lblNomFichier, gbc_lblNomFichier);
		GridBagConstraints gbc_supprimer = new GridBagConstraints ();
		gbc_supprimer.fill = GridBagConstraints.BOTH;
		gbc_supprimer.insets = new Insets (0, 0, 5, 5);
		gbc_supprimer.gridx = 1;
		gbc_supprimer.gridy = 3;
		this.panPrinc.add (supprimer, gbc_supprimer);
		this.supprimer.addActionListener (this);
		this.supprimer.setActionCommand ("supprimer");
		GridBagConstraints gbc_valider = new GridBagConstraints ();
		gbc_valider.fill = GridBagConstraints.BOTH;
		gbc_valider.insets = new Insets(0, 0, 5, 0);
		gbc_valider.gridx = 2;
		gbc_valider.gridy = 3;
		this.panPrinc.add (this.valider, gbc_valider);
		
		this.valider.addActionListener (this);
		this.valider.setActionCommand ("valider");
		lblInfoValidation.setHorizontalAlignment (SwingConstants.RIGHT);
		GridBagConstraints gbc_lblInfoValidation = new GridBagConstraints ();
		gbc_lblInfoValidation.gridwidth = 2;
		gbc_lblInfoValidation.anchor = GridBagConstraints.WEST;
		gbc_lblInfoValidation.fill = GridBagConstraints.VERTICAL;
		gbc_lblInfoValidation.gridx = 1;
		gbc_lblInfoValidation.gridy = 4;
		this.panPrinc.add (lblInfoValidation, gbc_lblInfoValidation);

		this.setModal (true);
		this.initialize ();
		this.pack ();
		this.setSize (410, 255);
		this.setResizable (false);
		this.setLocationRelativeTo (null);
		this.setVisible (true);
	}

	public void initialize ()
	{
		String reqSQL = "SELECT id_badge FROM Telethon..equipes order by 1";
		try
		{
			this.listeBadge.removeAllItems ();
			ResultSet res = this.cdb.getOrdreSql ().executeQuery (reqSQL);
			while (res.next ())
			{
				this.listeBadge.addItem (res.getString ("id_badge"));
			}
			res.close ();
			this.afficheBadge (this.listeBadge.getItemAt (0));
		}
		catch (SQLException aSQLE)
		{
			System.err.println (aSQLE.getMessage ());
		}

	}

	public void afficheBadge (String aItem)
	{
		String reqSQL = "SELECT entreprise, nom_equipe FROM Telethon..equipes WHERE id_badge = " + aItem;
		Image image;
		try
		{
			ResultSet res = this.cdb.getConnexionSqlServer ().createStatement ().executeQuery (reqSQL);
			while (res.next ())
			{
				this.saisieDescription.setText (res.getString ("nom_equipe"));
				if (res.getBlob ("entreprise") != null)
				{
					image = ImageIO.read (res.getBlob ("entreprise").getBinaryStream ());
					ImageIcon icon = new ImageIcon (image.getScaledInstance (100, 50, Image.SCALE_SMOOTH));
					this.lblNomFichier.setIcon (icon);
					this.lblNomFichier.setText ("");
					this.logo = true;
				}
				else
				{
					this.lblNomFichier.setText ("  Choisissez une image");
					this.lblNomFichier.setIcon (null);
					this.logo = false;
				}

			}
			res.close ();
		}

		catch (SQLException aSQLE)
		{
			System.out.println (aSQLE.getLocalizedMessage ());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace ();
		}
	}

	public void valideBadge ()
	{
		String item = (String) this.listeBadge.getSelectedItem ();
		String reqSQL = "SELECT count(*) FROM Telethon..equipes WHERE id_badge = " + item;
		int testItem = 0;
		String eq = this.saisieDescription.getText ();
		InputStream img = this.fis;

//		if (this.fis != null)
//		{
//			img = this.fis;
//		}

		if (eq.equals (""))
		{
			JOptionPane.showMessageDialog (this, "Saisir le nom de l'équipe", "Erreur de saisie",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try
		{
			ResultSet res = this.cdb.getOrdreSql ().executeQuery (reqSQL);
			while (res.next ())
			{
				testItem = res.getInt (1);
			}
			res.close ();

			if (testItem == 1)
			{
				if (logo == false)
				{
					String uSQL = "UPDATE Telethon..equipes SET entreprise = ? , nom_equipe = ? WHERE id_badge = ?";
					PreparedStatement pS = this.cdb.getpStatement (uSQL);
					pS.setBinaryStream (1, img, this.len);
					pS.setString (2, eq);
					pS.setString (3, item);
					pS.addBatch ();
					pS.executeBatch ();
					this.lblInfoValidation.setText ("Equipe " + item + " mise à jour");
					this.fis = null;
				}
				else
				{
					String uSQL = "UPDATE Telethon..equipes SET nom_equipe = ? WHERE id_badge = ?";
					PreparedStatement pS = this.cdb.getpStatement (uSQL);
					pS.setString (1, eq);
					pS.setString (2, item);
					pS.addBatch ();
					pS.executeBatch ();
					this.lblInfoValidation.setText ("Equipe " + item + " mise à jour");
					this.fis = null;
				}
				
				//this.lblNomFichier.setText ("  Choisissez un fichier");
			}
			else
			{
				String iSQL = "INSERT INTO Telethon..equipes (id_badge, entreprise, nom_equipe) values (?,?,?)";
				PreparedStatement pS = this.cdb.getpStatement (iSQL);
				if (item == null)
				{
					this.lblInfoValidation.setText ("Saisir un numéro de badge !");
				}
				else
				{
					pS.setString (1, item);
					pS.setBinaryStream (2, img, this.len);
					pS.setString (3, eq);
					pS.addBatch ();
					pS.executeBatch ();
					this.lblInfoValidation.setText ("Equipe " + item + " ajoutée !");
					this.fis = null;
					this.listeBadge.addItem (item);
				}
			}
		}
		catch (SQLException aSQLE)
		{
			System.out.println ("catch " + aSQLE.getLocalizedMessage ());
		}
	}

	@Override
	public void actionPerformed (ActionEvent aAe)
	{

		String cause = aAe.getActionCommand ();
		if (cause.equals ("valider"))
		{
			this.valideBadge ();
			return;
		}

		if (cause.equals ("chargement"))
		{
			int returnVal = this.filechooser.showOpenDialog (this);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = this.filechooser.getSelectedFile ();

				this.lblNomFichier.setText (file.getName ());
				this.lblNomFichier.setIcon (null);
				try
				{
					this.fis = new FileInputStream (file);
					this.len = (int) file.length ();

				}
				catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace ();
				}

			}
		}

		if (cause.equals ("supprimer"))
		{
			String item = (String) this.listeBadge.getSelectedItem ();
			if (item == null)
			{
				this.lblInfoValidation.setText ("Pas d'esclave à supprimer !");
			}
			else
			{
				String dSQL = "DELETE FROM Telethon..equipes WHERE id_badge = " + item;
				try
				{
					this.cdb.getOrdreSql ().executeUpdate (dSQL);
				}
				catch (SQLException e)
				{
					this.lblInfoValidation.setText (e.getLocalizedMessage ());
					return;
				}
				this.lblInfoValidation.setText ("Equipe " + item + " supprimé");
				this.listeBadge.removeItem (item);
				return;
			}
		}
	}

	@Override
	public void itemStateChanged (ItemEvent event)
	{
		if (event.getStateChange () == ItemEvent.SELECTED)
		{
			String item = (String) event.getItem ();
			this.afficheBadge (item);
		}
	}
}