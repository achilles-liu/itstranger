/**
 * 
 */
package org.core.knowledge.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-04
 */
public class CharBufferExample {

	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		// convert to <code>CharBuffer</code>
		CharBuffer cbuf = buf.asCharBuffer();
		cbuf.put("test char buffer");
		cbuf.flip();
		while(cbuf.hasRemaining()) {
			System.out.print(cbuf.get());
		}
		System.out.println();
	}

}
