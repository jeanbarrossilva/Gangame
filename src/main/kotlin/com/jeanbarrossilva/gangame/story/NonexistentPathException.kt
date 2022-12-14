package com.jeanbarrossilva.gangame.story

class NonexistentPathException internal constructor(pathID: String):
    IllegalArgumentException("Cannot advance to a nonexistent \"$pathID\" path.")