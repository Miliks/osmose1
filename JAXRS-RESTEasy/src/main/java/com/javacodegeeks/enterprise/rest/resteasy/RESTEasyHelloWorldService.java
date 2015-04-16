package com.javacodegeeks.enterprise.rest.resteasy;

import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import com.sample.ProcessMain;




import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sample.InitialClassKeySession;
 
//@Path("/RESTEasyHelloWorld")
@Path("/hello")
public class RESTEasyHelloWorldService {
	
	ConnectionFactory factory = new ConnectionFactory();
	Connection connection = null;
	Channel channel = null;
	
	/*@GET
	//@Path("/{pathParameter}")
	public Response responseMsg( @QueryParam("queryParameter") String queryParameter) {

		String response = "Hello from Mila!!!!!: " + " : " + queryParameter;

		return Response.status(200).entity(response).build();
	}*/
	
//	@GET
//	@Path("/{pathParameter}")
//	public Response responseMsg( @PathParam("pathParameter") String pathParameter,
//			@DefaultValue("Nothing to say") @QueryParam("queryParameter") String temperature) {
//			System.out.println("Helllllloooooooooooo");
//			String response = "Hello from Mila!!!!!: " + pathParameter + " : " + temperature;
//			Integer intTemp = Integer.parseInt(temperature);
//			InitialClassKeySession.startProcessInit().startProcess(intTemp);
//			
////			System.out.println("Calling processMain");
////			ProcessMain processMain = new ProcessMain();
////			processMain.startProcess();
////			System.out.println("END processMain");
////			
//			System.out.println("Temperature= " + temperature);
//		return Response.status(200).entity(response).build();
//	}
	
	@GET
	@Path("/temp")
	public Response responseNewMsg( 
			@DefaultValue("Nothing to say") @QueryParam("queryParameter") String temperature) {
			System.out.println("Helllllloooooooooooo");
			String response = "Hello from Mila!!!!!: " + " : " + temperature;
			Integer intTemp = Integer.parseInt(temperature);
			InitialClassKeySession.startProcessInit().startProcess(intTemp);
			
//			System.out.println("Calling processMain");
//			ProcessMain processMain = new ProcessMain();
//			processMain.startProcess();
//			System.out.println("END processMain");
//			
			System.out.println("Temperature in REST service called from bpmn = " + temperature);
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/danger")
	public Response callingNewService( 
			@DefaultValue("Nothing to say") @QueryParam("queryParameter") String temperature) {
			//System.out.println("Helllllloooooooooooo");
			String response = "New process with temperature: " + " : " + temperature;
			//Integer intTemp = Integer.parseInt(temperature);
			//InitialClassKeySession.startProcessInit().startProcess(intTemp);
			
			System.out.println("Go to the new process = " + temperature);
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/rabbit")
	public Response callingRabbitService( 
			@QueryParam("queryParameter") String temperature) {
			
			String response = "New Rabbit with temperature: " + " : " + temperature;
			//ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	       // Connection connection = factory.newConnection();
	        try {
				connection = factory.newConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //Channel channel = connection.createChannel();
	        try {
				channel = connection.createChannel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				channel.queueDeclare("hello", false, false, false, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String message = temperature;
	        try {
				channel.basicPublish("", "hello", null, message.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Integer intTemp = Integer.parseInt(temperature);
			//InitialClassKeySession.startProcessInit().startProcess(intTemp);
			
			System.out.println("Go to the new process = " + temperature);
		return Response.status(200).entity(response).build();
	}
		/*@GET
		@Path("/temp")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String sayPlainTextHello(@QueryParam("temperature") String temp) {
	    System.out.println("Temperature= " + temp);
	    Integer intTemp = Integer.parseInt(temp);
	    //InitialClassKeySession.startProcessInit().startProcess(intTemp);
	    System.out.println("After key session");
		return "Temperature = " + temp;
	  }*/
 
}
