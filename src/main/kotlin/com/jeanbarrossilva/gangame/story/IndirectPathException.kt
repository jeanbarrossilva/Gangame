package com.jeanbarrossilva.gangame.story

class IndirectPathException internal constructor(currentPathID: String?, destinationPathID: String):
    IllegalArgumentException(
        "Path \"$destinationPathID\" exists but cannot be advanced to from ${getPathDescription(currentPathID)}."
    ) {
    companion object {
        private fun getPathDescription(pathID: String?): String {
            return pathID?.let { "\"$it\"" } ?: "this point"
        }
    }
}