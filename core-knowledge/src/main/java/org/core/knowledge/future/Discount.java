/**
 * 
 */
package org.core.knowledge.future;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-24
 */
public enum Discount {
	NONE(0),SILVER(5),GOLD(10);
	
	private final int percent;
	 
    Discount(int percent){
        this.percent = percent;
    }
 
    public int getPercent() {
        return percent;
    }
    
}
