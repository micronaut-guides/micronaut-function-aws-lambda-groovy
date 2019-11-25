package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.reactivex.Single

import javax.inject.Singleton

@CompileStatic
@Requires(env = Environment.TEST)
@Singleton
class VatValidatorMock implements VatValidator {

    @Override
    Single<VatValidation> validateVat(VatValidationRequest request) {
        List<String> validVatNumbers = Collections.singletonList("B84965375")
        Single.just(new VatValidation(memberStateCode: request.memberStateCode,
                                      vatNumber: request.vatNumber,
                                      valid: validVatNumbers.contains(request.vatNumber)))
    }
}
