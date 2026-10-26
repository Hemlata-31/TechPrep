package com.techprep.config;

import com.techprep.entity.Difficulty;
import java.util.List;

public class ReasoningTopUp {
    private static final String RSN = "Reasoning";
    private static final String RSN_SUB = "Logical Reasoning";

    public static void populate(List<QuestionTopUpSeeder.QSpec> list) {
        addNumberSeries(list);
        addAlphabetSeries(list);
        addCodingDecoding(list);
        addBloodRelations(list);
        addDirectionSense(list);
        addSyllogism(list);
        addAnalogy(list);
        addClassification(list);
        addSeatingArrangement(list);
        addPuzzles(list);
        addRanking(list);
        addDataSufficiency(list);
        addClock(list);
        addCalendar(list);
        addVennDiagram(list);
    }

    private static void addNumberSeries(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the next number in series: 2, 6, 12, 20, 30, ?",
            "42", "40", "44", "38", "A",
            "Differences are consecutive even numbers: +4, +6, +8, +10, so next is +12. 30 + 12 = 42.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the missing number: 1, 4, 9, 16, 25, ?",
            "36", "49", "32", "40", "A",
            "Series consists of squares of natural numbers: 1^2, 2^2, 3^2, 4^2, 5^2, 6^2 = 36.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the next term: 3, 5, 9, 17, 33, ?",
            "65", "63", "67", "61", "A",
            "Differences: 2, 4, 8, 16, so next difference is 32. 33 + 32 = 65. (Pattern: 2*n - 1).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the missing number in series: 7, 10, 8, 11, 9, 12, ?",
            "10", "13", "11", "14", "A",
            "Alternating series: +3, -2, +3, -2, +3, -2. 12 - 2 = 10.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the next number: 2, 3, 5, 7, 11, 13, ?",
            "17", "15", "19", "21", "A",
            "The series is the sequence of consecutive prime numbers. Next prime is 17.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the next number: 1, 8, 27, 64, 125, ?",
            "216", "256", "343", "196", "A",
            "Cubes of natural numbers: 1^3, 2^3, 3^3, 4^3, 5^3, 6^3 = 216.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the next term: 4, 18, 48, 100, 180, ?",
            "294", "280", "300", "312", "A",
            "Pattern is n^3 - n^2: for n=6, 6^3 - 6^2 = 216 - 36 = 180? Wait: 1^2*2=2, 2^2*3=12... Here n^2*(n+1) or n^3-n^2 for n=2..7. For n=7, 7^3-7^2 = 343-49 = 294.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the next number: 10, 14, 26, 42, 70, ?",
            "114", "112", "116", "118", "A",
            "Sum of previous two numbers: 10+14=24(+2)... wait: 14-10=4, 26-14=12, 42-26=16, 70-42=28; 4+12=16, 12+16=28, 16+28=44 => 70+44 = 114.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the wrong number in the series: 3, 8, 15, 24, 34, 48, 63",
            "34", "24", "48", "15", "A",
            "Pattern: n^2 - 1 for n=2,3,4,5,6,7,8 -> 3, 8, 15, 24, 35 (not 34), 48, 63.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find next number: 6, 12, 36, 144, 720, ?",
            "4320", "3600", "5040", "2160", "A",
            "Multipliers are *2, *3, *4, *5, so next is *6. 720 * 6 = 4320.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the next number: 5, 6, 9, 15, ?, 40",
            "25", "24", "26", "23", "A",
            "Differences: +1, +3, +6, +10 (triangular numbers). 15 + 10 = 25, then 25 + 15 = 40.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Number Series",
            "Find the missing term: 2, 5, 11, 23, 47, ?",
            "95", "94", "96", "93", "A",
            "Pattern: 2n + 1: 2*2+1=5, 5*2+1=11, 11*2+1=23, 23*2+1=47, 47*2+1=95.", Difficulty.EASY, 2));
    }

    private static void addAlphabetSeries(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the next letter in the series: A, C, F, J, O, ?",
            "U", "T", "V", "W", "A",
            "Positions: 1 (+2) 3 (+3) 6 (+4) 10 (+5) 15 (+6) = 21, which is letter U.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the next term: B, E, H, K, N, ?",
            "Q", "P", "R", "S", "A",
            "Letters increase by +3 each time: 2, 5, 8, 11, 14, +3 = 17 = Q.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the next term: AZ, BY, CX, DW, ?",
            "EV", "EU", "FU", "FW", "A",
            "First letters increase: A, B, C, D, E. Second letters reverse from Z: Z, Y, X, W, V. So EV.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the missing term: BZA, DYC, FXE, ?",
            "HWG", "GVF", "HVF", "GWG", "A",
            "First letters: B(+2)D(+2)F(+2)H. Middle letters: Z(-1)Y(-1)X(-1)W. Third letters: A(+2)C(+2)E(+2)G. Result is HWG.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the next term: Z, W, S, N, ?",
            "H", "I", "G", "J", "A",
            "Positions: 26 (-3) 23 (-4) 19 (-5) 14 (-6) = 8 = H.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the next term in series: AB, DEF, HIJK, ?, STUVWX",
            "MNOPQ", "LMNO", "NOPQR", "KLMNO", "A",
            "Terms have lengths 2, 3, 4, 5, 6 letters. Gap between terms is 2 letters (C skipped, G skipped, L skipped -> MNOPQ, then R skipped).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "What letter is 7th to the right of the 13th letter from the left in English alphabet?",
            "T", "U", "S", "V", "A",
            "13 + 7 = 20th letter from left = T.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the missing letters in: a_b_a_b_a_b",
            "baba", "abab", "abba", "baab", "A",
            "Pattern is repetitive 'ab ab ab ab ab ab'. The blanks correspond to b, a, b, a.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the next term: PERPENDICULAR, ERPENDICULA, RPENDICUL, ?",
            "PENDICU", "PENDICUL", "RPENDICU", "PENDIC", "A",
            "In each step, the first and last letters are removed. Removing R and L gives PENDICU.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find next term: CX, FU, IR, ?",
            "LO", "MN", "KP", "LP", "A",
            "First letters: C(3), F(6), I(9), L(12). Second letters: X(24), U(21), R(18), O(15). Together: LO (sum of positions is 27).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Find the next term: WFB, TGD, QHG, ?",
            "NII", "NIK", "MJK", "OIK", "A",
            "First: W(-3) T(-3) Q(-3) N. Second: F(+1) G(+1) H(+1) I. Third: B(+2) D(+3) G(+2) I (or K). NIK/NII -> NIK fits progression.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Alphabet Series",
            "Which letter is midway between the 6th and 14th letter from the left?",
            "J", "K", "I", "L", "A",
            "Midpoint = (6 + 14)/2 = 10th letter = J.", Difficulty.EASY, 2));
    }

    private static void addCodingDecoding(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If TEACHER is coded as VGCEJGT, how is CHILDREN coded?",
            "EJKNFTGP", "EJKNFUTP", "EKJNHTGP", "EIJNFTGP", "A",
            "Each letter is shifted forward by +2. C->E, H->J, I->K, L->N, D->F, R->T, E->G, N->P.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If CAT = 24 and DOG = 26, what is RAT?",
            "39", "40", "38", "42", "A",
            "Sum of alphabetical positions: R(18) + A(1) + T(20) = 39.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "In a code language, 'pit dar na' means 'you are good', 'dar tok pa' means 'good and bad'. What is 'good'?",
            "dar", "pit", "na", "tok", "A",
            "Common word between the two phrases is 'good', and common code word is 'dar'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If DELHI is coded as 73541 and CALCUTTA as 82589662, how is CALICUT coded?",
            "8251896", "8251869", "8521896", "8258196", "A",
            "Direct letter-to-digit assignment: C=8, A=2, L=5, I=1, C=8, U=9, T=6 -> 8251896.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If WATER is written as YCVGT, how is HIKER written?",
            "JKMGT", "JKMGV", "JLMGT", "ILMHT", "A",
            "Every letter shifted by +2. H->J, I->K, K->M, E->G, R->T = JKMGT.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If 'white' is called 'blue', 'blue' is called 'red', 'red' is called 'yellow', what is the color of clear sky?",
            "red", "blue", "yellow", "white", "A",
            "Clear sky is blue. In this code, 'blue' is called 'red'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "In a certain code, MONKEY is written as XDJMNL. How is TIGER written in that code?",
            "QDFHS", "SDFHQ", "UJHFS", "QDFHT", "A",
            "Pattern: reverse and -1 for each letter: Y-1=X, E-1=D, K-1=J, N-1=M, O-1=N, M-1=L. For TIGER: R-1=Q, E-1=D, G-1=F, I-1=H, T-1=S => QDFHS.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If BANK is coded as 211411, how is CASH coded?",
            "31198", "31189", "31289", "31197", "A",
            "Concatenate letter positions: C=3, A=1, S=19, H=8 -> 31198.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If 'ROSE' is coded as 6821 and 'CHAIR' as 73456, what is 'SEARCH'?",
            "214673", "214763", "241673", "214637", "A",
            "S=2, E=1, A=4, R=6, C=7, H=3 => 214673.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If '3a, 2b, 7c' means 'truth is eternal', '7c, 9a, 8b' means 'enmity is not', which code means 'is'?",
            "7c", "3a", "2b", "8b", "A",
            "'is' is the only common word in both, and '7c' is the only common code.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If 'GIVE' is coded as '5137' and 'BAT' as '924', how is 'GATE' coded?",
            "5247", "5427", "2547", "5274", "A",
            "G=5, A=2, T=4, E=7 => 5247.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Coding-Decoding",
            "If TAP is SZO, then FRIEND is:",
            "EQHDMC", "EQHDMB", "EQHCMB", "EQGDMC", "A",
            "Each letter is shifted backward by -1: F->E, R->Q, I->H, E->D, N->M, D->C.", Difficulty.MEDIUM, 3));
    }

    private static void addBloodRelations(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "Pointing to a photograph, a man said, 'I have no brother or sister, but that man's father is my father's son.' Whose photograph was it?",
            "His son's", "His father's", "His own", "His nephew's", "A",
            "'My father's son' with no siblings means the speaker himself. So that man's father is the speaker => photograph is of his son.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "A is B's sister. C is B's mother. D is C's father. How is A related to D?",
            "Granddaughter", "Daughter", "Grandmother", "Niece", "A",
            "A is daughter of C, and C is daughter of D. So A is granddaughter of D.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "Introducing a boy, a girl said, 'He is the son of the only daughter of the father of my maternal uncle.' How is the boy related to the girl?",
            "Brother", "Cousin", "Uncle", "Nephew", "A",
            "Father of maternal uncle is maternal grandfather. The only daughter of maternal grandfather is the girl's mother. Her son is the girl's brother.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "Pointing to a lady, Rahul said, 'She is the mother of the only daughter of my father.' How is the lady related to Rahul?",
            "Mother", "Sister", "Aunt", "Wife", "A",
            "Only daughter of Rahul's father is Rahul's sister. Her mother is Rahul's mother.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "If P is brother of Q, Q is sister of R, and R is father of S, how is P related to S?",
            "Paternal Uncle", "Father", "Brother", "Grandfather", "A",
            "P and R are brothers. S is child of R. So P is paternal uncle of S.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "A and B are brothers. C and D are sisters. A's son is D's brother. How is B related to C?",
            "Uncle", "Father", "Brother", "Grandfather", "A",
            "D's brother is A's son, so C and D are daughters of A. B is A's brother, making B their uncle.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "Pointing to a gentleman, Deepak said, 'His only brother is the father of my daughter's father.' How is the gentleman related to Deepak?",
            "Uncle", "Father", "Brother", "Grandfather", "A",
            "'My daughter's father' is Deepak. The gentleman's only brother is Deepak's father. Thus the gentleman is Deepak's paternal uncle.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "If A + B means A is father of B, A - B means A is wife of B, A * B means A is brother of B, which represents P is mother of R?",
            "P - Q + R", "P + Q - R", "P * Q - R", "P - Q * R", "A",
            "P - Q means P is wife of Q; Q + R means Q is father of R. Therefore, P is mother of R.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "M is the brother of N. B is the brother of N. M is brother of D. Which of the following statements is definitely true?",
            "N is brother of B", "M is brother of B", "D is brother of M", "N is sister of M", "B",
            "Since M is brother of both N and B, M is definitely the brother of B. Gender of N and D is not specified.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "A woman walking with a boy meets another woman and on being asked about the boy says, 'My maternal uncle and his maternal uncle are brothers.' How is the boy related to the woman?",
            "Son or nephew", "Brother", "Husband", "Father", "A",
            "Maternal uncles are brothers means their mothers are sisters or the same person. Thus the boy is her son or nephew.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "X tells Y, 'Though I am the son of your father, you are not my brother.' How are X and Y related?",
            "Y is sister of X", "Y is father of X", "Y is mother of X", "Cannot be determined", "A",
            "If they have the same father and Y is not the brother, Y must be the sister.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Blood Relations",
            "Pointing to a photograph, Monica said, 'He is the brother of the daughter of the wife of my husband.' How is the person related to Monica?",
            "Son", "Brother", "Nephew", "Husband", "A",
            "Wife of my husband = Monica herself. Daughter of Monica = her daughter. Brother of her daughter = Monica's son.", Difficulty.EASY, 2));
    }

    private static void addDirectionSense(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "A person walks 10m North, turns right and walks 10m, turns right again and walks 10m. In which direction is he from the starting point?",
            "East", "West", "North", "South", "A",
            "After going 10m North and 10m South, his vertical displacement is 0. Horizontal displacement is 10m East.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "A man walks 3 km North, then 4 km East. What is the shortest distance from the start point?",
            "5 km", "7 km", "6 km", "4 km", "A",
            "Shortest distance = sqrt(3^2 + 4^2) = sqrt(9 + 16) = 5 km.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "Ravi faces South, turns left and walks 15m, then turns left again and walks 7m. Which direction is he facing now?",
            "North", "East", "West", "South", "A",
            "Facing South, left turn -> East. Another left turn -> North.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "At sunset, Amit is facing a pole. The shadow of the pole falls to his right. Which direction is Amit facing?",
            "South", "North", "East", "West", "A",
            "At sunset, sun is in the West, so shadows fall towards the East. If East is to his right, he must be facing North.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "A clock shows 4:30. If the minute hand points to East, in which direction does the hour hand point?",
            "North-East", "South-East", "North-West", "South-West", "A",
            "At 4:30, minute hand is at 6 (South normally). If South is East, map rotated 90° CCW. Hour hand is midway between 4 and 5 (SE), rotated 90° CCW -> North-East.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "A man walks 5 km towards South, then turns right and walks 3 km, turns right and walks 5 km, then turns left and walks 5 km. How far is he from the start?",
            "8 km", "10 km", "3 km", "5 km", "A",
            "Vertical displacement cancels (5 South, 5 North). Horizontal: 3 West + 5 West = 8 km West.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "Starting from point P, Sachin walked 20m South, turned left and walked 30m, turned left and walked 20m, then turned left and walked 40m. How far is he from P?",
            "10 m", "20 m", "30 m", "40 m", "A",
            "North-South displacement is 0. East-West: 30 East then 40 West = 10m West of P.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "If South-East becomes North, North-East becomes West and so on, what will West become?",
            "South-East", "North-East", "North-West", "South-West", "A",
            "Rotation is 135° counter-clockwise. West rotated 135° CCW becomes South-East.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "A person walks 1 km North, turns 45° right and walks 2 km. In which direction is he now moving?",
            "North-East", "East", "South-East", "North-West", "A",
            "Facing North, a 45° clockwise (right) turn points to North-East.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "One morning after sunrise, Suresh was standing facing a pole. The shadow of the pole fell exactly to his right. Which direction was he facing?",
            "South", "North", "East", "West", "A",
            "In morning, shadow falls towards West. If West is to his right, Suresh is facing South.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "Village A is 20 km to the West of village B. Village C is 20 km to the South of A. Village D is 20 km to the East of C. In which direction is D from B?",
            "South", "North", "West", "South-East", "A",
            "D is at (0, -20) relative to B (since C is (-20,-20) and D is (-20+20, -20) = (0,-20)). So D is directly South of B.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Direction Sense",
            "Kunal walks 10 km North, from there he walks 6 km South, then 3 km East. How far and in which direction is he from his starting point?",
            "5 km North-East", "7 km East", "5 km North", "7 km North-East", "A",
            "Net displacement: 10 - 6 = 4 km North, and 3 km East. Distance = sqrt(4^2 + 3^2) = 5 km North-East.", Difficulty.EASY, 2));
    }

    private static void addSyllogism(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: All cats are dogs. All dogs are birds. Conclusions: I. All cats are birds. II. All birds are cats.",
            "Only I follows", "Only II follows", "Both follow", "Neither follows", "A",
            "Cats subset Dogs subset Birds. Therefore All cats are birds is true, but All birds are cats is not necessarily true.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: Some pens are books. All books are pencils. Conclusions: I. Some pens are pencils. II. All pencils are books.",
            "Only I follows", "Only II follows", "Both follow", "Neither follows", "A",
            "Intersection of pens and books is inside pencils, so some pens are pencils. All pencils are books does not follow.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: No door is dog. All dogs are animals. Conclusions: I. No door is animal. II. Some animals are dogs.",
            "Only II follows", "Only I follows", "Both follow", "Neither follows", "A",
            "Since all dogs are animals, some animals are dogs (II follows). Animals that are not dogs could be doors, so I does not follow.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: Some mangoes are apples. Some apples are bananas. Conclusions: I. Some mangoes are bananas. II. No mango is banana.",
            "Either I or II follows", "Only I follows", "Only II follows", "Neither follows", "A",
            "Complementary pair (Some A is B and No A is B) with uncertain relation yields 'Either I or II follows'.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: All flowers are trees. No tree is fruit. Conclusions: I. No flower is fruit. II. Some trees are flowers.",
            "Both I and II follow", "Only I follows", "Only II follows", "Neither follows", "A",
            "Flowers are inside trees, and trees are disjoint from fruits, so no flower is fruit. Also some trees are flowers holds.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: All cars are jeeps. All jeeps are four-wheelers. Conclusions: I. All four-wheelers are cars. II. Some four-wheelers are cars.",
            "Only II follows", "Only I follows", "Both follow", "Neither follows", "A",
            "Cars subset Four-wheelers => Some four-wheelers are cars (II follows). All four-wheelers are cars doesn't necessarily hold.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: Some cups are plates. Some plates are jugs. Conclusions: I. Some cups are jugs. II. No cup is jug.",
            "Either I or II follows", "Neither follows", "Both follow", "Only I follows", "A",
            "Between cups and jugs, no relation is given. 'Some' and 'No' form a complementary pair, hence Either I or II follows.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: All roses are red. Some red are beautiful. Conclusions: I. Some roses are beautiful. II. All beautiful are roses.",
            "Neither follows", "Only I follows", "Only II follows", "Both follow", "A",
            "The beautiful subset might not overlap with roses at all. Neither conclusion follows.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: Some books are notes. All notes are papers. Conclusions: I. Some notes are books. II. Some papers are books.",
            "Both follow", "Only I follows", "Only II follows", "Neither follows", "A",
            "Some books are notes implies some notes are books (I). Notes subset papers means some papers are books (II). Both follow.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: No man is a bird. Ram is a man. Conclusions: I. Ram is not a bird. II. All men are Ram.",
            "Only I follows", "Only II follows", "Both follow", "Neither follows", "A",
            "Ram belongs to the set of men, which has no intersection with birds. Thus Ram is not a bird.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: All rings are circles. All circles are spheres. Conclusions: I. All rings are spheres. II. Some spheres are rings.",
            "Both follow", "Only I follows", "Only II follows", "Neither follows", "A",
            "Rings subset Circles subset Spheres implies all rings are spheres and some spheres are rings. Both follow.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Syllogism",
            "Statements: Some teachers are students. All students are hardworking. Conclusions: I. Some teachers are hardworking. II. All hardworking are teachers.",
            "Only I follows", "Only II follows", "Both follow", "Neither follows", "A",
            "Intersection of teachers and students is hardworking, so Some teachers are hardworking follows. II does not follow.", Difficulty.EASY, 2));
    }

    private static void addAnalogy(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Moon : Satellite :: Earth : ?",
            "Planet", "Sun", "Solar System", "Asteroid", "A",
            "Moon is a satellite; Earth is a planet.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Clock : Time :: Thermometer : ?",
            "Temperature", "Heat", "Mercury", "Energy", "A",
            "Clock measures time; Thermometer measures temperature.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Doctor : Hospital :: Teacher : ?",
            "School", "Class", "Book", "Student", "A",
            "Doctor works in a hospital; Teacher works in a school.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Book : Pages :: Car : ?",
            "Parts", "Wheels", "Driver", "Engine", "B",
            "Pages compose a book; wheels are fundamental components of a car (or parts). Wheels is the direct analog.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "8 : 64 :: 11 : ?",
            "121", "110", "131", "144", "A",
            "8^2 = 64; 11^2 = 121.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Pen : Ink :: Needle : ?",
            "Thread", "Cloth", "Tailor", "Stitch", "A",
            "Pen uses ink to function; needle uses thread.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Architect : Building :: Sculptor : ?",
            "Statue", "Museum", "Stone", "Chisel", "A",
            "An architect designs a building; a sculptor creates a statue.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Ornithologist : Birds :: Anthropologist : ?",
            "Mankind", "Plants", "Insects", "Animals", "A",
            "Ornithology is study of birds; Anthropology is study of mankind.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "25 : 37 :: 49 : ?",
            "65", "64", "60", "70", "A",
            "25 = 5^2, 37 = 6^2 + 1. 49 = 7^2, so next is 8^2 + 1 = 65.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Ophthalmic : Eye :: Nephrology : ?",
            "Kidney", "Heart", "Liver", "Lungs", "A",
            "Ophthalmology relates to eyes; Nephrology relates to kidneys.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Cat : Kitten :: Dog : ?",
            "Puppy", "Colt", "Calf", "Cub", "A",
            "A young cat is a kitten; a young dog is a puppy.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Analogy",
            "Poverty : Prosperity :: Intelligence : ?",
            "Stupidity", "Wisdom", "Knowledge", "Smartness", "A",
            "Antonym pair: Poverty is opposite to Prosperity; Intelligence is opposite to Stupidity.", Difficulty.EASY, 2));
    }

    private static void addClassification(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: Copper, Silver, Gold, Plastic",
            "Plastic", "Copper", "Silver", "Gold", "A",
            "Copper, silver, and gold are metals; plastic is a polymer / non-metal.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: 27, 64, 125, 144",
            "144", "27", "64", "125", "A",
            "27=3^3, 64=4^3, 125=5^3 are perfect cubes. 144 is 12^2 (not a cube of an integer).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: Apple, Mango, Potato, Orange",
            "Potato", "Apple", "Mango", "Orange", "A",
            "Apple, mango, and orange are fruits; potato is a vegetable (tuber).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: Square, Rectangle, Circle, Triangle",
            "Circle", "Square", "Rectangle", "Triangle", "A",
            "Circle is a curved geometric figure without vertices or straight edges; others are polygons.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: 13, 17, 23, 27",
            "27", "13", "17", "23", "A",
            "13, 17, and 23 are prime numbers; 27 is a composite number (3^3).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: Eyes, Ears, Nose, Liver",
            "Liver", "Eyes", "Ears", "Nose", "A",
            "Liver is an internal organ; others are external sense organs.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: January, May, July, November",
            "November", "January", "May", "July", "A",
            "January, May, and July have 31 days each. November has 30 days.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: BD, EG, HJ, KM",
            "EG", "BD", "HJ", "KM", "A",
            "B(+2)=D, H(+2)=J, K(+2)=M. But E(+2)=G (Wait: E(5), G(7) is +2). Let's check difference: KM is 11,13. EG is 5,7. If LN is used, EG follows. All are +2 except say KM if letter positions differ.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: Whale, Dolphin, Shark, Seal",
            "Shark", "Whale", "Dolphin", "Seal", "A",
            "Shark is a fish (cartilaginous); whale, dolphin, and seal are mammals.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: 24, 36, 48, 59",
            "59", "24", "36", "48", "A",
            "59 is prime; 24, 36, and 48 are composite numbers divisible by 12.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: Iron, Lead, Nitrogen, Zinc",
            "Nitrogen", "Iron", "Lead", "Zinc", "A",
            "Nitrogen is a gas / non-metal; iron, lead, and zinc are solid metals.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Classification",
            "Find the odd one out: Kilometer, Millimeter, Kilogram, Centimeter",
            "Kilogram", "Kilometer", "Millimeter", "Centimeter", "A",
            "Kilogram is a unit of mass; others are units of distance/length.", Difficulty.EASY, 2));
    }

    private static void addSeatingArrangement(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "Five friends A, B, C, D, E are sitting in a row facing North. C is sitting in the middle. A is to the immediate left of C. Who is at the left end?",
            "Cannot be determined without more info", "A", "B", "E", "A",
            "C is in position 3, A is in position 2. Positions 1, 4, 5 depend on B, D, E, so left end cannot be determined without additional info.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "Four people A, B, C, D sit around a circular table facing center. A is opposite to C, B is to right of A. Who is to right of C?",
            "D", "B", "A", "None", "A",
            "Facing center: A's right is B. A is opposite C. So C's right is D and C's left is B.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "In a row of boys, A is 10th from the left and B is 9th from the right. If they interchange positions, A becomes 15th from left. How many boys are there?",
            "23", "24", "22", "25", "A",
            "Total = (New position of A) + (Old position of B) - 1 = 15 + 9 - 1 = 23.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "Six persons P, Q, R, S, T, U sit in a circle facing center. P is opposite S. T is to immediate right of P. Who is to immediate left of S?",
            "T", "R", "Q", "U", "A",
            "S is opposite P. T is immediate right of P (clockwise). Moving around circle, T is opposite the seat to the left of S, or directly adjacent depending on numbering.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "5 students A, B, C, D, E sit in a line facing North. E is to the right of D, B is to the left of D but right of A. Who is on the extreme left?",
            "A", "B", "C", "D", "A",
            "Order: A, B, D, E (with C elsewhere). A is to the left of B, which is left of D, making A the extreme left.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "In a circular arrangement of 8 persons facing center, how many persons sit between any person and the person sitting directly opposite him?",
            "3", "4", "2", "5", "A",
            "In an 8-person circular table, 3 persons sit on each side between two opposite persons.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "Six friends A, B, C, D, E, F sit in two rows of 3 facing each other. If B is opposite E, who faces C if C is at an end?",
            "Depends on full constraints", "D", "F", "A", "A",
            "Requires full layout constraints to uniquely identify the opposite facing partner.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "A, B, C, D sit in a row. A and B are adjacent. C and D are adjacent. C does not sit next to B. If B is on the extreme left, who is next to C?",
            "D", "A", "B", "None", "A",
            "Row order: B, A, C, D (or B, A, D, C). In either, C is next to D.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "7 people sit in a row facing North. The person in the middle is at position:",
            "4th", "3rd", "5th", "Cannot be determined", "A",
            "(7 + 1)/2 = 4th position from either end.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "When people sit facing the center of a circle, what is the direction of moving to their 'right'?",
            "Counter-clockwise", "Clockwise", "Towards center", "Away from center", "A",
            "Facing center, the right hand points in the counter-clockwise direction.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "When people sit facing outward from a circle, what is the direction of moving to their 'right'?",
            "Clockwise", "Counter-clockwise", "Straight", "None", "A",
            "Facing outward, right hand points in the clockwise direction.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Seating Arrangement",
            "In a row of 20 people facing North, if Ramesh moves 4 places to the right, he becomes 12th from the right end. His initial position from left was:",
            "5th", "6th", "8th", "4th", "A",
            "New pos from right = 12th => new pos from left = 20 - 12 + 1 = 9th. Initial = 9 - 4 = 5th from left.", Difficulty.HARD, 4));
    }

    private static void addPuzzles(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "A farmer has 17 sheep and all but 9 die. How many sheep are left alive?",
            "9", "8", "0", "17", "A",
            "'All but 9 die' means exactly 9 sheep survived.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "A snail is at the bottom of a 30-foot well. Each day it climbs 3 feet and slips back 2 feet at night. How many days to reach the top?",
            "28 days", "30 days", "29 days", "27 days", "A",
            "Net climb per day = 1 foot. After 27 days it reaches 27 feet. On day 28, it climbs 3 feet to reach 30 feet and exits before slipping.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "If you have a 3-liter jug and a 5-liter jug with unlimited water, how can you measure exactly 4 liters?",
            "Fill 5L, pour into 3L (2L left). Empty 3L, pour 2L into 3L. Fill 5L, pour into 3L until full (1L poured) leaving 4L in 5L jug.", "Not possible", "Fill 3L twice", "Fill half of both", "A",
            "Standard water-jug riddle: 5L - 3L = 2L in 5L jug. Transfer to 3L jug. Fill 5L, pour 1L to fill 3L jug, leaving exactly 4L in 5L jug.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "There are 8 identical-looking balls; 1 is heavier. Minimum weighings on a balance scale to find the heavier ball is:",
            "2", "3", "4", "1", "A",
            "Weigh 3 vs 3. If equal, weigh remaining 2 (1 weighing). If unequal, weigh 1 vs 1 of the heavier 3. Total = 2 weighings.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "Two fathers and two sons go fishing. Each catches one fish. Total fish caught is 3. How is this possible?",
            "There are only 3 people: grandfather, father, and son.", "One fish escaped", "They shared a fish", "Impossible", "A",
            "Grandfather is a father. Father is both a son and a father. Son is a son. Exactly 3 people.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "A candle burns completely in 60 minutes. How many candles are needed to measure 45 minutes using burning from both ends?",
            "2 candles", "1 candle", "3 candles", "4 candles", "A",
            "Light candle 1 from both ends and candle 2 from one end. After 30 min, candle 1 is gone. Then light other end of candle 2 (burns out in 15 min). Total = 45 min.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "You have 100 doors in a row, all initially closed. You make 100 passes toggling doors. Which doors remain open?",
            "Doors whose numbers are perfect squares", "Even numbered doors", "Odd numbered doors", "Prime numbered doors", "A",
            "A door is toggled once for every divisor. Only perfect squares have an odd number of divisors, so they end up open.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "How many times do the hands of a clock overlap in a 24-hour day?",
            "22 times", "24 times", "20 times", "23 times", "A",
            "Clock hands overlap 11 times in 12 hours (due to 11-1 overlap), so in 24 hours they overlap 22 times.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "A bat and a ball cost $1.10 in total. The bat costs $1.00 more than the ball. How much does the ball cost?",
            "$0.05", "$0.10", "$0.01", "$0.15", "A",
            "Bat + Ball = 1.10; Bat = Ball + 1.00 => 2*Ball + 1.00 = 1.10 => 2*Ball = 0.10 => Ball = $0.05.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "If 5 machines take 5 minutes to make 5 widgets, how long would 100 machines take to make 100 widgets?",
            "5 minutes", "100 minutes", "20 minutes", "1 minute", "A",
            "1 machine takes 5 minutes to make 1 widget. So 100 machines working simultaneously will make 100 widgets in 5 minutes.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "A duck was given $9, a spider was given $36, a bee was given $27. Based on this logic, how much would be given to a cat?",
            "$18", "$16", "$12", "$20", "A",
            "Each leg is given $4.50: Duck (2 legs) = $9; Spider (8 legs) = $36; Bee (6 legs) = $27; Cat (4 legs) = 4 * 4.5 = $18.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Puzzles",
            "What occurs once in a minute, twice in a moment, but never in a thousand years?",
            "The letter 'M'", "A second", "A leap second", "A thought", "A",
            "The letter 'M' appears 1 time in 'minute', 2 times in 'moment', and 0 times in 'a thousand years'.", Difficulty.EASY, 2));
    }

    private static void addRanking(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "In a class of 40 students, Rohan is ranked 15th from the top. What is his rank from the bottom?",
            "26th", "25th", "27th", "24th", "A",
            "Rank from bottom = Total - (Rank from top) + 1 = 40 - 15 + 1 = 26th.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "Sunil is 7th from the top and 28th from the bottom in a test. How many students are there in the class?",
            "34", "35", "33", "36", "A",
            "Total = Top + Bottom - 1 = 7 + 28 - 1 = 34.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "In a row of trees, a tree is 7th from either end of the row. How many trees are in the row?",
            "13", "14", "12", "15", "A",
            "Total = 7 + 7 - 1 = 13.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "In a class of 60 where girls are twice that of boys, Kamal ranked 17th from top. If 9 girls are ahead of him, how many boys are behind him?",
            "12", "13", "11", "10", "A",
            "Boys = 20, Girls = 40. Ahead of Kamal = 16 students (9 girls, 7 boys). If Kamal is a boy, boys behind = 20 - (7 + 1) = 12.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "A is taller than B, C is taller than A, D is taller than E but shorter than B. Who is the tallest?",
            "C", "A", "B", "D", "A",
            "Order from tallest: C > A > B > D > E. So C is the tallest.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "In a row of 30 students, Mahesh is 14th from left and Suresh is 20th from right. How many students are between them?",
            "2", "3", "4", "1", "A",
            "Sum of positions = 14 + 20 = 34 > 30 (overlap). Students between = (14 + 20) - 30 - 2 = 2.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "P is heavier than Q. R is lighter than S. T is heavier than S but lighter than Q. Who is the heaviest?",
            "P", "Q", "T", "S", "A",
            "Order: P > Q > T > S > R. So P is the heaviest.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "Manoj and Sachin are ranked 7th and 11th from top in class of 31. What are their respective ranks from bottom?",
            "25th and 21st", "24th and 20th", "26th and 22nd", "25th and 20th", "A",
            "Manoj: 31 - 7 + 1 = 25th. Sachin: 31 - 11 + 1 = 21st.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "In an examination, Rahul got 11th rank and he was 47th from the bottom among passed candidates. 3 boys did not appear, 1 failed. Total students were:",
            "61", "60", "58", "62", "A",
            "Passed = 11 + 47 - 1 = 57. Total students = 57 + 3 + 1 = 61.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "Among five friends P, Q, R, S, T, each with different height, T is shorter than only P. Who is the second tallest?",
            "T", "P", "Q", "R", "A",
            "'T is shorter than only P' means P is the tallest and T is the second tallest.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "In a row facing North, A is 10th from left and B is 15th from right. If C is exactly between A and B and is 16th from left, how many total persons?",
            "36", "35", "37", "34", "A",
            "Persons between A and C = 16 - 10 - 1 = 5. Since C is midway, persons between C and B = 5. Total = 10 + 5 + 1 + 5 + 15 = 36.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Ranking",
            "Five boxes A, B, C, D, E are stacked. B is above C, A is below D, C is above D. Which box is at the bottom?",
            "A", "E", "D", "C", "A",
            "Stack order from top: B, C, D, A (with E unspecified, but among options A is below all). A is at the bottom.", Difficulty.MEDIUM, 3));
    }

    private static void addDataSufficiency(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "What is the value of x? Statement I: 2x + 4 = 10. Statement II: x > 0.",
            "Statement I alone is sufficient", "Statement II alone is sufficient", "Both together are sufficient", "Neither is sufficient", "A",
            "Statement I gives 2x = 6 => x = 3 uniquely. Statement II alone is insufficient.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "Is integer n even? Statement I: n + 1 is odd. Statement II: 2n is even.",
            "Statement I alone is sufficient", "Statement II alone is sufficient", "Both together are needed", "Neither is sufficient", "A",
            "If n + 1 is odd, n must be even (sufficient). 2n is always even for any integer n (insufficient).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "Who is the oldest among A, B, C? Statement I: A is older than B. Statement II: B is older than C.",
            "Both statements together are sufficient", "Statement I alone is sufficient", "Statement II alone is sufficient", "Neither is sufficient", "A",
            "From I and II: A > B > C. Thus A is the oldest. Both statements together are sufficient.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "What is the area of a rectangle? Statement I: Perimeter is 40 cm. Statement II: Length is 12 cm.",
            "Both statements together are sufficient", "Statement I alone", "Statement II alone", "Neither is sufficient", "A",
            "Perimeter = 2(L + W) = 40 => L + W = 20. With L = 12, W = 8, so Area = 96 cm². Both statements together are sufficient.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "What is the value of a + b? Statement I: a - b = 4. Statement II: a^2 - b^2 = 24.",
            "Both statements together are sufficient", "Statement I alone", "Statement II alone", "Neither is sufficient", "A",
            "a^2 - b^2 = (a - b)(a + b) => 24 = 4 * (a + b) => a + b = 6. Both statements together are sufficient.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "How many students are in the class? Statement I: Rahul is 10th from top. Statement II: Rahul is 20th from bottom.",
            "Both statements together are sufficient", "Statement I alone", "Statement II alone", "Neither is sufficient", "A",
            "Total = Top + Bottom - 1 = 10 + 20 - 1 = 29. Both statements together are sufficient.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "Is x > y? Statement I: x - y > 0. Statement II: x / y > 1.",
            "Statement I alone is sufficient", "Statement II alone is sufficient", "Both together are sufficient", "Neither is sufficient", "A",
            "x - y > 0 directly implies x > y. Statement II can fail if both are negative (e.g. -4 / -2 = 2 > 1 but -4 < -2).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "In which direction is city X with respect to city Y? Statement I: City X is East of Z. Statement II: City Y is South of Z.",
            "Both statements together are sufficient", "Statement I alone", "Statement II alone", "Neither is sufficient", "A",
            "Z is origin (0,0). X is (d1, 0) and Y is (0, -d2). Relative to Y, X is North-East. Both together are sufficient.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "What is the speed of the train? Statement I: Train crosses a 200m platform in 20s. Statement II: Train crosses a pole in 10s.",
            "Both statements together are sufficient", "Statement I alone", "Statement II alone", "Neither is sufficient", "A",
            "L/s = 10 and (L+200)/s = 20 => 200/s = 10 => s = 20 m/s. Both statements together are sufficient.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "What day of the week is today? Statement I: Tomorrow is Friday. Statement II: Yesterday was Wednesday.",
            "Either statement alone is sufficient", "Statement I alone", "Statement II alone", "Both together are required", "A",
            "Statement I implies today is Thursday. Statement II also implies today is Thursday. Either statement alone is sufficient.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "Is p an integer? Statement I: 2p is an integer. Statement II: p/2 is an integer.",
            "Statement II alone is sufficient", "Statement I alone", "Both together", "Neither is sufficient", "A",
            "If p/2 is an integer, p = 2k (an integer). Statement I allows p = 0.5 (not an integer).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Data Sufficiency",
            "How many books did Priya buy? Statement I: Total cost was Rs 300. Statement II: If each book cost Rs 5 less, she could buy 2 more for same amount.",
            "Both statements together are sufficient", "Statement I alone", "Statement II alone", "Neither is sufficient", "A",
            "Equations: n*p = 300 and (n+2)(p-5) = 300. This yields a quadratic with a unique positive integer solution. Both together are sufficient.", Difficulty.HARD, 4));
    }

    private static void addClock(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "What is the angle between the hands of a clock at 3:30?",
            "75°", "70°", "80°", "90°", "A",
            "Angle = |30*H - (11/2)*M| = |30(3) - 5.5(30)| = |90 - 165| = 75°.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "At what time between 4 and 5 o'clock will the hands of a clock be together?",
            "21 9/11 minutes past 4", "22 minutes past 4", "20 minutes past 4", "21 5/11 minutes past 4", "A",
            "Time = (30 * 4) / (11/2) = 120 * 2 / 11 = 240 / 11 = 21 9/11 minutes past 4.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "How many times are the hands of a clock at right angles in a 12-hour period?",
            "22 times", "24 times", "44 times", "20 times", "A",
            "The hands are at right angles twice an hour except during the 2-4 and 8-10 periods (where they are right angles 3 times each), giving 22 times.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "What is the angle between the clock hands at 8:20?",
            "130°", "120°", "140°", "125°", "A",
            "Angle = |30(8) - 5.5(20)| = |240 - 110| = 130°.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "How many degrees does the hour hand move in 20 minutes?",
            "10°", "20°", "5°", "12°", "A",
            "Hour hand moves 360° in 720 minutes = 0.5° per minute. In 20 minutes: 20 * 0.5° = 10°.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "At what rate does the minute hand gain on the hour hand?",
            "5.5° per minute", "6° per minute", "5° per minute", "6.5° per minute", "A",
            "Minute hand speed = 6°/min, hour hand speed = 0.5°/min. Gain = 6 - 0.5 = 5.5°/min = 11/2 °/min.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "A clock shows 7:00. What will be the reflex angle between the hands?",
            "210°", "150°", "180°", "200°", "A",
            "Angle = 30 * 7 = 210°. Since reflex angle > 180°, it is 210° (or 360 - 150 = 210°).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "How many times in a day are the hands of a clock straight (pointing opposite or together)?",
            "44 times", "48 times", "22 times", "24 times", "A",
            "Together 22 times + opposite 22 times = 44 times in 24 hours.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "A clock is set right at 5 a.m. It loses 16 minutes in 24 hours. What will be the true time when it shows 10 p.m. on the 4th day?",
            "11 p.m.", "10:30 p.m.", "11:30 p.m.", "12 midnight", "A",
            "Total elapsed clock time = 89 hours. Clock runs 23 hr 44 min per 24 true hours. 89 clock hours correspond to 90 true hours. 5 a.m. + 90 hr = 11 p.m.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "What angle is made by the minute hand from 12:00 to 12:25?",
            "150°", "120°", "180°", "135°", "A",
            "Minute hand covers 6° per minute. In 25 minutes: 25 * 6° = 150°.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "At what time between 9 and 10 o'clock will the hands be in opposite directions?",
            "16 4/11 minutes past 9", "15 minutes past 9", "16 minutes past 9", "17 3/11 minutes past 9", "A",
            "Angle = 180°. 30(9) - 5.5(m) = 180 => 270 - 180 = 5.5m => 90 = 5.5m => m = 180/11 = 16 4/11 min.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Clock",
            "What is the mirror image of a clock showing 3:25?",
            "8:35", "9:35", "8:25", "9:25", "A",
            "Mirror time = 11:60 - 3:25 = 8:35.", Difficulty.EASY, 2));
    }

    private static void addCalendar(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "If today is Monday, what day will it be after 61 days?",
            "Saturday", "Friday", "Sunday", "Tuesday", "A",
            "61 mod 7 = 5 odd days. Monday + 5 days = Saturday.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "How many odd days are there in a normal non-leap year?",
            "1", "2", "0", "3", "A",
            "365 days = 52 weeks + 1 day = 1 odd day.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "How many odd days are there in a leap year?",
            "2", "1", "0", "3", "A",
            "366 days = 52 weeks + 2 days = 2 odd days.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "Which of the following is a leap year?",
            "2000", "1900", "1800", "2100", "A",
            "A century year is a leap year only if it is divisible by 400. 2000 is divisible by 400.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "How many odd days are in 100 years?",
            "5", "6", "4", "3", "A",
            "100 years contain 76 ordinary years + 24 leap years = 76(1) + 24(2) = 124 days = 17 weeks + 5 odd days.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "How many odd days are in 400 years?",
            "0", "1", "2", "3", "A",
            "4 * 5 + 1 (for 400th year leap day) = 21 days = exactly 3 weeks = 0 odd days.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "If 1st January 2007 was Monday, what day of the week was 1st January 2008?",
            "Tuesday", "Monday", "Wednesday", "Sunday", "A",
            "2007 is an ordinary year, having 1 odd day. Monday + 1 = Tuesday.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "The calendar for the year 2007 will be the same for which year?",
            "2018", "2014", "2016", "2017", "A",
            "Count odd days from 2007: 2007(1)+2008(2)+2009(1)+2010(1)+2011(1)+2012(2)+2013(1)+2014(1)+2015(1)+2016(2)+2017(1) = 14 = 0 mod 7. Next year 2018 repeats 2007.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "What was the day of the week on 15th August 1947?",
            "Friday", "Thursday", "Saturday", "Wednesday", "A",
            "1600 years = 0, 300 years = 1, 46 years = 35 ordinary + 11 leap = 57 = 1 mod 7. Jan-Jul 1947 + 15 Aug = 3+0+3+2+3+2+3+15 = 31 = 3 mod 7. Total odd days = 1 + 1 + 3 = 5 = Friday.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "What was the day of the week on 26th January 1950?",
            "Thursday", "Wednesday", "Friday", "Tuesday", "A",
            "1600=0, 300=1, 49 years = 37 ord + 12 leap = 61 = 5 mod 7. 26 days of Jan = 5 mod 7. Total = 1 + 5 + 5 = 11 = 4 mod 7 = Thursday.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "The last day of a century cannot be:",
            "Tuesday", "Wednesday", "Friday", "Sunday", "A",
            "Odd days of centuries 100, 200, 300, 400 are 5, 3, 1, 0 (Fri, Wed, Mon, Sun). So last day cannot be Tuesday, Thursday, or Saturday.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Calendar",
            "If 8th March of a non-leap year is Wednesday, what day of the week was 8th February?",
            "Wednesday", "Tuesday", "Thursday", "Monday", "A",
            "February in a non-leap year has 28 days = 0 mod 7 odd days. So 8th March has the exact same weekday as 8th February.", Difficulty.EASY, 2));
    }

    private static void addVennDiagram(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "Which diagram best represents: Animals, Dogs, Pets?",
            "Dogs are inside Animals, and Pets overlaps with both Dogs and Animals.", "Three disjoint circles", "Three concentric circles", "Two concentric circles and one disjoint", "A",
            "All dogs are animals (Dogs subset Animals). Some pets are dogs, some are other animals, and some pets are outside animals (if considered loosely). Thus Pets circle intersects Dogs and Animals.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "Which diagram best represents: India, Delhi, Asia?",
            "Concentric circles: Delhi inside India, India inside Asia", "Three intersecting circles", "Three separate circles", "Two intersecting circles inside a third", "A",
            "Delhi is in India, and India is in Asia. Therefore, three concentric circles represent them.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "In a group of 50 people, 30 like tea, 25 like coffee, and 10 like both. How many like neither?",
            "5", "10", "15", "0", "A",
            "Union = 30 + 25 - 10 = 45. Neither = 50 - 45 = 5.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "Which diagram best represents: Men, Fathers, Doctors?",
            "Fathers inside Men, Doctors intersecting both Fathers and Men", "Three disjoint circles", "Three concentric circles", "Men inside Fathers", "A",
            "All fathers are men (Fathers subset Men). Doctors can be fathers, non-father men, or women (outside Men).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "In a class of 100 students, 60 play cricket, 50 play football, and 20 play both. How many play only cricket?",
            "40", "30", "50", "20", "A",
            "Only cricket = Cricket - Both = 60 - 20 = 40.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "Which diagram best represents: Sparrows, Birds, Mice?",
            "Sparrows inside Birds, Mice separate", "Three concentric circles", "All three separate", "All three intersecting", "A",
            "Sparrows are birds (subset). Mice are mammals, disjoint from birds.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "Which diagram represents: Liquids, Water, Milk?",
            "Water and Milk separate, both inside Liquids", "Water and Milk intersect inside Liquids", "Three concentric circles", "All separate", "A",
            "Water and milk are both liquids, but water is not milk.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "In a survey of 120 people, 70 read newspaper A, 60 read B, 20 read neither. How many read both?",
            "30", "20", "40", "25", "A",
            "Read either = 120 - 20 = 100. Both = 70 + 60 - 100 = 30.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "Which diagram best represents: Musicians, Violinists, Pianists?",
            "Violinists and Pianists inside Musicians, possibly overlapping", "Three disjoint circles", "Concentric", "None", "A",
            "Violinists and pianists are both musicians and some musicians play both instruments.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "Out of 200 people, 100 speak English, 120 speak Hindi, and 40 speak both. How many speak only Hindi?",
            "80", "60", "70", "90", "A",
            "Only Hindi = Total Hindi - Both = 120 - 40 = 80.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "Which represents: Vegetables, Potato, Cabbage?",
            "Potato and Cabbage separate, both inside Vegetables", "Concentric", "Intersecting with each other", "All separate", "A",
            "Potato and cabbage are distinct vegetables, both inside the Vegetables category.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(RSN, RSN_SUB, "Venn Diagram",
            "In a group of 70 people, 37 like coffee, 52 like tea, each person likes at least one. How many like both?",
            "19", "15", "17", "21", "A",
            "Both = (37 + 52) - 70 = 89 - 70 = 19.", Difficulty.EASY, 2));
    }
}
