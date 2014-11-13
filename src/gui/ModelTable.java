
package gui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ModelTable extends DefaultTableModel
{
	
	
	public ModelTable ()
	{
		super ();
		// TODO Auto-generated constructor stub
	}

	public ModelTable (int rowCount, int columnCount)
	{
		super (rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public ModelTable (Object [] columnNames, int rowCount)
	{
		super (columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public ModelTable (Object [][] data, Object [] columnNames)
	{
		super (data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public ModelTable (Vector<?> columnNames, int rowCount)
	{
		super (columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public ModelTable (Vector<?> data, Vector<?> columnNames)
	{
		super (data, columnNames);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<?> getColumnClass (int columnIndex)
	{
		if (getValueAt (0, columnIndex) != null)
		{
			if (columnIndex == 1)
			{
				//return ImageIcon.class;
			}
			
			return getValueAt (0, columnIndex).getClass ();
		}
		
		else
		{
			return Object.class;
		}
	}

	@Override
	public boolean isCellEditable (int row, int column)
	{
		return false;
	}
}
