package com.techprep.config;

import com.techprep.entity.*;
import com.techprep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * QuestionTopUpSeeder — runs after QuestionBankSeeder (@Order(2)).
 * For each topic, checks the current active question count.
 * Only adds questions if count < TARGET (15 per topic).
 * Safe to re-run: uses existsByTopicIdAndQuestionTextIgnoreCase to prevent duplicates.
 */
@Component
@Order(3)
@RequiredArgsConstructor
public class QuestionTopUpSeeder implements CommandLineRunner {

    private static final int TARGET = 15;

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("=== QuestionTopUpSeeder: Checking topic question counts ===");
        List<QSpec> specs = buildAllQuestions();
        Map<Long, Integer> added = new HashMap<>();
        Map<Long, String> topicNames = new HashMap<>();

        for (QSpec q : specs) {
            Category cat = categoryRepository.findByName(q.cat).orElse(null);
            if (cat == null) continue;
            SubCategory sub = subCategoryRepository.findByNameAndCategoryId(q.sub, cat.getId()).orElse(null);
            if (sub == null) continue;
            Topic topic = topicRepository.findByNameAndSubCategoryId(q.topic, sub.getId()).orElse(null);
            if (topic == null) continue;

            topicNames.put(topic.getId(), q.topic);
            long current = questionRepository.countByTopicIdAndActiveTrue(topic.getId());
            int alreadyAdded = added.getOrDefault(topic.getId(), 0);

            if (current + alreadyAdded >= TARGET) continue;
            if (questionRepository.existsByTopicIdAndQuestionTextIgnoreCase(topic.getId(), q.text)) continue;

            Question question = new Question();
            question.setTopic(topic);
            question.setQuestionText(q.text);
            question.setOptionA(q.a);
            question.setOptionB(q.b);
            question.setOptionC(q.c);
            question.setOptionD(q.d);
            question.setCorrectAnswer(q.ans.toUpperCase().trim());
            question.setExplanation(q.explanation);
            question.setDifficulty(q.difficulty);
            question.setMarks(q.marks);
            question.setActive(true);
            questionRepository.save(question);
            added.merge(topic.getId(), 1, Integer::sum);
        }

        // Final report
        System.out.println("\n=== QuestionTopUpSeeder Report ===");
        int total = 0;
        for (Map.Entry<Long, String> entry : topicNames.entrySet()) {
            Long tid = entry.getKey();
            long count = questionRepository.countByTopicIdAndActiveTrue(tid);
            int addedCount = added.getOrDefault(tid, 0);
            System.out.printf("  %-40s  prev=%-3d  added=%-3d  final=%d%n",
                    entry.getValue(),
                    count - addedCount,
                    addedCount,
                    count);
            total += addedCount;
        }
        System.out.println("Total questions added: " + total);
        System.out.println("=== QuestionTopUpSeeder complete ===\n");
    }

    // -------------------------------------------------------------------------
    private record QSpec(String cat, String sub, String topic,
                         String text, String a, String b, String c, String d,
                         String ans, String explanation, Difficulty difficulty, int marks) {}

    private static final String DSA = "DSA";
    private static final String DSA_SUB = "Data Structures & Algorithms";
    private static final String APT = "Aptitude";
    private static final String APT_SUB = "Quantitative Aptitude";
    private static final String RSN = "Reasoning";
    private static final String RSN_SUB = "Logical Reasoning";
    private static final String VRB = "Verbal Ability";
    private static final String VRB_SUB = "Grammar & Vocabulary";
    private static final String COM = "Communication";
    private static final String COM_SUB = "Communication Skills";
    private static final String TCH = "Technical";

    private List<QSpec> buildAllQuestions() {
        List<QSpec> list = new ArrayList<>();
        addDsaArrays(list);
        addDsaStrings(list);
        addDsaHashing(list);
        addDsaTwoPointer(list);
        addDsaSlidingWindow(list);
        addDsaSorting(list);
        addDsaSearching(list);
        addDsaBinarySearch(list);
        addDsaRecursion(list);
        addDsaBacktracking(list);
        addDsaLinkedList(list);
        addDsaStack(list);
        addDsaQueue(list);
        addDsaTrees(list);
        addDsaHeap(list);
        addDsaGraphs(list);
        addDsaGreedy(list);
        addDsaDynamicProgramming(list);
        addDsaBitManipulation(list);
        // Future top-up topics (to be implemented):
        /*
        addAptNumberSystem(list);
        addAptHcfLcm(list);
        addAptPercentage(list);
        addAptProfitLoss(list);
        addAptRatioProportions(list);
        addAptAverage(list);
        addAptInterest(list);
        addAptTimeWork(list);
        addAptTimeSpeedDistance(list);
        addAptProbability(list);
        addAptPermCombination(list);
        addAptAlgebra(list);
        addAptGeometry(list);
        addAptMensuration(list);
        addAptDataInterpretation(list);
        addRsnNumberSeries(list);
        addRsnAlphabetSeries(list);
        addRsnCodingDecoding(list);
        addRsnBloodRelations(list);
        addRsnDirectionSense(list);
        addRsnSyllogism(list);
        addRsnAnalogy(list);
        addRsnClassification(list);
        addRsnSeatingArrangement(list);
        addRsnPuzzles(list);
        addRsnRanking(list);
        addRsnDataSufficiency(list);
        addRsnClock(list);
        addRsnCalendar(list);
        addRsnVennDiagram(list);
        addVrbGrammar(list);
        addVrbTenses(list);
        addVrbArticles(list);
        addVrbPrepositions(list);
        addVrbSva(list);
        addVrbSynonymsAntonyms(list);
        addVrbVocabulary(list);
        addVrbErrorDetection(list);
        addVrbSentenceCorrection(list);
        addVrbFillBlanks(list);
        addVrbParaJumbles(list);
        addVrbReadingComp(list);
        addVrbSentenceCompletion(list);
        addComSpeaking(list);
        addComListening(list);
        addComGroupDiscussion(list);
        addComPresentation(list);
        addComPublicSpeaking(list);
        addComInterviewComm(list);
        addComBodyLanguage(list);
        addComEmailWriting(list);
        addJavaBasics(list);
        addJavaOop(list);
        addJavaCollections(list);
        addJavaExceptions(list);
        addJavaMultithreading(list);
        addJava8(list);
        addJavaJdbc(list);
        addCppBasics(list);
        addCppOop(list);
        addCppStl(list);
        addCppPointers(list);
        addCppMemoryManagement(list);
        addCppExceptions(list);
        addPythonBasics(list);
        addPythonDataTypes(list);
        addPythonFunctions(list);
        addPythonOop(list);
        addPythonExceptions(list);
        addPythonFileHandling(list);
        addJsBasics(list);
        addJsFunctions(list);
        addJsArrays(list);
        addJsObjects(list);
        addJsEs6(list);
        addJavaBackendSpring(list);
        addJavaBackendSpringBoot(list);
        addJavaBackendRestApi(list);
        addJavaBackendSecurity(list);
        addJavaBackendJpa(list);
        addJavaBackendHibernate(list);
        addDbSql(list);
        addDbDbms(list);
        addDbMysql(list);
        addDbPostgresql(list);
        addCsOs(list);
        addCsNetworks(list);
        addCsSoftwareEngineering(list);
        addCsGit(list);
        addCsSystemDesign(list);
        */
        return list;
    }

    // =========================================================================
    // DSA - Arrays
    // =========================================================================
    private void addDsaArrays(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "What is the time complexity of accessing an element in an array by index?",
            "O(1)", "O(n)", "O(log n)", "O(n²)", "A",
            "Array elements are stored in contiguous memory locations, so any element is accessed in constant time O(1) using its index.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "Given array [1, 2, 3, 4, 5], what is the output of rotating it right by 2 positions?",
            "[3, 4, 5, 1, 2]", "[4, 5, 1, 2, 3]", "[2, 3, 4, 5, 1]", "[5, 4, 3, 2, 1]", "B",
            "Right rotation by 2: last 2 elements [4,5] move to front → [4, 5, 1, 2, 3].",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "What is the space complexity of Kadane's algorithm for maximum subarray?",
            "O(n)", "O(n²)", "O(1)", "O(log n)", "C",
            "Kadane's algorithm uses only two variables (max_so_far, max_ending_here), so space is O(1).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "Find the second largest element in array [10, 5, 20, 8, 15]. What is it?",
            "15", "10", "8", "20", "A",
            "Sorted descending: 20, 15, 10, 8, 5. Second largest is 15.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "How do you find if there are two numbers in an array that sum to a target in O(n) time?",
            "Nested loops", "Use a HashSet to store visited elements", "Sort and use two pointers (O(n log n))", "Binary search", "B",
            "Using a HashSet, for each element check if (target - element) is already in the set. This gives O(n) time and O(n) space.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "What is the time complexity to find the majority element (appearing > n/2 times) using Boyer-Moore Voting?",
            "O(n²)", "O(n log n)", "O(n)", "O(1)", "C",
            "Boyer-Moore Voting Algorithm finds the majority element in O(n) time with O(1) extra space.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "What is the output of: int[] arr = {1,2,3}; arr[1] = arr[2]; System.out.print(arr[1]);",
            "1", "2", "3", "Compiler error", "C",
            "arr[2] = 3, so arr[1] = 3. Output is 3.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "What is a prefix sum array and what does it help achieve?",
            "Reversal of array", "O(1) range sum queries after O(n) preprocessing", "Sorting in O(n)", "Removing duplicates", "B",
            "A prefix sum array stores cumulative sums so that sum(l, r) = prefix[r] - prefix[l-1] in O(1) time.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "Given a sorted array, what is the most efficient way to remove duplicates in-place?",
            "Use a HashSet", "Two-pointer technique", "Nested loop comparison", "Sort and scan", "B",
            "Two-pointer approach: one pointer for the write position, another scans the array. O(n) time, O(1) space.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "What is the time complexity of the Dutch National Flag algorithm for 3-way partitioning?",
            "O(n²)", "O(n log n)", "O(n)", "O(log n)", "C",
            "Dutch National Flag sorts an array of 0s, 1s, 2s in a single pass with O(n) time and O(1) space.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "In a 2D matrix with rows and columns sorted, what algorithm finds an element in O(m+n)?",
            "Binary search on each row", "Staircase search from top-right corner", "Brute force O(mn)", "DFS traversal", "B",
            "Start from top-right; if element > target move left, if < target move down. This eliminates one row or column per step.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "What is the minimum number of jumps to reach the end of array [2,3,1,1,4]?",
            "3", "2", "4", "1", "B",
            "Jump from index 0 (value 2) to index 1 (value 3), then directly to end. Minimum 2 jumps.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "Which of these is NOT an in-place sorting algorithm?",
            "Bubble Sort", "Selection Sort", "Merge Sort", "Insertion Sort", "C",
            "Merge Sort requires O(n) extra space for the auxiliary array, making it not in-place (unlike Bubble, Selection, Insertion).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "What is the best-case time complexity of Bubble Sort?",
            "O(n²)", "O(n log n)", "O(n)", "O(log n)", "C",
            "With early termination optimization, Bubble Sort is O(n) for an already sorted array.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Arrays",
            "You need to find all pairs with difference k in an unsorted array. Best time complexity?",
            "O(n²)", "O(n log n)", "O(n)", "O(n²log n)", "C",
            "Use a HashSet: for each element x, check if x+k or x-k exists. O(n) time and O(n) space.",
            Difficulty.HARD, 4));
    }

    // =========================================================================
    // DSA - Strings
    // =========================================================================
    private void addDsaStrings(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What is the time complexity of the naive string matching algorithm?",
            "O(n)", "O(m+n)", "O(n*m)", "O(n log n)", "C",
            "Naive algorithm compares pattern at every position in the text: O(n*m) where n=text length, m=pattern length.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What does the KMP (Knuth-Morris-Pratt) algorithm improve over naive string matching?",
            "Space complexity", "Preprocessing time", "Avoiding redundant comparisons using failure function", "Case sensitivity handling", "C",
            "KMP uses a failure function (LPS array) to skip unnecessary comparisons, achieving O(n+m) time.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "How do you check if two strings are anagrams in O(n) time?",
            "Sort both strings and compare", "Count character frequencies and compare", "Check substring", "Reverse one and compare", "B",
            "Count character frequencies using an array or HashMap. Two strings are anagrams if their frequency maps are identical. O(n) time.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What is the output of: 'racecar'.split('').reverse().join('') in conceptual pseudocode?",
            "racecar", "racerac", "acecar", "racecr", "A",
            "'racecar' reversed is 'racecar' — it is a palindrome, so reversing yields the same string.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What data structure is used to efficiently find the longest prefix which is also a suffix (LPS)?",
            "Stack", "Array (DP)", "HashSet", "Queue", "B",
            "The LPS (Failure Function) in KMP is computed using a DP array in O(m) time.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What is the time complexity of finding the longest palindromic substring using Manacher's algorithm?",
            "O(n²)", "O(n log n)", "O(n)", "O(n³)", "C",
            "Manacher's algorithm finds the longest palindromic substring in O(n) time using symmetry properties.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "Which of the following approaches converts a Roman numeral to integer in one left-to-right pass?",
            "Stack-based approach", "Compare current and next value: subtract if current < next", "Reverse string first", "Brute force", "B",
            "Iterate left to right. If current symbol < next symbol, subtract current; otherwise add. O(n) time.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "How many distinct characters are needed to store all possible characters in an ASCII string?",
            "26", "52", "128", "256", "C",
            "Standard ASCII has 128 characters (0–127), so a frequency array of size 128 covers all ASCII characters.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What is the edit distance (Levenshtein distance) between 'kitten' and 'sitting'?",
            "2", "3", "4", "5", "B",
            "Edit distance = 3: kitten→sitten (substitution k→s), sitten→sittin (substitution e→i), sittin→sitting (insert g).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "Which algorithm finds all occurrences of a pattern in a text using a rolling hash?",
            "KMP", "Rabin-Karp", "Boyer-Moore", "Z-Algorithm", "B",
            "Rabin-Karp uses rolling hash to compute pattern hash and slide over text, matching in expected O(n+m) time.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What is the space complexity of storing a trie with n strings of average length m?",
            "O(n)", "O(m)", "O(n*m)", "O(1)", "C",
            "A Trie can store up to O(n*m) nodes in the worst case where each string is completely unique.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "How do you find the first non-repeating character in a string in one pass?",
            "Use nested loops", "Use a LinkedHashMap preserving insertion order with frequencies", "Sort and scan", "Brute force comparison", "B",
            "LinkedHashMap stores characters in insertion order; one pass to build freq map, second pass to find first with freq=1. O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What does the Z-function of a string compute?",
            "Longest palindrome", "Rolling hash values", "For each index, the length of the longest substring matching the prefix", "Character frequencies", "C",
            "Z[i] = length of longest substring starting at i that is also a prefix of the string. Used in pattern matching in O(n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "What is the output of reversing words in 'Hello World'?",
            "dlroW olleH", "World Hello", "Hello World", "olleH dlroW", "B",
            "Reversing words (not characters) in 'Hello World' gives 'World Hello'.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Strings",
            "How many unique substrings does the string 'aab' have?",
            "3", "5", "6", "4", "C",
            "All substrings of 'aab': a, a, b, aa, ab, aab. Unique: {a, b, aa, ab, aab} = 5. Note: 'a' appears twice but is unique substring. Count = 5.",
            Difficulty.MEDIUM, 3));
    }

    // =========================================================================
    // DSA - Hashing
    // =========================================================================
    private void addDsaHashing(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "What is the average time complexity for insert, delete, and search in a Hash Table?",
            "O(n)", "O(log n)", "O(1)", "O(n log n)", "C",
            "Hash tables provide O(1) average-case time for insert, delete, and search using a hash function.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "What is a hash collision and how does separate chaining resolve it?",
            "Two keys with same value; use BST", "Two keys mapped to the same bucket; each bucket holds a linked list of entries", "Overflow; use open addressing", "Hash mismatch; rehash", "B",
            "Separate chaining maintains a linked list at each bucket. Colliding entries are chained in the list at the shared bucket index.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "What is the load factor of a hash table and what happens when it gets too high?",
            "Number of buckets; performance degrades", "Ratio of entries to buckets; triggers rehashing", "Initial capacity; reduces memory", "Hash function complexity; changes algorithm", "B",
            "Load factor = n/m (entries/buckets). When it exceeds a threshold (typically 0.75 in Java), the table is resized/rehashed.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "Given an array, find the length of the longest subarray with sum 0. Best approach?",
            "Sliding window", "Prefix sum + HashMap", "Two pointers", "Binary search", "B",
            "Store prefix sums in a HashMap. If the same prefix sum appears at index i and j, subarray i+1..j has sum 0. O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "In Java, what class implements a HashMap that maintains insertion order?",
            "TreeMap", "HashMap", "LinkedHashMap", "Hashtable", "C",
            "LinkedHashMap extends HashMap and maintains a doubly-linked list to preserve insertion order of entries.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "How does open addressing (linear probing) handle collision differently from separate chaining?",
            "It stores the element in the next bucket", "It creates a new hash table", "It rejects the second element", "It converts to a tree", "A",
            "Linear probing finds the next available slot linearly (index+1, +2...) in the same table instead of a linked list.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "What is the worst-case time complexity of a HashMap lookup in Java?",
            "O(1)", "O(log n)", "O(n)", "O(n²)", "C",
            "In the worst case (all keys hash to same bucket), lookup degrades to O(n). Java 8 converts chains >8 to a tree for O(log n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "How can you use hashing to detect if an array contains a duplicate?",
            "Sort and compare neighbors", "Use a HashSet; return true if insertion fails", "Two nested loops", "Use a frequency array always", "B",
            "Add each element to a HashSet. If add() returns false, element already exists → duplicate found. O(n) average time.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "Which hashing technique is used in Java's HashMap to handle long collision chains (Java 8+)?",
            "Double hashing", "Convert chain to Red-Black Tree when chain length > 8", "Cuckoo hashing", "Robin Hood hashing", "B",
            "Java 8 HashMap converts a bucket's linked list to a TreeMap (Red-Black Tree) when chain length exceeds 8 for O(log n) lookups.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "What is the time complexity to find all subarrays with a given sum k using a prefix sum HashMap?",
            "O(n²)", "O(n)", "O(n log n)", "O(1)", "B",
            "Store prefix sums in a HashMap. For each prefix sum, check if (sum - k) exists. One pass gives O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "What makes a good hash function?",
            "Produces same output for any input", "Minimizes collisions and distributes keys uniformly", "Always returns a prime number", "Requires maximum computation time", "B",
            "A good hash function distributes keys uniformly across buckets, minimizes collisions, and is fast to compute.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "In the 'Group Anagrams' problem, what is the key trick to identify anagram groups?",
            "Sort and compare character by character", "Use sorted string as HashMap key to group anagrams", "Use character XOR as key", "Compare lengths", "B",
            "Sort each string; strings that are anagrams produce the same sorted string → use it as key in a HashMap. O(n * m log m).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "How does consistent hashing help in distributed systems?",
            "All keys go to one server", "Minimizes key remapping when nodes are added/removed", "Creates a perfect hash function", "Avoids collisions entirely", "B",
            "Consistent hashing places servers on a ring; only keys between the removed and predecessor node are remapped, minimizing redistribution.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "What is the time complexity of finding the first element that appears exactly once in a stream of characters?",
            "O(1)", "O(n)", "O(n log n)", "O(n²)", "B",
            "Use a LinkedHashMap to track frequency and insertion order. One pass builds the map; first entry with freq=1 is the answer. O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Hashing",
            "Which of the following problems CANNOT be efficiently solved with hashing?",
            "Finding duplicate elements", "Two-sum", "Finding kth smallest element in sorted order", "Counting word frequencies", "C",
            "Kth smallest requires ordering, which hashing doesn't provide. It needs sorting (O(n log n)) or a heap (O(n log k)).",
            Difficulty.MEDIUM, 3));
    }

    // =========================================================================
    // DSA - Two Pointer
    // =========================================================================
    private void addDsaTwoPointer(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "What problem does the two-pointer technique efficiently solve in a sorted array?",
            "Find maximum element", "Find pair with given sum in O(n)", "Sort the array", "Find median", "B",
            "With two pointers at start and end of a sorted array, move them inward based on sum comparison. O(n) time.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "What is the time complexity of the Two-Pointer approach for '3Sum' problem?",
            "O(n)", "O(n²)", "O(n³)", "O(n log n)", "B",
            "Fix one element (O(n)), then apply two-pointer on the rest (O(n)), giving O(n²) overall.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "How does the two-pointer approach solve 'Container With Most Water'?",
            "BFS traversal", "Start pointers at both ends, move the shorter height pointer inward", "Dynamic programming", "Binary search", "B",
            "Keep left and right pointers; water = min(height[l], height[r]) * (r-l). Move the pointer with smaller height for maximum area.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "What is the prerequisite for applying the two-pointer technique to find a pair with target sum?",
            "Array must be unsorted", "Array must be sorted", "Array must have no duplicates", "Array must be circular", "B",
            "Two-pointer for pair sum works on sorted arrays. On unsorted arrays, use a HashSet instead.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "In the 'Remove Duplicates from Sorted Array' problem, how does two-pointer work?",
            "Compare every pair O(n²)", "Slow pointer tracks unique position, fast pointer scans ahead", "Sort again after removal", "Use HashSet", "B",
            "Slow pointer advances only when a new unique element is found by the fast pointer. In-place, O(n) time, O(1) space.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "Which two-pointer pattern checks if a string is a valid palindrome ignoring non-alphanumeric chars?",
            "Left and right pointers moving inward, skipping invalid chars", "Stack-based reversal", "Sort chars and compare", "Hashing approach", "A",
            "Use left (start) and right (end) pointers. Skip non-alphanumeric; compare chars case-insensitively. O(n) time.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "What is 'Trapping Rain Water' problem's two-pointer time complexity?",
            "O(n²)", "O(n log n)", "O(n)", "O(1)", "C",
            "Two pointers from both ends track maxLeft and maxRight; compute water at each step. O(n) time, O(1) space.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "How do you use two pointers to merge two sorted arrays in-place?",
            "Start both from index 0", "Start from the ends and fill from the back", "Use a temporary array", "Binary search merging", "B",
            "Start from last elements of both arrays and fill the merged array from the back to avoid overwriting data. O(m+n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "What is the key insight in using two pointers on a linked list to detect a cycle?",
            "Count nodes", "Slow pointer moves 1 step, fast pointer moves 2 steps; if they meet, cycle exists", "Reverse the list and compare", "Use a HashSet", "B",
            "Floyd's Cycle Detection: if there's a cycle, fast and slow pointers will eventually meet. O(n) time, O(1) space.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "In the 'Sort Colors' (Dutch Flag) problem using two pointers, what is the approach?",
            "Sort normally O(n log n)", "Three pointers: low, mid, high to partition 0s, 1s, 2s in one pass", "Count each color then fill", "Use two separate arrays", "B",
            "Three-pointer approach partitions array in O(n) time and O(1) space — also known as the Dutch National Flag algorithm.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "What does 'fast and slow pointer' technique solve besides cycle detection?",
            "Only cycle detection", "Finding middle of linked list and cycle entry point", "Sorting linked list", "Merging linked lists", "B",
            "Fast/slow pointers find: (1) middle node — when fast reaches end, slow is at middle; (2) cycle entry via Floyd's algorithm.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "What is the time complexity of finding all triplets that sum to zero using two pointers?",
            "O(n)", "O(n log n)", "O(n²)", "O(n³)", "C",
            "Fix one element O(n), use sorted two-pointer for remaining O(n). With deduplication: O(n²) overall.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "How is the two-pointer technique used to find the longest substring with at most K distinct characters?",
            "Fixed window size", "Expand right pointer, shrink left when distinct count exceeds K", "Hashing without pointers", "Dynamic programming", "B",
            "This is actually a sliding window with two pointers. Expand right; when distinct chars > K, move left until valid. O(n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "Can two-pointer technique be applied to unsorted arrays to find pair with given sum?",
            "Yes, always works", "No, requires sorted array or use HashSet instead", "Yes, with minor modification", "Only for positive integers", "B",
            "Two-pointer relies on sorted order to decide which pointer to move. For unsorted arrays, use a HashSet for O(n) time.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Two Pointer",
            "What is the minimum window substring problem's best time complexity?",
            "O(n²)", "O(n*m)", "O(n)", "O(n log n)", "C",
            "Sliding window (two pointers) with a character frequency map finds the minimum window containing all characters in O(n+m).",
            Difficulty.HARD, 4));
    }

    // =========================================================================
    // DSA - Sliding Window
    // =========================================================================
    private void addDsaSlidingWindow(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What type of problem is best suited for the sliding window technique?",
            "Finding shortest path", "Subarray/substring problems with contiguous constraint", "Graph traversal", "Tree level order", "B",
            "Sliding window is optimal for contiguous subarray/substring problems like maximum sum, longest substring, etc.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What is the maximum sum subarray of size k=3 in [2, 1, 5, 1, 3, 2]?",
            "7", "8", "9", "6", "C",
            "Windows: [2,1,5]=8, [1,5,1]=7, [5,1,3]=9, [1,3,2]=6. Maximum is 9.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What is the time complexity of finding the longest substring without repeating characters?",
            "O(n²)", "O(n)", "O(n log n)", "O(n * alphabet)", "B",
            "Sliding window with a HashSet: expand right, shrink left on duplicate. O(n) time overall.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "In a fixed-size sliding window of size k, how many windows exist in an array of size n?",
            "n", "k", "n - k + 1", "n * k", "C",
            "Windows start from index 0 to n-k, giving (n - k + 1) total windows.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What data structure helps maintain the maximum in a sliding window in O(1) per step?",
            "Stack", "Deque (monotonic queue)", "Priority Queue", "Binary Search Tree", "B",
            "A monotonic deque keeps elements in decreasing order. When window slides, remove expired and smaller elements. O(n) total.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What is a 'variable-size' sliding window and when is it used?",
            "Window with random size at each step", "Window that expands/shrinks based on a condition to find optimal subarray", "Window that skips elements", "Fixed window with duplicates", "B",
            "Variable-size window expands right to include more elements and shrinks left when a condition is violated. Used for 'longest/shortest' problems.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "Longest subarray with sum ≤ k uses which technique?",
            "Fixed sliding window", "Binary search", "Variable sliding window", "Prefix sum without window", "C",
            "Expand right pointer to include elements, shrink left when sum exceeds k. Variable window tracks maximum valid size.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "In 'Minimum Size Subarray Sum ≥ target', the sliding window approach runs in:",
            "O(n²)", "O(n log n)", "O(n)", "O(1)", "C",
            "Expand right pointer, and when sum ≥ target, shrink left to minimize window. Each element is visited at most twice: O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "How does the sliding window find the longest substring with at most 2 distinct characters?",
            "Sort and scan", "HashMap to track char frequencies; shrink when distinct > 2", "Use nested loops", "Convert to integer codes", "B",
            "Use a HashMap of char→count. Expand right; when map size > 2, remove from left until map size ≤ 2. Track max window. O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What is the space complexity of the sliding window for finding the longest substring without repeating characters?",
            "O(1)", "O(n)", "O(alphabet size)", "O(n²)", "C",
            "We store at most |alphabet| characters in the HashSet/HashMap, which is O(alphabet size) ≤ O(128) for ASCII.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What is the 'Rabin-Karp' rolling hash related to in sliding window?",
            "Fixed-size window with O(1) hash update per slide", "Variable window", "Graph window", "Tree traversal", "A",
            "Rabin-Karp uses a rolling hash that removes old char contribution and adds new char in O(1), sliding over strings efficiently.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "Find the number of subarrays with product less than k=100 in [10, 5, 2, 6]. Answer?",
            "8", "10", "12", "6", "A",
            "Products: [10]=10, [5]=5, [2]=2, [6]=6, [10,5]=50, [5,2]=10, [2,6]=12, [5,2,6]=60. All 8 are < 100.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What distinguishes the sliding window from a prefix sum approach?",
            "Sliding window is always O(1) space", "Sliding window maintains a window and adjusts boundaries; prefix sum precomputes cumulative sums", "They are identical", "Prefix sum uses recursion", "B",
            "Sliding window dynamically adjusts boundaries. Prefix sum precomputes cumulative sums for O(1) range queries. Different trade-offs.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "In 'Find All Anagrams in String' problem, which window technique is used?",
            "Variable window", "Fixed-size window matching character frequency of pattern", "Binary search on window", "Stack-based window", "B",
            "Slide a fixed-size window equal to pattern length. Compare character frequencies at each position using a frequency array/map.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sliding Window",
            "What is the time complexity of finding the maximum average subarray of length k?",
            "O(n²)", "O(n log n)", "O(n)", "O(k)", "C",
            "Compute first window sum, then slide: subtract leftmost, add rightmost element. Single pass O(n).",
            Difficulty.EASY, 2));
    }

    // =========================================================================
    // DSA - Sorting
    // =========================================================================
    private void addDsaSorting(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "Which sorting algorithm has the best worst-case time complexity of O(n log n)?",
            "Quick Sort", "Bubble Sort", "Merge Sort", "Insertion Sort", "C",
            "Merge Sort always runs in O(n log n) regardless of input. Quick Sort's worst case is O(n²) with bad pivot selection.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "What is the space complexity of Merge Sort?",
            "O(1)", "O(log n)", "O(n)", "O(n²)", "C",
            "Merge Sort requires O(n) auxiliary space for the temporary arrays used during merging.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "Which sorting algorithm is stable and has O(n + k) time complexity for small k?",
            "Heap Sort", "Quick Sort", "Counting Sort", "Merge Sort", "C",
            "Counting Sort runs in O(n + k) where k is the range of values. It is stable and not comparison-based.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "What is the average time complexity of Quick Sort?",
            "O(n)", "O(n log n)", "O(n²)", "O(log n)", "B",
            "Quick Sort has average O(n log n) with random pivot selection, though worst case is O(n²) with sorted arrays.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "Which of these sorting algorithms is NOT stable?",
            "Bubble Sort", "Merge Sort", "Heap Sort", "Insertion Sort", "C",
            "Heap Sort is not stable — the heap operations can change the relative order of equal elements.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "What is the time complexity of Insertion Sort for a nearly sorted array?",
            "O(n²)", "O(n log n)", "O(n)", "O(1)", "C",
            "For nearly sorted arrays, Insertion Sort runs close to O(n) as very few elements need to be shifted.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "In Radix Sort, what does LSD stand for?",
            "Largest Significant Digit", "Least Significant Digit", "Linked Sort Digit", "Left-Side Digit", "B",
            "LSD Radix Sort processes digits from the least significant (rightmost) to most significant (leftmost). O(d*(n+k)) time.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "What is the key property that makes Quick Sort cache-efficient?",
            "It uses extra arrays", "It has good locality of reference - in-place with sequential memory access", "It sorts backwards", "It uses recursion", "B",
            "Quick Sort's in-place partitioning accesses memory sequentially, benefiting from CPU cache spatial locality.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "Which sorting algorithm does Java use for sorting arrays of primitives?",
            "Merge Sort", "Quick Sort (Dual-Pivot)", "Heap Sort", "Counting Sort", "B",
            "Java uses Dual-Pivot Quicksort for primitive arrays (java.util.Arrays.sort). For objects it uses TimSort.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "What is TimSort and which language's sort uses it?",
            "A sorting algorithm combining Insertion Sort and Merge Sort; Java, Python", "Pure Merge Sort; C++", "Modified Quick Sort; JavaScript", "Radix Sort; Java", "A",
            "TimSort is a hybrid stable sort (Merge Sort + Insertion Sort) used in Python's sorted() and Java's Arrays.sort for objects.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "What is the best case time complexity of Bubble Sort?",
            "O(n²)", "O(n log n)", "O(n)", "O(log n)", "C",
            "With the optimization flag that stops if no swaps occur in a pass, Bubble Sort is O(n) for already sorted input.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "Which sort should you prefer when memory is limited and you need O(n log n) worst case?",
            "Merge Sort", "Quick Sort", "Heap Sort", "Radix Sort", "C",
            "Heap Sort is in-place (O(1) space), not stable, but guarantees O(n log n) worst case — good when memory is constrained.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "What algorithm can sort a list of n integers in the range [0, n²) in O(n) time?",
            "Radix Sort (base n)", "Counting Sort", "Merge Sort", "Quick Sort", "A",
            "Radix Sort with base n processes 2 digits (since n² in base n has 2 digits), running in O(n) time.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "How does Selection Sort work?",
            "Picks minimum from unsorted part and swaps to correct position", "Compares adjacent elements", "Divides into halves", "Inserts into correct position", "A",
            "Selection Sort repeatedly finds the minimum element in the unsorted portion and moves it to its sorted position. O(n²).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Sorting",
            "When is it acceptable to use O(n²) sorting algorithms in practice?",
            "Never, always use Merge Sort", "For very small arrays (n < ~50) or nearly sorted arrays", "Only for strings", "When memory is unlimited", "B",
            "For very small n, O(n²) algorithms like Insertion Sort have lower overhead than O(n log n) algorithms due to constant factors.",
            Difficulty.MEDIUM, 3));
    }

    // =========================================================================
    // DSA - Searching
    // =========================================================================
    private void addDsaSearching(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is the time complexity of Linear Search in the worst case?",
            "O(1)", "O(log n)", "O(n)", "O(n²)", "C",
            "Linear search checks each element one by one, so worst case is O(n) when element is at the end or not found.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "Which search algorithm requires the array to be sorted?",
            "Linear Search", "Binary Search", "Jump Search", "Both B and C", "D",
            "Binary Search and Jump Search both require the input array to be sorted to work correctly.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is the time complexity of Interpolation Search for uniformly distributed data?",
            "O(n)", "O(log n)", "O(log log n)", "O(1)", "C",
            "Interpolation Search estimates the position proportionally for uniformly distributed data, achieving O(log log n) average time.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is the time complexity of searching in a Balanced BST?",
            "O(1)", "O(n)", "O(log n)", "O(n log n)", "C",
            "In a balanced BST (like AVL or Red-Black Tree), search takes O(log n) time by comparing and traversing left/right.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is the jump size used in Jump Search for an array of size n?",
            "n/2", "log n", "√n", "n²", "C",
            "Jump Search uses a block size of √n for jumping. Within each block it does linear search. Total time: O(√n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is Exponential Search and when is it useful?",
            "Linear search with exponential skip", "Find range where element exists by doubling index, then binary search; good for unbounded arrays", "Search in exponential time", "DFS in exponential tree", "B",
            "Exponential Search doubles the range (1, 2, 4, 8...) until overshoot, then binary searches that range. O(log n) for bounded sorted arrays.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "In a depth-first search (DFS) on a graph with V vertices and E edges, what is the time complexity?",
            "O(V)", "O(E)", "O(V + E)", "O(V * E)", "C",
            "DFS visits each vertex once and each edge once, so time complexity is O(V + E).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What data structure does BFS (Breadth-First Search) use internally?",
            "Stack", "Queue", "Priority Queue", "Deque", "B",
            "BFS uses a queue (FIFO) to process nodes level by level, ensuring shortest path in unweighted graphs.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is the difference between BFS and DFS in terms of memory usage for a wide tree?",
            "DFS uses more memory", "BFS uses more memory (stores all nodes at a level)", "They use the same memory", "Both use O(1) memory", "B",
            "BFS stores all nodes at the current level in the queue. For wide trees with many nodes per level, BFS uses more memory than DFS.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "Which search algorithm is used to search in a trie (prefix tree)?",
            "Binary Search", "Linear Search", "Character-by-character traversal matching prefix", "DFS with backtracking", "C",
            "Trie search follows edges character by character. Search time is O(m) where m is length of query string.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is the time complexity of searching for all occurrences of a pattern in a string using Aho-Corasick?",
            "O(n * m)", "O(n + m + z)", "O(n log n)", "O(m²)", "B",
            "Aho-Corasick preprocesses all patterns in O(m) and searches in O(n + z) where z is number of matches. Total O(n + m + z).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is the purpose of the 'visited' array in graph DFS/BFS?",
            "To store shortest paths", "To prevent revisiting nodes and avoid infinite loops", "To count edges", "To store node values", "B",
            "The visited array marks nodes that have been processed to avoid infinite loops in graphs with cycles.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "What is the best time complexity to search for a word in a list of n strings of length m?",
            "O(n)", "O(n*m)", "O(m) using a Trie after O(n*m) preprocessing", "O(log n)", "C",
            "After O(n*m) trie construction, each query word is searched in O(m) time by traversing character-by-character.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "How does Bidirectional BFS improve path search compared to standard BFS?",
            "It uses less memory", "Runs BFS from both source and target simultaneously, reducing search space", "It finds longer paths", "It avoids using a queue", "B",
            "Bidirectional BFS explores from both ends; they meet in the middle, reducing complexity from O(b^d) to O(b^(d/2)).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Searching",
            "In which case would you prefer Linear Search over Binary Search?",
            "When the array is very large", "When the array is unsorted or very small", "When searching for multiple elements", "Never prefer linear search", "B",
            "Binary Search requires a sorted array. For unsorted arrays or very small sizes (overhead of sorting > search gain), Linear Search is better.",
            Difficulty.EASY, 2));
    }

    // =========================================================================
    // DSA - Binary Search
    // =========================================================================
    private void addDsaBinarySearch(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What is the time complexity of Binary Search?",
            "O(n)", "O(n log n)", "O(log n)", "O(1)", "C",
            "Binary Search halves the search space at each step, leading to O(log n) time complexity.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "In Binary Search, how is the mid index calculated to avoid integer overflow?",
            "mid = (low + high) / 2", "mid = low + (high - low) / 2", "mid = high / 2", "mid = (low + high) >> 1", "B",
            "Using (low + high)/2 can cause integer overflow for large values. The safe formula is low + (high - low) / 2.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What is the condition for Binary Search to work correctly?",
            "Array must have unique elements", "Array must be sorted", "Array must have odd size", "Array must be circular", "B",
            "Binary Search works by comparing mid with target and discarding half the search space — only valid if array is sorted.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "Find the position of element 7 in sorted array [1, 3, 5, 7, 9, 11] using binary search. Which step finds it?",
            "Step 1", "Step 2", "Step 3", "Step 4", "B",
            "Step 1: low=0,high=5,mid=2,arr[2]=5<7 → low=3. Step 2: low=3,high=5,mid=4,arr[4]=9>7 → high=3. Wait: mid=3,arr[3]=7 found at step 2.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What does 'lower_bound' binary search find?",
            "Index of first element > target", "Index of first element ≥ target", "Index of last element ≤ target", "Index of target if exists else -1", "B",
            "lower_bound returns the index of the first element that is ≥ target (first position where target could be inserted to keep sorted order).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "How do you find the number of occurrences of an element in a sorted array using binary search?",
            "Linear scan after finding element", "upper_bound(x) - lower_bound(x)", "Use count variable in binary search", "Sort and count", "B",
            "upper_bound(x) gives first index > x, lower_bound(x) gives first index ≥ x. Difference gives count of x in O(log n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What is the key insight for applying binary search on the answer in optimization problems?",
            "The answer space has no structure", "The feasibility function is monotone: feasible/not-feasible forms a partition", "Answers are always integers", "Requires sorted input", "B",
            "If feasibility is monotone (once infeasible, stays infeasible), binary search on the answer space finds optimal in O(log(range) * check time).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "How do you search in a sorted rotated array [4,5,6,7,0,1,2] for target 0?",
            "Normal binary search fails", "Check which half is sorted; target must be in sorted half or other half", "Linear search only", "Sort first then binary search", "B",
            "At each mid, one half is always sorted. Check if target is in the sorted half; if not, search the other half. O(log n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What is the minimum number of pages problem and how is binary search applied?",
            "It's a graph problem", "Binary search on the answer (max pages per student) with feasibility check", "Greedy selection", "DP approach only", "B",
            "Binary search on range [max_page, sum_pages]. For each mid, check if books can be divided among m students ≤ mid pages each. O(n log n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "How do you find the square root of a number without using sqrt() function using binary search?",
            "Newton's method only", "Binary search on [0, n]; find largest x where x² ≤ n", "Bit manipulation", "Linear scan from 0", "B",
            "Binary search in [0, n] for the floor of sqrt. mid * mid ≤ n? go right; else go left. O(log n) time.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What is 'Peak Element' in an array and how does binary search find it?",
            "Maximum element; linear scan", "Element greater than its neighbors; check mid vs mid+1 to decide direction", "Minimum element; scan", "Any element; random pick", "B",
            "If arr[mid] < arr[mid+1], a peak exists in the right half; else it exists in the left. Find any peak in O(log n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What is the time complexity of finding the kth smallest element in a sorted matrix using binary search?",
            "O(n²)", "O(n log n log(max-min))", "O(n log n)", "O(1)", "B",
            "Binary search on value range [min, max]; for each mid count elements ≤ mid in O(n log n). Total: O(n log n * log(max-min)).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What is Ternary Search and when is it preferred over Binary Search?",
            "Divides into three parts; for unimodal functions to find maximum/minimum", "Faster than binary search always", "Used for string matching", "For circular arrays only", "A",
            "Ternary search finds maximum of a unimodal function by eliminating one-third of the range each step. O(log₃ n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "What is the time complexity of finding the first bad version using binary search?",
            "O(n)", "O(log n)", "O(1)", "O(n log n)", "B",
            "Binary search for the first bad version in [1, n]: check mid, if bad search left half, else search right. O(log n) API calls.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Binary Search",
            "In a sorted array of distinct integers, arr[i] = i for some i. How to find such an index?",
            "Linear scan O(n)", "Binary search: if arr[mid] > mid go left, else go right O(log n)", "Hash map", "DFS", "B",
            "Since elements are distinct and sorted, if arr[mid] > mid, the fixed point must be left; else right. O(log n).",
            Difficulty.MEDIUM, 3));
    }

    // =========================================================================
    // DSA - Recursion
    // =========================================================================
    private void addDsaRecursion(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What are the two essential components of a recursive function?",
            "Loop and condition", "Base case and recursive case", "Input and output", "Stack and queue", "B",
            "Every recursive function needs a base case (stopping condition) and a recursive case (calls itself with a smaller problem).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is the time complexity of computing Fibonacci(n) using naive recursion?",
            "O(n)", "O(n log n)", "O(2^n)", "O(n²)", "C",
            "Naive Fibonacci recursion has exponential time O(2^n) due to recomputing the same subproblems repeatedly.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is tail recursion and why is it important?",
            "Recursion with no base case", "The recursive call is the last operation; enables tail-call optimization", "Recursion that uses no extra memory", "Recursion on sorted arrays", "B",
            "In tail recursion, the recursive call is the last operation. Compilers can optimize this to avoid adding new stack frames (tail-call optimization).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What causes a StackOverflowError in recursive programs?",
            "Too many variables", "Missing base case leading to infinite recursion and call stack exhaustion", "Incorrect return type", "Integer overflow", "B",
            "Without a proper base case, recursive calls continue indefinitely, consuming stack frames until the call stack is exhausted.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is the output of this function: int f(int n) { return n <= 1 ? 1 : n * f(n-1); } when called as f(5)?",
            "5", "15", "120", "25", "C",
            "f(5) = 5 * f(4) = 5 * 4 * f(3) = 5 * 4 * 3 * f(2) = 5 * 4 * 3 * 2 * f(1) = 5! = 120.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "How many recursive calls are made in a Merge Sort on n elements?",
            "O(n)", "O(log n)", "O(n log n) total across all levels", "O(2^n)", "C",
            "Merge Sort splits into two halves at each level (O(log n) levels), each level processes all n elements: O(n log n) work total.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is memoization in the context of recursion?",
            "Storing all function calls in a stack", "Caching results of recursive calls to avoid recomputation", "Limiting recursion depth", "Converting recursion to iteration", "B",
            "Memoization stores already-computed results in a cache (HashMap/array). If same input appears, return cached result. Converts exponential to polynomial.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is the recursive formula for the Tower of Hanoi with n disks?",
            "T(n) = T(n-1) + n", "T(n) = 2*T(n-1) + 1", "T(n) = 2^n - 1", "T(n) = n * T(n-1)", "B",
            "T(n) = 2*T(n-1) + 1. The solution is T(n) = 2^n - 1 total moves.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is the time complexity to generate all permutations of a string of length n?",
            "O(n)", "O(n!)", "O(n²)", "O(2^n)", "B",
            "There are n! permutations of n characters. Generating all takes O(n * n!) time (n! permutations, each of length n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is mutual recursion?",
            "Two functions calling themselves", "Function A calls function B which calls function A", "Recursion inside a loop", "Recursive sorting", "B",
            "Mutual recursion involves two or more functions calling each other cyclically, e.g., isEven(n) calls isOdd(n-1) and vice versa.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is the total number of subsets of a set with n elements (Power Set)?",
            "n!", "n²", "2^n", "n*(n-1)", "C",
            "Each element either is or isn't in the subset (2 choices per element). Total = 2^n subsets. Generated by recursion in O(2^n * n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is the recurrence relation for binary search? Solve it using Master theorem.",
            "T(n) = T(n/2) + O(1) → O(log n)", "T(n) = 2T(n/2) + O(n) → O(n log n)", "T(n) = T(n-1) + O(1) → O(n)", "T(n) = T(n/3) + O(1) → O(log₃n)", "A",
            "Binary Search: T(n) = T(n/2) + O(1). By Master Theorem case 2: T(n) = O(log n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "How does recursion relate to the call stack in memory?",
            "Recursion uses the heap", "Each recursive call adds a stack frame; deep recursion can overflow the stack", "Recursion is always iterative under the hood", "No relationship", "B",
            "Each recursive call pushes a frame onto the call stack (return address, parameters, locals). Too many calls exhaust stack space.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is the time complexity of recursive Binary Search vs iterative Binary Search?",
            "Recursive O(n), Iterative O(log n)", "Both O(log n); recursive uses O(log n) stack space vs O(1) for iterative", "Recursive O(log n), Iterative O(n)", "No difference at all", "B",
            "Both achieve O(log n) time. But recursive version uses O(log n) call stack space; iterative uses O(1) space.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Recursion",
            "What is the recurrence relation for Merge Sort and its solution?",
            "T(n) = T(n/2) + O(1) = O(log n)", "T(n) = 2T(n/2) + O(n) = O(n log n)", "T(n) = T(n-1) + O(n) = O(n²)", "T(n) = 3T(n/3) + O(n) = O(n log n)", "B",
            "Merge Sort: T(n) = 2T(n/2) + O(n). By Master Theorem case 2 (a=2, b=2, f(n)=n): T(n) = O(n log n).",
            Difficulty.MEDIUM, 3));
    }

    // =========================================================================
    // DSA - Backtracking
    // =========================================================================
    private void addDsaBacktracking(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "What is the core idea of the backtracking algorithm?",
            "Greedily pick the best option", "Explore all possibilities by building candidates incrementally and abandoning invalid ones", "Divide into subproblems", "Use memoization", "B",
            "Backtracking builds solutions incrementally. It abandons a partial candidate (backtracks) as soon as it's determined to be infeasible.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "What is the time complexity of solving N-Queens problem with backtracking?",
            "O(n!)", "O(n²)", "O(n log n)", "O(2^n)", "A",
            "N-Queens backtracking has approximately O(n!) time in the worst case as it tries all permutations of queen placements.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "How does backtracking differ from brute force?",
            "Backtracking is always faster", "Backtracking prunes invalid paths early, reducing unnecessary exploration", "They are identical", "Backtracking uses DP", "B",
            "Backtracking prunes branches that cannot lead to valid solutions, unlike brute force which explores all possibilities exhaustively.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "What constraint makes Sudoku solvable via backtracking?",
            "Each row, column, and 3x3 box must contain all digits 1-9 without repetition", "Each row must sum to 45", "All cells must be non-zero", "Diagonal must be unique", "A",
            "Backtracking fills empty cells with digits 1-9 checking row, column, and 3x3 box constraints. Backtracks when no valid digit exists.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "What is the 'Word Search' problem and what algorithm solves it?",
            "Find words in a dictionary; Trie", "Find a word in a 2D grid by moving in 4 directions; DFS with backtracking", "Count words in text; HashMap", "Pattern matching; KMP", "B",
            "DFS explores each starting cell, marks visited cells, recursively checks adjacent cells, and backtracks if path fails.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "How many solutions does the 8-Queens problem have?",
            "1", "4", "12", "92", "D",
            "The 8-Queens problem has exactly 92 distinct solutions for placing 8 non-attacking queens on an 8×8 chessboard.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "What is the difference between backtracking and dynamic programming?",
            "Backtracking uses memoization, DP doesn't", "DP stores solutions to overlapping subproblems; backtracking doesn't memoize and explores feasible paths", "Both are identical", "DP is always faster", "B",
            "DP optimizes by storing subproblem results to avoid recomputation. Backtracking explores solution space without memoization, pruning infeasible paths.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "In the 'Combination Sum' problem, what ensures unique combinations with backtracking?",
            "Sort candidates and skip duplicates at same recursion level", "Use a HashSet for all combinations", "Try all permutations", "Always start from index 0", "A",
            "Sort candidates. At each recursion level, skip duplicate candidates that were already processed to avoid generating duplicate combinations.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "What is 'pruning' in the context of backtracking?",
            "Removing nodes from a tree", "Cutting off exploration of branches that cannot lead to valid solutions", "Sorting candidates first", "Converting recursion to iteration", "B",
            "Pruning eliminates branches of the search tree that violate constraints, dramatically reducing the search space.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "What is the time complexity for generating all subsets using backtracking?",
            "O(n!)", "O(n²)", "O(2^n)", "O(n log n)", "C",
            "Each element has 2 choices (include/exclude), leading to 2^n subsets. With n work per subset: O(n * 2^n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "How does the 'Rat in a Maze' problem use backtracking?",
            "BFS from start to end", "Recursively try all 4 directions; mark visited; backtrack if dead end", "Dijkstra's shortest path", "DP table filling", "B",
            "DFS explores all directions from current cell. If a path doesn't reach the destination, mark cell as unvisited and try another direction.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "In the 'Palindrome Partitioning' problem, what is the worst-case number of partitions for string of length n?",
            "n", "n-1", "2^(n-1)", "n!", "C",
            "Worst case: every partition point can be included or not (2 choices per gap), giving 2^(n-1) possible partitions.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "Which of these problems is best solved by backtracking rather than DP?",
            "Fibonacci numbers", "Longest common subsequence", "N-Queens placement", "Shortest path in graph", "C",
            "N-Queens requires exploring feasible arrangements with constraints — backtracking with pruning is the standard approach.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "What is the 'Letter Combinations of Phone Number' problem's solution count for n digits?",
            "n", "3^n", "4^n", "Up to 4^n depending on digits", "D",
            "Each digit maps to 3 or 4 letters. Maximum total combinations is 4^n (e.g., digit 7 and 9 have 4 letters each).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Backtracking",
            "Why is backtracking not always practical for large problem instances?",
            "It requires too much memory", "Its exponential/factorial time complexity makes it too slow for large inputs", "It can't find all solutions", "It only works for sorting", "B",
            "Backtracking's worst-case complexity is often exponential or factorial, making it impractical for large inputs without effective pruning.",
            Difficulty.EASY, 2));
    }

    // =========================================================================
    // DSA - Linked List
    // =========================================================================
    private void addDsaLinkedList(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "What is the time complexity of inserting at the head of a singly linked list?",
            "O(n)", "O(log n)", "O(1)", "O(n²)", "C",
            "Inserting at head simply updates the head pointer to the new node. O(1) time regardless of list size.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "How do you detect the start of a cycle in a linked list?",
            "Count nodes", "After Floyd's detection, move one pointer to head; advance both by 1; they meet at cycle start", "Use a stack", "Reverse the list", "B",
            "After cycle detection (slow meets fast inside cycle), reset one pointer to head. Advance both by 1 step; they meet at the cycle entry node.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "How do you reverse a singly linked list in O(n) time and O(1) space?",
            "Use a stack to store nodes", "Use three pointers: prev, curr, next; iteratively reverse links", "Create a new list", "Recursive reversal using O(n) stack space", "B",
            "Iteratively: prev=null, curr=head. For each node, save curr.next, point curr.next to prev, move prev and curr forward. O(n), O(1).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "What is the difference between a singly and a doubly linked list?",
            "Singly is faster", "Doubly has pointers to both next and previous nodes, enabling bidirectional traversal", "Doubly uses less memory", "Singly can be circular", "B",
            "Doubly linked lists maintain prev and next pointers allowing O(1) deletion of any node given its reference, unlike singly linked lists.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "What is the time complexity to find the middle of a linked list?",
            "O(n²)", "O(n)", "O(log n)", "O(1)", "B",
            "Fast-slow pointer: fast moves 2 steps, slow moves 1. When fast reaches end, slow is at the middle. O(n) time, O(1) space.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "How do you merge two sorted linked lists into one sorted list in O(m+n)?",
            "Combine and sort", "Compare heads; append smaller, recursively merge rest", "Use a priority queue", "Sort both then merge", "B",
            "Compare heads of both lists, link the smaller node, recurse/iterate on the rest. Time O(m+n), Space O(1) iterative.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "How do you delete the Nth node from the end of a linked list in one pass?",
            "Count total nodes, then delete at (total-N)th", "Use two pointers N apart; when right reaches end, delete left.next", "Reverse list, delete Nth, reverse again", "Recursion", "B",
            "Move right pointer N+1 steps ahead, then move both until right is null. left.next is the node to delete. One pass O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "What is a circular linked list and what is a common application?",
            "A list with no nodes", "Last node points back to head; used in round-robin scheduling", "List with sorted elements", "List with duplicate values", "B",
            "In a circular linked list, the tail node points to the head. Used in CPU round-robin scheduling and Josephus problem.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "What is the time complexity of adding two numbers represented as linked lists?",
            "O(1)", "O(max(m, n))", "O(m * n)", "O(log n)", "B",
            "Traverse both lists simultaneously, adding digits with carry. Time = O(max(m, n)) where m and n are lengths of the lists.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "How do you check if a linked list is a palindrome in O(n) time, O(1) space?",
            "Copy to array and check", "Find middle, reverse second half, compare with first half, then restore", "Use a stack", "Sort and compare", "B",
            "Find middle with slow/fast pointers, reverse the second half in-place, compare, then optionally restore. O(n) time, O(1) space.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "In a skip list, what is the average time complexity for search?",
            "O(n)", "O(n²)", "O(log n)", "O(1)", "C",
            "Skip lists use multiple levels of sorted linked lists with random promotions, achieving O(log n) expected search time.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "What happens when you do curr.next = curr.next.next in a linked list delete operation?",
            "Current node is deleted", "Node after curr is deleted (skipped over)", "A new node is inserted", "Cycle is created", "B",
            "Setting curr.next to curr.next.next skips over the node immediately after curr, effectively deleting it from the list.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "How do you flatten a multilevel doubly linked list?",
            "BFS traversal", "DFS: when child exists, splice child list between curr and curr.next", "Sort and rearrange", "Use a stack explicitly", "B",
            "DFS into child lists: find tail of child, connect tail to curr.next, connect curr to child, clear child pointer. Iterative or recursive.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "What is the space complexity of reversing a linked list recursively?",
            "O(1)", "O(log n)", "O(n)", "O(n²)", "C",
            "Recursive reversal maintains n stack frames simultaneously (one per node), so space complexity is O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Linked List",
            "What is the LRU Cache and how is it efficiently implemented?",
            "Least Recently Used; HashMap + Doubly Linked List for O(1) get and put", "Most used cache; Array based", "Random replacement; Queue", "FIFO cache; Stack", "A",
            "LRU Cache: HashMap for O(1) lookup + Doubly Linked List to track usage order. Move accessed node to front. O(1) get and put.",
            Difficulty.HARD, 4));
    }

    // =========================================================================
    // DSA - Stack
    // =========================================================================
    private void addDsaStack(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "What is the time complexity of push and pop operations in a stack?",
            "O(n)", "O(log n)", "O(1)", "O(n²)", "C",
            "Stack push and pop operate on the top of the stack using a pointer, both are O(1) operations.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "Which problem is classically solved using a stack?",
            "Shortest path in graph", "Balanced parentheses checking", "Finding median", "Binary search", "B",
            "A stack perfectly models balanced parenthesis checking: push opening brackets, pop and match on closing brackets.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "What is the 'Next Greater Element' problem and how is a stack used to solve it?",
            "Find maximum element; scan linearly", "For each element find the first greater element to its right; use a monotonic stack in O(n)", "Sort and find next; O(n log n)", "Binary search approach", "B",
            "Use a decreasing monotonic stack. Pop elements smaller than current; current is the Next Greater Element for popped elements. O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "How is a stack used to evaluate a postfix expression '2 3 4 * +'?",
            "Push operators, pop operands", "Push operands; on seeing operator, pop two operands, compute, push result", "Sort tokens first", "Use recursion", "B",
            "Postfix evaluation: push 2, push 3, push 4. See *: pop 3 and 4, push 12. See +: pop 2 and 12, push 14. Result: 14.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "How do you implement a stack that supports getMin() in O(1) time?",
            "Linear scan every time", "Use an auxiliary min-stack that tracks minimum at each push level", "Sort the stack", "Use a heap alongside", "B",
            "Maintain a parallel min-stack. On push x: push min(x, minStack.top) to min-stack. On pop: pop from both stacks. getMin = minStack.top.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "What is the output of the following stack operations: push(1), push(2), pop(), push(3), peek()? (LIFO)",
            "1", "2", "3", "Error", "C",
            "Push 1 [1], Push 2 [1,2], Pop → removes 2 [1], Push 3 [1,3], Peek → returns top = 3.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "How does DFS use a stack in iterative implementation?",
            "Queue for level-by-level traversal", "Push current node, pop to visit, push neighbors in reverse order", "No stack needed", "Priority queue for weighted graphs", "B",
            "Iterative DFS: push start to stack. While not empty: pop node, process, push unvisited neighbors. Reverse order to maintain DFS order.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "What is the 'Largest Rectangle in Histogram' approach using a stack?",
            "Sort heights then scan", "Use a monotonic increasing stack; pop when current bar is shorter to calculate areas", "Two pointers", "Divide and conquer", "B",
            "Maintain a stack of indices with increasing heights. When a shorter bar is found, pop and calculate area using popped bar as height. O(n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "How is the call stack used during recursion in programming languages?",
            "Stores final results only", "Each function call pushes a stack frame with return address, parameters, and local variables", "Not used in recursion", "Only stores return addresses", "B",
            "Each recursive call creates a new stack frame holding local variables, parameters, and the return address. Frames are popped when functions return.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "What is a monotonic stack and what kind of problems does it solve?",
            "Stack with sorted elements; binary search", "Stack maintaining monotone order (increasing or decreasing); used for next greater/smaller, span, histogram problems", "FIFO stack", "Circular stack", "B",
            "Monotonic stack maintains elements in monotone order. Elements are pushed/popped to maintain order, solving span and next-greater/smaller problems in O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "How do you design a browser forward/back navigation using stacks?",
            "Use one stack only", "Use two stacks: back-stack and forward-stack; push on visit, transfer on back/forward", "Use a queue", "Use a deque", "B",
            "Back-stack stores visited pages. Navigating back pops from back-stack to forward-stack. Forward navigation pops from forward-stack to back-stack.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "What is the stock span problem?",
            "Find max stock price", "For each day find number of consecutive preceding days where price ≤ current price; solved with monotonic stack in O(n)", "Sort stock prices", "Find average price", "B",
            "Span[i] = number of days (including current) where price ≤ price[i]. Monotonic stack stores indices; pop while top price ≤ current. O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "How many stacks are needed to implement a queue (enqueue/dequeue in O(1) amortized)?",
            "1", "2", "3", "4", "B",
            "Use two stacks: inStack and outStack. Enqueue to inStack. For dequeue, if outStack empty, pour all inStack to outStack. Amortized O(1).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "Which of the following is NOT a valid application of a stack?",
            "Function call management", "Undo/redo functionality", "Job scheduling (FIFO order)", "Expression parsing", "C",
            "Job scheduling in FIFO order uses a Queue, not a Stack. Stacks follow LIFO; queues follow FIFO.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Stack",
            "What is the time complexity of flattening a nested list using a stack?",
            "O(1)", "O(n)", "O(n log n)", "O(n²)", "B",
            "Each element is pushed and popped from the stack at most once, giving O(n) time where n is the total number of elements.",
            Difficulty.MEDIUM, 3));
    }

    // =========================================================================
    // DSA - Queue
    // =========================================================================
    private void addDsaQueue(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What ordering principle does a Queue follow?",
            "LIFO", "FIFO", "Priority-based", "Random", "B",
            "A Queue follows First-In First-Out (FIFO): the first element added is the first one to be removed.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "Which algorithm uses a Queue for level-order traversal?",
            "DFS", "BFS", "Dijkstra's", "A*", "B",
            "BFS (Breadth-First Search) uses a Queue to process nodes level by level.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is a Deque (Double Ended Queue) and what operations does it support?",
            "A circular array", "A queue that allows insertion and deletion at both front and rear ends", "A queue with priorities", "A single-ended queue variant", "B",
            "Deque supports addFront, addRear, removeFront, removeRear — all O(1). Used in sliding window maximum problem.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "How is a Queue efficiently implemented using two stacks?",
            "Using sorting", "Enqueue pushes to stack1; dequeue pours stack1 to stack2 if empty, then pops from stack2", "Using recursion", "Cannot be done", "B",
            "Push to inStack for enqueue. For dequeue: if outStack empty, pour all from inStack. Pop from outStack. Amortized O(1) per operation.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is a Circular Queue and what problem does it solve?",
            "Queue with priorities", "Fixed-size queue that reuses front positions when rear reaches end, avoiding wasted space", "Sorted queue", "Multi-level queue", "B",
            "Circular Queue wraps around: when rear reaches array end and front has vacated slots, rear resets to 0. Efficiently uses fixed-size array.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is a Priority Queue and what data structure typically implements it?",
            "FIFO queue; array", "Queue where each element has a priority; highest priority dequeued first; implemented with a Heap", "Queue sorted alphabetically; BST", "Round-robin queue; linked list", "B",
            "Priority Queue dequeues the highest (or lowest) priority element first. Implemented with a Binary Heap for O(log n) enqueue/dequeue.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is the time complexity of enqueue and dequeue in a queue implemented with a linked list?",
            "O(n) for both", "O(1) for both with head and tail pointers", "O(log n) for enqueue", "O(n) for dequeue", "B",
            "With both head (front) and tail (rear) pointers: enqueue at tail O(1), dequeue from head O(1).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is the 'Rotten Oranges' problem and how is BFS/Queue used?",
            "Find total oranges; DFS", "Multi-source BFS from all rotten oranges simultaneously to find minimum time to rot all fresh oranges", "Single-source BFS", "DP table", "B",
            "Start BFS from all initially rotten oranges simultaneously. Each BFS level = 1 minute. Track remaining fresh oranges. O(m*n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "How does a Queue implement OS process scheduling (Round Robin)?",
            "Stack-based LIFO", "Each process gets a time quantum; on expiry goes to rear of queue; next process from front is executed", "Priority-based preemption only", "Random scheduling", "B",
            "Round Robin maintains a FIFO queue of processes. Each process runs for a quantum; if not done, it re-enqueues at the rear.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is the sliding window maximum problem's efficient solution using a Queue?",
            "Sort each window O(n² log n)", "Use a Monotonic Deque maintaining decreasing order; O(n) time overall", "Binary search each window", "Priority Queue O(n log n)", "B",
            "Monotonic deque: remove indices outside window from front, remove elements smaller than current from back. Front always has window max. O(n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is the First Non-Repeating Character in a stream problem's queue-based solution?",
            "HashSet scan on each insertion", "Use a Queue + frequency HashMap; front of queue always holds first non-repeating character", "Sort stream", "Binary search frequency array", "B",
            "Enqueue each character. Maintain frequency map. When queried, pop front if frequency > 1 (now repeating); current front is the answer.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "In BFS for shortest path in an unweighted graph, why is a Queue used instead of a Stack?",
            "Stack is always faster", "Queue ensures nodes are processed in order of distance (level by level) giving shortest path", "Stack gives same result", "Queue has better memory", "B",
            "Queue processes nodes level by level. The first time a node is reached via BFS, it's the shortest path since all edges have equal weight.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is a BlockingQueue in Java concurrent programming?",
            "A synchronized Stack", "A thread-safe queue that blocks on put if full and on take if empty; used in Producer-Consumer pattern", "A sorted queue", "A queue with locks", "B",
            "BlockingQueue (java.util.concurrent) blocks the producer thread when queue is full and consumer thread when empty. Key for Producer-Consumer.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "How do you generate binary numbers from 1 to n using a queue?",
            "Use division by 2", "Start with '1'; for each dequeued string, enqueue string+'0' and string+'1'", "Bit manipulation on integers", "Recursion", "B",
            "Enqueue '1'. Dequeue front, print it, enqueue front+'0' and front+'1'. Repeat n times. Each BFS level adds a new binary digit.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Queue",
            "What is the time complexity of BFS on a graph with V vertices and E edges?",
            "O(V)", "O(E)", "O(V + E)", "O(V * E)", "C",
            "BFS visits each vertex once and processes each edge once. Total time is O(V + E).",
            Difficulty.EASY, 2));
    }

    // =========================================================================
    // DSA - Trees
    // =========================================================================
    private void addDsaTrees(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is the height of a complete binary tree with n nodes?",
            "n", "n/2", "⌊log₂n⌋", "log₂(n+1)", "C",
            "Height = floor(log₂n). A complete binary tree of height h has between 2^h and 2^(h+1)-1 nodes.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is the time complexity of searching in a balanced BST?",
            "O(n)", "O(n²)", "O(log n)", "O(1)", "C",
            "Balanced BST (AVL, Red-Black Tree) maintains height O(log n). At each node, compare and go left or right. O(log n) search.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What traversal of a BST gives nodes in sorted order?",
            "Pre-order", "In-order", "Post-order", "Level-order", "B",
            "In-order traversal (Left → Root → Right) visits a BST's nodes in ascending sorted order.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is the Lowest Common Ancestor (LCA) of two nodes in a binary tree?",
            "The root node always", "The deepest node that is an ancestor of both nodes", "The parent of one node", "One of the two nodes itself", "B",
            "LCA(u, v) is the deepest node that has both u and v as descendants (a node is a descendant of itself).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "How many nodes are there in a full binary tree of height h?",
            "2h", "2^h - 1", "2^(h+1) - 1", "h²", "C",
            "A full binary tree (all leaves at same level) has 2^(h+1) - 1 total nodes.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is an AVL tree?",
            "A tree with all nodes sorted", "A self-balancing BST where heights of left and right subtrees differ by at most 1", "A tree with exactly 2 children per node", "A B-tree variant", "B",
            "AVL tree maintains balance factor |height(left) - height(right)| ≤ 1 at every node. Rotations restore balance after insertion/deletion.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is the time complexity of Morris Traversal (in-order without extra space)?",
            "O(n log n)", "O(n) time and O(1) space", "O(n) time and O(n) space", "O(log n)", "B",
            "Morris Traversal uses temporary thread links (no stack/recursion). O(n) time, O(1) extra space.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is a segment tree and what queries does it support efficiently?",
            "A binary tree for searching; O(log n) search", "A tree supporting range queries (sum, min, max) and point updates in O(log n)", "A tree with all leaves at the same level", "A circular tree structure", "B",
            "Segment tree stores aggregates (sum/min/max) for array ranges. Build O(n), range query O(log n), point update O(log n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is the diameter of a binary tree?",
            "Number of nodes in the tree", "Length of the longest path between any two nodes", "Height of the tree", "Number of leaf nodes", "B",
            "Diameter = longest path between any two nodes (doesn't need to pass through root). Calculated by finding max (left_height + right_height) at each node.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is the difference between a complete binary tree and a perfect binary tree?",
            "No difference", "Complete: all levels full except possibly last (filled left to right); Perfect: all levels completely full", "Perfect has more nodes", "Complete is always balanced", "B",
            "Perfect: all internal nodes have 2 children, all leaves at same level. Complete: last level filled left-to-right (heap property).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is a Fenwick Tree (Binary Indexed Tree) used for?",
            "Range minimum queries", "Efficient prefix sum queries and point updates in O(log n)", "Level order traversal", "AVL rotations", "B",
            "Fenwick Tree supports prefix sum queries and point updates in O(log n) time with O(n) space — simpler than segment tree.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is the time complexity of deleting a node in a BST?",
            "O(1)", "O(n)", "O(log n) for balanced BST", "O(n log n)", "C",
            "In a balanced BST, deletion takes O(log n). Find the node O(log n), then handle cases: no child, one child, or replace with in-order successor.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "How do you serialize and deserialize a binary tree?",
            "BFS is sufficient", "Pre-order traversal with null markers for serialization; reconstruct using queue during deserialization", "Sorted array only", "Level order only", "B",
            "Serialize: pre-order traversal, store null as a marker. Deserialize: reconstruct by consuming from serialized string left-to-right.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is a Trie (Prefix Tree) and what is the time complexity of search?",
            "Binary tree for integers; O(log n)", "Tree where each node stores a character; search/insert in O(m) where m = string length", "Hash-based tree; O(1)", "Sorted BST for strings; O(n)", "B",
            "Trie stores strings character by character. Each edge = a character. Search/insert = O(m) where m is the string length.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Trees",
            "What is a Red-Black Tree and what guarantees does it provide?",
            "A fully balanced tree", "A self-balancing BST with coloring rules ensuring height O(log n) and O(log n) operations", "A tree used only in databases", "A probabilistic data structure", "B",
            "Red-Black Tree: each node is red/black. Root is black, no two consecutive red nodes, equal black-height. Guarantees O(log n) worst-case operations.",
            Difficulty.HARD, 4));
    }

    // =========================================================================
    // DSA - Heap
    // =========================================================================
    private void addDsaHeap(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What property does a max-heap maintain?",
            "Parent is always smaller than children", "Parent is always greater than or equal to its children", "All elements are sorted", "Leaves are always greater than root", "B",
            "Max-Heap: every parent node's value ≥ its children's values. Root holds the maximum element.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is the time complexity of extracting the max from a max-heap?",
            "O(1)", "O(n)", "O(log n)", "O(n log n)", "C",
            "Extract max: swap root with last, remove last (O(1)), heapify down to restore heap property O(log n).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is the time complexity of building a heap from n elements using heapify?",
            "O(n log n)", "O(n)", "O(n²)", "O(log n)", "B",
            "Building a heap using bottom-up heapify (Floyd's algorithm) runs in O(n), not O(n log n). Counter-intuitive but proven mathematically.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "How do you find the kth largest element in an array efficiently?",
            "Sort the array; index n-k", "Use a min-heap of size k; maintain k largest elements", "Linear scan", "Binary search", "B",
            "Min-heap of size k: insert each element; pop if size > k. After all elements, heap top = kth largest. O(n log k).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is Heap Sort's time and space complexity?",
            "O(n log n) time, O(n) space", "O(n log n) time, O(1) space", "O(n²) time, O(1) space", "O(n) time, O(log n) space", "B",
            "Heap Sort: build max-heap O(n), extract max n times O(n log n). In-place so O(1) extra space.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "How is a binary heap stored efficiently in an array?",
            "Linked nodes with pointers", "Root at index 1; children at 2i and 2i+1; parent at i/2 (1-indexed)", "Sorted array", "Hash map of indices", "B",
            "Heap can be stored in an array: parent of node i is at floor(i/2) [1-indexed]; children are at 2i and 2i+1.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What data structure is ideal for implementing Dijkstra's algorithm efficiently?",
            "Stack", "Queue", "Min-Heap (Priority Queue)", "Hash Map", "C",
            "Min-Heap extracts the minimum distance vertex in O(log V), making Dijkstra's O((V + E) log V) with a Priority Queue.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is the 'Merge K Sorted Lists' problem's most efficient solution?",
            "Merge pairwise O(n log²k)", "Use a min-heap of size k; always extract minimum and enqueue next element from that list: O(n log k)", "Merge one by one O(nk)", "Sort all elements O(n log n)", "B",
            "Min-heap stores (value, list-index, element-index). Extract min, add to result, push next element from the same list. O(n log k).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "How do you find the running median of a stream of numbers?",
            "Sort after each insertion", "Max-heap for lower half, min-heap for upper half; balance to maintain median", "Binary search insertion", "Linear scan per insertion", "B",
            "Two heaps: maxHeap (lower half) and minHeap (upper half). Median = top of maxHeap or average of both tops. O(log n) per insert.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is the difference between a binary heap and a binomial heap?",
            "No difference", "Binary heap: single tree; Binomial heap: collection of binomial trees supporting O(log n) merge", "Binomial heap is faster for all operations", "Binary heap supports merge in O(1)", "B",
            "Binary heap is a single complete binary tree. Binomial heap is a forest of binomial trees supporting efficient merge in O(log n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is a Fibonacci heap and its key advantage?",
            "Heap with Fibonacci numbers only", "Lazy heap supporting O(1) amortized insert and decrease-key; used in Dijkstra for O(E + V log V)", "Fastest heap for all operations", "A heap sorted by Fibonacci sequence", "B",
            "Fibonacci heap supports decrease-key in O(1) amortized, making Dijkstra's O(E + V log V) theoretically optimal.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is the time complexity of inserting an element into a binary heap?",
            "O(1)", "O(log n)", "O(n)", "O(n log n)", "B",
            "Insertion: add at end (O(1)), then heapify up to restore heap property O(log n). Total: O(log n).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "In Java, which class provides a min-heap implementation?",
            "TreeSet", "LinkedList", "PriorityQueue", "ArrayDeque", "C",
            "Java's PriorityQueue is a min-heap by default. For max-heap, use PriorityQueue(Collections.reverseOrder()).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is the 'Top K Frequent Elements' problem's optimal solution?",
            "Sort by frequency O(n log n)", "Bucket sort by frequency O(n)", "Min-heap of size k O(n log k)", "Both B and C are optimal depending on context", "D",
            "Bucket sort achieves O(n). Min-heap achieves O(n log k). Both are used in practice; bucket sort is asymptotically better.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Heap",
            "What is 'heapify down' (sift down) and when is it used?",
            "Moving element from root to leaf when it's too large for the heap", "After removing root, swap last element to root and move it down to restore heap property", "Initial heap construction only", "Inserting a new element", "B",
            "Heapify down: after removing root, place last element at root. Compare with children and swap with larger (max-heap) until heap property holds. O(log n).",
            Difficulty.MEDIUM, 3));
    }

    // =========================================================================
    // DSA - Graphs
    // =========================================================================
    private void addDsaGraphs(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What are the two common representations of a graph?",
            "Array and Linked List", "Adjacency Matrix and Adjacency List", "Stack and Queue", "Tree and Hash", "B",
            "Adjacency Matrix: O(V²) space, O(1) edge check. Adjacency List: O(V+E) space, O(degree(v)) edge check. Lists preferred for sparse graphs.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What algorithm detects if a cycle exists in a directed graph?",
            "BFS with visited array", "DFS with recursion stack (gray-white-black coloring)", "Topological sort only", "Dijkstra's algorithm", "B",
            "DFS with three states: unvisited (white), in-current-path (gray), done (black). A back edge (to gray node) indicates a cycle.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is Topological Sort and when is it used?",
            "Sorting graph edges by weight", "Linear ordering of vertices where for every edge (u,v), u comes before v; used for DAGs", "Shortest path algorithm", "Minimum spanning tree algorithm", "B",
            "Topological Sort orders DAG vertices so all edges go forward. Used in build systems, course prerequisites, task scheduling.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What does Kruskal's algorithm find and what is its time complexity?",
            "Shortest path; O(V log V)", "Minimum Spanning Tree; O(E log E) using sorting and Union-Find", "Maximum flow; O(V²E)", "Strongly Connected Components; O(V+E)", "B",
            "Kruskal's builds MST by sorting edges by weight and adding edge if it doesn't form a cycle (using Union-Find). O(E log E).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is the time complexity of Bellman-Ford algorithm?",
            "O(V log V)", "O(E log E)", "O(VE)", "O(V + E)", "C",
            "Bellman-Ford relaxes all E edges (V-1) times to find shortest paths, giving O(VE) time. Can detect negative cycles.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is a Strongly Connected Component (SCC)?",
            "Component with maximum edges", "Maximal subgraph where every vertex is reachable from every other vertex", "Minimum spanning subgraph", "Complete subgraph", "B",
            "SCC: maximal set of vertices where every pair (u,v) has a path from u to v and v to u. Found by Kosaraju's or Tarjan's algorithm in O(V+E).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is the Floyd-Warshall algorithm used for?",
            "Single-source shortest path", "All-pairs shortest paths in O(V³)", "Minimum spanning tree", "Topological ordering", "B",
            "Floyd-Warshall computes shortest paths between all pairs of vertices in O(V³) time and O(V²) space. Works with negative edges (not negative cycles).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is the Union-Find (Disjoint Set Union) data structure and what is its amortized complexity with optimizations?",
            "A sorting structure; O(n log n)", "Tracks connected components; O(α(n)) amortized with path compression and union by rank", "A tree balancing structure; O(log n)", "A hash map; O(1)", "B",
            "Union-Find with path compression and union by rank achieves O(α(n)) amortized per operation where α is the inverse Ackermann function.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is a bipartite graph?",
            "Graph with exactly 2 vertices", "Graph whose vertices can be divided into two independent sets with edges only between sets", "Graph with all nodes connected", "Directed acyclic graph", "B",
            "Bipartite: vertices split into two sets U and V; every edge connects a U-vertex to a V-vertex. No edges within same set. Detectable via 2-coloring BFS.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What problem does Prim's algorithm solve and what is its complexity with a binary heap?",
            "Shortest path; O(E log V)", "Minimum Spanning Tree; O(E log V) with adjacency list and priority queue", "Maximum flow; O(V²)", "Cycle detection; O(V+E)", "B",
            "Prim's builds MST by greedily adding minimum-weight edge connecting the MST to a new vertex. With priority queue: O(E log V).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is the time complexity of Dijkstra's algorithm with an adjacency list and binary heap?",
            "O(V²)", "O(E + V log V)", "O((V + E) log V)", "O(V log E)", "C",
            "With binary heap priority queue: each vertex extracted once O(V log V), each edge relaxed once O(E log V). Total O((V+E) log V).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is a bridge in a graph?",
            "An edge connecting two components", "An edge whose removal increases the number of connected components", "The heaviest edge in MST", "The shortest path edge", "B",
            "A bridge is a critical edge; removing it disconnects the graph. Found using DFS and low-link values in O(V+E) (Tarjan's bridge finding).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is the maximum flow problem and which algorithm solves it?",
            "Find heaviest path; Dijkstra", "Find maximum amount of flow from source to sink; Ford-Fulkerson or Edmonds-Karp", "Find MST; Kruskal", "Find SCC; Kosaraju", "B",
            "Maximum flow: push as much flow as possible from source to sink respecting edge capacities. Ford-Fulkerson O(E * max_flow), Edmonds-Karp O(VE²).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is the difference between a tree and a graph?",
            "No difference", "A tree is an acyclic connected graph with exactly V-1 edges; a graph can have cycles and disconnected components", "Trees are always directed", "Graphs are always unweighted", "B",
            "A tree is a special case of a graph: connected, undirected, acyclic, exactly V-1 edges, unique path between any two nodes.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Graphs",
            "What is the number of edges in a complete graph with V vertices?",
            "V", "V-1", "V*(V-1)/2", "V²", "C",
            "A complete graph (every pair connected) has V*(V-1)/2 edges for an undirected graph.",
            Difficulty.EASY, 2));
    }

    // =========================================================================
    // DSA - Greedy
    // =========================================================================
    private void addDsaGreedy(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is the fundamental principle of a greedy algorithm?",
            "Explore all possibilities", "Make the locally optimal choice at each step hoping to reach global optimum", "Use dynamic programming", "Divide into subproblems", "B",
            "Greedy makes the best immediate choice without reconsidering. Works when the problem has 'greedy choice property' and 'optimal substructure'.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "The Activity Selection Problem is solved by greedy by:",
            "Selecting activities by start time", "Selecting activities by minimum duration", "Selecting activities by earliest finish time", "Random selection", "C",
            "Sort activities by finish time. Always pick the next activity with earliest finish time that starts after the last selected activity. O(n log n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is the fractional knapsack problem and why does greedy work?",
            "Select items to maximize value with weight limit; greedy fails", "Select items by value/weight ratio (greedy works since fractions are allowed)", "DP approach only", "Backtracking required", "B",
            "Fractional knapsack allows taking fractions. Greedy: sort by value/weight ratio, take maximum fraction. Globally optimal due to greedy choice property.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "Why does greedy fail for the 0/1 Knapsack problem?",
            "Items can be divided", "Taking the locally optimal item may miss the globally optimal combination (no fractional choice allowed)", "Greedy is too slow", "Items are always integers", "B",
            "In 0/1 knapsack, an item is either taken or not. A greedy choice (highest ratio) might leave capacity unused that could have been better filled differently.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is Huffman Coding and how does greedy help?",
            "Fixed-length encoding; sorting", "Variable-length prefix coding; greedy builds tree by repeatedly merging two lowest-frequency nodes", "Constant compression; random", "Block encoding; DP", "B",
            "Huffman: use a min-heap, repeatedly merge two nodes with lowest frequencies. Greedy ensures optimal prefix-free code. O(n log n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is the minimum number of platforms needed at a station given arrival and departure times?",
            "Number of trains", "Maximum overlap of train timings at any point; found by sorting and two-pointer/greedy scan", "Number of departures before all arrivals", "Always 1", "B",
            "Sort arrivals and departures. Use two pointers: when arrival < departure, platform needed (increment count); else decrement. Track max count.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is the Coin Change Greedy approach and when does it fail?",
            "Always works perfectly", "Greedy picks largest coins first; fails for non-canonical coin systems (e.g., coins [1,3,4] target 6: greedy gives 4+1+1=3 coins but optimal is 3+3=2)", "Greedy uses DP", "Sort coins ascending", "B",
            "For standard denominations (US coins), greedy works. But for arbitrary coin systems, greedy may not give minimum coins — DP is needed.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "In the 'Jump Game' problem, what is the greedy approach?",
            "Try all jumps via backtracking", "Track the farthest reachable index at each step; if index exceeds farthest, return false", "Sort jump values", "BFS from start", "B",
            "Maintain 'maxReach'. At each index, if i > maxReach, unreachable. Else update maxReach = max(maxReach, i + nums[i]). O(n).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is the 'Task Scheduler' problem's greedy solution?",
            "Sort and schedule", "Schedule most frequent task first; idle time = max(0, (maxFreq-1)*(n+1) + countMaxFreq - tasks)", "Round robin", "DP table", "B",
            "The most frequent task determines idles needed. Formula: max(tasks, (maxFreq-1)*(n+1) + countOfMaxFreq) gives minimum time. O(n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What two properties must a problem have for greedy to give an optimal solution?",
            "Sorted input and unique elements", "Greedy Choice Property and Optimal Substructure", "Overlapping subproblems and memoization", "Divide and conquer structure", "B",
            "Greedy works when: (1) a locally optimal choice is globally safe (Greedy Choice Property), (2) optimal solution contains optimal sub-solutions (Optimal Substructure).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is the 'Gas Station' circular problem's greedy insight?",
            "Try all starting positions O(n²)", "Total gas ≥ total cost → solution exists; start from index where cumulative sum drops below 0", "Use DP", "Binary search for start", "B",
            "If total gas ≥ total cost, a solution exists. Start from 0; if tank drops below 0, set start to next index. The last chosen start is the answer. O(n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is the 'Meeting Rooms II' problem and its greedy solution?",
            "Find if all meetings fit in one room; sort and scan", "Find minimum rooms needed; use min-heap of end times or sort and track overlaps", "Graph coloring", "DP approach", "B",
            "Sort meetings by start time. Use min-heap of end times. If new meeting starts after heap top ends, reuse room (pop and push new end); else add new room.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What makes greedy provably optimal for Kruskal's and Prim's MST algorithms?",
            "Random selection", "The Cut Property: the minimum weight edge crossing a cut is always in some MST", "DFS ordering", "Sorting guarantees optimality", "B",
            "The Cut Property of matroids guarantees that greedily selecting the minimum weight crossing edge at each step always leads to an MST.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "In the 'Assign Cookies' problem, what greedy approach satisfies the most children?",
            "Give largest cookies first", "Sort children's greed and cookie sizes; use two pointers to match smallest sufficient cookie to each child", "Random assignment", "Give cookies by greed factor only", "B",
            "Sort both arrays. For each child (in order of greed), assign the smallest cookie that satisfies them. Two pointers, O(n log n).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Greedy",
            "What is the time complexity of Dijkstra's greedy step (choosing minimum distance vertex)?",
            "O(1)", "O(V) with array, O(log V) with binary heap", "O(E)", "O(V log V)", "B",
            "Without a priority queue: O(V) per extraction (scan all). With binary heap: O(log V) per extraction. Total complexity differs accordingly.",
            Difficulty.MEDIUM, 3));
    }

    // =========================================================================
    // DSA - Dynamic Programming
    // =========================================================================
    private void addDsaDynamicProgramming(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What are the two key properties a problem must have for Dynamic Programming to apply?",
            "Greedy choice and sorting", "Overlapping Subproblems and Optimal Substructure", "Recursion and memoization", "Divide and conquer structure", "B",
            "DP applies when: (1) subproblems repeat (Overlapping Subproblems) — memoize them; (2) optimal solution uses optimal sub-solutions (Optimal Substructure).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the difference between top-down DP (memoization) and bottom-up DP (tabulation)?",
            "Top-down is always faster", "Top-down: recursive with cache; bottom-up: iterative filling table from smallest subproblems", "Bottom-up uses recursion", "They always produce different results", "B",
            "Top-down (memoization): recursive, cache results on first computation. Bottom-up (tabulation): iterative, fill DP table from base cases to final answer.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the time and space complexity of the 0/1 Knapsack DP solution?",
            "O(n²) time, O(n) space", "O(n*W) time, O(n*W) space; optimizable to O(W) space", "O(n log n) time, O(log n) space", "O(2^n) time, O(n) space", "B",
            "Standard 0/1 Knapsack DP: O(n*W) time, O(n*W) space with 2D table. Optimize to O(W) space using 1D rolling array.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the Longest Common Subsequence (LCS) of 'ABCBDAB' and 'BDCAB'?",
            "3", "4", "5", "2", "B",
            "LCS('ABCBDAB', 'BDCAB') = 'BCAB' or 'BDAB' = length 4. DP table computed in O(m*n) time.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the recurrence relation for the Coin Change (minimum coins) DP?",
            "dp[i] = dp[i-1] + 1", "dp[i] = min(dp[i - coin] + 1) for each coin ≤ i", "dp[i] = max(dp[i-1], coin[i])", "dp[i] = dp[i] + dp[i-1]", "B",
            "For each amount i, dp[i] = min over all coins c of (dp[i-c] + 1). Base case: dp[0] = 0. O(amount * coins) time.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the Longest Increasing Subsequence (LIS) problem's O(n log n) solution?",
            "DP with memoization O(n²)", "Maintain a 'tails' array with binary search to find insertion point for each element", "Greedy selection", "Sort and find LCS", "B",
            "Patience sorting: maintain 'tails' array where tails[i] = smallest tail of increasing subsequence of length i+1. Binary search for each element. O(n log n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the Matrix Chain Multiplication problem?",
            "Multiply all matrices", "Find optimal parenthesization order to minimize total scalar multiplications", "Matrix addition order", "Sparse matrix optimization", "B",
            "Find the order to parenthesize matrix product A1*A2*...*An minimizing cost. DP: dp[i][j] = min cost to multiply matrices i through j. O(n³).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the DP recurrence for 'Edit Distance' between strings s and t?",
            "dp[i][j] = dp[i-1][j-1] if equal, else 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])",
            "dp[i][j] = dp[i-1][j] + dp[i][j-1]",
            "dp[i][j] = max(i, j)",
            "dp[i][j] = 0 if equal, else 1", "A",
            "If s[i]==t[j]: dp[i][j] = dp[i-1][j-1] (no edit). Else: min(insert=dp[i][j-1], delete=dp[i-1][j], replace=dp[i-1][j-1]) + 1.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the 'Rod Cutting' problem and what is its DP recurrence?",
            "Sorting rods by length", "Given rod of length n and prices, maximize revenue: dp[i] = max(price[j] + dp[i-j]) for j = 1 to i", "Binary search for optimal cut", "Greedy cut at midpoint", "B",
            "dp[i] = max revenue from rod of length i = max over j from 1 to i of (price[j] + dp[i-j]). O(n²) time and O(n) space.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What does the DP state dp[i][0] and dp[i][1] represent in stock buy-sell with cooldown?",
            "Buy price and sell price", "Maximum profit on day i when not holding [0] or holding [1] stock", "Stock quantity and price", "Transaction count", "B",
            "dp[i][0]: max profit on day i without stock (sold before or rested). dp[i][1]: max profit on day i holding stock. Transitions handle cooldown.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the Catalan number formula and what does it count?",
            "n! combinations", "C(n) = C(2n,n)/(n+1); counts BST shapes, parenthesizations, Dyck paths", "2^n subsets", "Fibonacci variants", "B",
            "Catalan number C(n) = C(2n,n)/(n+1). Counts: number of BSTs with n keys, valid bracket sequences of length 2n, triangulations, etc.",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the 'Subset Sum' DP solution and its complexity?",
            "Greedy selection O(n log n)", "dp[i][s] = true if subset of first i elements sums to s; O(n*sum) time, O(n*sum) space", "Backtracking only", "Hash set approach O(n)", "B",
            "dp[i][j] = dp[i-1][j] OR dp[i-1][j-arr[i]]. O(n * sum) time, space optimizable to O(sum) with 1D array.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the Egg Drop problem and what is the DP approach?",
            "Count eggs in a basket", "Find minimum trials to determine critical floor with f floors and e eggs; dp[e][t] = floors testable with e eggs and t trials", "Binary search always works", "Always use linear scan", "B",
            "dp[e][t] = dp[e-1][t-1] + dp[e][t-1] + 1 (egg breaks or survives). Find minimum t such that dp[eggs][t] ≥ floors. O(eggs * log(floors)).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is space optimization in 2D DP using 1D rolling array?",
            "Using only one variable", "Current row depends only on previous row; use two 1D arrays alternately to reduce O(n*m) to O(m) space", "Sort before DP", "Hash map optimization", "B",
            "If dp[i][j] only depends on dp[i-1][...], maintain only the current and previous rows. Reduces space from O(n*m) to O(m).",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Dynamic Programming",
            "What is the 'Palindrome Partitioning II' (minimum cuts) DP recurrence?",
            "dp[i] = dp[i-1] + 1 always", "dp[i] = min(dp[j-1] + 1) for all j ≤ i where s[j..i] is a palindrome; precompute palindrome table", "Sort characters then cut", "Backtracking all partitions", "B",
            "Precompute isPalin[j][i] in O(n²). Then dp[i] = 0 if s[0..i] is palindrome, else min(dp[j-1]+1) for valid j. O(n²) time.",
            Difficulty.HARD, 4));
    }

    // =========================================================================
    // DSA - Bit Manipulation
    // =========================================================================
    private void addDsaBitManipulation(List<QSpec> list) {
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "What is the result of 5 & 3 in binary?",
            "7", "2", "1", "6", "C",
            "5 = 101, 3 = 011. AND: 001 = 1.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "What is the result of 5 | 3?",
            "7", "2", "1", "15", "A",
            "5 = 101, 3 = 011. OR: 111 = 7.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "How do you check if a number n is a power of 2 using bit manipulation?",
            "n % 2 == 0", "n & (n-1) == 0 AND n > 0", "n >> 1 == 0", "n ^ 2 == 0", "B",
            "Powers of 2 have exactly one set bit. n & (n-1) clears the lowest set bit. If result is 0 and n > 0, n is a power of 2.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "How do you count the number of set bits (1s) in an integer n?",
            "Brian Kernighan's: repeatedly do n = n & (n-1), count iterations", "n / 2 iterations", "Integer.toString(n, 2).length()", "n >> 32", "A",
            "Brian Kernighan's algorithm: n = n & (n-1) clears the lowest set bit each iteration. Count iterations until n = 0. O(set bits).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "What does the XOR operation return when applied to the same number twice?",
            "The number doubled", "0", "1", "The number itself", "B",
            "n XOR n = 0. XOR is self-inverse. This property is used to find a single non-duplicate in an array of pairs.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "Given an array where every element appears twice except one, find the single element using XOR.",
            "Sum all and subtract sum of pairs", "XOR all elements; pairs cancel (a^a=0), leaving the single element", "Sort and find unpaired", "HashMap frequency count", "B",
            "XOR all elements: duplicate pairs cancel to 0. The single element XORed with 0 = itself. O(n) time, O(1) space.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "How do you swap two integers without a temporary variable using XOR?",
            "a = a + b; b = a - b; a = a - b", "a ^= b; b ^= a; a ^= b", "Use a third variable always", "Multiply and divide", "B",
            "a^=b makes a = a XOR b. b^=a makes b = b XOR (a XOR b) = original a. a^=b makes a = (a XOR b) XOR a = original b.",
            Difficulty.MEDIUM, 3));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "What does left shift (<<) by 1 do to an integer?",
            "Divides by 2", "Multiplies by 2", "Rotates bits", "Flips all bits", "B",
            "Left shift by 1 position multiplies the integer by 2. n << k = n * 2^k.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "How do you get the ith bit of a number n?",
            "(n >> i) & 1", "n & i", "n ^ (1 << i)", "n | (1 << i)", "A",
            "Right-shift n by i positions, then AND with 1. (n >> i) & 1 returns 1 if ith bit is set, 0 otherwise.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "How do you set the ith bit of a number n?",
            "n & ~(1 << i)", "n | (1 << i)", "n ^ (1 << i)", "n >> i", "B",
            "Create mask with 1 at position i using (1 << i), then OR with n to set that bit: n | (1 << i).",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "How do you clear (reset to 0) the ith bit of a number n?",
            "n | (1 << i)", "n & ~(1 << i)", "n ^ (1 << i)", "n >> i", "B",
            "Create mask ~(1 << i) with 0 at position i and 1s everywhere else. AND with n clears bit i.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "What is the output of: 7 ^ 3 in binary?",
            "4", "5", "3", "10", "A",
            "7 = 111, 3 = 011. XOR: 100 = 4.",
            Difficulty.EASY, 2));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "How do you find two numbers that appear once in an array where every other number appears twice?",
            "Two XOR passes", "XOR all to get x^y; find rightmost set bit; partition array; XOR each partition", "Sorting", "HashMap", "B",
            "XOR all → x^y. Find rightmost set bit. Split array into two groups by that bit. XOR each group gives x and y separately. O(n), O(1).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "What is the total number of set bits in all numbers from 1 to n?",
            "Cannot be computed efficiently", "Can be computed in O(log n) by summing contributions of each bit position", "n log n", "n itself", "B",
            "For each bit position k, count how many numbers 1..n have bit k set using (n+1)/2^(k+1) * 2^k + max(0, (n+1) mod 2^(k+1) - 2^k). O(log n).",
            Difficulty.HARD, 4));
        list.add(new QSpec(DSA, DSA_SUB, "Bit Manipulation",
            "How does bitmask DP work for the Travelling Salesman Problem?",
            "Greedy nearest neighbor", "dp[mask][i] = min cost to visit all cities in bitmask starting from city i; O(2^n * n²)", "Floyd-Warshall", "Backtracking without bitmask", "B",
            "TSP bitmask DP: state = (visited set as bitmask, current city). Transition: try all unvisited cities. O(2^n * n²) time, O(2^n * n) space.",
            Difficulty.HARD, 4));
    }
}
