package example.micronaut

import groovy.transform.CompileStatic

@CompileStatic
class VatValidationRequest implements Serializable {
    String memberStateCode
    String vatNumber
}
