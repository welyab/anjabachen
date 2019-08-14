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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.welyab.anjabachen.A;
import com.welyab.anjabachen.Board;
import com.welyab.anjabachen.GameConstants;
import com.welyab.anjabachen.Piece;
import com.welyab.anjabachen.Position;

public class BoardPainter {
	
	public static final int DEFAULT_SIZE = 500;
	
	private final Board board;
	
	private int size = DEFAULT_SIZE;
	
	private static final Map<Piece, String> pieceNames;
	static {
		Map<Piece, String> map = new EnumMap<>(Piece.class);
		map.put(Piece.BLACK_KING, "black_king.png");
		map.put(Piece.BLACK_QUEEN, "black_queen.png");
		map.put(Piece.BLACK_ROOK, "black_rook.png");
		map.put(Piece.BLACK_BISHOP, "black_bishop.png");
		map.put(Piece.BLACK_KNIGHT, "black_knight.png");
		map.put(Piece.BLACK_PAWN, "black_pawn.png");
		map.put(Piece.WHITE_KING, "white_king.png");
		map.put(Piece.WHITE_QUEEN, "white_queen.png");
		map.put(Piece.WHITE_ROOK, "white_rook.png");
		map.put(Piece.WHITE_BISHOP, "white_bishop.png");
		map.put(Piece.WHITE_KNIGHT, "white_knight.png");
		map.put(Piece.WHITE_PAWN, "white_pawn.png");
		pieceNames = Collections.unmodifiableMap(map);
	}
	
	public BoardPainter(String fen) {
		this.board = new Board(fen);
	}
	
	public BoardPainter(Board board) {
		this.board = board.copy();
	}
	
	public void paint(Path filePath) throws IOException {
		var image = new BufferedImage(getSize(), getSize(), BufferedImage.TYPE_INT_RGB);
		var blackSquareColor = new java.awt.Color(128, 128, 128);
		var whiteSquareColor = Color.WHITE;
		Graphics2D g = (Graphics2D) image.getGraphics();
		for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
			for (int column = 0; column < GameConstants.BOARD_SIZE; column++) {
				var squareColor = row % 2 == 0
						? column % 2 == 0 ? blackSquareColor : whiteSquareColor
						: column % 2 == 0 ? whiteSquareColor : blackSquareColor;
				g.setColor(squareColor);
				g.fill(
					new Rectangle2D.Double(
						column * getSquareSize(),
						row * getSquareSize(),
						getSquareSize(),
						getSquareSize()
					)
				);
				if (!board.isEmpty(Position.of(row, column))) {
					Image pieceImage = getPieceImage(
						board.getPiece(Position.of(row, column))
					).getScaledInstance(
						(int) getSquareSize(),
						(int) getSquareSize(),
						Image.SCALE_SMOOTH
					);
					g.drawImage(
						pieceImage,
						column * (int) getSquareSize(),
						row * (int) getSquareSize(),
						null
					);
				}
				
				Position kingPosition = board.getKingPosition(board.getActiveColor());
				List<Position> attackers = board.getAttackers(kingPosition, board.getWaitingColor());
				for (Position position : attackers) {
					g.setColor(Color.GREEN);
					g.setStroke(
						new BasicStroke(
							3,
							BasicStroke.CAP_ROUND,
							BasicStroke.JOIN_ROUND
						)
					);
					g.drawLine(
						(int) (kingPosition.getColumn() * getSquareSize() + getSquareSize() / 2),
						(int) (kingPosition.getRow() * getSquareSize() + getSquareSize() / 2),
						(int) (position.getColumn() * getSquareSize() + getSquareSize() / 2),
						(int) (position.getRow() * getSquareSize() + getSquareSize() / 2)
					);
				}
				
				if (board.hasPreviousMovement()) {
					g.setColor(Color.BLUE);
					g.setStroke(
						new BasicStroke(
							3,
							BasicStroke.CAP_ROUND,
							BasicStroke.JOIN_ROUND
						)
					);
					A a = board.get();
					g.drawLine(
						(int) (a.getOrigin().getColumn() * getSquareSize() + getSquareSize() / 2),
						(int) (a.getOrigin().getRow() * getSquareSize() + getSquareSize() / 2),
						(int) (a.getTarget().getColumn() * getSquareSize() + getSquareSize() / 2),
						(int) (a.getTarget().getRow() * getSquareSize() + getSquareSize() / 2)
					);
				}
			}
		}
		
		ImageIO.write(
			image,
			"png",
			filePath.toFile()
		);
	}
	
	private BufferedImage getPieceImage(Piece piece) throws IOException {
		var pieceName = pieceNames.get(piece);
		try (InputStream inputStream = getClass().getResourceAsStream("/ui/themes/first/" + pieceName)) {
			return ImageIO.read(inputStream);
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public double getSquareSize() {
		return (double) getSize() / GameConstants.BOARD_SIZE;
	}
}
