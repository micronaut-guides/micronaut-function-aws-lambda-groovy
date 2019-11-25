package example.micronaut

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import io.micronaut.core.annotation.Introspected

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@EqualsAndHashCode
@CompileStatic
@Introspected
class Invoice {

    @NotNull
    @NotBlank
    String vatNumber

    @NotNull
    @NotBlank
    String countryCode

    @NotEmpty
    List<InvoiceLine> lines
}
