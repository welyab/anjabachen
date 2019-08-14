package com.welyab.anjabachen.perft;

public class FenPerftResult {
	
	private final String fen;
	
	private final PerftResult perftResult;
	
	public FenPerftResult(String fen, PerftResult perftResult) {
		this.fen = fen;
		this.perftResult = perftResult;
	}
	
	public String getFen() {
		return fen;
	}
	
	public PerftResult getPerftResult() {
		return perftResult;
	}
}
