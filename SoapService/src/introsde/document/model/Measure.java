package introsde.document.model;

import introsde.document.dao.LifeCoachDao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The persistent class for the "Measurement" database table.
 * This class manages the communication between the application and the DB.
 */
@Entity
@Table(name="Measurement")
@NamedQueries( {
        @NamedQuery(name="Measurement.findAll", query="SELECT h FROM Measure h"),
        @NamedQuery(name="Measurement.findByPIDAndByTypeOrderByTimestampDesc",
                query="SELECT h FROM Measure h inner join MeasureType t ON h.realMeasure.idMeasureDef=t.idMeasureDef " +
                        "WHERE h.person.id=?1 and t.measureName like ?2 ORDER BY h.dateRegistered DESC"),
        @NamedQuery(name="Measurement.findByPIDAndByTypAndByMid",
                query="SELECT h FROM Measure h inner join MeasureType t ON h.realMeasure.idMeasureDef=t.idMeasureDef " +
                        "WHERE h.mid=?1"),
        @NamedQuery(name="Measurement.findByPIDOrderByTimestampDesc",
        query="SELECT h FROM Measure h inner join MeasureType t " +
                "ON h.realMeasure.idMeasureDef=t.idMeasureDef " +
                "WHERE h.person.id=?1 ORDER BY h.dateRegistered DESC"),

        @NamedQuery(name = "Measure.selectByType",
        query = "SELECT m FROM Person p INNER JOIN Measure m ON p.id=m.person.id " +
                "INNER JOIN MeasureType mt ON mt.idMeasureDef=m.realMeasure.idMeasureDef " +
                "WHERE m.realMeasure.measureName like ?1"),
        @NamedQuery(name="Measure.selectBetweenDates",
                query = "SELECT m from Measure m inner join Person p ON p.id = m.person.id " +
                        "INNER JOIN MeasureType mt ON mt.idMeasureDef=m.realMeasure.idMeasureDef " +
                        "WHERE p.id=?1 and mt.measureName like ?2 and m.dateRegistered between ?3 and ?4"

        )

})
public class Measure {

    @Id
    @GeneratedValue(generator="sqlite_mhistory")
    @TableGenerator(name="sqlite_mhistory", table="Sequence",
            pkColumnName="name", valueColumnName="seq",
            pkColumnValue="MeasureHistory")
    @Column(name="measureID")
    private Integer mid;

    @Column(name="timestamp")
    private String dateRegistered;

    @Transient
    private String measureType;

    @Column(name="value")
    private String measureValue;

    @Transient
    private String measureValueType;

    @ManyToOne
    @JoinColumn(name = "tID", referencedColumnName = "typeID")
    private MeasureType realMeasure; // string, integer, real

    @ManyToOne
    @JoinColumn(name = "pID", referencedColumnName = "personID")
    private Person person;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer value) {
        this.mid = value;
    }

    public Date getDateRegistered() {
        return Person.fromStringToDate(dateRegistered);
    }

    public void setDateRegistered(Date dateRegistered) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALY);
        this.dateRegistered =df.format(dateRegistered);
    }

    public String getMeasureType() {
        if(realMeasure!=null)
            measureType = realMeasure.getMeasureName();

        return measureType;
    }

    public void setMeasureType(String measureType) {
        if(realMeasure!=null)
            realMeasure.setMeasureName(measureType);
        else
            realMeasure = MeasureType.getMeasureDefinitionByName(measureType);

        this.measureType = measureType;
    }

    public String getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(String measureValue) {
        this.measureValue = measureValue;
    }

    public String getMeasureValueType() {
        if(realMeasure!=null)
            measureValueType = realMeasure.getValueType();

        return measureValueType;
    }

    public void setMeasureValueType(String measureValueType) {
        if(realMeasure!=null)
            realMeasure.setValueType(measureValueType);

        this.measureValueType = measureValueType;
    }

    @XmlTransient
    public MeasureType getRealMeasure() {
        return realMeasure;
    }

    public void setRealMeasure(MeasureType realMeasure) {
        this.realMeasure = realMeasure;
    }

    /**
     * This method given the id of the measurement return the related MeasureHistory.
     * @param id the id of the measure
     * @return the object that contains all the detail about it
     */
    public static Measure getMeasureByMid(int id) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Measure p = em.find(Measure.class, id);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    /**
     * This method allows to retrieve all the measurement for a certain type
     * related to a certain person in the database
     * @param personId the id of the person stored in the DB
     * @param type the type of the measures that we want to retrieve
     * @return the list of MeasureHistory objects for the required type and for the required person
     */
    public static List<Measure> getMeasurementsById(Long personId, String type) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Measure> list = em.createNamedQuery("Measurement.findByPIDAndByTypeOrderByTimestampDesc",Measure.class)
                .setParameter(1, personId)
                .setParameter(2, type)
                .getResultList();
        LifeCoachDao.instance.closeConnections(em);

        return list;
    }

    /**
     * This method allows to retrieve all the measurement for a certain person in the database
     * @param personId the id of the person stored in the DB
     * @return the list of MeasureHistory objects for the required type and for the required person
     */
    public static List<Measure> getMeasurementsByPersonId(Long personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Measure> list = em.createNamedQuery("Measurement.findByPIDOrderByTimestampDesc",Measure.class)
                .setParameter(1, personId)
                .getResultList();
        LifeCoachDao.instance.closeConnections(em);

        return list;
    }

    /**
     * This method allow to store a new Measure in the DB
     * @param p is the Measure object to store
     * @return the object stored in the DB
     */
    public static Measure saveMeasure(Measure p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);

        return p;
    }

    /**
     * This method allow to update a Measure already existent in the DB
     * @param p is the Measure object to store
     * @return the object updated from the DB
     */
    public static Measure updateHealthMeasureHistory(Measure p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    @XmlTransient
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * This method allows to retrieve all the measure that belongs to a certain
     * type
     * @param type the type of the measures that we want to retrieve
     * @return the List of Measure object that are of the requested type
     */
    public static List<Measure> getMeasureByType(String type) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Measure> list = em.createNamedQuery("Measure.selectByType", Measure.class)
                .setParameter(1,type)
                .getResultList();
        return list;
    }

    /**
     * This method returns the list of measure for a certain person for a certain
     * type between two dates
     * @param id the identifier of the person from which we want to retrieve measures
     * @param type the type of the measures that we want to retrieve
     * @param before the lower date limit
     * @param after the upper date limit
     * @return the list of Measure objects that are in the given range
     */
    public static List<Measure> getMeasureBetweenDates(Long id, String type, Date before, Date after) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        SimpleDateFormat df =new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        df.applyPattern("yyyy-MM-dd HH:mm:ss");

        List<Measure> results = em.createNamedQuery("Measure.selectBetweenDates", Measure.class)
                .setParameter(1, id)
                .setParameter(2, type)
                .setParameter(3, df.format(before))
                .setParameter(4, df.format(after))
                .getResultList();

        return results;
    }

}