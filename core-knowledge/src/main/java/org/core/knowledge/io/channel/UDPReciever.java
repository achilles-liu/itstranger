/**
 * 
 */
package org.core.knowledge.io.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-06
 */
public class UDPReciever {

	public static void main(String[] args) {
		try(DatagramChannel receiver = DatagramChannel.open()) {
			receiver.configureBlocking(false);
			receiver.bind(new InetSocketAddress(8090));
			Selector selector = Selector.open();
			receiver.register(selector, SelectionKey.OP_READ);
			ByteBuffer buf = ByteBuffer.allocate(1024);
			while(selector.select() > 0) {
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while(iter.hasNext()) {
					SelectionKey sk = iter.next();
					System.out.println("sk => "+sk);
					iter.remove();
					if(sk.isReadable()) {
						DatagramChannel sc = (DatagramChannel) sk.channel();
						sc.receive(buf);
						buf.flip();
						System.out.print(new String(buf.array(),0,buf.limit()));
						buf.clear();
					}
					System.out.println();
				}
			}
			System.out.println("receiver ready!");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
