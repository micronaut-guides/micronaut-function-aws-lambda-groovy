package example.micronaut

import groovy.transform.Field
import javax.inject.Inject

@Field @Inject VatService vatService // <1>

VatValidation viesVatValidator(VatValidationRequest request) {  // <2>
    final String memberStateCode = request.getMemberStateCode()
    final String vatNumber = request.getVatNumber()
    vatService.validateVat(memberStateCode, vatNumber)
            .map { Boolean valid -> new VatValidation(memberStateCode: memberStateCode, vatNumber: vatNumber, valid: valid) }
            .blockingGet() // <3>
}