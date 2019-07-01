// Generated from /home/welyab/sources/chess/src/main/antlr4/abacate.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class fenLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"LowerLetterA", "LowerLetterB", "LowerLetterC", "LowerLetterD", "LowerLetterE", 
		"LowerLetterF", "LowerLetterG", "LowerLetterH", "LowerLetterK", "LowerLetterN", 
		"LowerLetterP", "LowerLetterQ", "LowerLetterR", "LowerLetterW", "UpperLetterB", 
		"UpperLetterK", "UpperLetterN", "UpperLetterP", "UpperLetterQ", "UpperLetterR", 
		"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", 
		"Nine", "Hyphen", "Slash", "Space"
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


	public fenLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "abacate.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2#\u0087\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3"+
		"\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3 \3 \3!\3!\3\"\3\"\2\2#\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30"+
		"/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#\3\2\2\2\u0086\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\3E\3\2\2\2\5G\3\2\2\2\7I\3\2\2"+
		"\2\tK\3\2\2\2\13M\3\2\2\2\rO\3\2\2\2\17Q\3\2\2\2\21S\3\2\2\2\23U\3\2\2"+
		"\2\25W\3\2\2\2\27Y\3\2\2\2\31[\3\2\2\2\33]\3\2\2\2\35_\3\2\2\2\37a\3\2"+
		"\2\2!c\3\2\2\2#e\3\2\2\2%g\3\2\2\2\'i\3\2\2\2)k\3\2\2\2+m\3\2\2\2-o\3"+
		"\2\2\2/q\3\2\2\2\61s\3\2\2\2\63u\3\2\2\2\65w\3\2\2\2\67y\3\2\2\29{\3\2"+
		"\2\2;}\3\2\2\2=\177\3\2\2\2?\u0081\3\2\2\2A\u0083\3\2\2\2C\u0085\3\2\2"+
		"\2EF\7c\2\2F\4\3\2\2\2GH\7d\2\2H\6\3\2\2\2IJ\7e\2\2J\b\3\2\2\2KL\7f\2"+
		"\2L\n\3\2\2\2MN\7g\2\2N\f\3\2\2\2OP\7h\2\2P\16\3\2\2\2QR\7i\2\2R\20\3"+
		"\2\2\2ST\7j\2\2T\22\3\2\2\2UV\7m\2\2V\24\3\2\2\2WX\7p\2\2X\26\3\2\2\2"+
		"YZ\7r\2\2Z\30\3\2\2\2[\\\7s\2\2\\\32\3\2\2\2]^\7t\2\2^\34\3\2\2\2_`\7"+
		"y\2\2`\36\3\2\2\2ab\7D\2\2b \3\2\2\2cd\7M\2\2d\"\3\2\2\2ef\7P\2\2f$\3"+
		"\2\2\2gh\7R\2\2h&\3\2\2\2ij\7S\2\2j(\3\2\2\2kl\7T\2\2l*\3\2\2\2mn\7\62"+
		"\2\2n,\3\2\2\2op\7\63\2\2p.\3\2\2\2qr\7\64\2\2r\60\3\2\2\2st\7\65\2\2"+
		"t\62\3\2\2\2uv\7\66\2\2v\64\3\2\2\2wx\7\67\2\2x\66\3\2\2\2yz\78\2\2z8"+
		"\3\2\2\2{|\79\2\2|:\3\2\2\2}~\7:\2\2~<\3\2\2\2\177\u0080\7;\2\2\u0080"+
		">\3\2\2\2\u0081\u0082\7/\2\2\u0082@\3\2\2\2\u0083\u0084\7\61\2\2\u0084"+
		"B\3\2\2\2\u0085\u0086\7\"\2\2\u0086D\3\2\2\2\3\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}