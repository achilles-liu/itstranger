/**
 * 
 */
package org.core.knowledge.future;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-24
 */
@Data
@AllArgsConstructor
public class Quote {
	private Shop shop;
	private double price;
	private Discount discount;
}
