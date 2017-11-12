package cm.noofail.membership.protocol;

import cm.noofail.logs.Logs;
import cm.noofail.membership.protocol.utils.FailedMembersList;
import cm.noofail.membership.protocol.utils.OnlineMembersList;
import cm.noofail.membership.protocol.utils.RemovedMembersList;
import cm.noofail.membership.protocol.workers.MembersListDispatcher;
import cm.noofail.membership.protocol.workers.MembersListVerifier;
import cm.noofail.membership.protocol.workers.MessageReceivedProcessor;
import cm.noofail.messages.MessageSender;
import cm.noofail.messages.MessageType;
import cm.noofail.messages.formatter.MessageFormatter;
import cm.noofail.models.Member;
import cm.noofail.models.Message;
import cm.noofail.models.Node;
import cm.noofail.time.TimeControl;

public class MembershipProtocol {
	Logs logs = new Logs(MembershipProtocol.class);
	
	private Node introductor;
	private Member member;
	private OnlineMembersList onlineMembersList;
	private RemovedMembersList removedMembersList;
	private FailedMembersList failedMembersList;
	private MessageSender messageSender;
	private MembersListDispatcher dispatcher;
	private MessageReceivedProcessor receiverCallBack;
	private MembersListVerifier membersListVerifier;
	
	public MembershipProtocol(Node node, Node introductor, 
			MessageFormatter formatter, MessageSender messageSender) {
		this.introductor = introductor;
		this.messageSender = messageSender;
		member = new Member(node, false, false, 0);
		onlineMembersList = new OnlineMembersList();
		removedMembersList = new RemovedMembersList();
		failedMembersList = new FailedMembersList();
		dispatcher = new MembersListDispatcher(onlineMembersList, this.messageSender, member);
		membersListVerifier = new MembersListVerifier(onlineMembersList, removedMembersList, failedMembersList, member);
		receiverCallBack = new MessageReceivedProcessor(onlineMembersList, removedMembersList, member, this.messageSender, 
				formatter, dispatcher, membersListVerifier);
	}
	
	public void start() {
		member.setInitiated(true);
		receiverCallBack.start();
		logs.info("*********** " + member.getNode() + " ***********");
		introduceSelfToGroup();
	}
	
	private void introduceSelfToGroup() {
		if (member.getNode().equals(introductor)) {
			logs.info("Introduce myself to group");
			member.setInGroup(true);
			dispatcher.start();
			membersListVerifier.start();
		} else {
			logs.info("Introduce me to group");
			TimeControl.increment();
			Message messageRequest = new Message(MessageType.JOINREQ, member, TimeControl.getTime());
			messageSender.send(messageRequest, introductor);
		}
	}
	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public OnlineMembersList getMembershipList() {
		return onlineMembersList;
	}

	public void setMembershipList(OnlineMembersList membershipList) {
		this.onlineMembersList = membershipList;
	}
}