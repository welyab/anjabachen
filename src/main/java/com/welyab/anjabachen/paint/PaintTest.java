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
package com.welyab.anjabachen.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PaintTest extends JFrame {
	
	static boolean mouseClicked;
	
	private static Point point;
	
	private static List<PointColor> points = new ArrayList<>();
	
	private static Random r = new Random();
	
	private static List<Color> colors = Arrays.asList(
		Color.RED,
		Color.BLACK,
		Color.BLUE,
		Color.PINK,
		Color.YELLOW,
		Color.MAGENTA
	);
	
	static class PointColor {
		
		final Point p;
		
		final Color c;
		
		public PointColor(Point p, Color c) {
			this.p = p;
			this.c = c;
		}
	}
	
	public static void main(String[] args) {
		var frame = new PaintTest();
		frame.setSize(800, 600);
		frame.setContentPane(new JPanel() {
			
			@Override
			public void paint(Graphics gr) {
				super.paint(gr);
				Graphics2D g = (Graphics2D) gr;
				
				var points = new ArrayList<PointColor>();
				points.addAll(PaintTest.points);
				if (point != null) {
					points.add(new PointColor(point, Color.BLACK));
				}
				
				for (PointColor p : points) {
					g.setColor(colors.get(r.nextInt(colors.size())));
					g.draw(
						new Ellipse2D.Double(
							p.p.getX() - 3,
							p.p.getY() - 3,
							6,
							6
						)
					);
				}
				
				for (PointColor p1 : points) {
					for (PointColor p2 : points) {
						if (!p1.equals(p2)) {
							g.setColor(p2.c);
							g.draw(new Line2D.Double(p1.p, p2.p));
						}
					}
				}
			}
		});
		frame.getContentPane().addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				points.add(
					new PointColor(
						e.getPoint(),
						colors.get(r.nextInt(colors.size()))
					)
				);
				frame.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				point = e.getPoint();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				point = null;
			}
		});
		frame.getContentPane().addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				point = e.getPoint();
				frame.getContentPane().repaint();
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
