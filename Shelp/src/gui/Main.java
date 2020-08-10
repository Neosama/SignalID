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
import java.awt.BorderLayout;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tool.T_FFT;
import tool.T_SFFT;
import wavReader.Reader;
import wavsplitter.WaveSplitter;

public class Main {

	private JFrame frmSelp;

	private T_FFT t_fft;
	private T_SFFT t_sfft;

	private String pathFile = "";
	private String str_textArea = "";
	private boolean modeFFT = true;
	private boolean modeSFFT = false;
	private int top = 8;

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
		frmSelp.setResizable(false);
		frmSelp.setTitle("Shelp V1.1");
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

		JCheckBoxMenuItem chckbxmntmTop8 = new JCheckBoxMenuItem("8", true);
		mnTop.add(chckbxmntmTop8);

		JCheckBoxMenuItem chckbxmntmTop16 = new JCheckBoxMenuItem("16");
		mnTop.add(chckbxmntmTop16);

		JCheckBoxMenuItem chckbxmntmTop32 = new JCheckBoxMenuItem("32");
		mnTop.add(chckbxmntmTop32);

		JCheckBoxMenuItem chckbxmntmTop64 = new JCheckBoxMenuItem("64");
		mnTop.add(chckbxmntmTop64);

		JCheckBoxMenuItem chckbxmntmTop128 = new JCheckBoxMenuItem("128");
		mnTop.add(chckbxmntmTop128);

		JCheckBoxMenuItem chckbxmntmTop256 = new JCheckBoxMenuItem("256");
		mnTop.add(chckbxmntmTop256);

		JMenu mnDetector = new JMenu("Detector");
		menuBar.add(mnDetector);

		JMenuItem mntmD1_TONES = new JMenuItem("1-TONES");
		mnDetector.add(mntmD1_TONES);

		JMenuItem mntmD2_TONES = new JMenuItem("2-TONES");
		mnDetector.add(mntmD2_TONES);

		JMenuItem mntmD2_SANDWICH = new JMenuItem("2-SANDWICH");
		mnDetector.add(mntmD2_SANDWICH);

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

		chckbxmntmTop8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				top = 8;

				//chckbxmntmTop8.setSelected(false);
				chckbxmntmTop16.setSelected(false);
				chckbxmntmTop32.setSelected(false);
				chckbxmntmTop64.setSelected(false);
				chckbxmntmTop128.setSelected(false);
				chckbxmntmTop256.setSelected(false);
			}
		});

		chckbxmntmTop16.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				top = 16;

				chckbxmntmTop8.setSelected(false);
				//chckbxmntmTop16.setSelected(false);
				chckbxmntmTop32.setSelected(false);
				chckbxmntmTop64.setSelected(false);
				chckbxmntmTop128.setSelected(false);
				chckbxmntmTop256.setSelected(false);
			}
		});

		chckbxmntmTop32.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				top = 32;

				chckbxmntmTop8.setSelected(false);
				chckbxmntmTop16.setSelected(false);
				//chckbxmntmTop32.setSelected(false);
				chckbxmntmTop64.setSelected(false);
				chckbxmntmTop128.setSelected(false);
				chckbxmntmTop256.setSelected(false);
			}
		});

		chckbxmntmTop64.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				top = 64;

				chckbxmntmTop8.setSelected(false);
				chckbxmntmTop16.setSelected(false);
				chckbxmntmTop32.setSelected(false);
				//chckbxmntmTop64.setSelected(false);
				chckbxmntmTop128.setSelected(false);
				chckbxmntmTop256.setSelected(false);
			}
		});

		chckbxmntmTop128.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				top = 128;

				chckbxmntmTop8.setSelected(false);
				chckbxmntmTop16.setSelected(false);
				chckbxmntmTop32.setSelected(false);
				chckbxmntmTop64.setSelected(false);
				//chckbxmntmTop128.setSelected(false);
				chckbxmntmTop256.setSelected(false);
			}
		});

		chckbxmntmTop256.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				top = 256;

				chckbxmntmTop8.setSelected(false);
				chckbxmntmTop16.setSelected(false);
				chckbxmntmTop32.setSelected(false);
				chckbxmntmTop64.setSelected(false);
				chckbxmntmTop128.setSelected(false);
				//chckbxmntmTop256.setSelected(false);
			}
		});

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
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
					pathFile = chooser.getSelectedFile().getPath();

					// Check File SR, BPS, nbChannels
					try {
						Reader checkFile = new Reader(pathFile, false);

						File soundFile = new File(pathFile);
						String pathWithoutFilename = soundFile.getPath().replace(soundFile.getName(), "");

						boolean splittedFiles = false;
						if(checkFile.checkSplitFiles()) {
							new WaveSplitter(pathFile, pathWithoutFilename, 5000); // CHANGE OUT PATH
							splittedFiles = true;
						}

						if(splittedFiles) {
							File directoryFile = new File(pathWithoutFilename);

							int nbSplitFiles = 0;
							boolean showTextArea = true;
							for (File fileEntry : directoryFile.listFiles()) {
								if (!fileEntry.isDirectory()) {
									String tmpPathWav = fileEntry.getAbsolutePath();
									System.out.println(tmpPathWav);

									Reader checkFile2 = new Reader(tmpPathWav, false);

									if(nbSplitFiles < 8) {
										showTextArea = false;
										textArea.setText(str_textArea + "\nSave the file to view all content (\"File\" -> \"Save File\")");
									}

									if(checkFile2.checkFile()) {
										str_textArea += "====(" + nbSplitFiles + ")====\n";

										if(modeFFT) {
											t_fft = new T_FFT(tmpPathWav, top);
											str_textArea += t_fft.get() + "\n";
											if(showTextArea)
												textArea.setText(str_textArea);
										} else {
											t_sfft = new T_SFFT(tmpPathWav, top);
											str_textArea += t_sfft.get() + "\n";
											if(showTextArea)
												textArea.setText(t_sfft.get());
										}

										nbSplitFiles++;
									}
								}
							}
							
							// Remove tmp files
							for (File fileEntry : directoryFile.listFiles()) {
								if (!fileEntry.isDirectory()) {
									String tmpPathWav = fileEntry.getAbsolutePath();
									System.out.println(tmpPathWav);
									
									if(!fileEntry.getName().contentEquals(soundFile.getName())) {
										Files.delete(Paths.get(tmpPathWav));
									}
								}
							}
							
						} else {
							Reader checkFile2 = new Reader(pathFile, false);

							if(checkFile2.checkFile()) {
								if(modeFFT) {
									t_fft = new T_FFT(pathFile, top);
									str_textArea += t_fft.get() + "\n";
									textArea.setText(str_textArea);
								} else {
									t_sfft = new T_SFFT(pathFile, top);
									str_textArea += t_sfft.get() + "\n";
									textArea.setText(t_sfft.get());
								}
							} else
								textArea.setText("File (" + pathFile + ") not compatible!\nCheck if SAMPLERATE = 44100,\nBPS = 16,\nNumberChannels = 1,\nDuration >= 5 seconds");

						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		mntmSaveFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				JFrame parentFrame = new JFrame();

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("File to save");   

				int userSelection = fileChooser.showSaveDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();

					try {
						Files.write(Paths.get(fileToSave.getAbsolutePath()), str_textArea.getBytes());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		mntmD1_TONES.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String mode = "";
				if(modeFFT)
					mode = "FFT";
				else
					mode = "SFFT";

				if(pathFile == "") {
					textArea.setText("Please Select a File");
				} else {

					String[] parameters = {pathFile,mode,String.valueOf(top), "D1_TONES"};

					Detector detector = new Detector();
					detector.main(parameters);
				}
			}
		});

		mntmD2_TONES.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String mode = "";
				if(modeFFT)
					mode = "FFT";
				else
					mode = "SFFT";

				if(pathFile == "") {
					textArea.setText("Please Select a File");
				} else {

					String[] parameters = {pathFile,mode,String.valueOf(top), "D2_TONES"};

					Detector detector = new Detector();
					detector.main(parameters);
				}
			}
		});

		mntmD2_SANDWICH.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String mode = "";
				if(modeFFT)
					mode = "FFT";
				else
					mode = "SFFT";

				if(pathFile == "") {
					textArea.setText("Please Select a File");
				} else {

					String[] parameters = {pathFile,mode,String.valueOf(top), "D2_SANDWICH"};

					Detector detector = new Detector();
					detector.main(parameters);
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
