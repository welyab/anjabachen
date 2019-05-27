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
// http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm

grammar pgn;

games
	: game (NewLine NewLine game)*
	;

game
	: tags (NewLine NewLine gameMovements)?
	;
	
tags
	: requiredTags (NewLine optionalTags)*
	;
	
requiredTags
	: eventTag NewLine
      siteTag NewLine
      dateTag NewLine
      roundTag NewLine
      whiteTag NewLine
      blackTag NewLine
      resultTag
	;
	
eventTag
	: LeftBracket EventTagName Space QuotedString RightBracket
	;
	
siteTag
	: LeftBracket SiteTagName Space QuotedString RightBracket
	;
	
dateTag
	: LeftBracket DateTagName Space DateTagValue RightBracket
	;
	
roundTag
	: LeftBracket RoundTagName Space QuotedString RightBracket
	;
	
whiteTag
	: LeftBracket WhiteTagName Space QuotedString RightBracket
	;
	
blackTag
	: LeftBracket BlackTagName Space QuotedString RightBracket
	;
	
resultTag
	: LeftBracket ResultTagName Space TagGameResultValue RightBracket
	;

optionalTags
	: LeftBracket  Space QuotedString RightBracket
	;
	
gameMovements
	: '?'
	;

DateYear
	: Digit Digit Digit Digit
	| '????'
	;
	
DateMonth
	: Digit Digit
	| '??'
	;
	
DateDay
	: Digit Digit
	| '??'
	;

DateTagValue
	: DoubleQuote DateYear Dot DateMonth Dot DateDay DoubleQuote
	;

EventTagName: 'Event';
SiteTagName: 'Site';
DateTagName: 'Date';
RoundTagName: 'Round';
WhiteTagName: 'White';
BlackTagName: 'Black';
ResultTagName: 'Result';

WhiteWin: '1-0';
BlackWin: '0-1';
Draw: '1/2-1/2';
NoResult: '*';

GameResult
	: WhiteWin
    | BlackWin
    | Draw
    | NoResult
	;
	
TagGameResultValue
	: DoubleQuote GameResult DoubleQuote
	;
	
String: ~('\n'|'"');

DoubleQuote: '"';

QuotedString: DoubleQuote String? DoubleQuote;

LeftBracket: '[';
	
RightBracket: ']';

Digit: '0'..'9';

Space: ' ';

Dot : '.';

NewLine: '\r'?'\n';
