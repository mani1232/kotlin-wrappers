package web.geolocation

import js.coroutines.internal.internalSubscribeJob
import js.iterable.SuspendableIterator
import js.objects.unsafeJso
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GeolocationPositionInstance(
    internal val geolocation: Geolocation,
)

// once
suspend fun GeolocationPositionInstance.once(): GeolocationPosition =
    suspendCoroutine { continuation ->
        geolocation.getCurrentPosition(
            successCallback = continuation::resume,
            // TODO: implement error processing
            // errorCallback = continuation::resumeWithError,
        )
    }

// iterator
suspend operator fun GeolocationPositionInstance.iterator(): SuspendableIterator<GeolocationPosition> =
    SuspendableIterator(asChannel().iterator())

// channel
internal suspend fun GeolocationPositionInstance.asChannel(
    options: PositionOptions? = null,
): ReceiveChannel<GeolocationPosition> {
    val channel = Channel<GeolocationPosition>()

    val job = internalSubscribeJob {
        val watchId = geolocation.watchPosition(
            successCallback = channel::trySend,
            // TODO: implement error processing
            // errorCallback = channel.trySend(),
            options = options ?: unsafeJso(),
        )

        return@internalSubscribeJob {
            geolocation.clearWatch(watchId)
        }
    }

    channel.invokeOnClose { job.cancel() }

    return channel
}

// asFlow
fun GeolocationPositionInstance.asFlow(): Flow<GeolocationPosition> =
    flow {
        for (event in asChannel()) {
            emit(event)
        }
    }
