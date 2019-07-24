// Copyright (C) 2019 Welyab da Silva Paula
// 
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License. You may obtain a copy of
// the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// License for the specific language governing permissions and limitations under
// the License.

// This grammar is based on document present at:
// https://www.chessprogramming.org/Forsyth-Edwards_Notation

// Author: Welyba Paula

grammar FEN;

fen
	: piecePlacement         Space 
	  sideToMove             Space 
	  castlingAbility        Space 
	  enPassantTargetSquare   
	  (Space moveCounters)?  EOF
	;

piecePlacement
	: pieceDisposition Slash // rank 8
	  pieceDisposition Slash // rank 7
	  pieceDisposition Slash // rank 6
	  pieceDisposition Slash // rank 5
	  pieceDisposition Slash // rank 4
	  pieceDisposition Slash // rank 3
	  pieceDisposition Slash // rank 2
	  pieceDisposition       // rank 1
	;

pieceDisposition
	: Eight
	| piece+
	| (piece+ digitOneToSeven)+ piece*
	| (digitOneToSeven piece+)+ digitOneToNine?
	;

piece
	: blackPiece
	| whitePiece
	;

blackPiece
	: blackKing
	| blackQueen
	| blackRook
	| blackBishop
	| blackKnight
	| blackPawn
	;
whitePiece
	: whiteKing
	| whiteQueen
	| whiteRook
	| whiteBishop
	| whiteKnight
	| whitePawn
	;

blackKing:   LowerLetterK;
blackQueen:  LowerLetterQ;
blackRook:   LowerLetterR;
blackBishop: LowerLetterB;
blackKnight: LowerLetterN;
blackPawn:   LowerLetterP;
whiteKing:   UpperLetterK;
whiteQueen:  UpperLetterQ;
whiteRook:   UpperLetterR;
whiteBishop: UpperLetterB;
whiteKnight: UpperLetterN;
whitePawn:   UpperLetterP;

sideToMove
	: black
	| white
	;

castlingAbility
	: none
	| whiteKingSideCastling  whiteQueenSideCastling? blackKingSideCastling? blackQueenSideCastling?
	| whiteKingSideCastling? whiteQueenSideCastling  blackKingSideCastling? blackQueenSideCastling?
	| whiteKingSideCastling? whiteQueenSideCastling? blackKingSideCastling  blackQueenSideCastling?
	| whiteKingSideCastling? whiteQueenSideCastling? blackKingSideCastling? blackQueenSideCastling
	;

whiteKingSideCastling:  UpperLetterK;
whiteQueenSideCastling: UpperLetterQ;
blackKingSideCastling:  LowerLetterK;
blackQueenSideCastling: LowerLetterQ;

enPassantTargetSquare
	: none
	| file (rank3 | rank6)
	;

moveCounters
	: halfMoveClock Space fullMoveCounter
	;

halfMoveClock
	: digitZeroToNine
	| digitOneToNine digitZeroToNine
	| One Zero Zero
	;

fullMoveCounter
	: digitOneToNine digitZeroToNine*
	;

file
	: LowerLetterA
	| LowerLetterB
	| LowerLetterC
	| LowerLetterD
	| LowerLetterE
	| LowerLetterF
	| LowerLetterG
	| LowerLetterH
	;

rank3: Three;
rank6: Six;

none: Hyphen;

white: LowerLetterB;
black: LowerLetterW;

digitOneToFour
	: One
	| Two
	| Three
	| Four
	;

digitZeroToNine
	: Zero
	| One
	| Two
	| Three
	| Four
	| Five
	| Six
	| Seven
	| Eight
	| Nine
	;

digitOneToNine
	: One
	| Two
	| Three
	| Four
	| Five
	| Six
	| Seven
	| Eight
	| Nine
	;

digitOneToSeven
	: One
	| Two
	| Three
	| Four
	| Five
	| Six
	| Seven
	;

LowerLetterA: 'a';
LowerLetterB: 'b';
LowerLetterC: 'c';
LowerLetterD: 'd';
LowerLetterE: 'e';
LowerLetterF: 'f';
LowerLetterG: 'g';
LowerLetterH: 'h';
LowerLetterK: 'k';
LowerLetterN: 'n';
LowerLetterP: 'p';
LowerLetterQ: 'q';
LowerLetterR: 'r';
LowerLetterW: 'w';

UpperLetterB: 'B';
UpperLetterK: 'K';
UpperLetterN: 'N';
UpperLetterP: 'P';
UpperLetterQ: 'Q';
UpperLetterR: 'R';

Zero:  '0';
One:   '1';
Two:   '2';
Three: '3';
Four:  '4';
Five:  '5';
Six:   '6';
Seven: '7';
Eight: '8';
Nine:  '9';

Hyphen: '-';

Slash: '/';

Space: ' ';
