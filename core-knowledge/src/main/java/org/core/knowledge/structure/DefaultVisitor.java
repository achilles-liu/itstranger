/**
 * 
 */
package org.core.knowledge.structure;

import java.util.ArrayList;
import java.util.List;

import org.core.knowledge.structure.BinarySearchTree.Node;


/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-10-08
 */
public class DefaultVisitor<T extends Comparable<T>> implements Visitor<T> {
	
	private List<T> list = new ArrayList<>();

	public void visit(Node<T> node) {
		list.add(node.data);
	}

	public void out() {
		for(int i = 0;i < list.size();i++) {
			System.out.print(list.get(i));
			if(i < list.size() -1) {
				System.out.print(" < ");
			}
		}
		System.out.println();
	}

	public void clear() {
		list.clear();
	}
}
