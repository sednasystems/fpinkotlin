/**
 * Exercise 3.2
 *
 * Make the compose function polymorphic by using type parameters.
 */

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C = { f(g(it)) }