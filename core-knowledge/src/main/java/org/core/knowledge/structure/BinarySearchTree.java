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
public class BinarySearchTree<T extends Comparable<T>> {
	static class Node<T extends Comparable<T>> implements Visitable<T>{
		final T data;
		Node<T> left,right;
		
		Node(T data){ this.data = data; }
		public void accept(Visitor<T> visitor) { visitor.visit(this); }
	}
	
	private Node<T> newNode(T data){ return new Node<T>(data); }
	private Node<T> root;
	private int size;
	private Visitor<T> visitor;
	
	public BinarySearchTree(Visitor<T> visitor) { 
		this.visitor = visitor;
		this.root = null;
	}
	
	/**
	 * build binary search tree
	 * @param data
	 * @return
	 */
	public static <K extends Comparable<K>> BinarySearchTree<K> build(K[] data) {
		return new BinarySearchTree<K>(new DefaultVisitor<>()).build0(data);
	}
	
	/**
	 * build binary search tree with the specified <code>Visitor<K></code>
	 * @param data
	 * @param visitor
	 * @return
	 */
	public static <K extends Comparable<K>> BinarySearchTree<K> build(K[] data,Visitor<K> visitor) {
		return new BinarySearchTree<K>(visitor).build0(data);
	}
	
	private Node<T> find(Node<T> node){
		Node<T> p, q;
		p = this.root;
		for(;;) {
			q = p;
			p = p.data.compareTo(node.data) > 0 
					? p.left : p.right;
			if(p == null) break;
		}
		return q;
	}
	
	/**
	 * insert the specified data into this binary search tree
	 * @param data
	 */
	public void insert(T data) {
		Node<T> newNode = newNode(data);
		size++;
		if(this.root == null) {
			this.root = newNode;
			return ;
		}
		Node<T> parent = find(newNode);
		if(data.compareTo(parent.data) > 0) {
			parent.right = newNode;
		}else {
			parent.left = newNode;
		}
	}
	
	public BinarySearchTree<T> build0(T[] arr) {
		for(int i = 0;i < arr.length;i++) {
			insert(arr[i]);
		}
		return this;
	}
	
	/**
	 * preorder traversal
	 * @param root
	 */
	public void preOrder(Node<T> root) {
		if(root != null) {
			root.accept(visitor);
			preOrder(root.left);
			preOrder(root.right);
		}
	}
	
	/**
	 * inorder traversal
	 * @param root
	 */
	public void inOrder(Node<T> root) {
		if(root != null) {
			inOrder(root.left);
			root.accept(visitor);
			inOrder(root.right);
		}
	}
	
	/**
	 * postorder traversal
	 * @param root
	 */
	public void postOrder(Node<T> root) {
		if(root != null) {
			postOrder(root.left);
			postOrder(root.right);
			root.accept(visitor);
		}
	}
	
	/**
	 * reture root node of the binary search tree
	 * @return
	 */
	public Node<T> getRoot(){
		return this.root;
	}
	
	/**
	 * reture the node's size
	 * @return
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * set the visitor
	 * @param visitor
	 */
	public void setVisitor(Visitor<T> visitor) {
		this.visitor = visitor;
	}
	
	public void out() {
		this.visitor.out();
	}
	
	public void clear() {
		this.visitor.clear();
	}
}
