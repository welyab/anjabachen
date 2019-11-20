# AN.JA.BA.CH.EN

This is a hobbyist chess software completely programmed in Java.

### Features

- [x] Movement generation
- [x] FEN string parsing and generation
- [x] PERFT calculation with path enumeration
- [x] Text based board export
- [ ] PGN file reader and writer *
- [ ] Board image generation (png, jpeg...) *
- [ ] Checkmate finder in N moves *
- [ ] Game engine *
- [ ] Chess960 compatible game engine *
- [ ] Universal Chess Interface (UCI) support *
- [ ] GUI software with support for third party UCI compatible engines *

<strong><sup>*</sup></strong> <small style="color: Gray">features under developement</small>

### Movement generation

All chess playing softwares need a movement generation mechanism. In AN.JA.BA.CH.EN, all movement algorithms are executed inside the `Board` class with some help of the `MovementUtil` class. However, the algorithms implemented are very simple and trivial, based in a bi-dimensional array.

```java
byte[][] grid = new byte[8][8];
```
So, to move a piece from a square to another, internally the board may simply perform the follow steps:

```java
grid[a][b] = grid[x][y]
grid[x][y] = 0
```
The above snippet  transfers the piece located in the square `(x, y)` to the square located in `(a, b)`, following by assign `zero` to the square `(x, y)`. The `Board` class has lots and lots of code like that in order to execute piece movement.

The [Chess Programming Wiki (CPW)](https://www.chessprogramming.org) has a article about movement generation that fits as great start point for other technics of piece movement in chess software: [https://www.chessprogramming.org/Move_Generation](https://www.chessprogramming.org/Move_Generation)

#### Generating movements
In AN.JA.BA.CH.EN, get the movements is so simple as call a method. The `Board` class has some routines with this purpose:

```java
Movements getMovements(position)
Movements getMovements(color)
// other methods...
```
When the side color is informed, only movements for the specific color are considered. Otherwise, the movements are generated to the side color that has the turn to make the next movement. When a move is submitted to the board, it changes its state, of course, and a subsequent call to a movement generation method will consider the current state, and the side with turn to move, and so on.
```java
// create a board with initial position, white to play
Board board = new Board();

// retrieve all possible movements for white pieces
Movements movements = board.getMovements();
```
The `Movements` class is just a container of movements. It stores all generated piece movements with origin squares and target squares information.
#### Movement metadata information
Each movement in AN.JA.BA.CH.EN has some information linked to it:
- **capture** - the movement is capturing
- ***en passant*** - the movement is a capturing with pawn *en passant*
- **castling** - the movement is a castling
- **promotion** - the movement is a pawn promotion
- **check** - the movement is a check to the opposite color king
- **discovery check** - the movement is discovery check to the opposite color king
- **double check** - the movement is a double check to the opposite color king
- **checkmate** - the movement will finish the game with checkmate
- **stalemate** - the movement will finish the game with a draw

All these information is encoded as flags in the structure that holds the movement destination: `MovementTarget`:

```java
// creates a board with initial piece disposition, white to play
Board board = new Board();

// from the board, retrieve a random movement
Movement movement = board.getMovementRandom();

// retrieves the destination square of the movement
MovementTarget target = movement.getTarget();

// get the movement flags
short flags = target.getFlags();

// how to evaluate if flags variable is marked
// MovementUtil.isCapture(flags)
// MovementUtil.isEnPassant(flags)
// MovementUtil.isCastling(flags)
// MovementUtil.isPromotion(flags)
// MovementUtil.isCheck(flags)
// MovementUtil.isDiscoveryCheck(flags)
// MovementUtil.isDoubleCheck(flags)
// MovementUtil.isCheckmate(flags)
// MovementUtil.isStalemate(flags)
```

#### Moving pieces

To move a piece, the instruction is submitted to the board by saying the origin and target square of the movement. Some validations are executed and if the movement is valid, the board performs the operation.

```java
Board board = new Board();

// move the white king's pawn from square a2 to the square a4
board.move(Position.E2, Position.E4);
```
It is possible undo a movement:

```java
board.undo();
```
An undone movement can be redone:

```java
board.redo();
```

### Forsyth-Edwards Notation - FEN

It is widely used solution to share game positions, chess puzzles, etc. You can know more abount FEN by reading the related article in the CPW: https://www.chessprogramming.org/Forsyth-Edwards_Notation.

#### Parsing FEN strings

AN.JA.BA.CH.EN can extract pieces disposition by reading a FEN string:

```java
FenParser parser = new FenParser("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
List<LocalizedPiece> pieceList = parser.getLocalizedPieces();
FenPositionInfo positionInfo = parser.getFenPositionInfo();
```

It is possible to create chess boards directly from FEN strings:

```java
var board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
```

#### Generating FEN string

The main way to create FEN string is extract it from a board object. During game playing, an extracted FEN will reflect the current state of the board with its piece disposition, castling ability flags, move counter, etc.

```java
Board board = new Board();
String fen = board.getFen();
System.out.println(fen);
```
With follow output:

```text
rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
```
After a movement, the FEN string will change in order to reflect the current board state:
```java
Board board = new Board();

// the Ruy Lopez opening
board.move(Position.E2, Position.E4);
board.move(Position.E7, Position.E5);
board.move(Position.G1, Position.F3);
board.move(Position.B8, Position.C6);
board.move(Position.F1, Position.B5);

String fen = board.getFen();
System.out.println(fen);
```
The FEN string after Ruy Lopez opening:

```text
r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3
```

### PERFT calculation

PERFT is a technic used to help to find bugs in movement generation. A PERFT calculation walk all movement tree until a certain depth, count the nodes found, and other information, like the positions that are originated
 the movement generation is. Currently AN.JA.BA.CH.EN is spent about 18 (with Intel Core i7) minutes to generate PERFT results until depth 5 of the initial position described by this FEN string: `r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -`.
It is very, very slowly, but it was worse. Old implementations  of  AN.JA.BA.CH.EN took about 95 minutes to generate the same results. There are softwares that performs the same calculations in just a few seconds. Perhaps in the future implementations will be changed to improve performance.

With a generated PERFT result, it is possible to compare with results provided by other programs. If some differ occurs, then with have bug. Also, there are many PERFT results in the internet. The CPW has a article with some great chess positions and the respective PERFT calcualtions: [https://www.chessprogramming.org/Perft_Results](https://www.chessprogramming.org/Perft_Results).

In order to use PERFT calculation AN.JA.BA.CH.EN, just call the method passing the initial FEN position. 

```java
PerftResults results = PerftCalculator.perft("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -", 5);
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
Other PERFT calculation:

```text
Perft Calculation - AN.JA.BA.CH.EN
FEN: 8/2Q5/8/8/8/8/5K2/7k w - -
Total time: 17.358 s
+-------+----------+----------+-----+-----------+------------+--------+--------------+---------------+------------+------------+
| depth |    nodes | captures | e.p | castlings | promotions | checks | disc. checks | double checks | checkmates | stalemates |
+-------+----------+----------+-----+-----------+------------+--------+--------------+---------------+------------+------------+
|     1 |       29 |        0 |   0 |         0 |          0 |      5 |            0 |             0 |          1 |          6 |
|     2 |       25 |        1 |   0 |         0 |          0 |      0 |            0 |             0 |          0 |          0 |
|     3 |      663 |        0 |   0 |         0 |          0 |    118 |            4 |             0 |         25 |          6 |
|     4 |     1539 |       16 |   0 |         0 |          0 |      0 |            0 |             0 |          0 |          0 |
|     5 |    42562 |        0 |   0 |         0 |          0 |   8533 |          151 |             0 |        733 |       1396 |
|     6 |   111912 |      977 |   0 |         0 |          0 |      0 |            0 |             0 |          0 |          0 |
|     7 |  3084483 |        0 |   0 |         0 |          0 | 652565 |        16370 |             0 |      19722 |      21213 |
|     8 | 10810922 |   116800 |   0 |         0 |          0 |      0 |            0 |             0 |          0 |          0 |
+-------+----------+----------+-----+-----------+------------+--------+--------------+---------------+------------+------------+
```
#### Movement path enumeration

Enumerate the path is a feature of PERFT calculator that show all paths for each final board positions until reach certain depth.

```java
// the first parameter is the maximum depth
// the second parameter is the length of the movement path enumeration
// so, the configuration will output all possible movements only for the first movement
PerftCalculator.divide("8/2Q5/8/8/8/8/5K2/7k w - -", 1, 1);
```
Will output:

```
c7b8 
c7c8 
...  25 other movements ...
f2f1 
f2e2 
```
Another exemplo:

```java
// the  divide configuration in this call will walk the tree
// until the depth 6, showning movement path until the depth 3
// from depth 3 to the depth 6, the divide function counts the total possible positions
PerftCalculator.divide("8/2Q5/8/8/8/8/5K2/7k w - -", 6, 3);
```
And the output:

```text
c7c8 h1h2 c8b7 73
c7c8 h1h2 c8a6 107
... 599 other movements ...
f2e2 h1g1 e2e1 173
f2e2 h1g1 e2d2 436
```
As described, the above is the movement path enumeration until depth 3, showing the total possible positions  until reach the depth 6.

---

My https://lichess.org user: [welyab](https://lichess.org/@/welyab)
