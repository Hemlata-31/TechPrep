package com.techprep.config;

import com.techprep.entity.Category;
import com.techprep.entity.SubCategory;
import com.techprep.entity.Topic;
import com.techprep.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            
            // Aptitude
            Category aptitude = createCategory("Aptitude", "Quantitative Aptitude");
            SubCategory quant = createSubCategory("Quantitative Aptitude", aptitude);
            createTopics(quant, List.of("Number System", "HCF & LCM", "Percentage", "Profit & Loss", "Ratio & Proportion", "Average", "Interest", "Time & Work", "Time-Speed-Distance", "Probability", "Permutation & Combination", "Algebra", "Geometry", "Mensuration", "Data Interpretation"));
            aptitude.getSubCategories().add(quant);
            categoryRepository.save(aptitude);

            // Reasoning
            Category reasoning = createCategory("Reasoning", "Logical and Analytical Reasoning");
            SubCategory logical = createSubCategory("Logical Reasoning", reasoning);
            createTopics(logical, List.of("Number Series", "Alphabet Series", "Coding-Decoding", "Blood Relations", "Direction Sense", "Syllogism", "Analogy", "Classification", "Seating Arrangement", "Puzzles", "Ranking", "Data Sufficiency", "Clock", "Calendar", "Venn Diagram"));
            reasoning.getSubCategories().add(logical);
            categoryRepository.save(reasoning);

            // Verbal Ability
            Category verbal = createCategory("Verbal Ability", "English language skills");
            SubCategory grammar = createSubCategory("Grammar & Vocabulary", verbal);
            createTopics(grammar, List.of("Grammar", "Tenses", "Articles", "Prepositions", "Vocabulary", "Synonyms & Antonyms", "Sentence Correction", "Error Detection", "Fill in the Blanks", "Para Jumbles", "Reading Comprehension", "Sentence Completion"));
            verbal.getSubCategories().add(grammar);
            categoryRepository.save(verbal);

            // Communication
            Category communication = createCategory("Communication", "Soft skills and communication");
            SubCategory softSkills = createSubCategory("Soft Skills", communication);
            createTopics(softSkills, List.of("Speaking Skills", "Listening Skills", "Presentation Skills", "Group Discussion", "Public Speaking", "Interview Communication", "Body Language", "Email Writing"));
            communication.getSubCategories().add(softSkills);
            categoryRepository.save(communication);

            // Technical
            Category technical = createCategory("Technical", "Core Technical Subjects");
            SubCategory java = createSubCategory("Java", technical);
            createTopics(java, List.of("Basics", "OOP", "Collections", "Exception Handling", "Multithreading", "Java 8+", "JDBC"));
            
            SubCategory cpp = createSubCategory("C++", technical);
            createTopics(cpp, List.of("Basics", "OOP", "STL", "Pointers", "Exception Handling"));
            
            SubCategory python = createSubCategory("Python", technical);
            createTopics(python, List.of("Basics", "Data Types", "Functions", "OOP", "Exception Handling", "File Handling"));
            
            SubCategory js = createSubCategory("JavaScript", technical);
            createTopics(js, List.of("Basics", "Functions", "Arrays", "Objects", "ES6+"));
            
            SubCategory javaBackend = createSubCategory("Java Backend", technical);
            createTopics(javaBackend, List.of("Spring", "Spring Boot", "Spring Security", "REST API", "JPA", "Hibernate"));
            
            SubCategory db = createSubCategory("Database", technical);
            createTopics(db, List.of("SQL", "DBMS", "MySQL", "PostgreSQL"));
            
            SubCategory csFunc = createSubCategory("CS Fundamentals", technical);
            createTopics(csFunc, List.of("Operating System", "Computer Networks", "Software Engineering", "Git & GitHub", "System Design"));
            
            technical.getSubCategories().addAll(List.of(java, cpp, python, js, javaBackend, db, csFunc));
            categoryRepository.save(technical);

            // DSA
            Category dsa = createCategory("DSA", "Data Structures and Algorithms");
            SubCategory dsaConcepts = createSubCategory("Concepts", dsa);
            createTopics(dsaConcepts, List.of("Arrays", "Strings", "Hashing", "Two Pointer", "Sliding Window", "Sorting", "Searching", "Binary Search", "Recursion", "Backtracking", "Linked List", "Stack", "Queue", "Trees", "Heap", "Graphs", "Greedy", "Dynamic Programming", "Bit Manipulation"));
            dsa.getSubCategories().add(dsaConcepts);
            categoryRepository.save(dsa);

            System.out.println("Default Categories, SubCategories, and Topics loaded successfully.");
        }
    }

    private Category createCategory(String name, String desc) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(desc);
        return category;
    }

    private SubCategory createSubCategory(String name, Category category) {
        SubCategory sub = new SubCategory();
        sub.setName(name);
        sub.setCategory(category);
        return sub;
    }

    private void createTopics(SubCategory sub, List<String> topicNames) {
        for (String topicName : topicNames) {
            Topic topic = new Topic();
            topic.setName(topicName);
            topic.setSubCategory(sub);
            sub.getTopics().add(topic);
        }
    }
}
