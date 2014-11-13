
package gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import utilitaires.ApplicationConfiguration;
import connexionBase.ConnexionDBrec;

public class MiseAJourGUI
{
	private static MiseAJourGUI			instance;
	private ApplicationConfiguration	appConf				= ApplicationConfiguration.getInstance ();
	private FenetrePrincipale			fp;
	private Vector<Object>				enteteClassement	= new Vector<Object> ();
	private Vector<Vector<Object>>		donneesClassement	= new Vector<Vector<Object>> ();
	private Vector<Object>				enteteSuivi			= new Vector<Object> ();
	private Vector<Vector<Object>>		donneesSuivi		= new Vector<Vector<Object>> ();

	private ConnexionDBrec				cdb					= ConnexionDBrec.getInstance ();

	private DefaultTableModel			tmClassement;
	private DefaultTableModel			tmSuivi;

	private JTable						jtClassement		= new JTable ();
	private JTable						jtSuivi				= new JTable ();

	private MiseAJourGUI (FenetrePrincipale aFp)
	{
		this.fp = aFp;
		this.miseAjourJTable ();
		this.miseEnFormeJTable (this.jtClassement);
		this.miseEnFormeJTable (this.jtSuivi);
		this.tmClassement = new ModelTable (this.donneesClassement, this.enteteClassement);
		this.tmSuivi = new ModelTable (this.donneesSuivi, this.enteteSuivi);
		this.jtClassement.setModel (this.tmClassement);
		this.jtSuivi.setModel (this.tmSuivi);
		
	}
	
	public void miseEnFormeJTable (JTable aJTable)
	{
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        aJTable.setDefaultRenderer(String.class, centerRenderer); 
		
		TableCellRenderer rendererFromHeader = aJTable.getTableHeader().getDefaultRenderer();
		JLabel headerLabel = (JLabel) rendererFromHeader;
		headerLabel.setHorizontalAlignment(JLabel.CENTER);
		
		aJTable.setFont (new Font ("Tahoma", Font.BOLD, 14));
		aJTable.setRowHeight (30);
		aJTable.setRowSelectionAllowed (false);
		aJTable.setShowVerticalLines (false);
		aJTable.getTableHeader ().setAlignmentX (JTable.CENTER_ALIGNMENT);
	}

	public static MiseAJourGUI getInstance (FenetrePrincipale aFP)
	{
		if (MiseAJourGUI.instance == null)
		{
			MiseAJourGUI.instance = new MiseAJourGUI (aFP);
		}
		return MiseAJourGUI.instance;
	}

	public static MiseAJourGUI getInstance ()
	{
		return MiseAJourGUI.instance;
	}

	public void tableClassement ()
	{
		this.donneesClassement.clear ();
		this.enteteClassement.clear ();
		ResultSetMetaData description;
		int nombreDeColonnes;
		try
		{
			PreparedStatement ps = this.cdb.getpStatement ("exec Telethon..classement");
			ps.addBatch ();
			ps.executeBatch ();
			ResultSet res = this.cdb.getOrdreSql ().executeQuery (
					"select cast(Classement as nvarchar(2)) as Classement, nom_equipe as Equipe, cast(nb_tours_faits as nvarchar(4)) as 'Nb Tours'"
							+ ", Cast(date_heure_passage as time(3)) as 'Heure dernier passage', CAST(ecart as time(3)) as 'Ecart Temps', meilleur_temps as 'Meilleur Temps'"
							// ", ecart_temps as 'Ecart temps', ecart_tours as
							// 'Ecart tours'
							+ " from Telethon..vue_classement_general");
			description = res.getMetaData ();
			nombreDeColonnes = description.getColumnCount ();

			for (int i = 1; i <= nombreDeColonnes; i ++ )
			{
				this.enteteClassement.add (description.getColumnName (i));
			}

			while (res.next ())
			{
				Vector<Object> ligne = new Vector<Object> ();
//				Blob logo;
				for (int i = 1; i <= nombreDeColonnes; i ++ )
				{
//					if (i==2)
//					{
//						if ((logo = res.getBlob (i)) != null)
//						{
//							try
//							{
//								BufferedImage image = ImageIO.read(logo.getBinaryStream ());
//								ImageIcon icon = new ImageIcon (image);
//								ligne.add (icon);
//							}
//							catch (IOException e)
//							{
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//							}
//						}
//						else
//						{
//							ligne.add ("");
//						}
//						continue;
//					}
					ligne.add (res.getObject (i));					
					
				}
				this.donneesClassement.add (ligne);
			}
			
			res.close();
		}

		catch (SQLException aSQLE)
		{
			System.err.println (aSQLE.getMessage ());
			return;
		}
	}

	public void tableSuivi ()
	{
		this.donneesSuivi.clear ();
		this.enteteSuivi.clear ();
		System.out.println ("ok");
		ResultSetMetaData description;
		int nombreDeColonnes;

		try
		{
			String reqSQL = "SELECT TOP(20) cast(info_tours.id_badge as nvarchar(2)) as Badge, "
					+ "nom_equipe as Equipe, Cast(date_heure_passage as time(3)) as 'Heure dernier passage'"
					+ ", temps_dernier_tour as 'Temps dernier tour', lecture_badge as 'Passage badge'"
					+ " FROM Telethon..info_tours, Telethon..equipes WHERE info_tours.id_badge=equipes.id_badge"
					+ " order by date_heure_passage desc";
			ResultSet res = this.cdb.getOrdreSql ().executeQuery (reqSQL);
			description = res.getMetaData ();
			nombreDeColonnes = description.getColumnCount ();

			for (int i = 1; i <= nombreDeColonnes; i ++ )
			{
				this.enteteSuivi.add (description.getColumnName (i));
			}

			while (res.next ())
			{
				Vector<Object> ligne = new Vector<Object> ();
//				Blob logo;
				for (int i = 1; i <= nombreDeColonnes; i ++ )
				{
//					if (i==2)
//					{
//						if ((logo = res.getBlob (i)) != null)
//						{
//							try
//							{
//								BufferedImage image = ImageIO.read(logo.getBinaryStream ());
//								ImageIcon icon = new ImageIcon (image);
//								ligne.add (icon);
//							}
//							catch (IOException e)
//							{
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//							}
//						}
//						else
//						{
//							ligne.add ("");
//						}
//						continue;
//					}
					ligne.add (res.getObject (i));					
					
				}
				this.donneesSuivi.add (ligne);
			}
			
			res.close();
		}

		catch (SQLException aSQLE)
		{
			System.out.println (aSQLE.getMessage ());
			return;
		}

	}

//	public void rafraichirMeilleurtour ()
//	{
//		try
//		{
//			String reqSQL = "select top (1) id_badge, temps_dernier_tour from Telethon..info_tours " +
//					"where nb_tour_faits >0 and temps_dernier_tour is not null order by 2";
//			ResultSet res = this.cdb.getOrdreSql ().executeQuery (reqSQL);
//
//			while (res.next ())
//			{
//				this.fp.setLblMeilleurTour ("<HTML>Equipe " + res.getString (1) + "<br/><br/>" + res.getString (2)
//						+ "</HTML>");
//			}
//			res.close ();
//		}
//
//		catch (SQLException aSQLE)
//		{
//			System.out.println (aSQLE.getMessage ());
//			return;
//		}
//	}

	public void insertion (int aBadge, Long aTime, boolean aLecteur, boolean aDepart)
	{
		Date date = new Date (aTime);
		System.out.println (aTime);
		SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss.SSS");
		String query = "insert into Telethon..tour (id_badge, date_heure_passage, lecture_badge) values (?,?,?)";
		PreparedStatement pStatement = this.cdb.getpStatement (query);
		try
		{
			pStatement.setInt (1, aBadge);
			pStatement.setString (2, sdf.format (date));
			pStatement.setBoolean (3, aLecteur);
			pStatement.addBatch ();
			pStatement.executeBatch ();
			
			if (!aDepart) 
			{
				this.miseAjourJTable ();
				this.fp.setLblbadgedernier (Integer.toString (aBadge));
				Toolkit.getDefaultToolkit ().beep ();
			}
			

		}
		catch (SQLException e)
		{
			this.fp.setInfoCourante (e.getMessage ());
			e.printStackTrace ();
		}
	}

	public void miseAjourJTable ()
	{
		this.tableClassement ();
		this.tableSuivi ();
		//this.rafraichirMeilleurtour ();
		this.fp.getDefileurClassement ().setViewportView (this.jtClassement);
		this.fp.getDefileurSuivi ().setViewportView (this.jtSuivi);
	}

	public void departCourse ()
	{
		if (this.appConf.getConfiguration ("etat").equals ("OFF"))
		{
			//this.fp.setLblMeilleurTour ("");
			String truncateTours = "TRUNCATE TABLE Telethon..tour";
			String truncateInfoTours = "TRUNCATE TABLE Telethon..info_tours";
	
			String reqSQL = "SELECT id_badge FROM Telethon..equipes order by 1";
	
			try
			{
				// on supprime toutes les lignes de chaque tables.
				this.cdb.getOrdreSql ().execute (truncateTours);
				this.cdb.getOrdreSql ().execute (truncateInfoTours);
	
				ResultSet res = this.cdb.getOrdreSql ().executeQuery (reqSQL);
				long heureDepart = System.currentTimeMillis ();
	
				// insertion de la date et heure de depart pour chaque badge
				while (res.next ())
				{
					this.insertion (res.getInt (1), heureDepart, true, true);
				}
				res.close ();
			}
	
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog (this.fp, e.getMessage (), "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
			SimpleDateFormat sdatef = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
			
			this.appConf.setConfiguration ("etat", "ON");
			this.appConf.setConfiguration ("heuredepart", sdatef.format (new java.util.Date ()) );
			this.appConf.save ("config.properties");
			
			this.fp.setLblbadgecourant ("  0  ");
			this.fp.setLblbadgedernier ("  0  ");
			this.miseAjourJTable ();
		}
	}

	public FenetrePrincipale getFp ()
	{
		return fp;
	}

	public void setFp (FenetrePrincipale fp)
	{
		this.fp = fp;
	}

	

	public ConnexionDBrec getCdb ()
	{
		return cdb;
	}

	public void setCdb (ConnexionDBrec cdb)
	{
		this.cdb = cdb;
	}

	public DefaultTableModel getTmClassement ()
	{
		return tmClassement;
	}

	public JTable getJtClassement ()
	{
		return jtClassement;
	}

	public void setJtClassement (JTable jtClassement)
	{
		this.jtClassement = jtClassement;
	}

	public JTable getJtSuivi ()
	{
		return jtSuivi;
	}

	public void setJtSuivi (JTable jtSuivi)
	{
		this.jtSuivi = jtSuivi;
	}

}
