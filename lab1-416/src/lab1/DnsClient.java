package lab1;
/**
 * UDPClient
 * 
 * Adapted from the example given in Section 2.8 of Kurose and Ross, Computer
 * Networking: A Top-Down Approach (5th edition)
 * 
 * @author michaelrabbat
 * 
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random; 

public class DnsClient {
	
	private static int k = 0;

	public static void main(String args[]) throws Exception{
		
		
		// Open a reader to input from the command line
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		// Create a UDP socket
		// (Note, when no port number is specified, the OS will assign an arbitrary one)
		DatagramSocket clientSocket = new DatagramSocket();
		
		// Resolve a domain name to an IP address object
		// In this case, "localhost" maps to the so-called loop-back address, 127.0.0.1
		InetAddress ipAddress = InetAddress.getByName("localhost");
		
		// Allocate buffers for the data to be sent and received
				
		
		int port = 53;
		int timeOut = 5;
		String parseName = "";
		String parseType = "A"; // Address
		byte[] currentSendData = new byte[1024];
		int sendDataOK = 0;
		byte[] sendData = Arrays.copyOf(currentSendData, sendDataOK);
		byte[] receiveData = new byte[1024];
		// User inputs query in the format required
		////////
		
		System.out.println("Enter query");
		//first args is the IP address of host
		args[0] = inFromUser.readLine();
		System.out.println(args[0]);
		
		
		//second args is domain name
		args[1] = inFromUser.readLine();
		System.out.println(args[1]);
		
		String sentence = args[0] + " " + args[1];
		System.out.println(sentence);
		
		
		//String sentence = inFromUser.readLine();
		
		int aliasDetected = 0;
		int increase = 0;
		
		// i think this checking argument only works with run configuration 
		/*
		try {
			for (int i=0; i<args.length; i++) {
				char[] bitArgs = args[i].toCharArray();
				for (int j=0; j<bitArgs.length; j++) {
					if (bitArgs[j] == '@') {
						parseName = args[i+1];
						aliasDetected++;	
					}
					else if (bitArgs[j] == '.')
						increase++;
					
					else if (bitArgs)
				}
			}
		}
		*/

		/////
		
		// Convert the sentence from a String to an array of bytes
		sendData = sentence.getBytes();
		
		// Create a UDP packet to be sent to the server
		// This involves specifying the sender's address and port number
		//DatagramPacket(byte[] data to send,array data length,IPaddress,port number)
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
		
		// Send the packet
		clientSocket.send(sendPacket); // ok
		
		
		
		
		
		
		
		// DNS packet header - based on dns primer
		
		
		// for the ones with 16 bits
		
		// ID	16bits	random
		byte[] ID = new byte[2];
		Random randomID = new Random();
		randomID.nextBytes(ID);
		
		currentSendData[sendDataOK++]= ID[0];
		currentSendData[sendDataOK++]= ID[1];
		
		// not sure
		currentSendData[sendDataOK++]= 1;
		currentSendData[sendDataOK++]= 0;
		
		// QDCOUNT 	16bits	1
		currentSendData[sendDataOK++]= 0;
		currentSendData[sendDataOK++]= 1;
		
		// ANCOUNT	16bits	0
		currentSendData[sendDataOK++]= 0;
		currentSendData[sendDataOK++]= 0;
		
		// NSCOUNT	16bits	0 ignore
		currentSendData[sendDataOK++]= 0;
		currentSendData[sendDataOK++]= 0;
		
		// ARCOUNT 16biys 0
		currentSendData[sendDataOK++]= 0;
		currentSendData[sendDataOK++]= 0;
		
		// queueType
		byte[] queueType = {0,0};
		
		if (parseType == "A") 
			queueType[1] = 1;	// 0x0001		
		else if (parseType == "MX") 
			queueType[1] = 15;	// 0x000f
		else if (parseType == "NS") 
			queueType[1] = 2;	// 0x0002
		
		
		currentSendData[sendDataOK++] = 0;
		currentSendData[sendDataOK++] = queueType[1];
		
		// queueClass
		currentSendData[sendDataOK++]= 0;
		currentSendData[sendDataOK++]= 1;
		
		// the ones with 1bit and 4bit header
		
		int QR;
		int OPcode;
		int AA = 3;
		int TC;
		int RD;
		int RA;
		int Z;
		int Rcode;
		
		
		
		
		
		
		// while waiting for DNS server's response
				// Create a packet structure to store data sent back by the server
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // ok
				
				// Receive data from the server
				clientSocket.receive(receivePacket); //ok
				
				long timeBegins = System.currentTimeMillis(); //ok
				long timeEnds = System.currentTimeMillis(); //ok
				long totalTime = timeEnds - timeBegins; //ok
		
		// Extract the sentence (as a String object) from the received byte stream
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("From Server: " + modifiedSentence);
		
		// Close the socket
		clientSocket.close();
	}
	
	
}


