package com.jeanbarrossilva.gangame.story

class NonexistentNodeException internal constructor(nodeID: String):
    IllegalArgumentException("Cannot point to a nonexistent \"$nodeID\" node.")