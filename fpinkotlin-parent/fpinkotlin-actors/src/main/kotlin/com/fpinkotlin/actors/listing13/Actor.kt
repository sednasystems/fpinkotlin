package com.fpinkotlin.actors.listing13

import com.fpinkotlin.common.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


interface ActorContext<T> {

    fun behavior(): MessageProcessor<T>

    fun become(behavior: MessageProcessor<T>)
}

interface MessageProcessor<T> {

    fun process(message: T, sender: Result<Actor<T>>)
}

interface Actor<T> {

    val actorContext: ActorContext<T>

    fun self(): Result<Actor<T>> = Result(this)

    fun tell(message: T, sender: Result<Actor<T>> = self())

    fun shutdown()

    fun tell(message: T, sender: Actor<T>) = tell(message, Result(sender))

    companion object {

        fun <T> noSender(): Result<Actor<T>> = Result()
    }
}

abstract class AbstractActor<T>(protected val id: String) : Actor<T> {

    override val actorContext: ActorContext<T> = object: ActorContext<T> {

        var behavior: MessageProcessor<T> = object: MessageProcessor<T> {

            override fun process(message: T, sender: Result<Actor<T>>) {
                onReceive(message, sender)
            }
        }

        @Synchronized
        override fun become(behavior: MessageProcessor<T>) {
            this.behavior = behavior
        }

        override fun behavior() = behavior
    }

    abstract fun onReceive(message: T, sender: Result<Actor<T>>)

    override fun self(): Result<Actor<T>> {
        return Result(this)
    }

    private val dispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()

    override fun shutdown() {
        dispatcher.close()
    }

    @Synchronized
    override fun tell(message: T, sender: Result<Actor<T>>) {
        GlobalScope.launch(dispatcher) {
            actorContext.behavior().process(message, sender)
        }
    }
}
