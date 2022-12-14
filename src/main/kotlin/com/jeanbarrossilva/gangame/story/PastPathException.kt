package com.jeanbarrossilva.gangame.story

class PastPathException internal constructor(pathID: String):
    IllegalArgumentException("Cannot go back to path \"$pathID\".")