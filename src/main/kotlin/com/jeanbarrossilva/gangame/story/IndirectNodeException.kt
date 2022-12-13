package com.jeanbarrossilva.gangame.story

class IndirectNodeException internal constructor(nodeID: String):
    IllegalArgumentException("Node \"$nodeID\" exists but cannot be accessed from this point.")