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
