
package introsde.document.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for readPersonListByMeasurement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="readPersonListByMeasurement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="measuretype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "readPersonListByMeasurement", propOrder = {
    "measuretype",
    "maxValue",
    "minValue"
})
public class ReadPersonListByMeasurement {

    protected String measuretype;
    protected int maxValue;
    protected int minValue;

    /**
     * Gets the value of the measuretype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasuretype() {
        return measuretype;
    }

    /**
     * Sets the value of the measuretype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasuretype(String value) {
        this.measuretype = value;
    }

    /**
     * Gets the value of the maxValue property.
     * 
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the value of the maxValue property.
     * 
     */
    public void setMaxValue(int value) {
        this.maxValue = value;
    }

    /**
     * Gets the value of the minValue property.
     * 
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Sets the value of the minValue property.
     * 
     */
    public void setMinValue(int value) {
        this.minValue = value;
    }

}
