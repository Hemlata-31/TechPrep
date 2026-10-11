package com.techprep.constant;

/**
 * Centralized, configurable thresholds and parameters for the
 * TechPrep Performance & Recommendation Engine (Phase 1).
 * All classifications (Strong, Weak, Hard, Needs Revision, Improving, Declining)
 * are derived deterministically using these thresholds without external AI dependencies.
 */
public final class PerformanceEngineConstants {

    private PerformanceEngineConstants() {
        // Prevent instantiation
    }

    // ==========================================
    // 1. STRONG TOPIC THRESHOLDS
    // ==========================================
    /** Minimum accuracy (%) for a topic to be classified as Strong */
    public static final double STRONG_ACCURACY_THRESHOLD = 75.0;

    /** Minimum question attempts required before classifying a topic as Strong */
    public static final int STRONG_MIN_ATTEMPTS = 3;

    // ==========================================
    // 2. WEAK TOPIC THRESHOLDS
    // ==========================================
    /** Accuracy ceiling (%) below which a topic is classified as Weak */
    public static final double WEAK_ACCURACY_THRESHOLD = 50.0;

    /** Minimum question attempts required before classifying a topic as Weak */
    public static final int WEAK_MIN_ATTEMPTS = 3;

    // ==========================================
    // 3. HARD TOPIC THRESHOLDS
    // ==========================================
    /** Minimum HARD difficulty questions attempted to evaluate hard struggle */
    public static final int HARD_MIN_HARD_ATTEMPTS = 2;

    /** Accuracy ceiling (%) on HARD difficulty questions indicating struggle */
    public static final double HARD_ACCURACY_THRESHOLD = 50.0;

    /** Composite hard-score ceiling [0-100] above which topic is considered Hard */
    public static final double HARD_SCORE_THRESHOLD = 35.0;

    /** Attempt volume threshold for persistent topic struggles */
    public static final int HARD_VOLUME_ATTEMPTS = 6;

    /** Accuracy threshold for persistent volume struggle */
    public static final double HARD_VOLUME_ACCURACY_THRESHOLD = 55.0;

    // ==========================================
    // 4. NEEDS REVISION THRESHOLDS
    // ==========================================
    /** Inactivity period in days after which a previously learned topic requires revision */
    public static final int REVISION_DAYS_THRESHOLD = 7;

    /** Minimum question attempts in the past to qualify for revision status */
    public static final int REVISION_MIN_ATTEMPTS = 3;

    /** Minimum historical accuracy (%) confirming student had once understood the topic */
    public static final double REVISION_MIN_ACCURACY = 60.0;

    // ==========================================
    // 5. TREND ANALYSIS THRESHOLDS (IMPROVING / DECLINING)
    // ==========================================
    /** Minimum chronological attempts required to perform trend evaluation */
    public static final int TREND_MIN_ATTEMPTS = 4;

    /** Positive percentage point delta (recent vs historical) to classify as Improving */
    public static final double TREND_IMPROVING_DELTA = 10.0;

    /** Negative percentage point delta (recent vs historical) to classify as Declining */
    public static final double TREND_DECLINING_DELTA = -10.0;

    // ==========================================
    // 6. SOLVING TIME CALCULATION
    // ==========================================
    /** Maximum valid duration per question in seconds to avoid idle timer anomalies */
    public static final long MAX_VALID_QUESTION_TIME_SECONDS = 300L; // 5 minutes max
}
