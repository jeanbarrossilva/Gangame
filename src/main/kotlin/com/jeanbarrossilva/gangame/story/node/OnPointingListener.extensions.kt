package com.jeanbarrossilva.gangame.story.node

internal fun Collection<OnPointingListener>.notifyAll(branchID: String) {
    forEach {
        it.onPointTo(branchID)
    }
}