# Solutions to Chapter 4 Exercises

## Exercise 01

I thought that the corecursive form requires a helper, 
like in the one of the toString() examples on page 83. 
But I see that the last example in 4.1.1 on pg 83 simple has
an if-else statement for the function body.

```kotlin
fun add(a: Int, b: Int): Int {
    tailrec fun add_(x: Int, y: Int): Int =
        if (x == 0) y else add_(inc(x), dec(y))
    return add_(a, b)
}
```

Real solution:
```kotlin
tailrec fun add(a: Int, b: Int): Int =
    if (a == 0) b else add(inc(a), dec(b))
```

## Exercise 02

```kotlin
object Factorial {
    private lateinit var fact: (Int) -> Int
    init {
        fact = { n ->
            if (n == 0 || n == 1) 1
            else n * fact(n - 1)
        }
    }
    val factorial = fact
}
```

## Exercise 03

First, the loop-based Fibonacci implementation:

```kotlin
fun fib(n: Int): BigInteger {
    var acc1: BigInteger = BigInteger.ZERO
    var acc2: BigInteger = BigInteger.ZERO
    var tmp: BigInteger
    var x = 0

    while (x <= n) {
        if (x == 0) acc2 = BigInteger.ONE
        else {
            tmp = acc1
            acc1 = acc2
            acc2 += tmp
        }
        x++
    }

    return acc2
}
```

Then the tail-recursive function:

```kotlin
fun fib(n: Int): BigInteger {
    tailrec fun fib_(acc1: BigInteger, acc2: BigInteger, x: Int): BigInteger {
        return when {
            (x == 0) -> BigInteger.ONE
            (x > n) -> acc2
            else -> fib_(acc2, acc1 + acc2, x + 1)
        }
    }

    return fib_(BigInteger.ZERO, BigInteger.ZERO, n)
}
```

## Exercise 04

The IDE complained about putting underscores in function names, so the convention
adopted by the book won't work! haha

I finished the exercise but didn't use a delimiter! And the test still passed. 

```kotlin
fun string(list: List<Char>): String {
    tailrec fun makeString (acc: String, lst: List<Char>): String {
        return when {
            lst.isEmpty() -> ""
            lst.size == 1 -> acc + lst.head()
            else -> makeString(acc + lst.head(), lst.tail())
        }
    }
    return makeString("", list)
}
```

## Exercise 05

The tests are missing `makeString`, which the book asks for. Also not sure why this fails in the tests:

```kotlin
fun <T, U> foldLeft(list: List<T>, z: U, f: (U, T) -> U): U {
    tailrec fun helper(acc: U, lst: List<T>): U {
        return when {
            lst.isEmpty() -> acc
            else -> helper(f(acc, list.head()), lst.tail())
        }
    }
    return helper(z, list)
}

fun sum(list: List<Int>): Int = foldLeft(list, 0, Int::plus)

fun string(list: List<Char>): String = foldLeft(list, "", String::plus)

```

## Exercise 06

```kotlin
fun <T, U> foldRight(list: List<T>, identity: U, f: (T, U) -> U): U =
    if (list.isEmpty())
        identity
    else
        f(list.head(), foldRight(list.tail(), identity, f))
```

Originally I was confused because the provided code already has implementations for `sum` and `string` that use `foldRight`,
but I was supposed to use `prepend` to re-write the `string` implementation. Here we are:

```kotlin
fun string(list: List<Char>): String = foldRight(list, "") { a, b -> prepend(a, b)}

fun prepend(c: Char, s: String): String = "$c$s"
```

## Exercise 07

The test for exercise07 is importing from exercise08, and it looks like the reverse is true.

After looking at the solution, my error here is providing an explicit lambda for prepend 
instead of the function reference.

```kotlin

fun <T> prepend(list: List<T>, elem: T): List<T> = listOf(elem) + list

fun <T> reverse(list: List<T>): List<T> = foldLeft(list, listOf()) { l, e -> prepend(l, e) }

```

## Exercise 08

Q: Why is it such a big deal to pass an empty list in Ex07 anyway? Why are we making less-readable code
in order to not have an empty list "trick"?

I looked at the answer and think it's kinda dumb. I thought we'd be using the copy function! I thought 
we were avoiding the use of listOf(elem)! What is going on! Anyway for reference, the answer that I 
do not feel I earned:

```kotlin

fun <T> copy(list: List<T>): List<T> = foldLeft(list, listOf()) { l, e -> l + e }

fun <T> prepend(list: List<T>, elem: T): List<T> = foldLeft(list, listOf(elem)) { l, e -> l + e }

```

## Exercise 09

I mis-read the signature for `add` in the IDE as requiring two arguments at first, so that was incorrect.
Also once I changed to a `MutableList` the IDE let me know that my `var` could be a `val`. :) 

I stopped the test run after 12 minutes of not completing, and this implementation seems to match the 
solution in the book! haha

```kotlin
fun range(start: Int, end: Int): List<Int> {
    val list: MutableList<Int> = mutableListOf()
    var i = start

    while (i < end) {
        list.add(i)
        i++
    }

    return list
}
```

## Exercise 10

Since the book's answer is also running forever, I'll just point out my initial mistake
was not assigning `i` to `f(i)`. That's something that I should have checked the IDE 
notification about the `var` for, as it would have helped me see that `i` was not being 
reassigned to any new value.

```kotlin
fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    val list: MutableList<T> = mutableListOf()
    var i = seed

    while (p(seed)) {
        list.add(i)
        i = f(i)
    }

    return list
}

```

## Exercise 11

```kotlin

fun range(start: Int, end: Int): List<Int> = unfold(
        seed = start,
        f = { x -> x + 1 },
        p = { x -> x < end }
)
```

## Exercise 12

```kotlin
fun range(start: Int, end: Int): List<Int> =
    if (start >= end)
        listOf()
    else
        prepend(range(start + 1, end), start)
```

## Exercise 13

```kotlin
fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> =
    if (!p(seed))
        listOf()
    else
        prepend(unfold(f(seed), f, p), seed)
```

## Exercise 14

Honestly It took several looks in the book to get to this solution,
which does not cease execution in my IDE. I had trouble with this one.

```kotlin
fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    tailrec fun helper(acc: List<T>, newSeed: T): List<T> =
        if (!p(seed))
            acc
        else
            helper(acc + seed, f(newSeed))
    return helper(listOf(), seed)
}
```

## Exercise 15

I looked at the hint, and decided to try to use previous functions:

```kotlin
fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> =
    if (!p(seed))
        listOf()
    else
        prepend(unfold(f(seed), f, p), seed)

fun <T> prepend(list: List<T>, elem: T): List<T> = foldLeft(list, listOf(elem)) { l, e -> l + e }

fun fibo(number: Int): String {
    tailrec fun fib(acc1: Int, acc2: Int, x: Int): Int {
        return when {
            (x == 0) -> 1
            (x > number) -> acc2
            else -> fib(acc2, acc1 + acc2, x + 1)
        }
    }

    return makeString(unfold(0, { x -> fib(0, 0, x) }, { x -> x == number }), ",")
}

fun <T> makeString(list: List<T>, separator: String): String =
    when {
        list.isEmpty() -> ""
        list.size == 1 -> "${list.tail()}"
        else -> "${list.head()}$separator" + makeString(list.tail(), separator)
    }
```

Then I tried to first evaluate my solution by hand, and then print out the function calls during 
the test run, and I realized the test can be cheated with my solution above. Whoops!

## Exercise 16

My solution differs from the book because I added an extra helper parameter:

```kotlin
fun <T> iterate(seed: T, f: (T) -> T, n: Int): List<T> {
    tailrec fun helper(acc: List<T>, seed: T, total: Int): List<T> =
        if (total < n)
            helper(acc + seed, f(seed), total + 1)
        else acc
    return helper(listOf(), seed, 0)
}
```

## Exercise 17

```kotlin
fun <T, U> map(list: List<T>, f: (T) -> U): List<U> {
    tailrec fun helper(acc: List<U>, lst: List<T>): List<U> =
        if (lst.isEmpty())
            acc
        else
            helper(acc + f(lst.head()), lst.tail())

    return helper(listOf(), list)
}
```

## Exercise 18

I originally was trying to write this as a tail-recursive function because I thought that
tail-recursive functions can be transformed into corecursive functions -- and I thought
that is what Kotlin is doing. However, the internet says that TCE actually just creates
an iterative loop in the bytecode, so TIL.

```kotlin
fun fiboCorecursive(number: Int): String {
    val seed = Pair(0, 1)
    val f = { (x, y): Pair<Int, Int> -> Pair(y, x + y) }
    val pairs = iterate(seed, f, number + 1)
    val list = map(pairs) { it.first }
    return makeString(list, ",")
}
```