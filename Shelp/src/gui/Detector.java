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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import detector.D1_TONES;
import detector.D2_SANDWICH;
import detector.D2_TONES;
import tool.T_FFT;
import tool.T_SFFT;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

public class Detector {

	private JFrame frmDetector;

	private static String pathFile = "";
	private static String strMode = "";
	private static String rawTop = "";
	private static String typeDetector = "";

	private JTextField textFieldFq1;
	private JTextField textFieldMargin;
	private JTextField textFieldFq2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if(args.length < 1)
			return;

		// String[] parameters = {pathFile,mode,String.valueOf(top), "D2_SANDWICH"};
		pathFile = args[0];
		strMode = args[1];
		rawTop = args[2];
		typeDetector = args[3];

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detector window = new Detector();
					window.frmDetector.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Detector() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDetector = new JFrame();
		frmDetector.setResizable(false);
		frmDetector.setTitle("Shelp V1.1 - Detector (" + typeDetector + ")");
		frmDetector.setBounds(100, 100, 342, 304);
		frmDetector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textFieldFq1 = new JTextField();
		textFieldFq1.setText("Fq1");
		textFieldFq1.setColumns(10);

		textFieldMargin = new JTextField();
		textFieldMargin.setText("Margin");
		textFieldMargin.setColumns(10);

		textFieldFq2 = new JTextField();
		textFieldFq2.setText("Fq2");
		textFieldFq2.setColumns(10);

		if(typeDetector.contentEquals("D1_TONES")) {
			textFieldFq2.setVisible(false);
			textFieldFq2.setText("1");
		}

		JButton btnCheck = new JButton("Check");

		JTextArea textAreaResult = new JTextArea();
		textAreaResult.setText("Set Values");
		GroupLayout groupLayout = new GroupLayout(frmDetector.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(textFieldFq1, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
						.addGap(200)
						.addComponent(textFieldMargin, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(64)
						.addComponent(btnCheck, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
						.addGap(63))
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(textFieldFq2, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
						.addGap(263))
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(textAreaResult, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textFieldMargin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldFq1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textFieldFq2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textAreaResult, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
						.addGap(18)
						.addComponent(btnCheck)
						.addContainerGap())
				);
		frmDetector.getContentPane().setLayout(groupLayout);

		btnCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Check values textFields
				String rawTfFq1 = textFieldFq1.getText();
				String rawTfFq2 = textFieldFq2.getText();
				String rawTfMargin = textFieldMargin.getText();

				int fq1 = 0;
				int fq2 = 0;
				int margin = 0;
				int top = 0;
				try {
					fq1 = Integer.valueOf(rawTfFq1);
					fq2 = Integer.valueOf(rawTfFq2);
					margin = Integer.valueOf(rawTfMargin);
					textAreaResult.setText("fq1 = " + fq1 + " fq2 = " + fq2 + " margin = " + margin);

					top = Integer.valueOf(rawTop);
					
					// FFT or SFFT
					if(strMode.contentEquals("FFT")) {
						T_FFT t_fft = new T_FFT(pathFile, top);

						if(typeDetector.contentEquals("D1_TONES")) {
							D1_TONES d1_tones = new D1_TONES(fq1, margin, top, 44100, 65536, t_fft.getRaw());
							String tmp = "D1_TONES = " + d1_tones.check() + "\n\n";
							tmp += "With Parameters : \nFrequencie 1 = " + fq1 + "\nTop = " + top;
							textAreaResult.setText(tmp);
						}

						if(typeDetector.contentEquals("D2_TONES")) {
							D2_TONES d2_tones = new D2_TONES(fq1, fq2, margin, top, 44100, 65536, t_fft.getRaw());
							String tmp = "D2_TONES = " + d2_tones.check() + "\n\n";
							tmp += "With Parameters : \nFrequencie 1 = " + fq1 + "\nFrequencie 2 = " + fq2 + "\nTop = " + top;
							textAreaResult.setText(tmp);
						}

						if(typeDetector.contentEquals("D2_SANDWICH")) {
							D2_SANDWICH d2_sandwich = new D2_SANDWICH(fq1, fq2, margin, top, 44100, 65536, t_fft.getRaw());
							String tmp = "D2_SANDWICH = " + d2_sandwich.getScore() + "\n\n";
							tmp += "With Parameters : \nFrequencie 1 = " + fq1 + "\nFrequencie 2 = " + fq2 + "\nTop = " + top;
							textAreaResult.setText(tmp);
						}

					} else {
						T_SFFT t_sfft = new T_SFFT(pathFile, top);

						if(typeDetector.contentEquals("D1_TONES")) {
							D1_TONES d1_tones = new D1_TONES(fq1, margin, t_sfft.get());
							String tmp = "D1_TONES = " + d1_tones.check() + " (scoreSFFT = " + d1_tones.getScoreSfft() + ")\n\n";
							tmp += "With Parameters : \nFrequencie 1 = " + fq1 + "\nTop = " + top;
							textAreaResult.setText(tmp);
						}

						if(typeDetector.contentEquals("D2_TONES")) {
							D2_TONES d2_tones = new D2_TONES(fq1, fq2, margin, t_sfft.get());
							String tmp = "D2_TONES = " + d2_tones.check() + " (scoreSFFT = " + d2_tones.getScoreSfft() + ")\n\n";
							tmp += "With Parameters : \nFrequencie 1 = " + fq1 + "\nFrequencie 2 = " + fq2 + "\nTop = " + top;
							textAreaResult.setText(tmp);
						}

						if(typeDetector.contentEquals("D2_SANDWICH")) {
							D2_SANDWICH d2_sandwich = new D2_SANDWICH(fq1, fq2, margin, t_sfft.get());
							String tmp = "D2_SANDWICH = " + d2_sandwich.getScore() + "\n\n";
							tmp += "With Parameters : \nFrequencie 1 = " + fq1 + "\nFrequencie 2 = " + fq2 + "\nTop = " + top;
							textAreaResult.setText(tmp);
						}
					}

				} catch(Exception e2) {
					e2.printStackTrace();
					textAreaResult.setText("Set Only Numbers !");
				}
			}
		});

	}
}
