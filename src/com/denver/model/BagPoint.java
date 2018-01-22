package com.denver.model;

/**
 * 
 * The BagPoint Objects represents the location of a Bag at the 
 * Denver international Airport
 *  
 * @author Samuel Sokeye
 * 
 */
public class BagPoint {
	
	/**
	 * The name of the location of a Bag
	 */
	final private String name;
	
	
	/**
	 * Class constructor that creates a BagPoint this object identifies
	 * the location of a Bag
	 * 
	 * @param name A string to identify the location of a Bag
	 */
	public BagPoint(String name ){
		this.name = name;
	}

	/**
	 * Gets the name for the location of a Bag
	 * 
	 * @return A string to identify the location of a Bag
	 */
	public String getName() {
		return name;
	}
	
	
    @Override
    public String toString() {
        return name;
    }

    /**
     * 
     * @return Returns an hashcode value for the BagPoint object
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * 
	 * @return true if the Object is the same as the Object argument
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BagPoint other = (BagPoint) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
