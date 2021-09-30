package ModelClasses;

import java.util.TreeSet;

/**
 * <h1>Person</h1>
 * <p>
 * The Person class represents an individual in the Family Map Server
 * Application and is used internally by the server to pass information
 * to and from internal handlers, services, and DAOs.
 * </p>
 * <b>NOTE:</b> Extended by User
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class Person {
    /**
     * The username of the user to which this person belongs
     */
    private String descendant = null;
    /**
     * The unique ID which relates to this Person and this Person only.
     */
    private String personID = null;
    /**
     * The first name of the person.
     */
    private String firstName = null;
    /**
     * The last name of the person.
     */
    private String lastName = null;
    /**
     * The gender of the person.
     */
    private Character gender = null;
    /**
     * The personID of the person who is this persons father.
     */
    private String father = null;
    /**
     * A personID of the person who is this persons mother.
     */
    private String mother = null;
    /**
     * A personID of the person who is this persons spouse.
     */
    private String spouse = null;
    /**
     * The set of all recorded events in this person's life.
     */
    private TreeSet<Event> personalEvents = null;

    /**
     * The active constructor for the Person class.
     *
     * Sets the person's name, personIDs, descendant, and gender based on inputs.
     * Parent and spouse information must be entered via setters later.
     *
     * @param personID the unique personID for this person
     * @param firstName the first name of the person.
     * @param lastName the last name of the person.
     * @param gender the gender of the person as an Enum. Either MALE or FEMALE.
     * @param descendant the root descendant of this person. If this person, NULL
     */
    public Person(String personID, String descendant, String firstName, String lastName, Character gender) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * The active constructor for the Person class, including parents and spouse
     *
     * @param personID the unique personID for this person
     * @param descendant the root descendant of this person. If this person, NULL
     * @param firstName the first name of the person.
     * @param lastName the last name of the person.
     * @param gender the gender of the person as an Enum. Either MALE or FEMALE.
     * @param father the personID of the father of this person
     * @param mother the personID of the mother of this person
     * @param spouse the personID of the spouse of the person
     */
    public Person(String personID, String descendant, String firstName, String lastName,
                  Character gender, String father, String mother, String spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    /**
     * The active constructor for the Person class.
     *
     * Sets the person's name, descendant, and gender based on inputs.
     * Parent, spouse, and personID information must be entered via setters later.
     *
     * @param firstName the first name of the person.
     * @param lastName the last name of the person.
     * @param gender the gender of the person as an Enum. Either MALE or FEMALE.
     * @param descendant the root descendant of this person. If this person, NULL
     */
    public Person(String descendant, String firstName, String lastName, Character gender) {
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Getter for the personID
     *
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Setter for personID
     *
     * @param personID the personID to replace the current personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Setter for personID, takes integers
     *
     * @param personID the personID to replace the current personID
     */
    public void setPersonID(int personID) {
        StringBuilder builder = new StringBuilder();
        builder.append(personID);
        this.personID = builder.toString();
    }

    /**
     * Getter for father personID
     *
     * @return father the personID for the father
     */
    public String getFather() {
        return father;
    }

    /**
     * Setter for father personID
     *
     * @param father the father personID to be set as this person's father
     */
    public void setFather(String father) {
        this.father = father;
    }

    /**
     * Getter for mother personID
     *
     * @return mother the personID for the mother
     */
    public String getMother() {
        return mother;
    }

    /**
     * Setter for mother personID
     *
     * @param mother the mother personID to be set as this person's mother
     */
    public void setMother(String mother) {
        this.mother = mother;
    }

    /**
     * Getter for spouse personID
     *
     * @return spouse the personID for the spouse
     */
    public String getSpouse() {
        return spouse;
    }

    /**
     * Setter for spouse personID
     *
     * @param spouse the mother personID to be set as this person's spouse
     */
    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    /**
     * Getter for the TreeSet of Events belonging to the person "currently not in use*
     *
     * @return personalEvents
     */
    public TreeSet<Event> getPersonalEvents() {
        return personalEvents;
    }

    /**
     * Setter for the TreeSet of Events belonging to the person "currently not in use*
     *
     * @param personalEvents the new TreeSet
     */
    public void setPersonalEvents(TreeSet<Event> personalEvents) {
        this.personalEvents = personalEvents;
    }

    /**
     * Getter for firstName
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for lastName
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for gender
     *
     * @return gender
     */
    public Character getGender() {
        return gender;
    }

    /**
     * Getter for descendant
     *
     * @return descendant
     */
    public String getDescendant() {
        return descendant;
    }

    /**
     * Setter for descendant
     *
     * @param descendant the string that will replace the descendant
     */
    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    /**
     * Setter for the descendant, takes integers
     *
     * @param descendant the integer that will replace the descendant as a string
     */
    public void setDescendant(int descendant) {
        StringBuilder builder = new StringBuilder();
        builder.append(descendant);
        this.descendant = builder.toString();
    }

    /**
     * Comparator for the person class
     *
     * @param guy the person to be compared
     * @return true if the person objects are equal, false if they are not
     */
    public boolean equals(Person guy){
        if(guy.getClass() != this.getClass()){
            return false;
        }
        if(this.getPersonID().equals(guy.getPersonID())
                && (this.getDescendant().equals(guy.getDescendant()))
                && (this.getFirstName().equals(guy.getFirstName()))
                && (this.getLastName().equals(guy.getLastName()))
                && (this.getGender().equals(guy.getGender()))
                && (this.getPersonID().equals(guy.getPersonID()))
                && (((this.getMother() == null && guy.getMother() == null)) || this.getMother().equals(guy.getMother()))
                && (((this.getFather() == null && guy.getFather() == null)) || this.getFather().equals(guy.getFather()))
                && (((this.getSpouse() == null && guy.getSpouse() == null)) || this.getSpouse().equals(guy.getSpouse()))){
            return true;
        }else{
            return false;
        }
    }
}
