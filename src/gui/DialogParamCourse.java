
package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utilitaires.ApplicationConfiguration;

@SuppressWarnings("serial")
public class DialogParamCourse extends JDialog implements ActionListener
{
	private ApplicationConfiguration appConf = ApplicationConfiguration.getInstance ();
	private JTextField	textFieldHeureFin = new JTextField ();
	private SimpleDateFormat sdf = new SimpleDateFormat ("HH:mm:ss");
	
	private JPanel panPrinc = new JPanel();
	private JTextField textFieldDelay = new JTextField();
	private final JLabel lblTempsDeCourse = new JLabel("Temps au tour minimum");
	private final JLabel label = new JLabel("Temps de course");
	private final JLabel lblSecondes = new JLabel("secondes");
	private final JLabel label_1 = new JLabel("N\u00E9cessite un red\u00E9marrage de l'application");
	

	public DialogParamCourse ()
	{
		this.textFieldDelay.setColumns(10);
		this.setModal (true);
		this.setTitle ("Parametres course");

		this.initialize ();

		this.pack ();
		this.setResizable (false);
		this.setLocationRelativeTo (null);		
		this.setVisible (true);
	}

	public void initialize ()
	{	
		getContentPane().setLayout(new FlowLayout ());
		getContentPane().add(this.panPrinc);
		GridBagLayout gbl_panPrinc = new GridBagLayout();
		gbl_panPrinc.columnWidths = new int[]{119, 105, 106, 0};
		gbl_panPrinc.rowHeights = new int[]{30, 27, 24, 33, 0};
		gbl_panPrinc.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panPrinc.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panPrinc.setLayout(gbl_panPrinc);
		
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panPrinc.add(label, gbc_label);
		
		//Tools.faitBordureEtTexte (this.textFieldHeureFin, "Heure de fin");
		
		GridBagConstraints gbc_textFieldHeureFin = new GridBagConstraints();
		gbc_textFieldHeureFin.gridwidth = 2;
		gbc_textFieldHeureFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHeureFin.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldHeureFin.gridx = 1;
		gbc_textFieldHeureFin.gridy = 0;
		this.panPrinc.add (this.textFieldHeureFin, gbc_textFieldHeureFin);
		
		this.textFieldHeureFin.setText (this.appConf.getConfiguration ("tempscourse"));
		this.textFieldDelay.setText (this.appConf.getConfiguration ("delay"));
		
		GridBagConstraints gbc_lblTempsDeCourse = new GridBagConstraints();
		gbc_lblTempsDeCourse.insets = new Insets(0, 0, 5, 5);
		gbc_lblTempsDeCourse.anchor = GridBagConstraints.EAST;
		gbc_lblTempsDeCourse.gridx = 0;
		gbc_lblTempsDeCourse.gridy = 1;
		panPrinc.add(lblTempsDeCourse, gbc_lblTempsDeCourse);
		
		GridBagConstraints gbc_textFieldDelay = new GridBagConstraints();
		gbc_textFieldDelay.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDelay.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDelay.gridx = 1;
		gbc_textFieldDelay.gridy = 1;
		panPrinc.add(this.textFieldDelay, gbc_textFieldDelay);
		
		GridBagConstraints gbc_lblSecondes = new GridBagConstraints();
		gbc_lblSecondes.anchor = GridBagConstraints.WEST;
		gbc_lblSecondes.insets = new Insets(0, 0, 5, 0);
		gbc_lblSecondes.gridx = 2;
		gbc_lblSecondes.gridy = 1;
		panPrinc.add(lblSecondes, gbc_lblSecondes);
		
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.NORTH;
		gbc_label_1.gridwidth = 2;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 2;
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panPrinc.add(label_1, gbc_label_1);
		//JButton boutonDepart = new JButton      ("   Départ  ");
		JButton boutonArret = new JButton       ("   Arret   ");
		GridBagConstraints gbc_boutonArret = new GridBagConstraints();
		gbc_boutonArret.insets = new Insets(0, 0, 0, 5);
		gbc_boutonArret.gridx = 0;
		gbc_boutonArret.gridy = 3;
		panPrinc.add(boutonArret, gbc_boutonArret);
		//boutonDepart.setActionCommand ("depart");
		boutonArret.setActionCommand ("arret");
		//boutonDepart.addActionListener (this);
		boutonArret.addActionListener (this);
		
		JButton boutonQuitter = new JButton     ("  Annuler  ");
		GridBagConstraints gbc_boutonQuitter = new GridBagConstraints();
		gbc_boutonQuitter.insets = new Insets(0, 0, 0, 5);
		gbc_boutonQuitter.gridx = 1;
		gbc_boutonQuitter.gridy = 3;
		panPrinc.add(boutonQuitter, gbc_boutonQuitter);
		
		boutonQuitter.setActionCommand ("annuler");
		
		boutonQuitter.addActionListener (this);
		JButton boutonEnregistrer = new JButton ("Enregistrer");
		GridBagConstraints gbc_boutonEnregistrer = new GridBagConstraints();
		gbc_boutonEnregistrer.gridx = 2;
		gbc_boutonEnregistrer.gridy = 3;
		panPrinc.add(boutonEnregistrer, gbc_boutonEnregistrer);
		boutonEnregistrer.setActionCommand ("enregistrer");
		boutonEnregistrer.addActionListener (this);
	}

	@Override
	public void actionPerformed (ActionEvent evt)
	{
		String cause = evt.getActionCommand ();
		if (cause.equals ("annuler"))
		{
			this.dispose ();
		}
		if (cause.equals ("arret"))
		{
			//Date date = new Date (System.currentTimeMillis ());
			this.appConf.setConfiguration ("etat", "OFF");
			this.appConf.save ("config.properties");
			this.dispose ();
		}
		if (cause.equals ("enregistrer"))
		{
			try
			{
				this.sdf.parse (this.textFieldHeureFin.getText ());
				this.appConf.setConfiguration ("tempscourse", this.textFieldHeureFin.getText ());
				this.appConf.setConfiguration ("delay", this.textFieldDelay.getText ());
				this.appConf.save ("config.properties");
				this.dispose ();
			}
			catch (ParseException e)
			{
				JOptionPane.showMessageDialog (null, "Merci de respecter le format \n jj/mm/aaaa hh:mm:ss", "Erreur Saisie", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
}
