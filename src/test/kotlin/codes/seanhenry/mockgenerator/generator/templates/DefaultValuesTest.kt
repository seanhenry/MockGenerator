package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class DefaultValuesTest : MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Property.Builder("int")
            .type("Int")
            .build(),
        Property.Builder("opt")
            .type().optional { it.verbose().type("Int") }
            .build(),
        Property.Builder("shortOptional")
            .type().optional { it.type("Int") }
            .build(),
        Property.Builder("doubleOptional")
            .type().optional { it.type().optional { it.type("Int") } }
            .build(),
        Property.Builder("iuo")
            .type().optional { it.unwrapped().type("Int") }
            .build()
    )
    generator.add(
        Method.Builder("optionalInt")
            .returnType().optional { it.type("Int") }
            .build(),
        Method.Builder("iuoInt")
            .returnType().optional { it.unwrapped().type("Int") }
            .build(),
        Method.Builder("double")
            .returnType("Double")
            .build(),
        Method.Builder("float")
            .returnType("Float")
            .build(),
        Method.Builder("int16")
            .returnType("Int16")
            .build(),
        Method.Builder("int32")
            .returnType("Int32")
            .build(),
        Method.Builder("int64")
            .returnType("Int64")
            .build(),
        Method.Builder("int8")
            .returnType("Int8")
            .build(),
        Method.Builder("uInt")
            .returnType("UInt")
            .build(),
        Method.Builder("uInt16")
            .returnType("UInt16")
            .build(),
        Method.Builder("uInt32")
            .returnType("UInt32")
            .build(),
        Method.Builder("uInt64")
            .returnType("UInt64")
            .build(),
        Method.Builder("uInt8")
            .returnType("UInt8")
            .build(),
        Method.Builder("array")
            .returnType().generic("Array") { it.argument("String") }
            .build(),
        Method.Builder("arrayLiteral")
            .returnType().array { it.type("String") }
            .build(),
        Method.Builder("arraySlice")
            .returnType().generic("ArraySlice") { it.argument("String") }
            .build(),
        Method.Builder("contiguousArray")
            .returnType().generic("ContiguousArray") { it.argument("String") }
            .build(),
        Method.Builder("set")
            .returnType().generic("Set") { it.argument("String") }
            .build(),
        Method.Builder("optionalArray")
            .returnType().optional { opt ->
              opt.verbose()
                  .type().array { it.verbose().type("String") }
            }
            .build(),
        Method.Builder("shortOptionalArray")
            .returnType().optional { it.type().array { it.type("String") } }
            .build(),
        Method.Builder("dictionary")
            .returnType().dictionary { dict ->
              dict.keyType("String")
                  .valueType("String")
                  .verbose()
            }
            .build(),
        Method.Builder("dictionaryLiteral")
            .returnType().generic("DictionaryLiteral") { gen ->
              gen.argument("String")
                  .argument("String")
            }
            .build(),
        Method.Builder("dictionaryShorthand")
            .returnType().dictionary { dict ->
              dict.keyType("String")
                  .valueType("String")
            }
            .build(),
        Method.Builder("optionalDict")
            .returnType().optional { opt ->
              opt.verbose().type().generic("Dictionary") { gen ->
                gen.argument("String")
                    .argument("String")
              }
            }
            .build(),
        Method.Builder("shortOptionalDict")
            .returnType().optional { opt ->
              opt.type().dictionary { dict ->
                dict.keyType("String")
                    .valueType("String")
              }
            }
            .build(),
        Method.Builder("bool")
            .returnType("Bool")
            .build(),
        Method.Builder("unicodeScalar")
            .returnType("UnicodeScalar")
            .build(),
        Method.Builder("character")
            .returnType("Character")
            .build(),
        Method.Builder("staticString")
            .returnType("StaticString")
            .build(),
        Method.Builder("string")
            .returnType("String")
            .build()
    )
  }
}
