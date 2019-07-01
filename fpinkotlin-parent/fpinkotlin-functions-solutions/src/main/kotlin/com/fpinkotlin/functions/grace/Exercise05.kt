
/**
 * Just gonna put my questions here, I couldn't initially solve this:
 *
 * - polymorphic properties?
 * - the book and the provided code in the repo are a bit different, so I read the code,
 * which makes higherCompose() already a fun function AND YET the comment in the code
 * says "Define a value function composing two (Int) -> Int functions"
 * - so which is it supposed to be -- a fun function or a value function?!
 * - learning note for next time: write out the return type of the function for functions
 * similar to compose, maybe it'll help anyways through the IDE
 *
 */

fun <T, U, V> higherCompose(): ((U) -> V) -> ((T) -> U) -> (T) -> V = {
    f -> {
        g -> {
            x -> f(g(x))
        }
    }
}

