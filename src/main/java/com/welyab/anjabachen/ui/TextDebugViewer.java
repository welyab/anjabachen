/*
 * Copyright (C) 2019 Welyab da Silva Paula
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.welyab.anjabachen.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TextDebugViewer extends JPanel {
	
	private static final String NEWLINE = String.format("\n");
	
	private JTextArea textArea;
	
	public TextDebugViewer() {
		setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setFont(Font.decode("monospaced"));
		textArea.setEditable(false);
		TextLineNumber textLineNumber = new TextLineNumber(textArea);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setRowHeaderView(textLineNumber);
		add(scroll, BorderLayout.CENTER);
		
		JPanel commands = new JPanel();
		commands.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton clearButton = new JButton();
		clearButton.setText("Clear");
		clearButton.setRequestFocusEnabled(false);
		clearButton.addActionListener(e -> textArea.setText(""));
		commands.add(clearButton);
		add(commands, BorderLayout.PAGE_END);
	}
	
	public void addTextLog(String format, Object... args) {
		addTextLog(String.format(format, args));
	}
	
	public void addTextLog(String text) {
		Document document = textArea.getDocument();
		try {
			if (document.getLength() > 0) {
				document.insertString(document.getLength(), NEWLINE, null);
			}
			document.insertString(document.getLength(), text, null);
		} catch (BadLocationException e) {
			throw new UiException("Fail to insert text in debug viewer", e);
		}
	}
	
	public static TextDebugViwerFrame createFrame() {
		return new TextDebugViwerFrame();
	}
	
	public static class TextDebugViwerFrame extends JFrame {
		
		private TextDebugViewer textDebugViewer;
		
		public TextDebugViwerFrame() {
			setSize(400, 300);
			setTitle("Text debug viwer - AN.JA.BA.CH.EN");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			textDebugViewer = new TextDebugViewer();
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(textDebugViewer, BorderLayout.CENTER);
		}
		
		public void addTextLog(String text) {
			textDebugViewer.addTextLog(text);
		}
		
		public void addTextLog(String format, Object... args) {
			textDebugViewer.addTextLog(String.format(format, args));
		}
	}
	
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		TextDebugViwerFrame frame = createFrame();
		frame.addTextLog(UUID.randomUUID().toString());
		frame.addTextLog(UUID.randomUUID().toString());
		frame.addTextLog(UUID.randomUUID().toString());
		frame.addTextLog(UUID.randomUUID().toString());
		frame.setVisible(true);
	}
}
