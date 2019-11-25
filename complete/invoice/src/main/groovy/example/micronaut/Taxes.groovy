package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Introspected

@CompileStatic
@Introspected
class Taxes {
    BigDecimal vat

    Taxes() {}
}
