/**
 * Exercise 3.1
 *
 * Write the compose function (declared with 'fun'), allowing to compose
 * functions from Int to Int.
 */

fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { f(g(it)) }