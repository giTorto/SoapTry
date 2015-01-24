
package introsde.document.ws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "People", targetNamespace = "http://ws.document.introsde/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface People {


    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(name = "MeasureTypes", targetNamespace = "")
    @RequestWrapper(localName = "readMeasureTypes", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadMeasureTypes")
    @ResponseWrapper(localName = "readMeasureTypesResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadMeasureTypesResponse")
    @Action(input = "http://ws.document.introsde/People/readMeasureTypesRequest", output = "http://ws.document.introsde/People/readMeasureTypesResponse")
    public List<String> readMeasureTypes();

    /**
     * 
     * @param measureType
     * @param personId
     * @param mid
     * @return
     *     returns java.util.List<introsde.document.ws.HealthMeasureHistory>
     */
    @WebMethod
    @WebResult(name = "value", targetNamespace = "")
    @RequestWrapper(localName = "readPersonMeasurement", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonMeasurement")
    @ResponseWrapper(localName = "readPersonMeasurementResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonMeasurementResponse")
    @Action(input = "http://ws.document.introsde/People/readPersonMeasurementRequest", output = "http://ws.document.introsde/People/readPersonMeasurementResponse")
    public List<HealthMeasureHistory> readPersonMeasurement(
        @WebParam(name = "personId", targetNamespace = "")
        int personId,
        @WebParam(name = "measureType", targetNamespace = "")
        String measureType,
        @WebParam(name = "mid", targetNamespace = "")
        int mid);

    /**
     * 
     * @param after
     * @param before
     * @param measureType
     * @param personId
     * @return
     *     returns java.util.List<introsde.document.ws.HealthMeasureHistory>
     */
    @WebMethod
    @WebResult(name = "HealthMeasureHistory", targetNamespace = "")
    @RequestWrapper(localName = "readPersonMeasureByDates", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonMeasureByDates")
    @ResponseWrapper(localName = "readPersonMeasureByDatesResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonMeasureByDatesResponse")
    @Action(input = "http://ws.document.introsde/People/readPersonMeasureByDatesRequest", output = "http://ws.document.introsde/People/readPersonMeasureByDatesResponse")
    public List<HealthMeasureHistory> readPersonMeasureByDates(
        @WebParam(name = "personId", targetNamespace = "")
        int personId,
        @WebParam(name = "measureType", targetNamespace = "")
        String measureType,
        @WebParam(name = "Before", targetNamespace = "")
        String before,
        @WebParam(name = "After", targetNamespace = "")
        String after);

    /**
     * 
     * @param minValue
     * @param measuretype
     * @param maxValue
     * @return
     *     returns java.util.List<introsde.document.ws.Person>
     */
    @WebMethod
    @WebResult(name = "Person", targetNamespace = "")
    @RequestWrapper(localName = "readPersonListByMeasurement", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonListByMeasurement")
    @ResponseWrapper(localName = "readPersonListByMeasurementResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonListByMeasurementResponse")
    @Action(input = "http://ws.document.introsde/People/readPersonListByMeasurementRequest", output = "http://ws.document.introsde/People/readPersonListByMeasurementResponse")
    public List<Person> readPersonListByMeasurement(
        @WebParam(name = "measuretype", targetNamespace = "")
        String measuretype,
        @WebParam(name = "maxValue", targetNamespace = "")
        int maxValue,
        @WebParam(name = "minValue", targetNamespace = "")
        int minValue);

    /**
     * 
     * @param measureType
     * @param personId
     * @return
     *     returns java.util.List<introsde.document.ws.HealthMeasureHistory>
     */
    @WebMethod
    @WebResult(name = "HealthMeasureHistory", targetNamespace = "")
    @RequestWrapper(localName = "readPersonHistory", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonHistory")
    @ResponseWrapper(localName = "readPersonHistoryResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonHistoryResponse")
    @Action(input = "http://ws.document.introsde/People/readPersonHistoryRequest", output = "http://ws.document.introsde/People/readPersonHistoryResponse")
    public List<HealthMeasureHistory> readPersonHistory(
        @WebParam(name = "personId", targetNamespace = "")
        int personId,
        @WebParam(name = "measureType", targetNamespace = "")
        String measureType);

    /**
     * 
     * @return
     *     returns java.util.List<introsde.document.ws.Person>
     */
    @WebMethod
    @WebResult(name = "people", targetNamespace = "")
    @RequestWrapper(localName = "getPeopleList", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.GetPeopleList")
    @ResponseWrapper(localName = "getPeopleListResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.GetPeopleListResponse")
    @Action(input = "http://ws.document.introsde/People/getPeopleListRequest", output = "http://ws.document.introsde/People/getPeopleListResponse")
    public List<Person> getPeopleList();

    /**
     * 
     * @param personId
     */
    @WebMethod
    @RequestWrapper(localName = "deletePerson", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.DeletePerson")
    @ResponseWrapper(localName = "deletePersonResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.DeletePersonResponse")
    @Action(input = "http://ws.document.introsde/People/deletePersonRequest", output = "http://ws.document.introsde/People/deletePersonResponse")
    public void deletePerson(
        @WebParam(name = "personId", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Integer> personId);

    /**
     * 
     * @param personId
     * @return
     *     returns introsde.document.ws.Person
     */
    @WebMethod
    @WebResult(name = "person", targetNamespace = "http://ws.document.introsde/")
    @RequestWrapper(localName = "readPerson", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPerson")
    @ResponseWrapper(localName = "readPersonResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.ReadPersonResponse")
    @Action(input = "http://ws.document.introsde/People/readPersonRequest", output = "http://ws.document.introsde/People/readPersonResponse")
    public Person readPerson(
        @WebParam(name = "personId", targetNamespace = "")
        int personId);

    /**
     * 
     * @param person
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(name = "personId", targetNamespace = "")
    @RequestWrapper(localName = "updatePerson", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.UpdatePerson")
    @ResponseWrapper(localName = "updatePersonResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.UpdatePersonResponse")
    @Action(input = "http://ws.document.introsde/People/updatePersonRequest", output = "http://ws.document.introsde/People/updatePersonResponse")
    public int updatePerson(
        @WebParam(name = "person", targetNamespace = "http://ws.document.introsde/")
        Person person);

    /**
     * 
     * @param person
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(name = "personId", targetNamespace = "")
    @RequestWrapper(localName = "createPerson", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.CreatePerson")
    @ResponseWrapper(localName = "createPersonResponse", targetNamespace = "http://ws.document.introsde/", className = "introsde.document.ws.CreatePersonResponse")
    @Action(input = "http://ws.document.introsde/People/createPersonRequest", output = "http://ws.document.introsde/People/createPersonResponse")
    public int createPerson(
        @WebParam(name = "person", targetNamespace = "http://ws.document.introsde/")
        Person person);

}