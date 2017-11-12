package cm.noofail;

import cm.noofail.logs.Logs;
import cm.noofail.membership.protocol.MembershipProtocol;
import cm.noofail.messages.MessageSender;
import cm.noofail.messages.formatter.MessageFormatter;
import cm.noofail.messages.formatter.MessageFormatterFactory;
import cm.noofail.messages.tcp.TCPMessageSender;
import cm.noofail.models.Node;

public class Runtime {
	public static void main(String[] args) {
		Logs logs = new Logs(Runtime.class);
		
		if (args == null || args.length < 2) {
			logs.error("You must give 2 parameters. IPAddress and Port of Introductor Node and Current Node");
			System.out.println("For running, launch the introductor process first");
			System.out.println("like this: java -jar noofail.jar introductorIP:introductorPort introductorIP:introductorPort");
			System.out.println("After that, you can run any others process");
			System.out.println("like this: java -jar noofail.jar nodeIP:nodePort introductorIP:introductorPort");
			return;
		}
		
		String introductorIpAddressAndPort = args[0];
		String nodeIpAddressAndPort = args[1];
		
		MessageFormatter formatter = MessageFormatterFactory.getGsonFormatter();
		Node introductor = new Node(introductorIpAddressAndPort);
		Node currentNode = new Node(nodeIpAddressAndPort);
		
		MessageSender messageSender = new TCPMessageSender(formatter);
		MembershipProtocol membershipProtocol = 
				new MembershipProtocol(currentNode, introductor, formatter, messageSender);
		logs.info("Starting Membership protocole...");
		membershipProtocol.start();
		logs.info("Membership protocole started.");
	}
}