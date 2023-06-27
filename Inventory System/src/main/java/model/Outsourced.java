package model;

/**
 * This class is a model for Outsourced parts.
 */
public class Outsourced extends Part{
    /**
     * A string for company name.
     */
    private String companyName;

    /**
     * his is a constructor for an Outsourced object. It is an instance of the part class.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This sets the machine Id.
     * @param companyName variable for company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This gets the machine Id and returns it.
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
