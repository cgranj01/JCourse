package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import utilitaires.Tools;
import connexionBase.ConnexionDBrec;

@SuppressWarnings("serial")
public class DialogPassageManu extends JDialog implements ActionListener
{
	private ConnexionDBrec cdb = ConnexionDBrec.getInstance ();
	
	public DialogPassageManu()
	{
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel fond = new JPanel (new GridLayout(5, 5, 20, 20));
		Tools.faitBordureEtTexte (fond, "Panneau déclenchement tour mode secours");
		getContentPane().add (fond);
		
		String reqSQL = "SELECT id_badge FROM Telethon..equipes order by id_badge";
        try 
        {
            this.cdb.getOrdreSql ();
            ResultSet res = this.cdb.getOrdreSql ().executeQuery(reqSQL);
            while (res.next())
            {
            	String badge = res.getString (1);
    			JButton equipe = new JButton (" Passage équipe " + badge, new ImageIcon (
    					DialogPassageManu.class.getResource ("/images/chronometre-icone.png"))); 
    			equipe.addActionListener (this);
    			equipe.setActionCommand (badge);
    			fond.add (equipe);
            }
            res.close();
		}
        catch(SQLException aSQLE)
        {
        	System.out.println (aSQLE.getMessage ());
        }
        
        this.pack();
		//this.setSize(3, 210);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
        
        
	}
	
	public void validePassage(String aCause)
	{
		new DialogHorodatePassageManuel (aCause);	
	}
	

	@Override
	public void actionPerformed (ActionEvent e)
	{
		String cause = e.getActionCommand ();
		
		this.validePassage (cause);
		
	}

}
