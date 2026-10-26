package com.techprep.config;

import com.techprep.entity.Difficulty;
import java.util.List;

public class TechnicalTopUpPart2 {
    private static final String TCH = "Technical";

    public static void populate(List<QuestionTopUpSeeder.QSpec> list) {
        // JavaScript (5 topics)
        addJsBasics(list);
        addJsFunctions(list);
        addJsArrays(list);
        addJsObjects(list);
        addJsEs6(list);

        // Java Backend (6 topics)
        addJavaBackendSpring(list);
        addJavaBackendSpringBoot(list);
        addJavaBackendRestApi(list);
        addJavaBackendSecurity(list);
        addJavaBackendJpa(list);
        addJavaBackendHibernate(list);

        // Database (4 topics)
        addDbSql(list);
        addDbDbms(list);
        addDbMysql(list);
        addDbPostgresql(list);

        // CS Fundamentals (5 topics)
        addCsOs(list);
        addCsNetworks(list);
        addCsSoftwareEngineering(list);
        addCsGit(list);
        addCsSystemDesign(list);
    }

    // ==================== JAVASCRIPT ====================

    private static void addJsBasics(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is the difference between `let`, `const`, and `var` in JavaScript?",
            "`var` is function-scoped and hoisted; `let` and `const` are block-scoped; `const` cannot be reassigned", "`let` is globally scoped only", "`const` variables can be reassigned", "They are identical", "A",
            "ES6 `let` and `const` avoid variable leaking and hoisting quirks of `var`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is the difference between `==` and `===` in JavaScript?",
            "`==` performs type coercion before comparison; `===` checks both value and type strictly", "`===` converts types", "They are identical", "`==` is only for strings", "A",
            "`===` avoids implicit type coercion (e.g. `1 === '1'` is false, whereas `1 == '1'` is true).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is the output of `typeof null` in JavaScript?",
            "'object' (a historical bug in JS)", "'null'", "'undefined'", "'boolean'", "A",
            "`typeof null === 'object'` is a legacy bug preserved for backward compatibility.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What are the primitive data types in JavaScript?",
            "string, number, bigint, boolean, undefined, symbol, null", "object, array, function", "int, float, char", "class, interface, struct", "A",
            "JavaScript has 7 primitive types that are immutable and passed by value.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is hoisting in JavaScript?",
            "Variable and function declarations are moved to the top of their containing scope during compilation", "Calling functions asynchronously", "Garbage collection", "Converting variables to strings", "A",
            "Declarations are hoisted; assignments remain in place, causing `var` to initialize to undefined.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is the Temporal Dead Zone (TDZ)?",
            "The time between entering scope and variable declaration where accessing `let` or `const` throws ReferenceError", "Time waiting for server response", "Garbage collection cycle", "Event loop idle time", "A",
            "Variables declared with `let`/`const` cannot be accessed before their declaration statement.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is `NaN` in JavaScript, and what is `typeof NaN`?",
            "'Not-a-Number', but its type is 'number'", "'undefined'", "'string'", "'boolean'", "A",
            "`NaN` represents an invalid mathematical operation result, yet belongs to the numeric type.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "How do you check if a value is `NaN` reliably in JavaScript?",
            "`Number.isNaN(value)`", "`value === NaN`", "`value == NaN`", "`typeof value === 'NaN'`", "A",
            "`NaN === NaN` evaluates to false, so `Number.isNaN()` is required for accurate checking.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is the event loop in JavaScript?",
            "A concurrency mechanism coordinating synchronous call stack execution with asynchronous callback and microtask queues", "A while(true) loop running scripts", "A DOM animation timer", "A garbage collector", "A",
            "The event loop executes macrotasks and microtasks whenever the synchronous call stack is empty.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is the output of `[] + []` and `[] + {}` in JavaScript?",
            "'' and '[object Object]'", "[] and {}", "undefined and NaN", "TypeError", "A",
            "Array addition coerces operands to strings: `'' + '' = ''` and `'' + '[object Object]'`.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What are falsy values in JavaScript?",
            "`false`, `0`, `-0`, `0n`, `''`, `null`, `undefined`, and `NaN`", "Empty arrays `[]` and empty objects `{}`", "Negative numbers", "Strings with spaces", "A",
            "Everything else in JavaScript evaluates to truthy, including empty arrays `[]` and objects `{}`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Basics",
            "What is strict mode in JavaScript, enabled via `'use strict;'`?",
            "Enforces stricter parsing and error handling, preventing undeclared variables and silent errors", "Makes code run multithreaded", "Minifies code automatically", "Disables all functions", "A",
            "Strict mode eliminates silent errors, forbids duplicate parameter names, and secures `this`.", Difficulty.EASY, 2));
    }

    private static void addJsFunctions(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is a closure in JavaScript?",
            "A function bundled with references to its surrounding lexical environment", "A self-closing HTML tag", "A function that has finished executing", "An anonymous function", "A",
            "Closures grant inner functions access to outer function variables even after the outer scope has returned.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "How do arrow functions `() => {}` differ from regular functions regarding `this`?",
            "Arrow functions do not have their own `this`; they inherit `this` lexically from the enclosing scope", "Arrow functions bind this to window", "Arrow functions cannot access this", "No difference", "A",
            "Arrow functions retain the `this` value of their enclosing execution context without dynamic binding.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is an IIFE (Immediately Invoked Function Expression)?",
            "A function that runs as soon as it is defined: `(function() { ... })();`", "An asynchronous function", "A recursive function", "A constructor", "A",
            "IIFEs create an isolated local lexical scope to avoid polluting the global namespace.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is the difference between `call()`, `apply()`, and `bind()`?",
            "`call` invokes with comma arguments; `apply` with an array; `bind` returns a new function with bound `this`", "`bind` invokes immediately", "`apply` only works on objects", "They are identical", "A",
            "`call` and `apply` execute immediately with custom context; `bind` returns a callable wrapper.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is a higher-order function in JavaScript?",
            "A function that accepts other functions as arguments or returns a function", "A recursive function", "A function with high CPU usage", "A method of window", "A",
            "`map`, `filter`, and `reduce` are classic examples of higher-order functions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is currying in JavaScript?",
            "Transforming a function with multiple arguments into a sequence of functions that each take a single argument", "Calling a function repeatedly", "Merging two functions", "Deleting unused arguments", "A",
            "`f(a, b, c)` is curried into `f(a)(b)(c)`.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "Can arrow functions be used as constructors with `new`?",
            "No, arrow functions lack `[[Construct]]` internal method and have no `prototype` property", "Yes, just like regular functions", "Only with classes", "Only if capitalized", "A",
            "Invoking `new (() => {})` throws a `TypeError`.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is function debouncing?",
            "Delaying execution until a specified time has elapsed since the last time the function was invoked", "Executing immediately on every event", "Running functions in parallel", "Canceling functions permanently", "A",
            "Debouncing (e.g. search autocomplete) waits for user typing to pause before making API calls.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is function throttling?",
            "Limiting execution frequency to at most once in a specified time interval", "Delaying the initial call", "Running code on background threads", "Preventing memory leaks", "A",
            "Throttling (e.g. scroll or resize events) guarantees steady, capped execution rates.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What does the `arguments` object in regular functions contain?",
            "An array-like object containing all arguments passed to the function", "A true JavaScript Array", "A dictionary of keyword arguments", "Global parameters", "A",
            "`arguments` has length and index properties but lacks array prototype methods like `map`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is a pure function?",
            "A function that returns the same output for the same inputs and produces no observable side effects", "A function with no arguments", "A function returning void", "An anonymous function", "A",
            "Pure functions are deterministic, easy to test, and enable memoization.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Functions",
            "What is a callback function?",
            "A function passed into another function as an argument to be executed at a later time", "A function that calls itself", "A function stored in a database", "An error handler only", "A",
            "Callbacks provide asynchronous notification upon completion of I/O or background events.", Difficulty.EASY, 2));
    }

    private static void addJsArrays(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What does `Array.prototype.map()` return?",
            "A new array containing the results of calling a provided function on every element in the calling array", "The modified original array", "A boolean", "A single aggregated value", "A",
            "`map()` transforms elements immutably and returns a brand-new array of identical length.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What is the difference between `splice()` and `slice()` in JavaScript?",
            "`splice()` mutates the original array by removing/adding elements; `slice()` returns a shallow copy of a portion without mutating", "`slice()` mutates; `splice()` does not", "They are identical", "`splice()` only works on strings", "A",
            "`splice` alters the source array in place; `slice` extracts subarrays non-destructively.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What does `reduce()` do in JavaScript arrays?",
            "Executes a user-supplied reducer callback on each element, resulting in a single cumulative output value", "Sorts the array descending", "Removes duplicate elements", "Filters out nulls", "A",
            "`reduce(callback, initialValue)` accumulates running totals or aggregates structures.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What does `find()` vs `filter()` do?",
            "`find()` returns the first element satisfying the test; `filter()` returns an array of all matching elements", "`find()` returns all matches", "`filter()` returns only one", "No difference", "A",
            "`find()` short-circuits upon the first match; `filter()` evaluates the entire sequence.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "How does `Array.prototype.sort()` behave by default without a comparator function?",
            "Converts elements to strings and sorts them according to UTF-16 character code values", "Sorts numbers in ascending numeric order", "Sorts descending", "Throws an error on mixed types", "A",
            "Without a comparator, `[10, 2].sort()` produces `[10, 2]` because `'10' < '2'` lexicographically.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What does `Array.isArray(obj)` check?",
            "Whether the passed value is a true Array object across execution contexts (iframes)", "Whether obj has length property", "Whether obj is iterable", "Whether obj is not null", "A",
            "`Array.isArray()` safely identifies arrays even across distinct browser frames and windows.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What does `flat(depth)` do on an array?",
            "Creates a new array with all sub-array elements concatenated recursively up to the specified depth", "Flattens objects into strings", "Sorts arrays", "Removes empty slots only", "A",
            "`[1, [2, [3]]].flat(2)` produces `[1, 2, 3]`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What do `some()` and `every()` return?",
            "Booleans indicating if at least one (`some`) or all (`every`) elements pass the predicate test", "Arrays of matching items", "Indices of matches", "Numbers", "A",
            "`some()` and `every()` test logical existential and universal quantifications over arrays.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What is the difference between `push()`/`pop()` and `unshift()`/`shift()`?",
            "`push`/`pop` add/remove at the end (O(1)); `unshift`/`shift` add/remove at the beginning (O(n))", "`unshift`/`shift` operate at end", "They are identical", "`push` removes from start", "A",
            "Modifying the start of an array requires shifting all subsequent indices in memory (O(n)).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "How do you remove duplicates from an array using modern JavaScript?",
            "`[...new Set(array)]`", "`array.unique()`", "`array.filterDuplicates()`", "`new Array(array)`", "A",
            "Passing an array to `Set` removes duplicates, and spreading back restores an array.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What does `indexOf()` return if the target element is not found?",
            "-1", "null", "undefined", "false", "A",
            "`indexOf` returns the index of the first occurrence, or -1 if the element is absent.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Arrays",
            "What does `fill(value, start, end)` do?",
            "Fills all array elements in the specified index range with a static value", "Replaces nulls with zeros", "Expands array length", "Empties the array", "A",
            "`Array(5).fill(0)` creates an array of five zeros.", Difficulty.EASY, 2));
    }

    private static void addJsObjects(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "How does prototypal inheritance work in JavaScript?",
            "Objects have an internal `[[Prototype]]` link (`__proto__`); property lookups traverse up the prototype chain until found or null", "Classes copy all methods into each instance", "Objects use C++ virtual tables", "Inheritance is not supported", "A",
            "JavaScript objects inherit properties and methods dynamically through their prototype link chain.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What do `Object.keys()`, `Object.values()`, and `Object.entries()` return?",
            "Arrays of an object's own enumerable property names, values, and `[key, value]` pairs respectively", "Iterators only", "Strings", "Sets", "A",
            "These static Object methods extract arrays of property metadata from enumerable own properties.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What does `Object.freeze()` do?",
            "Prevents adding, removing, or modifying properties and values on an object (makes it shallowly immutable)", "Deletes the object", "Encrypts the object", "Clones the object", "A",
            "`Object.freeze` makes an object shallowly immutable; nested objects remain mutable unless frozen individually.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What is the difference between `Object.freeze()` and `Object.seal()`?",
            "`freeze()` prevents modification of values; `seal()` allows modification of existing properties but prevents adding or deleting properties", "`seal` is deeper than freeze", "They are identical", "`freeze` allows deletions", "A",
            "`seal()` marks existing properties non-configurable while allowing value updates.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What does `Object.assign(target, ...sources)` do?",
            "Copies all enumerable own properties from source objects to a target object and returns the target", "Compares two objects", "Freezes sources", "Clones prototypes", "A",
            "`Object.assign` performs shallow property copying and merging.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What is object destructuring in JavaScript?",
            "Unpacking properties from objects into distinct variables: `const { name, age } = user;`", "Deleting object properties", "Destroying objects in memory", "Stringifying objects", "A",
            "Destructuring syntax provides shorthand variable binding directly from matching object property keys.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What does `hasOwnProperty()` check on an object?",
            "Whether the object has the specified property as its own direct property rather than inherited via prototype", "Whether property is defined", "Whether property is a function", "Whether object is frozen", "A",
            "`hasOwnProperty()` excludes prototype chain properties from property checks.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What does `Object.create(proto)` do?",
            "Creates a new object with the specified prototype object and optional property descriptors", "Clones an object deeply", "Freezes an object", "Creates an empty array", "A",
            "`Object.create` sets up pure prototypal delegation without calling constructor functions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What are getters and setters in JavaScript objects?",
            "Accessor properties bound to methods executed when a property is read or assigned (`get prop()`, `set prop(val)`)", "Normal functions only", "Private variables", "Database triggers", "A",
            "Accessors disguise function execution behind natural property dot-syntax.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "How can you deeply clone an object natively in modern JavaScript?",
            "`structuredClone(obj)`", "`JSON.parse(JSON.stringify(obj))` (which loses functions/dates)", "`Object.assign({}, obj)`", "`{...obj}`", "A",
            "The standard `structuredClone()` handles cycles, Dates, Sets, Maps, and ArrayBuffers correctly.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What does the `delete` operator do on an object?",
            "Removes a property from an object: `delete obj.property`", "Deletes the entire object from memory", "Sets value to null", "Sets value to 0", "A",
            "`delete` unbinds a property key from an object; it does not directly free memory (GC handles that).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "Objects",
            "What is a JavaScript `Proxy` object?",
            "An object wrapping another object to intercept and redefine fundamental operations (getting, setting, enumeration)", "A network proxy", "A WebSocket wrapper", "A database client", "A",
            "`Proxy` allows custom trap handlers for property lookup, assignment, invocation, and meta-programming.", Difficulty.HARD, 4));
    }

    private static void addJsEs6(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What is the spread operator (`...`) vs rest parameter in ES6?",
            "Spread expands an iterable into individual elements; rest collects multiple elements into an array", "Spread creates arrays; rest creates objects", "They are identical", "Rest is used in strings only", "A",
            "`...` spreads values in calls/literals and gathers remaining values into arrays in function signatures.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What are Template Literals in ES6?",
            "Strings delimited with backticks (``) supporting multi-line strings and interpolation (`${expr}`)", "Strings inside double quotes", "HTML strings", "SQL templates", "A",
            "Template literals simplify string interpolation and multiline formatting without concatenation operators.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What are the three states of a JavaScript Promise?",
            "Pending, Fulfilled, Rejected", "Active, Inactive, Terminated", "Started, Running, Completed", "Open, Closed, Reconnecting", "A",
            "Promises represent asynchronous computations transitioning from pending to either fulfilled or rejected.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What does `async/await` do in ES2017?",
            "Syntactic sugar over Promises allowing asynchronous code to be written and read like synchronous code", "Runs code on multiple CPU threads", "Replaces the event loop", "Compiles JavaScript to C", "A",
            "`await` pauses execution of the async function until the awaited Promise settles.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What does `Promise.all()` do vs `Promise.allSettled()`?",
            "`Promise.all` rejects immediately if any promise fails; `Promise.allSettled` waits for all promises to finish regardless of outcome", "`allSettled` fails on first error", "`Promise.all` runs sequentially", "They are identical", "A",
            "`Promise.all` fails fast; `Promise.allSettled` returns the status and value/reason for every input promise.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What is Optional Chaining (`?.`) introduced in ES2020?",
            "Allows reading nested property values without throwing TypeError if an intermediate reference is null or undefined", "Makes arguments optional", "Creates optional functions", "A regex operator", "A",
            "`user?.address?.street` returns `undefined` safely rather than crashing if `address` is null.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What is the Nullish Coalescing Operator (`??`) in ES2020?",
            "Returns right-hand operand only if left-hand operand is null or undefined (unlike `||` which checks all falsy values like 0 or '')", "Identical to logical OR ||", "Performs null checks on functions", "A ternary shorthand", "A",
            "`0 ?? 42` returns `0`, whereas `0 || 42` returns `42`.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What is `Map` vs plain `Object` in ES6?",
            "`Map` allows keys of any type (including objects/functions), maintains insertion order, and has a `.size` property", "`Object` is faster for all operations", "`Map` only accepts string keys", "No difference", "A",
            "`Map` is optimized for frequent key additions/removals and supports arbitrary key types.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What is `WeakMap` in JavaScript?",
            "A collection of key-value pairs where keys must be objects and are held weakly (garbage collected if no other references exist)", "A Map that can hold only 10 items", "A thread-unsafe Map", "An encrypted Map", "A",
            "WeakMaps prevent memory leaks by allowing keys to be reclaimed when unreachable elsewhere.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What is a `Symbol` in ES6?",
            "A unique and immutable primitive data type often used as identifier keys for private object properties", "A character icon", "A math operator", "A string alias", "A",
            "Every `Symbol('desc')` call returns a distinct, collision-free token.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What is an ES6 Module (ESM), and how is code imported/exported?",
            "Standard module system using `export` and `import` statements, evaluated statically at compile-time with strict mode enabled", "Using `require()` and `module.exports` (CommonJS)", "Using script tags only", "Using eval()", "A",
            "ESM supports static analysis, tree-shaking, and asynchronous top-level loading.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "JavaScript", "ES6+",
            "What are generator functions (`function*`) and the `yield` keyword in ES6?",
            "Functions that can be paused and resumed, yielding an IteratorResult object with `{ value, done }`", "Functions that generate random numbers", "Async functions without promises", "Functions with no return value", "A",
            "Generators enable iterative state machines and custom sequence generators.", Difficulty.MEDIUM, 3));
    }

    // ==================== JAVA BACKEND ====================

    private static void addJavaBackendSpring(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What is Inversion of Control (IoC) in the Spring Framework?",
            "Transferring the control of object creation and lifecycle management from application code to the Spring IoC Container", "Writing code without interfaces", "Inverting method parameters", "A database design pattern", "A",
            "The container instantiates, configures, and wires application objects via Dependency Injection.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What are the three common ways to perform Dependency Injection in Spring?",
            "Constructor injection, Setter injection, and Field injection (`@Autowired`)", "Method injection, static injection, file injection", "Database injection, REST injection, SOAP injection", "XML injection only", "A",
            "Constructor injection is officially recommended for immutability and testability.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What is the default scope of a Spring bean?",
            "Singleton", "Prototype", "Request", "Session", "A",
            "Spring creates a single shared instance of a singleton bean per ApplicationContext container.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What is the difference between `@Component`, `@Service`, and `@Repository` annotations in Spring?",
            "They are stereotype annotations; `@Repository` adds exception translation, `@Service` holds business logic, and `@Component` is the generic stereotype", "They behave completely differently in runtime", "Only @Component creates a bean", "They are obsolete", "A",
            "All three register Spring beans, but specialized stereotypes clarify architecture and add specific post-processing.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What is Spring AOP (Aspect-Oriented Programming) used for?",
            "Separating cross-cutting concerns (logging, security, transaction management) from core business logic using aspects and join points", "Compiling Java to bytecode", "Writing multithreaded loops", "Generating HTML views", "A",
            "AOP injects modular behavior around methods via dynamic proxies without cluttering core domain classes.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What is the difference between Bean scopes 'singleton' and 'prototype' in Spring?",
            "Singleton creates one shared instance per container; prototype creates a new instance every time the bean is requested", "Prototype creates one instance per JVM", "Singleton cannot have fields", "They are identical", "A",
            "Prototype beans are instantiated afresh on each injection or `getBean()` request.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What does the `@Primary` annotation do in Spring?",
            "Gives preference to a bean when multiple beans of the same type are candidates for autowiring", "Makes the bean run first", "Marks bean as non-singleton", "Restricts bean to test profile", "A",
            "When autowiring by type is ambiguous, `@Primary` selects the preferred default bean.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What does `@Qualifier` annotation do in Spring?",
            "Differentiates and specifies exactly which bean name to inject when multiple beans of the same type exist", "Validates bean parameters", "Marks bean as prototype", "Encrypts bean fields", "A",
            "`@Qualifier(\"specificBeanName\")` resolves autowiring ambiguity with explicit name binding.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What are the `@PostConstruct` and `@PreDestroy` annotations used for?",
            "Lifecycle callbacks executed right after bean dependency initialization and right before container destruction", "Scheduling background tasks", "Database transaction management", "REST endpoint routing", "A",
            "These lifecycle hooks allow custom setup and cleanup operations during bean lifecycle transitions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What is the `ApplicationContext` in Spring?",
            "The central IoC container interface providing bean factory features, event publication, i18n, and environment properties", "A servlet container", "A database connection pool", "A classloader", "A",
            "`ApplicationContext` extends `BeanFactory` with enterprise features.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "How does Spring handle circular dependencies when using constructor injection?",
            "Throws a `BeanCurrentlyInCreationException` at startup because neither bean can be constructed first", "Resolves it automatically", "Initializes one with null", "Ignores the second bean", "A",
            "Constructor injection strictly forbids circular loops; setter or field injection with `@Lazy` is required if unavoidable.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring",
            "What does `@Value` annotation in Spring do?",
            "Injects values from properties files, system environment variables, or SpEL expressions into fields", "Validates field values", "Calculates hash code", "Marks field as constant", "A",
            "`@Value(\"${server.port}\")` dynamically binds configuration property values.", Difficulty.EASY, 2));
    }

    private static void addJavaBackendSpringBoot(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What is the main goal of Spring Boot over standard Spring Framework?",
            "Simplifies Spring development with convention-over-configuration, auto-configuration, embedded servers, and starter dependencies", "Makes Java run without a JVM", "Replaces Spring Framework completely", "Only for desktop apps", "A",
            "Spring Boot eliminates XML and complex configuration to enable rapid production-ready microservices.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What three annotations are combined into `@SpringBootApplication`?",
            "`@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`", "`@Component`, `@Service`, `@Repository`", "`@Controller`, `@ResponseBody`, `@RequestMapping`", "`@Entity`, `@Table`, `@Id`", "A",
            "`@SpringBootApplication` enables component scanning, auto-configuration, and Java-based bean setup.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What are Spring Boot Starter dependencies (e.g. `spring-boot-starter-web`)?",
            "Pre-packaged dependency descriptors that bundle compatible libraries and transitive dependencies for specific capabilities", "Scripts that run on OS boot", "Code generators", "Maven plugins only", "A",
            "Starters provide curated, tested sets of library dependencies with managed version compatibility.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What is Spring Boot Auto-Configuration?",
            "Automatically configures Spring beans based on the JAR dependencies present on the classpath", "Compiles code while typing", "Automatically writes SQL queries", "Provisions cloud servers", "A",
            "For example, seeing H2 on the classpath automatically configures an in-memory datasource.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What is Spring Boot Actuator used for?",
            "Provides production-ready monitoring and management endpoints (`/actuator/health`, `/metrics`, `/info`)", "Compiles code ahead-of-time", "Acts as a database driver", "Encrypts passwords", "A",
            "Actuator exposes HTTP and JMX endpoints for operational telemetry and health auditing.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What embedded servlet containers does Spring Boot support by default?",
            "Tomcat (default), Jetty, and Undertow", "WebLogic and WebSphere", "Apache HTTP Server and Nginx", "None, it requires external WAR deployment", "A",
            "Spring Boot packages web applications with embedded Tomcat into self-contained executable JARs.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What is the priority order of configuration between `application.yml` and `application.properties`?",
            "`application.properties` takes precedence if both exist in the same location, but using one consistently is standard practice", "YAML always overrides properties", "They are merged with error", "Command-line args are ignored", "A",
            "Both formats configure the Environment; properties files take precedence when co-located.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What does the `@Profile` annotation do in Spring Boot?",
            "Conditionally activates beans or configurations based on the active runtime profile (e.g. 'dev', 'test', 'prod')", "Sets user permissions", "Profiles CPU performance", "Restricts memory usage", "A",
            "`@Profile(\"prod\")` ensures specific beans or properties are only loaded in production environments.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What is the `CommandLineRunner` interface in Spring Boot?",
            "A functional interface whose `run()` method executes once after the Spring application context has fully loaded", "Runs commands in the terminal", "A CLI parser library", "Stops the server on command", "A",
            "Commonly used to seed test data or initialize cache state on application startup.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What does `@ConditionalOnProperty` do in Spring Boot?",
            "Loads a bean or configuration class only if a specified configuration property is present and matches a value", "Validates properties", "Encrypts properties", "Deletes properties at runtime", "A",
            "Enables dynamic toggling of features based on `application.properties` flags.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "How does Spring Boot support externalized configuration?",
            "Via application.properties, environment variables, command-line arguments, and cloud configuration servers in a defined hierarchy", "Hardcoded Java constants only", "XML files only", "Database tables only", "A",
            "Allows the same application artifact to run in different environments without code modifications.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Boot",
            "What is Spring Boot DevTools?",
            "A developer module providing automatic server restarts, live reload, and disabled template caching for fast development iteration", "A unit testing framework", "An IDE plugin", "A production monitoring tool", "A",
            "DevTools speeds up the edit-compile-test cycle by automatically restarting the context upon class file changes.", Difficulty.EASY, 2));
    }

    private static void addJavaBackendRestApi(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What is the difference between `@Controller` and `@RestController` in Spring?",
            "`@RestController` combines `@Controller` and `@ResponseBody`, serializing return values directly into JSON/XML HTTP responses", "`@RestController` only serves HTML", "`@Controller` cannot handle GET requests", "No difference", "A",
            "`@RestController` eliminates writing `@ResponseBody` on every REST endpoint method.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What is the difference between PUT and PATCH HTTP methods?",
            "PUT replaces the entire resource representation; PATCH applies partial modifications to an existing resource", "PUT creates; PATCH deletes", "PUT is non-idempotent; PATCH is idempotent", "They are identical", "A",
            "PUT semantics require sending the full resource payload; PATCH accepts delta changes.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What is an idempotent HTTP method?",
            "A method where making multiple identical requests has the exact same side-effect on the server as making a single request (e.g. GET, PUT, DELETE)", "A method that cannot fail", "A method that runs in 1ms", "A method with no payload", "A",
            "GET, PUT, and DELETE are idempotent; POST is non-idempotent because repeated calls create duplicate records.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What HTTP status code should be returned when a resource is successfully created via POST?",
            "201 Created", "200 OK", "204 No Content", "202 Accepted", "A",
            "201 Created signifies that a new resource was successfully generated, ideally paired with a Location header.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What is the difference between `@PathVariable` and `@RequestParam` in Spring MVC?",
            "`@PathVariable` extracts values from URI path segments (`/users/{id}`); `@RequestParam` extracts query parameters (`/users?id=1`)", "`@PathVariable` is for query strings", "They are interchangeable", "`@RequestParam` only works in POST", "A",
            "`@PathVariable` binds to URI template variables; `@RequestParam` extracts URL query parameters.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "How do you handle global exceptions in Spring REST APIs?",
            "Using `@ControllerAdvice` or `@RestControllerAdvice` with `@ExceptionHandler` methods returning standard error responses", "Wrapping every controller method in try-catch", "Letting server return 500 error page", "Using web.xml error pages", "A",
            "`@RestControllerAdvice` provides centralized, uniform exception handling and structured error DTO formatting across all controllers.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What does the `@RequestBody` annotation do in Spring?",
            "Deserializes the HTTP request body JSON into a Java object using HttpMessageConverters (Jackson)", "Extracts query parameters", "Sends data to the client", "Validates HTTP headers", "A",
            "Jackson maps the incoming JSON payload into the declared target DTO parameter.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What is CORS (Cross-Origin Resource Sharing), and how is it enabled in Spring Boot?",
            "A browser security mechanism restricting cross-origin requests; enabled using `@CrossOrigin` or `WebMvcConfigurer`", "An encryption protocol", "A database connection tool", "A JSON parser", "A",
            "CORS headers tell browsers that a web app running on origin A is allowed to access resources on origin B.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What HTTP status code should be returned when a requested resource does not exist?",
            "404 Not Found", "400 Bad Request", "500 Internal Server Error", "403 Forbidden", "A",
            "404 Not Found indicates that the origin server did not find a current representation for the target resource.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What HTTP status code is used for validation failure on client input?",
            "400 Bad Request (or 422 Unprocessable Entity)", "401 Unauthorized", "500 Internal Server Error", "403 Forbidden", "A",
            "400 Bad Request signals malformed syntax or failed input validation constraints.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What is the purpose of the `@Valid` or `@Validated` annotation in Spring?",
            "Triggers Jakarta / Hibernate Bean Validation constraints (e.g. `@NotNull`, `@Size`, `@Email`) on incoming DTOs", "Validates SQL queries", "Checks user passwords", "Validates JWT tokens", "A",
            "Spring validates annotated DTO fields and binds violation errors to a `BindingResult` or throws `MethodArgumentNotValidException`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "REST API",
            "What is HATEOAS in RESTful architecture?",
            "Hypermedia As The Engine Of Application State: including dynamic hypermedia links in responses guiding clients on available next actions", "A serialization library", "A security token standard", "A caching algorithm", "A",
            "HATEOAS decouples clients by discovering actions dynamically through resource links.", Difficulty.HARD, 4));
    }

    private static void addJavaBackendSecurity(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What is the difference between Authentication and Authorization?",
            "Authentication verifies who you are (identity); Authorization determines what resources/actions you are allowed to access (permissions)", "Authentication checks permissions; Authorization checks identity", "They are synonyms", "Authorization happens first", "A",
            "Authentication validates credentials; Authorization enforces role/privilege access boundaries.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "How does Spring Security process web requests internally?",
            "Through a chain of servlet filters called the `SecurityFilterChain` managed by `DelegatingFilterProxy`", "Using database triggers", "Using Java compiler hooks", "Through AOP aspects only", "A",
            "Requests pass through sequential security filters (CSRF, Authentication, Authorization) before hitting controllers.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What are the three parts of a JSON Web Token (JWT)?",
            "Header, Payload, and Signature separated by dots (`.`): `header.payload.signature`", "Username, Password, Role", "Key, Value, Timestamp", "Domain, Token, Expiry", "A",
            "Header specifies algorithm; Payload holds claims; Signature ensures tamper-proofing with a secret key.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "Why is JWT considered stateless for authentication?",
            "The token contains all necessary user claims and signature self-contained, so the server does not need to store session state in memory", "Tokens never expire", "Tokens cannot be verified", "Tokens are stored in cookies only", "A",
            "Stateless tokens facilitate horizontal scaling across distributed microservice instances without session replication.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "Why should passwords NEVER be stored in plain text or MD5/SHA-1 in a database?",
            "Plaintext and fast unsalted hashes are vulnerable to leaks and rainbow table attacks; slow salted algorithms like BCrypt must be used", "Databases cannot store strings", "Plaintext passwords take too much disk space", "SQL syntax prohibits it", "A",
            "BCrypt incorporates random salts and configurable work factors to thwart brute-force and rainbow table attacks.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What is CSRF (Cross-Site Request Forgery), and why can it typically be disabled for stateless JWT APIs?",
            "CSRF tricks authenticated browsers into submitting unauthorized actions; stateless APIs using Authorization headers (not ambient cookies) are immune", "CSRF is a database virus", "CSRF encrypts tokens", "It should never be disabled", "A",
            "Since JWTs are stored in app memory/headers rather than auto-attached ambient cookies, CSRF attacks cannot execute.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What is the `SecurityContextHolder` in Spring Security?",
            "The central storage location holding the `SecurityContext`, which contains the `Authentication` token of the current thread", "A database table", "An HTTP session cookie", "A login form HTML template", "A",
            "Uses ThreadLocal storage by default to associate security identity with the active request execution thread.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What does the `@PreAuthorize` annotation do in Spring Security?",
            "Enforces method-level access control based on SpEL expressions before executing the method (`@PreAuthorize(\"hasRole('ADMIN')\")`)", "Validates method parameters", "Logs method calls", "Authenticates user credentials", "A",
            "Method security evaluates authorization expressions prior to invoking service/controller methods.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What is the difference between HTTP 401 Unauthorized and 403 Forbidden?",
            "401 means authentication is required or failed; 403 means authenticated user lacks necessary permissions/roles", "401 means forbidden; 403 means login required", "They are identical", "401 is a server crash", "A",
            "401 prompts for credentials; 403 refuses access even though the identity is known.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What is OAuth2?",
            "An open authorization protocol allowing third-party applications to obtain limited access to user resources via tokens without exposing passwords", "A password hashing algorithm", "A single-sign-on database", "A firewall hardware protocol", "A",
            "OAuth2 delegates authorization through standard flows (e.g. Authorization Code Flow).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What is the `PasswordEncoder` interface in Spring Security?",
            "Service interface for one-way password transformation and comparison (`encode()` and `matches()`)", "A password generator", "A decryption tool", "A database validator", "A",
            "`BCryptPasswordEncoder` implements `PasswordEncoder` to secure user credential storage.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Spring Security",
            "What is role-based access control (RBAC) in Spring Security?",
            "Restricting system access to authorized users based on assigned roles (e.g. `ROLE_STUDENT`, `ROLE_ADMIN`)", "Allowing all users access", "Authenticating with biometrics", "Dynamic IP filtering", "A",
            "RBAC organizes fine-grained authorities into intuitive, manageable domain roles.", Difficulty.EASY, 2));
    }

    private static void addJavaBackendJpa(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is JPA (Jakarta Persistence API)?",
            "A standard Java specification for Object-Relational Mapping (ORM) and relational data persistence management", "A database software like MySQL", "A JDBC driver", "A frontend framework", "A",
            "JPA provides specifications and annotations; Hibernate is its most popular implementation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is the difference between JPA and Hibernate?",
            "JPA is a specification (interfaces and metadata rules); Hibernate is a concrete ORM library implementing JPA", "JPA is an implementation of Hibernate", "Hibernate is only for NoSQL", "They are identical", "A",
            "JPA defines the standard ORM contract; Hibernate provides the functional engine.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What are the four states of an entity in the JPA entity lifecycle?",
            "New (Transient), Managed (Persistent), Detached, and Removed", "Active, Inactive, Closed, Deleted", "Created, Read, Updated, Deleted", "Pending, Approved, Rejected, Archived", "A",
            "JPA tracks entities through distinct lifecycle transitions relative to the active EntityManager persistence context.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is Spring Data JPA?",
            "An abstraction layer built on top of JPA that dramatically reduces boilerplate by generating repository implementations from interfaces", "A new SQL database", "A replacement for JDBC drivers", "A caching engine", "A",
            "`JpaRepository` derives SQL queries automatically from method naming conventions (`findByTopicIdAndActiveTrue`).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is the N+1 select problem in JPA/Hibernate?",
            "Executing 1 initial query to fetch N entities, followed by N separate queries to fetch lazily-loaded related entities", "An infinite loop bug", "Selecting N+1 columns in a table", "A database deadlock", "A",
            "N+1 query cascades degrade performance; resolved using `JOIN FETCH` or `@EntityGraph`.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is the difference between `FetchType.LAZY` and `FetchType.EAGER` in JPA?",
            "`LAZY` loads related entities on-demand when accessed; `EAGER` loads related entities immediately in the initial query", "`EAGER` is faster for all queries", "`LAZY` causes immediate OutOfMemory", "No difference", "A",
            "LAZY fetching avoids hauling unnecessary object graphs into memory until explicitly traversed.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What does the `@Transactional` annotation in Spring do?",
            "Wraps the method execution in a database transaction, committing on success and rolling back on unhandled runtime exceptions", "Logs SQL execution", "Runs queries in parallel", "Makes method thread-safe in memory only", "A",
            "`@Transactional` ensures atomic ACID compliance across multiple repository invocations.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is JPQL (Java Persistence Query Language)?",
            "A database-independent query language operating on entity objects and their fields rather than database tables and columns", "Standard SQL only", "JSON query syntax", "NoSQL language", "A",
            "`SELECT q FROM Question q WHERE q.active = true` targets domain classes, translating to vendor SQL.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is the difference between `findById()` and `getReferenceById()` in Spring Data JPA?",
            "`findById()` queries the database immediately and returns an Optional; `getReferenceById()` returns a lazy proxy without immediate DB hit", "`getReferenceById` always hits the database", "They are identical", "`findById` throws exception if missing", "A",
            "`getReferenceById()` avoids unnecessary SELECT queries when assigning foreign keys to new entities.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is CascadeType in JPA entity relationships?",
            "Defines which entity state transitions (PERSIST, MERGE, REMOVE, REFRESH, ALL) are propagated from parent to child entities", "Cascades CSS styles", "Sorts query results", "Creates foreign keys", "A",
            "`cascade = CascadeType.ALL` automatically saves or deletes child entities when the parent entity is saved or deleted.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What is the `@Embedded` and `@Embeddable` annotation pair in JPA?",
            "`@Embeddable` declares a value type class; `@Embedded` embeds its fields directly as columns in the containing entity table", "Creates foreign key tables", "Encrypts column values", "Creates relationships", "A",
            "Value objects (e.g. `Address`) share the database row of the parent entity without dedicated identity tables.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "JPA",
            "What causes a `LazyInitializationException` in JPA/Hibernate?",
            "Accessing a lazily-loaded relationship or collection after the persistence context (Session/Transaction) has closed", "Database is offline", "Lazy collection is empty", "Invalid SQL syntax", "A",
            "Accessing unloaded proxies outside a live transaction context causes Hibernate to throw this exception.", Difficulty.HARD, 4));
    }

    private static void addJavaBackendHibernate(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is the First-Level Cache in Hibernate?",
            "Session-scoped cache enabled by default that stores entity instances to avoid duplicate SELECT queries within the same transaction", "A global distributed Redis cache", "A browser cache", "An operating system RAM cache", "A",
            "The First-Level cache operates within the boundaries of the active Hibernate Session.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is the Second-Level Cache in Hibernate?",
            "An optional, configurable SessionFactory-scoped cache (e.g. Ehcache, Hazelcast) shared across all sessions and transactions", "The First-Level Cache backup", "A client browser cache", "A MySQL query cache", "A",
            "Second-Level cache survives across sessions to minimize database roundtrips for read-heavy domain data.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is 'dirty checking' in Hibernate?",
            "Automatic detection of entity state modifications during a transaction, triggering UPDATE queries automatically on commit", "Scanning database for viruses", "Checking for syntax errors", "Validating null values", "A",
            "Hibernate snapshots entity state upon loading and compares snapshots at flush time, issuing UPDATEs automatically.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What are the primary generator strategies for `@GeneratedValue` in Hibernate?",
            "AUTO, IDENTITY, SEQUENCE, and TABLE", "UUID only", "RANDOM and HASH", "INCREMENT only", "A",
            "IDENTITY relies on database auto-increment columns; SEQUENCE leverages database sequence objects.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is the difference between `session.get()` and `session.load()` in Hibernate?",
            "`get()` hits database immediately and returns null if not found; `load()` returns a lazy proxy and throws ObjectNotFoundException on access if missing", "`load()` is always faster", "They are identical", "`get()` throws exception", "A",
            "`get()` performs eager database reads; `load()` creates proxies assuming the entity exists.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What does `hibernate.hbm2ddl.auto = update` do?",
            "Updates the database schema structure automatically based on entity definitions without dropping tables", "Drops and recreates tables on every run (`create`)", "Validates schema only (`validate`)", "Does nothing", "A",
            "`update` adds new columns and tables automatically while preserving existing table data.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is optimistic locking in Hibernate, and how is it enabled?",
            "Uses a `@Version` field (integer or timestamp) to detect conflicting concurrent updates, throwing `OptimisticLockException` on collision", "Uses database row-level pessimistic locks (SELECT FOR UPDATE)", "Locks entire tables", "Disables transactions", "A",
            "Optimistic locking avoids locking database rows by checking version counters during UPDATE WHERE version=expected.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is pessimistic locking in Hibernate?",
            "Explicitly locks database rows at the database level using `SELECT ... FOR UPDATE` via `LockModeType.PESSIMISTIC_WRITE`", "Uses version fields", "Disables database locks", "Caches rows in memory", "A",
            "Pessimistic locking prevents concurrent access at the database level for high-contention operations.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is the purpose of the Hibernate Criteria API?",
            "A programmatic, type-safe API for building dynamic queries without writing string-based HQL/JPQL queries", "A database backup tool", "A benchmarking tool", "A schema generator", "A",
            "The Criteria API facilitates assembling complex dynamic filters and search queries in pure Java code.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is the SessionFactory in Hibernate?",
            "A thread-safe, heavyweight factory object created once per application that configures and opens lightweight `Session` instances", "A database connection", "A single transaction", "An entity class", "A",
            "SessionFactory holds global metadata, mappings, and second-level cache configurations.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What does `orphanRemoval = true` in `@OneToMany` do?",
            "Automatically deletes child entity rows from the database when removed from the parent entity's collection", "Cascades save operations only", "Prevents deleting children", "Creates orphan tables", "A",
            "`orphanRemoval` cleans up disconnected child rows when they are dereferenced from the parent collection.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Java Backend", "Hibernate",
            "What is the difference between `merge()` and `persist()` in Hibernate?",
            "`persist()` takes a transient entity and makes it persistent; `merge()` copies state of a detached entity into a managed instance", "`persist()` updates existing rows", "They are identical", "`merge()` cannot insert new rows", "A",
            "`persist()` is for new records; `merge()` reattaches detached state by returning a managed copy.", Difficulty.HARD, 4));
    }

    // ==================== DATABASE ====================

    private static void addDbSql(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What is the difference between WHERE and HAVING clauses in SQL?",
            "`WHERE` filters rows before aggregation; `HAVING` filters groups after `GROUP BY` aggregation", "`HAVING` filters rows before grouping", "They are interchangeable", "`WHERE` only works with numbers", "A",
            "`WHERE` filters individual records; `HAVING` filters aggregate calculations (`HAVING COUNT(*) > 5`).", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What are the differences between INNER JOIN, LEFT JOIN, and RIGHT JOIN?",
            "INNER returns matching rows from both; LEFT returns all from left + matching right; RIGHT returns all from right + matching left", "LEFT returns only non-matching rows", "INNER returns all rows from both tables", "No difference", "A",
            "Outer joins preserve rows from one side even when no corresponding match exists in the other.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What is the difference between UNION and UNION ALL in SQL?",
            "`UNION` removes duplicate rows from combined result sets; `UNION ALL` retains all rows including duplicates and is faster", "`UNION ALL` removes duplicates", "They are identical", "`UNION` cannot combine two tables", "A",
            "`UNION` incurs an extra sorting/deduplication step; `UNION ALL` concatenates streams directly.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What is the difference between DROP, TRUNCATE, and DELETE in SQL?",
            "`DROP` removes table and schema; `TRUNCATE` rapidly removes all rows resetting identity (DDL); `DELETE` removes rows conditionally (DML)", "`TRUNCATE` removes schema", "`DELETE` cannot use WHERE clause", "They are identical", "A",
            "`DELETE` logs individual row deletions; `TRUNCATE` deallocates data pages directly.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "How do you find the second highest salary in an Employee table?",
            "`SELECT MAX(salary) FROM Employee WHERE salary < (SELECT MAX(salary) FROM Employee);`", "`SELECT salary FROM Employee ORDER BY salary LIMIT 2;`", "`SELECT SECOND(salary) FROM Employee;`", "`SELECT salary[2] FROM Employee;`", "A",
            "Standard nested query filters out the top salary to locate the next highest salary.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What are window functions in SQL (e.g. `ROW_NUMBER()`, `RANK()`, `DENSE_RANK()`)?",
            "Functions performing calculations across a set of table rows related to the current row without collapsing rows into a single group", "Functions that open UI windows", "Aggregate functions that collapse rows", "Functions running in parallel", "A",
            "Window functions evaluate partitions via the `OVER (PARTITION BY ... ORDER BY ...)` clause.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What is the difference between `RANK()` and `DENSE_RANK()`?",
            "`RANK()` leaves gaps in ranking numbers after ties (e.g. 1, 2, 2, 4); `DENSE_RANK()` does not leave gaps (e.g. 1, 2, 2, 3)", "`DENSE_RANK` leaves gaps", "They are identical", "`RANK` cannot handle ties", "A",
            "`RANK()` skips subsequent ranks based on tie count; `DENSE_RANK()` produces contiguous rankings.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What is a Common Table Expression (CTE) defined with `WITH` in SQL?",
            "A temporary named result set that can be referenced within a SELECT, INSERT, UPDATE, or DELETE statement", "A permanent database table", "An index structure", "A stored procedure", "A",
            "CTEs (`WITH cte AS (SELECT ...)`) improve readability and support recursive queries.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What is a correlated subquery in SQL?",
            "A subquery that references columns from the outer query, executing once for each row evaluated by the outer query", "A subquery that runs once independently", "A subquery returning multiple tables", "A query with JOIN", "A",
            "Because correlated subqueries depend on outer row values, they are re-evaluated per outer row.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What is SQL Injection, and how is it prevented?",
            "Injecting malicious SQL syntax into user inputs; prevented using Parameterized Queries / Prepared Statements", "A database backup technique", "Injecting data into tables", "An indexing strategy", "A",
            "Prepared statements treat user input as literal data rather than executable SQL syntax.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What does `EXPLAIN` or `EXPLAIN ANALYZE` do in SQL databases?",
            "Displays the execution plan chosen by the query optimizer showing index scans, table scans, and join strategies", "Explains syntax errors", "Runs a spelling checker", "Backs up the database", "A",
            "`EXPLAIN` assists database administrators in optimizing query performance and index usage.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "SQL",
            "What is the difference between DDL, DML, DCL, and TCL in SQL?",
            "DDL defines schema (CREATE, ALTER); DML manipulates data (SELECT, INSERT); DCL controls access (GRANT); TCL manages transactions (COMMIT)", "DDL is for transactions only", "DML defines schema", "They are all identical", "A",
            "Standard categorization of SQL command responsibilities.", Difficulty.EASY, 2));
    }

    private static void addDbDbms(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What are the ACID properties in database transaction management?",
            "Atomicity, Consistency, Isolation, and Durability", "Accuracy, Concurrency, Integrity, Distribution", "Access, Control, Identification, Delegation", "Automation, Clustering, Indexing, Decoupling", "A",
            "ACID guarantees reliable transaction processing even in the face of crashes or errors.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What are database Normal Forms (1NF, 2NF, 3NF, BCNF)?",
            "Rules to eliminate data redundancy and insertion/update/deletion anomalies: 1NF atomic, 2NF no partial dependency, 3NF no transitive dependency", "Encryption standards", "Index optimization levels", "File format standards", "A",
            "Normalization organizes tables to reduce duplicate data and prevent integrity anomalies.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What are the standard Transaction Isolation Levels in ANSI SQL?",
            "Read Uncommitted, Read Committed, Repeatable Read, and Serializable", "Low, Medium, High, Extreme", "Local, Global, Distributed, Isolated", "Open, Shared, Exclusive, Locked", "A",
            "Isolation levels trade off concurrency throughput against consistency anomalies (dirty reads, phantom reads).", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What is a 'dirty read' concurrency anomaly?",
            "A transaction reads uncommitted data written by another concurrent transaction that is subsequently rolled back", "Reading corrupt bytes from disk", "Reading data without indexes", "Reading deleted tables", "A",
            "Prevented in Read Committed and higher isolation levels.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What is a B+ Tree, and why is it preferred over B-Tree for database indexing?",
            "All data records are stored in leaf nodes, and leaf nodes are linked sequentially, providing superior range scans and high fan-out", "B+ Tree is a binary tree", "B+ Tree only stores integers", "It uses less disk space", "A",
            "B+ Tree internal nodes store only routing keys, allowing high branch fan-out and efficient disk paging.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What is the difference between a Clustered Index and a Non-Clustered Index?",
            "A clustered index physically sorts and stores table data rows (1 per table); non-clustered stores pointers to data rows (multiple permitted)", "Non-clustered physically orders data", "Every table must have 10 clustered indexes", "No difference", "A",
            "The clustered index defines the physical order of table pages on disk.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What is a phantom read in database transactions?",
            "A transaction re-runs a query finding newly inserted rows committed by another transaction matching search criteria", "Reading deleted data", "Reading uncommitted data", "A database driver glitch", "A",
            "Prevented by the Serializable isolation level (or range locks/MVCC).", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What is Two-Phase Locking (2PL) protocol in DBMS?",
            "Concurrency control protocol ensuring serializability: Growing Phase (locks acquired) followed by Shrinking Phase (locks released)", "Locking two tables simultaneously", "Locking across two servers", "Encrypting two columns", "A",
            "2PL guarantees conflict-serializable execution schedules.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What is a Foreign Key constraint in relational databases?",
            "A field in one table that uniquely identifies a row of another table, enforcing referential integrity", "A primary key in another database", "An encryption key", "A key generated by cloud providers", "A",
            "Foreign keys prevent orphan records and maintain relational consistency across parent-child tables.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What is Write-Ahead Logging (WAL) in database engines?",
            "Changes are written to a sequential log on persistent storage before being applied to actual data pages, ensuring Durability and crash recovery", "Logging user search queries", "Logging HTTP requests", "Archiving old tables", "A",
            "WAL allows fast in-memory updates with the guarantee that committed states can be recovered after power loss.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What is the difference between RDBMS and NoSQL databases?",
            "RDBMS is structured, relational, uses SQL, and prioritizes ACID; NoSQL provides flexible schema (document, key-value, graph) and prioritizes scalability", "NoSQL cannot store data permanently", "RDBMS does not use tables", "They are identical", "A",
            "NoSQL relaxes relational constraints to scale horizontally across distributed clusters.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "DBMS",
            "What does CAP theorem state for distributed data stores?",
            "A distributed system can guarantee at most two out of three: Consistency, Availability, and Partition Tolerance", "Calculate, Analyze, Process", "Cloud, Application, Platform", "Capacity, Allocation, Performance", "A",
            "Network partitions (P) are inevitable in distributed systems, forcing a design tradeoff between Consistency (CP) and Availability (AP).", Difficulty.MEDIUM, 3));
    }

    private static void addDbMysql(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What is the default storage engine in MySQL 5.5 and later?",
            "InnoDB", "MyISAM", "MEMORY", "CSV", "A",
            "InnoDB provides ACID compliance, row-level locking, and foreign key support by default.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What is the primary difference between InnoDB and MyISAM storage engines in MySQL?",
            "InnoDB supports transactions, row-level locking, and foreign keys; MyISAM only supports table-level locking and lacks transactions", "MyISAM supports transactions", "InnoDB has no indexes", "They are identical", "A",
            "InnoDB is designed for high-concurrency transactional systems; MyISAM lacks crash recovery and transactions.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What is the difference between `CHAR` and `VARCHAR` in MySQL?",
            "`CHAR` is fixed-length and pads unused space; `VARCHAR` is variable-length and stores only actual characters plus length prefix bytes", "`CHAR` is variable-length", "`VARCHAR` allocates fixed space", "They store identical byte lengths", "A",
            "`VARCHAR` saves disk and memory space for variable-length text entries.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What is the MySQL binary log (binlog) used for?",
            "Recording all DDL and DML data modifications for replication to replicas and point-in-time data recovery", "Storing user passwords", "Logging compiler errors", "Storing table backups", "A",
            "Replication replicas read master binlogs to replay data changes asynchronously.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What is the default port number for MySQL database server?",
            "3306", "5432", "1521", "8080", "A",
            "MySQL operates on TCP port 3306 by default.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What does `AUTO_INCREMENT` attribute do in MySQL?",
            "Automatically generates a unique sequential integer for new rows inserted into a primary key column", "Increases query speed automatically", "Scales server memory", "Increments all numbers in a row", "A",
            "`AUTO_INCREMENT` simplifies synthetic surrogate primary key generation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What is the MySQL Slow Query Log used for?",
            "Logging queries that exceed a configurable execution time threshold (`long_query_time`) to identify performance bottlenecks", "Slowing down queries", "Logging errors", "Storing deleted rows", "A",
            "DBAs analyze the slow query log to identify queries requiring optimization or indexing.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What does the `LIMIT offset, count` clause in MySQL do?",
            "Constrains the number of rows returned, skipping `offset` rows and taking up to `count` rows (used for pagination)", "Limits CPU usage", "Limits database size", "Restricts table access", "A",
            "`LIMIT 10, 20` retrieves rows 11 through 30 for pagination displays.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What is the buffer pool in MySQL InnoDB?",
            "A dedicated main-memory area where InnoDB caches table data pages and indexes to reduce disk I/O", "A network buffer", "A thread pool for queries", "A cache for query strings", "A",
            "`innodb_buffer_pool_size` is the most critical configuration parameter for MySQL database throughput.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What does `INSERT INTO ... ON DUPLICATE KEY UPDATE` do in MySQL?",
            "Performs an 'upsert': inserts a new row, or updates the existing row if a duplicate primary/unique key collision occurs", "Deletes duplicates", "Throws an error on duplicate", "Creates a duplicate table", "A",
            "Enables atomic insertion or updating without explicit select-then-update round trips.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What is the default transaction isolation level in MySQL InnoDB?",
            "REPEATABLE READ", "READ COMMITTED", "READ UNCOMMITTED", "SERIALIZABLE", "A",
            "MySQL InnoDB uses REPEATABLE READ by default, using Next-Key Locking to prevent phantom reads.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "MySQL",
            "What tool is used to perform logical backups of MySQL databases into SQL script dumps?",
            "`mysqldump`", "`mysqlrestore`", "`mysqlbackup`", "`mysqlexport`", "A",
            "`mysqldump -u root -p database_name > backup.sql` produces portable SQL dump files.", Difficulty.EASY, 2));
    }

    private static void addDbPostgresql(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is PostgreSQL known as in database architecture?",
            "An advanced, open-source object-relational database management system (ORDBMS)", "A pure NoSQL database", "An in-memory cache", "A flat-file system", "A",
            "PostgreSQL supports extensible data types, table inheritance, foreign keys, and complex analytical features.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is the default port number for PostgreSQL?",
            "5432", "3306", "1433", "27017", "A",
            "PostgreSQL listens on TCP port 5432 by default.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is the difference between `JSON` and `JSONB` data types in PostgreSQL?",
            "`JSON` stores exact text with whitespace preserved; `JSONB` stores decomposed binary format with indexing (GIN) and faster lookup", "`JSON` is faster for lookups", "`JSONB` does not support indexing", "They are identical", "A",
            "`JSONB` is generally preferred because it supports GIN indexes and fast nested attribute queries.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is MVCC (Multi-Version Concurrency Control) in PostgreSQL?",
            "A concurrency control mechanism where readers never block writers and writers never block readers by maintaining multiple row versions", "Locking all tables during write", "Single-threaded execution", "A disk encryption tool", "A",
            "MVCC allows transactions to view a consistent snapshot of data without read locks.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is the purpose of the `VACUUM` process in PostgreSQL?",
            "Reclaims storage occupied by dead row tuples generated by MVCC updates/deletes and updates optimizer statistics", "Clears user passwords", "Backs up database", "Drops unused tables", "A",
            "PostgreSQL auto-vacuum reclaims disk space from obsolete row versions.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is a GIN (Generalized Inverted Index) in PostgreSQL used for?",
            "Indexing composite items like JSONB documents, arrays, and full-text search vectors for fast lookup", "Indexing primary key integers", "Sorting strings alphabetically", "Encrypting columns", "A",
            "GIN indexes contain entries for individual sub-elements (keys, array items, words) mapping to rows.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is the `RETURNING` clause in PostgreSQL?",
            "Returns values from rows modified by INSERT, UPDATE, or DELETE without needing a separate SELECT query", "Returns from stored procedures", "Restores database backups", "Reverses a transaction", "A",
            "`INSERT INTO users (...) VALUES (...) RETURNING id;` retrieves generated identifiers directly.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is a SERIAL or IDENTITY type in PostgreSQL?",
            "Auto-incrementing integer sequence generator assigned to primary keys", "A character type", "An encrypted token", "A floating-point type", "A",
            "Modern PostgreSQL standard uses `GENERATED ALWAYS AS IDENTITY` or `SERIAL` for auto-increment keys.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is an array data type in PostgreSQL?",
            "Columns can directly store multi-dimensional arrays of variable length of any valid data type (e.g. `integer[]`, `text[]`)", "Arrays cannot be stored in PostgreSQL", "Only JSON can store lists", "Arrays are converted to tables", "A",
            "PostgreSQL has first-class native array column support with array slice and containment operators.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is the difference between a View and a Materialized View in PostgreSQL?",
            "A standard View executes its query dynamically on each read; a Materialized View physically caches query results on disk and must be refreshed", "Views store data on disk", "Materialized views cannot be refreshed", "No difference", "A",
            "`REFRESH MATERIALIZED VIEW` updates cached query snapshots for expensive analytical queries.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What is table partitioning in PostgreSQL?",
            "Splitting one logically large table into smaller physical tables (by range, list, or hash) to improve query performance and maintenance", "Splitting columns into tables", "Exporting tables to CSV", "Compressing tables", "A",
            "Partition pruning allows PostgreSQL query planners to scan only relevant physical partition tables.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "Database", "PostgreSQL",
            "What command-line interactive terminal client is used with PostgreSQL?",
            "`psql`", "`mysql`", "`sqlplus`", "`pgadmin_cli`", "A",
            "`psql` is the standard terminal frontend for managing PostgreSQL servers.", Difficulty.EASY, 2));
    }

    // ==================== CS FUNDAMENTALS ====================

    private static void addCsOs(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is the difference between a process and a thread?",
            "A process is an executing program with its own dedicated address space; a thread is a lightweight unit of execution sharing the process's memory", "Threads have separate memory spaces", "Processes share memory by default", "They are identical", "A",
            "Threads within a process share code, data, and heap segments but have distinct thread stacks and program counters.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is Virtual Memory in an Operating System?",
            "A memory management capability that provides an idealized, large address space to processes using RAM and secondary storage paging", "RAM chips on the motherboard", "Cache memory inside the CPU", "Virtual reality memory", "A",
            "Virtual memory maps virtual pages to physical frames via page tables, offering memory protection and isolation.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is a page fault in an operating system?",
            "An interrupt raised by the hardware MMU when an application accesses a memory page not currently mapped in physical RAM", "A crash in the browser", "A hardware RAM failure", "A corrupted file", "A",
            "The OS handles the page fault by swapping the missing page from disk into RAM.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is thrashing in operating system memory management?",
            "A state where the CPU spends more time swapping pages in and out of virtual memory than executing actual instructions", "A fast CPU speed mode", "A hard drive formatting process", "An infinite loop", "A",
            "Thrashing occurs when total working set sizes exceed physical RAM capacity.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What are common CPU scheduling algorithms in Operating Systems?",
            "First-Come First-Served (FCFS), Shortest Job First (SJF), Round Robin (RR), and Priority Scheduling", "QuickSort, MergeSort, HeapSort", "TCP, UDP, IP, ICMP", "RAID 0, RAID 1, RAID 5", "A",
            "CPU schedulers allocate CPU execution time slices across ready processes.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is a context switch in an Operating System?",
            "Saving the state of the currently running process/thread and loading the state of another process to resume execution", "Switching user accounts", "Changing screen resolution", "Booting the OS", "A",
            "Context switches involve saving CPU registers, program counters, and updating page tables.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is a Semaphore vs a Mutex in inter-process synchronization?",
            "A Mutex is a locking mechanism with ownership (1 thread); a Semaphore is a signaling mechanism with a counter (N concurrent threads)", "Mutex allows N threads", "Semaphore is only for 1 thread", "No difference", "A",
            "Mutex guarantees mutual exclusion; counting semaphores regulate access to pools of N shared resources.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is a zombie process in Unix/Linux?",
            "A process that has finished execution but still has an entry in the process table because its parent hasn't read its exit status via `wait()`", "A process that never dies", "A virus program", "A background daemon", "A",
            "Zombies consume process IDs (PIDs) until reaped by their parent process or init.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is an orphan process in Unix/Linux?",
            "A process whose parent process has finished or terminated, causing it to be adopted by `init` (PID 1)", "A process with no PID", "A process with no memory", "A deleted program", "A",
            "The `init` or `systemd` process adopts orphan processes to reap them upon exit.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is the difference between User Mode and Kernel Mode in OS CPU execution?",
            "Kernel Mode has unrestricted hardware access; User Mode has restricted privileges, using system calls to request OS kernel services", "User mode has full hardware access", "Kernel mode is for user applications", "They are identical", "A",
            "Dual-mode CPU operation protects operating system integrity and system stability from buggy user code.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What algorithm is used for Deadlock Avoidance in Operating Systems?",
            "Banker's Algorithm", "Dijkstra's Algorithm", "Kruskal's Algorithm", "Round Robin", "A",
            "Dijkstra's Banker's Algorithm simulates resource allocation to ensure systems never enter an unsafe state.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Operating Systems",
            "What is Belady's Anomaly in page replacement algorithms?",
            "The phenomenon where increasing the number of page frames results in an increase in the number of page faults (observed in FIFO)", "A CPU scheduling bug", "A memory leak in Linux", "A disk error", "A",
            "FIFO page replacement exhibits Belady's anomaly; stack-based algorithms like LRU do not.", Difficulty.HARD, 4));
    }

    private static void addCsNetworks(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What are the 7 layers of the OSI model from Layer 1 to Layer 7?",
            "Physical, Data Link, Network, Transport, Session, Presentation, Application", "Application, Transport, Internet, Network Access", "Hardware, Software, Protocol, TCP, UDP, HTTP, DNS", "MAC, IP, TCP, HTTP, HTML, CSS, JS", "A",
            "Standard ISO/OSI 7-layer reference architecture for telecommunication and computer networking.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is the difference between TCP and UDP at the Transport layer?",
            "TCP is connection-oriented, reliable, and guarantees ordered delivery via handshakes; UDP is connectionless, fast, and unacknowledged", "UDP is connection-oriented", "TCP is faster and lossy", "No difference", "A",
            "TCP provides reliable delivery and flow control; UDP minimizes latency for streaming and gaming.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is the TCP 3-way handshake used to establish a connection?",
            "SYN -> SYN-ACK -> ACK", "ACK -> SYN -> SYN-ACK", "HELLO -> HELLO-ACK -> READY", "CONNECT -> ACCEPT -> CONNECTED", "A",
            "Client sends SYN; server responds with SYN-ACK; client acknowledges with ACK to establish full-duplex communication.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is DNS (Domain Name System) and what default port does it use?",
            "Translates human-readable domain names (e.g. google.com) into numeric IP addresses; operates on port 53 (primarily UDP)", "Encrypts web traffic", "Assigns IP addresses dynamically", "A web hosting tool", "A",
            "DNS acts as the internet's distributed address phonebook using port 53.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is DHCP (Dynamic Host Configuration Protocol)?",
            "A network management protocol that automatically assigns IP addresses, subnet masks, and default gateways to devices on a network", "Translates domain names", "Routes packets across continents", "A firewall protocol", "A",
            "DHCP automates network configuration, eliminating manual static IP assignment.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is the difference between IPv4 and IPv6?",
            "IPv4 uses 32-bit addresses (~4.3 billion); IPv6 uses 128-bit addresses (virtually unlimited 3.4 x 10^38)", "IPv4 uses 64 bits", "IPv6 uses 32 bits", "IPv4 is encrypted by default", "A",
            "IPv6 was created to solve global IPv4 address exhaustion.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is ARP (Address Resolution Protocol)?",
            "Resolves a known Network layer IP address to a physical Data Link layer MAC address", "Translates URLs to IPs", "Encrypts packets", "Allocates ports", "A",
            "ARP maps Layer 3 IP addresses to Layer 2 Ethernet MAC hardware addresses.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is the difference between HTTP and HTTPS?",
            "HTTPS encrypts communication over TLS/SSL (default port 443); HTTP sends plaintext over port 80", "HTTPS is faster", "HTTP requires certificates", "They use the same port", "A",
            "HTTPS uses Transport Layer Security to provide encryption, data integrity, and server authentication.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is NAT (Network Address Translation)?",
            "A method of mapping multiple private local IP addresses to a single public IP address on an internet gateway", "A protocol to name computers", "A web browser caching mechanism", "An encryption algorithm", "A",
            "NAT conserves registered public IPv4 addresses and shields internal network topologies.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is ICMP (Internet Control Message Protocol) used for?",
            "Used by network devices to send error messages and operational info (e.g. `ping` and `traceroute`)", "Transferring web pages", "Streaming video", "Sending emails", "A",
            "ICMP generates diagnostic signals such as destination unreachable, time exceeded, and echo requests.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is the purpose of subnetting and subnet masks?",
            "Divides a large network into smaller subnetworks to improve routing efficiency and manage broadcast domains", "Encrypts IP addresses", "Speeds up internet bandwidth", "Creates domain names", "A",
            "A subnet mask distinguishes the network prefix portion from the host address portion of an IP.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Computer Networks",
            "What is a SYN Flood attack in network security?",
            "A denial-of-service attack where an attacker sends rapid SYN requests without sending the final ACK, exhausting server connection resources", "Flooding network cables with water", "Hacking passwords", "A DNS spoofing technique", "A",
            "SYN cookies and firewalls protect servers from half-open connection table exhaustion.", Difficulty.HARD, 4));
    }

    private static void addCsSoftwareEngineering(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What does the SOLID design principles acronym stand for?",
            "Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion", "Simple, Optimized, Linear, Iterative, Dynamic", "Secure, Object, Logic, Interface, Design", "Software, Operations, Lifecycle, Integration, Deployment", "A",
            "SOLID guides clean, maintainable, modular object-oriented software architecture.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is the Single Responsibility Principle (SRP)?",
            "A class should have only one reason to change, meaning it should perform only a single cohesive job", "A class should have only one method", "A program should have only one class", "A function cannot call other functions", "A",
            "Separating distinct responsibilities into dedicated classes minimizes regression risk during maintenance.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is the Open/Closed Principle (OCP)?",
            "Software entities should be open for extension, but closed for modification", "Code must be open source", "Classes must have public fields", "Files must be closed after writing", "A",
            "New features should be added via inheritance or polymorphism without rewriting tested core code.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is the Liskov Substitution Principle (LSP)?",
            "Objects of a superclass should be replaceable with objects of its subclasses without breaking program correctness", "Subclasses cannot add methods", "All classes must be abstract", "Inheritance should never be used", "A",
            "Subclasses must conform strictly to behavioral expectations established by base types.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is the difference between Agile and Waterfall software development methodologies?",
            "Waterfall is linear and sequential; Agile is iterative and flexible with rapid incremental sprints", "Waterfall delivers software every week", "Agile has no customer feedback", "They are identical", "A",
            "Agile embraces evolving requirements through short sprint iterations and continuous collaboration.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is CI/CD in DevOps and modern software engineering?",
            "Continuous Integration (automating building and testing) and Continuous Delivery/Deployment (automating release pipelines)", "Cloud Infrastructure and Cloud Data", "Code Inspection and Code Documentation", "Customer Interaction and Customer Design", "A",
            "CI/CD enables rapid, reliable code integration and deployment to staging/production clusters.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is Test-Driven Development (TDD)?",
            "Writing an automated failing unit test before writing the production code required to pass that test (Red-Green-Refactor)", "Testing software only in production", "Manual testing by QA teams", "Writing code without unit tests", "A",
            "TDD ensures comprehensive test coverage and tight, modular architectural design.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is the difference between Black-Box and White-Box testing?",
            "Black-Box tests functionality without knowing internal code; White-Box tests internal logic, paths, and structure", "White-Box tests user interface only", "Black-Box examines code line by line", "They are identical", "A",
            "Black-box tests specifications and inputs/outputs; white-box tests execution branches and code coverage.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is technical debt in software development?",
            "The implied cost of future rework caused by choosing an easy/hacky solution now instead of a better approach that takes longer", "Money owed to software vendors", "Hardware leasing costs", "Cloud hosting fees", "A",
            "Unmanaged technical debt compounds over time, slowing feature velocity and increasing bug frequency.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is the Singleton design pattern and what is a potential downside?",
            "Ensures a class has only one instance and provides global access; downside: introduces global state and complicates unit testing", "Creates unlimited instances", "Encrypts objects", "A functional programming pattern", "A",
            "Singletons act as global variables, tightly coupling components and complicating mocking in tests.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is the Factory Method design pattern?",
            "Defines an interface for creating an object, but lets subclasses decide which specific class to instantiate", "Creates database tables", "Destroys objects", "Compiles code", "A",
            "Factory patterns decouple object instantiation from calling business logic.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Software Engineering",
            "What is regression testing?",
            "Re-running functional and non-functional tests to ensure that previously developed and tested software still performs after recent changes", "Testing software performance under load", "Testing security holes", "Testing user interface design", "A",
            "Automated regression suites verify that bug fixes or new features haven't introduced regressions.", Difficulty.EASY, 2));
    }

    private static void addCsGit(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What is Git in software development?",
            "A distributed version control system for tracking changes in source code across developers", "A cloud code hosting service", "A programming language", "A compiler", "A",
            "Git gives every developer a full local clone of project history and branch tracking.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What is the difference between Git and GitHub?",
            "Git is the local command-line version control tool; GitHub is a cloud platform for hosting Git repositories and collaboration", "GitHub is the tool; Git is the website", "They are identical", "Git is only for Windows", "A",
            "Git is the distributed VCS software; GitHub, GitLab, and Bitbucket are cloud hosting platforms.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What is the staging area (index) in Git?",
            "An intermediate holding area where changes are formatted and reviewed using `git add` before committing to history", "The remote GitHub server", "The local working directory", "The trash bin", "A",
            "Staging permits crafting atomic commits containing only select modified files.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What is the difference between `git merge` and `git rebase`?",
            "`merge` creates a new merge commit preserving history branches; `rebase` rewrites commit history onto the target base for a linear history", "`rebase` creates merge commits", "They are identical", "`merge` deletes branches", "A",
            "Rebase rebases feature commits sequentially on top of master for a clean, linear git log.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What does `git stash` do?",
            "Temporarily shelves (stashes) uncommitted dirty working directory changes so you can work on another branch cleanly", "Deletes uncommitted files permanently", "Pushes code to GitHub", "Creates a new branch", "A",
            "`git stash pop` restores shelved modifications back to your working copy.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What is a merge conflict in Git, and how is it resolved?",
            "Occurs when two branches modify the same line of a file differently; resolved by manually editing the conflict markers `<<<<<<<` and committing", "Git deletes the file automatically", "Git crashes", "The server rejects both branches", "A",
            "Developers manually reconcile competing line changes, stage the resolved file, and finalize the commit.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What is the difference between `git pull` and `git fetch`?",
            "`git fetch` downloads remote commits without modifying local working files; `git pull` does a `git fetch` followed by `git merge`", "`git pull` only downloads headers", "They are identical", "`git fetch` deletes local branches", "A",
            "`git fetch` lets you inspect incoming remote changes safely before merging them into local branches.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What does a `.gitignore` file do?",
            "Specifies intentionally untracked file patterns (e.g. build artifacts, credentials, logs) that Git should ignore", "Deletes secret files", "Encrypts code files", "Locks the repository", "A",
            "Files matching `.gitignore` patterns are ignored during `git status` and `git add`.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What is a Pull Request (PR) on GitHub?",
            "A collaborative proposal to merge changes from a feature branch into a target branch, facilitating code review, discussion, and CI checks", "A command to download code", "A bug report", "A git stash command", "A",
            "Pull Requests allow peer review and automated testing before changes enter main branches.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What does `git reset --hard HEAD~1` do?",
            "Discards the most recent commit and permanently wipes out all uncommitted working directory changes matching it", "Undoes commit but keeps files in staging", "Pushes to remote", "Deletes git", "A",
            "`--hard` rewinds HEAD and overwrites staging and working tree state irreversibly.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What is `git cherry-pick <commit-hash>` used for?",
            "Applies the changes introduced by a specific existing commit from another branch onto the current working branch", "Deletes a commit", "Branches out", "Merges all commits", "A",
            "Cherry-picking extracts a single isolated bug fix or feature commit without merging the whole branch.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "Git & GitHub",
            "What does `git blame <filename>` do?",
            "Annotates each line of a file with the author, commit hash, and timestamp of the last modification", "Accuses developers of bugs", "Deletes bad commits", "Reverts a file", "A",
            "`git blame` displays revision history line-by-line for context and investigation.", Difficulty.EASY, 2));
    }

    private static void addCsSystemDesign(List<QuestionTopUpSeeder.QSpec> list) {
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is the difference between Horizontal Scaling and Vertical Scaling?",
            "Horizontal scaling adds more machines to a cluster (scale out); Vertical scaling adds more CPU/RAM to a single machine (scale up)", "Horizontal adds more RAM", "Vertical adds more servers", "They are identical", "A",
            "Horizontal scaling provides elastic, fault-tolerant scale; vertical scaling hits hardware cost and physical limits.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is a Load Balancer in system architecture?",
            "Distributes incoming network traffic efficiently across a pool of backend servers to prevent overloads and ensure high availability", "A database query optimizer", "A CPU cooler", "A network cable", "A",
            "Load balancers (e.g. Nginx, AWS ALB) balance workloads and perform health checks.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is caching in system design, and what eviction policies are commonly used?",
            "Storing frequently accessed data in fast in-memory stores (Redis/Memcached); common eviction: LRU (Least Recently Used), LFU, FIFO", "Saving files to hard disk", "Compressing database rows", "Writing data to tape", "A",
            "In-memory caches dramatically reduce database read bottlenecks and lower request latency.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is Database Sharding?",
            "Horizontal partitioning of a database table across multiple independent database servers by a shard key", "Creating indexes", "Normalizing tables", "Replicating data to one read replica", "A",
            "Sharding distributes large datasets across servers to surpass single-node storage and I/O ceilings.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is a CDN (Content Delivery Network)?",
            "A geographically distributed network of proxy servers that cache static assets (images, videos, JS/CSS) close to end users", "A central database", "A code repository", "A firewall tool", "A",
            "CDNs minimize latency and offload traffic from origin servers by serving content from edge points-of-presence.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is a Message Queue (e.g. Apache Kafka, RabbitMQ) used for?",
            "Asynchronous communication, decoupling distributed services, and buffering traffic spikes", "Storing user passwords", "Serving HTTP requests directly", "Replacing databases", "A",
            "Message brokers provide asynchronous task execution and reliable queue buffering during traffic surges.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is Consistent Hashing in distributed caching and storage?",
            "A hashing technique mapping keys to a logical circle, minimizing remapped keys when nodes are added or removed to O(K/N)", "Standard modulo hashing", "A symmetric encryption algorithm", "A database sorting method", "A",
            "Consistent hashing prevents catastrophic cache stampedes when scaling distributed clusters up or down.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is an API Gateway in microservices architecture?",
            "A single entry point managing request routing, authentication, rate limiting, and SSL termination for internal microservices", "A physical network router", "A database driver", "A frontend UI framework", "A",
            "API Gateways decouple client interfaces from internal microservice decomposition.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is the difference between Monolithic and Microservices architecture?",
            "Monolith packages all capabilities in a single unified codebase/deployment; Microservices decouples domains into independently deployable services", "Microservices is always single-threaded", "Monolith cannot use databases", "They are identical", "A",
            "Microservices enable independent team deployment velocity at the cost of operational and distributed complexity.", Difficulty.EASY, 2));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is Rate Limiting in web APIs, and what algorithms are used?",
            "Restricting the number of API requests a client can make in a given timeframe (Token Bucket, Leaky Bucket, Sliding Window)", "Limiting network bandwidth", "Reducing CPU speed", "Compressing responses", "A",
            "Rate limiting shields backend services from abusive traffic, scraping, and denial-of-service degradation.", Difficulty.MEDIUM, 3));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is the Circuit Breaker pattern in distributed systems?",
            "Prevents an application from repeatedly attempting an operation likely to fail, returning fallback responses while downstream systems recover", "Trips power in server racks", "Terminates all server threads", "Deletes corrupt data", "A",
            "Transitions between Closed, Open, and Half-Open states to prevent cascading failures across services.", Difficulty.HARD, 4));
        list.add(new QuestionTopUpSeeder.QSpec(TCH, "CS Fundamentals", "System Design",
            "What is database replication (Leader-Follower / Primary-Replica)?",
            "Writing to a primary leader node while replicating data to multiple follower nodes to scale read throughput and ensure redundancy", "Copying database tables manually", "Taking hourly backups", "Splitting tables into shards", "A",
            "Primary-replica setups distribute read queries across replicas while funneling writes through the primary node.", Difficulty.EASY, 2));
    }
}
