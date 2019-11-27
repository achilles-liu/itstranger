/**
 * 
 */
package org.core.knowledge.rpc;

import org.core.knowledge.delivery.grpc.GreeterGrpc;
import org.core.knowledge.delivery.grpc.HelloReply;
import org.core.knowledge.delivery.grpc.HelloRequest;

import io.grpc.stub.StreamObserver;

/**
 * core-knowledge-rpc
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-14
 */
public class GreeterImpl extends GreeterGrpc.GreeterImplBase{

	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
		HelloReply reply = HelloReply.newBuilder().setMessage("Hello "+request.getName()).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}
	
}
