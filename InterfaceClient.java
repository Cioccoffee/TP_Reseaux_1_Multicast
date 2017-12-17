import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InterfaceClient {

	BufferedReader in;
	//PrintStream out;
	JFrame frame = new JFrame("Chat");
	
	MulticastSocket ms;
	InetAddress gpAddress;
	int gpPort;
	 
	JTextField msgField = new JTextField(40);
	JTextArea messageArea = new JTextArea(40, 40);

	JLabel gpAddr = new JLabel("IP of the group :");
	JTextField gpAddrField = new JTextField(20);
	JLabel gpPort = new JLabel("Port of the group :");;
	JTextField gpPortField = new JTextField(10);
	JButton btnMulticast = new Jbutton("Join group");
	JPanel panelMulticast = new JPanel();
	
	public MulticastSocket getMS()
	{
		return ms;
	}
	
	public InterfaceClient(/*MulticastSocket ms /*Socket clientSocket*/) throws IOException {
		
		//out = new PrintStream(clientSocket.getOutputStream());
		// Layout GUI
		panelMulticast.add(gpAddr);
		panelMulticast.add(gpAddrField);
		panelMulticast.add(gpPort);
		panelMulticast.add(gpPortField);
		btnMulticast.addActionListener(new ActionListener() {
			/**
			 * @param e
			 *            
			 */
			public void actionPerformed(ActionEvent e) {
				 this.gpAddress = InetAddress.getByName(gpAddrField.getText()); 
		      	 this.gpPort = Integer.parseInt(gpPortField.getText());
		      	 ms = new MulticastSocket(gpPort);
		      	 ms.joinGroup(gpAddress);
			}
		});
		panelMulticast.add(btnMulticast);
		msgField.setEditable(true);
		msgField.setSelectedTextColor(Color.BLUE);
		messageArea.setEditable(false);
		frame.getContentPane().add(panelMulticast, "North");
		frame.getContentPane().add(msgField, "South");
		frame.getContentPane().add(new JScrollPane(messageArea), "Center");
		frame.pack();

		// Add Listeners
		textField.addActionListener(new ActionListener() {
			/**
			 * @param e
			 *            Envoie le message lorsque le client appuie sur la
			 *            touche Entrée.
			 */
			public void actionPerformed(ActionEvent e) {
				//out.println(msgField.getText());
				String toSend = msgField.getText();
				ms.send(new DatagramPacket (toSend.getBytes(), toSend.length(),gpAddr, gpPort));
				msgField.setText("");
			}
		});
	}

}
