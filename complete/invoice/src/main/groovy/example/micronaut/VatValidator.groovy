package example.micronaut

import io.micronaut.http.annotation.Body
import io.reactivex.Single

interface VatValidator {

    Single<VatValidation> validateVat(@Body VatValidationRequest req) // <1>
}
