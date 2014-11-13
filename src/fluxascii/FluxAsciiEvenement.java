
package fluxascii;

import gui.MiseAJourGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import utilitaires.ApplicationConfiguration;

import com.sun.comm.Win32Driver;

public class FluxAsciiEvenement implements SerialPortEventListener
{
	private ApplicationConfiguration appConf = ApplicationConfiguration.getInstance ();
	private MiseAJourGUI			majGUI	= MiseAJourGUI.getInstance ();
	private CommPortIdentifier		portId;
	private SerialPort				serialPort;
	private BufferedReader			fluxLecture;
	private int						intBadge;
	private HashMap<Integer, Long>	ht		= new HashMap<> ();
	private Long					delai;
	private Timer t;

	public FluxAsciiEvenement ()
	{
		this.t = new Timer (2000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				FluxAsciiEvenement.this.majGUI.getFp().setInfoCourante ("");;
			}
		});
		Win32Driver w32Driver = new Win32Driver ();
		w32Driver.initialize ();
		// RXTXCommDriver rxtxDriver = new RXTXCommDriver ();
		// rxtxDriver.initialize ();
		try
		{
			String aDelay = this.appConf.getConfiguration ("delay");
			String aPortCOM = this.appConf.getConfiguration ("portCOM");

			this.portId = CommPortIdentifier.getPortIdentifier (aPortCOM);
			this.delai = Long.parseLong (aDelay) * 1000L;

			this.serialPort = (SerialPort) this.portId.open ("Mode Evenement", 2000);
			this.fluxLecture = new BufferedReader (new InputStreamReader (this.serialPort.getInputStream ()));
			this.serialPort.addEventListener (this);
			this.serialPort.notifyOnDataAvailable (true);
			this.serialPort.setSerialPortParams (9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			this.majGUI.getFp().setInfoCourante ("Port COM en liaison");
		}
		catch (NoSuchPortException e)
		{
			JOptionPane.showMessageDialog (null, e.getMessage (), "Erreur port COM", JOptionPane.ERROR_MESSAGE);
			this.majGUI.getFp().setInfoCourante (e.getMessage ());
		}

		catch (IOException e)
		{
			this.majGUI.getFp().setInfoCourante (e.getMessage ());
		}
		catch (PortInUseException e)
		{
			JOptionPane.showMessageDialog (null, e.getMessage (), "Erreur port COM", JOptionPane.ERROR_MESSAGE);
			this.majGUI.getFp().setInfoCourante (e.getMessage ());
		}
		catch (TooManyListenersException e)
		{
			this.majGUI.getFp().setInfoCourante (e.getMessage ());
			return;
		}
		catch (UnsupportedCommOperationException e)
		{
			JOptionPane.showMessageDialog (null, e.getMessage (), "Erreur port COM", JOptionPane.ERROR_MESSAGE);
		}
		
		

		int i;
		for (i = 1; i < 21; i ++ )
		{
			this.ht.put (i, 0L);
		}		
	}

	public void serialEvent (SerialPortEvent event)
	{
		switch (event.getEventType ())
		{
			case SerialPortEvent.BI :
			case SerialPortEvent.OE :
			case SerialPortEvent.FE :
			case SerialPortEvent.PE :
			case SerialPortEvent.CD :
			case SerialPortEvent.CTS :
			case SerialPortEvent.DSR :
			case SerialPortEvent.RI :
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY :
				break;
			case SerialPortEvent.DATA_AVAILABLE :
				try
				{
					String badge = (String) fluxLecture.readLine ().substring (8);
					this.intBadge = Integer.parseInt (badge);
					this.majGUI.getFp().setInfoCourante ("Passage badge numéro " + badge + " .....");
					this.t.start ();
					this.majGUI.getFp().setLblbadgecourant (badge);
					Long timer = System.currentTimeMillis ();
					for (Entry<Integer, Long> entree : ht.entrySet ())
					{
						if (entree.getKey () == this.intBadge)
						{
							if ( (System.currentTimeMillis () - entree.getValue ()) > this.delai)
							{
								ht.put (this.intBadge, timer);
								this.majGUI.insertion (entree.getKey (), timer, true, false);
							}
						}
					}
				}
				catch (NumberFormatException aNFE)
				{
					this.majGUI.getFp().setInfoCourante ("Erreur lecture Badge");
					return;
				}
				catch (IOException e)
				{
				}
				break;
		}
	}

	public int getIntBadge ()
	{
		return intBadge;
	}

	public void setIntBadge (int aIntBadge)
	{
		this.intBadge = aIntBadge;
	}

	public SerialPort getSerialPort ()
	{
		return serialPort;
	}

	public void setSerialPort (SerialPort serialPort)
	{
		this.serialPort = serialPort;
	}

	public BufferedReader getFluxLecture ()
	{
		return fluxLecture;
	}

	public void setFluxLecture (BufferedReader fluxLecture)
	{
		this.fluxLecture = fluxLecture;
	}
}
