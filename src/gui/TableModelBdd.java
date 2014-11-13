package gui;

import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;


@SuppressWarnings("serial")
public class TableModelBdd extends DefaultTableModel
{
	private ResultSet			resultat;
	private ResultSetMetaData	description;
	Vector<Vector<String>> dataVector = new Vector<Vector<String>> ();

	public TableModelBdd (ResultSet aResultat) throws SQLException
	{
		this.resultat = aResultat;
		this.description = this.resultat.getMetaData ();
	}

	public int getRowCount ()
	{
		try
		{
			this.resultat.last ();
			int taille = this.resultat.getRow ();
			this.resultat.beforeFirst ();
			return taille;
		}
		catch (SQLException aSQLE)
		{
			return 0;
		}
	}

	public int getColumnCount ()
	{
		try
		{
			return this.description.getColumnCount ();
		}
		catch (SQLException aSQLE)
		{
			return 0;
		}
	}

	public Object getValueAt (int aLigne, int aColonnes)
	{
		try
		{
			this.resultat.absolute (aLigne + 1);
			return this.resultat.getObject (aColonnes + 1);
		}
		catch (SQLException aSQLE)
		{
			return null;
		}
	}

	public String getColumnName (int aColonne)
	{
		try
		{
			return this.description.getColumnName (aColonne + 1);
		}
		catch (SQLException aSQLE)
		{
			return null;
		}
	}

	public boolean isCellEditable (int aLigne, int aColonne)
	{
		return true;
	}

	public void setValueAt (Object aValeur, int aLigne, int aColonne)
	{
		try
		{
			this.resultat.absolute (aLigne + 1);
			this.resultat.updateObject (aColonne + 1, aValeur);
			this.resultat.updateRow ();
			this.resultat.beforeFirst ();
		}

		catch (SQLException aSQLE)
		{
			System.out.println (aSQLE.getMessage ());
		}
	}

	public void addRow (Vector aVecteurLigne)
	{
		//dataVector.add(0, aVecteurLigne);
        //fireTableRowsInserted(0, 0);
		this.insertRow (getRowCount ()-1, aVecteurLigne);		
		
	}

	public void insertRow (int aLigne, Vector rowData)
	{
		System.out.println ("avant setsize " + dataVector.size ());
		this.justifyRows (aLigne-1, aLigne-1);
		//this.dataVector.insertElementAt(rowData, aLigne+1 );
		//dataVector.setSize (getRowCount ());
		dataVector.add(aLigne, rowData);
		System.out.println(this.dataVector);
		System.out.println ("apres setsize " + dataVector.size ());
		fireTableRowsInserted (aLigne+1, aLigne+1);
	
	}

	private void justifyRows (int from, int to)
	{
		dataVector.setSize (getRowCount ());

		for (int i = from; i < to; i ++ )
		{
			if (dataVector.elementAt (i) == null)
			{
				dataVector.setElementAt (new Vector (), i);
			}
			((Vector) dataVector.elementAt (i)).setSize (getColumnCount ());
		}
	}
}

