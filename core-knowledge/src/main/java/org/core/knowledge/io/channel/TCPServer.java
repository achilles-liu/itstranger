/**
 * 
 */
package org.core.knowledge.io.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-08
 */
public class TCPServer {
	
	Selector selector;
	ByteBuffer readBuf = ByteBuffer.allocate(1024);
	ByteBuffer writeBuf = ByteBuffer.allocate(1024);
	
	public void start() {
		try(ServerSocketChannel server = ServerSocketChannel.open()){
			server.bind(new InetSocketAddress("127.0.0.1", 8099));
			server.configureBlocking(false);
			
			selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			for(;;) {
				selector.select();
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while(iter.hasNext()) {
					SelectionKey sk = iter.next();
					if(!sk.isValid()) continue;
					if(sk.isAcceptable()) {
						accept(sk);
					}else if(sk.isReadable()) {
						read(sk);
					}else if(sk.isWritable()) {
						write(sk);
					}
					iter.remove();
				}
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void write(SelectionKey sk) throws IOException{
		SocketChannel sc = (SocketChannel) sk.channel();
		writeBuf.clear();
		String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		writeBuf.put(str.getBytes());
		writeBuf.flip();
		sc.write(writeBuf);
		sc.register(selector, SelectionKey.OP_READ);
	}
	
	private void read(SelectionKey sk) throws IOException{
		SocketChannel ch = (SocketChannel) sk.channel();
		readBuf.clear();
		int len = ch.read(readBuf);
		String str = new String(readBuf.array(),0,len);
		System.out.println("str => "+str);
		if("quit".equals(str)) {
			ch.close();
		}else {
			ch.register(selector, SelectionKey.OP_WRITE);
		}
	}
	
	
	private void accept(SelectionKey sk) throws IOException{
		ServerSocketChannel ch = (ServerSocketChannel) sk.channel();
		SocketChannel cli = ch.accept();
		cli.configureBlocking(false);
		cli.register(selector, SelectionKey.OP_READ);
		System.out.println("a new client connected "+cli.getRemoteAddress());
	}
	
	public static void main(String[] args) {
		new TCPServer().start();
	}
}
