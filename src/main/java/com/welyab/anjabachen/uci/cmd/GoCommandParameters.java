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
package com.welyab.anjabachen.uci.cmd;

public class GoCommandParameters {
	
	private boolean ponder;
	
	private boolean infinite;
	
	private int whiteTime;
	
	private int blackTime;
	
	private int whiteIncrement;
	
	private int blackIncrement;
	
	private int movesToGo;
	
	private int depthLimit;
	
	private int nodesLimit;
	
	private int timeLimit;
	
	public boolean isPonder() {
		return ponder;
	}
	
	public boolean isInfinite() {
		return infinite;
	}
	
	public int getWhiteTime() {
		return whiteTime;
	}
	
	public int getBlackTime() {
		return blackTime;
	}
	
	public int getWhiteIncrement() {
		return whiteIncrement;
	}
	
	public int getBlackIncrement() {
		return blackIncrement;
	}
	
	public int getMovesToGo() {
		return movesToGo;
	}
	
	public int getDepthLimit() {
		return depthLimit;
	}
	
	public int getNodesLimit() {
		return nodesLimit;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static final class Builder {
		
		private GoCommandParameters parameters = new GoCommandParameters();
		
		public Builder setPonder(boolean ponder) {
			parameters.ponder = ponder;
			return this;
		}
		
		public Builder setInfinite(boolean infinite) {
			parameters.infinite = infinite;
			return this;
		}
		
		public Builder setWhiteTime(int whiteTime) {
			parameters.whiteTime = whiteTime;
			return this;
		}
		
		public Builder setBlackTime(int blackTime) {
			parameters.blackTime = blackTime;
			return this;
		}
		
		public Builder setWhiteIncrement(int whiteIncrement) {
			parameters.whiteIncrement = whiteIncrement;
			return this;
		}
		
		public Builder setBlackIncrement(int blackIncrement) {
			parameters.blackIncrement = blackIncrement;
			return this;
		}
		
		public Builder setMovesToGo(int movesToGo) {
			parameters.movesToGo = movesToGo;
			return this;
		}
		
		public Builder setDepthLimit(int depthLimit) {
			parameters.depthLimit = depthLimit;
			return this;
		}
		
		public Builder setNodesLimit(int nodesLimit) {
			parameters.nodesLimit = nodesLimit;
			return this;
		}
		
		public Builder setTimeLimit(int timeLimit) {
			parameters.timeLimit = timeLimit;
			return this;
		}
		
		public GoCommandParameters build() {
			return parameters;
		}
	}
}
