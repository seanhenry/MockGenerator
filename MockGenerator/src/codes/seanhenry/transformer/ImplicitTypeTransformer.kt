package codes.seanhenry.transformer

import com.jetbrains.swift.psi.SwiftInitializer
import com.jetbrains.swift.psi.SwiftRecursiveVisitor

class ImplicitTypeTransformer: SwiftRecursiveVisitor() {

  override fun visitInitializer(p0: SwiftInitializer) {
    super.visitInitializer(p0)
  }
}
