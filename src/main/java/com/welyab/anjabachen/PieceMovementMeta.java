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
package com.welyab.anjabachen;

public class PieceMovementMeta {

	private int captureCount;

	private int promotionCount;

	private int totalMovements;

	private int castlingsCount;

	private int enPassantCount;

	public static Builder builder() {
		return new Builder();
	}

	public int getCaptureCount() {
		return captureCount;
	}

	public int getPromotionCount() {
		return promotionCount;
	}

	public int getCastlingsCount() {
		return castlingsCount;
	}

	public int getEnPassantCount() {
		return enPassantCount;
	}

	public int getTotalMovements() {
		return totalMovements;
	}

	public static final class Builder {

		private final PieceMovementMeta meta;

		private Builder() {
			meta = new PieceMovementMeta();
		}

		public Builder add(PieceMovementMeta meta) {
			incrementCaptureCount(meta.captureCount);
			incrementPromotionCount(meta.promotionCount);
			incrementCastlings(meta.castlingsCount);
			incrementTotalMovements(meta.totalMovements);
			incrementEnPassantCount(meta.enPassantCount);
			return this;
		}

		public Builder incrementCaptureCount() {
			return incrementCaptureCount(1);
		}

		public Builder incrementCaptureCount(int captureCount) {
			meta.captureCount += captureCount;
			return this;
		}

		public Builder incrementPromotionCount() {
			return incrementPromotionCount(1);
		}

		public Builder incrementPromotionCount(int promotionCount) {
			meta.promotionCount += promotionCount;
			return this;
		}

		public Builder incrementTotalMovements() {
			return incrementTotalMovements(1);
		}

		public Builder incrementCastlings() {
			return incrementCastlings(1);
		}

		public Builder incrementCastlings(int castlingsCount) {
			meta.castlingsCount += castlingsCount;
			return this;
		}

		public Builder incrementEnPassantCount() {
			return incrementEnPassantCount(1);
		}

		public Builder incrementEnPassantCount(int enPassantCount) {
			meta.enPassantCount += enPassantCount;
			return this;
		}

		public Builder incrementTotalMovements(int totalMovements) {
			meta.totalMovements += totalMovements;
			return this;
		}

		public PieceMovementMeta build() {
			return meta;
		}

	}
}
