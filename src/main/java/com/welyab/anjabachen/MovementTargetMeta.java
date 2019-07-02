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

public class MovementTargetMeta {
	
	private boolean capture;
	
	private boolean enPassant;
	
	private boolean castling;
	
	private boolean promotion;
	
	private boolean check;
	
	private boolean discoveryCheck;
	
	private boolean doubleCheck;
	
	private boolean checkmate;
	
	public boolean isCapture() {
		return capture;
	}
	
	public boolean isEnPassant() {
		return enPassant;
	}
	
	public boolean isCastling() {
		return castling;
	}
	
	public boolean isPromotion() {
		return promotion;
	}
	
	public boolean isCheck() {
		return check;
	}
	
	public boolean isDicoveryCheck() {
		return discoveryCheck;
	}
	
	public boolean isDoubleCheck() {
		return doubleCheck;
	}
	
	public boolean isCheckmate() {
		return checkmate;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static final class Builder {
		
		private final MovementTargetMeta meta = new MovementTargetMeta();
		
		private Builder() {
		}
		
		public Builder setCapture(boolean capture) {
			meta.capture = capture;
			return this;
		}
		
		public Builder setEnPassant(boolean enPassant) {
			meta.enPassant = enPassant;
			return this;
		}
		
		public Builder setCastling(boolean castling) {
			meta.castling = castling;
			return this;
		}
		
		public Builder setPromotion(boolean promotion) {
			meta.promotion = promotion;
			return this;
		}
		
		public Builder setCheck(boolean check) {
			meta.check = check;
			return this;
		}
		
		public Builder setDicoveryCheck(boolean discoveryCheck) {
			meta.discoveryCheck = discoveryCheck;
			return this;
		}
		
		public Builder setDoubleCheck(boolean doubleCheck) {
			meta.doubleCheck = doubleCheck;
			return this;
		}
		
		public Builder setCheckmate(boolean checkmate) {
			meta.checkmate = checkmate;
			return this;
		}
		
		public MovementTargetMeta build() {
			return meta;
		}
	}
}
