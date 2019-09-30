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
public class SkipListExample {

	public static void main(String[] args) {
		SkipList<Integer> sl = new SkipListImpl<>();
		sl.put("ABC", 10);
		System.out.println("size:"+sl.size()+",height:"+sl.height());
		System.out.println("--------------------");
		sl.put("DEF", 20);
		System.out.println("size:"+sl.size()+",height:"+sl.height());
		Integer v = sl.get("DEF");
		System.out.println("key:DEF,value:"+v);
		System.out.println("--------------------");
		sl.remove("ABC");
		System.out.println("size:"+sl.size()+",height:"+sl.height());
		System.out.println("--------------------");
		sl.remove("LTJ");
		System.out.println("size:"+sl.size()+",height:"+sl.height());
	}

}
