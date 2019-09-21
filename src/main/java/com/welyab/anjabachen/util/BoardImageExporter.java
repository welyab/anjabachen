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
package com.welyab.anjabachen.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.welyab.anjabachen.movement.Board;
import com.welyab.anjabachen.movement.BoardUtils;
import com.welyab.anjabachen.movement.Movement;
import com.welyab.anjabachen.movement.Piece;
import com.welyab.anjabachen.movement.Position;

public class BoardImageExporter {
	
	/** Generated image default dimension size in pixels. */
	public static final int DEFAULT_IMAGE_SIZE = 800;
	
	private static final Map<Piece, String> pieceIcons;
	
	static {
		EnumMap<Piece, String> map = new EnumMap<>(Piece.class);
		map.put(Piece.BLACK_KING, "/ui/themes/first/black_king.png");
		map.put(Piece.BLACK_QUEEN, "/ui/themes/first/black_queen.png");
		map.put(Piece.BLACK_ROOK, "/ui/themes/first/black_rook.png");
		map.put(Piece.BLACK_BISHOP, "/ui/themes/first/black_bishop.png");
		map.put(Piece.BLACK_KNIGHT, "/ui/themes/first/black_knight.png");
		map.put(Piece.BLACK_PAWN, "/ui/themes/first/black_pawn.png");
		map.put(Piece.WHITE_KING, "/ui/themes/first/white_king.png");
		map.put(Piece.WHITE_QUEEN, "/ui/themes/first/white_queen.png");
		map.put(Piece.WHITE_ROOK, "/ui/themes/first/white_rook.png");
		map.put(Piece.WHITE_BISHOP, "/ui/themes/first/white_bishop.png");
		map.put(Piece.WHITE_KNIGHT, "/ui/themes/first/white_knight.png");
		map.put(Piece.WHITE_PAWN, "/ui/themes/first/white_pawn.png");
		pieceIcons = Collections.unmodifiableMap(map);
	}
	
	/** The board to create the image. */
	private final Board board;
	
	private int imageSize;
	
	public BoardImageExporter(String fen) {
		this(new Board(fen));
		this.imageSize = DEFAULT_IMAGE_SIZE;
	}
	
	public BoardImageExporter(Board board) {
		this.board = board.copy();
		this.imageSize = DEFAULT_IMAGE_SIZE;
	}
	
	public static void main(String[] args) {
		Board b = new Board();
		System.out.println(b.toString(true));
		b.move(Position.of(6, 4), Position.of(4, 4));
		BoardImageExporter exporter = new BoardImageExporter(b);
		exporter.export(
			Paths.get("C:\\Users\\welyab\\Desktop\\saida123").resolve(UUID.randomUUID().toString() + ".png")
		);
	}
	
	public void export(Path outfile) {
		BufferedImage bufferedImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
		double squareDimension = imageSize / (double) BoardUtils.BOARD_SIZE;
		for (int row = 0; row < BoardUtils.BOARD_SIZE; row++) {
			for (int column = 0; column < BoardUtils.BOARD_SIZE; column++) {
				Rectangle2D rectangle = new Rectangle2D.Double(
					column * squareDimension,
					row * squareDimension,
					squareDimension,
					squareDimension
				);
				g.setColor(getSquareColor(row, column));
				g.fill(rectangle);
				Position position = Position.of(row, column);
				if (board.isNotEmpty(position)) {
					Image pieceImage = loadPieceImage(board.getPiece(Position.of(row, column)), squareDimension);
					AffineTransform affineTransform = new AffineTransform();
					affineTransform.translate(
						column * squareDimension,
						row * squareDimension
					);
					affineTransform.scale(
						Double.max(squareDimension, pieceImage.getWidth(null)) / Double.min(squareDimension, pieceImage.getWidth(null)),
						Double.max(squareDimension, pieceImage.getHeight(null)) / Double.min(squareDimension, pieceImage.getHeight(null))
					);
					g.drawImage(pieceImage, affineTransform, null);
				}
			}
		}
		
		Movement lastMovement = board.getLastMovement();
		g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(Color.RED);
		java.awt.geom.Line2D.Double lastMovementLine = new Line2D.Double(
			new Point2D.Double(
				lastMovement.getOrigin().getPosition().getColumn() * squareDimension + squareDimension / 2,
				lastMovement.getOrigin().getPosition().getRow() * squareDimension + squareDimension / 2
			),
			new Point2D.Double(
				lastMovement.getTarget().getPosition().getColumn() * squareDimension + squareDimension / 2,
				lastMovement.getTarget().getPosition().getRow() * squareDimension + squareDimension / 2
			)
		);
		g.draw(lastMovementLine);
		java.awt.geom.Ellipse2D.Double lastMovementTargetPoint = new Ellipse2D.Double(
			lastMovement.getTarget().getPosition().getColumn() * squareDimension + squareDimension / 2 - 8,
			lastMovement.getTarget().getPosition().getRow() * squareDimension + squareDimension / 2 - 8,
			16,
			16
		);
		g.fill(lastMovementTargetPoint);
		
		Position kingPosition = board.getKingPosition(board.getActiveColor());
		List<Position> attackers = board.getAttackers(kingPosition, board.getActiveColor().getOpposite());
		
		if (attackers.size() != 2) {
			return;
		}
		
		for (Position originAttacker : attackers) {
			java.awt.geom.Line2D.Double attackerLine = new Line2D.Double(
				new Point2D.Double(
					originAttacker.getColumn() * squareDimension + squareDimension / 2,
					originAttacker.getRow() * squareDimension + squareDimension / 2
				),
				new Point2D.Double(
					kingPosition.getColumn() * squareDimension + squareDimension / 2,
					kingPosition.getRow() * squareDimension + squareDimension / 2
				)
			);
			g.setColor(Color.BLUE);
			g.draw(attackerLine);
		}
		
		try {
			ImageIO.write(
				bufferedImage,
				"png",
				outfile.toFile()
			);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private Image loadPieceImage(Piece piece, double squareDimention) {
		String path = pieceIcons.get(piece);
		try (InputStream inputStream = BoardImageExporter.class.getResourceAsStream(path)) {
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			return bufferedImage.getScaledInstance(
				(int) squareDimention,
				(int) squareDimention,
				Image.SCALE_AREA_AVERAGING
			);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private Color getSquareColor(int row, int column) {
		return row % 2 == 0
				? column % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY
				: column % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE;
	}
}