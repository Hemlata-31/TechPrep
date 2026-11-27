package com.techprep.config;

import com.techprep.entity.StudyNote;
import com.techprep.repository.StudyNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(11)
@RequiredArgsConstructor
public class StudyNoteSeeder implements CommandLineRunner {

    private final StudyNoteRepository studyNoteRepository;

    @Override
    public void run(String... args) {
        if (studyNoteRepository.count() > 0) {
            return;
        }
        studyNoteRepository.saveAll(List.of(
                StudyNote.builder()
                        .title("Percentage — Formulas & Question Types")
                        .category("Aptitude")
                        .description("Fraction-to-percentage table, successive % change, price-consumption, population growth, and 5 solved placement question types.")
                        .highlights("Net % = a + b + (ab)/100 | Same +x% & -x% = Loss (x^2/100)% | 1/8 = 12.5%, 1/7 = 14.28%")
                        .pdfUrl("/notes/aptitude-percentage.pdf")
                        .fileName("aptitude-percentage.pdf")
                        .build(),
                StudyNote.builder()
                        .title("Number System — Divisibility, Unit Digit & Remainders")
                        .category("Aptitude")
                        .description("Divisibility rules (2 to 11), unit digit cyclicity, remainder theorem, trailing zeroes in n!, and HCF/LCM formulas.")
                        .highlights("HCF x LCM = a x b | Cyclicity of 2,3,7,8 is 4 | Zeroes in n! = [n/5] + [n/25] + ...")
                        .pdfUrl("/notes/aptitude-number-system.pdf")
                        .fileName("aptitude-number-system.pdf")
                        .build(),
                StudyNote.builder()
                        .title("Profit, Loss & Discount")
                        .category("Aptitude")
                        .description("CP/SP/MP formulas, successive discounts, dishonest dealer shortcut, and same-SP gain/loss problems.")
                        .highlights("CP/MP = (100 - D%) / (100 + P%) | Dishonest Gain% = ((True - Used)/Used) x 100")
                        .pdfUrl("/notes/aptitude-profit-loss.pdf")
                        .fileName("aptitude-profit-loss.pdf")
                        .build(),
                StudyNote.builder()
                        .title("Time, Speed & Distance")
                        .category("Aptitude")
                        .description("km/h to m/s conversions, average speed, relative speed, trains crossing platforms, and boats & streams.")
                        .highlights("1 km/h = 5/18 m/s | Avg Speed = 2xy/(x+y) | Boat = (Down + Up)/2")
                        .pdfUrl("/notes/aptitude-time-speed-distance.pdf")
                        .fileName("aptitude-time-speed-distance.pdf")
                        .build(),
                StudyNote.builder()
                        .title("Time & Work + Pipes & Cisterns")
                        .category("Aptitude")
                        .description("LCM method, efficiency ratios, chain rule formula, and inlet/outlet pipe shortcuts.")
                        .highlights("Together = xy/(x+y) | (M1*D1*H1)/W1 = (M2*D2*H2)/W2")
                        .pdfUrl("/notes/aptitude-time-work.pdf")
                        .fileName("aptitude-time-work.pdf")
                        .build(),
                StudyNote.builder()
                        .title("DSA — Big-O Complexity & Array Patterns")
                        .category("DSA")
                        .description("Time complexity reference, HashMap O(1) lookup, Two Pointers, Kadane's Algorithm, and Stack patterns.")
                        .highlights("HashMap: O(1) avg | Binary Search: O(log n) | Kadane's: O(n)")
                        .pdfUrl("/notes/dsa-arrays-complexity.pdf")
                        .fileName("dsa-arrays-complexity.pdf")
                        .build()
        ));
        System.out.println("Seeded 6 Study Guide PDFs.");
    }
}

