package com.techprep.config;

import com.techprep.entity.Difficulty;
import java.util.List;

public class VerbalAndCommTopUp {
    private static final String VRB = "Verbal Ability";
    private static final String VRB_SUB = "Grammar & Vocabulary";
    private static final String COM = "Communication";
    private static final String COM_SUB = "Communication Skills";

    public static void populate(List<QuestionTopUpSeeder.QSpec> list) {
        // Verbal Ability Topics (13 topics)
        addGrammar(list);
        addTenses(list);
        addArticles(list);
        addPrepositions(list);
        addSubjectVerbAgreement(list);
        addSynonymsAntonyms(list);
        addVocabulary(list);
        addErrorDetection(list);
        addSentenceCorrection(list);
        addFillInTheBlanks(list);
        addParaJumbles(list);
        addReadingComprehension(list);
        addSentenceCompletion(list);

        // Communication Topics (8 topics)
        addSpeakingSkills(list);
        addListeningSkills(list);
        addGroupDiscussion(list);
        addPresentationSkills(list);
        addPublicSpeaking(list);
        addInterviewCommunication(list);
        addBodyLanguage(list);
        addEmailWriting(list);
    }

    // ----------------- Verbal Ability -----------------

    private static void addGrammar(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Identify the part of speech of 'quickly' in: 'He ran quickly.'",
            "Adverb", "Adjective", "Verb", "Noun", "A",
            "'Quickly' modifies the verb 'ran', answering how he ran, making it an adverb.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Which sentence uses a gerund correctly?",
            "Swimming is a great exercise.", "I saw him swimming yesterday.", "She is swimming right now.", "To swim is fun.", "A",
            "'Swimming' in 'Swimming is a great exercise' acts as the subject noun of the sentence, which defines a gerund.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Choose the passive voice: 'The chef cooked a delicious meal.'",
            "A delicious meal was cooked by the chef.", "A delicious meal is cooked by the chef.", "A delicious meal had been cooked by the chef.", "A delicious meal was being cooked by the chef.", "A",
            "Past simple passive is formed by was/were + past participle (cooked).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "What type of pronoun is 'themselves' in: 'They prepared the meal themselves'?",
            "Emphatic/Intensive pronoun", "Relative pronoun", "Demonstrative pronoun", "Interrogative pronoun", "A",
            "Here 'themselves' adds emphasis to the subject 'They'.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Which conjunction is used to express contrast?",
            "Although", "Because", "Since", "Therefore", "A",
            "'Although' introduces a concession or contrasting thought.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Choose the correct indirect speech: He said, 'I am working hard.'",
            "He said that he was working hard.", "He said that he is working hard.", "He told that he worked hard.", "He said he had been working hard.", "A",
            "Present continuous 'am working' changes to past continuous 'was working'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Identify the adjective clause in: 'The car that I bought last week broke down.'",
            "that I bought last week", "The car broke down", "last week broke down", "The car", "A",
            "'that I bought last week' modifies the noun 'car'.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Which of the following is a collective noun?",
            "Flock", "Bird", "Feather", "Flying", "A",
            "'Flock' denotes a group of birds, making it a collective noun.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Identify the mood of the verb in: 'If I were you, I would accept the offer.'",
            "Subjunctive mood", "Indicative mood", "Imperative mood", "Conditional mood", "A",
            "'If I were' expresses a hypothetical or counter-to-fact situation, which is subjunctive mood.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "What is an intransitive verb?",
            "A verb that does not take a direct object", "A verb that requires an object", "A linking verb", "A modal verb", "A",
            "Intransitive verbs (e.g. sleep, arrive) do not transfer action to a direct object.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Identify the error: 'Between you and I, this decision is unfair.'",
            "'I' should be 'me'", "'unfair' should be 'unfairly'", "'Between' should be 'Among'", "No error", "A",
            "Prepositions govern objective case pronouns: 'Between you and me'.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Grammar",
            "Choose the correct comparative form: 'Of the two options, this is the ______.'",
            "better", "best", "more good", "gooder", "A",
            "When comparing two items, the comparative degree 'better' must be used.", Difficulty.EASY, 2));
    }

    private static void addTenses(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "I ______ in London for five years before I moved to Paris.",
            "had lived", "have lived", "lived", "was living", "A",
            "An action completed before another past event requires Past Perfect ('had lived').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "She ______ English since 2015.",
            "has been teaching", "is teaching", "taught", "was teaching", "A",
            "An action beginning in the past and continuing up to the present with 'since' requires Present Perfect Continuous.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "By next month, we ______ this project.",
            "will have finished", "will finish", "finish", "are finishing", "A",
            "'By next month' indicates an action that will be completed by a future deadline, requiring Future Perfect.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "When the phone rang, I ______ dinner.",
            "was having", "had", "have had", "am having", "A",
            "An ongoing background action interrupted by a past event is expressed in Past Continuous.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "The sun ______ in the east.",
            "rises", "is rising", "rose", "has risen", "A",
            "Universal truths and scientific facts take Simple Present tense.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "If it rains tomorrow, we ______ the picnic.",
            "will cancel", "would cancel", "cancelled", "had cancelled", "A",
            "First conditional structure: If + present simple, will + base verb.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "He ______ just ______ the office.",
            "has, left", "did, leave", "had, left", "is, leaving", "A",
            "'Just' with recently completed actions takes Present Perfect ('has just left').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "I wish I ______ the answer to this question.",
            "knew", "know", "had known", "will know", "A",
            "Present unfulfilled wishes take Past Simple subjunctive ('knew').", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "If I ______ harder, I would have passed the exam.",
            "had studied", "studied", "would study", "have studied", "A",
            "Third conditional: If + past perfect, would have + past participle.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "The train ______ before we reached the station.",
            "had departed", "departed", "was departing", "has departed", "A",
            "The earlier of two past actions takes Past Perfect ('had departed').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "Look! The child ______ down the stairs.",
            "is falling", "falls", "fell", "has fallen", "A",
            "Actions happening at the exact moment of speaking ('Look!') take Present Continuous.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Tenses",
            "We ______ each other for ten years.",
            "have known", "are knowing", "know", "were knowing", "A",
            "'Know' is a stative verb (not used in continuous form) so Present Perfect ('have known') is correct.", Difficulty.MEDIUM, 3));
    }

    private static void addArticles(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "He is ______ honest man.",
            "an", "a", "the", "no article", "A",
            "'Honest' starts with a vowel sound (silent 'h'), so 'an' is required.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "Copper is ______ useful metal.",
            "a", "an", "the", "no article", "A",
            "'Useful' begins with a consonant sound ('yu'), so article 'a' is used.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "______ Himalayas are located to the north of India.",
            "The", "A", "An", "No article", "A",
            "Definite article 'The' is used before names of mountain ranges.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "He studies at ______ university in New York.",
            "a", "an", "the", "no article", "A",
            "'University' begins with consonant sound ('yu'), so 'a' is used.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "Man is ______ mortal being.",
            "a", "an", "the", "no article", "A",
            "'A mortal being' uses the indefinite article 'a' for singular countable noun phrase.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "He is ______ European citizen.",
            "a", "an", "the", "no article", "A",
            "'European' begins with consonant sound ('yu'), requiring 'a'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "______ higher you climb, ______ cooler it gets.",
            "The, the", "A, a", "The, a", "A, the", "A",
            "Parallel progression using comparatives takes 'The... the...'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "She returned after ______ hour.",
            "an", "a", "the", "no article", "A",
            "'Hour' begins with a vowel sound (silent 'h'), requiring 'an'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "I enjoy playing ______ guitar in my free time.",
            "the", "a", "an", "no article", "A",
            "Musical instruments take the definite article 'the' when referring to playing them.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "______ gold is a precious metal.",
            "No article", "The", "A", "An", "A",
            "Material and abstract nouns used in a general sense take no article.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "He was elected ______ chairman of the board.",
            "No article", "the", "a", "an", "A",
            "No article is used before nouns denoting a unique position/office held by one person at a time after verbs like elect/appoint.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Articles",
            "This is ______ best book I have ever read.",
            "the", "a", "an", "no article", "A",
            "Superlative adjectives always take the definite article 'the'.", Difficulty.EASY, 2));
    }

    private static void addPrepositions(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "He has been suffering ______ fever since Monday.",
            "from", "with", "of", "by", "A",
            "The standard preposition after 'suffer' for illnesses is 'from'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "She is good ______ mathematics.",
            "at", "in", "with", "for", "A",
            "The phrase 'good at' is used to denote competence in a subject or skill.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "The cat jumped ______ the table.",
            "onto", "into", "in", "at", "A",
            "'Onto' indicates movement towards and resting on a surface.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "Distribute these sweets ______ the children.",
            "among", "between", "to", "with", "A",
            "'Among' is used for distribution among more than two persons.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "He died ______ malaria.",
            "of", "from", "by", "with", "A",
            "One dies 'of' a disease, and 'from' a cause such as overwork or thirst.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "The meeting will begin ______ 10:00 AM.",
            "at", "on", "in", "by", "A",
            "Specific clock times take the preposition 'at'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "We walked ______ the beach at sunset.",
            "along", "across", "through", "over", "A",
            "'Along' indicates moving in a line following the length of the beach.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "He is senior ______ me in the office.",
            "to", "than", "from", "of", "A",
            "Adjectives ending in -ior (senior, junior, superior, inferior) take 'to', not 'than'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "He was prevented ______ entering the hall.",
            "from", "to", "for", "against", "A",
            "'Prevent' takes the preposition 'from' followed by a gerund.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "I agree ______ your proposal.",
            "to", "with", "on", "about", "A",
            "One agrees 'with' a person, but agrees 'to' a plan, proposal, or condition.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "The dog jumped ______ the river.",
            "into", "in", "to", "onto", "A",
            "'Into' indicates motion towards the inside of something.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Prepositions",
            "He is blind ______ his son's faults.",
            "to", "in", "with", "of", "A",
            "'Blind to' faults means unwilling or unable to perceive them.", Difficulty.HARD, 4));
    }

    private static void addSubjectVerbAgreement(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "Neither the teacher nor the students ______ present in the lab.",
            "were", "was", "is", "has been", "A",
            "In 'neither... nor...', the verb agrees with the closer subject ('students' -> plural 'were').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "The quality of these mangoes ______ not good.",
            "is", "are", "were", "have been", "A",
            "The subject is 'The quality' (singular), not 'mangoes', so the verb must be 'is'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "Ten kilometers ______ a long distance to walk.",
            "is", "are", "were", "have been", "A",
            "Units of measurement, distance, or time thought of as a single quantity take a singular verb.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "The committee ______ divided in their opinions.",
            "were", "was", "is", "has been", "A",
            "When members of a collective noun act individually or disagree, a plural verb ('were') is used.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "Bread and butter ______ his favorite breakfast.",
            "is", "are", "were", "have been", "A",
            "When two nouns express a single combined idea/dish, they take a singular verb.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "Either you or he ______ mistaken.",
            "is", "are", "were", "have been", "A",
            "In 'either... or...', the verb agrees with the subject closest to it ('he' -> 'is').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "A large number of students ______ passed the test.",
            "have", "has", "is", "was", "A",
            "'A number of' takes a plural verb ('have'), whereas 'The number of' takes a singular verb.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "The Prime Minister, along with his cabinet ministers, ______ arrived.",
            "has", "have", "are", "were", "A",
            "Phrases introduced by 'along with', 'as well as', 'together with' do not change the number of the subject ('Prime Minister' -> 'has').", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "Each of the participants ______ given a certificate.",
            "was", "were", "are", "have been", "A",
            "'Each of' always takes a singular verb ('was').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "The news ______ too good to be true.",
            "is", "are", "were", "have been", "A",
            "'News' is an uncountable noun that takes a singular verb ('is').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "Many a man ______ tempted by gold.",
            "has been", "have been", "are", "were", "A",
            "'Many a' followed by a singular noun takes a singular verb ('has been').", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Subject-Verb Agreement",
            "One of the boys who ______ playing football is my brother.",
            "are", "is", "was", "has been", "A",
            "The relative pronoun 'who' refers to the antecedent 'boys' (plural), so 'are' is correct.", Difficulty.HARD, 4));
    }

    private static void addSynonymsAntonyms(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the synonym of 'CANDID'?",
            "Frank", "Dishonest", "Shy", "Arrogant", "A",
            "'Candid' means truthful, straightforward, and frank.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the antonym of 'OBSOLETE'?",
            "Contemporary", "Outdated", "Ancient", "Extinct", "A",
            "'Obsolete' means no longer produced or used (out of date); its antonym is contemporary / modern.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the synonym of 'METICULOUS'?",
            "Careful", "Careless", "Fast", "Lazy", "A",
            "'Meticulous' means showing great attention to detail; very careful and precise.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the antonym of 'FRUGAL'?",
            "Extravagant", "Thrifty", "Economical", "Stingy", "A",
            "'Frugal' means sparing or economical with money; opposite is extravagant / wasteful.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the synonym of 'LUCID'?",
            "Clear", "Confusing", "Dark", "Murky", "A",
            "'Lucid' means expressed clearly and easy to understand.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the antonym of 'NADIR'?",
            "Zenith", "Bottom", "Depth", "Base", "A",
            "'Nadir' is the lowest point; 'Zenith' is the highest peak/point.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the synonym of 'PRAGMATIC'?",
            "Practical", "Idealistic", "Theoretical", "Emotional", "A",
            "'Pragmatic' means dealing with things sensibly and realistically based on practical considerations.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the antonym of 'EPHEMERAL'?",
            "Permanent", "Fleeting", "Transient", "Short-lived", "A",
            "'Ephemeral' means lasting for a very short time; opposite is permanent / everlasting.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the synonym of 'ABUNDANT'?",
            "Plentiful", "Scarce", "Meager", "Few", "A",
            "'Abundant' means existing or available in large quantities; plentiful.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the antonym of 'LOQUACIOUS'?",
            "Reticent", "Talkative", "Chatty", "Garrulous", "A",
            "'Loquacious' means tending to talk a great deal; its antonym is reticent / reserved.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the synonym of 'INEVITABLE'?",
            "Unavoidable", "Unlikely", "Doubtful", "Preventable", "A",
            "'Inevitable' means certain to happen; unavoidable.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Synonyms & Antonyms",
            "What is the antonym of 'BENEVOLENT'?",
            "Malevolent", "Generous", "Kind", "Helpful", "A",
            "'Benevolent' means well-meaning and kindly; 'Malevolent' means having or showing a wish to do evil.", Difficulty.MEDIUM, 3));
    }

    private static void addVocabulary(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "What is the meaning of 'ubiquitous'?",
            "Present everywhere", "Very rare", "Harmful", "Ancient", "A",
            "'Ubiquitous' means present, appearing, or found everywhere.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "One who looks on the bright side of things is called an:",
            "Optimist", "Pessimist", "Altruist", "Egoist", "A",
            "An optimist always looks on the favorable side of events.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "A person who knows everything is known as:",
            "Omniscient", "Omnipotent", "Omnipresent", "Polyglot", "A",
            "'Omniscient' means all-knowing; 'omnipotent' is all-powerful; 'omnipresent' is present everywhere.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "What does the idiom 'bite the bullet' mean?",
            "Face a painful situation with courage", "Avoid a difficult task", "Get injured in battle", "Eat quickly", "A",
            "'Bite the bullet' means to endure a painful or undesirable situation with fortitude.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "What is the meaning of 'gregarious'?",
            "Fond of company; sociable", "Introverted and quiet", "Aggressive", "Deceptive", "A",
            "'Gregarious' describes a person fond of company and enjoying social interaction.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "A government by the wealthy is called a:",
            "Plutocracy", "Autocracy", "Democracy", "Oligarchy", "A",
            "'Plutocracy' is a society ruled or governed by the small minority of the wealthiest citizens.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "What does the idiom 'burn the midnight oil' mean?",
            "Work late into the night", "Waste resources", "Cause a fire", "Complain excessively", "A",
            "'Burn the midnight oil' means to study or work late into the night.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "One who speaks many languages is called a:",
            "Polyglot", "Linguist", "Grammarian", "Bilingual", "A",
            "A 'polyglot' is a person who knows and is able to speak several languages.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "What does 'cacophony' mean?",
            "A harsh, discordant mixture of sounds", "A sweet melody", "Complete silence", "An echo", "A",
            "'Cacophony' refers to a harsh and jarring sound.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "A post without salary is called an:",
            "Honorary post", "Involuntary post", "Ad-hoc post", "Permanent post", "A",
            "An 'honorary' position is given as an honor without normal payment/salary.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "What does the phrase 'Achilles' heel' mean?",
            "A small but fatal weakness", "A great strength", "A fast runner", "A mythical victory", "A",
            "'Achilles' heel' refers to a vulnerability in an otherwise strong situation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Vocabulary",
            "The fear of enclosed spaces is called:",
            "Claustrophobia", "Acrophobia", "Hydrophobia", "Agoraphobia", "A",
            "Claustrophobia is extreme fear of confined or enclosed spaces.", Difficulty.EASY, 2));
    }

    private static void addErrorDetection(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'No sooner did he see the police (A) / when he took (B) / to his heels (C) / No error (D)'",
            "B ('when' should be 'than')", "A", "C", "D", "A",
            "'No sooner' is always paired with 'than', never 'when'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'Hardly had I arrived (A) / than it started (B) / to rain heavily (C) / No error (D)'",
            "B ('than' should be 'when')", "A", "C", "D", "A",
            "'Hardly' and 'Scarcely' are paired with 'when', not 'than'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'He is one of those men (A) / who does not (B) / believe in superstitions (C) / No error (D)'",
            "B ('does' should be 'do')", "A", "C", "D", "A",
            "'who' refers to plural antecedent 'men', so the verb must be plural 'do not'.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'He told to me (A) / that he was (B) / feeling sick (C) / No error (D)'",
            "A ('told to me' should be 'told me')", "B", "C", "D", "A",
            "'Tell' is a transitive verb that takes an indirect object directly without 'to' ('told me').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'Although he worked hard (A) / but he failed (B) / in the exam (C) / No error (D)'",
            "B (remove 'but')", "A", "C", "D", "A",
            "'Although' already expresses contrast; using 'but' with it is a redundant double conjunction.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'Unless you do not apologize (A) / you will not be (B) / permitted to enter (C) / No error (D)'",
            "A ('Unless you do not' should be 'Unless you')", "B", "C", "D", "A",
            "'Unless' has a negative meaning itself; using 'do not' creates an erroneous double negative.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'The sceneries of Kashmir (A) / are more beautiful (B) / than Himachal (C) / No error (D)'",
            "A ('sceneries' should be 'scenery')", "B", "C", "D", "A",
            "'Scenery' is an uncountable noun and has no plural form 'sceneries'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'He has disposed off (A) / all his old (B) / office furniture (C) / No error (D)'",
            "A ('disposed off' should be 'disposed of')", "B", "C", "D", "A",
            "The correct phrasal verb meaning to get rid of is 'dispose of' with a single 'f'.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'I prefer coffee (A) / than tea (B) / in the morning (C) / No error (D)'",
            "B ('than' should be 'to')", "A", "C", "D", "A",
            "'Prefer' takes the preposition 'to', not 'than'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'Neither of the five candidates (A) / was found suitable (B) / for the job (C) / No error (D)'",
            "A ('Neither' should be 'None')", "B", "C", "D", "A",
            "'Neither' is used only for two entities. For more than two, 'None' is used.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'Suppose if it rains (A) / what will (B) / we do? (C) / No error (D)'",
            "A (use either 'Suppose' or 'if', not both)", "B", "C", "D", "A",
            "'Suppose' and 'if' are synonymous conditions; using both together is redundant.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Error Detection",
            "Find the error: 'He gave me (A) / a two hundred rupees note (B) / yesterday (C) / No error (D)'",
            "B ('rupees' should be 'rupee')", "A", "C", "D", "A",
            "In compound adjective phrases ('a two-hundred-rupee note'), the noun is kept singular.", Difficulty.MEDIUM, 3));
    }

    private static void addSentenceCorrection(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'He is working in this company since 2018.'",
            "He has been working in this company since 2018.", "He works in this company since 2018.", "He was working in this company since 2018.", "No improvement needed", "A",
            "Actions continuing from the past with 'since' require Present Perfect Continuous ('has been working').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'If I was rich, I would travel the world.'",
            "If I were rich, I would travel the world.", "If I am rich, I will travel the world.", "If I had been rich, I would travel.", "No improvement needed", "A",
            "Counterfactual condition requires subjunctive mood 'were'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'She is more cleverer than her sister.'",
            "She is cleverer than her sister.", "She is more clever than her sister.", "She is clever than her sister.", "No improvement", "A",
            "Double comparatives ('more cleverer') are grammatically incorrect. Use 'cleverer'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'The climate of Chennai is hotter than Delhi.'",
            "The climate of Chennai is hotter than that of Delhi.", "The climate of Chennai is hotter than Delhi's climate.", "Chennai's climate is hotter than Delhi.", "No improvement", "A",
            "Comparison must be between climates, not climate and a city. Use 'that of Delhi'.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'One must obey his parents.'",
            "One must obey one's parents.", "One must obey their parents.", "One must obey his or her parents.", "No improvement", "A",
            "The indefinite pronoun 'one' takes the possessive form 'one's'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'Being a rainy day, I decided to stay home.'",
            "It being a rainy day, I decided to stay home.", "As a rainy day, I stayed home.", "Being rainy, I stayed home.", "No improvement", "A",
            "Dangling participle: 'Being a rainy day' implies 'I' was a rainy day. Add impersonal subject 'It being a rainy day'.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'I look forward to meet you soon.'",
            "I look forward to meeting you soon.", "I look forward to met you soon.", "I look forward meeting you soon.", "No improvement", "A",
            "'Look forward to' takes a gerund (-ing) because 'to' is a preposition here.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'He did not knew the answer.'",
            "He did not know the answer.", "He does not knew the answer.", "He has not knew the answer.", "No improvement", "A",
            "Auxiliary 'did' is always followed by the base form of the verb ('know').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'He congratulated me for my success.'",
            "He congratulated me on my success.", "He congratulated me in my success.", "He congratulated me at my success.", "No improvement", "A",
            "The correct preposition with congratulate is 'on' ('congratulate on').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'She insisted to pay the bill.'",
            "She insisted on paying the bill.", "She insisted to paying the bill.", "She insisted for paying the bill.", "No improvement", "A",
            "'Insist' takes the preposition 'on' followed by a gerund.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'He is junior than me in college.'",
            "He is junior to me in college.", "He is junior from me in college.", "He is more junior to me.", "No improvement", "A",
            "Latin comparatives ending in -ior take 'to', not 'than'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Correction",
            "Correct: 'Every boy and girl have completed the task.'",
            "Every boy and girl has completed the task.", "Every boy and girl had completed.", "Every boys and girls have completed.", "No improvement", "A",
            "'Every' distributes individually, making the compound subject singular ('has completed').", Difficulty.MEDIUM, 3));
    }

    private static void addFillInTheBlanks(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "The manager was satisfied ______ his performance.",
            "with", "by", "at", "for", "A",
            "'Satisfied with' is the standard prepositional phrase.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "He was accused ______ theft.",
            "of", "with", "for", "by", "A",
            "'Accused of' is the correct collocation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "The plane took ______ on time.",
            "off", "up", "away", "in", "A",
            "'Take off' means to leave the ground and begin flight.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "I could not ______ what he was saying due to the noise.",
            "make out", "make up", "make for", "make in", "A",
            "'Make out' means to decipher, discern, or comprehend.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "He is addicted ______ gambling.",
            "to", "with", "at", "for", "A",
            "'Addicted to' is the fixed preposition.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "The fire was ______ by the firefighters in two hours.",
            "put out", "put on", "put away", "put up", "A",
            "'Put out' means to extinguish a fire.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "She has a great passion ______ music.",
            "for", "of", "to", "with", "A",
            "One has a 'passion for' an art or field.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "The car broke ______ on the highway.",
            "down", "up", "off", "out", "A",
            "'Break down' means to cease functioning (for a vehicle/machine).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "You must adhere ______ the rules.",
            "to", "with", "on", "in", "A",
            "'Adhere to' means to abide by or follow strictly.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "He is capable ______ handling complex software systems.",
            "of", "for", "to", "in", "A",
            "'Capable of' takes a gerund.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "The company will call ______ an emergency meeting.",
            "for", "at", "in", "with", "A",
            "'Call for' means to demand or require.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Fill in the Blanks",
            "He tried to cope ______ the heavy workload.",
            "with", "up with", "to", "for", "A",
            "The correct idiom is 'cope with', never 'cope up with'.", Difficulty.HARD, 4));
    }

    private static void addParaJumbles(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: in the digital era, Q: technology plays R: a crucial role S: in education.",
            "Q-R-S-P", "P-S-Q-R", "R-S-P-Q", "S-P-Q-R", "A",
            "'Technology plays (Q) a crucial role (R) in education (S) in the digital era (P)' forms a logical, coherent sentence.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: for the exam Q: he studied diligently R: to ensure S: a high score.",
            "Q-P-R-S", "P-R-S-Q", "R-S-P-Q", "S-Q-P-R", "A",
            "'He studied diligently for the exam to ensure a high score' is Q-P-R-S.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: renewable energy Q: is essential R: combating climate change S: for the purpose of.",
            "P-Q-S-R", "Q-P-R-S", "R-S-P-Q", "S-R-P-Q", "A",
            "'Renewable energy is essential for the purpose of combating climate change' gives P-Q-S-R.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: without fear of failure Q: to achieve greatness R: one must take risks S: and pursue dreams.",
            "Q-R-S-P", "P-Q-R-S", "R-P-Q-S", "S-R-Q-P", "A",
            "'To achieve greatness (Q) one must take risks (R) and pursue dreams (S) without fear of failure (P)'.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: which has transformed communication Q: the invention of the internet R: is a revolutionary milestone S: across the globe.",
            "Q-R-P-S", "P-Q-R-S", "R-S-P-Q", "S-P-Q-R", "A",
            "'The invention of the internet (Q) is a revolutionary milestone (R) which has transformed communication (P) across the globe (S)'.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: by continuous practice Q: mastering a skill R: and dedication S: is made possible.",
            "Q-S-P-R", "P-R-Q-S", "R-S-P-Q", "S-Q-P-R", "A",
            "'Mastering a skill (Q) is made possible (S) by continuous practice (P) and dedication (R)'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: had been waiting Q: the passengers R: for three hours S: when the train arrived.",
            "Q-P-R-S", "P-R-S-Q", "R-S-P-Q", "S-P-Q-R", "A",
            "'The passengers (Q) had been waiting (P) for three hours (R) when the train arrived (S)'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: in order to promote Q: sustainable development R: government policies S: should focus on green technology.",
            "P-Q-R-S", "R-S-P-Q", "Q-P-R-S", "S-R-P-Q", "A",
            "'In order to promote sustainable development, government policies should focus on green technology'. P-Q-R-S.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: but also improves Q: physical health R: regular exercise S: not only relieves stress.",
            "R-S-P-Q", "P-Q-R-S", "S-R-P-Q", "Q-P-R-S", "A",
            "'Regular exercise (R) not only relieves stress (S) but also improves (P) physical health (Q)'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: despite facing Q: numerous obstacles R: they completed S: the expedition.",
            "P-Q-R-S", "R-S-P-Q", "Q-R-S-P", "S-P-Q-R", "A",
            "'Despite facing numerous obstacles, they completed the expedition' -> P-Q-R-S.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: requires empathy Q: effective leadership R: and clear communication S: among team members.",
            "Q-P-R-S", "P-Q-R-S", "R-S-P-Q", "S-P-Q-R", "A",
            "'Effective leadership (Q) requires empathy (P) and clear communication (R) among team members (S)'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Para Jumbles",
            "Arrange: P: a solution was found Q: after several hours R: of rigorous brainstorming S: to the problem.",
            "Q-R-P-S", "P-S-Q-R", "R-S-P-Q", "S-P-Q-R", "A",
            "'After several hours (Q) of rigorous brainstorming (R) a solution was found (P) to the problem (S)'.", Difficulty.HARD, 4));
    }

    private static void addReadingComprehension(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "What is the primary purpose of an author's topic sentence in a paragraph?",
            "To express the main idea of the paragraph", "To provide supporting details", "To introduce a counter-argument", "To conclude the essay", "A",
            "A topic sentence encapsulates and presents the central thought of that paragraph.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "What does 'reading between the lines' mean in comprehension?",
            "Inferring implied meanings not directly stated", "Reading rapidly", "Skipping complex sentences", "Memorizing facts", "A",
            "It means deriving an author's underlying or implicit message from contextual clues.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "Which technique involves quickly scanning a passage to locate specific keywords?",
            "Scanning", "Skimming", "Critical reading", "Intensive reading", "A",
            "Scanning is reading rapidly in search of specific terms, numbers, or facts.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "What is the technique of reading quickly to get the general gist of a passage?",
            "Skimming", "Scanning", "Proofreading", "Summarizing", "A",
            "Skimming is glancing through text rapidly to grasp the general theme or outline.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "When an author writes without personal bias or emotional adjectives, the tone is:",
            "Objective", "Subjective", "Sarcastic", "Pessimistic", "A",
            "An objective tone relies on neutral facts, evidence, and balanced reporting.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "If an author mocks foolish behavior by using irony and exaggeration, the style is:",
            "Satirical", "Didactic", "Nostalgic", "Eulogistic", "A",
            "Satire uses irony, humor, and exaggeration to expose folly or flaws.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "What does an author do when establishing a cause-and-effect relationship?",
            "Explains why something happened and its consequences", "Compares two similar objects", "Tells an engaging personal story", "Defines technical jargon", "A",
            "Cause-and-effect structures identify catalysts and their downstream results.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "A tone intended primarily to teach or preach moral lessons is termed:",
            "Didactic", "Pedantic", "Sardonic", "Nostalgic", "A",
            "'Didactic' works and tones are expressly meant for instruction or moral teaching.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "In reading passages, what is an inference?",
            "A conclusion reached based on evidence and reasoning", "A direct fact stated in the first paragraph", "A dictionary definition", "The title of the passage", "A",
            "An inference is a logical deduction derived from textual evidence.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "Which question is typically asked to evaluate the author's primary attitude?",
            "What is the tone of the passage?", "What is the word count of the text?", "Who printed the book?", "How many characters are introduced?", "A",
            "Passage tone questions evaluate whether the author is optimistic, cynical, critical, neutral, etc.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "What does a transitional word like 'Furthermore' indicate?",
            "Addition of a supporting point", "Contrast with the preceding idea", "A concluding summary", "A chronological timeline", "A",
            "'Furthermore' adds additional reinforcing information to an established point.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Reading Comprehension",
            "When an author uses phrases like 'on the other hand' and 'conversely', the pattern is:",
            "Compare and contrast", "Chronological order", "Problem and solution", "Spatial order", "A",
            "These transition words signal comparative or contrasting arguments.", Difficulty.EASY, 2));
    }

    private static void addSentenceCompletion(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "Although the economic climate was challenging, the startup ______ to expand its market share.",
            "managed", "failed", "refused", "hesitated", "A",
            "'Although' sets up a contrast with the challenging climate, making 'managed to expand' logical.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "Because of his ______ dedication to quality, his products were renowned worldwide.",
            "unwavering", "sporadic", "hesitant", "negligent", "A",
            "'Renowned worldwide' requires a positive modifier indicating steadfast devotion ('unwavering').", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "The scientist's theory was initially dismissed as absurd, but later experiments ______ its validity.",
            "corroborated", "disproved", "weakened", "obscured", "A",
            "'But' implies contrast from initial dismissal to subsequent verification ('corroborated').", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "The team worked with exceptional ______ to finish the urgent assignment ahead of schedule.",
            "alacrity", "lethargy", "reluctance", "indifference", "A",
            "'Alacrity' means brisk and cheerful readiness or speed.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "Despite her ______ exterior, she possessed a remarkably kind and generous heart.",
            "stern", "gentle", "friendly", "cheerful", "A",
            "'Despite' indicates contrast with a kind heart, so 'stern' is the appropriate opposite.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "His speech was so ______ that even listeners unfamiliar with the subject understood completely.",
            "lucid", "convoluted", "abstruse", "verbose", "A",
            "'Lucid' means clear and easily intelligible.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "The company's profits increased ______ over the past decade, defying market expectations.",
            "exponentially", "marginally", "scarcely", "negligibly", "A",
            "'Defying expectations' of positive increase aligns with 'exponentially'.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "She made a ______ decision without consulting any team members, causing widespread friction.",
            "unilateral", "collaborative", "unanimous", "deliberate", "A",
            "'Unilateral' denotes an action undertaken without the concurrence of other parties.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "The diplomat remained ______ even when confronted with aggressive questions.",
            "composed", "agitated", "flustered", "hysterical", "A",
            "'Composed' means calm and self-possessed under pressure.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "In order to avoid confusion, the instructions were presented with utmost ______.",
            "precision", "ambiguity", "brevity", "secrecy", "A",
            "'Utmost precision' ensures clarity and avoids misunderstandings.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "His arguments were so ______ that the entire jury was convinced of his innocence.",
            "persuasive", "feeble", "dubious", "hasty", "A",
            "Arguments that successfully convince a jury are 'persuasive' or compelling.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(VRB, VRB_SUB, "Sentence Completion",
            "The new policy aims to ______ poverty by creating vocational training programs.",
            "alleviate", "aggravate", "prolong", "intensify", "A",
            "'Alleviate' means to make suffering or poverty less severe.", Difficulty.EASY, 2));
    }

    // ----------------- Communication Skills -----------------

    private static void addSpeakingSkills(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "What is the recommended speaking rate for professional presentations?",
            "120 to 150 words per minute", "250 to 300 words per minute", "60 words per minute", "Over 200 words per minute", "A",
            "A natural, conversational professional pace ranges comfortably between 120 and 150 wpm.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "How does intentional pausing enhance speech delivery?",
            "It gives the audience time to absorb key points and allows the speaker to breathe", "It signals that the speaker forgot what to say", "It wastes audience time", "It disrupts rhythm", "A",
            "Strategic pauses highlight key information and convey confidence.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "What is vocal variety in public speaking?",
            "Varying pitch, volume, pace, and tone to maintain audience engagement", "Speaking at the exact same frequency throughout", "Using complex accents", "Shouting key words", "A",
            "Modulating pitch, volume, and pace avoids monotone delivery and keeps listeners invested.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "What are filler words like 'um', 'uh', and 'you know' indicative of?",
            "Gaps in thought where the speaker fears silence", "Advanced vocabulary", "Confidence", "Strategic pausing", "A",
            "Fillers often reflect apprehension about pausing while formulating thoughts.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "Articulation in speaking refers to:",
            "The clarity with which individual speech sounds and words are formed", "The volume of the voice", "The length of sentences", "The speed of speaking", "A",
            "Articulation is crisp and precise formation of consonants and vowels.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "What is voice projection?",
            "Directing voice powerfully so it can be heard clearly across a room without straining", "Shouting loudly", "Using a megaphone", "Singing notes", "A",
            "Projection uses diaphragmatic breathing to project sound effortlessly.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "How should a speaker address an audience with diverse background knowledge?",
            "Avoid overly technical jargon and define acronyms clearly", "Use deepest technical terms to sound smart", "Assume everyone knows everything", "Speak as quickly as possible", "A",
            "Clear communication adapts language to ensure universal accessibility.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "Which breathing technique is best for sustaining speech control and calming nerves?",
            "Diaphragmatic (deep belly) breathing", "Shallow chest breathing", "Rapid hyperventilation", "Holding breath periodically", "A",
            "Diaphragmatic breathing relaxes the vocal chords and stabilizes voice pitch.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "What does 'intonation' refer to?",
            "The rise and fall of voice pitch while speaking", "The volume level", "Word spelling", "Grammatical correctness", "A",
            "Intonation indicates questions, statements, enthusiasm, and attitude through pitch contours.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "What is an elevator pitch?",
            "A concise summary of an idea or background delivered in 30 to 60 seconds", "A speech given in an elevator only", "A 1-hour keynote", "A written whitepaper", "A",
            "An elevator pitch succinctly outlines value propositions in the time of an elevator ride.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "Which trait demonstrates assertiveness in communication without aggression?",
            "Expressing needs and viewpoints clearly, calmly, and respectfully", "Interrupting others loudly", "Agreeing to everything silently", "Avoiding eye contact", "A",
            "Assertiveness balances self-respect with respect for others.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Speaking Skills",
            "When responding to a difficult question during a talk, a speaker should:",
            "Acknowledge the question, pause briefly to frame the answer, and reply concisely", "Deflect angrily", "Pretend not to hear", "Make up fictional data", "A",
            "Professional composure involves acknowledging the query, pausing, and answering objectively.", Difficulty.EASY, 2));
    }

    private static void addListeningSkills(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What distinguishes active listening from passive hearing?",
            "Active listening involves conscious attention, comprehension, and reflective response", "Active listening requires interrupting frequently", "Passive hearing is always better", "They are identical", "A",
            "Active listening is an intentional, focused cognitive process aimed at understanding the speaker completely.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "Which non-verbal cue signals attentive listening?",
            "Nodding periodically and maintaining comfortable eye contact", "Checking notifications on a smartphone", "Slouching and looking out the window", "Tapping fingers impatiently", "A",
            "Nodding, open posture, and consistent eye contact demonstrate engagement.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What is paraphrasing in the context of active listening?",
            "Restating the speaker's core message in your own words to verify understanding", "Repeating word-for-word exactly", "Changing the subject", "Interrupting with counter-arguments", "A",
            "Paraphrasing confirms mutual comprehension before moving forward.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What is empathetic listening primarily focused on?",
            "Understanding the speaker's emotions and perspective without immediate judgment", "Fixing the problem instantly", "Critiquing the grammar of the speaker", "Documenting errors", "A",
            "Empathetic listening connects with the feelings and viewpoints underlying words.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What is a common cognitive barrier to effective listening?",
            "Formulating one's rebuttal while the other person is still speaking", "Taking notes", "Asking clarifying questions", "Maintaining eye contact", "A",
            "Rehearsing replies preempts cognitive bandwidth needed to fully comprehend what is being said.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What is critical or evaluative listening used for?",
            "Assessing the logic, evidence, and validity of an argument", "Memorizing poetic phrases", "Enjoying music", "Emotional comforting", "A",
            "Critical listening evaluates arguments, distinguishes facts from opinions, and detects bias.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "How should a listener handle emotional triggers or biased remarks during a discussion?",
            "Remain calm, recognize the bias, and focus on the substantive facts being conveyed", "Shout immediately", "Walk out abruptly", "Argue emotionally", "A",
            "Professional listeners suspend reactive impulses to process information objectively.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What type of questions encourage a speaker to elaborate deeply?",
            "Open-ended questions (e.g. 'Can you tell me more about...?')", "Closed yes/no questions", "Leading questions with baked-in answers", "Rhetorical questions", "A",
            "Open-ended questions invite detailed explanations and exploration.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What is the 70/30 rule of listening in client interviews and consulting?",
            "Spend 70% of time listening and 30% speaking", "Speak 70% of time to dominate", "Listen 30% and write 70%", "Divide time equally between 7 people", "A",
            "High-impact communicators dedicate the majority of time to actively listening to requirements.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What is selective listening?",
            "Hearing only what conforms to one's preexisting beliefs and ignoring the rest", "Listening to select podcasts", "Listening through headphones", "Recording audio", "A",
            "Selective listening filters out uncomfortable or contradictory information.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "Why is taking brief notes beneficial during long technical briefings?",
            "It aids retention and frees working memory while signaling attentiveness", "It allows doodling unnoticed", "It substitutes for eye contact completely", "It is legally mandatory", "A",
            "Note-taking reinforces memory encoding and provides concrete reference points.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Listening Skills",
            "What should a listener do if they lose track of the speaker's line of thought?",
            "Politely ask a clarifying question at a natural break", "Nod blindly and guess later", "Interrupt mid-sentence loudly", "Complain to others", "A",
            "Polite, timely clarification prevents compounding misunderstandings.", Difficulty.EASY, 2));
    }

    private static void addGroupDiscussion(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "What is the primary evaluation objective in a campus placement Group Discussion (GD)?",
            "Assessing teamwork, communication, leadership, and analytical thinking", "Testing who can shout the loudest", "Evaluating speed of talking", "Seeing who dominates the floor completely", "A",
            "GDs evaluate collaborative problem-solving, structured articulation, and interpersonal dynamics.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "When initiating a GD, what is the best strategy?",
            "Define the topic, provide context, and outline a framework for discussion", "State a controversial opinion aggressively", "Immediately conclude the discussion", "Ask everyone to remain silent", "A",
            "Initiating effectively requires laying down a sensible framework and inviting contributions.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "How should a participant handle a situation when someone interrupts them rudely?",
            "Politely say: 'Kindly allow me to conclude my point, and I'd be glad to hear your view.'", "Shout over them", "Bang on the table", "Give up and stay silent for the rest of the GD", "A",
            "Assertive, calm boundary-setting reflects mature emotional intelligence and composure.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "What is the role of a 'facilitator' in a group discussion?",
            "Encouraging quiet members, summarizing progress, and steering discussion back to the topic", "Dominating speaking time", "Judging winners", "Writing on whiteboard continuously", "A",
            "A facilitator exhibits leadership by steering the group harmoniously towards objectives.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "If you do not know the factual background of an abstract GD topic, you should:",
            "Listen carefully to initial points, identify a valid perspective, and contribute structured insights", "Fake factual numbers", "Remain completely silent throughout", "Protest to the moderator", "A",
            "Observing early contributions allows one to synthesize themes and offer logical insights.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "How should you express disagreement with another participant in a GD?",
            "'I understand your perspective, however, we should also examine this dimension...'", "'You are completely wrong'", "'That makes zero sense'", "'I reject your statement entirely'", "A",
            "Disagreeing agreeably shows respect and fosters intellectual discourse.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "What should a good GD summary contain?",
            "A balanced synthesis of all major viewpoints discussed, without introducing new arguments", "Only one's personal opinions", "A vote on who spoke best", "A brand new topic", "A",
            "A summary encapsulates the group's collective insights objectively.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "What is the optimal body language during a group discussion?",
            "Sitting upright, leaning slightly forward, and looking around at all group members", "Leaning back with arms crossed", "Staring exclusively at the evaluator", "Slumping in the chair", "A",
            "Eye contact should be distributed across peers, not directed at the evaluator.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "Why is quality of contribution valued over quantity of speaking time in GDs?",
            "Relevant, structured points steer value, while ramblings waste group time", "Evaluators count seconds only", "Speaking less is always better", "No reason", "A",
            "A few crisp, impactful, evidence-based points carry far more weight than monopolizing the floor.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "What is a 'fish market' situation in a GD, and how should you respond?",
            "When multiple people shout simultaneously; wait for a split-second lull to speak calmly and firmly to restore order", "Shout louder than everyone else", "Leave the room", "Clap hands loudly", "A",
            "Stepping in with calm authority during chaos highlights natural leadership.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "Is it advisable to look directly at the examiner/evaluator while speaking in a GD?",
            "No, you should address and maintain eye contact with fellow group participants", "Yes, look only at the evaluator", "Look at the floor", "Close your eyes", "A",
            "Addressing the evaluator treats the GD as a speech rather than a collaborative peer discussion.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Group Discussion",
            "What should you do if the discussion goes off-track into irrelevant tangents?",
            "Gently intervene: 'While that is interesting, let us refocus on the core theme of our topic.'", "Insult the speaker", "Join the off-topic debate", "Say nothing", "A",
            "Refocusing the group demonstrates task orientation and leadership acumen.", Difficulty.MEDIUM, 3));
    }

    private static void addPresentationSkills(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "What is the recommended guideline for text density on presentation slides?",
            "The 6x6 rule: no more than 6 bullet points, with up to 6 words per bullet", "Fill entire slides with full paragraphs", "No text at all under any circumstances", "At least 20 bullets per slide", "A",
            "The 6x6 guideline prevents cognitive overload and keeps slides legible.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "What is the primary role of presentation slides during a talk?",
            "To serve as visual aids supporting the speaker's narrative", "To replace the speaker completely", "To serve as a full reading transcript", "To show decorative clipart", "A",
            "Slides enhance and reinforce what the speaker is communicating.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "How should a presenter handle eye contact during a slide presentation?",
            "Look at various sections of the audience, glancing at slides only briefly", "Turn back and read word-for-word from the screen", "Stare at the floor", "Look only at one person in the front row", "A",
            "Turning one's back to read slides disconnects the presenter from the audience.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "What is Guy Kawasaki's 10/20/30 rule of PowerPoint?",
            "10 slides, 20 minutes duration, minimum 30-point font size", "10 minutes, 20 slides, 30 bullets", "10 words, 20 images, 30 slides", "10 seconds per slide for 20 minutes", "A",
            "The 10/20/30 rule enforces brevity, time discipline, and readable typography.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "What is the most effective way to open a business or technical presentation?",
            "A compelling hook: a provocative question, striking statistic, or relatable story", "Reading the table of contents slide word-for-word", "Apologizing for being unprepared", "Telling a long personal autobiography", "A",
            "A strong hook immediately captures audience attention and sets up relevance.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "Why should high-contrast color palettes be used on presentation slides?",
            "To ensure readability across various projector types and lighting conditions", "To make the presentation look flashy", "To hide spelling errors", "Color contrast does not matter", "A",
            "Dark text on light backgrounds (or vice-versa) guarantees legibility.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "How should technical data and metrics be presented to non-technical stakeholders?",
            "Using intuitive visual charts and highlighting key takeaways rather than raw tables", "Showing raw SQL dumps", "Using maximum statistical formulas", "Omitting all outcomes", "A",
            "Visualizing data with clear 'so what?' implications bridges technical gaps.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "What is the purpose of a 'Call to Action' (CTA) at the end of a presentation?",
            "To clearly define the next steps or actions required from the audience", "To thank the organizers", "To list references", "To display contact info only", "A",
            "A CTA ensures the presentation translates into actionable outcomes.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "When encountering unexpected technical glitches during slide projection, you should:",
            "Remain calm, troubleshoot briefly, and continue delivering the core message verbally", "Panic and cancel the presentation", "Blame the AV team loudly", "Leave the stage", "A",
            "Composure during technical failure shows resilience and deep mastery of the topic.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "How should a presenter handle the Q&A session at the end of a presentation?",
            "Listen fully to each question, repeat or rephrase for the room, and answer concisely", "Dismiss difficult questions", "Argue with skeptics", "End the meeting instantly", "A",
            "Rephrasing verifies comprehension and ensures everyone in the room heard the query.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "What is the 'Signposting' technique in oral presentations?",
            "Using verbal markers like 'First, let us examine...', 'Moving on to...', 'In conclusion...'", "Holding physical cards", "Pointing with a laser pointer continuously", "Standing like a statue", "A",
            "Signposting guides listeners through the structural roadmap of the speech.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Presentation Skills",
            "Why is rehearsing with a timer essential before presenting?",
            "To ensure pacing fits strictly within the allotted time without rushing or spilling over", "To memorize word-for-word robotically", "To tire yourself out", "Timers are unnecessary", "A",
            "Respecting time boundaries is a hallmark of professional etiquette.", Difficulty.EASY, 2));
    }

    private static void addPublicSpeaking(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "What is glossophobia?",
            "The fear of public speaking", "Fear of crowds", "Fear of heights", "Fear of open spaces", "A",
            "Glossophobia is the medical/psychological term for speech anxiety.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "What Aristotle's triad forms the foundation of persuasive rhetoric?",
            "Ethos, Pathos, and Logos", "Thesis, Antithesis, Synthesis", "Introduction, Body, Conclusion", "Pitch, Pace, Volume", "A",
            "Ethos (credibility), Pathos (emotional resonance), and Logos (logical argument).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "How does storytelling improve audience engagement in public speaking?",
            "Stories make abstract ideas relatable, emotionally evocative, and memorable", "Stories allow the speaker to stretch time", "Stories eliminate the need for facts", "Stories distract the audience", "A",
            "Narratives stimulate mirror neurons and humanize complex technical insights.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "What is the best way to manage stage fright symptoms (sweating palms, elevated heart rate)?",
            "Channel nervous energy into vocal passion, practice deep breathing, and focus on the message", "Drink excessive caffeine", "Cancel the speech", "Avoid eye contact by looking at the ceiling", "A",
            "Reframing nervousness as excitement and steadying breath lowers sympathetic arousal.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "What does the 'Rule of Three' in speechwriting refer to?",
            "Ideas presented in trios are naturally more memorable and rhythmically satisfying", "Speaking for only 3 minutes", "Giving 3 rehearsals", "Inviting 3 speakers", "A",
            "Human cognition latches onto triads ('Veni, vidi, vici', 'Life, liberty, and the pursuit of happiness').", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "What is an extemporaneous speech?",
            "A well-researched, organized speech delivered from brief notes rather than memorized word-for-word", "An impromptu speech without any preparation", "A speech read verbatim from a manuscript", "A recited poem", "A",
            "Extemporaneous speaking combines thorough preparation with natural conversational adaptability.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "How can a speaker connect effectively with a large auditorium audience?",
            "Divide the room into imaginary quadrants and rotate eye contact steadily across each", "Fix gaze solely on the first row", "Look exclusively at the teleprompter", "Stare at the back wall", "A",
            "Sweeping eye contact across zones makes every quadrant feel included.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "What is the effect of pacing nervously back and forth across the stage?",
            "It distracts the audience and telegraphs anxiety", "It shows dynamic leadership", "It helps audience follow points", "It improves vocal projection", "A",
            "Unconscious pacing communicates insecurity; deliberate stage movement anchored to key transitions is far more effective.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "What is the best approach to handling a hostile heckler in a public forum?",
            "Remain composed, do not escalate into a shouting match, and offer to address points offline", "Engage in personal insults", "Storm off stage", "Challenge them to a fight", "A",
            "Dignified calm preserves the speaker's credibility in the eyes of the rest of the audience.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "Why is audience analysis crucial prior to drafting any speech?",
            "To align tone, examples, vocabulary, and depth with their interests and expectations", "To see how many chairs are needed", "To charge ticket fees", "It is not crucial", "A",
            "A speech crafted without understanding audience needs rarely resonates.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "What is an effective closing strategy for a keynote address?",
            "Summarizing the central thesis and ending with an inspiring, memorable concluding remark", "Abruptly saying 'that's it'", "Introducing a brand new argument", "Rushing through 10 leftover slides", "A",
            "A potent closing leaves an enduring impression in the listeners' minds.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Public Speaking",
            "How should a speaker use humor during a professional public address?",
            "Sparingly, tastefully, self-deprecatingly or relevantly, never at the expense of others", "Using offensive jokes to shock the room", "Telling unrelated jokes throughout", "Humor should never be used", "A",
            "Tasteful humor breaks tension and builds immediate rapport.", Difficulty.EASY, 2));
    }

    private static void addInterviewCommunication(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "What does the STAR method stand for when answering behavioral interview questions?",
            "Situation, Task, Action, Result", "Status, Target, Approach, Review", "Story, Timeline, Achievement, Reaction", "Strategy, Theory, Action, Report", "A",
            "STAR is the structured framework for behavioral questions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "When asked 'Tell me about yourself', what should a candidate emphasize?",
            "A structured journey of relevant academic background, key projects/skills, and career aspirations", "Life story from childhood", "Personal family history", "Hobbies only", "A",
            "The answer should focus concisely on professional value propositions and alignment with the role.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "How should you handle the question 'What is your greatest weakness?'",
            "Mention a genuine technical/professional area of growth and the proactive steps you are taking to overcome it", "Say 'I have no weaknesses'", "Say 'I am too perfectionist and work too hard'", "Confess to being chronically lazy", "A",
            "Authentic self-awareness paired with active remediation exhibits maturity and coachability.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "At the end of an interview, when asked 'Do you have any questions for us?', you should:",
            "Ask thoughtful questions about engineering culture, challenges, or company vision", "Say 'No, I have no questions'", "Ask immediately about vacation days", "Ask when you can leave", "A",
            "Insightful questions demonstrate genuine intellectual curiosity and investment in the opportunity.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "If an interviewer asks a coding question and you do not know the answer immediately, you should:",
            "Clarify requirements, talk through your thought process aloud, and explore potential approaches", "Remain completely silent until solved", "Say 'I never studied this' and give up", "Guess randomly without explanation", "A",
            "Interviewers assess analytical reasoning and communication under ambiguity, not just rote recall.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "Why is talking through your thought process essential during technical interviews?",
            "It allows the interviewer to understand your problem-solving paradigm and guide you if needed", "To fill silent airtime", "To show off vocabulary", "It is not essential", "A",
            "Externalizing reasoning reveals problem-decomposition capabilities and openness to feedback.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "When discussing past projects, what pronoun balance is ideal?",
            "Use 'We' to credit team collaboration, but highlight individual contributions with 'I designed/implemented'", "Use only 'I' and claim all credit", "Use only 'They' to avoid responsibility", "Never mention personal involvement", "A",
            "Balancing 'We' and 'I' shows both collaborative humility and personal accountability.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "How should you address a question about an employment or academic gap on your resume?",
            "Answer honestly and succinctly, emphasizing constructive activities (courses, certifications, caregiving) undertaken", "Lie about dates", "Blame professors or previous companies bitterly", "Refuse to answer", "A",
            "Honest, transparent framing focused on constructive learning maintains integrity.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "What is the impact of badmouthing a former employer or professor in an interview?",
            "It reflects poorly on the candidate's professionalism and attitude", "It proves the candidate is honest", "It impresses the interviewer", "It has no impact", "A",
            "Disparaging past colleagues signals toxicity and poor emotional maturity.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "What does appropriate non-verbal etiquette in a video interview include?",
            "Looking directly into the camera lens, well-lit environment, and professional attire", "Looking at your own picture on screen the whole time", "Lying in bed", "Wearing pajamas with camera off", "A",
            "Camera eye level simulates direct eye contact in digital interactions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "When answering behavioral questions, how long should an ideal response be?",
            "Around 1.5 to 2 minutes", "10 seconds", "10 minutes", "30 seconds without details", "A",
            "1.5 to 2 minutes delivers sufficient STAR depth without meandering.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Interview Communication",
            "What should you send within 24 hours of completing an interview?",
            "A personalized thank-you email reiterating enthusiasm and referencing a key discussion point", "A phone call asking for the job decision", "A connection request with no note", "Nothing", "A",
            "A prompt, tailored thank-you note reaffirms professionalism and interest.", Difficulty.EASY, 2));
    }

    private static void addBodyLanguage(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What do crossed arms and a backwards lean typically communicate in professional settings?",
            "Defensiveness, disinterest, or resistance", "Openness and enthusiasm", "Deep agreement", "Extreme confidence", "A",
            "Closed postures signal emotional barriers or skepticism.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What does leaning slightly forward during a conversation indicate?",
            "Active engagement and interest in what the speaker is saying", "Aggression and hostility", "Fatigue", "Disrespect", "A",
            "Leaning in demonstrates focus and attentive involvement.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What constitutes a professional, confident handshake?",
            "Firm, palm-to-palm, 2-3 pumps with mutual eye contact and a warm smile", "Limp 'dead fish' grip", "Bone-crushing squeeze", "Holding hands for a minute", "A",
            "A firm, balanced handshake establishes mutual respect and poise.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What is 'mirroring' in non-verbal communication?",
            "Subtly matching the conversational partner's posture, gestures, or energy to build rapport", "Staring into a mirror while talking", "Copying someone mocking them", "Wearing identical clothing", "A",
            "Subtle mirroring fosters unconscious trust and psychological safety.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What does excessive fidgeting, pen-clicking, or ring-twisting convey?",
            "Nervousness, anxiety, or impatience", "High intelligence", "Confidence", "Active listening", "A",
            "Fidgeting is a common displacement activity driven by inner tension.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What does maintaining continuous, unblinking eye contact for prolonged periods cause?",
            "Discomfort and an impression of intimidation or aggression", "Trust and empathy", "Better hearing", "Relaxation", "A",
            "Natural eye contact lasts 3-5 seconds at a time; unblinking stares feel invasive.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What are open hand gestures (palms exposed) generally associated with?",
            "Honesty, transparency, and openness", "Guilt and deception", "Aggression", "Weakness", "A",
            "Open palms historically signal that one is unarmed and speaking without deception.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "In proxemics, what is the standard 'social distance' for professional interactions?",
            "4 to 12 feet (1.2 to 3.7 meters)", "0 to 18 inches", "18 inches to 4 feet", "Over 25 feet", "A",
            "Social distance in Edward Hall's proxemics framework is 4 to 12 feet.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What does frequent checking of a wristwatch or phone during a conversation signal?",
            "Disrespect, boredom, or an urgency to end the interaction", "Punctuality", "Interest in the topic", "Politeness", "A",
            "Glancing at timepieces communicates that the conversational partner is a low priority.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What does a genuine 'Duchenne smile' involve?",
            "Contraction of both the zygomatic major muscle (raising mouth corners) and orbicularis oculi (crinkling eyes)", "Only moving the lips", "Showing teeth without smiling", "Frowning while laughing", "A",
            "A true smile reaches the eyes, producing distinctive crow's feet crinkles.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What does an upright, relaxed posture with shoulders back project?",
            "Confidence, poise, and readiness", "Arrogance", "Defensiveness", "Tiredness", "A",
            "Good posture facilitates deep breathing and signals quiet self-assurance.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Body Language",
            "What does clearing the throat repeatedly or touching the neck often signify under stress?",
            "Discomfort, anxiety, or attempts to self-soothe", "Thirst only", "Enthusiasm", "Authority", "A",
            "Touching the suprasternal notch or neck is a classic pacifying gesture under anxiety.", Difficulty.MEDIUM, 3));
    }

    private static void addEmailWriting(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "What constitutes an effective email subject line?",
            "Clear, concise, and specific regarding the email's core purpose", "Single word like 'Urgent' or 'Hi'", "Leaving it completely blank", "Writing the entire email message in the subject", "A",
            "Specific subject lines allow recipients to prioritize and retrieve messages efficiently.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "When should you use the 'BCC' (Blind Carbon Copy) field?",
            "When sending to large groups whose email addresses must remain private from each other", "When you want everyone to reply all", "To send confidential passwords", "To replace the 'To' field always", "A",
            "BCC preserves privacy and prevents accidental 'Reply All' storms.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "What does writing an email in ALL CAPS communicate to the reader?",
            "Shouting, aggression, or extreme unprofessionalism", "High importance", "Standard formatting", "Politeness", "A",
            "ALL CAPS in digital correspondence is universally interpreted as screaming.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "What is the best opening greeting for a formal professional email to a hiring manager?",
            "Dear Mr./Ms. [Last Name] or Dear Hiring Team,", "Hey buddy,", "Yo,", "What's up,", "A",
            "Respectful, formal salutations set a professional tone.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "What is the recommended structure for a professional business email?",
            "Salutation -> Context/Purpose -> Details/Bulleted points -> Call to Action -> Sign-off", "One gigantic unstructured paragraph", "Random sentences without greetings", "Signature only", "A",
            "Structured emails enhance scannability and quick comprehension.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "Why is proofreading before clicking 'Send' vital?",
            "To catch typos, grammatical errors, broken links, and verify correct attachments", "It isn't necessary with spell check", "To delay the email", "To increase file size", "A",
            "Careless errors in email undermine professional credibility.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "When should 'Reply All' be used?",
            "Only when the information is truly relevant to every single recipient on the thread", "On every email chain automatically", "To say 'Thanks!' to the whole company", "To complain publicly", "A",
            "Misusing 'Reply All' floods inboxes with unnecessary noise.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "What should a professional email signature include?",
            "Full name, job title, company/institution, and professional contact information", "Inspirational poems and GIFs", "Personal social media handles only", "No information", "A",
            "A clean signature provides verifiable identity and easy contact options.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "How should you mention an attached document in an email body?",
            "'Please find attached [Document Name] for your review.'", "'Look at the file'", "'Attachment somewhere'", "Do not mention it", "A",
            "Explicitly referencing attachments ensures recipients don't overlook them.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "What is the recommended turnaround time for responding to internal workplace emails?",
            "Within 24 to 48 business hours", "Within 2 weeks", "Never", "Within 1 minute always", "A",
            "A 24-48 hour window is standard workplace courtesy for non-emergencies.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "How should an angry or frustrated email from a client be handled?",
            "Pause to cool down, remain polite, address the root issue constructively, and offer a path forward", "Fire back an angry reply immediately", "Forward to the whole company", "Delete it and pretend it didn't arrive", "A",
            "Emotional restraint and solution-oriented responses de-escalate workplace conflict.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(COM, COM_SUB, "Email Writing",
            "What is the purpose of an 'Out of Office' (OOO) auto-responder?",
            "To inform senders of your absence, return date, and emergency contact person", "To brag about your vacation", "To block all incoming emails permanently", "To hide from work", "A",
            "An OOO notification manages expectations regarding response delays.", Difficulty.EASY, 2));
    }
}
