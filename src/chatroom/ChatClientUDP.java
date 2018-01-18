package chatroom;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class ChatClientUDP {

	public static void main(String args[]) throws InterruptedException {

		JFrame window = new JFrame("ChatClientUDP");
		window.setLocationRelativeTo(null);
		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());

//		JPanel textArea = new JPanel();
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		JTextField messages = new JTextField();
		JButton sender = new JButton("Send");

		messages.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				 	sender.doClick();
			}

			@Override
			public void keyReleased(KeyEvent e) {}

		});

		sender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String  s = messages.getText();
				messages.setText("");
				exercice3.SendUDP Sclient ;
				Sclient = new exercice3.SendUDP(s);
				Sclient.start();
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new GridLayout(1, 2));
		panel.add(messages);
		panel.add(sender);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(new EmptyBorder(5, 5, 0, 5));
		textArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		window.add(scrollPane, BorderLayout.CENTER);
		window.add(panel, BorderLayout.SOUTH);

		window.setVisible(true);

		exercice3.ReceiveUDP client = new exercice3.ReceiveUDP(textArea);
		client.start();
	}
}
