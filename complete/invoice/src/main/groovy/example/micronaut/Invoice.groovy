package example.micronaut

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@EqualsAndHashCode
@CompileStatic
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
