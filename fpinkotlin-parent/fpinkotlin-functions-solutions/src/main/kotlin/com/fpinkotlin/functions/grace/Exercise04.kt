/**
 * Exercise 3.4
 *
 * Define a value function composing two (Int) -> Int functions
 */

// wrong
//val compose: ((Int) -> Int, (Int) -> Int) -> Int = { f: (Int) -> Int, g: (Int) -> Int -> { f(g(it)) } }


// after reading how the types should work with currying
val compose: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int = { a -> { b -> { c -> a(b(c)) } } }