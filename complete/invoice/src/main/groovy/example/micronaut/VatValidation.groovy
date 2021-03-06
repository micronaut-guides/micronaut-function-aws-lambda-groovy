package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Introspected

@CompileStatic
@Introspected
class VatValidation extends VatValidationRequest implements Serializable {
    Boolean valid
}
