/**
 * 
 */
package org.core.knowledge.io.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-11
 */
public class TCPClient {
	
	public void start() {
    	ByteBuffer writeBuf = ByteBuffer.allocate(1024);
        ByteBuffer readBuf = ByteBuffer.allocate(1024);
		try(SocketChannel sc = SocketChannel.open()){
    		Selector selector = Selector.open();
    		sc.configureBlocking(false);
    		sc.connect(new InetSocketAddress("127.0.0.1",8099));
    		sc.register(selector, SelectionKey.OP_CONNECT);
    		@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
    		for(;;) {
    			selector.select();
    			
    			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while(iter.hasNext()) {
					SelectionKey sk = iter.next();
					iter.remove();
					if(sk.isConnectable()) {
						sc.finishConnect();
						sc.register(selector, SelectionKey.OP_WRITE);
						System.out.println("server connected..."); 
						break;
					}else if(sk.isReadable()) {
						System.out.print("receive message:");
						SocketChannel client = (SocketChannel) sk.channel();
						readBuf.clear();
	                    int num = client.read(readBuf);
	                    System.out.println(new String(readBuf.array(),0, num));
	                    sc.register(selector, SelectionKey.OP_WRITE);
					}else if(sk.isWritable()) {
						System.out.print("please input message:"); 
						String message = scanner.nextLine(); 
						writeBuf.clear();
	                    writeBuf.put(message.getBytes());
	                    writeBuf.flip();
	                    sc.write(writeBuf); 
	                    sc.register(selector, SelectionKey.OP_READ);
					}
				}
    		}
    	}catch(Exception e) {
    		System.err.println(e.getMessage());
    	}
	}

	public static void main(String[] args) {
		new TCPClient().start();
	}

}
