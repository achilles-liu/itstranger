/**
 * 
 */
package org.core.knowledge.structure;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-10-08
 */
public interface Visitable<T extends Comparable<T>> {
	void accept(Visitor<T> visitor);
}
