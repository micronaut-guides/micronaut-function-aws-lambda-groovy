package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Introspected

@CompileStatic
@Introspected
class VatValidationRequest implements Serializable {
    String memberStateCode
    String vatNumber
}
