package cm.noofail.models;

import java.util.List;

import cm.noofail.messages.MessageType;

public class Message {
	private MessageType messageType;
	private Member senderMember;
	private List<Member> membershipList;
	private boolean success;
	private long senderTime;
	
	public Message(MessageType messageType, Member member, long senderTime) {
		super();
		this.messageType = messageType;
		this.senderMember = member;
		this.senderTime = senderTime;
	}

	public MessageType getMessageType() {
		return messageType;
	}
	
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
	public Member getMember() {
		return senderMember;
	}

	public void setMember(Member member) {
		this.senderMember = member;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Member> getMembershipList() {
		return membershipList;
	}

	public void setMembershipList(List<Member> membershipList) {
		this.membershipList = membershipList;
	}

	public long getSenderTime() {
		return senderTime;
	}

	public void setSenderTime(long senderTime) {
		this.senderTime = senderTime;
	}
}