# AN.JA.BA.CH.EN
## Another Java Based Chess Engine

This is a hobbyist chess engine completely programmed in Java.

It is in development. The list of available features is the follow:

### Features

* FEN parsing and generation
* Text based board printing
* Movement generation (in development)
* PGN parsing and generation (in development)
* Engine (in development)

### Parsing FEN strings

```java
var parser = new FenParser("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
var pieceList = parser.getLocalizedPieces();
var positionInfo = parser.getFenPositionInfo();
```

It's possible to create chess boards directly from FEN strings:

```java
var board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
```

### Generating FEN string

```java
var board = new Board();
var fen = board.getFen();
```

### Text based board output

```java
var board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
System.out.println(board.toString());
```

Will produce the follow output

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

---

My https://lichess.org user: [welyab](https://lichess.org/@/welyab)
