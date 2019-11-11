/**
 * 
 */
package org.core.knowledge.io;

import java.nio.ByteBuffer;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-04
 */
public class BufferExample {

	public static void main(String[] args) {
		// 1) create <code>Buffer</code> instance
		ByteBuffer buf = ByteBuffer.allocate(1024);
		// 2) write data in buf
		buf.put("test buffer".getBytes());
		// 3) switch to READ mode
		buf.flip();
		// 4) check remain of buffer
		while(buf.hasRemaining()) {
			// 5) get byte data
			char c = (char) buf.get();
			System.out.print(c);
		}
		System.out.println();
	}

}
