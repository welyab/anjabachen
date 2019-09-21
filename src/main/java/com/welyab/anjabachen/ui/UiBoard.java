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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.BoardUtils;
import com.welyab.anjabachen.movement.Position;
import com.welyab.anjabachen.ui.theme.Theme;

public class UiBoard extends JPanel {
	
	private MouseState mouseState;
	
	private Theme theme;
	
	private Board board;
	
	public UiBoard() {
		theme = new Theme();
		board = new Board();
		mouseState = new MouseState();
		addMouseListener(new MouseListenerImpl());
		addMouseMotionListener(new MouseListenerImpl());
		addComponentListener(new ComponentListenerImpl());
	}
	
	@Override
	public void paint(Graphics g) {
		paintBoard((Graphics2D) g);
		paintPieces((Graphics2D) g);
		paintMouseDebugs((Graphics2D) g);
	}
	
	private void paintMouseDebugs(Graphics2D g) {
		if (!mouseState.isOverComponent()) {
			return;
		}
		
		Point2D squareOrigin = getSquareOriginRealtiveTo(mouseState.getX(), mouseState.getY());
		Dimension2D squareDimension = getSquareDimension();
		g.setColor(Color.BLACK);
		g.draw(
			new Rectangle2D.Double(
				squareOrigin.getX(), squareOrigin.getY(),
				squareDimension.getWidth(), squareDimension.getHeight()
			)
		);
		g.draw(new Line2D.Double(squareOrigin, mouseState));
		g.draw(
			new Line2D.Double(
				mouseState,
				new Point2D.Double(
					squareOrigin.getX() + squareDimension.getWidth(),
					squareOrigin.getY() + squareDimension.getHeight()
				)
			)
		);
		g.draw(
			new Line2D.Double(
				mouseState,
				new Point2D.Double(
					squareOrigin.getX(),
					squareOrigin.getY() + squareDimension.getHeight()
				)
			)
		);
		g.draw(
			new Line2D.Double(
				mouseState,
				new Point2D.Double(
					squareOrigin.getX() + squareDimension.getWidth(),
					squareOrigin.getY()
				)
			)
		);
	}
	
	private void paintPieces(Graphics2D g) {
		paintNonHoldedPieces(g);
		paintHoldedPiece(g);
	}
	
	private void paintNonHoldedPieces(Graphics2D g) {
		Position holdedPieceSquare = null;
		if (mouseState.isPressed()) {
			holdedPieceSquare = getSquarePositionRelativeTo(mouseState.getPressedLocation());
		}
		Dimension2D squareDimension = getSquareDimension();
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Position position = Position.of(row, column);
				if (!board.isEmpty(position) && !position.equals(holdedPieceSquare)) {
					Point2D squareOrigin = getSquareOrigin(row, column);
					Image image = theme.getPiece(
						board.getPiece(position),
						squareDimension.getWidth(),
						squareDimension.getHeight()
					);
					AffineTransform affineTransform = new AffineTransform();
					affineTransform.translate(squareOrigin.getX(), squareOrigin.getY());
					g.drawImage(image, affineTransform, this);
				}
			}
		}
	}
	
	private void paintHoldedPiece(Graphics2D g) {
		if (mouseState.isReleased()) {
			return;
		}
		Position holdedPieceSquare = getSquarePositionRelativeTo(mouseState.getPressedLocation());
		if (board.isEmpty(holdedPieceSquare)) {
			return;
		}
		
		Dimension2D squareDimension = getSquareDimension();
		Image image = theme.getPiece(
			board.getPiece(holdedPieceSquare),
			squareDimension.getWidth(),
			squareDimension.getHeight()
		);
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(
			mouseState.getX() - squareDimension.getWidth() / 2,
			mouseState.getY() - squareDimension.getHeight() / 2
		);
		g.drawImage(image, affineTransform, this);
	}
	
	private void paintBoard(Graphics2D g) {
		Dimension2D squareDimension = getSquareDimension();
		Image whiteSquareBackground = theme.getWhiteSquareBackground(
			squareDimension.getWidth(),
			squareDimension.getHeight()
		);
		Image blackSquareBackground = theme.getBlackSquareBackground(
			squareDimension.getWidth(),
			squareDimension.getHeight()
		);
		
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Image background = row % 2 == 0
						? column % 2 == 0 ? whiteSquareBackground : blackSquareBackground
						: column % 2 == 0 ? blackSquareBackground : whiteSquareBackground;
				
				Point2D squareOrigin = getSquareOrigin(row, column);
				AffineTransform transform = new AffineTransform();
				transform.translate(squareOrigin.getX(), squareOrigin.getY());
				transform.scale(
					Double.max(background.getWidth(null), squareDimension.getWidth()) / Double.min(background.getWidth(null), squareDimension.getWidth()),
					Double.max(background.getHeight(null), squareDimension.getHeight()) / Double.min(background.getHeight(null), squareDimension.getHeight())
				);
				g.drawImage(background, transform, this);
			}
		}
	}
	
	private Point2D getSquareCenterRelativeTo(Point point) {
		Dimension2D squareDimension = getSquareDimension();
		Point2D squareOrigin = getSquareOriginRealtiveTo(point.getX(), point.getY());
		return new Point2D.Double(
			squareOrigin.getX() + squareDimension.getWidth() / 2,
			squareOrigin.getY() + squareDimension.getHeight() / 2
		);
	}
	
	private Point2D getSquareOrigin(int row, int column) {
		Dimension2D squareDimension = getSquareDimension();
		double x = column * squareDimension.getWidth();
		double y = row * squareDimension.getHeight();
		return new Point2D.Double(x, y);
	}
	
	private Point2D getSquareOriginRealtiveTo(double x, double y) {
		Position position = getSquarePositionRelativeTo(x, y);
		return getSquareOrigin(position.getRow(), position.getColumn());
	}
	
	private Position getSquarePositionRelativeTo(Point p) {
		return getSquarePositionRelativeTo(p.getX(), p.getY());
	}
	
	private Position getSquarePositionRelativeTo(double x, double y) {
		Dimension2D squareDimension = getSquareDimension();
		return Position.of(
			(int) (y / squareDimension.getHeight()),
			(int) (x / squareDimension.getWidth())
		);
	}
	
	private void dropPiece(Position origin, Position target) {
		if (board.isEmpty(origin)) {
			return;
		}
		board.move(origin, target);
	}
	
	private class ComponentListenerImpl extends ComponentAdapter {
		
		@Override
		public void componentResized(ComponentEvent e) {
			theme.clearCache();
		}
	}
	
	private class MouseListenerImpl extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			mouseState.setPressed(true);
			mouseState.setReleased(false);
			mouseState.setOverComponent(true);
			mouseState.setLocation(e.getPoint());
			mouseState.setPressedLocation(e.getPoint());
			repaint();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			mouseState.setPressed(false);
			mouseState.setReleased(true);
			mouseState.setOverComponent(true);
			mouseState.setLocation(e.getPoint());
			mouseState.setReleasedLocation(e.getPoint());
			
			Position origin = getSquarePositionRelativeTo(mouseState.getPressedLocation());
			Position target = getSquarePositionRelativeTo(mouseState.getReleasedLocation());
			dropPiece(origin, target);
			repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseState.setOverComponent(true);
			mouseState.setLocation(e.getPoint());
			repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			mouseState.setOverComponent(true);
			mouseState.setLocation(e.getPoint());
			repaint();
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			mouseState.setOverComponent(true);
			mouseState.setLocation(e.getPoint());
			repaint();
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			mouseState.setOverComponent(false);
			mouseState.setLocation(e.getPoint());
			repaint();
		}
	}
	
	private Dimension2D getSquareDimension() {
		Dimension boardSize = getSize();
		return new DimentionImpl(
			boardSize.getWidth() / BoardUtils.BOARD_SIZE,
			boardSize.getHeight() / BoardUtils.BOARD_SIZE
		);
	}
	
	private class DimentionImpl extends Dimension2D {
		
		private double width;
		
		private double height;
		
		public DimentionImpl() {
		}
		
		public DimentionImpl(double width, double height) {
			this.width = width;
			this.height = height;
		}
		
		@Override
		public double getWidth() {
			return width;
		}
		
		@Override
		public double getHeight() {
			return height;
		}
		
		@Override
		public void setSize(double width, double height) {
			this.width = width;
			this.height = height;
		}
	}
	
	public static void main(String args[])
			throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame();
		frame.setTitle("Board viwer - AN.JA.BA.CH.EN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		UiBoard uiBoard = new UiBoard();
		frame.getContentPane().add(uiBoard);
		SwingUtilities.invokeLater(() -> frame.setVisible(true));
	}
}
