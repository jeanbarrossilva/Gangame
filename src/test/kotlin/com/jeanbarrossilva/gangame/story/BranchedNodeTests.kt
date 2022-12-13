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
        val branch = node("branch")
        var isPointingToBranch = false
        val node = branchedNode("branched-node") {
            branch(branch)
            onPointing { isPointingToBranch = it == branch.id }
        }
        node.pointTo(branch.id)
        assertTrue(isPointingToBranch)
    }

    @Test
    fun `GIVEN a nonexistent branch WHEN pointing to it THEN it throws`() {
        assertFailsWith<NonexistentBranchException> {
            branchedNode("branched-node") {
                branch(node("first-branch"))
                branch(node("second-branch"))
                branch(node("third-branch"))
            }
                .pointTo("fourth-branch")
        }
    }
}