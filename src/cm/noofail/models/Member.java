package cm.noofail.models;

public class Member {
	private Node node;
	private boolean initiated;
	private boolean inGroup;
	private boolean failed;
	private long heartbeat;
	
	public Member(String ipAddress, int port, boolean initiated, boolean inGroup, long heartbeat) {
		super();
		node = new Node(ipAddress, port);
		this.initiated = initiated;
		this.inGroup = inGroup;
		this.heartbeat = heartbeat;
	}
	
	public Member(Node node, boolean initiated, boolean inGroup, long heartbeat) {
		super();
		this.node = node;
		this.initiated = initiated;
		this.inGroup = inGroup;
		this.heartbeat = heartbeat;
	}
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public boolean isInitiated() {
		return initiated;
	}
	
	public void setInitiated(boolean initiated) {
		this.initiated = initiated;
	}
	
	public boolean isInGroup() {
		return inGroup;
	}
	
	public void setInGroup(boolean inGroup) {
		this.inGroup = inGroup;
	}
	
	public boolean isFailed() {
		return failed;
	}
	
	public void setFailed(boolean failed) {
		this.failed = failed;
	}
	
	public long getHeartbeat() {
		return heartbeat;
	}
	
	public void setHeartbeat(long heartbeat) {
		this.heartbeat = heartbeat;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Member) {
			return this.getNode().equals(((Member) o).getNode());
		} else {
			return false;
		}
	}
}