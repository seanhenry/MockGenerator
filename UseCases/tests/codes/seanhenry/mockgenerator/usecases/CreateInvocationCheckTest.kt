package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase
import org.junit.Assert

class CreateInvocationCheckTest : TestCase() {

  var createCheck: CreateInvocationCheck? = null

  override fun setUp() {
    super.setUp()
    createCheck = CreateInvocationCheck(false)
  }

  fun testTransformsName() {
    Assert.assertEquals("invokedName", createCheck?.transform("name")?.name)
  }

  fun testTransformsLongName() {
    Assert.assertEquals("invokedLongName", createCheck?.transform("longName")?.name)
  }

  fun testTransformsAlreadyCapitalized() {
    Assert.assertEquals("invokedURL", createCheck?.transform("URL")?.name)
  }

  fun testAddsFalseInitializer() {
    createCheck = CreateInvocationCheck(false)
    Assert.assertEquals(false, createCheck?.transform("name")?.initializer)
  }

  fun testAddsTrueInitializer() {
    createCheck = CreateInvocationCheck(true)
    Assert.assertEquals(true, createCheck?.transform("name")?.initializer)
  }
}
