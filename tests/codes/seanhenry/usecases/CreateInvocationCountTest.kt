package codes.seanhenry.usecases

import junit.framework.TestCase
import org.junit.Assert

class CreateInvocationCountTest: TestCase() {

  fun testTransformsToProperty() {
    val createCount = CreateInvocationCount()
    val property = createCount.transform("name")
    Assert.assertEquals("invokedNameCount", property.name)
    Assert.assertEquals(0, property.initializer)
  }

  fun testTransformsToPropertyWithLongName() {
    val createCount = CreateInvocationCount()
    val property = createCount.transform("longName")
    Assert.assertEquals("invokedLongNameCount", property.name)
  }

  fun testTransformsToPropertyWithCapitalizedName() {
    val createCount = CreateInvocationCount()
    val property = createCount.transform("URL")
    Assert.assertEquals("invokedURLCount", property.name)
  }
}
