package model;

/**
 * This class is a model for In-House parts.
 */
public class InHouse extends Part {
    /**
     *  A private machine Id.
     */
    private int machineId;
    /**
     * This is a constructor for an InHouse object. It is an instance of the part class.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineId = machineID;
    }
    /**
     * Setter for machine Id.
     * @param machineId sets machine Id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    /**
     * Getter for machine Id.
     * @return machine id
     */
    public int getMachineId() {
        return machineId;
    }
}
