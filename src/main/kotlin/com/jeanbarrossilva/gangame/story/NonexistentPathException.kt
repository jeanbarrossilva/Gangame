package com.jeanbarrossilva.gangame.story

class NonexistentPathException internal constructor(pathID: String):
    IllegalArgumentException("Cannot point to a nonexistent \"$pathID\" path.")