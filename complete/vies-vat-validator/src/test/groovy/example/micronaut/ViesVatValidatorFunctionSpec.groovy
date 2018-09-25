package example.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.Specification
import spock.lang.Unroll

class ViesVatValidatorFunctionSpec extends Specification {

    @Unroll("#code #vatNumber is #description")
    void testViesVatValidatorFunction(String code, String vatNumber, boolean expected, String description) throws Exception {
        given:
        EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class)

        when:
        ViesVatValidatorClient client = server.getApplicationContext().getBean(ViesVatValidatorClient.class)

        then:
        noExceptionThrown()

        when:
        VatValidationRequest req = new VatValidationRequest(memberStateCode: code, vatNumber: vatNumber)

        then:
        expected == client.apply(req).blockingGet().valid

        cleanup:
        server.stop()
        server.close()

        where:
        code | vatNumber   | expected
        "es" | "B99286353" | true
        "es" | "B19280031" | true
        "es" | "XXXXXXXXX" | false

        description = expected ? 'is valid' : 'is invalid'

    }
}
