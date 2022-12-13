package com.jeanbarrossilva.gangame.story.node.branched

import com.jeanbarrossilva.gangame.story.node.Node

fun interface OnPointingListener {
    fun onPointTo(branch: Node)
}