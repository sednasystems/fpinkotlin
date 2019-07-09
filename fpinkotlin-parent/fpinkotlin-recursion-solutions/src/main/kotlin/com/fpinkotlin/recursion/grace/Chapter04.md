# solutions to Chapter 4 Exercises

## Exercise 01

I thought that the corecursive form requires a helper, 
like in the one of the toString() examples on page 83. 
But I see that the last example in 4.1.1 on pg 83 simple has
an if-else statement for the function body.

```
fun add(a: Int, b: Int): Int {
    tailrec fun add_(x: Int, y: Int): Int =
        if (x == 0) y else add_(inc(x), dec(y))
    return add_(a, b)
}
```

Real solution:
```
tailrec fun add(a: Int, b: Int): Int =
    if (a == 0) b else add(inc(a), dec(b))
```

## Exercise 02

```
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

```
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

```
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

