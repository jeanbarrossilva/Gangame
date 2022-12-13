package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.Node
import com.jeanbarrossilva.gangame.story.node.branched.BranchedNode
import com.jeanbarrossilva.gangame.story.node.branched.NonexistentBranchException
import com.jeanbarrossilva.gangame.story.node.node
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class BranchedNodeTests {
    @Test
    fun `GIVEN a branch WHEN pointing to it THEN the listener is notified`() {
        val branch = Node.Builder().id("branch").build()
        var isPointingToBranch = false
        val node = BranchedNode.Builder()
            .id("branched-node")
            .branch(branch)
            .onPointing { isPointingToBranch = it == branch.id }
            .build()
        node.pointTo(branch.id)
        assertTrue(isPointingToBranch)
    }

    @Test
    fun `GIVEN a nonexistent branch WHEN pointing to it THEN it throws`() {
        assertFailsWith<NonexistentBranchException> {
            BranchedNode.Builder()
                .id("branched-node")
                .branch(node { id("first-branch") })
                .branch(node { id("second-branch") })
                .branch(node { id("third-branch") })
                .build()
                .pointTo("fourth-branch")
        }
    }
}