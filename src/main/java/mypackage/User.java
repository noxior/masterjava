
package mypackage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="flag" type="{http://javaops.ru}flagType" />
 *       &lt;attribute name="email" type="{http://javaops.ru}emailAddress" />
 *       &lt;attribute name="city" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="groupRefs" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "User")
public class User {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "flag")
    protected FlagType flag;
    @XmlAttribute(name = "email")
    protected String email;
    @XmlAttribute(name = "city")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object city;
    @XmlAttribute(name = "groupRefs")
    @XmlIDREF
    @XmlSchemaType(name = "IDREFS")
    protected List<Object> groupRefs;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link FlagType }
     *     
     */
    public FlagType getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagType }
     *     
     */
    public void setFlag(FlagType value) {
        this.flag = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCity(Object value) {
        this.city = value;
    }

    /**
     * Gets the value of the groupRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getGroupRefs() {
        if (groupRefs == null) {
            groupRefs = new ArrayList<Object>();
        }
        return this.groupRefs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getValue(), user.getValue()) &&
                getFlag() == user.getFlag() &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getCity(), user.getCity()) &&
                Objects.equals(getGroupRefs(), user.getGroupRefs());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getValue(), getFlag(), getEmail(), getCity(), getGroupRefs());
    }
}
