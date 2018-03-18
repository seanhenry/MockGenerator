package codes.seanhenry.mockgenerator.util

import junit.framework.TestCase

class OptionalUtilTest: TestCase() {

    fun testShouldBeTrueWhenEndsInQuestionMark() {
        assertIsOptional(true, "Type?")
        assertIsOptional(true, "Type!?")
        assertIsOptional(true, "Type?? ")
    }

    fun testShouldBeTrueWhenEndsInExclamationMark() {
        assertIsOptional(true, "Type!")
        assertIsOptional(true, "Type?!")
        assertIsOptional(true, "Type!! ")
    }

    fun testShouldBeTrueWhenOptionalType() {
        assertIsOptional(true, "Optional<Type>")
        assertIsOptional(true, "Optional  < Type >")
        assertIsOptional(true, "Optional\t < Type >")
        assertIsOptional(true, "Optional\n < Type >")
    }

    fun testShouldBeFalseWhenConcreteType() {
        assertIsOptional(false, "Type")
        assertIsOptional(false, "Type ")
    }

    fun testShouldRemoveLastOptional() {
        assertRemoveOptional("Type", "Type?")
        assertRemoveOptional("Type", "Type!")
    }

    fun testShouldOnlyRemoveLastOptional() {
        assertRemoveOptional("Type?", "Type??")
        assertRemoveOptional("Type?", "Type?!")
        assertRemoveOptional("Type!", "Type!!")
        assertRemoveOptional("Type!", "Type!?")
    }

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
