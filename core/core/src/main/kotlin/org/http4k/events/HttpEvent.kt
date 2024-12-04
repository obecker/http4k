package org.http4k.events

import org.http4k.core.HttpTransaction
import org.http4k.core.Method
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.routing.RoutedMessage
import org.http4k.routing.RoutedRequest

sealed class HttpEvent(
    uri: Uri,
    val method: Method,
    status: Status,
    latency: Long,
    xUriTemplate: String,
) : ProtocolEvent(uri, ProtocolStatus(status.code, status.description, status.clientGenerated), latency, xUriTemplate, "http") {
    class Incoming(
        uri: Uri,
        method: Method,
        status: Status,
        latency: Long,
        xUriTemplate: String,
    ) : HttpEvent(uri, method, status, latency, xUriTemplate) {
        constructor(tx: HttpTransaction) : this(
            tx.request.uri,
            tx.request.method,
            tx.response.status,
            tx.duration.toMillis(),
            if (tx.request is RoutedRequest) tx.request.xUriTemplate.toString() else tx.request.uri.path.trimStart('/')
        )

        override fun toString() =
            "Incoming(uri=$uri, method=$method, status=$status, latency=$latency, xUriTemplate=$xUriTemplate, protocol=$protocol)"

        companion object
    }

    class Outgoing(
        uri: Uri,
        method: Method,
        status: Status,
        latency: Long,
        xUriTemplate: String,
    ) : HttpEvent(uri, method, status, latency, xUriTemplate) {
        constructor(tx: HttpTransaction) : this(
            tx.request.uri,
            tx.request.method,
            tx.response.status,
            tx.duration.toMillis(),
            if (tx.response is RoutedMessage) tx.response.xUriTemplate.toString() else tx.request.uri.path.trimStart('/')
        )

        override fun toString() =
            "Outgoing(uri=$uri, method=$method, status=$status, latency=$latency, xUriTemplate=$xUriTemplate, protocol=$protocol)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpEvent

        if (uri != other.uri) return false
        if (method != other.method) return false
        if (status != other.status) return false
        if (latency != other.latency) return false
        if (xUriTemplate != other.xUriTemplate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uri.hashCode()
        result = 31 * result + method.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + latency.hashCode()
        result = 31 * result + xUriTemplate.hashCode()
        return result
    }
}
