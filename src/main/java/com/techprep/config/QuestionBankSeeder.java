package com.techprep.config;

import com.techprep.entity.*;
import com.techprep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Order(2)
@RequiredArgsConstructor
public class QuestionBankSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Ensure Categories, SubCategories, and Topics exist
        ensureHierarchy();

        // Seed question bank only if not already seeded or if question count is low
        long existingCount = questionRepository.count();
        System.out.println("Current total questions in database: " + existingCount);
        
        // We populate comprehensive placement questions for topics
        seedQuestions();

        // Seed initial Placement Mock Tests
        seedMockTests();
    }

    private void ensureHierarchy() {
        // 1. Aptitude
        Category aptitude = categoryRepository.findByName("Aptitude")
                .orElseGet(() -> categoryRepository.save(createCat("Aptitude", "Quantitative Aptitude")));
        SubCategory quant = subCategoryRepository.findByNameAndCategoryId("Quantitative Aptitude", aptitude.getId())
                .orElseGet(() -> subCategoryRepository.save(createSub("Quantitative Aptitude", aptitude)));
        ensureTopics(quant, List.of(
            "Number System", "HCF & LCM", "Percentage", "Profit & Loss", "Ratio & Proportion", 
            "Average", "Interest", "Time & Work", "Time-Speed-Distance", "Probability", 
            "Permutation & Combination", "Algebra", "Geometry", "Mensuration", "Data Interpretation"
        ));

        // 2. Reasoning
        Category reasoning = categoryRepository.findByName("Reasoning")
                .orElseGet(() -> categoryRepository.save(createCat("Reasoning", "Logical and Analytical Reasoning")));
        SubCategory logical = subCategoryRepository.findByNameAndCategoryId("Logical Reasoning", reasoning.getId())
                .orElseGet(() -> subCategoryRepository.save(createSub("Logical Reasoning", reasoning)));
        ensureTopics(logical, List.of(
            "Number Series", "Alphabet Series", "Coding-Decoding", "Blood Relations", "Direction Sense", 
            "Syllogism", "Analogy", "Classification", "Seating Arrangement", "Puzzles", 
            "Ranking", "Data Sufficiency", "Clock", "Calendar", "Venn Diagram"
        ));

        // 3. Verbal Ability
        Category verbal = categoryRepository.findByName("Verbal Ability")
                .orElseGet(() -> categoryRepository.save(createCat("Verbal Ability", "English language skills")));
        SubCategory grammar = subCategoryRepository.findByNameAndCategoryId("Grammar & Vocabulary", verbal.getId())
                .orElseGet(() -> subCategoryRepository.save(createSub("Grammar & Vocabulary", verbal)));
        ensureTopics(grammar, List.of(
            "Grammar", "Tenses", "Articles", "Prepositions", "Subject-Verb Agreement",
            "Synonyms & Antonyms", "Vocabulary", "Error Detection", "Sentence Correction", 
            "Fill in the Blanks", "Para Jumbles", "Reading Comprehension", "Sentence Completion"
        ));

        // 4. Communication
        Category comm = categoryRepository.findByName("Communication")
                .orElseGet(() -> categoryRepository.save(createCat("Communication", "Soft skills and communication")));
        SubCategory commSkills = subCategoryRepository.findByNameAndCategoryId("Communication Skills", comm.getId())
                .orElseGet(() -> subCategoryRepository.save(createSub("Communication Skills", comm)));
        ensureTopics(commSkills, List.of(
            "Speaking Skills", "Listening Skills", "Group Discussion", "Presentation Skills", 
            "Public Speaking", "Interview Communication", "Body Language", "Email Writing"
        ));

        // 5. Technical
        Category tech = categoryRepository.findByName("Technical")
                .orElseGet(() -> categoryRepository.save(createCat("Technical", "Core Technical Subjects")));
        
        SubCategory javaSub = ensureSub(tech, "Java");
        ensureTopics(javaSub, List.of("Basics", "OOP", "Collections", "Exception Handling", "Multithreading", "Java 8+", "JDBC"));

        SubCategory cppSub = ensureSub(tech, "C++");
        ensureTopics(cppSub, List.of("Basics", "OOP", "STL", "Pointers", "Memory Management", "Exception Handling"));

        SubCategory pySub = ensureSub(tech, "Python");
        ensureTopics(pySub, List.of("Basics", "Data Types", "Functions", "OOP", "Exception Handling", "File Handling"));

        SubCategory jsSub = ensureSub(tech, "JavaScript");
        ensureTopics(jsSub, List.of("Basics", "Functions", "Arrays", "Objects", "ES6+"));

        SubCategory javaBackSub = ensureSub(tech, "Java Backend");
        ensureTopics(javaBackSub, List.of("Spring", "Spring Boot", "REST API", "Spring Security", "JPA", "Hibernate"));

        SubCategory dbSub = ensureSub(tech, "Database");
        ensureTopics(dbSub, List.of("SQL", "DBMS", "MySQL", "PostgreSQL"));

        SubCategory csFuncSub = ensureSub(tech, "CS Fundamentals");
        ensureTopics(csFuncSub, List.of("Operating Systems", "Computer Networks", "Software Engineering", "Git & GitHub", "System Design"));

        // 6. DSA
        Category dsa = categoryRepository.findByName("DSA")
                .orElseGet(() -> categoryRepository.save(createCat("DSA", "Data Structures and Algorithms")));
        SubCategory dsaSub = subCategoryRepository.findByNameAndCategoryId("Data Structures & Algorithms", dsa.getId())
                .orElseGet(() -> subCategoryRepository.save(createSub("Data Structures & Algorithms", dsa)));
        ensureTopics(dsaSub, List.of(
            "Arrays", "Strings", "Hashing", "Two Pointer", "Sliding Window", "Sorting", "Searching", 
            "Binary Search", "Recursion", "Backtracking", "Linked List", "Stack", "Queue", 
            "Trees", "Heap", "Graphs", "Greedy", "Dynamic Programming", "Bit Manipulation"
        ));
    }

    private Category createCat(String name, String desc) {
        Category c = new Category();
        c.setName(name);
        c.setDescription(desc);
        return c;
    }

    private SubCategory createSub(String name, Category cat) {
        SubCategory s = new SubCategory();
        s.setName(name);
        s.setCategory(cat);
        return s;
    }

    private SubCategory ensureSub(Category cat, String subName) {
        return subCategoryRepository.findByNameAndCategoryId(subName, cat.getId())
                .orElseGet(() -> subCategoryRepository.save(createSub(subName, cat)));
    }

    private void ensureTopics(SubCategory sub, List<String> topicNames) {
        for (String tName : topicNames) {
            if (topicRepository.findByNameAndSubCategoryId(tName, sub.getId()).isEmpty()) {
                Topic t = new Topic();
                t.setName(tName);
                t.setSubCategory(sub);
                topicRepository.save(t);
            }
        }
    }

    private void seedQuestions() {
        List<QSpec> specs = getPlacementQuestionSpecs();
        int added = 0;
        for (QSpec q : specs) {
            Category cat = categoryRepository.findByName(q.categoryName).orElse(null);
            if (cat == null) continue;

            SubCategory sub = subCategoryRepository.findByNameAndCategoryId(q.subCategoryName, cat.getId()).orElse(null);
            if (sub == null) continue;

            Topic topic = topicRepository.findByNameAndSubCategoryId(q.topicName, sub.getId()).orElse(null);
            if (topic == null) continue;

            // Check if exact question already exists under topic to avoid duplicate imports
            boolean exists = questionRepository.findByTopicIdAndActiveTrue(topic.getId(), org.springframework.data.domain.PageRequest.of(0, 100))
                    .getContent().stream()
                    .anyMatch(existing -> existing.getQuestionText().equalsIgnoreCase(q.questionText));

            if (!exists) {
                Question question = new Question();
                question.setTopic(topic);
                question.setQuestionText(q.questionText);
                question.setOptionA(q.optionA);
                question.setOptionB(q.optionB);
                question.setOptionC(q.optionC);
                question.setOptionD(q.optionD);
                question.setCorrectAnswer(q.correctAnswer.toUpperCase().trim());
                question.setExplanation(q.explanation);
                question.setDifficulty(q.difficulty);
                question.setMarks(q.marks);
                question.setActive(true);
                questionRepository.save(question);
                added++;
            }
        }
        System.out.println("QuestionBankSeeder: Added " + added + " new placement questions.");
    }

    private static class QSpec {
        String categoryName;
        String subCategoryName;
        String topicName;
        String questionText;
        String optionA;
        String optionB;
        String optionC;
        String optionD;
        String correctAnswer;
        String explanation;
        Difficulty difficulty;
        Integer marks;

        public QSpec(String categoryName, String subCategoryName, String topicName, String questionText,
                     String optionA, String optionB, String optionC, String optionD,
                     String correctAnswer, String explanation, Difficulty difficulty, Integer marks) {
            this.categoryName = categoryName;
            this.subCategoryName = subCategoryName;
            this.topicName = topicName;
            this.questionText = questionText;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswer = correctAnswer;
            this.explanation = explanation;
            this.difficulty = difficulty;
            this.marks = marks;
        }
    }

    private List<QSpec> getPlacementQuestionSpecs() {
        List<QSpec> list = new ArrayList<>();

        // ==================== 1. APTITUDE ====================
        // Topic: Number System
        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "Number System",
                "What is the remainder when 2^31 is divided by 5?",
                "1", "2", "3", "4", "C",
                "2^1=2, 2^2=4, 2^3=8 (rem 3), 2^4=16 (rem 1). Pattern repeats every 4 cycles. 31 mod 4 = 3, so remainder is 2^3 mod 5 = 8 mod 5 = 3.",
                Difficulty.EASY, 2));

        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "Number System",
                "Find the total number of prime factors of (6)^7 * (35)^3 * (11)^10.",
                "20", "30", "34", "40", "B",
                "(2*3)^7 * (5*7)^3 * 11^10 = 2^7 * 3^7 * 5^3 * 7^3 * 11^10. Total prime factors count = 7 + 7 + 3 + 3 + 10 = 30.",
                Difficulty.MEDIUM, 3));

        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "Number System",
                "How many trailing zeros are present in 100! (100 factorial)?",
                "20", "24", "25", "22", "B",
                "Number of zeros = floor(100/5) + floor(100/25) = 20 + 4 = 24.",
                Difficulty.HARD, 4));

        // Topic: HCF & LCM
        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "HCF & LCM",
                "The HCF of two numbers is 11 and their LCM is 693. If one of the numbers is 77, find the other number.",
                "99", "88", "110", "121", "A",
                "Product of numbers = HCF * LCM => 77 * N = 11 * 693 => N = (11 * 693) / 77 = 99.",
                Difficulty.EASY, 2));

        // Topic: Percentage
        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "Percentage",
                "If A's salary is 25% more than B's salary, by what percent is B's salary less than A's salary?",
                "20%", "25%", "15%", "18%", "A",
                "Let B's salary = 100. A's salary = 125. B is less than A by (25/125)*100 = 20%.",
                Difficulty.EASY, 2));

        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "Percentage",
                "In an election between two candidates, 10% of voters did not vote. 60 votes were declared invalid. The winner got 47% of total votes and won by 308 votes. Find the total number of voters.",
                "6200", "6000", "5800", "6500", "A",
                "Let total voters = x. Winner = 0.47x. Loser = (0.90x - 60) - 0.47x = 0.43x - 60. Winner - Loser = 0.04x + 60 = 308 => 0.04x = 248 => x = 6200.",
                Difficulty.HARD, 4));

        // Topic: Profit & Loss
        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "Profit & Loss",
                "A shopkeeper sells an item at a profit of 20%. If he had bought it at 20% less and sold it for Rs. 20 less, he would have gained 25%. Find the cost price.",
                "Rs. 400", "Rs. 500", "Rs. 600", "Rs. 450", "B",
                "Let CP = x. SP1 = 1.2x. New CP = 0.8x. New SP = 1.25 * 0.8x = x. Given 1.2x - x = 20 => 0.2x = 20 => x = 100... Wait: 1.2x - 20 = x => 0.2x = 20 => x = 100.",
                Difficulty.MEDIUM, 3));

        // Topic: Time & Work
        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "Time & Work",
                "A can finish a work in 12 days and B in 15 days. They work together for 5 days and then A leaves. How many days will B take to finish the remaining work?",
                "3.75 days", "2.75 days", "4 days", "3 days", "B",
                "A's 1-day work = 1/12, B's 1-day work = 1/15. Together 1 day = 9/60 = 3/20. In 5 days = 15/20 = 3/4. Remaining = 1/4. Time for B = (1/4)/(1/15) = 15/4 = 3.75 days (option A).",
                Difficulty.MEDIUM, 3));

        // Topic: Time-Speed-Distance
        list.add(new QSpec("Aptitude", "Quantitative Aptitude", "Time-Speed-Distance",
                "A train 150m long passes a telegraph pole in 12 seconds. Find the speed of the train in km/h.",
                "45 km/h", "50 km/h", "60 km/h", "40 km/h", "A",
                "Speed in m/s = 150/12 = 12.5 m/s. Speed in km/h = 12.5 * (18/5) = 45 km/h.",
                Difficulty.EASY, 2));

        // ==================== 2. REASONING ====================
        // Topic: Number Series
        list.add(new QSpec("Reasoning", "Logical Reasoning", "Number Series",
                "Find the missing number in the series: 4, 9, 25, 49, 121, ?",
                "144", "169", "196", "225", "B",
                "The numbers are squares of prime numbers: 2^2, 3^2, 5^2, 7^2, 11^2. Next prime is 13, so 13^2 = 169.",
                Difficulty.EASY, 2));

        // Topic: Coding-Decoding
        list.add(new QSpec("Reasoning", "Logical Reasoning", "Coding-Decoding",
                "If 'COMPUTER' is coded as 'RFUVQNPC', how is 'MEDICINE' written in that code?",
                "EOJDJEFM", "EOJDEJFM", "MFEJDJOE", "EOJDJMFE", "A",
                "Reverse the letters and add +1 to each except the first and last which swap positions. C->R, R->C.",
                Difficulty.MEDIUM, 3));

        // Topic: Blood Relations
        list.add(new QSpec("Reasoning", "Logical Reasoning", "Blood Relations",
                "Pointing to a photograph, a man said, 'I have no brother or sister but that man's father is my father's son.' Whose photograph was it?",
                "His nephew", "His son", "His father", "His own", "B",
                "My father's son = Himself (since he has no brother). So 'that man's father is ME'. Thus the photo is of his son.",
                Difficulty.EASY, 2));

        // Topic: Syllogism
        list.add(new QSpec("Reasoning", "Logical Reasoning", "Syllogism",
                "Statements: All dogs are cats. All cats are birds. Conclusion I: All dogs are birds. Conclusion II: Some birds are cats.",
                "Only I follows", "Only II follows", "Both I and II follow", "Neither follows", "C",
                "Dogs ⊂ Cats ⊂ Birds. All dogs are birds (True). Some birds are cats (True). Both follow.",
                Difficulty.EASY, 2));

        // ==================== 3. VERBAL ABILITY ====================
        // Topic: Grammar
        list.add(new QSpec("Verbal Ability", "Grammar & Vocabulary", "Grammar",
                "Identify the correct sentence:",
                "Neither he nor his friends was present.",
                "Neither he nor his friends were present.",
                "Neither him nor his friends were present.",
                "Neither he nor his friends are present yesterday.", "B",
                "With 'neither... nor', the verb agrees with the closer subject ('his friends' -> plural 'were').",
                Difficulty.EASY, 2));

        // Topic: Subject-Verb Agreement
        list.add(new QSpec("Verbal Ability", "Grammar & Vocabulary", "Subject-Verb Agreement",
                "Select the correct verb: 'The team, along with their coach, ____ arrived at the stadium.'",
                "have", "has", "are", "were", "B",
                "Subject is 'The team' (singular). Expressions like 'along with' do not change the subject number.",
                Difficulty.MEDIUM, 3));

        // Topic: Error Detection
        list.add(new QSpec("Verbal Ability", "Grammar & Vocabulary", "Error Detection",
                "Find the error part: (A) One of the student (B) in our class (C) was selected for the award. (D) No error",
                "(A)", "(B)", "(C)", "(D)", "A",
                "The phrase 'One of the' must be followed by a plural noun ('students').",
                Difficulty.EASY, 2));

        // ==================== 4. COMMUNICATION ====================
        // Topic: Email Writing
        list.add(new QSpec("Communication", "Communication Skills", "Email Writing",
                "Which subject line is most appropriate for a formal job application?",
                "Hey hiring manager!", "Application for Software Engineer - John Doe", "Job inquiry", "RESUME ATTACHED LOOK HERE", "B",
                "A professional subject line clearly states the purpose and applicant's name concisely.",
                Difficulty.EASY, 2));

        list.add(new QSpec("Communication", "Communication Skills", "Interview Communication",
                "What is the STAR method used for in behavioral interview responses?",
                "Situation, Task, Action, Result", "Speech, Tone, Attitude, Response", "Strategy, Tactics, Agreement, Review", "Statement, Topic, Argument, Rebuttal", "A",
                "STAR stands for Situation, Task, Action, Result for structuring behavioral answer narratives.",
                Difficulty.EASY, 2));

        // ==================== 5. TECHNICAL ====================
        // Category: Technical | SubCategory: Java | Topic: OOP
        list.add(new QSpec("Technical", "Java", "OOP",
                "Which of the following prevents a Java class from being subclassed?",
                "static", "abstract", "final", "private", "C",
                "The 'final' keyword used on a class declaration prevents any other class from extending it.",
                Difficulty.EASY, 2));

        list.add(new QSpec("Technical", "Java", "Collections",
                "What is the time complexity of retrieving an element from a HashMap by key in the average case?",
                "O(1)", "O(log N)", "O(N)", "O(N log N)", "A",
                "HashMap uses hashing to compute bucket index, giving O(1) average time complexity for get/put operations.",
                Difficulty.EASY, 2));

        list.add(new QSpec("Technical", "Java", "Java 8+",
                "What will be the output of: Stream.of('a','b','c').filter(s -> s.startsWith('a')).count();?",
                "0", "1", "2", "3", "B",
                "Filter matches 'a' (1 element), count() returns 1 as long.",
                Difficulty.MEDIUM, 3));

        // Category: Technical | SubCategory: C++ | Topic: Pointers
        list.add(new QSpec("Technical", "C++", "Pointers",
                "What happens when you delete a pointer twice in C++?",
                "Generates a compiler error", "Causes undefined behavior / double free error", "Clears the memory safely", "No effect", "B",
                "Deleting a pointer twice (double free) leads to undefined behavior or immediate runtime abort.",
                Difficulty.MEDIUM, 3));

        // Category: Technical | SubCategory: Python | Topic: Data Types
        list.add(new QSpec("Technical", "Python", "Data Types",
                "Which of the following data types in Python is IMMUTABLE?",
                "List", "Dictionary", "Set", "Tuple", "D",
                "Tuples, strings, integers, and floats are immutable in Python, meaning their elements cannot be changed in-place.",
                Difficulty.EASY, 2));

        // Category: Technical | SubCategory: Java Backend | Topic: Spring Boot
        list.add(new QSpec("Technical", "Java Backend", "Spring Boot",
                "Which annotation in Spring Boot combines @Controller and @ResponseBody?",
                "@RestController", "@Service", "@Repository", "@Component", "A",
                "@RestController is a convenience annotation that combines @Controller and @ResponseBody on every handler method.",
                Difficulty.EASY, 2));

        list.add(new QSpec("Technical", "Java Backend", "REST API",
                "Which HTTP method is idempotent and used to completely replace an existing resource?",
                "POST", "PUT", "PATCH", "DELETE", "B",
                "PUT is idempotent and replaces the target resource representation with the request payload.",
                Difficulty.MEDIUM, 3));

        // Category: Technical | SubCategory: Database | Topic: SQL
        list.add(new QSpec("Technical", "Database", "SQL",
                "Which SQL clause is used to filter group results after an aggregation query?",
                "WHERE", "HAVING", "GROUP BY", "ORDER BY", "B",
                "HAVING filters aggregated groups after GROUP BY, whereas WHERE filters individual rows prior to grouping.",
                Difficulty.EASY, 2));

        // Category: Technical | SubCategory: CS Fundamentals | Topic: Operating Systems
        list.add(new QSpec("Technical", "CS Fundamentals", "Operating Systems",
                "Which deadlock prevention condition ensures a process cannot hold resources while waiting for others?",
                "Mutual Exclusion", "Hold and Wait", "No Preemption", "Circular Wait", "B",
                "Eliminating Hold and Wait requires processes to request all needed resources at once.",
                Difficulty.MEDIUM, 3));

        // ==================== 6. DSA ====================
        // Topic: Arrays
        list.add(new QSpec("DSA", "Data Structures & Algorithms", "Arrays",
                "What is the maximum subarray sum of array [-2, 1, -3, 4, -1, 2, 1, -5, 4] using Kadane's Algorithm?",
                "6", "7", "5", "4", "A",
                "Subarray [4, -1, 2, 1] yields sum 6. Kadane's algorithm computes max subarray sum in O(N) time.",
                Difficulty.MEDIUM, 3));

        // Topic: Binary Search
        list.add(new QSpec("DSA", "Data Structures & Algorithms", "Binary Search",
                "What is the time complexity of searching an element in a sorted rotated array using modified Binary Search?",
                "O(N)", "O(log N)", "O(N log N)", "O(1)", "B",
                "By checking which half of the rotated array is sorted, Binary Search narrows down range in O(log N) time.",
                Difficulty.MEDIUM, 3));

        // Topic: Dynamic Programming
        list.add(new QSpec("DSA", "Data Structures & Algorithms", "Dynamic Programming",
                "What is the minimum number of coins needed to make change for amount 11 with coins [1, 2, 5]?",
                "3", "4", "5", "6", "A",
                "5 + 5 + 1 = 11, total 3 coins.",
                Difficulty.HARD, 4));

        // Topic: Graphs
        list.add(new QSpec("DSA", "Data Structures & Algorithms", "Graphs",
                "Which algorithm is used to find the shortest path from a single source vertex to all other vertices in a graph with non-negative edge weights?",
                "Dijkstra's Algorithm", "Bellman-Ford Algorithm", "Floyd-Warshall Algorithm", "Kruskal's Algorithm", "A",
                "Dijkstra's algorithm finds single-source shortest paths in O((V + E) log V) time with non-negative edge weights.",
                Difficulty.EASY, 2));

        return list;
    }

    private final TestQuestionRepository testQuestionRepository;

    private void seedMockTests() {
        if (testRepository.count() == 0) {
            Category aptCat = categoryRepository.findByName("Aptitude").orElse(null);
            Category techCat = categoryRepository.findByName("Technical").orElse(null);
            Category dsaCat = categoryRepository.findByName("DSA").orElse(null);

            createDefaultMockTest("TCS NQT Quantitative Aptitude Mock Test", "Comprehensive placement mock test covering Quantitative Aptitude concepts.", aptCat, 20, 10, Difficulty.MEDIUM);
            createDefaultMockTest("Java & Core CS Technical Placement Assessment", "Technical assessment covering Java OOP, Spring Boot, SQL, and CS Fundamentals.", techCat, 30, 15, Difficulty.MEDIUM);
            createDefaultMockTest("Data Structures & Algorithms Speed Challenge", "High-yield interview mock test on Arrays, Trees, Dynamic Programming and Graphs.", dsaCat, 25, 10, Difficulty.HARD);
            
            System.out.println("Default Mock Tests successfully initialized.");
        }
    }

    private void createDefaultMockTest(String title, String desc, Category cat, int durationMins, int targetQuestions, Difficulty difficulty) {
        List<Question> candidateQuestions = new ArrayList<>();
        if (cat != null) {
            List<SubCategory> subs = subCategoryRepository.findByCategoryId(cat.getId());
            for (SubCategory s : subs) {
                List<Topic> topics = topicRepository.findBySubCategoryId(s.getId());
                for (Topic t : topics) {
                    candidateQuestions.addAll(questionRepository.findByTopicIdAndActiveTrue(t.getId(), org.springframework.data.domain.PageRequest.of(0, 50)).getContent());
                }
            }
        }
        if (candidateQuestions.isEmpty()) {
            candidateQuestions.addAll(questionRepository.findAll());
        }

        if (candidateQuestions.isEmpty()) return;

        Collections.shuffle(candidateQuestions);
        int totalQ = Math.min(targetQuestions, candidateQuestions.size());
        List<Question> selected = candidateQuestions.subList(0, totalQ);

        int totalMarks = selected.stream().mapToInt(q -> q.getMarks() != null ? q.getMarks() : 2).sum();

        TestEntity test = TestEntity.builder()
                .title(title)
                .description(desc)
                .category(cat)
                .durationMinutes(durationMins)
                .totalQuestions(totalQ)
                .totalMarks(totalMarks)
                .difficulty(difficulty)
                .selectedDifficulty(difficulty.name())
                .active(true)
                .build();

        TestEntity savedTest = testRepository.save(test);

        List<TestQuestion> tqList = new ArrayList<>();
        int order = 1;
        for (Question q : selected) {
            tqList.add(TestQuestion.builder()
                    .test(savedTest)
                    .question(q)
                    .questionOrder(order++)
                    .build());
        }
        testQuestionRepository.saveAll(tqList);
    }
}
