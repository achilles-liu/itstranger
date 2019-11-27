/**
 * 
 */
package org.core.knowledge.rpc.callee;

import java.io.IOException;

import org.core.knowledge.rpc.GreeterImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * core-knowledge-rpc
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-14
 */
public class HelloWorldCallee {
	
	private Server server;
	
	private void start() throws IOException {
		int port = 50051;
		server = ServerBuilder.forPort(port)
				.addService(new GreeterImpl()).build().start();
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				HelloWorldCallee.this.stop();
				System.err.println("*** server shut down");
			}
			
		});
	}
	
	private void stop() {
		if(server != null) {
			server.shutdown();
		}
	}
	
	private void blockUntilShutdown() throws InterruptedException {
		if(server != null) {
			server.awaitTermination();
		}
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		HelloWorldCallee server = new HelloWorldCallee();
		server.start();
		server.blockUntilShutdown();
	}
}
