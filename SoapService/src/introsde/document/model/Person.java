package introsde.document.model;

import introsde.document.dao.LifeCoachDao;
import javassist.NotFoundException;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gt on 11/20/14.
 */
@Entity
@Table(name = "Person")
@NamedQueries({
        @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),


})
public class Person {

    @Id
    // For sqlite in particular, you need to use the following @GeneratedValue annotation
    // This holds also for the other tables
    // SQLITE implements auto increment ids through named sequences that are stored in a
    // special table named "sqlite_sequence"
    @GeneratedValue(generator = "sqlite_person")
    @TableGenerator(name = "sqlite_person", table = "Sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue = "Person")
    @Column(name = "personID")
    private Long id;

    @Column(name = "firstName")
    private String firstname;

    @Column(name = "lastName")
    private String lastname;

    @Column(name = "birthDate")
    private String birthdate;

    @Transient
    private List<Measure> measure; // one for each type of measure

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("dateRegistered DESC")
    private List<Measure> healthHistory; // all measurements

    /**
     * This method converts a text in a Date. It's really useful because it accept two
     * different date representation.
     * @param text the string date to convert in a date
     * @return the converted date
     */
    public static Date fromStringToDate(String text) {
        Date temp = null;
        try {
            if (text.matches("[0-9]{4}(-)[0-9]{1,2}(-)[0-9]{1,2}"))
                temp = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(text);
            else if (text.matches("[0-9]{4}(-)[0-9]{1,2}(-)[0-9]{1,2}(\\s)[0-9]{1,2}(:)[0-9]{1,2}(:)[0-9]{1,2}"))
                temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(text);
            else
                temp = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(text);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public static Person updatePerson(Person p) {
        Person existing = getPersonById(p.getId());

        if (existing == null) {
            try {
                throw new NotFoundException("Error. No existent id");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (p.getFirstname()!=null && p.getFirstname()!="")
                existing.setFirstname(p.getFirstname());
            if (p.getLastname()!=null && p.getLastname()!="")
                existing.setLastname(p.getLastname());
            if (p.getBirthdate()!=null && p.getBirthdate()!="")
                existing.setBirthdate(p.getBirthdate());
        }

        return  Person.updatePersonDB(existing);

    }

    /**
     * This method allow to update a Person already existent in the DB
     * @param p is the Person object to store
     * @return the object updated from the DB
     */
    public static Person updatePersonDB(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p = em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    /**
     * This method allow to store a new Person in the DB
     * @param p is the Person object to store
     * @return the object stored in the DB
     */
    public static Person savePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    /**
     * This method allows to retrieve a Person object from the database by its id
     * @param personId which is the unique identifier for the person in the database
     * @return the Person Object which contains all the person detail retrieved from the DB
     */
    public static Person getPersonById(Long personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);

        LifeCoachDao.instance.closeConnections(em);
        return p;
    }


    /**
     * This method allows to retrieve the people in the DB
     * @return the list of person object contained in the DB
     */
    public static List<Person> getAll() {

        System.out.println("--> Initializing Entity manager...");
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        System.out.println("--> Querying the database for all the people...");
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
        System.out.println("--> Closing connections of entity manager...");
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @XmlElementWrapper(name="currentHealth")
    @Transient
    public List<Measure> getMeasure() {
        measure = getLatestMeasures();
        return measure;
    }

    private List<Measure> getLatestMeasures() {
        List<Measure> measures = new ArrayList<Measure>();
        HashMap<String,Measure> latestMeasures = new HashMap<String, Measure>();
        for (Measure m: Measure.getMeasurementsByPersonId(this.id)) {
            if (!latestMeasures.containsKey(m.getMeasureType())){
                latestMeasures.put(m.getMeasureType(),m);
                measures.add(latestMeasures.get(m.getMeasureType()));
            }
        }

        return measures;
    }

    public void setMeasure(List<Measure> measure) {
        setHealthHistory(measure);
        this.measure = measure;
    }

    @XmlTransient
    public List<Measure> getHealthHistory() {
        return healthHistory;
    }

    public void setHealthHistory(List<Measure> healthHistory) {
        this.healthHistory = healthHistory;
    }

    /**
     * This method remove a Person  existent in the DB
     * @param p is the Person object to delete
     */
    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p = em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }

    /**
     * This method remove a Person  existent in the DB
     * @param id is the Person object to delete
     */
    public static void removePerson(Long id) {
        Person p = Person.getPersonById(id);
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p = em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * This method retrieve the last measure for a given type for the person id
     * of the object.
     * @param type is the measure type from which we want to extract the last measure
     * @return the last MeasureHistory object containing the last measure
     */
    private Measure lastMeasure(String type) {
        Date dataWeight = null;
        Measure lastWeight = null;

        for (Measure m : Measure.getMeasurementsById(this.id,type)) {

            if (dataWeight == null) {
                dataWeight = m.getDateRegistered();

                lastWeight = m;
            } else {
                Date temp = m.getDateRegistered();

                if (temp.after(dataWeight)) {
                    dataWeight = temp;
                    lastWeight = m;
                }
            }
        }

        return lastWeight;
    }

    /**
     * This method allows to return the list of people that has measure value between
     * a certain range for a certain type
     * @param max the max value in the range to select people
     * @param min the min value in the range to select people
     * @param type the type determines which value look for determine if the person is in the range
     * @return the list of person object that has values in the range given
     */
    public static List<Person> getPersonBetweenVal(String max,String min, String type) {
        List<Measure> measures = Measure.getMeasureByType(type);
        List<Person> results = new Vector<Person>();
        String valueType = MeasureType.getMeasureDefinitionByName(type).getValueType();
        Double maxVal = null ;
        Double minVal = null;
        Double value = null;

        if(max!=null && !max.equals("") )
            maxVal = new Double(Double.parseDouble(max));

        if(min!=null && !min.equals(""))
            minVal = new Double(Double.parseDouble(min));


        for(Measure m: measures){
           if(m.getMeasureValue()!= null && !m.getMeasureValue().equals(""))
               value=Double.parseDouble(m.getMeasureValue());

            if (value!=null) {
                if ( (minVal != null && maxVal != null) ) {
                    if (value > minVal && value < maxVal)
                        results.add(m.getPerson());

                } else if (minVal != null && max == null) {
                    minVal = Double.parseDouble(min);
                    if (value>minVal)
                        results.add(m.getPerson());

                } else if (max != null && min==null) {
                    maxVal = Double.parseDouble(max);
                    if (value<maxVal)
                        results.add(m.getPerson());
                }
            }
        }

        return results;
    }

}