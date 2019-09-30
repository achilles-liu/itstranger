/**
 * 
 */
package org.core.knowledge.structure;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-30
 */
public interface SkipList<T> {
	final static String HEAD = "HEAD";
	final static String TAIL = "EOF";
	/**
     * Returns the number of elements in this skip list.
     *
     * @return the number of elements in this skip list
     */
	int size();
	/**
     * Returns <tt>true</tt> if this skip list contains no elements.
     *
     * @return <tt>true</tt> if this skip list contains no elements
     */
	boolean isEmpty();
	/**
     * Returns the height of this skip list.
     *
     * @return the height of this skip list
     */
	int height();
	/**
	 * Returns the value in this skip list according to the specified key.
	 * @param key
	 * @return he value in this skip list according to the specified key
	 */
	T get(String key);
	/**
	 * add the key-value mappings to skip list.
	 * @param key
	 * @param value
	 * @return the inputed value
	 */
	T put(String key,T value);
	/**
	 * 
	 * @param key
	 */
	void remove(String key);
}
