/**
 * 
 */
package org.core.knowledge.rpc.caller;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.core.knowledge.delivery.grpc.GreeterGrpc;
import org.core.knowledge.delivery.grpc.HelloReply;
import org.core.knowledge.delivery.grpc.HelloRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * core-knowledge-rpc-caller
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-14
 */
public class HelloWorldCaller {
	
	private ManagedChannel channel;
	private GreeterGrpc.GreeterBlockingStub blockingStub;
	
	public HelloWorldCaller(String host,int port) {
		this(ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext().build());
	}
	
	HelloWorldCaller(ManagedChannel channel){
		this.channel = channel;
		this.blockingStub = GreeterGrpc.newBlockingStub(channel);
	}
	
	public void shutdown() throws InterruptedException {
		this.channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
	
	public void greet(String name) {
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		HelloReply response;
		try {
			response = this.blockingStub.sayHello(request);
		} catch (StatusRuntimeException  e) {
			System.err.println(e.getMessage());
			return ;
		}
		System.out.println("Greeting: "+response.getMessage());
	}

	public static void main(String[] args) throws InterruptedException {
		HelloWorldCaller caller = new HelloWorldCaller("localhost", 50051);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		try {
			for(;;) {
				System.out.print("please input message:");
				String str = scanner.nextLine();
				if("quit".equals(str)) break;
				caller.greet(str);
			}
		}finally {
			caller.shutdown();
		}
	}

}
