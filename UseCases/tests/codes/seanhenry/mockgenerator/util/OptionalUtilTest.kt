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

    private fun assertIsOptional(expected: Boolean, type: String) {
        assertEquals(expected, OptionalUtil.isOptional(type))
    }
}
