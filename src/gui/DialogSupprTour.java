package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import utilitaires.Tools;
import connexionBase.ConnexionDBrec;

@SuppressWarnings("serial")
public class DialogSupprTour extends javax.swing.JDialog implements ActionListener
{
		private JTextField	textFieldIdBadge;
		private JTextField	textFieldNumtour;
		private ConnexionDBrec cdb	= ConnexionDBrec.getInstance ();

		
		public DialogSupprTour ()
		{
			setModal (true);
			setTitle ("Suppression tour");
			getContentPane ().setLayout (new FlowLayout (FlowLayout.CENTER, 5, 5));

			JPanel panel = new JPanel ();
			getContentPane ().add (panel);
			GridBagLayout gbl_panel = new GridBagLayout ();
			gbl_panel.columnWidths = new int [] {163, 117, 0};
			gbl_panel.rowHeights = new int [] {15, 10, 0, 20, 20, 20, 40};
			gbl_panel.columnWeights = new double [] {0.0, 1.0, 0.0};
			gbl_panel.rowWeights = new double [] {0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout (gbl_panel);

			JLabel lblBadgeId = new JLabel ("Num\u00E9ro badge");
			GridBagConstraints gbc_lblid_course = new GridBagConstraints ();
			gbc_lblid_course.anchor = GridBagConstraints.EAST;
			gbc_lblid_course.insets = new Insets (0, 0, 5, 5);
			gbc_lblid_course.gridx = 0;
			gbc_lblid_course.gridy = 1;
			panel.add (lblBadgeId, gbc_lblid_course);
			lblBadgeId.setHorizontalAlignment (SwingConstants.RIGHT);

			textFieldIdBadge = new JTextField ();
			GridBagConstraints gbc_textFieldIdBadge = new GridBagConstraints ();
			gbc_textFieldIdBadge.anchor = GridBagConstraints.WEST;
			gbc_textFieldIdBadge.fill = GridBagConstraints.VERTICAL;
			gbc_textFieldIdBadge.insets = new Insets (0, 0, 5, 5);
			gbc_textFieldIdBadge.gridx = 1;
			gbc_textFieldIdBadge.gridy = 1;
			panel.add (textFieldIdBadge, gbc_textFieldIdBadge);
			textFieldIdBadge.setText ("1");
			textFieldIdBadge.setColumns (10);

			JLabel lblNumtour = new JLabel ("Numero tour");
			GridBagConstraints gbc_lblNumtour = new GridBagConstraints ();
			gbc_lblNumtour.anchor = GridBagConstraints.EAST;
			gbc_lblNumtour.insets = new Insets (0, 0, 5, 5);
			gbc_lblNumtour.gridx = 0;
			gbc_lblNumtour.gridy = 2;
			panel.add (lblNumtour, gbc_lblNumtour);
			lblNumtour.setHorizontalAlignment (SwingConstants.RIGHT);

			textFieldNumtour = new JTextField ();
			GridBagConstraints gbc_textFieldNumtour = new GridBagConstraints ();
			gbc_textFieldNumtour.anchor = GridBagConstraints.WEST;
			gbc_textFieldNumtour.fill = GridBagConstraints.VERTICAL;
			gbc_textFieldNumtour.insets = new Insets (0, 0, 5, 5);
			gbc_textFieldNumtour.gridx = 1;
			gbc_textFieldNumtour.gridy = 2;
			panel.add (textFieldNumtour, gbc_textFieldNumtour);
			textFieldNumtour.setColumns (10);
			GridBagConstraints gbc_lblHeureFin = new GridBagConstraints ();
			gbc_lblHeureFin.insets = new Insets (0, 0, 5, 5);
			gbc_lblHeureFin.gridx = 0;
			gbc_lblHeureFin.gridy = 4;
			GridBagConstraints gbc_textFieldHeureFin = new GridBagConstraints ();
			gbc_textFieldHeureFin.gridwidth = 2;
			gbc_textFieldHeureFin.fill = GridBagConstraints.BOTH;
			gbc_textFieldHeureFin.insets = new Insets (0, 0, 5, 0);
			gbc_textFieldHeureFin.gridx = 1;
			gbc_textFieldHeureFin.gridy = 4;
			

			
			JButton btnNouvDepart = new JButton ("Supprimer");
			GridBagConstraints gbc_btnNouvDepart = new GridBagConstraints ();
			gbc_btnNouvDepart.insets = new Insets (0, 0, 0, 5);
			gbc_btnNouvDepart.gridx = 1;
			gbc_btnNouvDepart.gridy = 6;
			panel.add (btnNouvDepart, gbc_btnNouvDepart);
			btnNouvDepart.addActionListener (this);
			btnNouvDepart.setActionCommand ("sup");

			Tools.faitBordureEtTexte (panel, "Parametres courses");

			this.pack ();
			this.setSize (320, 240);
			this.setResizable (false);
			this.setLocationRelativeTo (null);
			this.setVisible (true);
		}

		

		public void supprimer ()
		{
			
				String insertHeureDepart = "WITH q AS  " +
											"( " +
													" select top(1) *" +
													" from Telethon..info_tours where id_badge = ?"   +
													" and nb_tour_faits = ? ORDER BY date_heure_passage desc" +
											" )" +
											" DELETE FROM  q";
				PreparedStatement pStatement = this.cdb.getpStatement (insertHeureDepart);		
				try
				{
					pStatement.setString (1, this.textFieldIdBadge.getText ());
					pStatement.setString (2, this.textFieldNumtour.getText ());
					pStatement.addBatch ();
					pStatement.executeBatch ();
					MiseAJourGUI.getInstance ().miseAjourJTable ();
					this.dispose ();
		
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog (this, "Erreur : Suppression impossible", "Erreur", JOptionPane.ERROR_MESSAGE);
				}			
						
				
		}

		@Override
		public void actionPerformed (ActionEvent aAe)
		{
			String cause = aAe.getActionCommand ();

			if ("sup".equals (cause))
			{
				this.supprimer ();

				// this.tpsCourse.start ();
			}

			if ("arreter".equals (cause))
			{
				String strg = "UPDATE Telethon..course SET etat_course = 'OFF' where id_course = 1";
				try
				{
					this.cdb.getpStatement (strg).execute ();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace ();
				}

			}

		}

}
