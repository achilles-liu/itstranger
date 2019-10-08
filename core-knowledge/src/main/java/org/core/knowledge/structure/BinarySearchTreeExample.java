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
public class BinarySearchTreeExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] arr = { 16,8,3,24,9 };
		BinarySearchTree<Integer> bst = BinarySearchTree.build(arr);
		bst.preOrder(bst.getRoot());
		bst.out();
		bst.clear();
		bst.inOrder(bst.getRoot());
		bst.out();
		bst.clear();
		bst.postOrder(bst.getRoot());
		bst.out();
	}

}
