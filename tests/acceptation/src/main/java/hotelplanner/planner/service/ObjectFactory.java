
package hotelplanner.planner.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the planner.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetHotels_QNAME = new QName("http://service.planner/", "getHotels");
    private final static QName _GetHotelsForTravel_QNAME = new QName("http://service.planner/", "getHotelsForTravel");
    private final static QName _GetHotelsForTravelResponse_QNAME = new QName("http://service.planner/", "getHotelsForTravelResponse");
    private final static QName _GetHotelsResponse_QNAME = new QName("http://service.planner/", "getHotelsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: planner.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetHotels }
     * 
     */
    public GetHotels createGetHotels() {
        return new GetHotels();
    }

    /**
     * Create an instance of {@link GetHotelsForTravel }
     * 
     */
    public GetHotelsForTravel createGetHotelsForTravel() {
        return new GetHotelsForTravel();
    }

    /**
     * Create an instance of {@link GetHotelsForTravelResponse }
     * 
     */
    public GetHotelsForTravelResponse createGetHotelsForTravelResponse() {
        return new GetHotelsForTravelResponse();
    }

    /**
     * Create an instance of {@link GetHotelsResponse }
     * 
     */
    public GetHotelsResponse createGetHotelsResponse() {
        return new GetHotelsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotels }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.planner/", name = "getHotels")
    public JAXBElement<GetHotels> createGetHotels(GetHotels value) {
        return new JAXBElement<GetHotels>(_GetHotels_QNAME, GetHotels.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotelsForTravel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.planner/", name = "getHotelsForTravel")
    public JAXBElement<GetHotelsForTravel> createGetHotelsForTravel(GetHotelsForTravel value) {
        return new JAXBElement<GetHotelsForTravel>(_GetHotelsForTravel_QNAME, GetHotelsForTravel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotelsForTravelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.planner/", name = "getHotelsForTravelResponse")
    public JAXBElement<GetHotelsForTravelResponse> createGetHotelsForTravelResponse(GetHotelsForTravelResponse value) {
        return new JAXBElement<GetHotelsForTravelResponse>(_GetHotelsForTravelResponse_QNAME, GetHotelsForTravelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotelsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.planner/", name = "getHotelsResponse")
    public JAXBElement<GetHotelsResponse> createGetHotelsResponse(GetHotelsResponse value) {
        return new JAXBElement<GetHotelsResponse>(_GetHotelsResponse_QNAME, GetHotelsResponse.class, null, value);
    }

}
