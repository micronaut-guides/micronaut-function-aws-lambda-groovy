package example.micronaut

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest // <1>
class InvoiceControllerSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient rxHttpClient // <2>

    @Inject
    EmbeddedServer server // <3>

    void "test InvoiceController"() {
        when:
        VatValidator bean = server.getApplicationContext().getBean(VatValidator.class)

        then:
        noExceptionThrown()
        bean instanceof VatValidatorMock // <4>

        when:
        Invoice invoice = new Invoice(vatNumber: "B84965375", countryCode: "es", lines: [
                new InvoiceLine(productId: "1491950358", count: 2, price: new BigDecimal(19.99)),
                new InvoiceLine(productId: "1680502395", count: 1, price: new BigDecimal(15)),
        ])
        HttpRequest request = HttpRequest.POST("/invoice/vat", invoice)
        Taxes rsp = rxHttpClient.toBlocking().retrieve(request, Taxes.class)
        BigDecimal expected = new BigDecimal("11.55")

        then:
        expected == rsp.vat

        when:
        invoice.setVatNumber("B99999999")
        rsp = rxHttpClient.toBlocking().retrieve(request, Taxes.class)
        expected = new BigDecimal("0.00")

        then:
        expected == rsp.vat
    }
}
