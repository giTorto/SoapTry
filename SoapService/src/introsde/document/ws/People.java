package introsde.document.ws;

import introsde.document.model.Measure;
import introsde.document.model.Person;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * Created by gt on 11/20/14.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use= SOAPBinding.Use.LITERAL) //optional
public interface People {
    @WebMethod(operationName="readPerson")
    @WebResult(name="person")
    public Person readPerson(@WebParam(name="personId") Long id);

    @WebMethod(operationName="readPersonList")
    @WebResult(name="people")
    public List<Person> readPersonList();

    @WebMethod(operationName="createPerson")
    @WebResult(name="person")
    public Person createPerson(@WebParam(name = "person") Person person);

    @WebMethod(operationName="updatePerson")
    @WebResult(name="person")
    public Person updatePerson(@WebParam(name = "person") Person person);

    @WebMethod(operationName="deletePerson")
    @WebResult(name="deleted")
    public void deletePerson(@WebParam(name="personId") Long id);

    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="healthProfile-History")
    public List<Measure> readPersonHistory(@WebParam(name="id") Long id, @WebParam(name="measureType") String measureType);

    @WebMethod(operationName="readPersonMeasurement")
    @WebResult(name="measure")
    public Measure readPersonMeasurement(@WebParam(name="id") Long id, @WebParam(name="measureType") String measureType, @WebParam(name="mid") Long mid);

    @WebMethod(operationName="savePersonMeasurement")
    @WebResult(name="measure")
    public Measure savePersonMeasurement(@WebParam(name="id") Long id, @WebParam(name="m") Measure m);

    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measureTypes")
    public List<String> readMeasureTypes();

    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="measure")
    public Measure updatePersonMeasure(@WebParam(name="id") Long id, @WebParam(name="m") Measure m) ;

    @WebMethod(operationName="readPersonListByMeasurement")
    @WebResult(name="people")
    public List<Person> readPersonListByMeasurement(@WebParam(name="measureType") String measureType, @WebParam(name="maxValue") String maxValue, @WebParam(name="minValue") String minValue);

    @WebMethod(operationName="readPersonMeasureByDates")
    @WebResult(name="healthProfile-History")
    public List<Measure> readPersonMeasureByDates(@WebParam(name="id") Long id, @WebParam(name="measureType") String measureType, @WebParam(name="before") Date before, @WebParam(name="after") Date after);

}

