# Ammar's solutions to Chapter 3 Exercises

### 1. 
```fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { x: Int -> f(g(x)) }```

Notes: 
* It's possible to omit the first and only argument in a returned lambda by using the **it** keyword.

### 2.

```fun <T> compose(f: (T) -> T, g: (T) -> T): (T) -> T = { f(g(it)) }```

Notes:
* This is incorrect because it generalizes to the same type for each invocation.
Not allowing for intermediary types, nor enforcing the order for function applications.

Correct Solution:

```fun <T,U,V> compose(f: (T) -> V, g: (U) -> T): (U) -> V = { f(g(it)) }```

### 3.

```val add: (Int) -> (Int) -> Int = { x -> { x + it } }```

### 4.

```val <T,U,V> compose: ((U) -> V) -> ((T) -> U) -> (T) -> V = { a -> { b -> { a(b(it)) } } }```

Notes:

* I jumped ahead a little bit. The implementation is correct if I didn't use polymorphism, that's the next
exercise :\)

Correct Solution:

```val compose: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int = { a -> { b -> { a(b(it)) } } }```

### 5.

`fun <T,U,V> higherCompose(): ((U) -> V) -> ((T) -> U) -> (T) -> V = { a -> { b -> { a(b(it)) }} }`

### 6.

`fun <T, U, V> higherAndThen(): ((U) -> V) -> ((V) -> T) -> (U) -> T =
           { f ->
               { g ->
                   { x -> g(f(x)) }
               }
           }`
           
### 7.

Notes:
* The wording of this question was super confusing, I had to look at the solution to figure out what it was even asking :S

Correct Solution:

`fun <A,B,C> partialA(a: A, x: (A) -> (B) -> C): (B) -> C = x(a)`

### 8.

`fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C = { a -> f(a)(b) }`

### 9.

`fun <A ,B, C, D> curried(): (A) -> (B) -> (C) -> (D) -> String = { a -> { b -> { c -> { d ->  "$a, $b, $c, $d" }}}}`

### 10.

`fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C = { a -> { b -> f(a, b) } }`

### 11.

`fun <T, U, V> swapArgs(f: (T) -> (U) -> V): (U) -> (T) -> V = { u-> { t -> f(t)(u) }}`

Notes:
* Think there's a mistake in the exercise? Return type is (V)?
