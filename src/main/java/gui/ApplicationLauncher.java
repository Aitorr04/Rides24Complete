package gui;

import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businesslogic.BusinessLogicFactory;
import businesslogic.ExtendedIterator;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import businesslogic.BLFacade;
import businesslogic.BLFacadeImplementation;

public class ApplicationLauncher {

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: " + Locale.getDefault());

		try {

			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			appFacadeInterface = BusinessLogicFactory.createBusinessLogic(c.isBusinessLogicLocal());

			MainGUI.setBussinessLogic(appFacadeInterface);
			MainGUI a = new MainGUI();
			a.setVisible(true);

			//Iterator
			ExtendedIterator<String> i = appFacadeInterface.getDepartingCitiesIterator();
			String city;
			System.out.println("_____________________");
			System.out.println("FROM	LAST	TO	FIRST");
			i.goLast();	//	Go	to	last	element
			while (i.hasPrevious())	{
				city =	i.previous();
				System.out.println(city);
			}
			System.out.println();
			System.out.println("_____________________");
			System.out.println("FROM	FIRST	TO	LAST");
			i.goFirst();	//	Go	to	first	element
			while (i.hasNext())	{
				city =	i.next();
				System.out.println(city);
			}

		} catch (Exception e) {
			// a.jLabelSelectOption.setText("Error: "+e.toString());
			// a.jLabelSelectOption.setForeground(Color.RED);

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		// a.pack();
	}

}
