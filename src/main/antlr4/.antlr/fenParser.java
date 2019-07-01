// Generated from /home/welyab/sources/chess/src/main/antlr4/fen.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class fenParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LowerLetterA=1, LowerLetterB=2, LowerLetterC=3, LowerLetterD=4, LowerLetterE=5, 
		LowerLetterF=6, LowerLetterG=7, LowerLetterH=8, LowerLetterK=9, LowerLetterN=10, 
		LowerLetterP=11, LowerLetterQ=12, LowerLetterR=13, LowerLetterW=14, UpperLetterB=15, 
		UpperLetterK=16, UpperLetterN=17, UpperLetterP=18, UpperLetterQ=19, UpperLetterR=20, 
		Zero=21, One=22, Two=23, Three=24, Four=25, Five=26, Six=27, Seven=28, 
		Eight=29, Nine=30, Hyphen=31, Slash=32, Space=33;
	public static final int
		RULE_fen = 0, RULE_piecePlacement = 1, RULE_dispositionRank1 = 2, RULE_dispositionRank2 = 3, 
		RULE_dispositionRank3 = 4, RULE_dispositionRank4 = 5, RULE_dispositionRank5 = 6, 
		RULE_dispositionRank6 = 7, RULE_dispositionRank7 = 8, RULE_dispositionRank8 = 9, 
		RULE_pieceDisposition = 10, RULE_piece = 11, RULE_blackPiece = 12, RULE_whitePiece = 13, 
		RULE_blackKing = 14, RULE_blackQueen = 15, RULE_blackRook = 16, RULE_blackBishop = 17, 
		RULE_blackKnight = 18, RULE_blackPawn = 19, RULE_whiteKing = 20, RULE_whiteQueen = 21, 
		RULE_whiteRook = 22, RULE_whiteBishop = 23, RULE_whiteKnight = 24, RULE_whitePawn = 25, 
		RULE_sideToMove = 26, RULE_castlingAbility = 27, RULE_whiteKingSideCastling = 28, 
		RULE_whiteQueenSideCastling = 29, RULE_blackKingSideCastling = 30, RULE_blackQueenSideCastling = 31, 
		RULE_enPassantTargetSquare = 32, RULE_halfMoveClock = 33, RULE_fullMoveCounter = 34, 
		RULE_file = 35, RULE_rank1 = 36, RULE_rank2 = 37, RULE_rank3 = 38, RULE_rank4 = 39, 
		RULE_rank5 = 40, RULE_rank6 = 41, RULE_rank7 = 42, RULE_rank9 = 43, RULE_none = 44, 
		RULE_white = 45, RULE_black = 46, RULE_digitZeroToNine = 47, RULE_digitOneToNine = 48, 
		RULE_digitOneToSeven = 49;
	public static final String[] ruleNames = {
		"fen", "piecePlacement", "dispositionRank1", "dispositionRank2", "dispositionRank3", 
		"dispositionRank4", "dispositionRank5", "dispositionRank6", "dispositionRank7", 
		"dispositionRank8", "pieceDisposition", "piece", "blackPiece", "whitePiece", 
		"blackKing", "blackQueen", "blackRook", "blackBishop", "blackKnight", 
		"blackPawn", "whiteKing", "whiteQueen", "whiteRook", "whiteBishop", "whiteKnight", 
		"whitePawn", "sideToMove", "castlingAbility", "whiteKingSideCastling", 
		"whiteQueenSideCastling", "blackKingSideCastling", "blackQueenSideCastling", 
		"enPassantTargetSquare", "halfMoveClock", "fullMoveCounter", "file", "rank1", 
		"rank2", "rank3", "rank4", "rank5", "rank6", "rank7", "rank9", "none", 
		"white", "black", "digitZeroToNine", "digitOneToNine", "digitOneToSeven"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'a'", "'b'", "'c'", "'d'", "'e'", "'f'", "'g'", "'h'", "'k'", "'n'", 
		"'p'", "'q'", "'r'", "'w'", "'B'", "'K'", "'N'", "'P'", "'Q'", "'R'", 
		"'0'", "'1'", "'2'", "'3'", "'4'", "'5'", "'6'", "'7'", "'8'", "'9'", 
		"'-'", "'/'", "' '"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LowerLetterA", "LowerLetterB", "LowerLetterC", "LowerLetterD", 
		"LowerLetterE", "LowerLetterF", "LowerLetterG", "LowerLetterH", "LowerLetterK", 
		"LowerLetterN", "LowerLetterP", "LowerLetterQ", "LowerLetterR", "LowerLetterW", 
		"UpperLetterB", "UpperLetterK", "UpperLetterN", "UpperLetterP", "UpperLetterQ", 
		"UpperLetterR", "Zero", "One", "Two", "Three", "Four", "Five", "Six", 
		"Seven", "Eight", "Nine", "Hyphen", "Slash", "Space"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "fen.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public fenParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FenContext extends ParserRuleContext {
		public PiecePlacementContext piecePlacement() {
			return getRuleContext(PiecePlacementContext.class,0);
		}
		public List<TerminalNode> Space() { return getTokens(fenParser.Space); }
		public TerminalNode Space(int i) {
			return getToken(fenParser.Space, i);
		}
		public SideToMoveContext sideToMove() {
			return getRuleContext(SideToMoveContext.class,0);
		}
		public CastlingAbilityContext castlingAbility() {
			return getRuleContext(CastlingAbilityContext.class,0);
		}
		public EnPassantTargetSquareContext enPassantTargetSquare() {
			return getRuleContext(EnPassantTargetSquareContext.class,0);
		}
		public HalfMoveClockContext halfMoveClock() {
			return getRuleContext(HalfMoveClockContext.class,0);
		}
		public FullMoveCounterContext fullMoveCounter() {
			return getRuleContext(FullMoveCounterContext.class,0);
		}
		public TerminalNode EOF() { return getToken(fenParser.EOF, 0); }
		public FenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fen; }
	}

	public final FenContext fen() throws RecognitionException {
		FenContext _localctx = new FenContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_fen);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			piecePlacement();
			setState(101);
			match(Space);
			setState(102);
			sideToMove();
			setState(103);
			match(Space);
			setState(104);
			castlingAbility();
			setState(105);
			match(Space);
			setState(106);
			enPassantTargetSquare();
			setState(107);
			match(Space);
			setState(108);
			halfMoveClock();
			setState(109);
			match(Space);
			setState(110);
			fullMoveCounter();
			setState(111);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PiecePlacementContext extends ParserRuleContext {
		public DispositionRank1Context dispositionRank1() {
			return getRuleContext(DispositionRank1Context.class,0);
		}
		public List<TerminalNode> Slash() { return getTokens(fenParser.Slash); }
		public TerminalNode Slash(int i) {
			return getToken(fenParser.Slash, i);
		}
		public DispositionRank2Context dispositionRank2() {
			return getRuleContext(DispositionRank2Context.class,0);
		}
		public DispositionRank3Context dispositionRank3() {
			return getRuleContext(DispositionRank3Context.class,0);
		}
		public DispositionRank4Context dispositionRank4() {
			return getRuleContext(DispositionRank4Context.class,0);
		}
		public DispositionRank5Context dispositionRank5() {
			return getRuleContext(DispositionRank5Context.class,0);
		}
		public DispositionRank6Context dispositionRank6() {
			return getRuleContext(DispositionRank6Context.class,0);
		}
		public DispositionRank7Context dispositionRank7() {
			return getRuleContext(DispositionRank7Context.class,0);
		}
		public DispositionRank8Context dispositionRank8() {
			return getRuleContext(DispositionRank8Context.class,0);
		}
		public PiecePlacementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_piecePlacement; }
	}

	public final PiecePlacementContext piecePlacement() throws RecognitionException {
		PiecePlacementContext _localctx = new PiecePlacementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_piecePlacement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			dispositionRank1();
			setState(114);
			match(Slash);
			setState(115);
			dispositionRank2();
			setState(116);
			match(Slash);
			setState(117);
			dispositionRank3();
			setState(118);
			match(Slash);
			setState(119);
			dispositionRank4();
			setState(120);
			match(Slash);
			setState(121);
			dispositionRank5();
			setState(122);
			match(Slash);
			setState(123);
			dispositionRank6();
			setState(124);
			match(Slash);
			setState(125);
			dispositionRank7();
			setState(126);
			match(Slash);
			setState(127);
			dispositionRank8();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DispositionRank1Context extends ParserRuleContext {
		public PieceDispositionContext pieceDisposition() {
			return getRuleContext(PieceDispositionContext.class,0);
		}
		public DispositionRank1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dispositionRank1; }
	}

	public final DispositionRank1Context dispositionRank1() throws RecognitionException {
		DispositionRank1Context _localctx = new DispositionRank1Context(_ctx, getState());
		enterRule(_localctx, 4, RULE_dispositionRank1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			pieceDisposition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DispositionRank2Context extends ParserRuleContext {
		public PieceDispositionContext pieceDisposition() {
			return getRuleContext(PieceDispositionContext.class,0);
		}
		public DispositionRank2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dispositionRank2; }
	}

	public final DispositionRank2Context dispositionRank2() throws RecognitionException {
		DispositionRank2Context _localctx = new DispositionRank2Context(_ctx, getState());
		enterRule(_localctx, 6, RULE_dispositionRank2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			pieceDisposition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DispositionRank3Context extends ParserRuleContext {
		public PieceDispositionContext pieceDisposition() {
			return getRuleContext(PieceDispositionContext.class,0);
		}
		public DispositionRank3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dispositionRank3; }
	}

	public final DispositionRank3Context dispositionRank3() throws RecognitionException {
		DispositionRank3Context _localctx = new DispositionRank3Context(_ctx, getState());
		enterRule(_localctx, 8, RULE_dispositionRank3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			pieceDisposition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DispositionRank4Context extends ParserRuleContext {
		public PieceDispositionContext pieceDisposition() {
			return getRuleContext(PieceDispositionContext.class,0);
		}
		public DispositionRank4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dispositionRank4; }
	}

	public final DispositionRank4Context dispositionRank4() throws RecognitionException {
		DispositionRank4Context _localctx = new DispositionRank4Context(_ctx, getState());
		enterRule(_localctx, 10, RULE_dispositionRank4);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			pieceDisposition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DispositionRank5Context extends ParserRuleContext {
		public PieceDispositionContext pieceDisposition() {
			return getRuleContext(PieceDispositionContext.class,0);
		}
		public DispositionRank5Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dispositionRank5; }
	}

	public final DispositionRank5Context dispositionRank5() throws RecognitionException {
		DispositionRank5Context _localctx = new DispositionRank5Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_dispositionRank5);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			pieceDisposition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DispositionRank6Context extends ParserRuleContext {
		public PieceDispositionContext pieceDisposition() {
			return getRuleContext(PieceDispositionContext.class,0);
		}
		public DispositionRank6Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dispositionRank6; }
	}

	public final DispositionRank6Context dispositionRank6() throws RecognitionException {
		DispositionRank6Context _localctx = new DispositionRank6Context(_ctx, getState());
		enterRule(_localctx, 14, RULE_dispositionRank6);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			pieceDisposition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DispositionRank7Context extends ParserRuleContext {
		public PieceDispositionContext pieceDisposition() {
			return getRuleContext(PieceDispositionContext.class,0);
		}
		public DispositionRank7Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dispositionRank7; }
	}

	public final DispositionRank7Context dispositionRank7() throws RecognitionException {
		DispositionRank7Context _localctx = new DispositionRank7Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_dispositionRank7);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			pieceDisposition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DispositionRank8Context extends ParserRuleContext {
		public PieceDispositionContext pieceDisposition() {
			return getRuleContext(PieceDispositionContext.class,0);
		}
		public DispositionRank8Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dispositionRank8; }
	}

	public final DispositionRank8Context dispositionRank8() throws RecognitionException {
		DispositionRank8Context _localctx = new DispositionRank8Context(_ctx, getState());
		enterRule(_localctx, 18, RULE_dispositionRank8);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			pieceDisposition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PieceDispositionContext extends ParserRuleContext {
		public TerminalNode Eight() { return getToken(fenParser.Eight, 0); }
		public List<DigitOneToSevenContext> digitOneToSeven() {
			return getRuleContexts(DigitOneToSevenContext.class);
		}
		public DigitOneToSevenContext digitOneToSeven(int i) {
			return getRuleContext(DigitOneToSevenContext.class,i);
		}
		public List<PieceContext> piece() {
			return getRuleContexts(PieceContext.class);
		}
		public PieceContext piece(int i) {
			return getRuleContext(PieceContext.class,i);
		}
		public PieceDispositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pieceDisposition; }
	}

	public final PieceDispositionContext pieceDisposition() throws RecognitionException {
		PieceDispositionContext _localctx = new PieceDispositionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_pieceDisposition);
		int _la;
		try {
			int _alt;
			setState(166);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Eight:
				enterOuterAlt(_localctx, 1);
				{
				setState(145);
				match(Eight);
				}
				break;
			case One:
			case Two:
			case Four:
			case Five:
			case Six:
			case Seven:
				enterOuterAlt(_localctx, 2);
				{
				setState(149); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(146);
						digitOneToSeven();
						setState(147);
						piece();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(151); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << One) | (1L << Two) | (1L << Four) | (1L << Five) | (1L << Six) | (1L << Seven))) != 0)) {
					{
					setState(153);
					digitOneToSeven();
					}
				}

				}
				break;
			case LowerLetterB:
			case LowerLetterK:
			case LowerLetterN:
			case LowerLetterP:
			case LowerLetterQ:
			case LowerLetterR:
			case UpperLetterB:
			case UpperLetterK:
			case UpperLetterN:
			case UpperLetterP:
			case UpperLetterQ:
			case UpperLetterR:
				enterOuterAlt(_localctx, 3);
				{
				setState(159); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(156);
						piece();
						setState(157);
						digitOneToSeven();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(161); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LowerLetterB) | (1L << LowerLetterK) | (1L << LowerLetterN) | (1L << LowerLetterP) | (1L << LowerLetterQ) | (1L << LowerLetterR) | (1L << UpperLetterB) | (1L << UpperLetterK) | (1L << UpperLetterN) | (1L << UpperLetterP) | (1L << UpperLetterQ) | (1L << UpperLetterR))) != 0)) {
					{
					setState(163);
					piece();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PieceContext extends ParserRuleContext {
		public BlackPieceContext blackPiece() {
			return getRuleContext(BlackPieceContext.class,0);
		}
		public WhitePieceContext whitePiece() {
			return getRuleContext(WhitePieceContext.class,0);
		}
		public PieceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_piece; }
	}

	public final PieceContext piece() throws RecognitionException {
		PieceContext _localctx = new PieceContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_piece);
		try {
			setState(170);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LowerLetterB:
			case LowerLetterK:
			case LowerLetterN:
			case LowerLetterP:
			case LowerLetterQ:
			case LowerLetterR:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				blackPiece();
				}
				break;
			case UpperLetterB:
			case UpperLetterK:
			case UpperLetterN:
			case UpperLetterP:
			case UpperLetterQ:
			case UpperLetterR:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				whitePiece();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackPieceContext extends ParserRuleContext {
		public BlackKingContext blackKing() {
			return getRuleContext(BlackKingContext.class,0);
		}
		public BlackQueenContext blackQueen() {
			return getRuleContext(BlackQueenContext.class,0);
		}
		public BlackRookContext blackRook() {
			return getRuleContext(BlackRookContext.class,0);
		}
		public BlackBishopContext blackBishop() {
			return getRuleContext(BlackBishopContext.class,0);
		}
		public BlackKnightContext blackKnight() {
			return getRuleContext(BlackKnightContext.class,0);
		}
		public BlackPawnContext blackPawn() {
			return getRuleContext(BlackPawnContext.class,0);
		}
		public BlackPieceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackPiece; }
	}

	public final BlackPieceContext blackPiece() throws RecognitionException {
		BlackPieceContext _localctx = new BlackPieceContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_blackPiece);
		try {
			setState(178);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LowerLetterK:
				enterOuterAlt(_localctx, 1);
				{
				setState(172);
				blackKing();
				}
				break;
			case LowerLetterQ:
				enterOuterAlt(_localctx, 2);
				{
				setState(173);
				blackQueen();
				}
				break;
			case LowerLetterR:
				enterOuterAlt(_localctx, 3);
				{
				setState(174);
				blackRook();
				}
				break;
			case LowerLetterB:
				enterOuterAlt(_localctx, 4);
				{
				setState(175);
				blackBishop();
				}
				break;
			case LowerLetterN:
				enterOuterAlt(_localctx, 5);
				{
				setState(176);
				blackKnight();
				}
				break;
			case LowerLetterP:
				enterOuterAlt(_localctx, 6);
				{
				setState(177);
				blackPawn();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhitePieceContext extends ParserRuleContext {
		public WhiteKingContext whiteKing() {
			return getRuleContext(WhiteKingContext.class,0);
		}
		public WhiteQueenContext whiteQueen() {
			return getRuleContext(WhiteQueenContext.class,0);
		}
		public WhiteRookContext whiteRook() {
			return getRuleContext(WhiteRookContext.class,0);
		}
		public WhiteBishopContext whiteBishop() {
			return getRuleContext(WhiteBishopContext.class,0);
		}
		public WhiteKnightContext whiteKnight() {
			return getRuleContext(WhiteKnightContext.class,0);
		}
		public WhitePawnContext whitePawn() {
			return getRuleContext(WhitePawnContext.class,0);
		}
		public WhitePieceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whitePiece; }
	}

	public final WhitePieceContext whitePiece() throws RecognitionException {
		WhitePieceContext _localctx = new WhitePieceContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_whitePiece);
		try {
			setState(186);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UpperLetterK:
				enterOuterAlt(_localctx, 1);
				{
				setState(180);
				whiteKing();
				}
				break;
			case UpperLetterQ:
				enterOuterAlt(_localctx, 2);
				{
				setState(181);
				whiteQueen();
				}
				break;
			case UpperLetterR:
				enterOuterAlt(_localctx, 3);
				{
				setState(182);
				whiteRook();
				}
				break;
			case UpperLetterB:
				enterOuterAlt(_localctx, 4);
				{
				setState(183);
				whiteBishop();
				}
				break;
			case UpperLetterN:
				enterOuterAlt(_localctx, 5);
				{
				setState(184);
				whiteKnight();
				}
				break;
			case UpperLetterP:
				enterOuterAlt(_localctx, 6);
				{
				setState(185);
				whitePawn();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackKingContext extends ParserRuleContext {
		public TerminalNode LowerLetterK() { return getToken(fenParser.LowerLetterK, 0); }
		public BlackKingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackKing; }
	}

	public final BlackKingContext blackKing() throws RecognitionException {
		BlackKingContext _localctx = new BlackKingContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_blackKing);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(LowerLetterK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackQueenContext extends ParserRuleContext {
		public TerminalNode LowerLetterQ() { return getToken(fenParser.LowerLetterQ, 0); }
		public BlackQueenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackQueen; }
	}

	public final BlackQueenContext blackQueen() throws RecognitionException {
		BlackQueenContext _localctx = new BlackQueenContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_blackQueen);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(LowerLetterQ);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackRookContext extends ParserRuleContext {
		public TerminalNode LowerLetterR() { return getToken(fenParser.LowerLetterR, 0); }
		public BlackRookContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackRook; }
	}

	public final BlackRookContext blackRook() throws RecognitionException {
		BlackRookContext _localctx = new BlackRookContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_blackRook);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(LowerLetterR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackBishopContext extends ParserRuleContext {
		public TerminalNode LowerLetterB() { return getToken(fenParser.LowerLetterB, 0); }
		public BlackBishopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackBishop; }
	}

	public final BlackBishopContext blackBishop() throws RecognitionException {
		BlackBishopContext _localctx = new BlackBishopContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_blackBishop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(LowerLetterB);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackKnightContext extends ParserRuleContext {
		public TerminalNode LowerLetterN() { return getToken(fenParser.LowerLetterN, 0); }
		public BlackKnightContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackKnight; }
	}

	public final BlackKnightContext blackKnight() throws RecognitionException {
		BlackKnightContext _localctx = new BlackKnightContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_blackKnight);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			match(LowerLetterN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackPawnContext extends ParserRuleContext {
		public TerminalNode LowerLetterP() { return getToken(fenParser.LowerLetterP, 0); }
		public BlackPawnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackPawn; }
	}

	public final BlackPawnContext blackPawn() throws RecognitionException {
		BlackPawnContext _localctx = new BlackPawnContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_blackPawn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(LowerLetterP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiteKingContext extends ParserRuleContext {
		public TerminalNode UpperLetterK() { return getToken(fenParser.UpperLetterK, 0); }
		public WhiteKingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiteKing; }
	}

	public final WhiteKingContext whiteKing() throws RecognitionException {
		WhiteKingContext _localctx = new WhiteKingContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_whiteKing);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(UpperLetterK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiteQueenContext extends ParserRuleContext {
		public TerminalNode UpperLetterQ() { return getToken(fenParser.UpperLetterQ, 0); }
		public WhiteQueenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiteQueen; }
	}

	public final WhiteQueenContext whiteQueen() throws RecognitionException {
		WhiteQueenContext _localctx = new WhiteQueenContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_whiteQueen);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(UpperLetterQ);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiteRookContext extends ParserRuleContext {
		public TerminalNode UpperLetterR() { return getToken(fenParser.UpperLetterR, 0); }
		public WhiteRookContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiteRook; }
	}

	public final WhiteRookContext whiteRook() throws RecognitionException {
		WhiteRookContext _localctx = new WhiteRookContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_whiteRook);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			match(UpperLetterR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiteBishopContext extends ParserRuleContext {
		public TerminalNode UpperLetterB() { return getToken(fenParser.UpperLetterB, 0); }
		public WhiteBishopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiteBishop; }
	}

	public final WhiteBishopContext whiteBishop() throws RecognitionException {
		WhiteBishopContext _localctx = new WhiteBishopContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_whiteBishop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(UpperLetterB);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiteKnightContext extends ParserRuleContext {
		public TerminalNode UpperLetterN() { return getToken(fenParser.UpperLetterN, 0); }
		public WhiteKnightContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiteKnight; }
	}

	public final WhiteKnightContext whiteKnight() throws RecognitionException {
		WhiteKnightContext _localctx = new WhiteKnightContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_whiteKnight);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			match(UpperLetterN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhitePawnContext extends ParserRuleContext {
		public TerminalNode UpperLetterP() { return getToken(fenParser.UpperLetterP, 0); }
		public WhitePawnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whitePawn; }
	}

	public final WhitePawnContext whitePawn() throws RecognitionException {
		WhitePawnContext _localctx = new WhitePawnContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_whitePawn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(UpperLetterP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SideToMoveContext extends ParserRuleContext {
		public BlackContext black() {
			return getRuleContext(BlackContext.class,0);
		}
		public WhiteContext white() {
			return getRuleContext(WhiteContext.class,0);
		}
		public SideToMoveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sideToMove; }
	}

	public final SideToMoveContext sideToMove() throws RecognitionException {
		SideToMoveContext _localctx = new SideToMoveContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_sideToMove);
		try {
			setState(214);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LowerLetterW:
				enterOuterAlt(_localctx, 1);
				{
				setState(212);
				black();
				}
				break;
			case LowerLetterB:
				enterOuterAlt(_localctx, 2);
				{
				setState(213);
				white();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CastlingAbilityContext extends ParserRuleContext {
		public NoneContext none() {
			return getRuleContext(NoneContext.class,0);
		}
		public WhiteKingSideCastlingContext whiteKingSideCastling() {
			return getRuleContext(WhiteKingSideCastlingContext.class,0);
		}
		public WhiteQueenSideCastlingContext whiteQueenSideCastling() {
			return getRuleContext(WhiteQueenSideCastlingContext.class,0);
		}
		public BlackKingSideCastlingContext blackKingSideCastling() {
			return getRuleContext(BlackKingSideCastlingContext.class,0);
		}
		public BlackQueenSideCastlingContext blackQueenSideCastling() {
			return getRuleContext(BlackQueenSideCastlingContext.class,0);
		}
		public CastlingAbilityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_castlingAbility; }
	}

	public final CastlingAbilityContext castlingAbility() throws RecognitionException {
		CastlingAbilityContext _localctx = new CastlingAbilityContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_castlingAbility);
		try {
			setState(238);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(216);
				none();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(217);
				whiteKingSideCastling();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(218);
				whiteKingSideCastling();
				setState(219);
				whiteQueenSideCastling();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(221);
				whiteKingSideCastling();
				setState(222);
				whiteQueenSideCastling();
				setState(223);
				blackKingSideCastling();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(225);
				whiteKingSideCastling();
				setState(226);
				whiteQueenSideCastling();
				setState(227);
				blackKingSideCastling();
				setState(228);
				blackQueenSideCastling();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(230);
				whiteQueenSideCastling();
				setState(231);
				blackKingSideCastling();
				setState(232);
				blackQueenSideCastling();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(234);
				blackKingSideCastling();
				setState(235);
				blackQueenSideCastling();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(237);
				blackQueenSideCastling();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiteKingSideCastlingContext extends ParserRuleContext {
		public TerminalNode UpperLetterK() { return getToken(fenParser.UpperLetterK, 0); }
		public WhiteKingSideCastlingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiteKingSideCastling; }
	}

	public final WhiteKingSideCastlingContext whiteKingSideCastling() throws RecognitionException {
		WhiteKingSideCastlingContext _localctx = new WhiteKingSideCastlingContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_whiteKingSideCastling);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(UpperLetterK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiteQueenSideCastlingContext extends ParserRuleContext {
		public TerminalNode UpperLetterQ() { return getToken(fenParser.UpperLetterQ, 0); }
		public WhiteQueenSideCastlingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiteQueenSideCastling; }
	}

	public final WhiteQueenSideCastlingContext whiteQueenSideCastling() throws RecognitionException {
		WhiteQueenSideCastlingContext _localctx = new WhiteQueenSideCastlingContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_whiteQueenSideCastling);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			match(UpperLetterQ);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackKingSideCastlingContext extends ParserRuleContext {
		public TerminalNode LowerLetterK() { return getToken(fenParser.LowerLetterK, 0); }
		public BlackKingSideCastlingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackKingSideCastling; }
	}

	public final BlackKingSideCastlingContext blackKingSideCastling() throws RecognitionException {
		BlackKingSideCastlingContext _localctx = new BlackKingSideCastlingContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_blackKingSideCastling);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(LowerLetterK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackQueenSideCastlingContext extends ParserRuleContext {
		public TerminalNode LowerLetterQ() { return getToken(fenParser.LowerLetterQ, 0); }
		public BlackQueenSideCastlingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blackQueenSideCastling; }
	}

	public final BlackQueenSideCastlingContext blackQueenSideCastling() throws RecognitionException {
		BlackQueenSideCastlingContext _localctx = new BlackQueenSideCastlingContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_blackQueenSideCastling);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(LowerLetterQ);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnPassantTargetSquareContext extends ParserRuleContext {
		public NoneContext none() {
			return getRuleContext(NoneContext.class,0);
		}
		public FileContext file() {
			return getRuleContext(FileContext.class,0);
		}
		public Rank3Context rank3() {
			return getRuleContext(Rank3Context.class,0);
		}
		public Rank6Context rank6() {
			return getRuleContext(Rank6Context.class,0);
		}
		public EnPassantTargetSquareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enPassantTargetSquare; }
	}

	public final EnPassantTargetSquareContext enPassantTargetSquare() throws RecognitionException {
		EnPassantTargetSquareContext _localctx = new EnPassantTargetSquareContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_enPassantTargetSquare);
		try {
			setState(254);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Hyphen:
				enterOuterAlt(_localctx, 1);
				{
				setState(248);
				none();
				}
				break;
			case LowerLetterA:
			case LowerLetterB:
			case LowerLetterC:
			case LowerLetterD:
			case LowerLetterE:
			case LowerLetterF:
			case LowerLetterG:
			case LowerLetterH:
				enterOuterAlt(_localctx, 2);
				{
				setState(249);
				file();
				setState(252);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Three:
					{
					setState(250);
					rank3();
					}
					break;
				case Six:
					{
					setState(251);
					rank6();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HalfMoveClockContext extends ParserRuleContext {
		public DigitZeroToNineContext digitZeroToNine() {
			return getRuleContext(DigitZeroToNineContext.class,0);
		}
		public TerminalNode One() { return getToken(fenParser.One, 0); }
		public TerminalNode Two() { return getToken(fenParser.Two, 0); }
		public TerminalNode Four() { return getToken(fenParser.Four, 0); }
		public TerminalNode Five() { return getToken(fenParser.Five, 0); }
		public TerminalNode Zero() { return getToken(fenParser.Zero, 0); }
		public HalfMoveClockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_halfMoveClock; }
	}

	public final HalfMoveClockContext halfMoveClock() throws RecognitionException {
		HalfMoveClockContext _localctx = new HalfMoveClockContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_halfMoveClock);
		int _la;
		try {
			setState(261);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(256);
				digitZeroToNine();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(257);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << One) | (1L << Two) | (1L << Four))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(258);
				digitZeroToNine();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(259);
				match(Five);
				setState(260);
				match(Zero);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FullMoveCounterContext extends ParserRuleContext {
		public List<DigitZeroToNineContext> digitZeroToNine() {
			return getRuleContexts(DigitZeroToNineContext.class);
		}
		public DigitZeroToNineContext digitZeroToNine(int i) {
			return getRuleContext(DigitZeroToNineContext.class,i);
		}
		public DigitOneToNineContext digitOneToNine() {
			return getRuleContext(DigitOneToNineContext.class,0);
		}
		public FullMoveCounterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fullMoveCounter; }
	}

	public final FullMoveCounterContext fullMoveCounter() throws RecognitionException {
		FullMoveCounterContext _localctx = new FullMoveCounterContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_fullMoveCounter);
		int _la;
		try {
			setState(270);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				digitZeroToNine();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				digitOneToNine();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(266); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(265);
					digitZeroToNine();
					}
					}
					setState(268); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Zero) | (1L << One) | (1L << Two) | (1L << Four) | (1L << Five) | (1L << Six) | (1L << Seven) | (1L << Eight) | (1L << Nine))) != 0) );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FileContext extends ParserRuleContext {
		public TerminalNode LowerLetterA() { return getToken(fenParser.LowerLetterA, 0); }
		public TerminalNode LowerLetterB() { return getToken(fenParser.LowerLetterB, 0); }
		public TerminalNode LowerLetterC() { return getToken(fenParser.LowerLetterC, 0); }
		public TerminalNode LowerLetterD() { return getToken(fenParser.LowerLetterD, 0); }
		public TerminalNode LowerLetterE() { return getToken(fenParser.LowerLetterE, 0); }
		public TerminalNode LowerLetterF() { return getToken(fenParser.LowerLetterF, 0); }
		public TerminalNode LowerLetterG() { return getToken(fenParser.LowerLetterG, 0); }
		public TerminalNode LowerLetterH() { return getToken(fenParser.LowerLetterH, 0); }
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LowerLetterA) | (1L << LowerLetterB) | (1L << LowerLetterC) | (1L << LowerLetterD) | (1L << LowerLetterE) | (1L << LowerLetterF) | (1L << LowerLetterG) | (1L << LowerLetterH))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rank1Context extends ParserRuleContext {
		public TerminalNode One() { return getToken(fenParser.One, 0); }
		public Rank1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank1; }
	}

	public final Rank1Context rank1() throws RecognitionException {
		Rank1Context _localctx = new Rank1Context(_ctx, getState());
		enterRule(_localctx, 72, RULE_rank1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			match(One);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rank2Context extends ParserRuleContext {
		public TerminalNode Two() { return getToken(fenParser.Two, 0); }
		public Rank2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank2; }
	}

	public final Rank2Context rank2() throws RecognitionException {
		Rank2Context _localctx = new Rank2Context(_ctx, getState());
		enterRule(_localctx, 74, RULE_rank2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			match(Two);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rank3Context extends ParserRuleContext {
		public TerminalNode Three() { return getToken(fenParser.Three, 0); }
		public Rank3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank3; }
	}

	public final Rank3Context rank3() throws RecognitionException {
		Rank3Context _localctx = new Rank3Context(_ctx, getState());
		enterRule(_localctx, 76, RULE_rank3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			match(Three);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rank4Context extends ParserRuleContext {
		public TerminalNode Four() { return getToken(fenParser.Four, 0); }
		public Rank4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank4; }
	}

	public final Rank4Context rank4() throws RecognitionException {
		Rank4Context _localctx = new Rank4Context(_ctx, getState());
		enterRule(_localctx, 78, RULE_rank4);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(Four);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rank5Context extends ParserRuleContext {
		public TerminalNode Five() { return getToken(fenParser.Five, 0); }
		public Rank5Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank5; }
	}

	public final Rank5Context rank5() throws RecognitionException {
		Rank5Context _localctx = new Rank5Context(_ctx, getState());
		enterRule(_localctx, 80, RULE_rank5);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(Five);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rank6Context extends ParserRuleContext {
		public TerminalNode Six() { return getToken(fenParser.Six, 0); }
		public Rank6Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank6; }
	}

	public final Rank6Context rank6() throws RecognitionException {
		Rank6Context _localctx = new Rank6Context(_ctx, getState());
		enterRule(_localctx, 82, RULE_rank6);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			match(Six);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rank7Context extends ParserRuleContext {
		public TerminalNode Seven() { return getToken(fenParser.Seven, 0); }
		public Rank7Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank7; }
	}

	public final Rank7Context rank7() throws RecognitionException {
		Rank7Context _localctx = new Rank7Context(_ctx, getState());
		enterRule(_localctx, 84, RULE_rank7);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			match(Seven);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rank9Context extends ParserRuleContext {
		public TerminalNode Eight() { return getToken(fenParser.Eight, 0); }
		public Rank9Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank9; }
	}

	public final Rank9Context rank9() throws RecognitionException {
		Rank9Context _localctx = new Rank9Context(_ctx, getState());
		enterRule(_localctx, 86, RULE_rank9);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(Eight);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NoneContext extends ParserRuleContext {
		public TerminalNode Hyphen() { return getToken(fenParser.Hyphen, 0); }
		public NoneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_none; }
	}

	public final NoneContext none() throws RecognitionException {
		NoneContext _localctx = new NoneContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_none);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(Hyphen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiteContext extends ParserRuleContext {
		public TerminalNode LowerLetterB() { return getToken(fenParser.LowerLetterB, 0); }
		public WhiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_white; }
	}

	public final WhiteContext white() throws RecognitionException {
		WhiteContext _localctx = new WhiteContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_white);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			match(LowerLetterB);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlackContext extends ParserRuleContext {
		public TerminalNode LowerLetterW() { return getToken(fenParser.LowerLetterW, 0); }
		public BlackContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_black; }
	}

	public final BlackContext black() throws RecognitionException {
		BlackContext _localctx = new BlackContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_black);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			match(LowerLetterW);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DigitZeroToNineContext extends ParserRuleContext {
		public TerminalNode Zero() { return getToken(fenParser.Zero, 0); }
		public TerminalNode One() { return getToken(fenParser.One, 0); }
		public TerminalNode Two() { return getToken(fenParser.Two, 0); }
		public TerminalNode Four() { return getToken(fenParser.Four, 0); }
		public TerminalNode Five() { return getToken(fenParser.Five, 0); }
		public TerminalNode Six() { return getToken(fenParser.Six, 0); }
		public TerminalNode Seven() { return getToken(fenParser.Seven, 0); }
		public TerminalNode Eight() { return getToken(fenParser.Eight, 0); }
		public TerminalNode Nine() { return getToken(fenParser.Nine, 0); }
		public DigitZeroToNineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_digitZeroToNine; }
	}

	public final DigitZeroToNineContext digitZeroToNine() throws RecognitionException {
		DigitZeroToNineContext _localctx = new DigitZeroToNineContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_digitZeroToNine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Zero) | (1L << One) | (1L << Two) | (1L << Four) | (1L << Five) | (1L << Six) | (1L << Seven) | (1L << Eight) | (1L << Nine))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DigitOneToNineContext extends ParserRuleContext {
		public TerminalNode One() { return getToken(fenParser.One, 0); }
		public TerminalNode Two() { return getToken(fenParser.Two, 0); }
		public TerminalNode Four() { return getToken(fenParser.Four, 0); }
		public TerminalNode Five() { return getToken(fenParser.Five, 0); }
		public TerminalNode Six() { return getToken(fenParser.Six, 0); }
		public TerminalNode Seven() { return getToken(fenParser.Seven, 0); }
		public TerminalNode Eight() { return getToken(fenParser.Eight, 0); }
		public TerminalNode Nine() { return getToken(fenParser.Nine, 0); }
		public DigitOneToNineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_digitOneToNine; }
	}

	public final DigitOneToNineContext digitOneToNine() throws RecognitionException {
		DigitOneToNineContext _localctx = new DigitOneToNineContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_digitOneToNine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << One) | (1L << Two) | (1L << Four) | (1L << Five) | (1L << Six) | (1L << Seven) | (1L << Eight) | (1L << Nine))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DigitOneToSevenContext extends ParserRuleContext {
		public TerminalNode One() { return getToken(fenParser.One, 0); }
		public TerminalNode Two() { return getToken(fenParser.Two, 0); }
		public TerminalNode Four() { return getToken(fenParser.Four, 0); }
		public TerminalNode Five() { return getToken(fenParser.Five, 0); }
		public TerminalNode Six() { return getToken(fenParser.Six, 0); }
		public TerminalNode Seven() { return getToken(fenParser.Seven, 0); }
		public DigitOneToSevenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_digitOneToSeven; }
	}

	public final DigitOneToSevenContext digitOneToSeven() throws RecognitionException {
		DigitOneToSevenContext _localctx = new DigitOneToSevenContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_digitOneToSeven);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << One) | (1L << Two) | (1L << Four) | (1L << Five) | (1L << Six) | (1L << Seven))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3#\u0131\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7"+
		"\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\6\f\u0098\n\f\r\f\16"+
		"\f\u0099\3\f\5\f\u009d\n\f\3\f\3\f\3\f\6\f\u00a2\n\f\r\f\16\f\u00a3\3"+
		"\f\5\f\u00a7\n\f\5\f\u00a9\n\f\3\r\3\r\5\r\u00ad\n\r\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\5\16\u00b5\n\16\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00bd"+
		"\n\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\5\34"+
		"\u00d9\n\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u00f1\n\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3\"\5\"\u00ff\n\"\5\"\u0101\n"+
		"\"\3#\3#\3#\3#\3#\5#\u0108\n#\3$\3$\3$\6$\u010d\n$\r$\16$\u010e\5$\u0111"+
		"\n$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3"+
		"/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\63\2\2\64\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bd\2\7"+
		"\4\2\30\31\33\33\3\2\3\n\4\2\27\31\33 \4\2\30\31\33 \4\2\30\31\33\36\2"+
		"\u011f\2f\3\2\2\2\4s\3\2\2\2\6\u0083\3\2\2\2\b\u0085\3\2\2\2\n\u0087\3"+
		"\2\2\2\f\u0089\3\2\2\2\16\u008b\3\2\2\2\20\u008d\3\2\2\2\22\u008f\3\2"+
		"\2\2\24\u0091\3\2\2\2\26\u00a8\3\2\2\2\30\u00ac\3\2\2\2\32\u00b4\3\2\2"+
		"\2\34\u00bc\3\2\2\2\36\u00be\3\2\2\2 \u00c0\3\2\2\2\"\u00c2\3\2\2\2$\u00c4"+
		"\3\2\2\2&\u00c6\3\2\2\2(\u00c8\3\2\2\2*\u00ca\3\2\2\2,\u00cc\3\2\2\2."+
		"\u00ce\3\2\2\2\60\u00d0\3\2\2\2\62\u00d2\3\2\2\2\64\u00d4\3\2\2\2\66\u00d8"+
		"\3\2\2\28\u00f0\3\2\2\2:\u00f2\3\2\2\2<\u00f4\3\2\2\2>\u00f6\3\2\2\2@"+
		"\u00f8\3\2\2\2B\u0100\3\2\2\2D\u0107\3\2\2\2F\u0110\3\2\2\2H\u0112\3\2"+
		"\2\2J\u0114\3\2\2\2L\u0116\3\2\2\2N\u0118\3\2\2\2P\u011a\3\2\2\2R\u011c"+
		"\3\2\2\2T\u011e\3\2\2\2V\u0120\3\2\2\2X\u0122\3\2\2\2Z\u0124\3\2\2\2\\"+
		"\u0126\3\2\2\2^\u0128\3\2\2\2`\u012a\3\2\2\2b\u012c\3\2\2\2d\u012e\3\2"+
		"\2\2fg\5\4\3\2gh\7#\2\2hi\5\66\34\2ij\7#\2\2jk\58\35\2kl\7#\2\2lm\5B\""+
		"\2mn\7#\2\2no\5D#\2op\7#\2\2pq\5F$\2qr\7\2\2\3r\3\3\2\2\2st\5\6\4\2tu"+
		"\7\"\2\2uv\5\b\5\2vw\7\"\2\2wx\5\n\6\2xy\7\"\2\2yz\5\f\7\2z{\7\"\2\2{"+
		"|\5\16\b\2|}\7\"\2\2}~\5\20\t\2~\177\7\"\2\2\177\u0080\5\22\n\2\u0080"+
		"\u0081\7\"\2\2\u0081\u0082\5\24\13\2\u0082\5\3\2\2\2\u0083\u0084\5\26"+
		"\f\2\u0084\7\3\2\2\2\u0085\u0086\5\26\f\2\u0086\t\3\2\2\2\u0087\u0088"+
		"\5\26\f\2\u0088\13\3\2\2\2\u0089\u008a\5\26\f\2\u008a\r\3\2\2\2\u008b"+
		"\u008c\5\26\f\2\u008c\17\3\2\2\2\u008d\u008e\5\26\f\2\u008e\21\3\2\2\2"+
		"\u008f\u0090\5\26\f\2\u0090\23\3\2\2\2\u0091\u0092\5\26\f\2\u0092\25\3"+
		"\2\2\2\u0093\u00a9\7\37\2\2\u0094\u0095\5d\63\2\u0095\u0096\5\30\r\2\u0096"+
		"\u0098\3\2\2\2\u0097\u0094\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u0097\3\2"+
		"\2\2\u0099\u009a\3\2\2\2\u009a\u009c\3\2\2\2\u009b\u009d\5d\63\2\u009c"+
		"\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u00a9\3\2\2\2\u009e\u009f\5\30"+
		"\r\2\u009f\u00a0\5d\63\2\u00a0\u00a2\3\2\2\2\u00a1\u009e\3\2\2\2\u00a2"+
		"\u00a3\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a6\3\2"+
		"\2\2\u00a5\u00a7\5\30\r\2\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00a9\3\2\2\2\u00a8\u0093\3\2\2\2\u00a8\u0097\3\2\2\2\u00a8\u00a1\3\2"+
		"\2\2\u00a9\27\3\2\2\2\u00aa\u00ad\5\32\16\2\u00ab\u00ad\5\34\17\2\u00ac"+
		"\u00aa\3\2\2\2\u00ac\u00ab\3\2\2\2\u00ad\31\3\2\2\2\u00ae\u00b5\5\36\20"+
		"\2\u00af\u00b5\5 \21\2\u00b0\u00b5\5\"\22\2\u00b1\u00b5\5$\23\2\u00b2"+
		"\u00b5\5&\24\2\u00b3\u00b5\5(\25\2\u00b4\u00ae\3\2\2\2\u00b4\u00af\3\2"+
		"\2\2\u00b4\u00b0\3\2\2\2\u00b4\u00b1\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4"+
		"\u00b3\3\2\2\2\u00b5\33\3\2\2\2\u00b6\u00bd\5*\26\2\u00b7\u00bd\5,\27"+
		"\2\u00b8\u00bd\5.\30\2\u00b9\u00bd\5\60\31\2\u00ba\u00bd\5\62\32\2\u00bb"+
		"\u00bd\5\64\33\2\u00bc\u00b6\3\2\2\2\u00bc\u00b7\3\2\2\2\u00bc\u00b8\3"+
		"\2\2\2\u00bc\u00b9\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bb\3\2\2\2\u00bd"+
		"\35\3\2\2\2\u00be\u00bf\7\13\2\2\u00bf\37\3\2\2\2\u00c0\u00c1\7\16\2\2"+
		"\u00c1!\3\2\2\2\u00c2\u00c3\7\17\2\2\u00c3#\3\2\2\2\u00c4\u00c5\7\4\2"+
		"\2\u00c5%\3\2\2\2\u00c6\u00c7\7\f\2\2\u00c7\'\3\2\2\2\u00c8\u00c9\7\r"+
		"\2\2\u00c9)\3\2\2\2\u00ca\u00cb\7\22\2\2\u00cb+\3\2\2\2\u00cc\u00cd\7"+
		"\25\2\2\u00cd-\3\2\2\2\u00ce\u00cf\7\26\2\2\u00cf/\3\2\2\2\u00d0\u00d1"+
		"\7\21\2\2\u00d1\61\3\2\2\2\u00d2\u00d3\7\23\2\2\u00d3\63\3\2\2\2\u00d4"+
		"\u00d5\7\24\2\2\u00d5\65\3\2\2\2\u00d6\u00d9\5^\60\2\u00d7\u00d9\5\\/"+
		"\2\u00d8\u00d6\3\2\2\2\u00d8\u00d7\3\2\2\2\u00d9\67\3\2\2\2\u00da\u00f1"+
		"\5Z.\2\u00db\u00f1\5:\36\2\u00dc\u00dd\5:\36\2\u00dd\u00de\5<\37\2\u00de"+
		"\u00f1\3\2\2\2\u00df\u00e0\5:\36\2\u00e0\u00e1\5<\37\2\u00e1\u00e2\5>"+
		" \2\u00e2\u00f1\3\2\2\2\u00e3\u00e4\5:\36\2\u00e4\u00e5\5<\37\2\u00e5"+
		"\u00e6\5> \2\u00e6\u00e7\5@!\2\u00e7\u00f1\3\2\2\2\u00e8\u00e9\5<\37\2"+
		"\u00e9\u00ea\5> \2\u00ea\u00eb\5@!\2\u00eb\u00f1\3\2\2\2\u00ec\u00ed\5"+
		"> \2\u00ed\u00ee\5@!\2\u00ee\u00f1\3\2\2\2\u00ef\u00f1\5@!\2\u00f0\u00da"+
		"\3\2\2\2\u00f0\u00db\3\2\2\2\u00f0\u00dc\3\2\2\2\u00f0\u00df\3\2\2\2\u00f0"+
		"\u00e3\3\2\2\2\u00f0\u00e8\3\2\2\2\u00f0\u00ec\3\2\2\2\u00f0\u00ef\3\2"+
		"\2\2\u00f19\3\2\2\2\u00f2\u00f3\7\22\2\2\u00f3;\3\2\2\2\u00f4\u00f5\7"+
		"\25\2\2\u00f5=\3\2\2\2\u00f6\u00f7\7\13\2\2\u00f7?\3\2\2\2\u00f8\u00f9"+
		"\7\16\2\2\u00f9A\3\2\2\2\u00fa\u0101\5Z.\2\u00fb\u00fe\5H%\2\u00fc\u00ff"+
		"\5N(\2\u00fd\u00ff\5T+\2\u00fe\u00fc\3\2\2\2\u00fe\u00fd\3\2\2\2\u00ff"+
		"\u0101\3\2\2\2\u0100\u00fa\3\2\2\2\u0100\u00fb\3\2\2\2\u0101C\3\2\2\2"+
		"\u0102\u0108\5`\61\2\u0103\u0108\t\2\2\2\u0104\u0108\5`\61\2\u0105\u0106"+
		"\7\34\2\2\u0106\u0108\7\27\2\2\u0107\u0102\3\2\2\2\u0107\u0103\3\2\2\2"+
		"\u0107\u0104\3\2\2\2\u0107\u0105\3\2\2\2\u0108E\3\2\2\2\u0109\u0111\5"+
		"`\61\2\u010a\u0111\5b\62\2\u010b\u010d\5`\61\2\u010c\u010b\3\2\2\2\u010d"+
		"\u010e\3\2\2\2\u010e\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0111\3\2"+
		"\2\2\u0110\u0109\3\2\2\2\u0110\u010a\3\2\2\2\u0110\u010c\3\2\2\2\u0111"+
		"G\3\2\2\2\u0112\u0113\t\3\2\2\u0113I\3\2\2\2\u0114\u0115\7\30\2\2\u0115"+
		"K\3\2\2\2\u0116\u0117\7\31\2\2\u0117M\3\2\2\2\u0118\u0119\7\32\2\2\u0119"+
		"O\3\2\2\2\u011a\u011b\7\33\2\2\u011bQ\3\2\2\2\u011c\u011d\7\34\2\2\u011d"+
		"S\3\2\2\2\u011e\u011f\7\35\2\2\u011fU\3\2\2\2\u0120\u0121\7\36\2\2\u0121"+
		"W\3\2\2\2\u0122\u0123\7\37\2\2\u0123Y\3\2\2\2\u0124\u0125\7!\2\2\u0125"+
		"[\3\2\2\2\u0126\u0127\7\4\2\2\u0127]\3\2\2\2\u0128\u0129\7\20\2\2\u0129"+
		"_\3\2\2\2\u012a\u012b\t\4\2\2\u012ba\3\2\2\2\u012c\u012d\t\5\2\2\u012d"+
		"c\3\2\2\2\u012e\u012f\t\6\2\2\u012fe\3\2\2\2\21\u0099\u009c\u00a3\u00a6"+
		"\u00a8\u00ac\u00b4\u00bc\u00d8\u00f0\u00fe\u0100\u0107\u010e\u0110";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}