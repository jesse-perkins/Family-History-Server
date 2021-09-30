package ModelClasses;

/**
 * <h1>AuthKey</h1>
 * <p>
 * The AuthKey class represents an authorization key in the Family Map
 * Server Application and is used internally by the server to pass
 * information to and from internal handlers, services, and DAOs.
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class AuthKey implements Comparable<AuthKey> {
    /**
     * The authorization key for a given user.
     */
    private int key = -1;
    /**
     * The active constructor for the AuthKey class.
     *
     * Creates a new AuthKey and sets the key value to the passed in parameter.
     *
     * @param newKey the key that will be the authorization key.
     */
    public AuthKey(int newKey){key = newKey;}

    /**
     * Getter for key
     *
     * @return key as integer
     */
    public int getKey() {
        return key;
    }


    /**
     * Getter for key as a string
     *
     * @return key as string
     */
    public String getStringKey(){return "" + key;}


    /**
     * Implementation of the compareTo function so that AuthKeys can be used in a TreeSet
     *
     * @param key another AuthKey
     * @return 0 if the keys are the same, -1 if they are not.
     */
    @Override
    public int compareTo(AuthKey key){
        if(this.getKey() == key.getKey()){
            return 0;
        }else{
            return -1;
        }
    }
}
