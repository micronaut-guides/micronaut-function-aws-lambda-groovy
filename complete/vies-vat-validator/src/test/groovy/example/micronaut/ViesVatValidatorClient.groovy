package example.micronaut

import io.micronaut.function.client.FunctionClient
import io.micronaut.http.annotation.Body
import io.reactivex.Single

import javax.inject.Named

@FunctionClient // <1>
interface ViesVatValidatorClient {

    @Named("vies-vat-validator") // <2>
    Single<VatValidation> apply(@Body VatValidationRequest request) // <3>

}
