package com.jeanbarrossilva.gangame.story.node.branched

class NonexistentBranchException internal constructor(parentNodeID: String, branchID: String):
    IllegalArgumentException("Cannot point to a nonexistent \"$branchID\" branch from \"$parentNodeID\".")