package com.jeanbarrossilva.gangame.story

import com.jeanbarrossilva.gangame.story.node.branched.NonexistentBranchException
import com.jeanbarrossilva.gangame.story.node.branched.branchedNode
import com.jeanbarrossilva.gangame.story.node.node
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class BranchedNodeTests {
    @Test
    fun `GIVEN a branch WHEN pointing to it THEN the listener is notified`() {
        val branch = node { id("branch") }
        var isPointingToBranch = false
        val node = branchedNode {
            id("branched-node")
            branch(branch)
            onPointing { isPointingToBranch = it == branch.id }
        }
        node.pointTo(branch.id)
        assertTrue(isPointingToBranch)
    }

    @Test
    fun `GIVEN a nonexistent branch WHEN pointing to it THEN it throws`() {
        assertFailsWith<NonexistentBranchException> {
            branchedNode {
                id("branched-node")
                branch(node { id("first-branch") })
                branch(node { id("second-branch") })
                branch(node { id("third-branch") })
            }
                .pointTo("fourth-branch")
        }
    }
}