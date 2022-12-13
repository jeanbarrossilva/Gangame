package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.branched.BranchedNode
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class BranchedNodeTests {
    @Test
    fun `GIVEN a branch WHEN pointing to it THEN the listener is notified`() {
        val branchID = "branch"
        var isPointingToBranch = false
        val node = BranchedNode.Builder()
            .id("branched-node")
            .branch(branchID)
            .listen { isPointingToBranch = it.id == branchID }
            .build()
        node.pointTo(branchID)
        assertTrue(isPointingToBranch)
    }

    @Test
    fun `GIVEN a nonexistent branch WHEN pointing to it THEN it throws`() {
        assertFailsWith<IllegalArgumentException> {
            BranchedNode.Builder()
                .id("branched-node")
                .branch("first-branch")
                .branch("second-branch")
                .branch("third-branch")
                .build()
                .pointTo("fourth-branch")
        }
    }
}