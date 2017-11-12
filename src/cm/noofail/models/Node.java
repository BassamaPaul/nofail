package cm.noofail.models;

public class Node {
	private String ipAddress;
	private int port;
	
	public Node(String ipAddressAndPort) {
		super();
		parseIpAddressAndPort(ipAddressAndPort);
	}
	
	private void parseIpAddressAndPort(String ipAddressAndPort) {
		if (ipAddressAndPort == null || !ipAddressAndPort.contains(":")) {
			throw new RuntimeException();
		}
		String[] ipAndPort = ipAddressAndPort.split(":");
		this.ipAddress = ipAndPort[0];
		this.port = Integer.parseInt(ipAndPort[1]);
	}

	public Node(String ipAddress, int port) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null || o instanceof Node) {
			Node node = (Node) o;
			return ipAddress.equals(node.getIpAddress()) && port == node.getPort();
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.ipAddress + ":" + this.port;
	}
}