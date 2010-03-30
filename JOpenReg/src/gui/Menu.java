package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menu {

	public Menu(final JFrame frame) {
		// menu bar
		JMenuBar mb;
		JMenu fileMenu, helpMenu;
		JMenuItem menuItem;

		// file menu
		fileMenu = new JMenu("Datei");
		fileMenu.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuItem = new JMenuItem("Neu");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.repaint();
			}
		});
		fileMenu.add(menuItem);

//		Connect to Maple
//		menuItem = new JMenuItem("Mit Maple verbinden...");
//		menuItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				engine = new MapleEngine();
//			}
//		});
//		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Beenden");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(menuItem);

		// help menu
		helpMenu =  new JMenu("Über");
		helpMenu.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuItem = new JMenuItem("Help");
		/*
		 * TODO generate Help Menu
		 * help
		 */
		helpMenu.add(menuItem);
		menuItem = new JMenuItem("Über");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Autor: Thomas Pfister\n" + 
						"Datum: 29.01.2010",
						"Über",					      
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(menuItem);

		// add the menu items in the menu bar
		mb = new JMenuBar();
		mb.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mb.add(fileMenu);
		mb.add(helpMenu);
		frame.setJMenuBar(mb);
	}

	
}
