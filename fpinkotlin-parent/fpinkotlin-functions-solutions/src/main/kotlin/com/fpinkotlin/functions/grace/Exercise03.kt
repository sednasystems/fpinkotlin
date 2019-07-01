/**
 * Exercise 3.3
 *
 * Write a function to add two Int values, in curried form.
 *
 */

val add: (Int) -> (Int) -> Int = { a -> { b -> a + b } }
