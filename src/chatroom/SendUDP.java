package chatroom;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUDP extends Thread {
	private InetAddress dest;
	private int port;
	private DatagramSocket socket;
	private String message;

	public SendUDP(String message) {
		try {
			this.dest = InetAddress.getByName("224.0.0.1");
			this.socket = new DatagramSocket();
			this.port = 7654;
			this.message = message;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		byte array[];
		DatagramPacket p;
		try {
			array = this.message.getBytes();
			p = new DatagramPacket(array, array.length, this.dest, this.port);
			this.socket.send(p);
			this.socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
