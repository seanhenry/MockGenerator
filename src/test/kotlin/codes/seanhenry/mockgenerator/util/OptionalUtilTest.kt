package codes.seanhenry.mockgenerator.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OptionalUtilTest {

    @Test
    fun testShouldBeTrueWhenEndsInQuestionMark() {
        assertIsOptional(true, "Type?")
        assertIsOptional(true, "Type!?")
        assertIsOptional(true, "Type?? ")
    }

    @Test
    fun testShouldBeTrueWhenEndsInExclamationMark() {
        assertIsOptional(true, "Type!")
        assertIsOptional(true, "Type?!")
        assertIsOptional(true, "Type!! ")
    }

    @Test
    fun testShouldBeTrueWhenOptionalType() {
        assertIsOptional(true, "Optional<Type>")
        assertIsOptional(true, "Optional  < Type >")
        assertIsOptional(true, "Optional\t < Type >")
        assertIsOptional(true, "Optional\n < Type >")
    }

    @Test
    fun testShouldBeFalseWhenConcreteType() {
        assertIsOptional(false, "Type")
        assertIsOptional(false, "Type ")
    }

    @Test
    fun testShouldRemoveLastOptional() {
        assertRemoveOptional("Type", "Type?")
        assertRemoveOptional("Type", "Type!")
    }

    @Test
    fun testShouldOnlyRemoveLastOptional() {
        assertRemoveOptional("Type?", "Type??")
        assertRemoveOptional("Type?", "Type?!")
        assertRemoveOptional("Type!", "Type!!")
        assertRemoveOptional("Type!", "Type!?")
    }

    @Test
    fun testShouldRemoveAllOptionals() {
        assertRemoveOptionalRecursively("Type", "Type??")
        assertRemoveOptionalRecursively("Type", "Type?!")
        assertRemoveOptionalRecursively("Type", "Type!!")
        assertRemoveOptionalRecursively("Type", "Type!?")
    }

    private fun assertIsOptional(expected: Boolean, type: String) {
        assertEquals(expected, OptionalUtil.isOptional(type))
    }

    private fun assertRemoveOptional(expected: String, type: String) {
        assertEquals(expected, OptionalUtil.removeOptional(type))
    }

    private fun assertRemoveOptionalRecursively(expected: String, type: String) {
        assertEquals(expected, OptionalUtil.removeOptionalRecursively(type))
    }
}
