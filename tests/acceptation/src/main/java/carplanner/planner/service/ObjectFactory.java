
package carplanner.planner.service;

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

    private final static QName _GetAllCars_QNAME = new QName("http://service.planner/", "getAllCars");
    private final static QName _GetAllCarsResponse_QNAME = new QName("http://service.planner/", "getAllCarsResponse");
    private final static QName _GetCarByPlace_QNAME = new QName("http://service.planner/", "getCarByPlace");
    private final static QName _GetCarByPlaceResponse_QNAME = new QName("http://service.planner/", "getCarByPlaceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: planner.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllCars }
     * 
     */
    public GetAllCars createGetAllCars() {
        return new GetAllCars();
    }

    /**
     * Create an instance of {@link GetAllCarsResponse }
     * 
     */
    public GetAllCarsResponse createGetAllCarsResponse() {
        return new GetAllCarsResponse();
    }

    /**
     * Create an instance of {@link GetCarByPlace }
     * 
     */
    public GetCarByPlace createGetCarByPlace() {
        return new GetCarByPlace();
    }

    /**
     * Create an instance of {@link GetCarByPlaceResponse }
     * 
     */
    public GetCarByPlaceResponse createGetCarByPlaceResponse() {
        return new GetCarByPlaceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllCars }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.planner/", name = "getAllCars")
    public JAXBElement<GetAllCars> createGetAllCars(GetAllCars value) {
        return new JAXBElement<GetAllCars>(_GetAllCars_QNAME, GetAllCars.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllCarsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.planner/", name = "getAllCarsResponse")
    public JAXBElement<GetAllCarsResponse> createGetAllCarsResponse(GetAllCarsResponse value) {
        return new JAXBElement<GetAllCarsResponse>(_GetAllCarsResponse_QNAME, GetAllCarsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCarByPlace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.planner/", name = "getCarByPlace")
    public JAXBElement<GetCarByPlace> createGetCarByPlace(GetCarByPlace value) {
        return new JAXBElement<GetCarByPlace>(_GetCarByPlace_QNAME, GetCarByPlace.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCarByPlaceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.planner/", name = "getCarByPlaceResponse")
    public JAXBElement<GetCarByPlaceResponse> createGetCarByPlaceResponse(GetCarByPlaceResponse value) {
        return new JAXBElement<GetCarByPlaceResponse>(_GetCarByPlaceResponse_QNAME, GetCarByPlaceResponse.class, null, value);
    }

}
