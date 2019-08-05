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

// Author: Welyba Paula

grammar PGN;

pgn: game+;

game
	: tagsArea NEWLINE
	  NEWLINE
	  movementsLis
	;

tagsArea
	: requiredTags (NEWLINE 
	  optionalTags)?
	;

requiredTags
	: eventTag  NEWLINE
	  siteTag   NEWLINE
	  dateTag   NEWLINE
	  roudTag   NEWLINE
	  whiteTag  NEWLINE
	  blackTag  NEWLINE
	  resultTag
	;

// =================================================================================
// Event tag
// =================================================================================
eventTag
	: LeftBracket EventTagName Space Quote eventTagValue Quote RightBracket
	;
	
eventTagValue
	: eventName
	| unknowEventName
	;
	
unknowEventName
	: QuestionMark
	;
	
eventName
	: SimpleString
	;

eventNameUnknow
	:
	;

// =================================================================================
// Site tag
// =================================================================================
siteTag
	: LeftBracket SiteTagName Space Quote siteTagValue Quote RightBracket
	;

siteTagValue
	: siteName
	| unknowSiteName
	;

siteName
	: SimpleString
	;

unknowSiteName
	: QuestionMark
	;

// =================================================================================
// Date tag
// =================================================================================
dateTag
	: LeftBracket DateTagName Space Quote gameDateValue Quote RightBracket
	;

gameDateValue
	: gameDateYearValue Dot gameDateMonthValue Dot gameDateDayValue
	;

gameDateYearValue
	: gameDateYear
	| unknowGameDateYear
	;

gameDateYear
	: Year
	;
	
unknowGameDateYear
	: '????'
	;
	
gameDateMonthValue
	: gameDateMonth
	| unknowGameDateMonth
	;

gameDateMonth
	: unknowGameDateMonth
	| gameDateDayValue
	;
	
unknowGameDateMonth
	: '??'
	;
	
gameDateDayValue
	: gameDateDay
	| unknowGameDateDay
	;

gameDateDay
	: DayOfMonth
	;
	
unknowGameDateDay
	: '??'
	;

// =================================================================================
// Round tag
// =================================================================================
roudTag
	: LeftBracket RoundTagName Space Quote roundTagValue Quote RightBracket
	;

roundTagValue
	: roundInfo
	| roundInfoNotApplicable
	| roundInfoUnknow
	;

roundInfo
	: SimpleString
	;

roundInfoNotApplicable
	: Hyphen
	;

roundInfoUnknow
	: QuestionMark
	;

// =================================================================================
// White tag
// =================================================================================
whiteTag
	: LeftBracket WhiteTagName Space Quote whiteTagValue Quote RightBracket
	;

whiteTagValue
	: whitePlayerName
	| unknowWhitePlayerName
	;

whitePlayerName
	: SimpleString
	;
	
unknowWhitePlayerName
	: QuestionMark
	;

// =================================================================================
// Black tag
// =================================================================================
blackTag
	: LeftBracket BlackTagName Space Quote blackTagValue Quote RightBracket
	;

blackTagValue
	: blackPlayerName
	| unknowBlackPlayerName
	;

blackPlayerName
	: SimpleString
	;
	
unknowBlackPlayerName
	: QuestionMark
	;

// =================================================================================
// Black tag
// =================================================================================
resultTag
	: LeftBracket ResultTagName Space Quote resultTagValue Quote RightBracket
	;

resultTagValue
	: resultTagWhiteWin
	| resultTagBlackWin
	| resultTagDraw
	| resultTagUnknow
	;

resultTagWhiteWin: WhiteWinGameResult;
resultTagBlackWin: BlackWinGameResult;
resultTagDraw:     DrawGameResult;
resultTagUnknow:   UnknowGameResult;
	
optionalTags
	: optionalTag 
	  (NEWLINE optionalTag)*
	;
	
optionalTag
	: LeftBracket optionalTagName Quote optionalTagValue Quote RightBracket
	;

optionalTagName
	: SimpleString
	;

optionalTagValue
	: SimpleString?
	;

movementsLis
	: 'a'
	;

WhiteWinGameResult: '1-0';
BlackWinGameResult: '0-1';
DrawGameResult:     '1/2-1/2';
UnknowGameResult:   '*';

Year
	: Digit Digit Digit Digit
	;

Month
	: Digit Digit
	;
	
DayOfMonth
	: Digit Digit
	;
	
Digit
	: '0'
	| '1'
	| '2'
	| '3'
	| '4'
	| '5'
	| '6'
	| '7'
	| '8'
	| '9'
	;

EventTagName:  'Event';
SiteTagName:   'Site';
DateTagName:   'Date';
RoundTagName:  'Round';
WhiteTagName:  'White';
BlackTagName:  'Black';
ResultTagName: 'Result';

SimpleString: ~[\r\n"]+;

Quote: '"';

Space: ' ';

Dot: '.';

Hyphen: '-';

QuestionMark: '?';

LeftBracket:  '[';
RightBracket: ']';

NEWLINE: '\r'? '\n';
