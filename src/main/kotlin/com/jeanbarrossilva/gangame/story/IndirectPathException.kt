package com.jeanbarrossilva.gangame.story

class IndirectPathException internal constructor(pathID: String):
    IllegalArgumentException("Path \"$pathID\" exists but cannot be advanced to from this point.")