/**
 * 
 */
package org.core.knowledge.io.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-06
 */
public class UDPSender {

	public static void main(String[] args) throws IOException {
		try(DatagramChannel sender = DatagramChannel.open()) {
			sender.configureBlocking(false);
			ByteBuffer buf = ByteBuffer.allocate(1024);
			Scanner scanner = new Scanner(System.in);
			while(scanner.hasNext()) {
				String str = scanner.next();
				buf.put((new Date().toString()+" => "+str).getBytes());
				buf.flip();
				sender.send(buf, new InetSocketAddress("127.0.0.1", 8090));
				buf.clear();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
