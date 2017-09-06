package codes.seanhenry.usecases

import junit.framework.TestCase
import org.junit.Assert

class CreateInvocationCheckTest : TestCase() {

  var createCheck: CreateInvocationCheck? = null

  override fun setUp() {
    super.setUp()
    createCheck = CreateInvocationCheck("name")
  }

  fun testTransformsName() {
    Assert.assertEquals("invokedName", createCheck?.transform()?.name)
  }

  fun testTransformsLongName() {
    createCheck = CreateInvocationCheck("longName")
    Assert.assertEquals("invokedLongName", createCheck?.transform()?.name)
  }

  fun testTransformsAlreadyCapitalized() {
    createCheck = CreateInvocationCheck("URL")
    Assert.assertEquals("invokedURL", createCheck?.transform()?.name)
  }

  fun testAddsInitializer() {
    Assert.assertEquals(false, createCheck?.transform()?.initializer)
  }
}
