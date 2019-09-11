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
package com.welyab.anjabachen.ui.theme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.welyab.anjabachen.Piece;

public class Theme {
	
	private Image whiteSquareBackground;
	
	private Image blackSquareBackground;
	
	private Map<Piece, Image> pieces;
	
	public void clearCache() {
		whiteSquareBackground = null;
		blackSquareBackground = null;
		pieces = null;
	}
	
	public Image getWhiteSquareBackground(double width, double height) {
		if (whiteSquareBackground == null) {
			BufferedImage bufferedImage = new BufferedImage(
				(int) width,
				(int) height,
				BufferedImage.TYPE_INT_RGB
			);
			Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fill(new Rectangle2D.Double(0, 0, width, height));
			whiteSquareBackground = bufferedImage;
		}
		return whiteSquareBackground;
	}
	
	public Image getBlackSquareBackground(double width, double height) {
		if (blackSquareBackground == null) {
			BufferedImage bufferedImage = new BufferedImage(
				(int) width,
				(int) height,
				BufferedImage.TYPE_INT_RGB
			);
			Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fill(new Rectangle2D.Double(0, 0, width, height));
			blackSquareBackground = bufferedImage;
		}
		return blackSquareBackground;
	}
	
	private String getPieceFile(Piece piece) {
		return switch (piece) {
			case BLACK_KING -> "black_king.png";
			case BLACK_QUEEN -> "black_queen.png";
			case BLACK_ROOK -> "black_rook.png";
			case BLACK_BISHOP -> "black_bishop.png";
			case BLACK_KNIGHT -> "black_knight.png";
			case BLACK_PAWN -> "black_pawn.png";
			case WHITE_KING -> "white_king.png";
			case WHITE_QUEEN -> "white_queen.png";
			case WHITE_ROOK -> "white_rook.png";
			case WHITE_BISHOP -> "white_bishop.png";
			case WHITE_KNIGHT -> "white_knight.png";
			case WHITE_PAWN -> "white_pawn.png";
		};
	}
	
	public Image getPiece(Piece piece, double width, double height) {
		if (pieces == null) {
			pieces = new EnumMap<>(Piece.class);
		}
		if (!pieces.containsKey(piece)) {
			BufferedImage bufferedImage = null;
			try {
				bufferedImage = ImageIO.read(
					Theme.class.getClassLoader()
						.getResource("ui/themes/first/" + getPieceFile(piece))
				);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			Image scaledInstance = bufferedImage.getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);
			pieces.put(piece, scaledInstance);
		}
		return pieces.get(piece);
	}
}
