package codes.seanhenry.mockgenerator.util

import junit.framework.TestCase
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GroupUtilTest : TestCase() {

  lateinit var groups: Map<Int, IntRange>
  lateinit var string: String

  fun testShouldReturnNoGroupsWhenEmptyString() {
    findGroups("")
    assertEmptyGroup()
  }

  fun testShouldReturnSimplestGroup() {
    findGroups("()")
    assertGroup(IntRange(0, 1), "()", 0)
  }

  fun testShouldReturnNoGroupsWhenGroupDoesNotComplete() {
    findGroups("(")
    assertEmptyGroup()
  }

  fun testShouldReturnNoGroupsWhenGroupIsMalformed() {
    findGroups(")")
    assertEmptyGroup()
    findGroups(")(")
    assertEmptyGroup()
  }

  fun testShouldFindSideBySideGroups() {
    findGroups("()()()")
    assertGroup(0..1, "()", 0)
    assertGroup(2..3, "()", 1)
    assertGroup(4..5, "()", 2)
  }

  fun testShouldFindNestedGroups() {
    findGroups("(())")
    assertGroup(0..3, "(())", 0)
    assertGroup(1..2, "()", 1)
  }

  fun testShouldFindNestedGroupsAndSideBySideGroups() {
    findGroups("(()())")
    assertGroup(0..5, "(()())", 0)
    assertGroup(1..2, "()", 1)
    assertGroup(3..4, "()", 2)
  }

  fun testShouldFindSideBySideAndNestedGroups() {
    findGroups("(()())(()())")
    assertGroup(0..5, "(()())", 0)
    assertGroup(1..2, "()", 1)
    assertGroup(3..4, "()", 2)
    assertGroup(6..11, "(()())", 3)
    assertGroup(7..8, "()", 4)
    assertGroup(9..10, "()", 5)
  }

  fun testShouldFindGroupsWithOtherCharactersInBetween() {
    findGroups("((String, String) -> (Int))?")
    assertGroup(0..26, "((String, String) -> (Int))", 0)
    assertGroup(1..16, "(String, String)", 1)
    assertGroup(21..25, "(Int)", 2)
  }

  fun testShouldAttemptToAddRangesForPartialGroups() {
    findGroups("(()()")
    assertNull(groups[0])
    assertGroup(1..2, "()", 1)
    assertGroup(3..4, "()", 2)
  }

  private fun assertGroup(expectedRange: IntRange, expectedSubstring: String, index: Int) {
    assertEquals(expectedRange, groups[index])
    assertEquals(expectedSubstring, string.substring(expectedRange))
  }

  private fun assertEmptyGroup() {
    assert(groups.isEmpty())
  }

  private fun findGroups(string: String) {
    this.string = string
    groups = GroupUtil('(', ')').findGroups(string)
  }
}
