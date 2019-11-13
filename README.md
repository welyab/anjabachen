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

AN.JA.BA.CH.EN can extract pieces disposition by reading a FEN string:

```java
var parser = new FenParser("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
var pieceList = parser.getLocalizedPieces();
var positionInfo = parser.getFenPositionInfo();
```

Also, chess boards may be created directly from FEN strings:

```java
var board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
```

#### Generating FEN string

The mainly way to create FEN string in AN.JA.BA.CH.EN is extract it from a board object.
During game playing, an extracted FEN will reflect the current state of the board
with its piece disposition, castling ability flags, move counter, etc.

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

All chess playing softwares need a movement generation mechanism.
In AN.JA.BA.CH.EN, all movements algorithms are made inside the class `Board`, that provides a easy to use API. 
However, the algorithms implemented are very simple, based in a bi-dimensional array.

```java
byte[][] grid = new byte[8][8];
```

The `Board` class just swaps array contents in order to make movements.
Something like `grid[a][b] = grid[x][y]`, followed by `grid[x][y] = 0`, move the piece
located in `[x, y]` to the position indicated by `[a, b]`, and than clear the position `[x, y]`
assign `0` to it. All rules for castling, _en passant_ captures, pin, etc, are considered during calculations.

#### Generating movements

```java
// create a board with initial position, white to play
Board board = new Board();
// retrieve all possible movements for white piece
Movements movements = board.getMovements();
```

The method `.getMovements()` retrieves
all movements for the color that has the turn to move. But it is possible to get movements for the other side 
by indicating an specific color: `.getMovements(color)`. When a movement is submitted to the board object, subsequent
call to `.getMovements()` will retrieve movements for the other side color, and so on.
There are some methods to generate the movements for an specific pieces as well.

#### Moving pieces

### PERFT calculation

PERFT is a technic used to help to find bugs in movement generation. A PERFT calculation walk all movement tree
until a certain depth, count the nodes found, and other information, like the positions that are originated
from a movement of capturing, castling movements, _en passant_, etc. This technic also shows how fast
the movement generation is. Currently AN.JA.BA.CH.EN is spent about 18 minutes to generate PERFT results until
depth 5 of the initial position described by this FEN string: `r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -`.
It is slowly, but was it was worse; there are softwares that performs the same calculation in a few seconds. 
Old implements take 95 minutes. There is space to improvements. As said before,
AN.JA.BA.CH.EN uses very simples algorithms to perform movement generation.

In order to use PERFT calculation, just call the method passing the initial FEN position. 

```
PerftResults results = PerftCalculator.perft("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -", 4);
results.asTextTable();
```

... and the results:

```text
Perft Calculation - AN.JA.BA.CH.EN
FEN: r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -
Total time: 17 m 25.991 s
+-------+-----------+----------+-------+-----------+------------+---------+--------------+---------------+------------+------------+
| depth |     nodes | captures |   e.p | castlings | promotions |  checks | disc. checks | double checks | checkmates | stalemates |
+-------+-----------+----------+-------+-----------+------------+---------+--------------+---------------+------------+------------+
|     1 |        48 |        8 |     0 |         2 |          0 |       0 |            0 |             0 |          0 |          0 |
|     2 |      2039 |      351 |     1 |        91 |          0 |       3 |            0 |             0 |          0 |          0 |
|     3 |     97862 |    17102 |    45 |      3162 |          0 |     993 |            0 |             0 |          1 |          0 |
|     4 |   4085603 |   757163 |  1929 |    128013 |      15172 |   25523 |           42 |             6 |         43 |          0 |
|     5 | 193690690 | 35043416 | 73365 |   4993637 |       8392 | 3309875 |        19883 |          2645 |      30171 |          0 |
+-------+-----------+----------+-------+-----------+------------+---------+--------------+---------------+------------+------------+
```



---

My https://lichess.org user: [welyab](https://lichess.org/@/welyab)
