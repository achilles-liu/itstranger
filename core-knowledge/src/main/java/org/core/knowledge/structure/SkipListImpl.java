/**
 * 
 */
package org.core.knowledge.structure;

import java.util.Random;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-30
 */
public class SkipListImpl<T> implements SkipList<T>{
	static class Node<T>{
		T t;
		Node<T> left,up,down,right;
		final String key;
		
		Node(String key,T t){
			this.key = key;
			this.t = t;
			left = up = down = right = null;
		}
		
	}
	
	private Node<T> newNode(String key,T t){
		return new Node<T>(key,t);
	}
	
	private Node<T> head;
	private Node<T> tail;
	private int height,size;
	private Random coin;
	
	SkipListImpl(){
		this.head = newNode(HEAD,null);
		this.tail = newNode(TAIL,null);
		head.right = tail;
		tail.left = head;
		height = 1;
		size = 0;
		coin = new Random();
	}
	
	/**
	 * search the insertion point.
	 * which conditions should be used to search insertion point?
	 * 1. if existing node's key  great then the specified key unless the tail;
	 * 2. if the currrent node has down node, will search the down linked list.
	 * @param key
	 * @return
	 */
	private Node<T> find(String key){
		Node<T> p = head;
		for(;;) {
			while(p.right.key != TAIL && p.right.key.compareTo(key) <= 0)
				p = p.right;
			if(p.down == null) break;
			p = p.down;
		}
		return p;
	}
	
	public T put(String key,T t) {
		// find the taget node
		Node<T> p = find(key);
		// create a new node and build node's relationship
		Node<T> newNode = newNode(key,t);
		newNode.left = p;
		newNode.right = p.right;
		p.right.left = newNode;
		p.right = newNode;
		
		int currLvl = 1;
		while(coin.nextDouble() < 0.5) {
			// build a new linked list
			if(currLvl >= height++) {
				Node<T> newHeader = newNode(HEAD,null);
				Node<T> newTail = newNode(TAIL,null);
				newHeader.right = newTail;
				newHeader.down = head;
				head.up = newHeader;
				
				newTail.left = newHeader;
				newTail.down = tail;
				tail.up = newTail;
				
				head = newHeader;
				tail = newTail;
			}
			
			while(p.up == null) p = p.left;
			p = p.up;
			
			Node<T> upNewNode = newNode(key,null);
			upNewNode.left = p;
			upNewNode.right = p.right;
			upNewNode.down = newNode;
			newNode.up = upNewNode;
			p.right.left = upNewNode;
			p.right = upNewNode;
			
			newNode = upNewNode;// next iterator
			currLvl++;
		}
		size += 1;
		return t;
	}
	
	public int size() {
		return this.size;
	}
	
	public int height() {
		return this.height;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}

	public T get(String key) {
		Node<T> p = find(key);
		return key.equals(p.key) ? p.t : null;
	}

	public void remove(String key) {
		Node<T> p = find(key);
		
		if(!key.equals(p.key)) return ;
		
		while(p.up != null) {
			p.left.right = p.right;
			p.right.left = p.left;
			p.left = p.right = null;
			
			p = p.up;
		}
		
		p.left.right = p.right;
		p.right.left = p.left;
		p.left = p.right = null;
		
		while(p.down != null) {
			Node<T> q = p.down;
			p.down.up = p.down = null;
			p = q;
		}
		
		// remove the empty-level linked list
		while(head.right.key.equals(tail.key) && height > 0) {
			Node<T> h = head.down,t = tail.down;
			head.right = head.down = tail.left = tail.down = null;
			h.up = t.up = null;
			head = h;
			tail = t;
			height -= 1;
		}
		
		size -= 1;
	}
}
