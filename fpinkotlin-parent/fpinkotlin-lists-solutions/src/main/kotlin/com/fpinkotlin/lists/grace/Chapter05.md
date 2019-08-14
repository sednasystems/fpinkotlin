# Solutions to Chapter 5 Exercises

## Exercise 01

Couldn't figure out the second param, so I peeked, then felt silly. Haha!

```kotlin
    fun cons(a: A): List<A> = Cons(a, this)
```

## Exercise 02

Wow this looks nothing like the book solution! Mine is way more verbose.

```kotlin

import java.lang.IllegalStateException

    fun setHead(a: A): List<A> {
        if (this.isEmpty()) {
            throw IllegalStateException("super illegal!")
        }
        
        return when (this) {
            Nil -> Cons(a, this)
            is Cons -> Cons(a, this.tail)
        }
    }
```

## Exercise 03

I followed the hint, which meant that I was stuck on the Cons implementation. I couldn't get it to recognize the `tail`.
So I read the solution which was not actually to write different implementations for Nil and Cons via 
overriding an abstract function for List. So here's my initial bad implementation for Cons: 

```kotlin
    override fun drop(n: Int): List<A> {
        tailrec fun helper(remainingInt: Int, newList: List<A>): List<A> =
            if (remainingInt == 0)
                newList
            else
                helper(remainingInt - 1, newList.tail)

        return helper(n, this)
    }
```

My solution ended up being:

```kotlin
    fun drop(n: Int): List<A> {
        tailrec fun helper(remainingInt: Int, newList: List<A>): List<A> {
            return if (remainingInt == 0) newList else when (newList) {
                Nil -> this
                is Cons -> helper(remainingInt - 1, newList.tail)
            }
        }

        return helper(n, this)
    }
```

## Exercise 04

I didn't do this as a companion object function (mentioned in the book solution).

```kotlin
    fun dropWhile(p: (A) -> Boolean): List<A> {
        tailrec fun helper(list: List<A>): List<A> {
            return when (list) {
                Nil -> list
                is Cons -> if (!p(list.head)) list else helper(list.tail)
            }
        }
        return helper(this)
    }
```

## Exercise 05

The code set up for Exercise 5 already has the reverse function implemented in the companion object!

I tried writing the reverse function myself from scratch, but without it being in the companion object, I couldn't
see how to correctly call the helper function with the initial values. I didn't remember List.invoke(), like in the
book solution for the instance function:

```kotlin
fun reverse(): List<A> {
    tailrec fun helper(acc: List<A>, list: List<A>): List<A> {
        return when (list) {
            Nil -> acc
            is Cons -> helper(acc.cons(list.head), list.tail)
        }
    }
    return helper(this.head, this.tail) // these don't work!
}
```

Completed solution, not including the companion object function already provided:

```kotlin
fun init(): List<A> = this.reverse().drop(1).reverse()

fun reverse(): List<A> = reverse(List.invoke(), this)
```

## Exercise 06

Solution said it won't compile, so, hooray?

```kotlin

fun sum(ints: List<Int>): Int = when (ints) {
    List.Nil -> 0
    is List.Cons -> ints.head + sum(ints.tail)
}
```

## Exercise 07

```kotlin
fun product(ints: List<Double>): Double = when (ints) {
    List.Nil -> 1.0
    is List.Cons -> ints.head * product(ints.tail)
}
```

## Exercise 08

```kotlin
    fun length(): Int = foldRight(this,0) { _ -> { it + 1 } }
```

## Exercise 09

The companion object already has foldLeft in the exercise code! 

```kotlin
    fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B = foldLeft(identity, this, f)
```

## Exercise 10

The test code was referring to exercise08's `length` instead of Ex10; now fixed.

```kotlin
fun length(): Int = foldLeft(0) { x -> { x + 1 } }

fun sum(list: List<Int>): Int = list.foldLeft(0) { x -> { y -> x + y } }

fun product(list: List<Double>): Double = list.foldLeft(1.0) { x -> { y -> x * y } }

```

## Exercise 11

```kotlin
fun reverse(): List<A> = foldLeft(List.invoke()) { x -> { a -> x.cons(a) } }
```

## Exercise 12

```kotlin

```
## Exercise 13

```kotlin

```

## Exercise 14

```kotlin

```

## Exercise 15

```kotlin

```

## Exercise 16

```kotlin

```

## Exercise 17

```kotlin

```

## Exercise 18

```kotlin

```

## Exercise 19

```kotlin

```

## Exercise 20

```kotlin

```

## Exercise 21

```kotlin

```
