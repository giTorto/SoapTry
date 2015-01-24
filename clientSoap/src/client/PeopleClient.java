package client;

/**
 * Created by gt on 11/20/14.
 */


import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.GregorianCalendarConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import introsde.document.ws.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * This class manages all the request made to the server in order to test it.
 */
public class PeopleClient {
    static Integer errors = 0;
    static String header;
    static List<Object> dataSent = new ArrayList<Object>();
    static Long id = null;
    static List<Person> pList;
    static Person person;
    static Holder<Person> holder;
    static HealthMeasureHistory measure;

    public static void main(String[] args) throws Exception {

        PeopleService people = new PeopleService();
        People p = people.getPeopleImplPort();

        //method 1
        method1Request(p);

        //method 2
        method2Request(p);

        //method 3
        method3Request(p);

        //method 4
        method4Request(p);

        //method 5
        method5Request(p);

        //method 6
        method6Request(p);

        //method 7
        method7Request(p);

        //method 8
        /*method8Request(p);
/*
        //method 9
        method9Request(p);

        //Extra 2 Method # 10
        method10Request(p);

        //Extra 3 Method # 11
        method11Request(p);


        //Extra 4 Method # 12
        method12Request(p);
*/
        System.out.println("Total errors:"+ errors);
    }

    /**
     * This method allows to convert a date to a XMLGregorianCalendar
     * @param d the date to convert to the XMLGregorianCalendar
     * @return the XMLGregorianCalendar object from the given date
     */
    protected static XMLGregorianCalendar fromDateToCalendar(Date d) {
        GregorianCalendar data = new GregorianCalendar();
        data.setTime(d);
        XMLGregorianCalendar date2 = null;
        try {
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(data);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return date2;
    }

    /**
     * This method prints the received object and if there are errors or not
     * @param method the name of the method (e.g. "Method # 1", "Method # 2",..., "Method #12")
     * @param isCorrect if there are errors according to the value obtained from the expression
     * @param objectEvaluated the object retrieved from the server
     * @return 1 if there is an error, otherwise 0
     */
    protected static Integer printOnScreen(String method, boolean isCorrect, Object objectEvaluated) {
        printData(objectEvaluated);
        System.out.println();
        if(isCorrect){
            System.out.println(method+": OK");
            return 0;
        }

        System.out.println(method + ": ERROR");
        System.exit(0);
        return 1;

    }

    /**
     * This method allows print whatever object given
     * @param givenObject the object to print on system.out
     */
    private static void printData(Object givenObject) {
        XStream xStream= new XStream(new DomDriver());
        xStream.registerConverter(new GregorianCalendarConverter());
        xStream.alias("person", Person.class);
        xStream.alias("measure", Person.Measurements.class);
        xStream.alias("gregorianCalendar", XMLGregorianCalendarImpl.class);

        xStream.toXML(givenObject, System.out);

    }

    /**
     * This method is made to print the data sent to the server to make the request
     * @param header the name of the method (e.g. "Method # 1", "Method # 2",..., "Method #12")
     * @param list the list of object that are sent to the server
     */
    protected static void printSent(String header, List<Object> list) {
        System.out.println(header);
        System.out.println("Data SENT:");
        for(Object o : list){
            if(o!=null){
                printData(o);
                System.out.println();
            }
        }
    }

    /**
     * This method calls the first method required (readPersonList)
     * @param p
     */
    public static void method1Request(People p) {
        header ="Method # 1 "; //no data sent
        // make the request
        pList = p.getPeopleList();
        errors+= printOnScreen(header, pList!=null && pList.size()>1, pList);
        id = new Long(2);
    }

    /**
     * This method calls the second method required (readPerson)
     * @param p
     */
    public static void method2Request(People p) {
        header ="Method # 2 ";
        //print data sent
        dataSent.add(id); printSent(header, dataSent);

        //make request
        person = p.readPerson(1);

        //show the response
        errors += printOnScreen(header, person!=null && person.getIdPerson()>0, person);
    }

    /**
     * This method calls the third method required (updatePerson)
     * @param p
     */
    public static void method3Request(People p) {
        header ="Method # 3 ";
        person.setFirstname("Julie");
        person.setLastname("Wonderful");
         holder = new Holder<Person>(person);
        //print data sent
        dataSent.clear(); dataSent.add(holder.value);
        printSent(header, dataSent);
        //make the request
        p.updatePerson(holder.value);
        errors += printOnScreen(header, holder.value!=null && holder.value.getLastname().equals("Wonderful"), holder.value);

    }

    /**
     * This method calls the fourth method required (createPerson)
     * @param p
     */
    public static void method4Request(People p) {
        header = "Method # 4";
        holder = new Holder<Person>(new Person());
        holder.value.setLastname("Stark");
        holder.value.setFirstname("Robb");

        //print data sent
        dataSent.clear(); dataSent.add(holder.value);
        printSent(header, dataSent);
        //make the request
        p.createPerson(holder.value);

        errors += printOnScreen(header, holder.value!=null && holder.value.getIdPerson()>1, holder.value);

    }

    /**
     * This method calls the fifth method required (deletePerson)
     * @param p
     */
    public static void method5Request(People p) {
        header = "Method # 5";
        //print data sent
        dataSent.clear(); dataSent.add(holder.value.getIdPerson());
        printSent(header, dataSent);
        //make the request
        p.deletePerson(new Holder<Integer>(4)); Person checkIfNotExist = p.readPerson(holder.value.getIdPerson());
        errors += printOnScreen(header, checkIfNotExist==null, checkIfNotExist);

    }

    /**
     * This method calls the sixth method required (readPersonHistory)
     * @param p
     */
    public static void method6Request(People p) {
        header = "Method # 6";
        //print data sent
        dataSent.clear(); dataSent.add(person.getIdPerson()); dataSent.add("weight");
        printSent(header, dataSent);
        //make the request
        List<HealthMeasureHistory> history = p.readPersonHistory(person.getIdPerson(), "weight");
        errors += printOnScreen(header,history!=null && history.size()>0, history);
    }

    /**
     * This method calls the seventh method required (readPersonMeasurement)
     * @param p
     */
    public static void method7Request(People p) {
        header = "Method # 7";
        //print data sent
        dataSent.add(2, new Long(4));
        printSent(header, dataSent); //data sent
        //make the request
        HealthMeasureHistory m = p.readPersonMeasurement(person.getIdPerson(),
                "weight", 4).get(0);

        errors += printOnScreen(header, m!=null && m!=null, m);

    }

    /*
    /**
     * This method calls the eighth method required (savePersonMeasurement)
     * @param p
     */
  /*  public static void method8Request(People p) {
        header = "Method # 8";
        //setting the measure
        measure = new HealthMeasureHistory();
        measure.setMeasureDefinition(person.getMeasure().get(0).getMeasureType());
        measure.setMeasureValue("99");
        Calendar calendar = Calendar.getInstance(); measure.setDateRegistered(fromDateToCalendar(calendar.getTime()));
        holder.value = pList.get(pList.size()-1);
        //print data sent
        dataSent.clear();dataSent.add(holder.value); dataSent.add(measure);
        printSent(header, dataSent);
        //make the request
        measure =  p.savePersonMeasurement(holder.value.getId(), measure);
        //print data received
        errors += printOnScreen(header, measure!=null && measure.getMid()!=null, measure);
    }
*/
    /**
     * This method calls the ninth method required (readMeasureTypes)
     * @param p
     */
/*    public static void method9Request(People p) {
        header = "Method # 9";
        //no data sent make the request
        List<String> types = p.readMeasureTypes();
        errors += printOnScreen(header,types!=null && types.size()>1, types);
    }

    /**
     * This method calls the tenth method required (updatePersonMeasure)
     * @param p
     */
    /*public static void method10Request(People p) {
        header ="Method # 10";
        measure.setMeasureValue("1122");
        //print data sent
        dataSent.clear(); dataSent.add(holder.value.getId()); dataSent.add(measure);
        printSent(header, dataSent);
        //make the request
        Measure updatePersonMeasure = p.updatePersonMeasure(holder.value.getId(),
                measure);
        errors += printOnScreen(header,updatePersonMeasure!=null && updatePersonMeasure.getMid()!=null, updatePersonMeasure);

    }

    /**
     * This method calls the eleventh method required (readPersonMeasureByDates)
     * @param p
     */
    /*public static void method11Request(People p) throws ParseException {
        header="Method # 11";
        Date before = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY).parse("2014-12-12");
        Date after = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY).parse("2000-12-12");
        //print data sent
        dataSent.clear();
        dataSent.add(person.getId());dataSent.add("weight");
        dataSent.add(fromDateToCalendar(before)); dataSent.add(fromDateToCalendar(after));
        printSent(header, dataSent);
        //make the request
        List<Measure> measureByDates = p.readPersonMeasureByDates(person.getId(),
                "weight",
                fromDateToCalendar(before), fromDateToCalendar(after));

        errors += printOnScreen(header, measureByDates!=null && !measureByDates.isEmpty(), measureByDates);

    }*/

    /**
     * This method calls the twelfth method required (readPersonListByMeasurement)
     * @param p
     */
    /*public static void method12Request(People p) {
        header="Method # 12";
        person = p.readPerson(id);
        //print data sent
        dataSent.clear();
        dataSent.add("weight"); dataSent.add("120");dataSent.add("19");
        printSent(header, dataSent);
        //make the request
        List<Person> peoples = p.readPersonListByMeasurement(
                "weight",
                "120", "19");
        errors += printOnScreen(header, peoples!=null && !peoples.isEmpty(), peoples);

    }
    */



}