package example.micronaut

import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@MicronautTest // <1>
class ViesVatValidatorFunctionSpec extends Specification {

    @Inject
    ViesVatValidatorClient client // <2>

    @Unroll("#code #vatNumber is #description")
    void testViesVatValidatorFunction(String code, String vatNumber, boolean expected, String description) throws Exception {
        when:
        VatValidationRequest req = new VatValidationRequest(memberStateCode: code, vatNumber: vatNumber)

        then:
        expected == client.apply(req).blockingGet().valid

        where:
        code | vatNumber   | expected
        "es" | "B99286353" | true
        "es" | "B19280031" | true
        "es" | "XXXXXXXXX" | false

        description = expected ? 'is valid' : 'is invalid'
    }
}
