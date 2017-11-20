package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase
import org.junit.Assert

class CreateInvocationCheckTest : TestCase() {

  var createCheck: CreateInvocationCheck? = null

  override fun setUp() {
    super.setUp()
    createCheck = CreateInvocationCheck()
  }

  override fun tearDown() {
    createCheck = null
    super.tearDown()
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

  fun testSetsBooleanType() {
    createCheck = CreateInvocationCheck()
    Assert.assertEquals("Bool", createCheck?.transform("name")?.type)
  }
}
