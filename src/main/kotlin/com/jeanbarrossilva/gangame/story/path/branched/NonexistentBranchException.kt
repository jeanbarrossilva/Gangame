package com.jeanbarrossilva.gangame.story.path.branched

class NonexistentBranchException internal constructor(parentPathID: String, branchID: String):
    IllegalArgumentException("Cannot point to a nonexistent \"$branchID\" branch from \"$parentPathID\".")