/*
 *  Copyright (C) 2020 Tortillum / Neosama
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import tool.T_FFT;
import tool.T_SFFT;

import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Main {

	private JFrame frmSelp;

	private T_FFT t_fft;
	private T_SFFT t_sfft;

	private boolean modeFFT = true;
	private boolean modeSFFT = false;
	private JTextField textNbTop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmSelp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSelp = new JFrame();
		frmSelp.setTitle("Shelp");
		frmSelp.setBounds(100, 100, 450, 300);
		frmSelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmSelp.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpenFile = new JMenuItem("Open File");
		mnFile.add(mntmOpenFile);

		JMenuItem mntmSaveFile = new JMenuItem("Save File");
		mnFile.add(mntmSaveFile);

		JMenu mnFourier = new JMenu("Fourier");
		menuBar.add(mnFourier);

		JCheckBoxMenuItem chckbxmntmFFT = new JCheckBoxMenuItem("FFT", true);
		mnFourier.add(chckbxmntmFFT);

		JCheckBoxMenuItem chckbxmntmSFFT = new JCheckBoxMenuItem("SFFT");
		mnFourier.add(chckbxmntmSFFT);

		JMenu mnTop = new JMenu("Top");
		menuBar.add(mnTop);

		textNbTop = new JTextField();
		textNbTop.setText("16");
		mnTop.add(textNbTop);
		textNbTop.setColumns(10);

		chckbxmntmFFT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modeFFT = chckbxmntmFFT.getState();
				modeSFFT = !modeFFT;
				chckbxmntmSFFT.setSelected(modeSFFT);
			}
		});

		chckbxmntmSFFT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modeSFFT = chckbxmntmSFFT.getState();
				modeFFT = !modeSFFT;
				chckbxmntmFFT.setSelected(modeFFT);
			}
		});

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		JScrollPane scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frmSelp.getContentPane().add(scroll, BorderLayout.CENTER);

		// Listeners
		mntmOpenFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					int top = Integer.getInteger(textNbTop.getText());

					try {
						if(modeFFT) {
							t_fft = new T_FFT(chooser.getSelectedFile().getPath(), top);
							textArea.setText(t_fft.get());
						} else {
							t_sfft = new T_SFFT(chooser.getSelectedFile().getPath(), top);
							textArea.setText(t_sfft.get());
						}

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		mntmSaveFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				String content = textArea.getText();

				JFrame parentFrame = new JFrame();

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("File to save");   

				int userSelection = fileChooser.showSaveDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();

					try {
						Files.write( Paths.get(fileToSave.getAbsolutePath()), content.getBytes());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
