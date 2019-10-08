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
public interface Visitor<T extends Comparable<T>> {
	void visit(BinarySearchTree.Node<T> node);
	public void out();
	public void clear();
}
