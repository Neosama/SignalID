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
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.tortillum.salome.recognizer.Recognizer;
import com.tortillum.salome.wavsplitter.WavFileException;

public class Main {

	private JFrame frmStationid;
	
	private String pathFile = "";
	
	Recognizer recognizer;
	
	JTextArea textAreaInfo;
	JTextArea textAreaInfo2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmStationid.setVisible(true);
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
		frmStationid = new JFrame();
		frmStationid.setResizable(false);
		frmStationid.setTitle("SignalID");
		frmStationid.setBounds(100, 100, 612, 290);
		frmStationid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStationid.getContentPane().setLayout(null);
		
		JButton btnMain = new JButton("New button");
		btnMain.setBounds(484, 11, 112, 23);
		frmStationid.getContentPane().add(btnMain);
		
		textAreaInfo = new JTextArea();
		textAreaInfo.setLineWrap(true);
		textAreaInfo.setEditable(false);
		textAreaInfo.setBounds(0, 45, 596, 176);
		frmStationid.getContentPane().add(textAreaInfo);
		
		// Here
		btnMain.setText("Select a file");
		
		textAreaInfo2 = new JTextArea();
		textAreaInfo2.setEditable(false);
		textAreaInfo2.setBounds(0, 226, 596, 22);
		frmStationid.getContentPane().add(textAreaInfo2);
		
		btnMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					pathFile = chooser.getSelectedFile().getPath();
					
					textAreaInfo2.setText(pathFile);
					
					textAreaInfo.setText("Loading....");
					RecognizerThread tRecognizer = new RecognizerThread();
					tRecognizer.start();
				}
			}
		});
	}
	
	public class RecognizerThread extends Thread {
		  public RecognizerThread(){
		    super();
		  }
		  public void run(){
			  try {
				recognizer = new Recognizer(pathFile, false);
				textAreaInfo.setText(recognizer.getResult());
			} catch (IOException | WavFileException e) {
				e.printStackTrace();
			}
		  }
		}
}
