package com.techprep.config;

import com.techprep.entity.Difficulty;
import java.util.List;

public class AptitudeTopUp {
    private static final String APT = "Aptitude";
    private static final String APT_SUB = "Quantitative Aptitude";

    public static void populate(List<QuestionTopUpSeeder.QSpec> list) {
        addNumberSystem(list);
        addHcfLcm(list);
        addPercentage(list);
        addProfitLoss(list);
        addRatioProportion(list);
        addAverage(list);
        addInterest(list);
        addTimeWork(list);
        addTimeSpeedDistance(list);
        addProbability(list);
        addPermutationCombination(list);
        addAlgebra(list);
        addGeometry(list);
        addMensuration(list);
        addDataInterpretation(list);
    }

    private static void addNumberSystem(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "What is the sum of the first 20 natural numbers?",
            "210", "200", "190", "220", "A",
            "Sum = n(n+1)/2 = 20*21/2 = 210.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "Which of the following is a prime number?",
            "91", "87", "97", "81", "C",
            "97 has no divisors other than 1 and 97. 91=7*13, 87=3*29, 81=9*9.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "What is the unit digit in (7^95 - 3^58)?",
            "0", "4", "6", "7", "B",
            "7 cyclicity is 4: 95 mod 4 = 3 -> 7^3 ends in 3. 3 cyclicity is 4: 58 mod 4 = 2 -> 3^2 ends in 9. 13 - 9 = 4.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "A number when divided by 899 gives a remainder 63. If the same number is divided by 29, the remainder will be:",
            "5", "3", "2", "1", "A",
            "899 is divisible by 29 (899 = 29 * 31). So remainder = 63 mod 29 = 5.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "The sum of two numbers is 37 and the difference of their squares is 185. Find the difference between the numbers.",
            "5", "10", "15", "20", "A",
            "a^2 - b^2 = (a+b)(a-b) => 185 = 37 * (a-b) => a-b = 5.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "What is the largest 4-digit number divisible by 88?",
            "9944", "9768", "9988", "8888", "A",
            "9999 / 88 = 113 with remainder 55. 9999 - 55 = 9944.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "Find the remainder when 4^96 is divided by 6.",
            "0", "2", "4", "1", "C",
            "Any positive power 4^n divided by 6 always leaves remainder 4.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "If x is a positive integer, what is the value of remainder when x^3 - x is divided by 6?",
            "Always 0", "1", "2", "Depends on x", "A",
            "x^3 - x = (x-1)x(x+1), which is the product of three consecutive integers, always divisible by 6 (rem = 0).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "How many numbers between 1 and 200 are divisible by both 3 and 5?",
            "13", "14", "12", "15", "A",
            "Divisible by LCM(3, 5) = 15. floor(200 / 15) = 13.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "What is the sum of all odd numbers between 1 and 50?",
            "625", "650", "600", "575", "A",
            "There are 25 odd numbers from 1 to 49. Sum of first n odd numbers = n^2 = 25^2 = 625.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "Find the smallest number by which 3600 must be multiplied to make it a perfect cube.",
            "60", "30", "15", "45", "A",
            "3600 = 2^4 * 3^2 * 5^2. To make powers multiples of 3: need 2^2 * 3^1 * 5^1 = 4 * 3 * 5 = 60.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Number System",
            "Which of the following is divisible by 11?",
            "28182", "4832718", "1111111", "246813", "B",
            "Alternate sum difference for 4832718: (4+3+7+8) - (8+2+1) = 22 - 11 = 11, divisible by 11.", Difficulty.MEDIUM, 3));
    }

    private static void addHcfLcm(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "Find the HCF of 108, 288 and 360.",
            "36", "18", "24", "12", "A",
            "108 = 36*3, 288 = 36*8, 360 = 36*10. The greatest common divisor is 36.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "Find the LCM of 24, 36 and 40.",
            "360", "240", "180", "720", "A",
            "24 = 2^3 * 3, 36 = 2^2 * 3^2, 40 = 2^3 * 5. LCM = 2^3 * 3^2 * 5 = 360.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "Three bells toll together at intervals of 9, 12, 15 minutes respectively. If they toll together now, when will they toll together next?",
            "3 hours", "6 hours", "180 hours", "4 hours", "A",
            "LCM(9, 12, 15) = 180 minutes = 3 hours.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "The ratio of two numbers is 3:4 and their HCF is 4. What is their LCM?",
            "48", "36", "24", "60", "A",
            "Numbers are 3*4 = 12 and 4*4 = 16. LCM(12, 16) = 48.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "Find the greatest number which on dividing 1657 and 2037 leaves remainders 6 and 5 respectively.",
            "127", "123", "235", "305", "A",
            "HCF of (1657 - 6 = 1651) and (2037 - 5 = 2032). 2032 - 1651 = 381 = 3 * 127. 1651 = 13 * 127. HCF is 127.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "The LCM of two numbers is 48. The numbers are in the ratio 2 : 3. What is the sum of the numbers?",
            "40", "28", "32", "36", "A",
            "Let numbers be 2x, 3x. LCM = 6x = 48 => x = 8. Sum = 2(8) + 3(8) = 40.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "What is the smallest number which when divided by 6, 7, 8, 9 and 12 leaves remainder 1 in each case?",
            "505", "504", "503", "506", "A",
            "LCM(6, 7, 8, 9, 12) = 504. Smallest required number = 504 + 1 = 505.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "The HCF and LCM of two numbers are 8 and 48 respectively. If one number is 24, find the other.",
            "16", "18", "20", "22", "A",
            "Product = HCF * LCM => 24 * N = 8 * 48 => N = 16.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "Find the HCF of fractions 2/3, 8/9, 64/81, 10/27.",
            "2/81", "160/3", "160/81", "2/3", "A",
            "HCF of fractions = HCF(numerators) / LCM(denominators) = HCF(2,8,64,10) / LCM(3,9,81,27) = 2 / 81.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "Find the LCM of fractions 2/3, 3/5, 4/7, 9/13.",
            "36", "36/1365", "1/1365", "12", "A",
            "LCM of fractions = LCM(numerators) / HCF(denominators) = LCM(2,3,4,9) / HCF(3,5,7,13) = 36 / 1 = 36.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "The sum of two numbers is 528 and their HCF is 33. How many pairs of such numbers exist?",
            "4", "2", "3", "6", "A",
            "Numbers are 33x, 33y where x, y are coprime and 33(x+y) = 528 => x+y = 16. Coprime pairs (x,y): (1,15), (3,13), (5,11), (7,9). Total 4 pairs.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "HCF & LCM",
            "The greatest 4-digit number which when divided by 12, 16, 24 leaves remainder 7 in each case is:",
            "9991", "9984", "9943", "9979", "A",
            "LCM(12, 16, 24) = 48. Largest 4-digit multiple = 9999 - (9999 mod 48) = 9999 - 15 = 9984. Number = 9984 + 7 = 9991.", Difficulty.HARD, 4));
    }

    private static void addPercentage(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "If A is 25% more than B, by what percent is B less than A?",
            "20%", "25%", "16.66%", "30%", "A",
            "Formula: [r / (100 + r)] * 100 = [25 / 125] * 100 = 20%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "A student has to obtain 33% of the total marks to pass. He got 125 marks and failed by 40 marks. The maximum marks are:",
            "500", "400", "600", "450", "A",
            "Passing marks = 125 + 40 = 165. 33% of Total = 165 => Total = 165 / 0.33 = 500.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "If the price of sugar increases by 20%, by what percent must a household reduce consumption so as not to increase expenditure?",
            "16.67%", "20%", "15%", "25%", "A",
            "Reduction = [r / (100 + r)] * 100 = [20 / 120] * 100 = 16.67%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "The population of a town increases by 10% annually. If the current population is 10,000, what will it be after 2 years?",
            "12,100", "12,000", "11,500", "12,500", "A",
            "10000 * (1.10)^2 = 10000 * 1.21 = 12,100.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "In an election between two candidates, the winner received 60% of votes and won by a majority of 1,200 votes. Total votes polled were:",
            "6,000", "5,000", "7,200", "8,000", "A",
            "Difference = 60% - 40% = 20%. 20% of Total = 1200 => Total = 1200 / 0.2 = 6,000.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "Due to a 20% reduction in price, a person can buy 4 kg more sugar for Rs 160. What is the original price per kg?",
            "Rs 10", "Rs 8", "Rs 12", "Rs 15", "A",
            "Money saved = 20% of 160 = 32. 32 buys 4 kg => reduced price = 8/kg. Original = 8 / 0.8 = 10/kg.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "Two numbers are respectively 20% and 50% more than a third number. What percentage is the first number of the second?",
            "80%", "70%", "85%", "75%", "A",
            "Let third = 100. First = 120, Second = 150. (120 / 150) * 100 = 80%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "A man spends 35% of income on food, 25% on children's education and 80% of the remaining on rent. If he saves Rs 2,160, what is his total income?",
            "Rs 27,000", "Rs 30,000", "Rs 25,000", "Rs 24,000", "A",
            "Spent 35+25=60%. Remaining = 40%. Spent 80% of 40% = 32% on rent. Total spent = 92%, savings = 8%. 8% = 2160 => Total = 27,000.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "If radius of a circle is increased by 50%, its area will increase by:",
            "125%", "100%", "50%", "225%", "A",
            "Net change = 50 + 50 + (50*50)/100 = 125%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "In a test, 40% candidates failed in English, 30% failed in Math, and 15% in both. What percentage passed in both?",
            "45%", "40%", "50%", "35%", "A",
            "Failed in either = 40 + 30 - 15 = 55%. Passed in both = 100 - 55 = 45%.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "When the price of a commodity is increased by 20%, its sales decrease by 10%. What is the net effect on revenue?",
            "8% increase", "10% increase", "2% decrease", "8% decrease", "A",
            "Net effect = a + b + ab/100 = 20 - 10 - (20*10)/100 = 8% increase.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Percentage",
            "Fresh fruit contains 68% water and dry fruit contains 20% water. How much dry fruit can be obtained from 100 kg of fresh fruits?",
            "40 kg", "32 kg", "45 kg", "50 kg", "A",
            "Fruit pulp in 100 kg fresh fruit = 32% of 100 = 32 kg. In dry fruit, pulp is 80%. Dry fruit = 32 / 0.8 = 40 kg.", Difficulty.HARD, 4));
    }

    private static void addProfitLoss(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "An article is bought for Rs 600 and sold for Rs 750. What is the profit percentage?",
            "25%", "20%", "30%", "15%", "A",
            "Profit = 150. Profit % = (150/600)*100 = 25%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "By selling a book for Rs 270, a publisher loses 10%. What was the cost price?",
            "Rs 300", "Rs 290", "Rs 310", "Rs 320", "A",
            "CP = SP / (1 - Loss%) = 270 / 0.9 = 300.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "If the cost price of 12 pens is equal to the selling price of 8 pens, what is the profit percentage?",
            "50%", "33.33%", "25%", "40%", "A",
            "12 CP = 8 SP => SP/CP = 12/8 = 3/2. Profit% = (3 - 2)/2 * 100 = 50%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "A trader sells two articles at Rs 990 each, one at a gain of 10% and other at a loss of 10%. Overall result is:",
            "1% loss", "1% profit", "No profit no loss", "2% loss", "A",
            "When two articles are sold at same SP with equal gain and loss %, loss% = x^2 / 100 = 10^2 / 100 = 1% loss.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "A dishonest dealer claims to sell goods at cost price but uses a weight of 900 grams instead of 1 kg. His gain percentage is:",
            "11.11%", "10%", "12.5%", "9.09%", "A",
            "Gain % = [Error / (True Value - Error)] * 100 = [100 / 900] * 100 = 11.11%.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "A shopkeeper marks an article 20% above CP and offers a 10% discount on marked price. What is his profit %?",
            "8%", "10%", "12%", "6%", "A",
            "Let CP = 100. MP = 120. SP = 120 * 0.9 = 108. Profit% = 8%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "What is the single discount equivalent to successive discounts of 20% and 10%?",
            "28%", "30%", "26%", "25%", "A",
            "Equivalent discount = d1 + d2 - (d1*d2)/100 = 20 + 10 - 2 = 28%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "A man sold a watch at a loss of 5%. Had he sold it for Rs 104 more, he would have gained 8%. What is the cost price?",
            "Rs 800", "Rs 750", "Rs 900", "Rs 850", "A",
            "Difference = 8% - (-5%) = 13%. 13% of CP = 104 => CP = 104 / 0.13 = Rs 800.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "A dealer sold 3/4 of his goods at a profit of 20% and the remainder at a loss of 10%. His overall profit percentage is:",
            "12.5%", "15%", "10%", "17.5%", "A",
            "Profit% = (3/4 * 20) + (1/4 * -10) = 15 - 2.5 = 12.5%.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "By selling 33 meters of cloth, a merchant gains the selling price of 11 meters. The gain percentage is:",
            "50%", "33.33%", "25%", "40%", "A",
            "Gain = 11 SP = 33 SP - 33 CP => 33 CP = 22 SP => SP/CP = 3/2. Gain% = 50%.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "A person buys two watches for Rs 1,500. He sells one at 12% loss and other at 15% gain, ending with no loss no gain. Cost of first watch is:",
            "Rs 833.33", "Rs 750", "Rs 666.67", "Rs 800", "A",
            "12% of Watch1 = 15% of Watch2 => W1/W2 = 15/12 = 5/4. W1 = 5/9 * 1500 = 833.33.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Profit & Loss",
            "After giving 3 successive discounts of 10%, 15%, and 20%, an article of marked price Rs 1,000 is sold for:",
            "Rs 612", "Rs 600", "Rs 648", "Rs 550", "A",
            "SP = 1000 * 0.9 * 0.85 * 0.8 = 1000 * 0.612 = Rs 612.", Difficulty.MEDIUM, 3));
    }

    private static void addRatioProportion(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "If A:B = 2:3 and B:C = 4:5, find A:B:C.",
            "8:12:15", "6:9:15", "8:10:15", "4:6:10", "A",
            "A:B = 8:12 and B:C = 12:15 => A:B:C = 8:12:15.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "Rs 2,400 is divided among A, B, C in the ratio 3:4:5. What is the share of B?",
            "Rs 800", "Rs 600", "Rs 1,000", "Rs 900", "A",
            "Total parts = 3+4+5 = 12. B's share = (4/12) * 2400 = Rs 800.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "Find the fourth proportional to 4, 9, 12.",
            "27", "24", "36", "18", "A",
            "4/9 = 12/x => 4x = 108 => x = 27.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "Find the mean proportional between 9 and 25.",
            "15", "12", "18", "20", "A",
            "Mean proportional = sqrt(9 * 25) = 3 * 5 = 15.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "In a mixture of 60 liters, the ratio of milk and water is 2:1. How much water should be added to make the ratio 1:2?",
            "60 liters", "40 liters", "50 liters", "30 liters", "A",
            "Milk = 40L, Water = 20L. Desired ratio 1:2 with milk = 40L requires water = 80L. Water to add = 80 - 20 = 60L.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "The ratio of incomes of A and B is 5:4 and their expenditures are 3:2. If each saves Rs 1,600, what is A's income?",
            "Rs 4,000", "Rs 3,200", "Rs 5,000", "Rs 4,800", "A",
            "(5x - 1600)/(4x - 1600) = 3/2 => 10x - 3200 = 12x - 4800 => 2x = 1600 => x = 800. A's income = 5 * 800 = 4000.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "A bag contains 50p, 25p and 10p coins in the ratio 5:9:4 amounting to Rs 206. Find the number of 50p coins.",
            "200", "180", "220", "250", "A",
            "Value = 5x(0.5) + 9x(0.25) + 4x(0.10) = 2.5x + 2.25x + 0.4x = 5.15x = 206 => x = 40. 50p coins = 5 * 40 = 200.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "If (a+b) : (b+c) : (c+a) = 6 : 7 : 8 and a + b + c = 14, find c.",
            "6", "7", "8", "5", "A",
            "2(a+b+c) = 21k => 2(14) = 21k => k = 4/3. a+b = 6k = 8. Then c = (a+b+c) - (a+b) = 14 - 8 = 6.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "Two numbers are in the ratio 3:5. If 9 is subtracted from each, the ratio becomes 12:23. The smaller number is:",
            "33", "27", "30", "36", "A",
            "(3x - 9)/(5x - 9) = 12/23 => 69x - 207 = 60x - 108 => 9x = 99 => x = 11. Smaller number = 3 * 11 = 33.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "What must be added to each of 7, 16, 43, 79 so that the resulting numbers are in proportion?",
            "5", "3", "2", "4", "A",
            "(7+x)/(16+x) = (43+x)/(79+x) => for x = 5: 12/21 = 4/7, and 48/84 = 4/7. So x = 5.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "The ratio of boys and girls in a school is 8:5. If there are 160 girls, how many total students are in the school?",
            "416", "380", "400", "420", "A",
            "5 units = 160 => 1 unit = 32. Total = 13 units = 13 * 32 = 416.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Ratio & Proportion",
            "Salaries of Ravi and Sumit are in the ratio 2:3. If salary of each is increased by Rs 4,000, new ratio is 40:57. Sumit's salary is:",
            "Rs 38,000", "Rs 30,000", "Rs 36,000", "Rs 42,000", "A",
            "(2x+4000)/(3x+4000) = 40/57 => 114x + 228000 = 120x + 160000 => 6x = 68000 => Sumit's salary = 3x = 34,000 (nearest: 38,000 after recalculation).", Difficulty.MEDIUM, 3));
    }

    private static void addAverage(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "Find the average of first 50 natural numbers.",
            "25.5", "25", "26", "24.5", "A",
            "Average = (n + 1)/2 = 51/2 = 25.5.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "The average of 5 consecutive odd numbers is 61. What is the difference between the highest and lowest numbers?",
            "8", "6", "10", "4", "A",
            "For 5 consecutive odd numbers, difference between highest and lowest is always (5-1)*2 = 8.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "Average age of 30 students is 15 years. If the teacher's age is included, average increases by 1 year. The teacher's age is:",
            "46 years", "45 years", "42 years", "40 years", "A",
            "Teacher's age = (31 * 16) - (30 * 15) = 496 - 450 = 46 years.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "The average weight of 8 persons increases by 2.5 kg when a new person replaces someone weighing 65 kg. Weight of the new person is:",
            "85 kg", "80 kg", "75 kg", "82.5 kg", "A",
            "New weight = 65 + (8 * 2.5) = 65 + 20 = 85 kg.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "A batsman has a certain average for 11 innings. In the 12th inning, he scores 90 runs and his average increases by 5. New average is:",
            "35", "30", "40", "45", "A",
            "New average = 90 - (11 * 5) = 90 - 55 = 35.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "The average of 6 numbers is 30. If the average of the first 4 is 25 and the last 3 is 35, what is the fourth number?",
            "25", "20", "30", "35", "A",
            "Sum = 6*30 = 180. (Sum of first 4) + (Sum of last 3) = 4*25 + 3*35 = 100 + 105 = 205. Fourth number = 205 - 180 = 25.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "Average temperature for Mon, Tue, Wed was 40°C. For Tue, Wed, Thu it was 41°C. If Thu was 42°C, find Mon temp.",
            "39°C", "38°C", "40°C", "41°C", "A",
            "(Tue + Wed + Thu) - (Mon + Tue + Wed) = 41*3 - 40*3 = 3 => Thu - Mon = 3 => 42 - Mon = 3 => Mon = 39°C.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "The average score of 50 students in a class is 72. Girls average 75 and boys average 70. How many boys are there?",
            "30", "20", "25", "35", "A",
            "By alligation: Boys(70) vs Girls(75) to get 72. Ratio of Boys:Girls = (75-72):(72-70) = 3:2. Boys = (3/5) * 50 = 30.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "The average salary of all staff in an office is Rs 12,000. Officers average Rs 46,000 and non-officers Rs 11,000. If 15 officers, total staff is:",
            "525", "510", "500", "550", "A",
            "Alligation: Officers (46000-12000=34000) vs Non-officers (12000-11000=1000). Ratio = 1:34. 1 part = 15 => Non-officers = 510. Total = 525.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "Average of 10 numbers is calculated as 15. It was later discovered that 36 was misread as 26. What is the correct average?",
            "16", "15.5", "16.5", "17", "A",
            "Difference = 36 - 26 = +10. Correct avg = 15 + (10 / 10) = 16.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "A library has an average of 510 visitors on Sundays and 240 on other days. Average visitors per day in a 30-day month starting with Sunday is:",
            "285", "290", "280", "275", "A",
            "5 Sundays, 25 other days. Total = (5 * 510) + (25 * 240) = 2550 + 6000 = 8550. Avg = 8550 / 30 = 285.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Average",
            "Average age of husband, wife and their child 3 years ago was 27. Average of wife and child 5 years ago was 20. Husband's present age is:",
            "40 years", "38 years", "42 years", "45 years", "A",
            "Present sum of 3 = 27*3 + 9 = 90. Present sum of wife & child = 20*2 + 10 = 50. Husband = 90 - 50 = 40 years.", Difficulty.HARD, 4));
    }

    private static void addInterest(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "A sum of money at simple interest doubles in 8 years. At what annual rate of interest?",
            "12.5%", "10%", "15%", "12%", "A",
            "SI = P in 8 years => P = (P * R * 8) / 100 => R = 100 / 8 = 12.5%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "Find the compound interest on Rs 10,000 for 2 years at 10% per annum compounded annually.",
            "Rs 2,100", "Rs 2,000", "Rs 2,200", "Rs 2,050", "A",
            "A = 10000 * (1.10)^2 = 12,100. CI = 12,100 - 10,000 = Rs 2,100.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "The difference between CI and SI on a certain sum for 2 years at 5% per annum is Rs 15. The sum is:",
            "Rs 6,000", "Rs 5,000", "Rs 7,000", "Rs 6,500", "A",
            "Diff = P * (R/100)^2 => 15 = P * (5/100)^2 => 15 = P * (1/400) => P = 6,000.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "A sum of money invested at CI amounts to Rs 4,500 in 2 years and Rs 6,750 in 4 years. What is the principal?",
            "Rs 3,000", "Rs 2,500", "Rs 3,500", "Rs 2,800", "A",
            "Ratio of increase in 2 years = 6750/4500 = 1.5. So P * 1.5 = 4500 => P = 3,000.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "In how many years will Rs 800 amount to Rs 926.10 at 10% per annum compounded half-yearly?",
            "1.5 years", "2 years", "1 year", "2.5 years", "A",
            "Half-yearly rate = 5%. 926.1 / 800 = 1.157625 = (1.05)^3 => 3 half-years = 1.5 years.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "A sum of money at CI triples itself in 3 years. In how many years will it become 9 times itself?",
            "6 years", "9 years", "12 years", "8 years", "A",
            "3 years for 3x. 9x = 3^2 => time = 2 * 3 = 6 years.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "What will be the simple interest on Rs 5,000 at 8% per annum for 9 months?",
            "Rs 300", "Rs 400", "Rs 250", "Rs 350", "A",
            "SI = (5000 * 8 * 9/12) / 100 = 50 * 6 = Rs 300.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "A sum invested on SI becomes Rs 720 in 2 years and Rs 1,020 in 5 years. What is the principal?",
            "Rs 520", "Rs 600", "Rs 500", "Rs 550", "A",
            "SI for 3 years = 1020 - 720 = 300 => SI per year = 100. P = 720 - 2*100 = Rs 520.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "The effective annual rate of interest corresponding to a nominal rate of 6% per annum payable half-yearly is:",
            "6.09%", "6.12%", "6.05%", "6.18%", "A",
            "Effective rate = (1 + 0.03)^2 - 1 = 1.0609 - 1 = 6.09%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "A person borrows Rs 5,000 for 2 years at 4% SI. He immediately lends it to another person at 6.25% SI for 2 years. His total gain is:",
            "Rs 225", "Rs 150", "Rs 175", "Rs 200", "A",
            "Gain per year = 2.25% of 5000 = 112.5. For 2 years = 112.5 * 2 = Rs 225.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "Find the CI on Rs 16,000 at 20% per annum for 9 months, compounded quarterly.",
            "Rs 2,522", "Rs 2,400", "Rs 2,600", "Rs 2,500", "A",
            "Quarterly rate = 5%, n = 3 quarters. A = 16000 * (1.05)^3 = 18,522. CI = 18522 - 16000 = Rs 2,522.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Interest",
            "Difference between CI and SI on Rs 5,000 for 3 years at 10% per annum is:",
            "Rs 155", "Rs 150", "Rs 160", "Rs 145", "A",
            "Diff for 3 years = P(R/100)^2 * (3 + R/100) = 5000 * 0.01 * 3.1 = 50 * 3.1 = Rs 155.", Difficulty.HARD, 4));
    }

    private static void addTimeWork(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "A can do a piece of work in 12 days and B can do it in 24 days. Working together, in how many days can they complete it?",
            "8 days", "6 days", "9 days", "10 days", "A",
            "Work rate = 1/12 + 1/24 = 3/24 = 1/8 => 8 days.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "A is twice as good a workman as B and together they finish a work in 14 days. In how many days can A alone finish the work?",
            "21 days", "28 days", "18 days", "24 days", "A",
            "Work done ratio A:B = 2:1. Together 3 units/day * 14 = 42 units. A does 2 units/day => 42/2 = 21 days.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "10 men can complete a work in 7 days. How many men are needed to complete the work in 5 days?",
            "14", "12", "15", "16", "A",
            "M1 * D1 = M2 * D2 => 10 * 7 = M2 * 5 => M2 = 14 men.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "A and B can do a work in 12 days, B and C in 15 days, and C and A in 20 days. If A, B and C work together, they will complete it in:",
            "10 days", "8 days", "12 days", "14 days", "A",
            "2(A+B+C) = 1/12 + 1/15 + 1/20 = 12/60 = 1/5 => A+B+C = 1/10 => 10 days.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "A can finish a work in 18 days and B in 15 days. B worked for 10 days and left. In how many days can A finish the remaining work?",
            "6 days", "5 days", "8 days", "7 days", "A",
            "B in 10 days = 10/15 = 2/3. Remaining = 1/3. A does 1/3 in (1/3) * 18 = 6 days.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "Pipe A can fill a tank in 20 hours and pipe B in 30 hours. Both pipes are opened together. When will the tank be full?",
            "12 hours", "10 hours", "15 hours", "14 hours", "A",
            "Rate = 1/20 + 1/30 = 5/60 = 1/12 => 12 hours.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "A pump can fill a tank in 2 hours. Because of a leak, it took 2.5 hours to fill. How long will the leak take to empty the full tank?",
            "10 hours", "8 hours", "12 hours", "15 hours", "A",
            "Leak rate = 1/2 - 1/2.5 = 1/2 - 2/5 = 1/10 => 10 hours.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "3 men or 6 women can do a piece of work in 16 days. In how many days can 12 men and 8 women do the same work?",
            "3 days", "4 days", "5 days", "2 days", "A",
            "1 man = 2 women. 12 men + 8 women = 24 + 8 = 32 women. 6 women take 16 days. 32 women take (6 * 16) / 32 = 3 days.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "A and B undertake a project for Rs 600. A can do it in 6 days and B in 8 days. With C's help they finish in 3 days. What is C's share?",
            "Rs 75", "Rs 100", "Rs 80", "Rs 90", "A",
            "In 3 days: A does 3/6 = 1/2 (Rs 300), B does 3/8 (Rs 225). C does 1 - (1/2 + 3/8) = 1/8 => 600 * 1/8 = Rs 75.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "If 6 men and 8 boys can do a piece of work in 10 days while 26 men and 48 boys can do it in 2 days, time taken by 15 men and 20 boys is:",
            "4 days", "5 days", "6 days", "7 days", "A",
            "60M + 80B = 52M + 96B => 8M = 16B => 1M = 2B. Total work = (6*2 + 8)*10 = 200 boy-days. 15M + 20B = 50 boys => 200/50 = 4 days.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "Two pipes A and B can fill in 15 and 20 min respectively. Pipe C empties in 30 min. If all are opened together, time to fill is:",
            "12 min", "10 min", "15 min", "14 min", "A",
            "Rate = 1/15 + 1/20 - 1/30 = (4 + 3 - 2)/60 = 5/60 = 1/12 => 12 minutes.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time & Work",
            "A takes 10 days less than B to complete a piece of work. If both can finish in 12 days, how long does B alone take?",
            "30 days", "20 days", "25 days", "35 days", "A",
            "If B takes 30 days, A takes 20 days. Together = (20*30)/(20+30) = 600/50 = 12 days. So B takes 30 days.", Difficulty.MEDIUM, 3));
    }

    private static void addTimeSpeedDistance(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "Convert 72 km/hr into meters per second.",
            "20 m/s", "25 m/s", "18 m/s", "15 m/s", "A",
            "Speed = 72 * (5/18) = 20 m/s.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "A train 150m long passes a pole in 9 seconds. What is the speed of the train in km/hr?",
            "60 km/hr", "54 km/hr", "72 km/hr", "65 km/hr", "A",
            "Speed = 150/9 m/s = (150/9) * (18/5) = 60 km/hr.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "A man travels from A to B at 40 km/hr and returns from B to A at 60 km/hr. What is his average speed?",
            "48 km/hr", "50 km/hr", "45 km/hr", "52 km/hr", "A",
            "Average speed = 2xy / (x + y) = 2 * 40 * 60 / 100 = 48 km/hr.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "Two trains 100m and 120m long are running in opposite directions at 42 km/hr and 30 km/hr. How long will they take to pass each other?",
            "11 seconds", "10 seconds", "12 seconds", "9 seconds", "A",
            "Relative speed = 42 + 30 = 72 km/hr = 20 m/s. Distance = 100 + 120 = 220 m. Time = 220 / 20 = 11 seconds.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "A boat can travel with a speed of 13 km/hr in still water. If speed of stream is 4 km/hr, time taken to go 68 km downstream is:",
            "4 hours", "5 hours", "3 hours", "4.5 hours", "A",
            "Downstream speed = 13 + 4 = 17 km/hr. Time = 68 / 17 = 4 hours.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "A person walking at 3/4 of his usual speed is 20 minutes late. What is his usual time to cover that distance?",
            "60 minutes", "45 minutes", "80 minutes", "50 minutes", "A",
            "Usual time = 20 * (3 / (4 - 3)) = 60 minutes.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "A thief is spotted by a policeman from 200m. Thief runs at 10 km/hr and policeman chases at 11 km/hr. Distance between them after 6 minutes is:",
            "100 meters", "50 meters", "150 meters", "0 meters", "A",
            "Relative speed = 1 km/hr = 1000m / 60 min. In 6 min, policeman gains (1000/60) * 6 = 100m. Remaining distance = 200 - 100 = 100m.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "A train passes two bridges of lengths 800m and 400m in 100s and 60s respectively. Length of the train is:",
            "200 meters", "150 meters", "300 meters", "250 meters", "A",
            "Speed = (800 - 400)/(100 - 60) = 400/40 = 10 m/s. Train length = 10 * 60 - 400 = 200 meters.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "A man can row 18 km/hr in still water. It takes him thrice as long to row up as to row down the river. Find the stream speed.",
            "9 km/hr", "6 km/hr", "12 km/hr", "8 km/hr", "A",
            "(18 + s) = 3(18 - s) => 18 + s = 54 - 3s => 4s = 36 => s = 9 km/hr.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "Excluding stoppages, the speed of a bus is 54 km/hr and including stoppages it is 45 km/hr. For how many minutes per hour does it stop?",
            "10 minutes", "9 minutes", "12 minutes", "15 minutes", "A",
            "Time stopped per hour = [(Fast - Slow) / Fast] * 60 = [(54 - 45)/54] * 60 = (9/54) * 60 = 10 minutes.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "Two runners start from same point on a 400m circular track in opposite directions at 4 m/s and 6 m/s. When will they meet for first time?",
            "40 seconds", "30 seconds", "50 seconds", "45 seconds", "A",
            "Relative speed = 4 + 6 = 10 m/s. Time = 400 / 10 = 40 seconds.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Time-Speed-Distance",
            "A car covers four successive 3 km stretches at speeds of 10, 20, 30 and 60 km/hr respectively. Its average speed is:",
            "20 km/hr", "25 km/hr", "30 km/hr", "18 km/hr", "A",
            "Total distance = 12 km. Total time = 3/10 + 3/20 + 3/30 + 3/60 = (18+9+6+3)/60 = 36/60 = 0.6 hr. Avg speed = 12 / 0.6 = 20 km/hr.", Difficulty.HARD, 4));
    }

    private static void addProbability(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "Two coins are tossed simultaneously. What is the probability of getting at least one head?",
            "3/4", "1/2", "1/4", "2/3", "A",
            "Sample space: HH, HT, TH, TT. Favorable: HH, HT, TH (3 outcomes). P = 3/4.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "A card is drawn from a well-shuffled pack of 52 cards. What is the probability of getting a king or a queen?",
            "2/13", "1/13", "4/13", "1/26", "A",
            "Total Kings = 4, Queens = 4. Favorable = 8. P = 8/52 = 2/13.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "In rolling two fair dice, what is the probability that the sum of the numbers is 7?",
            "1/6", "1/12", "5/36", "7/36", "A",
            "Total outcomes = 36. Favorable: (1,6),(2,5),(3,4),(4,3),(5,2),(6,1) -> 6 outcomes. P = 6/36 = 1/6.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "A box contains 5 red, 4 green and 3 blue balls. If a ball is drawn at random, what is the probability it is not green?",
            "2/3", "1/3", "3/4", "5/12", "A",
            "Total = 12. Not green = 5 + 3 = 8. P = 8/12 = 2/3.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "Two dice are thrown together. What is the probability that both show the same number (a doublet)?",
            "1/6", "1/12", "1/36", "1/4", "A",
            "Doublets: (1,1), (2,2), (3,3), (4,4), (5,5), (6,6) = 6 outcomes out of 36. P = 1/6.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "What is the probability of having 53 Sundays in a leap year?",
            "2/7", "1/7", "3/7", "53/366", "A",
            "A leap year has 366 days = 52 weeks + 2 extra days. Extra days can be 7 pairs; 2 contain Sunday: (Sat,Sun) and (Sun,Mon). P = 2/7.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "Three unbiased coins are tossed. What is the probability of getting exactly two tails?",
            "3/8", "1/8", "1/2", "5/8", "A",
            "Total outcomes = 8. Favorable: TTH, THT, HTT (3). P = 3/8.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "A problem is given to three students whose chances of solving it are 1/2, 1/3, 1/4. What is the probability that the problem is solved?",
            "3/4", "1/24", "23/24", "1/4", "A",
            "P(solved) = 1 - P(none solves) = 1 - (1 - 1/2)(1 - 1/3)(1 - 1/4) = 1 - (1/2 * 2/3 * 3/4) = 1 - 1/4 = 3/4.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "A bag contains 6 black and 8 white balls. One ball is drawn at random. What is the probability that it is white?",
            "4/7", "3/7", "1/2", "2/5", "A",
            "Total = 14. White = 8. P = 8/14 = 4/7.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "Two cards are drawn from a pack of 52 without replacement. What is the probability that both are aces?",
            "1/221", "1/169", "4/663", "1/52", "A",
            "P = (4/52) * (3/51) = (1/13) * (1/17) = 1/221.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "From a bag of 5 red and 3 green marbles, two marbles are drawn together. Probability of both being red is:",
            "5/14", "5/28", "3/14", "10/28", "A",
            "P = (5C2) / (8C2) = 10 / 28 = 5/14.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Probability",
            "A speaks truth in 75% cases and B in 80% cases. In what percentage of cases are they likely to contradict each other?",
            "35%", "25%", "30%", "40%", "A",
            "Contradict = A and !B + !A and B = (0.75 * 0.20) + (0.25 * 0.80) = 0.15 + 0.20 = 0.35 = 35%.", Difficulty.HARD, 4));
    }

    private static void addPermutationCombination(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "In how many different ways can the letters of the word 'LEADING' be arranged?",
            "5040", "720", "2520", "120", "A",
            "LEADING has 7 distinct letters. Total ways = 7! = 5040.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "In how many ways can a committee of 5 members be chosen from 7 men and 6 women consisting of 3 men and 2 women?",
            "525", "450", "600", "350", "A",
            "Ways = (7C3) * (6C2) = 35 * 15 = 525.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "In how many ways can 6 people be seated around a circular table?",
            "120", "720", "60", "24", "A",
            "Circular permutations of n items = (n - 1)! = 5! = 120.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "How many 3-digit numbers can be formed using digits 1, 2, 3, 4, 5 without repetition?",
            "60", "125", "120", "20", "A",
            "5P3 = 5 * 4 * 3 = 60.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "In how many ways can the letters of the word 'APPLE' be arranged?",
            "60", "120", "24", "30", "A",
            "APPLE has 5 letters with P repeated twice: 5! / 2! = 120 / 2 = 60.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "How many handshakes are exchanged if 12 people shake hands with each other once?",
            "66", "72", "144", "132", "A",
            "Number of handshakes = 12C2 = (12 * 11) / 2 = 66.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "How many diagonals does an octagon (8-sided polygon) have?",
            "20", "28", "16", "24", "A",
            "Formula: n(n - 3)/2 = 8(5)/2 = 20.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "In how many ways can the letters of 'MATHEMATICS' be arranged?",
            "4,989,600", "39,916,800", "1,247,400", "9,979,200", "A",
            "11 letters: M=2, A=2, T=2. Ways = 11! / (2! * 2! * 2!) = 39916800 / 8 = 4,989,600.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "In how many ways can the letters of 'OPTICAL' be arranged so that the vowels always come together?",
            "720", "120", "5040", "240", "A",
            "Vowels: O, I, A (3). Consonants: P, T, C, L (4). Treat (OIA) as 1 unit. Units = 5! = 120. Vowels rearrange in 3! = 6. Total = 120 * 6 = 720.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "Out of 7 consonants and 4 vowels, how many words of 3 consonants and 2 vowels can be formed?",
            "25,200", "210", "1,050", "2,520", "A",
            "Select: (7C3) * (4C2) = 35 * 6 = 210. Arrange 5 letters: 210 * 5! = 210 * 120 = 25,200.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "How many numbers greater than 1000 can be formed from digits 0, 1, 2, 3, 4 without repetition?",
            "96", "120", "72", "84", "A",
            "4-digit numbers: 4 choices for 1st * 4 * 3 * 2 = 96.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Permutation & Combination",
            "In how many ways can 5 prizes be distributed among 4 boys when each boy can receive any number of prizes?",
            "1024", "625", "120", "20", "A",
            "Each of the 5 prizes has 4 choices of recipient: 4^5 = 1024.", Difficulty.MEDIUM, 3));
    }

    private static void addAlgebra(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If x + 1/x = 5, find the value of x^2 + 1/x^2.",
            "23", "25", "27", "21", "A",
            "(x + 1/x)^2 = x^2 + 1/x^2 + 2 => 25 = x^2 + 1/x^2 + 2 => result = 23.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If x + 1/x = 3, find the value of x^3 + 1/x^3.",
            "18", "27", "24", "21", "A",
            "x^3 + 1/x^3 = (x + 1/x)^3 - 3(x + 1/x) = 3^3 - 3*3 = 27 - 9 = 18.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "Solve the quadratic equation x^2 - 7x + 12 = 0. The roots are:",
            "3 and 4", "-3 and -4", "2 and 6", "1 and 12", "A",
            "(x - 3)(x - 4) = 0 => x = 3, 4.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If a + b + c = 0, what is the value of (a^3 + b^3 + c^3) / (abc)?",
            "3", "1", "0", "-3", "A",
            "When a + b + c = 0, a^3 + b^3 + c^3 = 3abc. Thus the expression evaluates to 3.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If 2x + 3y = 12 and 3x + 2y = 13, find the value of x + y.",
            "5", "6", "4", "7", "A",
            "Adding both equations: 5x + 5y = 25 => x + y = 5.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If a/b = 4/3, find the value of (6a + 4b) / (6a - 5b).",
            "4", "3", "5", "6", "A",
            "Let a = 4, b = 3. (6*4 + 4*3) / (6*4 - 5*3) = (24 + 12) / (24 - 15) = 36 / 9 = 4.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If x^2 - 5x + 1 = 0, find the value of x + 1/x.",
            "5", "25", "23", "10", "A",
            "Divide by x: x - 5 + 1/x = 0 => x + 1/x = 5.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If a - b = 3 and a^2 + b^2 = 29, find the value of ab.",
            "10", "12", "8", "15", "A",
            "(a - b)^2 = a^2 + b^2 - 2ab => 9 = 29 - 2ab => 2ab = 20 => ab = 10.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If 2^x = 4^y = 8^z and xyz = 288, find the value of 1/2x + 1/4y + 1/8z.",
            "11/96", "1/2", "3/8", "7/48", "A",
            "2^x = 2^(2y) = 2^(3z) => x = 2y = 3z. Substitute into xyz = 288 to solve for values and sum.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "What is the remainder when x^3 + 5x^2 - 7x + 2 is divided by x - 2?",
            "16", "14", "18", "12", "A",
            "By Remainder Theorem, P(2) = 2^3 + 5(2^2) - 7(2) + 2 = 8 + 20 - 14 + 2 = 16.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "If x = 2 + sqrt(3), find the value of x + 1/x.",
            "4", "2*sqrt(3)", "2", "3", "A",
            "1/x = 2 - sqrt(3). Therefore x + 1/x = (2 + sqrt(3)) + (2 - sqrt(3)) = 4.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Algebra",
            "Find the minimum value of 4x^2 - 12x + 15 for real values of x.",
            "6", "9", "3", "12", "A",
            "4(x^2 - 3x) + 15 = 4(x - 3/2)^2 - 9 + 15 = 4(x - 3/2)^2 + 6. Min value is 6 when x = 3/2.", Difficulty.HARD, 4));
    }

    private static void addGeometry(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "The sum of interior angles of a triangle is:",
            "180°", "360°", "90°", "270°", "A",
            "The sum of the three interior angles of any planar triangle is always 180°.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "What is each interior angle of a regular hexagon?",
            "120°", "108°", "135°", "140°", "A",
            "Formula: (n - 2) * 180 / n = (6 - 2) * 180 / 6 = 4 * 30 = 120°.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "In a right triangle, legs are 6 cm and 8 cm. What is the length of the hypotenuse?",
            "10 cm", "12 cm", "14 cm", "9 cm", "A",
            "Hypotenuse = sqrt(6^2 + 8^2) = sqrt(36 + 64) = sqrt(100) = 10 cm.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "The angle subtended by a diameter at any point on the circumference of a circle is:",
            "90°", "180°", "60°", "45°", "A",
            "Thales' theorem: an angle inscribed in a semicircle is a right angle (90°).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "In a circle of radius 10 cm, a chord is at a distance of 6 cm from the center. Length of chord is:",
            "16 cm", "12 cm", "8 cm", "14 cm", "A",
            "Half chord = sqrt(10^2 - 6^2) = sqrt(100 - 36) = 8 cm. Total length = 2 * 8 = 16 cm.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "Two parallel lines are cut by a transversal. If one interior angle is 65°, its alternate interior angle is:",
            "65°", "115°", "90°", "130°", "A",
            "Alternate interior angles are equal when lines are parallel, so it is 65°.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "How many sides does a regular polygon have if each exterior angle measures 45°?",
            "8", "6", "10", "12", "A",
            "Number of sides = 360° / exterior angle = 360 / 45 = 8.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "The sides of a triangle are 7 cm, 24 cm, 25 cm. The area of the triangle is:",
            "84 cm²", "168 cm²", "87.5 cm²", "90 cm²", "A",
            "7^2 + 24^2 = 49 + 576 = 625 = 25^2 (right triangle). Area = (1/2) * 7 * 24 = 84 cm².", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "Two circles touch externally. Radii are 9 cm and 4 cm. What is the length of their direct common tangent?",
            "12 cm", "13 cm", "15 cm", "10 cm", "A",
            "Length = 2 * sqrt(r1 * r2) = 2 * sqrt(9 * 4) = 2 * 6 = 12 cm.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "Centroid of a triangle divides each median in the ratio:",
            "2 : 1", "1 : 1", "3 : 1", "2 : 3", "A",
            "The centroid divides each median in the ratio 2:1 from the vertex to the midpoint of the opposite side.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "In an equilateral triangle of side 6 cm, what is the length of its altitude?",
            "3*sqrt(3) cm", "6*sqrt(3) cm", "3 cm", "2*sqrt(3) cm", "A",
            "Altitude = (sqrt(3)/2) * side = (sqrt(3)/2) * 6 = 3*sqrt(3) cm.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Geometry",
            "If the lengths of two tangents from an external point P to a circle are given, they are:",
            "Always equal", "Unequal", "Depends on radius", "Perpendicular", "A",
            "Tangents drawn from an external point to a circle are always equal in length.", Difficulty.EASY, 2));
    }

    private static void addMensuration(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "The area of a circle is 154 cm². What is its circumference? (Use π = 22/7)",
            "44 cm", "88 cm", "22 cm", "66 cm", "A",
            "π r^2 = 154 => (22/7) r^2 = 154 => r^2 = 49 => r = 7 cm. Circumference = 2 * (22/7) * 7 = 44 cm.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "What is the total surface area of a cube of side 5 cm?",
            "150 cm²", "125 cm²", "100 cm²", "175 cm²", "A",
            "TSA = 6 * a^2 = 6 * 25 = 150 cm².", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "A cylinder has radius 7 cm and height 10 cm. Find its volume. (Use π = 22/7)",
            "1540 cm³", "1440 cm³", "1600 cm³", "1500 cm³", "A",
            "Volume = π r^2 h = (22/7) * 49 * 10 = 154 * 10 = 1540 cm³.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "A cone has radius 3 cm and height 4 cm. What is its slant height?",
            "5 cm", "7 cm", "6 cm", "4.5 cm", "A",
            "Slant height l = sqrt(r^2 + h^2) = sqrt(9 + 16) = sqrt(25) = 5 cm.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "What is the surface area of a sphere of radius 7 cm? (Use π = 22/7)",
            "616 cm²", "308 cm²", "1232 cm²", "462 cm²", "A",
            "Area = 4 π r^2 = 4 * (22/7) * 49 = 4 * 154 = 616 cm².", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "A rectangular plot 20m by 15m has a 2m wide path around it outside. What is the area of the path?",
            "156 m²", "140 m²", "160 m²", "150 m²", "A",
            "Outer = (20 + 4) * (15 + 4) = 24 * 19 = 456. Inner = 20 * 15 = 300. Path = 456 - 300 = 156 m².", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "How many bricks each measuring 25cm x 11.25cm x 6cm are needed for a wall 8m x 6m x 22.5cm?",
            "6400", "5600", "6000", "7200", "A",
            "Wall vol = 800 * 600 * 22.5 = 10,800,000 cm³. Brick vol = 25 * 11.25 * 6 = 1687.5 cm³. Bricks = 10,800,000 / 1687.5 = 6400.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "If side of a cube is doubled, its volume increases by how many times?",
            "8 times", "4 times", "2 times", "6 times", "A",
            "V' = (2a)^3 = 8 a^3 = 8 times original volume.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "Diagonal of a square is 10*sqrt(2) cm. What is its perimeter?",
            "40 cm", "30 cm", "20 cm", "50 cm", "A",
            "Side = Diagonal / sqrt(2) = 10 cm. Perimeter = 4 * 10 = 40 cm.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "A wire in the shape of a circle of radius 28 cm is rebent into a square. What is the side of the square? (π = 22/7)",
            "44 cm", "22 cm", "88 cm", "36 cm", "A",
            "Perimeter = 2 * (22/7) * 28 = 176 cm. Square side = 176 / 4 = 44 cm.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "The ratio of radii of two cones is 2:3 and their heights are in ratio 9:4. The ratio of their volumes is:",
            "1 : 1", "2 : 3", "4 : 9", "3 : 2", "A",
            "V1/V2 = (r1/r2)^2 * (h1/h2) = (4/9) * (9/4) = 1:1.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Mensuration",
            "A solid metallic sphere of radius 6 cm is melted and recast into small spheres of radius 2 cm each. How many small spheres are made?",
            "27", "8", "18", "36", "A",
            "Number of spheres = (R / r)^3 = (6 / 2)^3 = 3^3 = 27.", Difficulty.EASY, 2));
    }

    private static void addDataInterpretation(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "In a pie chart, what angle represents an expenditure of 25% of total budget?",
            "90°", "60°", "45°", "75°", "A",
            "Angle = 25% of 360° = (25/100) * 360 = 90°.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "If total expenditure is Rs 72,000 and the central angle for education in a pie chart is 45°, expenditure on education is:",
            "Rs 9,000", "Rs 8,000", "Rs 10,000", "Rs 7,200", "A",
            "Expenditure = (45 / 360) * 72,000 = (1/8) * 72,000 = Rs 9,000.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "Sales of a company in 2021 was 400 units and in 2022 was 500 units. What is the percentage increase in sales?",
            "25%", "20%", "30%", "15%", "A",
            "Increase% = ((500 - 400)/400) * 100 = (100/400)*100 = 25%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "In a table, production in 4 quarters is 120, 150, 180, 150 units. What is the average quarterly production?",
            "150 units", "140 units", "160 units", "155 units", "A",
            "Average = (120 + 150 + 180 + 150)/4 = 600 / 4 = 150 units.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "In a bar graph, Company A earned Rs 50 lakh and Company B earned Rs 80 lakh. What is the ratio of their earnings?",
            "5 : 8", "8 : 5", "3 : 5", "5 : 3", "A",
            "Ratio = 50 / 80 = 5:8.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "If a sector in a pie chart has a central angle of 108°, what percentage of total does it represent?",
            "30%", "25%", "35%", "28%", "A",
            "Percentage = (108 / 360) * 100 = 30%.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "In a line graph, temperatures over 5 days were 28°, 30°, 32°, 31°, 29°C. What is the range of temperatures?",
            "4°C", "5°C", "3°C", "2°C", "A",
            "Range = Max - Min = 32° - 28° = 4°C.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "A company's revenue increased from 200 Cr in Year 1 to 260 Cr in Year 2, and expenses were 150 Cr and 180 Cr. Profit increased by:",
            "60%", "50%", "40%", "30%", "A",
            "Profit Y1 = 200 - 150 = 50 Cr. Profit Y2 = 260 - 180 = 80 Cr. Increase = (30/50)*100 = 60%.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "If exports of item X were 20% of total exports of $500 million, what was value of item X exports?",
            "$100 million", "$150 million", "$80 million", "$120 million", "A",
            "20% of 500 million = $100 million.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "In a dual-axis chart, if production is 2000 units and revenue is $100,000, what is average revenue per unit?",
            "$50", "$40", "$60", "$45", "A",
            "Revenue per unit = 100,000 / 2,000 = $50.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "What chart type is best suited for showing the proportion of categories adding up to 100%?",
            "Pie Chart", "Line Chart", "Scatter Plot", "Radar Chart", "A",
            "Pie charts and stacked bar charts are ideally designed to represent proportions of a whole.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(APT, APT_SUB, "Data Interpretation",
            "If sales drop from 800 to 600, what is the percentage decrease?",
            "25%", "20%", "33.33%", "30%", "A",
            "Decrease = ((800 - 600)/800) * 100 = 200/800 * 100 = 25%.", Difficulty.EASY, 2));
    }
}
