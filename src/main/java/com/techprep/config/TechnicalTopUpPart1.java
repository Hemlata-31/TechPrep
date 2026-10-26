package com.techprep.config;

import com.techprep.entity.Difficulty;
import java.util.List;

public class TechnicalTopUpPart1 {
    private static final String TCH = "Technical";

    public static void populate(List<QuestionTopUpSeeder.QSpec> list) {
        // Java (7 topics)
        addJavaBasics(list);
        addJavaOop(list);
        addJavaCollections(list);
        addJavaExceptions(list);
        addJavaMultithreading(list);
        addJava8(list);
        addJavaJdbc(list);

        // C++ (6 topics)
        addCppBasics(list);
        addCppOop(list);
        addCppStl(list);
        addCppPointers(list);
        addCppMemoryManagement(list);
        addCppExceptions(list);

        // Python (6 topics)
        addPythonBasics(list);
        addPythonDataTypes(list);
        addPythonFunctions(list);
        addPythonOop(list);
        addPythonExceptions(list);
        addPythonFileHandling(list);
    }

    // ==================== JAVA ====================

    private static void addJavaBasics(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "Why is Java considered platform independent?",
            "Because Java source code compiles to bytecode which runs on any JVM", "Because Java compilers are written in C", "Because it does not use memory", "Because it only runs on Linux", "A",
            "The JVM executes platform-independent bytecode (.class files) on any underlying OS.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "What is the default value of a boolean instance variable in Java?",
            "false", "true", "0", "null", "A",
            "In Java, instance boolean variables default to false.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "What is the size of an int data type in Java?",
            "32 bits (4 bytes)", "16 bits (2 bytes)", "64 bits (8 bytes)", "8 bits (1 byte)", "A",
            "Java's int primitive is fixed at 32 bits (4 bytes) signed two's complement integer.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "Which component of Java is responsible for running bytecode and memory management?",
            "JVM (Java Virtual Machine)", "JDK", "JRE only", "JIT compiler only", "A",
            "The JVM loads, verifies, and executes bytecode, managing runtime memory and garbage collection.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "What is the difference between JDK and JRE?",
            "JDK contains development tools + JRE; JRE contains only the runtime environment + JVM", "JDK is only for running, JRE is for compiling", "They are identical", "JRE contains javac", "A",
            "JDK provides the compiler (javac), debugger, and full tools alongside JRE.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "What does the 'final' keyword on a variable in Java mean?",
            "The variable becomes a constant whose value cannot be reassigned once initialized", "The variable can only be accessed inside the class", "The variable is destroyed on method exit", "The variable is static", "A",
            "A final variable can be assigned only once and cannot be modified thereafter.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "Can the main method in Java be overloaded?",
            "Yes, you can have multiple main methods with different parameters, but JVM calls only String[] args", "No, it causes compile error", "Yes, and JVM calls all of them", "Only if marked abstract", "A",
            "Java supports overloading the main method, but standard program entry requires String[] args signature.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "What is type casting from double to int in Java called?",
            "Narrowing (explicit) casting", "Widening (implicit) casting", "Autoboxing", "Unboxing", "A",
            "Converting a larger type (double) to smaller type (int) requires explicit narrowing cast: (int) d.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "Where are local variables stored in Java memory?",
            "On the Stack", "On the Heap", "In the Metaspace", "In the PermGen", "A",
            "Local variables and method call frames are allocated on thread call stacks.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "What does String immutability mean in Java?",
            "Once created, the internal character sequence of a String object cannot be modified", "String references cannot be changed", "Strings cannot be stored in variables", "Strings are thread-unsafe", "A",
            "String objects cannot have their state modified after creation, enabling string pool caching.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "What is the difference between == and equals() for objects in Java?",
            "== compares reference memory addresses; equals() compares state/contents if overridden", "== compares values; equals compares references", "They behave identically", "equals() cannot be overridden", "A",
            "== checks reference equality, while equals() method provides semantic equality checks.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Basics",
            "What is the purpose of the 'static' keyword on a method in Java?",
            "The method belongs to the class itself rather than instances and can be invoked without creating an object", "The method cannot be called", "The method cannot return a value", "The method is executed on a background thread", "A",
            "Static members belong to the class namespace and are loaded with the class bytecode.", Difficulty.EASY, 2));
    }

    private static void addJavaOop(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "Which OOP principle is implemented by private fields and public getters/setters?",
            "Encapsulation", "Inheritance", "Polymorphism", "Abstraction", "A",
            "Encapsulation bundles data with accessor methods and restricts direct outside modification.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "Can a class in Java inherit from multiple classes using 'extends'?",
            "No, Java does not support multiple class inheritance to avoid the diamond problem", "Yes, using comma separated class names", "Yes, up to 3 classes", "Only if classes are abstract", "A",
            "Java prevents diamond ambiguity by permitting single class inheritance while allowing multiple interface implementations.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "What is runtime polymorphism in Java achieved through?",
            "Method overriding (dynamic method dispatch)", "Method overloading", "Operator overloading", "Static binding", "A",
            "Overriding methods allows the JVM to dynamically dispatch calls based on the runtime object type.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "Can an abstract class have constructors in Java?",
            "Yes, abstract classes can have constructors called during subclass instantiation via super()", "No, abstract classes cannot have constructors", "Only default constructors", "Only private constructors", "A",
            "Subclasses invoke abstract superclass constructors via super() to initialize inherited fields.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "What happens if a class does not provide any constructor?",
            "The compiler inserts a default no-argument constructor automatically", "The code fails to compile", "Objects cannot be instantiated", "The class becomes abstract", "A",
            "The Java compiler synthesizes a default no-arg constructor if no explicit constructor is defined.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "What does the 'super' keyword refer to in Java?",
            "The direct parent (superclass) object/constructor", "The current class instance", "The top-level Object class always", "The static context", "A",
            "'super' refers to the immediate superclass members or constructor.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "Can you override a private or static method in Java?",
            "No, private methods are not visible to subclasses and static methods are shadowed/hidden, not overridden", "Yes, both can be overridden", "Only private methods", "Only static methods", "A",
            "Method overriding relies on dynamic dispatch; private methods aren't inherited and static methods are bound at compile time.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "What is the difference between an interface and an abstract class in modern Java?",
            "An abstract class can maintain state with instance fields and constructors; interfaces define contracts and can have default/static methods", "Interfaces cannot have method bodies under any circumstance", "Abstract classes support multiple inheritance", "No difference", "A",
            "Abstract classes retain instance state/constructors; interfaces represent contracts across disparate hierarchies.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "What is method overloading in Java?",
            "Methods in the same class with identical names but different parameter lists (types, number, or order)", "Subclass redefining parent method with same signature", "Calling a method recursively", "Assigning a method to a variable", "A",
            "Overloading allows multiple methods with identical names differentiated by parameters at compile time.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "What is the Covariant Return Type feature introduced in Java 5?",
            "An overriding method can return a subtype of the return type declared in the overridden method", "Return type can be changed to void", "Return type can be any primitive", "It is not allowed", "A",
            "Covariant returns allow a subclass method override to return a more specific subclass type.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "What does the 'final' keyword on a class signify in Java?",
            "The class cannot be subclassed/extended", "The class has no methods", "All fields are automatically static", "The class cannot be instantiated", "A",
            "A final class (e.g. java.lang.String) cannot be inherited by any subclass.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "OOP",
            "What is composition in OOP, and why is it often preferred over inheritance?",
            "'Has-a' relationship offering greater flexibility and loose coupling without exposing internal subclassing quirks", "'Is-a' relationship with tight coupling", "Calling static methods", "Using interface default methods", "A",
            "'Favor composition over inheritance' prevents brittle base class hierarchies and permits dynamic component swapping.", Difficulty.MEDIUM, 3));
    }

    private static void addJavaCollections(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "What is the difference between ArrayList and LinkedList in Java?",
            "ArrayList uses a dynamic array with O(1) random access; LinkedList uses a doubly-linked list with O(1) insertion/deletion at ends", "LinkedList has O(1) random access", "ArrayList cannot resize", "LinkedList is thread-safe", "A",
            "ArrayList provides contiguous memory random indexing; LinkedList consists of linked nodes.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "What is the average time complexity of get() and put() in HashMap?",
            "O(1)", "O(n)", "O(log n)", "O(n²)", "A",
            "With an effective hash function and proper load balancing, HashMap operations run in O(1) average time.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "How does HashMap handle hash collisions since Java 8?",
            "Chaining with linked lists; converts to a Red-Black Tree when bucket size exceeds 8 (TREEIFY_THRESHOLD)", "Linear probing", "Throws an exception", "Overwrites previous keys", "A",
            "Java 8 transforms hash buckets into balanced Red-Black trees when collisions exceed 8, improving worst-case search to O(log n).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "Which Set implementation maintains insertion order of elements?",
            "LinkedHashSet", "HashSet", "TreeSet", "EnumSet", "A",
            "LinkedHashSet maintains a doubly-linked list running through its hash table buckets to preserve insertion order.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "Which Set implementation keeps elements sorted in natural or comparator order?",
            "TreeSet", "HashSet", "LinkedHashSet", "CopyOnWriteArraySet", "A",
            "TreeSet uses a NavigableMap (Red-Black tree) to maintain elements in sorted order.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "What is the contract between equals() and hashCode() in Java?",
            "If two objects are equal according to equals(), they must produce identical hashCode() values", "If hash codes are equal, objects must be equal", "There is no contract", "They must never return equal values", "A",
            "Equal objects must have equal hash codes to prevent hash collection lookup failures.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "What causes a ConcurrentModificationException?",
            "Modifying a collection directly while iterating over it with an iterator (fail-fast behavior)", "Two threads reading the collection", "Adding null keys", "Reaching collection capacity", "A",
            "Iterators track a modCount; structural changes outside iterator methods throw ConcurrentModificationException.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "What is the difference between Comparable and Comparator in Java?",
            "Comparable defines natural ordering via compareTo() inside the class; Comparator defines external custom ordering via compare()", "Comparable is in java.util, Comparator in java.lang", "They are identical", "Comparator can only sort integers", "A",
            "Comparable provides internal natural sorting; Comparator permits multiple external comparison strategies.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "Why is ConcurrentHashMap preferred over Collections.synchronizedMap()?",
            "ConcurrentHashMap uses lock striping / CAS operations allowing concurrent reads and writes without locking the whole map", "It does not allow null keys", "It has smaller memory footprint", "It is sorted", "A",
            "SynchronizedMap locks the entire object per operation; ConcurrentHashMap offers high throughput via segmented/CAS concurrency.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "Can HashMap contain null keys and null values in Java?",
            "Yes, HashMap allows one null key and multiple null values", "No, it throws NullPointerException", "Only null values, not null keys", "Only null keys, not null values", "A",
            "HashMap handles a null key by placing it in bucket index 0; multiple null values are permitted.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "What is the underlying data structure of PriorityQueue in Java?",
            "A binary min-heap implemented as an array", "A linked list", "A binary search tree", "A hash table", "A",
            "PriorityQueue implements a min-heap array where the head represents the least element.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Collections",
            "What is the default initial capacity and load factor of a Java HashMap?",
            "16 and 0.75", "10 and 0.5", "32 and 0.8", "8 and 0.75", "A",
            "The default initial capacity is 16, and the default load factor is 0.75 (resizing at 12 elements).", Difficulty.EASY, 2));
    }

    private static void addJavaExceptions(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is the difference between checked and unchecked exceptions in Java?",
            "Checked exceptions inherit from Exception (excluding RuntimeException) and must be handled or declared; unchecked inherit from RuntimeException/Error", "Checked occur at runtime; unchecked occur at compile time", "Unchecked must be caught with try-catch", "No difference", "A",
            "Checked exceptions are enforced by the compiler at compile-time; unchecked exceptions are runtime exceptions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "Under what circumstance does a 'finally' block NOT execute in Java?",
            "When System.exit(0) is called or JVM crashes", "When an uncaught exception is thrown in try", "When return is called inside try", "It always executes unconditionally", "A",
            "System.exit() terminates the JVM process immediately, bypassing the finally block.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is the base class for all errors and exceptions in the Java language?",
            "java.lang.Throwable", "java.lang.Exception", "java.lang.Error", "java.lang.RuntimeException", "A",
            "Throwable is the root superclass of both Exception and Error in Java.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is the primary benefit of try-with-resources introduced in Java 7?",
            "Automatic closing of resources implementing AutoCloseable without explicit finally blocks", "Catches all exceptions automatically", "Improves execution speed", "Eliminates catch blocks", "A",
            "Try-with-resources guarantees automatic resource cleanup and avoids resource leak bugs.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is the difference between 'throw' and 'throws' keywords in Java?",
            "'throw' is used to explicitly throw an exception object; 'throws' declares exceptions a method might throw in its signature", "'throw' is in method signature; 'throws' is in method body", "They are synonyms", "'throws' can only throw checked exceptions", "A",
            "'throw' is an executable statement; 'throws' is a method signature clause.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "Can you have a try block without a catch block in Java?",
            "Yes, provided it is followed by a finally block or is a try-with-resources statement", "No, catch is strictly mandatory", "Only if the method has throws Exception", "Never", "A",
            "Try can be paired with finally directly (try-finally) or used as try-with-resources.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is the correct ordering when catching subclass and superclass exceptions?",
            "Subclass exception catch block must come before the superclass exception catch block", "Superclass must come first", "Any order is valid", "They cannot be in the same try block", "A",
            "Catching superclass before subclass triggers an 'unreachable code' compile-time error.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is an OutOfMemoryError in Java?",
            "An Error thrown by the JVM when the Garbage Collector cannot free heap space to allocate an object", "A checked exception", "A file error", "A stack overflow error", "A",
            "OutOfMemoryError occurs when heap memory is completely exhausted and GC cannot reclaim space.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is exception chaining in Java?",
            "Wrapping an underlying caught exception inside a newly thrown exception using constructor or initCause()", "Catching multiple exceptions in one line", "Chaining try blocks", "Throwing exceptions in a loop", "A",
            "Exception chaining preserves the original root cause stack trace while rethrowing higher-level exceptions.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is a multi-catch block in Java 7+?",
            "Catching multiple unrelated exceptions in a single catch block separated by '|'", "Having 10 catch blocks", "Catching Throwable directly", "Catching exceptions across threads", "A",
            "Syntax `catch (IOException | SQLException e)` eliminates repetitive boilerplate handling.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is the return value of a method if both 'try' and 'finally' blocks contain return statements?",
            "The return value from the 'finally' block overrides the 'try' block return value", "The 'try' block return value", "A compile error occurs", "Both values concatenated", "A",
            "A return inside a finally block silently discards and overrides any return or exception from the try block.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Exception Handling",
            "What is a custom exception, and how is it created in Java?",
            "A user-defined class extending Exception (for checked) or RuntimeException (for unchecked)", "A class implementing Throwable directly", "A method annotated with @Exception", "A native C function", "A",
            "Developers create custom exceptions by subclassing standard Exception or RuntimeException.", Difficulty.EASY, 2));
    }

    private static void addJavaMultithreading(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What are the two common ways to create a thread in Java?",
            "Extending Thread class or implementing Runnable interface", "Implementing Callable or Future only", "Using static methods", "Writing native assembly", "A",
            "Threads are created either by extending java.lang.Thread or implementing java.lang.Runnable.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What happens when you call run() directly instead of start() on a Thread object?",
            "run() executes synchronously on the current thread without spawning a new thread", "A new thread is spawned normally", "Throws IllegalThreadStateException", "Program crashes", "A",
            "start() invokes OS-level thread creation and registers run() to execute asynchronously on the new thread.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What is the difference between Runnable and Callable in Java?",
            "Callable's call() method can return a result and throw checked exceptions; Runnable's run() returns void and cannot throw checked exceptions", "Runnable can return a value", "Callable cannot be submitted to ExecutorService", "No difference", "A",
            "Callable<V> provides typed return values and checked exception declaration for concurrent tasks.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What does the 'volatile' keyword guarantee in Java?",
            "Visibility of variable updates across threads directly to/from main memory without caching", "Mutual exclusion / atomicity", "Reentrant locking", "Deadlock prevention", "A",
            "Volatile prevents thread-local caching of variable reads/writes, ensuring memory visibility.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What is a race condition in multithreading?",
            "When multiple threads access and mutate shared data concurrently and the outcome depends on execution timing", "A thread running too fast", "A deadlock between two locks", "Thread starvation", "A",
            "Race conditions cause indeterminate state when concurrent non-atomic updates interleave unpredictably.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What are the four necessary conditions for deadlock to occur?",
            "Mutual exclusion, Hold and wait, No preemption, and Circular wait", "Concurrency, Parallelism, Scheduling, Locks", "Cache misses, RAM limits, CPU spikes, Thrashing", "Threads, Runnables, Futures, Callables", "A",
            "Coffman's four conditions must all hold concurrently for a deadlock state to materialize.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "Why must wait(), notify(), and notifyAll() be called inside a synchronized context?",
            "Because they operate on the intrinsic monitor (lock) of the object", "To prevent NullPointerException", "They are static methods", "Compiler requirement with no reason", "A",
            "Calling wait/notify without owning the monitor throws an IllegalMonitorStateException.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What is the purpose of ThreadPoolExecutor in Java?",
            "Reuses a bounded pool of pre-created threads to execute submitted tasks, reducing thread creation overhead", "Creates unlimited threads dynamically", "Terminates inactive threads immediately", "Replaces the JVM Garbage Collector", "A",
            "Thread pools reuse existing worker threads, avoiding expensive kernel thread allocation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What does ThreadLocal provide in Java?",
            "Thread-confined variables where each thread accessing the variable has its own independently initialized copy", "A global synchronized lock", "A shared thread pool", "Thread priority boost", "A",
            "ThreadLocal stores data isolated to the lifecycle of the executing thread.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What is the purpose of AtomicInteger and compare-and-swap (CAS)?",
            "Lock-free, hardware-supported atomic updates on integer values without synchronization overhead", "Locks the entire JVM", "Performs integer parsing", "Generates random numbers", "A",
            "CAS primitives provide non-blocking thread-safe mutations at the hardware instruction level.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What does the join() method on a Thread instance do?",
            "Pauses the calling thread until the target thread finishes execution", "Joins two thread pools together", "Kills the thread immediately", "Merges their memory stacks", "A",
            "t.join() blocks the current thread until thread t terminates.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Multithreading",
            "What is a daemon thread in Java?",
            "A background thread that does not prevent the JVM from exiting when all user threads finish", "A thread with highest priority", "A corrupted thread", "A synchronized thread", "A",
            "The JVM terminates automatically when only daemon threads (e.g. Garbage Collector) remain active.", Difficulty.MEDIUM, 3));
    }

    private static void addJava8(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What is a Functional Interface in Java 8?",
            "An interface that contains exactly one abstract method (can have multiple default/static methods)", "An interface with no methods", "An interface with only private methods", "Any interface with @FunctionalInterface annotation only", "A",
            "A Single Abstract Method (SAM) interface enables lambda expression assignment.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What is the purpose of the Optional class introduced in Java 8?",
            "To represent the presence or absence of a value and reduce NullPointerExceptions", "To make variables optional in methods", "To speed up collections", "To replace boolean variables", "A",
            "Optional<T> provides a container object that explicitly encapsulates missing values.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What is the difference between intermediate and terminal operations in Java Streams?",
            "Intermediate operations are lazy and return a new Stream; terminal operations trigger execution and produce a result/side-effect", "Intermediate operations return void", "Terminal operations can be chained indefinitely", "No difference", "A",
            "Stream pipelines remain lazy until a terminal operation (e.g. collect, forEach, reduce) is invoked.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What does map() vs flatMap() do in the Stream API?",
            "map() transforms each element into one value; flatMap() transforms each element into a stream and flattens them into one stream", "flatMap() sorts elements", "map() flattens lists", "They are interchangeable", "A",
            "flatMap flattens nested Stream<Stream<T>> structures into a single Stream<T>.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "Which built-in functional interface takes one argument and returns a boolean?",
            "Predicate<T>", "Function<T, R>", "Consumer<T>", "Supplier<T>", "A",
            "Predicate<T> defines `boolean test(T t)`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "Which functional interface takes no arguments and returns a value?",
            "Supplier<T>", "Consumer<T>", "Function<T, R>", "Predicate<T>", "A",
            "Supplier<T> defines `T get()` without accepting arguments.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What is the syntax for a method reference to an instance method of an arbitrary object of a particular type?",
            "ClassName::methodName", "instance.methodName()", "ClassName->methodName", "new ClassName()", "A",
            "Syntax `String::compareToIgnoreCase` references instance methods unbound to a specific instance.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What was the main purpose of adding default methods to interfaces in Java 8?",
            "To allow backward-compatible API evolution of interfaces without breaking existing implementing classes", "To replace abstract classes entirely", "To make interfaces slower", "To prevent inheritance", "A",
            "Default methods enabled adding methods like stream() to Collection without breaking legacy code.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What package provides the modern, immutable, thread-safe date and time API in Java 8?",
            "java.time", "java.util.Date", "java.sql.Date", "java.calendar", "A",
            "The java.time package (JSR-310) replaced legacy mutable Date/Calendar APIs.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What feature was introduced in Java 14+ as a preview and standardized in Java 16 to create transparent data carrier classes?",
            "Records", "Enums", "Structs", "Sealed classes", "A",
            "Records (`record Point(int x, int y) {}`) generate boilerplate constructors, getters, equals, and hashCode.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What is a Sealed Class introduced in Java 17?",
            "A class that restricts which other classes or interfaces may extend or implement it using 'permits'", "A class that cannot be instantiated", "A private class", "A encrypted class", "A",
            "Sealed classes provide domain modeling control over exhaustive class hierarchies.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "Java 8+",
            "What major lightweight threading model was introduced in Project Loom / Java 21?",
            "Virtual Threads", "Async/Await", "Coroutines", "Green Threads 2", "A",
            "Virtual threads are cheap JVM-managed user-mode threads enabling massive throughput for I/O-bound tasks.", Difficulty.MEDIUM, 3));
    }

    private static void addJavaJdbc(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "What is JDBC in Java?",
            "Java Database Connectivity: an API for connecting Java applications to relational databases", "Java Data Bytecode Compiler", "Java Distributed Component", "Java Data Binary Cache", "A",
            "JDBC defines standard interfaces for query execution and relational database interaction.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "Why is PreparedStatement preferred over Statement in JDBC?",
            "PreparedStatement precompiles SQL, improves performance, and prevents SQL Injection attacks", "Statement is faster for complex queries", "Statement automatically caches query results", "No difference", "A",
            "PreparedStatement binds user input as parameterized data rather than executable SQL syntax.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "Which interface in JDBC is used to retrieve data from a SQL query?",
            "ResultSet", "RowSet", "DataTable", "QueryResult", "A",
            "ResultSet maintains a cursor pointing to rows returned by an executed SELECT query.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "How do you manage database transactions manually in JDBC?",
            "Set connection.setAutoCommit(false), then call connection.commit() or connection.rollback()", "Use the transaction keyword", "Execute COMMIT TABLE SQL", "Transactions are always automatic", "A",
            "Disabling autoCommit allows grouping multiple operations into an atomic transaction unit.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "Which method of Statement is used to execute INSERT, UPDATE, or DELETE queries?",
            "executeUpdate()", "executeQuery()", "executeSelect()", "executeModify()", "A",
            "executeUpdate() returns an int representing the number of rows affected by DML statements.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "What is the purpose of database connection pooling (e.g. HikariCP)?",
            "Maintains a cache of active database connections to avoid the heavy cost of establishing a new connection per request", "Runs database queries in parallel automatically", "Replaces SQL queries with Java code", "Backs up database tables", "A",
            "Establishing TCP handshakes and database authentication is expensive; pooling reuses live sockets.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "What does CallableStatement in JDBC allow you to do?",
            "Execute stored procedures and database functions", "Call external REST APIs", "Execute DDL commands only", "Call Java methods from SQL", "A",
            "CallableStatement provides standard syntax to invoke database stored procedures.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "What is batch processing in JDBC, and how is it used?",
            "Grouping multiple SQL queries via addBatch() and executing them in one network round-trip via executeBatch()", "Running queries overnight", "Reading data in chunks of 100", "Parallel threads running queries", "A",
            "executeBatch() reduces network latency by transmitting bulk queries in single round-trips.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "Which interface provides metadata about database tables, columns, and capabilities?",
            "DatabaseMetaData", "ResultSetMetaData", "TableMetaData", "SchemaInfo", "A",
            "DatabaseMetaData provides comprehensive information about the database software and schema.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "Which method advances the cursor to the next row of a ResultSet in JDBC?",
            "rs.next()", "rs.forward()", "rs.get()", "rs.hasNext()", "A",
            "rs.next() moves the cursor to the next row and returns true if a row is present.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "What exception is thrown by JDBC methods when a database access error occurs?",
            "java.sql.SQLException", "java.io.IOException", "java.lang.DatabaseException", "java.sql.DataFaultException", "A",
            "SQLException is the primary checked exception for database access failures in JDBC.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java", "JDBC",
            "Why is closing Connection, Statement, and ResultSet objects crucial in JDBC?",
            "To release underlying physical database sockets, cursors, and server-side resources to prevent leaks", "To commit changes automatically", "The JVM will not exit otherwise", "It is only a cosmetic convention", "A",
            "Unclosed JDBC resources leak database cursors and connections, eventually exhausting the server connection pool.", Difficulty.EASY, 2));
    }

    // ==================== C++ ====================

    private static void addCppBasics(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What is the output of `sizeof('a')` in C++ versus C?",
            "1 byte in C++, 4 bytes (sizeof(int)) in C", "4 bytes in both", "1 byte in both", "2 bytes in C++", "A",
            "In C++, character literals are of type `char` (1 byte); in C, they are promoted to `int`.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What is a reference variable in C++?",
            "An alias or alternate name for an already existing variable", "A pointer that can be null", "A variable with automatic garbage collection", "A global variable", "A",
            "A reference (`int& ref = x;`) acts as an immutable alias bound to the storage location of `x`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What is the difference between `const` and `constexpr` in C++?",
            "`constexpr` guarantees evaluation at compile-time; `const` only guarantees that a variable cannot be mutated after initialization", "`const` is evaluated at compile time only", "They are identical", "`constexpr` cannot be used with functions", "A",
            "`constexpr` enforces compile-time constant expression calculation.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What does the `auto` keyword do in C++11 and later?",
            "Directs the compiler to deduce the variable's type from its initializer expression", "Specifies automatic storage duration only", "Creates dynamic memory", "Makes variable constant", "A",
            "Modern C++ uses `auto` for automatic type deduction at compile time.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What is the scope resolution operator in C++?",
            "::", "->", ".", ":", "A",
            "The double colon `::` qualifies identifiers with their enclosing namespace or class scope.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What happens if you do not specify an access specifier for members of a `class` vs a `struct` in C++?",
            "`class` members are private by default; `struct` members are public by default", "Both are public by default", "Both are private by default", "`class` is protected", "A",
            "The only syntactic difference between class and struct in C++ is default member and inheritance accessibility.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What is an inline function in C++?",
            "A suggestion to the compiler to substitute the function body at each call site to eliminate function call overhead", "A function defined inside another function", "A recursive function", "A function that cannot return a value", "A",
            "Inline expansion replaces calls with function code bodies for small, hot functions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What header file is required to use `std::cin` and `std::cout` in C++?",
            "<iostream>", "<stdio.h>", "<conio.h>", "<stream.h>", "A",
            "The `<iostream>` header defines standard input and output stream objects.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What does `std::endl` do in addition to printing a newline character?",
            "It flushes the output stream buffer", "It clears the screen", "It resets the cursor to origin", "It terminates the program", "A",
            "`std::endl` emits `\n` and calls `flush()`, which can impact performance in tight loops compared to `\n`.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What is a namespace in C++ used for?",
            "To group named entities into distinct logical scopes and prevent identifier naming collisions", "To allocate dynamic memory", "To speed up compilation", "To encrypt source code", "A",
            "Namespaces (e.g. `namespace mylib {}`) prevent global namespace pollution and collisions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What is the return type of the `main()` function in C++?",
            "int", "void", "char", "bool", "A",
            "ISO C++ mandates that main must return `int` representing process exit status.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Basics",
            "What is function overloading in C++?",
            "Defining multiple functions with the same name but different signatures within the same scope", "Overwriting base class functions", "Passing pointers to functions", "Writing recursive functions", "A",
            "C++ differentiates overloaded functions using argument types and counts via name mangling.", Difficulty.EASY, 2));
    }

    private static void addCppOop(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is a virtual function in C++?",
            "A member function declared in a base class with `virtual` that can be overridden in derived classes for dynamic binding", "A function that has no body", "A static function", "A template function", "A",
            "Virtual functions enable runtime polymorphism via the virtual method table (vtable).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is a pure virtual function, and what does it make the class?",
            "A function defined as `virtual void func() = 0;`, making the containing class an abstract class", "A function that returns 0", "A private virtual function", "A function with empty braces", "A",
            "Pure virtual functions define pure interfaces; classes containing them cannot be instantiated.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "Why should a base class destructor always be declared `virtual` if derived classes exist?",
            "To ensure derived class destructors are called when deleting derived objects through a base class pointer, preventing memory leaks", "To prevent compiler errors", "To make destruction faster", "C++ mandates virtual destructors for all classes", "A",
            "Deleting via `Base*` without a virtual destructor results in undefined behavior and resource leaks.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is the 'vtable' (virtual method table) in C++?",
            "A compiler-generated lookup table of function pointers used to resolve virtual function calls dynamically at runtime", "A table storing variable names", "A hash table of object IDs", "A stack frame structure", "A",
            "Objects of classes with virtual methods carry a hidden `vptr` pointing to their class's vtable.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is the diamond problem in C++ multiple inheritance, and how is it solved?",
            "Duplicate base class instances in multiple inheritance; solved using `virtual` base class inheritance (`virtual public Base`)", "Solved using templates", "Solved using pointers", "C++ does not have diamond problems", "A",
            "Virtual inheritance ensures only a single shared instance of the common ancestor exists.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is a copy constructor in C++?",
            "A constructor initializing an object using another object of the same class: `ClassName(const ClassName& other)`", "A function that clones pointers", "A constructor with no arguments", "A constructor that copies files", "A",
            "The copy constructor handles deep copy semantics when objects are passed or returned by value.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is the 'Rule of Three' in C++ (prior to C++11)?",
            "If a class defines a Destructor, Copy Constructor, or Copy Assignment Operator, it should define all three", "Every class must have three methods", "Inherit at most three classes", "Have three access specifiers", "A",
            "Managing raw resources manually requires defining custom destruction, copy construction, and assignment.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is operator overloading in C++?",
            "Redefining the behavior of built-in operators (+, -, *, <<) for user-defined types", "Creating new operator symbols", "Calling operators in loops", "Converting operators to functions", "A",
            "Operator overloading allows custom classes to integrate with idiomatic operator syntax.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is a `friend` function in C++?",
            "A non-member function granted access to the private and protected members of a class", "A derived class member", "A public function", "A global library function", "A",
            "Declaring `friend void func();` explicitly breaks encapsulation boundaries for specific helpers.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is member initializer list syntax in a C++ constructor?",
            "`ClassName() : member1(val1), member2(val2) {}` which initializes fields before constructor body runs", "Assigning values inside `{}`", "Using static blocks", "Declaring types in header", "A",
            "Initializer lists avoid redundant default construction and are mandatory for const/reference members.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What is the `this` pointer in C++?",
            "A constant pointer available inside non-static member functions pointing to the object invoking the function", "A global pointer to main", "A pointer to the parent class", "A null pointer", "A",
            "`this` represents the memory address of the invoking instance (`this->field`).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "OOP",
            "What does the `override` specifier in C++11 do?",
            "Directs the compiler to ensure the member function is overriding a virtual function in a base class, catching typo bugs", "Forces a function to be virtual", "Overwrites global variables", "Prevents further overrides", "A",
            "`override` triggers compiler errors if the signature does not match an existing base virtual method.", Difficulty.EASY, 2));
    }

    private static void addCppStl(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What is `std::vector` in C++ STL?",
            "A dynamically resizable contiguous array providing O(1) amortized insertion at the back and O(1) random access", "A linked list", "A binary search tree", "A hash set", "A",
            "`std::vector` manages contiguous heap memory with automatic dynamic resizing.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What is the difference between `std::map` and `std::unordered_map` in C++?",
            "`std::map` is ordered and backed by a Red-Black Tree (O(log n)); `std::unordered_map` is backed by a Hash Table (O(1) average)", "`std::map` uses hash tables", "`std::unordered_map` is sorted", "No difference", "A",
            "`std::map` maintains key order with O(log n) operations; `std::unordered_map` trades order for O(1) speed.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What algorithm from `<algorithm>` performs an in-place sort on a random-access container?",
            "`std::sort(begin, end)`", "`std::order()`", "`std::bsearch()`", "`std::filter()`", "A",
            "`std::sort` typically implements Introsort (quicksort + heapsort + insertion sort) with O(n log n) guarantee.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What container adapter provides LIFO semantics in C++ STL?",
            "`std::stack`", "`std::queue`", "`std::deque`", "`std::vector`", "A",
            "`std::stack` adapts underlying sequences (default `std::deque`) to provide Last-In First-Out operations.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What is `std::lower_bound` in C++ STL?",
            "Returns an iterator pointing to the first element that is not less than (i.e. greater than or equal to) the given value in a sorted range", "Returns the smallest element in array", "Returns the floor index", "Performs linear search", "A",
            "`std::lower_bound` uses binary search on sorted ranges in O(log n) time.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What is `std::priority_queue` in C++ and what is its default heap type?",
            "A max-heap container adapter using `std::vector` and `std::less` by default", "A min-heap by default", "A sorted linked list", "A binary search tree", "A",
            "The top of `std::priority_queue` returns the greatest element by default (max-heap).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What does `push_back()` vs `emplace_back()` do in `std::vector`?",
            "`emplace_back()` constructs the element in-place directly in vector storage, avoiding unnecessary copies or moves", "`push_back()` is faster", "`emplace_back()` only accepts primitives", "They are identical aliases", "A",
            "`emplace_back` forwards arguments directly to the constructor in-place using variadic templates.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What is the time complexity of searching in `std::set` in C++?",
            "O(log n)", "O(1)", "O(n)", "O(n log n)", "A",
            "`std::set` is backed by a balanced binary search tree (Red-Black tree) with logarithmic lookups.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What is `std::pair` and `std::tuple` in C++ STL?",
            "Heterogeneous collections of 2 (`std::pair`) or arbitrary N (`std::tuple`) elements", "Containers for only integers", "Array wrappers", "Hash maps", "A",
            "Utility templates that bundle heterogeneous values without requiring custom struct declarations.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What happens to iterators when a `std::vector` reallocates capacity during insertion?",
            "All existing iterators, pointers, and references to elements are invalidated", "They point to the new location automatically", "Nothing happens", "Throws an exception", "A",
            "Vector reallocation moves memory blocks, making all stale pointers and iterators invalid dangling references.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What is the difference between `std::deque` and `std::vector` in C++?",
            "`std::deque` provides O(1) push and pop at both front and back using chunked arrays; vector only at back", "Vector provides fast push_front", "Deque is contiguous in one single memory block", "Deque cannot be indexed", "A",
            "`std::deque` allocates multiple fixed-size blocks connected via a central map pointer.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "STL",
            "What does `std::unique(begin, end)` do?",
            "Removes consecutive duplicate elements in a range and returns an iterator to the new logical end (requires `erase` to resize)", "Sorts the container", "Filters out odd numbers", "Clears the container", "A",
            "`std::unique` shifts non-duplicate elements left; the 'erase-remove' idiom completes physical deletion.", Difficulty.MEDIUM, 3));
    }

    private static void addCppPointers(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What does the dereference operator `*` do on a pointer `int* p`?",
            "Accesses or modifies the value stored at the memory address pointed to by `p`", "Returns the address of p", "Multiplies p by 2", "Deletes the memory", "A",
            "`*p` dereferences the pointer to retrieve the target variable's content.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is a dangling pointer in C++?",
            "A pointer pointing to a memory location that has already been deallocated / freed", "A pointer initialized to nullptr", "A pointer to a function", "A void pointer", "A",
            "Accessing memory via a dangling pointer leads to undefined behavior and memory corruption.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is `nullptr` in modern C++ (C++11), and why is it preferred over `NULL` or 0?",
            "`nullptr` is a strongly-typed pointer literal of type `std::nullptr_t` that avoids overload ambiguity with integer 0", "`nullptr` is just a macro for 0", "`nullptr` allocates heap space", "No difference", "A",
            "`nullptr` cannot be implicitly cast to integer types, preventing wrong overload resolution.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is the difference between `const int* p` and `int* const p`?",
            "`const int* p` points to a constant integer (value cannot change); `int* const p` is a constant pointer (address cannot change)", "They are identical", "Both make pointer and value constant", "Neither makes anything constant", "A",
            "Read pointer declarations right-to-left: const before `*` means pointed data is const; const after `*` means pointer itself is const.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is pointer arithmetic on `int* ptr` when executing `ptr + 1`?",
            "Advances the address by `sizeof(int)` bytes (typically 4 bytes)", "Advances the address by 1 byte", "Increments the value pointed to by 1", "Causes a syntax error", "A",
            "Pointer arithmetic scales increments by the byte size of the underlying target data type.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is a `void*` (void pointer) in C++?",
            "A generic pointer that can hold the address of any data type, but cannot be directly dereferenced without casting", "A pointer that points to nothing", "A null pointer", "A function pointer", "A",
            "Void pointers lack type information, requiring explicit static_cast before dereferencing.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is a pointer to a pointer (`int** ptr`) used for?",
            "Storing the memory address of another pointer variable", "Multiplying two pointers", "Pointing to an array of integers", "Invalid syntax", "A",
            "Double pointers are used to modify pointer values passed into functions or for 2D dynamic arrays.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is a function pointer in C++?",
            "A pointer storing the starting memory address of executable code for a function", "A pointer returned by a function", "A pointer that calls itself", "A pointer to an inline class", "A",
            "Function pointers (`void (*fp)(int)`) allow passing functions as callbacks.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is the difference between a pointer and a reference in C++?",
            "A pointer can be reassigned and be null; a reference must be initialized on declaration and cannot be reseated or null", "References use more memory", "Pointers cannot access members", "They are identical at compile time", "A",
            "References provide safe aliases guaranteed to refer to valid objects without null checks.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is a wild pointer in C++?",
            "An uninitialized pointer pointing to an arbitrary, unknown memory address", "A pointer initialized to nullptr", "A smart pointer", "A pointer to a virtual function", "A",
            "Wild pointers hold garbage stack addresses until explicitly assigned.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What does the arrow operator `ptr->member` do in C++?",
            "Shorthand for dereferencing the pointer and accessing the member: `(*ptr).member`", "Assigns member to ptr", "Compares ptr with member", "Shifts bits right", "A",
            "`->` combines pointer dereference and field selection into one clean operator.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Pointers",
            "What is memory alignment in pointer access?",
            "Arranging data in memory at addresses that are multiples of the data word size for fast hardware CPU retrieval", "Sorting memory addresses", "Compressing RAM", "Clearing unused pointers", "A",
            "Unaligned pointer access forces CPUs to perform multiple memory bus reads or triggers hardware faults.", Difficulty.HARD, 4));
    }

    private static void addCppMemoryManagement(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What is the difference between `new` / `delete` and `malloc()` / `free()` in C++?",
            "`new` allocates memory and calls constructors; `delete` calls destructors and frees memory; `malloc`/`free` only allocate raw bytes without constructor/destructor execution", "malloc is type-safe", "new cannot fail", "They are identical", "A",
            "`new` and `delete` integrate fully with C++ object life cycle and type safety.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What operator must be used to deallocate an array allocated with `int* arr = new int[10];`?",
            "`delete[] arr;`", "`delete arr;`", "`free(arr);`", "`remove arr;`", "A",
            "Calling scalar `delete` instead of array `delete[]` leads to undefined behavior and skips destructors.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What is RAII (Resource Acquisition Is Initialization) in C++?",
            "A paradigm where resource lifetime (memory, file handles, locks) is tied to object lifetime and automatically cleaned up in destructors", "Initializing all variables to 0", "Allocating all memory at program start", "A garbage collection algorithm", "A",
            "RAII ensures deterministic exception-safe cleanup when stack objects go out of scope.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What is `std::unique_ptr` in C++11?",
            "A smart pointer that owns and manages another object exclusively and disposes of that object when the `unique_ptr` goes out of scope", "A pointer that can be copied infinitely", "A reference counter", "A raw pointer", "A",
            "`std::unique_ptr` enforces single ownership semantics with zero runtime overhead over raw pointers.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What is `std::shared_ptr` and how does it manage resource lifetimes?",
            "It maintains a reference count; the managed object is destroyed when the last `shared_ptr` is destroyed or reset", "It uses mark-and-sweep garbage collection", "It only allows one owner", "It never frees memory", "A",
            "`std::shared_ptr` tracks co-owners using a control block containing the reference counter.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What problem does `std::weak_ptr` solve in C++?",
            "Breaks circular reference cycles between `std::shared_ptr` instances that would otherwise cause memory leaks", "Speeds up unique_ptr", "Allocates uninitialized memory", "Prevents stack overflow", "A",
            "A weak pointer observes an object without incrementing its strong reference count.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "Why is `std::make_unique` and `std::make_shared` preferred over calling `new` directly?",
            "Ensures exception safety and `make_shared` combines object and control block into a single memory allocation", "It uses less CPU", "It makes pointers global", "Raw new is deprecated", "A",
            "`make_shared` performs one allocation for both object and control block, reducing fragmentation.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What is a memory leak in C++?",
            "Failure to deallocate dynamically allocated heap memory after it is no longer reachable by the program", "Exceeding stack memory", "Reading past array bounds", "A segmentation fault", "A",
            "Leaked memory cannot be reclaimed until process termination, degrading system performance.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What happens if `new` fails to allocate memory in C++?",
            "It throws a `std::bad_alloc` exception by default", "It returns NULL always", "It terminates the operating system", "It hangs the computer", "A",
            "Standard `new` throws `std::bad_alloc` unless `new (std::nothrow)` is explicitly requested.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What is placement new in C++?",
            "Constructs an object in a pre-allocated memory buffer: `new (buffer) ClassName(args)`", "Allocates memory on the GPU", "A multithreaded allocator", "Replaces delete", "A",
            "Placement new avoids allocation overhead by constructing objects into user-supplied memory buffers.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What is move semantics in C++11, and how does it prevent unnecessary memory copies?",
            "Transfers ownership of heap resources from rvalue temporary objects without copying deep memory (`std::move`)", "Deletes objects faster", "Compresses pointers", "A multithreading feature", "A",
            "Move constructors pilfer internal pointers of rvalues, converting expensive O(n) copies to O(1) pointer swaps.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Memory Management",
            "What is a memory fragmentation problem in long-running C++ applications?",
            "Free heap memory is broken into many small, non-contiguous blocks, preventing allocation of large contiguous blocks", "Memory corruption by pointers", "CPU cache invalidation", "Virtual memory overflow", "A",
            "Frequent allocations and deallocations leave holes in heap address space, causing allocation failures.", Difficulty.HARD, 4));
    }

    private static void addCppExceptions(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What keywords are used for exception handling in C++?",
            "`try`, `catch`, and `throw`", "`try`, `catch`, `finally`", "`begin`, `rescue`, `ensure`", "`throws`, `except`", "A",
            "C++ uses try, catch, and throw; unlike Java, C++ does not have a `finally` keyword (relying on RAII instead).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What is the base class for standard exceptions in C++?",
            "`std::exception`", "`std::error`", "`std::throwable`", "`std::fault`", "A",
            "All standard library exceptions (e.g. `std::runtime_error`, `std::out_of_range`) derive from `std::exception`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What method of `std::exception` returns the explanatory string description of the error?",
            "`what()`", "`getMessage()`", "`description()`", "`toString()`", "A",
            "The virtual member function `const char* what() const noexcept` returns error details.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What is the catch-all exception handler syntax in C++?",
            "`catch (...)`", "`catch (all)`", "`catch (std::any)`", "`catch (Exception e)`", "A",
            "`catch (...)` catches any unhandled exception of any type.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What is stack unwinding in C++ during exception propagation?",
            "The process of destroying all local automatic objects by calling their destructors in reverse order of construction as the stack is exited", "Emptying the function call stack without cleanup", "Reallocating memory", "Terminating threads", "A",
            "Stack unwinding ensures RAII destructors run deterministically until a matching catch block is found.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "Why is throwing an exception from a destructor strongly discouraged in C++?",
            "If an exception is thrown while another exception is already unwinding the stack, `std::terminate` is immediately called", "Destructors cannot contain throw statements", "It causes compiler error", "Memory will not be freed", "A",
            "Two concurrent active exceptions trigger immediate process termination by the C++ runtime.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What does the `noexcept` specifier in C++11 declare?",
            "Guarantees that a function will not throw any exceptions, enabling compiler optimizations like move operations in vector", "Function throws all exceptions", "Disables try-catch in function", "Function cannot return", "A",
            "Marking functions `noexcept` allows STL containers like `std::vector` to safely move elements instead of copying.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "Why should exceptions in C++ be caught by reference (`catch (const std::exception& e)`)?",
            "To prevent object slicing and avoid unnecessary copy overhead", "To modify the exception object", "Pointers are not allowed in catch", "Compiler requirement", "A",
            "Catching by value slices derived exception classes into base classes, discarding specialized diagnostic information.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What happens if an exception is thrown and not caught anywhere in a C++ program?",
            "`std::terminate()` is called, which calls `std::abort()` to terminate the process", "The program ignores the error and continues", "Operating system handles it", "Returns 0 to shell", "A",
            "Uncaught exceptions invoke `std::terminate` to abort execution immediately.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "How do you rethrow the current active exception inside a catch block?",
            "`throw;`", "`throw e;`", "`rethrow;`", "`throw current;`", "A",
            "An empty `throw;` statement rethrows the exact original exception preserving its polymorphic dynamic type.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What exception is thrown by `std::vector::at(index)` when the index is out of bounds?",
            "`std::out_of_range`", "`std::bad_alloc`", "`std::invalid_argument`", "`std::range_error`", "A",
            "`vector::at()` provides bounds checking and throws `std::out_of_range` on violations.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "C++", "Exception Handling",
            "What is the basic exception safety guarantee in C++?",
            "If an exception occurs, no resources are leaked and all invariants remain valid, though state may change", "State remains exactly unchanged (Strong)", "No exceptions can ever be thrown (Nothrow)", "No guarantees", "A",
            "Basic guarantee prevents leaks and undefined states; Strong guarantee provides transaction rollback semantics.", Difficulty.HARD, 4));
    }

    // ==================== PYTHON ====================

    private static void addPythonBasics(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "Is Python an interpreted, dynamically-typed language?",
            "Yes, Python code is compiled to bytecode and interpreted, and variable types are checked at runtime", "No, it is purely statically compiled", "No, it requires manual type declaration", "It is only an assembly language", "A",
            "Python dynamically infers types and runs on the CPython virtual machine interpreter.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What is PEP 8 in Python?",
            "The official Style Guide for Python Code", "A Python package manager", "A Python compiler", "A debugging tool", "A",
            "PEP 8 outlines standard formatting conventions (indentation, naming, whitespace) for Python code.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "How is code block indentation structured in Python?",
            "Whitespace indentation (typically 4 spaces) defines scope, replacing curly braces {}", "Curly braces {}", "BEGIN and END keywords", "Semicolons", "A",
            "Python enforces semantic block indentation using whitespace.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What is the difference between `is` and `==` in Python?",
            "`is` checks identity (same memory address `id()`); `==` checks equality of values", "`is` checks value; `==` checks memory", "They are completely interchangeable", "`is` is only for strings", "A",
            "`is` tests object identity (`id(a) == id(b)`), while `==` calls `__eq__()` for value equality.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What does the Global Interpreter Lock (GIL) in CPython do?",
            "A mutex that protects access to Python objects, preventing multiple native threads from executing Python bytecode simultaneously", "Locks the keyboard", "Prevents file editing", "A security encryption feature", "A",
            "CPython's GIL restricts pure Python thread execution to a single CPU core at any instant.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What is the output of `type([])` and `type({})` in Python?",
            "`<class 'list'>` and `<class 'dict'>`", "`<class 'array'>` and `<class 'set'>`", "`list` and `set`", "`array` and `map`", "A",
            "`[]` creates a list; empty `{}` creates a dictionary (empty set requires `set()`).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What does `pass` statement do in Python?",
            "A null operation; acts as a syntactical placeholder where code is required but no action is needed", "Terminates the program", "Skips to next loop iteration", "Returns None from function", "A",
            "`pass` satisfies syntax requirements in empty functions, classes, or loops.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What is the result of integer division `7 // 2` in Python 3?",
            "3", "3.5", "4", "3.0", "A",
            "`//` performs floor division; `7 / 2` yields `3.5` float division.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What are Python's docstrings?",
            "String literals occurring as the first statement in a module, function, or class used for documentation (`__doc__`)", "Single line comments with #", "Compiled binary documentation", "Type annotations only", "A",
            "Docstrings (enclosed in triple quotes) are accessible via the `help()` function and `__doc__` attribute.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What is slicing syntax `arr[::-1]` used for in Python?",
            "Reversing the sequence", "Sorting the sequence", "Removing the first element", "Copying elements at odd indices", "A",
            "`sequence[start:stop:step]` with step `-1` reverses strings, lists, or tuples.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "What does `__name__ == '__main__'` signify in a Python script?",
            "Checks whether the script is being executed directly or imported as a module into another script", "Checks if Python is installed", "Validates syntax", "Imports standard libraries", "A",
            "Top-level code executed directly receives `'__main__'` as its `__name__` attribute.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Basics",
            "How does Python manage memory and clean up unused objects?",
            "Reference counting combined with a cyclic garbage collector (generational)", "Manual free() calls", "Pure mark-and-sweep only", "Stack memory only", "A",
            "Objects are freed immediately when refcount reaches zero; cyclic reference detector handles reference cycles.", Difficulty.MEDIUM, 3));
    }

    private static void addPythonDataTypes(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "Which of the following data types in Python is mutable?",
            "List", "Tuple", "String", "Frozenset", "A",
            "Lists and dictionaries are mutable; tuples, strings, and frozensets are immutable.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "Can a Python dictionary have a list as a key?",
            "No, because dictionary keys must be hashable and lists are unhashable mutable objects", "Yes, any object can be a key", "Yes, up to 10 elements", "Only if list is sorted", "A",
            "Dictionary keys must implement `__hash__` and be immutable (e.g. tuples, strings, ints).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What is a set in Python?",
            "An unordered collection of unique, hashable elements", "An ordered list with duplicates", "A key-value pair map", "An immutable array", "A",
            "Sets do not allow duplicate items and support mathematical union, intersection, and difference.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What is list comprehension in Python?",
            "A concise, readable syntax for generating new lists from existing iterables: `[expr for item in iterable if cond]`", "A method for sorting lists", "A function that merges lists", "A type of linked list", "A",
            "List comprehensions replace verbose `for` append loops with expressive inline syntax.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What is the difference between shallow copy and deep copy in Python?",
            "Shallow copy copies the outer object but references inner nested objects; deep copy recursively duplicates all nested objects", "Shallow copy duplicates everything", "Deep copy creates references only", "No difference", "A",
            "The `copy` module provides `copy.copy()` (shallow) and `copy.deepcopy()` (full recursive cloning).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What does the dictionary method `dict.get(key, default)` do?",
            "Returns the value for the key if present, otherwise returns the specified default value without raising KeyError", "Deletes the key", "Adds the key if missing", "Throws an exception on missing key", "A",
            "`get()` provides safe dictionary lookups avoiding `KeyError` crashes.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What is the output of `bool('')`, `bool([])`, and `bool(0)` in Python?",
            "False, False, False", "True, True, True", "False, True, False", "None, None, None", "A",
            "Empty sequences, empty mappings, zero values, and None are considered 'falsy' in Python boolean evaluation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What is a tuple with a single element declared as in Python?",
            "`(element,)` (with a trailing comma)", "`(element)`", "`[element]`", "`tuple(element)`", "A",
            "Without the trailing comma, `(5)` is interpreted as a parenthesized integer arithmetic expression.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What does `zip()` function do in Python?",
            "Aggregates elements from multiple iterables into tuples pairwise: `[(a1, b1), (a2, b2)...]`", "Compresses files into a .zip archive", "Sorts elements", "Concatenates lists", "A",
            "`zip()` pairs corresponding items from iterables until the shortest iterable is exhausted.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What is `collections.defaultdict` in Python?",
            "A dictionary subclass that returns a default value via a callable factory function when an unmapped key is accessed", "A read-only dictionary", "A sorted dictionary", "A dictionary of fixed size", "A",
            "`defaultdict(list)` eliminates manual checking and key initialization when grouping data.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What is `collections.Counter` in Python?",
            "A dict subclass designed for counting hashable elements, tracking element frequencies automatically", "A stopwatch timer", "A thread lock counter", "A loop index", "A",
            "`Counter('abracadabra')` computes character or token frequency distributions cleanly.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Data Types",
            "What is the time complexity of element membership testing (`x in s`) in a Python set vs a list?",
            "O(1) in set, O(n) in list", "O(n) in both", "O(log n) in set, O(1) in list", "O(1) in both", "A",
            "Sets use hash tables offering O(1) average lookup; lists require O(n) linear scans.", Difficulty.EASY, 2));
    }

    private static void addPythonFunctions(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What is the difference between `*args` and `**kwargs` in Python function definitions?",
            "`*args` collects positional arguments into a tuple; `**kwargs` collects keyword arguments into a dictionary", "`*args` is for dictionaries", "`**kwargs` is for lists", "They are identical", "A",
            "`*args` handles variable positional arguments, while `**kwargs` captures arbitrary named parameters.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What is a lambda function in Python?",
            "An anonymous inline function defined using the `lambda` keyword with single-expression body", "A recursive function", "A function defined inside C", "A generator function", "A",
            "Syntax `lambda x: x * 2` creates lightweight anonymous functions commonly used with map/filter/sort.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What is a generator function in Python, and how does it differ from a regular function?",
            "It uses `yield` instead of `return` to produce values lazily on-demand, preserving execution state across calls", "It generates random numbers", "It runs in parallel threads", "It compiles to machine code", "A",
            "`yield` pauses function execution and emits values sequentially without loading full lists into RAM.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What is a decorator in Python?",
            "A callable that takes another function as an argument, extends its behavior without modifying it, and returns a function", "A graphical UI element", "A CSS style in Python", "A class destructor", "A",
            "Decorators (using `@decorator_name` syntax) implement the decorator pattern cleanly via metaprogramming.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "Why is using a mutable default argument (like `def func(lst=[]):`) considered dangerous in Python?",
            "The default list is created once at function definition time, so mutations persist across subsequent invocations", "Causes a syntax error", "Memory is immediately freed", "It is converted to a tuple", "A",
            "Persistent mutable default arguments cause subtle state leakage bugs; use `lst=None` instead.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What is a closure in Python?",
            "An inner function that retains access to variables from its enclosing lexical scope even after the outer function has returned", "Closing a file stream", "Ending a loop", "A class destructor", "A",
            "Closures bundle inner functions with captured enclosing scope bindings.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What does the `map()` function do in Python?",
            "Applies a specified function to all items in an input iterable and returns an iterator", "Creates a dictionary", "Draws a geographic map", "Filters elements", "A",
            "`map(func, iterable)` applies transformation logic over sequence elements lazily.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What does the `filter()` function do in Python?",
            "Constructs an iterator from elements of an iterable for which a boolean function returns True", "Removes duplicate elements", "Sorts items", "Reverses a list", "A",
            "`filter(predicate, iterable)` retains items that evaluate to truthy against the predicate.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What is the LEGB rule for variable scope resolution in Python?",
            "Local, Enclosing, Global, Built-in", "List, Element, Group, Block", "Literal, Expression, Generic, Base", "Loop, Execution, Garbage, Bytecode", "A",
            "Python resolves name references hierarchically from Local outward to Built-in scopes.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What do the `global` and `nonlocal` keywords do in Python?",
            "`global` modifies variables in module scope; `nonlocal` modifies variables in nearest outer enclosing scope", "`global` makes variables local", "`nonlocal` creates class fields", "They are identical", "A",
            "`nonlocal` binds to enclosing closure variables, while `global` binds to the module's top-level namespace.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What is the purpose of `functools.lru_cache` decorator?",
            "Wraps a function with a memoizing callable that saves the results of expensive calls up to a maxsize limit", "Cleans cache memory", "Deletes unused functions", "Logs function calls to disk", "A",
            "`lru_cache` automates dynamic programming memoization with Least Recently Used eviction.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Functions",
            "What is recursion depth limit in Python, and how is it checked?",
            "CPython enforces a default limit (typically 1000) checked and configured via `sys.getrecursionlimit()`", "Unlimited", "Fixed at 100", "Limited only by hard disk size", "A",
            "Recursion limits prevent C stack overflow segfaults from infinite recursion bugs.", Difficulty.EASY, 2));
    }

    private static void addPythonOop(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What is `self` in Python class methods?",
            "An explicit reference to the current instance of the class invoking the method", "A global pointer", "A keyword that cannot be renamed", "A reference to the parent class", "A",
            "Python passes the calling instance as the first argument to instance methods conventionally named `self`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What is the purpose of the `__init__` method in a Python class?",
            "The constructor/initializer method called automatically when a new object instance is created", "Destructor method", "Static block", "Class method decorator", "A",
            "`__init__` initializes instance attributes upon object instantiation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "How is inheritance declared in Python?",
            "`class Child(Parent):`", "`class Child extends Parent:`", "`class Child : public Parent`", "`class Child implements Parent:`", "A",
            "Derived classes pass parent class names in parentheses during class definition.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What is the difference between `@classmethod` and `@staticmethod` in Python?",
            "`@classmethod` receives the class (`cls`) as first argument; `@staticmethod` receives neither `self` nor `cls`", "`@staticmethod` receives instance `self`", "They are identical", "`@classmethod` cannot be called on instances", "A",
            "Class methods can access and mutate class state; static methods are pure utility functions namespace-bound to the class.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What is Method Resolution Order (MRO) in Python, and what algorithm calculates it?",
            "The order in which Python searches base classes for methods in multiple inheritance, calculated using the C3 Linearization algorithm", "Depth-first search only", "Breadth-first search only", "Random order", "A",
            "C3 linearization guarantees monotonicity and consistent subclass ordering in complex inheritance hierarchies.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "How does Python achieve private member encapsulation by convention?",
            "Prefixing attribute names with a double underscore `__name` triggers name mangling (`_ClassName__name`)", "Using `private` keyword", "Using encrypted variables", "Python enforces strict access controls natively", "A",
            "Python relies on community convention (`_single_leading_underscore`) and name mangling (`__double`).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What is the purpose of the `@property` decorator in Python?",
            "Allows a method to be accessed like an attribute, providing getter/setter encapsulation cleanly", "Makes the variable static", "Stores values in databases", "Encrypts fields", "A",
            "`@property` provides idiomatic getter, setter, and deleter encapsulation without breaking public attribute access APIs.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What do dunder methods `__str__` and `__repr__` do?",
            "`__str__` provides human-readable output for users; `__repr__` provides unambiguous representation for developers and debugging", "They are identical", "`__repr__` is called by print()", "`__str__` returns memory addresses", "A",
            "`__str__` is informal; `__repr__` should ideally be valid Python code capable of recreating the object.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What does `super()` do in Python?",
            "Returns a proxy object that delegates method calls to a parent or sibling class in the MRO", "Calls the top-level Object class", "Terminates inheritance", "Creates a superuser instance", "A",
            "`super()` facilitates cooperative multiple inheritance by invoking the next class in the MRO.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What is duck typing in Python?",
            "'If it walks like a duck and quacks like a duck, it's a duck' — object suitability is determined by presence of methods/attributes, not explicit inheritance", "Strict type checking", "Casting to duck classes", "A compile-time verification", "A",
            "Duck typing focuses on what operations an object supports rather than its formal type hierarchy.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What is the purpose of `__slots__` in a Python class?",
            "Restricts attribute creation to a predefined set, eliminating `__dict__` and significantly saving memory per instance", "Defines slot machine games", "Enables multithreading", "Allows dynamic methods", "A",
            "`__slots__` replaces per-instance dynamic dictionaries with fixed compact arrays.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "OOP",
            "What is an Abstract Base Class (ABC) in Python, and how is it implemented?",
            "Using the `abc` module and `@abstractmethod` decorator; cannot be instantiated if abstract methods are unimplemented", "Using `abstract class` keywords", "All classes in Python are abstract", "Using interfaces", "A",
            "`from abc import ABC, abstractmethod` enforces interface compliance on derived classes.", Difficulty.MEDIUM, 3));
    }

    private static void addPythonExceptions(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "What is the standard block structure for handling exceptions in Python?",
            "`try`, `except`, `else`, `finally`", "`try`, `catch`, `finally`", "`begin`, `rescue`, `ensure`", "`try`, `except`, `then`", "A",
            "Python uses `try-except`, with optional `else` (runs if no exception) and `finally` (always runs).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "When does the `else` block execute in a Python `try-except-else-finally` construct?",
            "Only when the `try` block executes successfully without raising any exceptions", "Whenever an exception is caught", "Always before finally", "Only if finally fails", "A",
            "The `else` clause isolates code that should run only if the protected try body completed cleanly.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "What is the base class for all non-system-exiting exceptions in Python?",
            "`Exception`", "`BaseException`", "`StandardError`", "`RuntimeError`", "A",
            "`Exception` is the parent of all application errors; `BaseException` includes `KeyboardInterrupt` and `SystemExit`.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "What exception is raised when accessing a dictionary with a key that does not exist?",
            "`KeyError`", "`IndexError`", "`ValueError`", "`AttributeError`", "A",
            "Direct indexing `d[missing_key]` raises `KeyError`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "What exception is raised when attempting to access an invalid index of a list?",
            "`IndexError`", "`KeyError`", "`LookupError`", "`ValueError`", "A",
            "Referencing list indices beyond `len(lst) - 1` raises `IndexError`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "How do you manually raise an exception in Python?",
            "`raise ValueError('Invalid argument')`", "`throw ValueError()`", "`emit Exception()`", "`assert False` only", "A",
            "The `raise` keyword triggers explicit exception instantiation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "What is exception chaining in Python 3 using `from`?",
            "`raise CustomError() from original_error` explicitly indicates cause and links tracebacks", "Catches exceptions in chain", "Rethrows in a loop", "Combines two exceptions into a list", "A",
            "`from` establishes the `__cause__` attribute, preserving original stack context during exception translation.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "How do you define a custom exception class in Python?",
            "Create a class that inherits from `Exception`", "Inherit from `object`", "Create a function returning error", "Annotate class with @error", "A",
            "User-defined exceptions subclass `Exception` (e.g. `class MyError(Exception): pass`).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "What is the purpose of the `assert` statement in Python?",
            "Tests an internal condition for debugging; raises `AssertionError` if expression evaluates to False", "Handles exceptions", "Terminates thread", "Logs errors to a database", "A",
            "`assert condition, message` helps developers sanity-check invariants during testing.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "Why is a bare `except:` block considered bad practice in Python?",
            "It intercepts system-exiting signals like `KeyboardInterrupt` (Ctrl+C) and `SystemExit`, making programs hard to stop", "It causes syntax error", "It runs slower", "It ignores finally", "A",
            "Always catch specific exceptions or `except Exception:` to allow system signals to propagate.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "What happens if an exception is raised inside the `finally` block?",
            "It replaces and discards any previous exception or return value from the `try` block", "Both exceptions are merged", "The program crashes immediately", "It is ignored", "A",
            "Exceptions in finally supersede prior active exceptions, obscuring original root causes.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "Exception Handling",
            "What exception is raised when passing an argument of correct type but inappropriate value (e.g. `int('abc')`)?",
            "`ValueError`", "`TypeError`", "`KeyError`", "`SyntaxError`", "A",
            "`ValueError` is raised when operations receive arguments of right type but invalid value.", Difficulty.EASY, 2));
    }

    private static void addPythonFileHandling(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "Why is using the `with open(...)` context manager preferred for file handling in Python?",
            "It guarantees that the file stream is automatically closed when execution leaves the block, even if exceptions occur", "It opens files faster", "It compresses files", "It encrypts file content", "A",
            "`with` statements leverage context managers (`__enter__` / `__exit__`) for deterministic resource cleanup.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What is the difference between file modes `'w'` and `'a'` in Python?",
            "`'w'` truncates (overwrites) existing content or creates a new file; `'a'` appends new content to the end of the file", "`'a'` overwrites; `'w'` appends", "`'w'` is read-only", "No difference", "A",
            "Mode `'w'` resets file length to 0 upon opening; `'a'` places the write cursor at end-of-file.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What is the difference between `read()`, `readline()`, and `readlines()` in Python?",
            "`read()` reads entire file as a string; `readline()` reads one line; `readlines()` reads all lines into a list of strings", "They are identical", "`readline()` reads all lines", "`read()` reads one character", "A",
            "`read()` ingests entire stream; `readline()` processes incrementally; `readlines()` buffers all lines as a list.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What module in Python is used for object serialization and deserialization (pickling)?",
            "`pickle`", "`json`", "`marshal`", "`serialize`", "A",
            "The `pickle` module converts arbitrary Python object hierarchies into byte streams.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "How do you read and parse a JSON file in Python?",
            "`json.load(file_object)`", "`json.loads(file_object)`", "`json.parse(file_object)`", "`json.read(file_object)`", "A",
            "`json.load()` parses file streams; `json.loads()` parses string variables.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What does the `file.seek(offset, whence)` method do?",
            "Moves the file read/write pointer to a specified byte position", "Searches for text in file", "Sorts lines in file", "Deletes bytes", "A",
            "`seek()` adjusts the file offset for random access reading and writing.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What is the modern object-oriented library for filesystem path manipulation in Python 3.4+?",
            "`pathlib.Path`", "`os.path`", "`sys.path`", "`filepath`", "A",
            "`pathlib` provides intuitive, cross-platform path manipulation semantics over legacy `os.path` strings.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What file open mode is required to read binary files (like images or executables) in Python?",
            "`'rb'`", "`'r'`", "`'wb'`", "`'b'`", "A",
            "Appended `'b'` denotes binary mode, returning bytes instead of decoded strings.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "How can you iterate through a large multi-gigabyte text file line-by-line without exhausting RAM?",
            "`for line in file_object:` iterates lazily using a generator/buffer without loading the entire file into memory", "Call `read()` all at once", "Call `readlines()`", "Convert to list first", "A",
            "File objects in Python act as lazy line iterators reading buffered chunks from disk.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What does `file.flush()` do?",
            "Forces writing buffered data in internal memory buffers to disk immediately", "Clears file contents", "Closes the file", "Deletes the file", "A",
            "`flush()` synchronizes runtime memory buffers with underlying operating system file descriptors.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What standard module provides functions for reading and writing CSV files?",
            "`csv`", "`pandas`", "`tabulate`", "`excel`", "A",
            "Python includes a built-in `csv` module with `csv.reader` and `csv.DictWriter`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Python", "File Handling",
            "What exception is raised when trying to open a non-existent file in read mode?",
            "`FileNotFoundError`", "`FileExistsError`", "`PermissionError`", "`IOError`", "A",
            "Opening a missing file with mode `'r'` raises `FileNotFoundError`.", Difficulty.EASY, 2));
    }
}
