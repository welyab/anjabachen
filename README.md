# AN.JA.BA.CH.EN
## Another Java Based Chess Engine

This is a hobbyist chess engine completely programmed in Java.

### Features

* FEN Parser and producer
* Text based board printing

### Parsing FEN strings

```java
var fenParser = new FenParser("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 b - -");
var board fenParser.getBoard();
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
