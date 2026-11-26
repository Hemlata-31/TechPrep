package com.techprep.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techprep.dto.*;
import com.techprep.entity.*;
import com.techprep.repository.*;
import com.techprep.service.CodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodingServiceImpl implements CodingService {

    private static final String JUDGE0_EXECUTE_URL = "https://ce.judge0.com/submissions?base64_encoded=false&wait=true";

    private final CodingProblemRepository problemRepository;
    private final CodingTestCaseRepository testCaseRepository;
    private final CodingSubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional(readOnly = true)
    public List<CodingProblemSummaryDto> getAllProblems(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<Long> solvedIds = new HashSet<>(submissionRepository.findSolvedProblemIdsByUserId(user.getId()));

        return problemRepository.findByActiveTrueOrderByIdAsc().stream()
                .map(p -> CodingProblemSummaryDto.builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .slug(p.getSlug())
                        .difficulty(p.getDifficulty().name())
                        .categoryTag(p.getCategoryTag())
                        .solved(solvedIds.contains(p.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CodingProblemDetailDto getProblemBySlug(String slug, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        CodingProblem problem = problemRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Coding problem not found: " + slug));

        Set<Long> solvedIds = new HashSet<>(submissionRepository.findSolvedProblemIdsByUserId(user.getId()));
        List<CodingTestCaseDto> samples = testCaseRepository.findByProblemIdAndSampleTrueOrderByIdAsc(problem.getId())
                .stream()
                .map(tc -> CodingTestCaseDto.builder()
                        .id(tc.getId())
                        .displayInput(tc.getDisplayInput() != null ? tc.getDisplayInput() : tc.getInput())
                        .expectedOutput(tc.getExpectedOutput())
                        .explanation(tc.getExplanation())
                        .build())
                .collect(Collectors.toList());

        return CodingProblemDetailDto.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .slug(problem.getSlug())
                .difficulty(problem.getDifficulty().name())
                .categoryTag(problem.getCategoryTag())
                .description(problem.getDescription())
                .constraintsText(problem.getConstraintsText())
                .starterCodeJava(problem.getStarterCodeJava())
                .starterCodePython(problem.getStarterCodePython())
                .starterCodeCpp(problem.getStarterCodeCpp())
                .sampleTestCases(samples)
                .solved(solvedIds.contains(problem.getId()))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CodingRunResponseDto runSampleTests(Long problemId, CodingRunRequestDto request) {
        CodingProblem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found: " + problemId));
        List<CodingTestCase> samples = testCaseRepository.findByProblemIdAndSampleTrueOrderByIdAsc(problemId);
        return executeAgainstTestCases(problem, request.getLanguage(), request.getSourceCode(), samples);
    }

    @Override
    @Transactional
    public CodingRunResponseDto submitSolution(Long problemId, CodingRunRequestDto request, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        CodingProblem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found: " + problemId));
        List<CodingTestCase> allCases = testCaseRepository.findByProblemIdOrderByIdAsc(problemId);

        CodingRunResponseDto response = executeAgainstTestCases(
                problem, request.getLanguage(), request.getSourceCode(), allCases);

        CodingSubmission submission = CodingSubmission.builder()
                .user(user)
                .problem(problem)
                .language(request.getLanguage().toUpperCase())
                .sourceCode(request.getSourceCode())
                .status(response.getStatus())
                .passedTestCases(response.getPassedCount())
                .totalTestCases(response.getTotalCount())
                .errorMessage(response.getErrorMessage())
                .build();
        submissionRepository.save(submission);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodingSubmissionDto> getSubmissions(Long problemId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return submissionRepository.findByUserIdAndProblemIdOrderBySubmittedAtDesc(user.getId(), problemId)
                .stream()
                .map(s -> CodingSubmissionDto.builder()
                        .id(s.getId())
                        .language(s.getLanguage())
                        .status(s.getStatus())
                        .passedTestCases(s.getPassedTestCases())
                        .totalTestCases(s.getTotalTestCases())
                        .errorMessage(s.getErrorMessage())
                        .submittedAt(s.getSubmittedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CodingProblemSummaryDto createProblem(CreateCodingProblemRequestDto req) {
        if (problemRepository.existsBySlug(req.getSlug())) {
            throw new RuntimeException("Problem with slug already exists: " + req.getSlug());
        }
        CodingProblem problem = CodingProblem.builder()
                .title(req.getTitle())
                .slug(req.getSlug())
                .difficulty(Difficulty.valueOf(req.getDifficulty().toUpperCase()))
                .categoryTag(req.getCategoryTag())
                .description(req.getDescription())
                .constraintsText(req.getConstraintsText())
                .starterCodeJava(req.getStarterCodeJava())
                .starterCodePython(req.getStarterCodePython())
                .starterCodeCpp(req.getStarterCodeCpp())
                .driverCodeJava(req.getDriverCodeJava())
                .driverCodePython(req.getDriverCodePython())
                .driverCodeCpp(req.getDriverCodeCpp())
                .active(true)
                .build();
        CodingProblem saved = problemRepository.save(problem);

        List<CodingTestCase> cases = req.getTestCases().stream()
                .map(tc -> CodingTestCase.builder()
                        .problem(saved)
                        .input(tc.getInput())
                        .expectedOutput(tc.getExpectedOutput())
                        .displayInput(tc.getDisplayInput())
                        .explanation(tc.getExplanation())
                        .sample(tc.isSample())
                        .build())
                .collect(Collectors.toList());
        testCaseRepository.saveAll(cases);

        return CodingProblemSummaryDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .slug(saved.getSlug())
                .difficulty(saved.getDifficulty().name())
                .categoryTag(saved.getCategoryTag())
                .solved(false)
                .build();
    }

    private CodingRunResponseDto executeAgainstTestCases(
            CodingProblem problem, String language, String studentCode, List<CodingTestCase> testCases) {

        String lang = language.toUpperCase();
        String fullSource = buildFullSource(problem, lang, studentCode);

        List<CodingRunResponseDto.TestCaseResultDto> results = new ArrayList<>();
        int passed = 0;
        String overallStatus = "ACCEPTED";
        String firstError = null;

        for (int i = 0; i < testCases.size(); i++) {
            CodingTestCase tc = testCases.get(i);
            if (i > 0) {
                try { Thread.sleep(250); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
            }

            PistonResult res = callPiston(lang, fullSource, tc.getInput());

            if (res.apiError != null) {
                return CodingRunResponseDto.builder()
                        .status("API_ERROR")
                        .passedCount(passed)
                        .totalCount(testCases.size())
                        .errorMessage(res.apiError)
                        .results(results)
                        .build();
            }

            if (res.compileError != null && !res.compileError.isBlank()) {
                return CodingRunResponseDto.builder()
                        .status("COMPILATION_ERROR")
                        .passedCount(0)
                        .totalCount(testCases.size())
                        .errorMessage(res.compileError)
                        .results(results)
                        .build();
            }

            String actual = res.stdout != null ? res.stdout.trim() : "";
            String expected = tc.getExpectedOutput() != null ? tc.getExpectedOutput().trim() : "";
            boolean isRuntimeErr = res.stderr != null && !res.stderr.isBlank() && res.exitCode != 0;
            boolean ok = !isRuntimeErr && normalizeOutput(actual).equals(normalizeOutput(expected));

            if (ok) {
                passed++;
            } else {
                if (isRuntimeErr) {
                    if ("ACCEPTED".equals(overallStatus)) overallStatus = "RUNTIME_ERROR";
                    if (firstError == null) firstError = res.stderr;
                } else {
                    if ("ACCEPTED".equals(overallStatus)) overallStatus = "WRONG_ANSWER";
                }
            }

            results.add(CodingRunResponseDto.TestCaseResultDto.builder()
                    .testCaseNumber(i + 1)
                    .passed(ok)
                    .sample(tc.isSample())
                    .input(tc.isSample() ? (tc.getDisplayInput() != null ? tc.getDisplayInput() : tc.getInput()) : "Hidden")
                    .expectedOutput(tc.isSample() ? expected : "Hidden")
                    .actualOutput(tc.isSample() ? actual : (ok ? "Passed" : "Output mismatch"))
                    .error(isRuntimeErr ? res.stderr : null)
                    .build());
        }

        return CodingRunResponseDto.builder()
                .status(overallStatus)
                .passedCount(passed)
                .totalCount(testCases.size())
                .errorMessage(firstError)
                .results(results)
                .build();
    }

    private String normalizeOutput(String s) {
        return s.replace("\r\n", "\n").trim();
    }

    private String buildFullSource(CodingProblem problem, String lang, String studentCode) {
        switch (lang) {
            case "JAVA": {
                String sanitized = studentCode.replaceAll("public\\s+class\\s+Solution", "class Solution");
                return problem.getDriverCodeJava().replace("// {{SOLUTION}}", sanitized);
            }
            case "PYTHON":
                return problem.getDriverCodePython().replace("# {{SOLUTION}}", studentCode);
            case "CPP":
                return problem.getDriverCodeCpp().replace("// {{SOLUTION}}", studentCode);
            default:
                throw new IllegalArgumentException("Unsupported language: " + lang);
        }
    }

    private PistonResult callPiston(String lang, String source, String stdin) {
        int languageId;
        switch (lang) {
            case "JAVA":
                languageId = 91; // Java (JDK 17.0.6)
                break;
            case "PYTHON":
                languageId = 100; // Python (3.12.5)
                break;
            case "CPP":
                languageId = 105; // C++ (GCC 14.1.0)
                break;
            default:
                return PistonResult.apiError("Unsupported language: " + lang);
        }

        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("source_code", source);
            payload.put("language_id", languageId);
            payload.put("stdin", stdin != null ? stdin : "");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<String> resp = restTemplate.postForEntity(
                    JUDGE0_EXECUTE_URL, new HttpEntity<>(payload, headers), String.class);

            JsonNode root = objectMapper.readTree(resp.getBody());
            int statusId = root.path("status").path("id").asInt(0);

            if (statusId == 6) {
                String cErr = root.path("compile_output").asText("Compilation failed");
                return PistonResult.compileError(cErr);
            }

            PistonResult result = new PistonResult();
            result.stdout = root.path("stdout").isNull() ? "" : root.path("stdout").asText("");
            String errOut = root.path("stderr").isNull() ? "" : root.path("stderr").asText("");
            if (errOut.isBlank() && !root.path("message").isNull()) {
                errOut = root.path("message").asText("");
            }
            result.stderr = errOut;
            // Status 3 = Accepted (ran without runtime error/TLE); anything else > 4 is runtime/TLE error
            result.exitCode = (statusId == 3) ? 0 : 1;
            if (statusId == 5 && result.stderr.isBlank()) {
                result.stderr = "Time Limit Exceeded";
            }
            return result;
        } catch (Exception e) {
            return PistonResult.apiError("Code runner service unavailable: " + e.getMessage());
        }
    }

    private static class PistonResult {
        String stdout;
        String stderr;
        int exitCode;
        String compileError;
        String apiError;

        static PistonResult compileError(String err) {
            PistonResult r = new PistonResult();
            r.compileError = err;
            return r;
        }

        static PistonResult apiError(String err) {
            PistonResult r = new PistonResult();
            r.apiError = err;
            return r;
        }
    }
}

