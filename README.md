# AN.JA.BA.CH.EN
## Another Java Based Chess Engine

This is a hobbyist chess software completely programmed in Java.

### Features

- [x] FEN string parsing
- [x] FEN string generation
- [x] Movement generation
- [x] Movement metadata information
- [x] PERFT calculation
- [x] Movement path enumeration
- [ ] PGN file reader (in development)
- [ ] PGN file writer (in development)
- [ ] Text based board export
- [ ] Board image generation (png, jpeg...) (in development)
- [ ] Checkmate finder in N moves (in development)
- [ ] Game engine (in development)
- [ ] Chess960 compatible game engine (in development)
- [ ] Universal Chess Interface (UCI) support (in development)

### Forsyth-Edwards Notation - FEN

FEN is a pattern used to describe a specific chess position.
It is widely used solution to share game positions, chess puzzles, etc.
You can know more by reading the related article in the Chess Programming Wiki: 
https://www.chessprogramming.org/Forsyth-Edwards_Notation.

#### Parsing FEN strings

AN.JA.BA.CH.EN can extract piece disposition by reading a FEN string:

```java
var parser = new FenParser("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
var pieceList = parser.getLocalizedPieces();
var positionInfo = parser.getFenPositionInfo();
```

Chess boards may be created directly from FEN strings:

```java
var board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
```

#### Generating FEN string

The mainly way to create FEN string in AN.JA.BA.CH.EN is extract it from a board object.
During game playing, an extracted FEN will reflect the current state of the board
with its piece disposition, castling ability flags, move couter, etc.

```java
var board = new Board();
var fen = board.getFen();
System.out.println(fen);
```

Will produce the FEN string, that is the representation for the usual chess in the initial positions:

```text
rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
```

### Movement generation

### Text based board output

```java
var board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
System.out.println(board.toString());
```

Will produce the follow output:

```text
┌───┬───┬───┬───┬───┬───┬───┬───┐
│   │   │   │   │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┤
│   │   │ p │   │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┤
│   │   │   │ p │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┤
│ K │ P │   │   │   │   │   │ r │
├───┼───┼───┼───┼───┼───┼───┼───┤
│   │ R │   │   │   │ p │   │ k │
├───┼───┼───┼───┼───┼───┼───┼───┤
│   │   │   │   │   │   │   │   │
├───┼───┼───┼───┼───┼───┼───┼───┤
│   │   │   │   │ P │   │ P │   │
├───┼───┼───┼───┼───┼───┼───┼───┤
│   │   │   │   │   │   │   │   │
└───┴───┴───┴───┴───┴───┴───┴───┘
```

### Performing perft calculation

```java
var results = PerftCalculator.perft("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -", 4, true);
results.printTable();
```

Will produce the follow output:

```text
+-------+--------+----------+------+---------+------------+--------+------------+------------+------------+
| Depth |  Nodes | Captures | e.p. | castles | promotions | checks | dis checks | dou checks | checkmates |
+-------+--------+----------+------+---------+------------+--------+------------+------------+------------+
|     1 |     48 |        8 |    0 |       2 |          0 |      0 |         0 |           0 |          0 |
|     2 |   2039 |      351 |    1 |      91 |          0 |      3 |         0 |           0 |          0 |
|     3 |  97862 |    17102 |   45 |    3162 |          0 |    993 |         0 |           0 |          1 |
|     4 | 4085603|   757163 | 1929 |  128013 |      15172 |  25523 |        42 |           6 |         43 |
+-------+--------+----------+------+---------+------------+--------+------------+------------+------------+
```

---

My https://lichess.org user: [welyab](https://lichess.org/@/welyab)
