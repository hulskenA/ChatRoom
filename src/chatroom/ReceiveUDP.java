package chatroom;


import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Calendar;
import java.util.Properties;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ReceiveUDP extends Thread {

	private MulticastSocket socket;
	private InetAddress group;
	private DatagramPacket packet;
	private JTextArea text;

	public ReceiveUDP(JTextArea txt) {
		try {
			this.text = txt;
			this.group = InetAddress.getByName("224.0.0.1");
			this.socket = new MulticastSocket(7654);
			this.packet = new DatagramPacket(new byte[512],512);
			this.socket.joinGroup(this.group);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				socket.receive(this.packet);
//				System.out.println(this.packet.getAddress().getHostName()+" : "+ new String(this.packet.getData()));
				String sender = this.packet.getAddress().getHostName();
				String msg = new String(this.packet.getData());
				String tmp = null;
				HashMap<String, String> values = new HashMap<String, String>();

				try {
					Properties prop = new Properties();
					InputStream input = new FileInputStream("./resources/names.properties");
					prop.load(input);

					for (String key : prop.stringPropertyNames())
						values.put(key, prop.getProperty(key));

					tmp = prop.getProperty(sender);
					if (tmp != null)
						sender = prop.getProperty(sender);

					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (tmp == null) {
					tmp = JOptionPane.showInputDialog(null, "select a new name for this IP adress :\n" + sender);
					if (tmp == null || tmp.isEmpty())
						tmp = sender;
					else {
						tmp = tmp.toUpperCase();
						values.put(sender, tmp);
						sender = tmp;
					}
				}

				try {
					Properties prop = new Properties();
					OutputStream output = new FileOutputStream("./resources/names.properties");
					for (String key : values.keySet())
						prop.setProperty(key, values.get(key));
					sender = tmp;
					prop.store(output, null);
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();

				String date = calendar.get(Calendar.DATE) + "/"+ (calendar.get(Calendar.MONTH) + 1) + "/"+ calendar.get(Calendar.YEAR) + "  " + calendar.get(Calendar.HOUR_OF_DAY) + "h" + calendar.get(Calendar.MINUTE) + "min" + calendar.get(Calendar.SECOND) + "s";
//				text.add(new JLabel(sender));
//				text.add(new JLabel(date));
//				text.add(new JLabel(msg));
				text.append(sender + " (" + date + ")  : "+ msg + "\n\n");
//				JOptionPane.showMessageDialog(null, new JLabel(msg));
				this.packet.setData(new byte[512]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
