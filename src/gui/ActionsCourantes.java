
package gui;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class ActionsCourantes extends AbstractAction
{
	private Traitements			traitements	= Traitements.getInstance ();
	private BarreDeMenu			barreDesMenus;
	//private FenetrePrincipale	fenetrePrincipale;

	public ActionsCourantes (String aNom, String aDescription, String aActionCommande, String aTexteCourt,
			KeyStroke aAccelerator, int aMnemonique, String aImageSmall, String aImageLarge, BarreDeMenu aBarre)
	{
		super (aNom);
		this.putValue (Action.LONG_DESCRIPTION, aDescription);
		this.putValue (Action.ACTION_COMMAND_KEY, aActionCommande);
		this.putValue (Action.SHORT_DESCRIPTION, aTexteCourt);
		this.putValue (Action.ACCELERATOR_KEY, aAccelerator);
		this.putValue (Action.MNEMONIC_KEY, aMnemonique);
		this.setBarreDesMenus (aBarre);
		
		String imgLocation = "/images/" + aImageSmall;
		URL imageURL = this.getClass ().getResource (imgLocation);
		ImageIcon icone = new ImageIcon (imageURL, aNom);
		this.putValue (Action.SMALL_ICON, icone);

		imgLocation = "/images/" + aImageLarge;
		imageURL = this.getClass ().getResource (imgLocation);
		icone = new ImageIcon (imageURL, aNom);
		this.putValue (Action.LARGE_ICON_KEY, icone);

	}

	public void actionPerformed (ActionEvent aAction)
	{
		String cause = aAction.getActionCommand ();
		if ("quitter".equals (cause))
		{
			this.traitements.quitter ();
			return;
		}
		if ("depart".equals (cause))
		{
			this.traitements.depart ();
			return;
		}
		if ("arret".equals (cause))
		{
			this.traitements.arret ();
			return;
		}
		if ("actualise".equals (cause))
		{
			this.traitements.actualise ();
			return;
		}
		if ("editer".equals (cause))
		{
			this.traitements.editer ();
			return;
		}
		if ("manu".equals (cause))
		{
			this.traitements.manu ();
			return;
		}
		if ("supprimer".equals (cause))
		{
			this.traitements.supprimer ();
			return;
		}
		if ("paramCourse".equals (cause))
		{
			this.traitements.paramCourse ();
			return;
		}
		if ("enregistrer".equals (cause))
		{
			this.traitements.enregistrer ();
			return;
		}
	}

	public BarreDeMenu getBarreDesMenus ()
	{
		return barreDesMenus;
	}

	public void setBarreDesMenus (BarreDeMenu barreDesMenus)
	{
		this.barreDesMenus = barreDesMenus;
	}
}
