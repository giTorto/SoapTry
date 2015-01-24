package introsde.document.ws;

import introsde.document.model.*;
import introsde.document.model.Measure;
import introsde.document.model.wrapper.MeasureTypesWrapper;

import javax.jws.WebService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gt on 11/20/14.
 */
@WebService(endpointInterface = "introsde.document.ws.People")
public class PeopleImpl implements People {

    @Override
    public Person readPerson(Long id) {
        System.out.println("---> Reading Person by id = "+id);
        Person p = Person.getPersonById(id);
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getFirstname());
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
        return p;
    }

    @Override
    public List<Person> readPersonList() {
        return Person.getAll();
    }


    @Override
    public Person createPerson(Person person) {
        Person.savePerson(person);
        return person;
    }

    @Override
    public Person updatePerson(Person person) {
        return Person.updatePerson(person);
    }

    @Override
    public void deletePerson(Long id) {
        Person.removePerson(new Long(id));
    }

    @Override
    public List<Measure> readPersonHistory(Long id, String measureType) {
        return  Measure.getMeasurementsById(id,measureType);
    }

    @Override
    public Measure readPersonMeasurement(Long id, String measureType, Long mid) {
        return Measure.getMeasureByMid(mid.intValue());
    }

    @Override
    public Measure savePersonMeasurement(Long id, Measure m) {
        Calendar cal = Calendar.getInstance();
        java.sql.Date d = new java.sql.Date(cal.getTimeInMillis());

        m.setDateRegistered(d);
        m.setPerson(Person.getPersonById(id));

        if(m.getMeasureType()!=null){
            m.setRealMeasure(MeasureType.getMeasureDefinitionByName(m.getMeasureType()));

        }else
            return null;

        return Measure.saveMeasure(m);
    }

    @Override
    public List<String> readMeasureTypes() {
        return new MeasureTypesWrapper(MeasureType.getAll()).getMeasureTypes();
    }

    @Override
    public Measure updatePersonMeasure(Long id, Measure m) {
        m.setPerson(Person.getPersonById(id));
        Calendar cal = Calendar.getInstance();
        Date d = new Date(cal.getTimeInMillis());

        if(m.getDateRegistered()==null)
            m.setDateRegistered(d);
        if (m.getMeasureType()!=null)
            m.setRealMeasure(MeasureType.getMeasureDefinitionByName(m.getMeasureType()));
        else
            return null;

        return Measure.updateHealthMeasureHistory(m);
    }

    @Override
    public List<Person> readPersonListByMeasurement(String measureType, String maxValue, String minValue) {
        return Person.getPersonBetweenVal(maxValue,minValue,measureType);
    }

    @Override
    public List<Measure> readPersonMeasureByDates(Long id, String measureType, Date before, Date after) {
        return Measure.getMeasureBetweenDates(id,measureType,new Date(after.getTime()),new Date(before.getTime()));
    }
}