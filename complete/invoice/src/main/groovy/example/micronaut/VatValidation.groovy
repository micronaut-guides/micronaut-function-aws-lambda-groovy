package example.micronaut

import groovy.transform.CompileStatic

@CompileStatic
class VatValidation extends VatValidationRequest implements Serializable {
    Boolean valid
}
