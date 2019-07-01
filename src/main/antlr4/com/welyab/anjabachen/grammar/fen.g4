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

grammar fen;

fen
	: piecePlacement        Space 
	  sideToMove            Space 
	  castlingAbility       Space 
	  enPassantTargetSquare Space 
	  halfMoveClock         Space 
	  fullMoveCounter       EOF
	;

piecePlacement
	: dispositionRank1 Slash
	  dispositionRank2 Slash
	  dispositionRank3 Slash
	  dispositionRank4 Slash
	  dispositionRank5 Slash
	  dispositionRank6 Slash
	  dispositionRank7 Slash
	  dispositionRank8
	;

dispositionRank1
	: pieceDisposition
	;
dispositionRank2
	: pieceDisposition
	;
dispositionRank3
	: pieceDisposition
	;
dispositionRank4
	: pieceDisposition
	;
dispositionRank5
	: pieceDisposition
	;
dispositionRank6
	: pieceDisposition
	;
dispositionRank7
	: pieceDisposition
	;
dispositionRank8
	: pieceDisposition
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

blackKing: LowerLetterK;
blackQueen: LowerLetterQ;
blackRook: LowerLetterR;
blackBishop: LowerLetterB;
blackKnight: LowerLetterN;
blackPawn: LowerLetterP;
whiteKing:  UpperLetterK;
whiteQueen: UpperLetterQ;
whiteRook:  UpperLetterR;
whiteBishop: UpperLetterB;
whiteKnight: UpperLetterN;
whitePawn: UpperLetterP;

sideToMove
	: black
	| white
	;

castlingAbility
	: none
	| whiteKingSideCastling
	| whiteKingSideCastling whiteQueenSideCastling
	| whiteKingSideCastling whiteQueenSideCastling blackKingSideCastling
	| whiteKingSideCastling whiteQueenSideCastling blackKingSideCastling blackQueenSideCastling
	|                       whiteQueenSideCastling blackKingSideCastling blackQueenSideCastling
  |                                              blackKingSideCastling blackQueenSideCastling
	|                                                                    blackQueenSideCastling
	;

whiteKingSideCastling: UpperLetterK;
whiteQueenSideCastling: UpperLetterQ;
blackKingSideCastling: LowerLetterK;
blackQueenSideCastling: LowerLetterQ;

enPassantTargetSquare
	: none
	| file (rank3 | rank6)
	;

halfMoveClock
	: digitZeroToNine
	| digitOneToFour | digitZeroToNine
	| Five Zero
	;

fullMoveCounter
	: digitZeroToNine
	| digitOneToNine | digitZeroToNine+
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

rank1: One;
rank2: Two;
rank3: Three;
rank4: Four;
rank5: Five;
rank6: Six;
rank7: Seven;
rank9: Eight;

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
