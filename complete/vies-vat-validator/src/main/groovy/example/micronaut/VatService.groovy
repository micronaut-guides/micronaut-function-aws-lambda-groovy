package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.Client
import io.micronaut.http.client.RxHttpClient
import io.reactivex.Flowable
import io.reactivex.Single

import javax.inject.Singleton

@CompileStatic
@Singleton // <1>
class VatService {
    private static final String SERVER = "http://ec.europa.eu"
    private static final String PATH = "/taxation_customs/vies/services/checkVatService"
    private static final String VALID_XML_OPEN_TAG = "<valid>"
    private static final String VALID_XML_CLOSE_TAG = "</valid>"

    protected final RxHttpClient client

    VatService(@Client("http://ec.europa.eu") RxHttpClient client) { // <2>
        this.client = client
    }

    Single<Boolean> validateVat(String memberStateCode, String vatNumber) {
        String soapEnvelope = soapEnvelope(memberStateCode, vatNumber)
        HttpRequest request = HttpRequest.POST("${SERVER}${PATH}".toString(), soapEnvelope)  // <3>
                .contentType("application/soap+xml")

        Flowable<String> response = client.retrieve(request, String.class)
        response.firstOrError().map(this.&parseResponseToBoolean) as Single<Boolean>
    }

    private Boolean parseResponseToBoolean(String response) {
        if (!response.contains(VALID_XML_OPEN_TAG) || !response.contains(VALID_XML_CLOSE_TAG)) {
            return false
        }
        int beginIndex = response.indexOf(VALID_XML_OPEN_TAG) + VALID_XML_OPEN_TAG.length()
        String validResponse = response.substring(beginIndex, response.indexOf(VALID_XML_CLOSE_TAG))
        Boolean.valueOf(validResponse)
    }

    private String soapEnvelope(String memberStateCode, String vatNumber) {
        """\
<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">
<soapenv:Header/>
<soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">
<urn:checkVat xmlns:urn=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">
<urn:countryCode>${memberStateCode}</urn:countryCode>
<urn:vatNumber>${vatNumber}</urn:vatNumber>
</urn:checkVat>
</soapenv:Body>
</soapenv:Envelope>"""
    }
}