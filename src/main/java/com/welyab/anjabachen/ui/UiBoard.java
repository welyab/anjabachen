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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class UiBoard extends JPanel {
	
	private Point mousePosition;
	
	@Override
	public void paint(Graphics gg) {
		if (mousePosition != null) {
			Graphics2D g = (Graphics2D) gg;
			g.draw(
				new Ellipse2D.Double(
					mousePosition.getX() - 10,
					mousePosition.getY() - 10,
					20,
					20
				)
			);
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		UiBoard uiBoard = new UiBoard();
		uiBoard.addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				uiBoard.mousePosition = e.getPoint();
				uiBoard.repaint();
			}
		});
		frame.getContentPane().add(uiBoard);
		SwingUtilities.invokeLater(() -> frame.setVisible(true));
	}
}
