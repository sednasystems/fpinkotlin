/**
 * Not falling for the code instructions which say 'value function' again!!
 */

fun <T, U, V> higherAndThen(): ((T) -> U) -> ((U) -> V) -> (T) -> V =
        { f ->
            { g ->
                { x -> g(f(x)) }
            }
        }