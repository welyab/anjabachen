# AN.JA.BA.CH.EN
## Another Java Based Chess Engine

This is a hobbyist chess software completely programmed in Java.

### Features

- [x] FEN parsing and generation
- [x] Text based board printing
- [ ] PGN parsing and generation (in development)
- [ ] Movement generation 
- [ ] [PERFT](https://www.chessprogramming.org/Perft) calculation
- [ ] Movement path enumeration
- [ ] Find checkmate in x moves (in development)
- [ ] UCI compatible engine (in development)
- [ ] Chess960 compatibility (in development)

### Parsing FEN strings

```java
var parser = new FenParser("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
var pieceList = parser.getLocalizedPieces();
var positionInfo = parser.getFenPositionInfo();
```

Chess boards may be created directly from FEN strings:

```java
var board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
```

### Generating FEN string

```java
var board = new Board();
var fen = board.getFen();
System.out.println(fen);
```

Will produce the follow output:

```text
rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
```

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
