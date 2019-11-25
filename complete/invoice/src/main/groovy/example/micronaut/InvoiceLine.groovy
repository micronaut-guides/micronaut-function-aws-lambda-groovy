package example.micronaut

import groovy.transform.EqualsAndHashCode
import io.micronaut.core.annotation.Introspected

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@EqualsAndHashCode
@Introspected
class InvoiceLine {
    @NotNull
    @NotBlank
    String productId

    @Positive
    Integer count

    @NotNull
    @Positive
    BigDecimal price

    BigDecimal vatPrice(BigDecimal vatPercentage) {
        getPrice().multiply(BigDecimal.valueOf(getCount()).multiply(vatPercentage))
    }
}
