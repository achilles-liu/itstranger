/**
 * 
 */
package org.core.knowledge.io.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-06
 */
public class FileChannelExample {
	
	public static void new1() {
		try {
			RandomAccessFile tmp = new RandomAccessFile("raf.txt","rw");
			FileChannel tch = tmp.getChannel();
			try(FileInputStream fis = new FileInputStream(new File("tmp.txt"));
					FileInputStream fis2 = new FileInputStream(new File("tmp2.txt"))){
				FileChannel rch1 = fis.getChannel();
				ByteBuffer rbuf = ByteBuffer.allocate(1024);
				rch1.read(rbuf);
				rbuf.flip();
				
				
				tch.write(rbuf, 0);
				int end = rbuf.limit();
				rbuf.clear();
				FileChannel rch2 = fis2.getChannel();
				rch2.read(rbuf);
				rbuf.flip();
				tch.write(rbuf, end);
			}catch(Exception e) {
				System.err.println(e.getMessage());
			};
			tmp.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println("done!");
	}
	
	public static void new2() {
		try(FileChannel fc = FileChannel.open(Paths.get("tmp.txt"), StandardOpenOption.READ)){
			ByteBuffer buf = ByteBuffer.allocate(1024);
			fc.read(buf);
			buf.flip();
			while(buf.hasRemaining()) {
				String str = new String(buf.array(),0,buf.limit());
				System.out.println("str => "+str);
				buf.position(buf.limit());
			}
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void new3() {
		try(FileChannel fc = (FileChannel) Files.newByteChannel(Paths.get("tmp.txt"), StandardOpenOption.READ)){
			ByteBuffer buf = ByteBuffer.allocate(1024);
			while(fc.read(buf) > 0) {
				buf.flip();
				String str = new String(buf.array(),0,buf.limit());
				System.out.println("str => "+str);
				buf.clear();
			}
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void readLine() {
		File file = new File("tmp.txt");
		try(LineIterator iter = FileUtils.lineIterator(file,Charsets.UTF_8.name())){
			while(iter.hasNext()) {
				String line = iter.nextLine();
				System.out.println(line);
			}
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		new1();
		//readLine();
	}

}
