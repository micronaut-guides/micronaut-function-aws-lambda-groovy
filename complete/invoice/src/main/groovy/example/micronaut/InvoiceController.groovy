package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Value
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import io.reactivex.Single

import javax.validation.Valid

@CompileStatic
@Validated // <1>
@Controller("/invoice") // <2>
class InvoiceController {

    private final BigDecimal vatPercentage

    private final VatValidator vatValidator

    private final int scale

    InvoiceController(@Value('${vat.percentage}') BigDecimal vatPercentage, // <3>
                      @Value('${vat.scale}') int scale,
                      VatValidator vatValidator) { // <4>
        this.vatPercentage = vatPercentage
        this.scale = scale
        this.vatValidator = vatValidator
    }

    @Post("/vat") // <5>
    Single<Taxes> calculateVat(@Valid @Body Invoice invoice) {  // <6>
        vatValidator.validateVat(new VatValidationRequest(memberStateCode: invoice.countryCode, vatNumber: invoice.vatNumber)) // <7>
                .map { VatValidation vatValidation ->
                    BigDecimal percentage = vatValidation.valid ? vatPercentage : new BigDecimal("0")
                    new Taxes(vat: invoice.lines.stream()
                            .map { InvoiceLine line -> line.vatPrice(percentage) }
                            .reduce { BigDecimal a, BigDecimal b -> a.add(b) }
                            .get()
                            .setScale(scale, BigDecimal.ROUND_HALF_EVEN))
                }
    }
}
