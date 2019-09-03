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

/**
 * @author Welyab Paula
 */
public class MovementMetadata {
	
	private static final MovementMetadata EMPTY = builder().build();
	
	private int totalMovements;
	
	private int captureCount;
	
	private int enPassantCount;
	
	private int castlingsCount;
	
	private int promotionCount;
	
	private int checkCount;
	
	private int discoveryCheckCount;
	
	private int doubleCheckCount;
	
	private int checkmateCount;
	
	private int stalemateCount;
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static MovementMetadata empty() {
		return EMPTY;
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
	
	public int getCheckCount() {
		return checkCount;
	}
	
	public int getDiscoveryCheckCount() {
		return discoveryCheckCount;
	}
	
	public int getDoubleCheckCount() {
		return doubleCheckCount;
	}
	
	public int getCheckmateCount() {
		return checkmateCount;
	}
	
	public int getStalemateCount() {
		return stalemateCount;
	}
	
	@Override
	public String toString() {
		return "PieceMovementMeta [totalMovements=" + totalMovements + ", captureCount=" + captureCount
				+ ", enPassantCount=" + enPassantCount + ", castlingsCount=" + castlingsCount + ", promotionCount="
				+ promotionCount + ", checkCount=" + checkCount + ", discoveryCheckCount=" + discoveryCheckCount
				+ ", doubleCheckCount=" + doubleCheckCount + ", checkmateCount=" + checkmateCount + ", stalemateCount="
				+ stalemateCount + "]";
	}
	
	public static final class Builder {
		
		private final MovementMetadata meta;
		
		private Builder() {
			meta = new MovementMetadata();
		}
		
		public Builder add(MovementMetadata meta) {
			incrementTotalMovements(meta.totalMovements);
			incrementCaptureCount(meta.captureCount);
			incrementEnPassantCount(meta.enPassantCount);
			incrementCastlings(meta.castlingsCount);
			incrementPromotionCount(meta.promotionCount);
			incrementCheckCount(meta.checkCount);
			incrementDiscoveryCheckCount(meta.discoveryCheckCount);
			incrementDoubleCheckCount(meta.doubleCheckCount);
			incrementCheckmateCount(meta.checkmateCount);
			incrementStalemateCount(meta.stalemateCount);
			return this;
		}
		
		public Builder addMovement(int movementFlags) {
			incrementTotalMovements();
			if (BoardUtils.isCapture(movementFlags)) {
				incrementCaptureCount();
			}
			if (BoardUtils.isEnPassant(movementFlags)) {
				incrementEnPassantCount();
			}
			if (BoardUtils.isCastling(movementFlags)) {
				incrementCastlings();
			}
			if (BoardUtils.isPromotion(movementFlags)) {
				incrementPromotionCount();
			}
			if (BoardUtils.isCheck(movementFlags)) {
				incrementCheckCount();
			}
			if (BoardUtils.isDiscoveryCheck(movementFlags)) {
				incrementDiscoveryCheckCount();
			}
			if (BoardUtils.isDoubleCheck(movementFlags)) {
				incrementDoubleCheckCount();
			}
			if (BoardUtils.isCheckmate(movementFlags)) {
				incrementCheckmateCount();
			}
			if (BoardUtils.isStalemate(movementFlags)) {
				incrementStalemateCount();
			}
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
		
		public Builder incrementCheckCount() {
			return incrementCheckCount(1);
		}
		
		public Builder incrementCheckCount(int checkCount) {
			meta.checkCount += checkCount;
			return this;
		}
		
		public Builder incrementDiscoveryCheckCount() {
			return incrementDiscoveryCheckCount(1);
		}
		
		public Builder incrementDiscoveryCheckCount(int discoveryCheckCount) {
			meta.discoveryCheckCount += discoveryCheckCount;
			return this;
		}
		
		public Builder incrementDoubleCheckCount() {
			return incrementDoubleCheckCount(1);
		}
		
		public Builder incrementDoubleCheckCount(int doubleCheckCount) {
			meta.doubleCheckCount += doubleCheckCount;
			return this;
		}
		
		public Builder incrementCheckmateCount() {
			return incrementCheckmateCount(1);
		}
		
		public Builder incrementCheckmateCount(int checkmateCount) {
			meta.checkmateCount += checkmateCount;
			return this;
		}
		
		public Builder incrementStalemateCount() {
			return incrementStalemateCount(1);
		}
		
		public Builder incrementStalemateCount(int stalemateCount) {
			meta.stalemateCount += stalemateCount;
			return this;
		}
		
		public MovementMetadata build() {
			return meta;
		}
	}
}
