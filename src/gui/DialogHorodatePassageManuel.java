package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DialogHorodatePassageManuel extends JDialog implements ActionListener
{
	private JTextField textFieldHEquipe;
	private String equipe;
	private MiseAJourGUI majGUI = MiseAJourGUI.getInstance ();
	private SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
	
	public DialogHorodatePassageManuel (String aEquipe)
	{
		this.equipe = aEquipe;
		setTitle("Horodatage \u00E9quipe");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{156, 0, 146, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 33, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblHEquipe = new JLabel("Heure passage Equipe " + this.equipe);
		GridBagConstraints gbc_lblHEquipe = new GridBagConstraints();
		gbc_lblHEquipe.anchor = GridBagConstraints.EAST;
		gbc_lblHEquipe.fill = GridBagConstraints.VERTICAL;
		gbc_lblHEquipe.insets = new Insets(0, 0, 5, 5);
		gbc_lblHEquipe.gridx = 0;
		gbc_lblHEquipe.gridy = 2;
		getContentPane().add(lblHEquipe, gbc_lblHEquipe);
		
		textFieldHEquipe = new JTextField();
		GridBagConstraints gbc_textFieldHEquipe = new GridBagConstraints();
		gbc_textFieldHEquipe.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHEquipe.fill = GridBagConstraints.BOTH;
		gbc_textFieldHEquipe.gridx = 2;
		gbc_textFieldHEquipe.gridy = 2;
		getContentPane().add(textFieldHEquipe, gbc_textFieldHEquipe);
		textFieldHEquipe.setColumns(10);
		
		JButton btnValider = new JButton("  Valider  ");
		btnValider.setIcon(new ImageIcon(DialogHorodatePassageManuel.class.getResource("/images/valider_petit.png")));
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.insets = new Insets(0, 0, 5, 5);
		gbc_btnValider.fill = GridBagConstraints.VERTICAL;
		gbc_btnValider.gridx = 2;
		gbc_btnValider.gridy = 4;
		getContentPane().add(btnValider, gbc_btnValider);
		btnValider.addActionListener (this);
		btnValider.setActionCommand ("valider");
	
		this.initialize ();
		
		this.pack ();
		//this.setSize (320, 240);
		this.setResizable (false);
		this.setLocationRelativeTo (null);
		this.setVisible (true);	
	}
	
	public void initialize ()
	{
		Date date = new Date (System.currentTimeMillis ());
		this.textFieldHEquipe.setText (this.sdf.format (date));		
	}

	@Override
	public void actionPerformed (ActionEvent aAe)
	{
		String cause = aAe.getActionCommand ();
		if(cause.equals ("valider"))
		{		
			long parsedate;
			try
			{
				parsedate = this.sdf.parse (this.textFieldHEquipe.getText ()).getTime ();
				this.majGUI.insertion (Integer.parseInt (this.equipe), parsedate, false, false);
			}
			catch (ParseException e1)
			{
				JOptionPane.showMessageDialog (null, "Saisie incorrecte", "Erreur saisie", JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.dispose ();
			
			
		}		
	}
	
}
