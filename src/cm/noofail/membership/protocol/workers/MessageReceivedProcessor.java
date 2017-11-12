package cm.noofail.membership.protocol.workers;

import java.util.Collection;

import cm.noofail.comm.server.socket.SocketServer;
import cm.noofail.logs.Logs;
import cm.noofail.membership.protocol.utils.OnlineMembersList;
import cm.noofail.membership.protocol.utils.Process;
import cm.noofail.membership.protocol.utils.RemovedMembersList;
import cm.noofail.messages.MessageSender;
import cm.noofail.messages.MessageType;
import cm.noofail.messages.formatter.MessageFormatter;
import cm.noofail.messages.queue.MessageQueue;
import cm.noofail.models.Member;
import cm.noofail.models.Message;
import cm.noofail.models.Node;
import cm.noofail.time.TimeControl;

public class MessageReceivedProcessor extends Process {
	private static String name = "Receiver";
	
	Logs logs = new Logs(getClass());
	
	private OnlineMembersList onlineMembersList;
	private RemovedMembersList removedMembersList;
	private Member member;
	private MessageSender messageSender;
	private MessageFormatter formatter;
	private MembersListDispatcher dispatcher;
	private MembersListVerifier nodeAvailabilityChecker;
	
	public MessageReceivedProcessor(OnlineMembersList onlineMembersList, RemovedMembersList removedMembersList, 
			Member member, MessageSender messageSender, MessageFormatter messageFormatter, 
			MembersListDispatcher dispatcher, MembersListVerifier nodeAvailabilityChecker) {
		super(name);
		this.onlineMembersList = onlineMembersList;
		this.removedMembersList = removedMembersList;
		this.member = member;
		this.messageSender = messageSender;
		this.formatter = messageFormatter;
		this.dispatcher = dispatcher;
		this.nodeAvailabilityChecker = nodeAvailabilityChecker;
	}
	
	public void run() {
		logs.info("Starting message receiver...");
		startMessageReceiver();
		logs.info("ReceiverCallBack started...");
		while (member.isInitiated()) {
			if (!MessageQueue.isEmpty()) {
				Message message = MessageQueue.dequeue();
				process(member, message);
			}
			customWait(500);
		}
	}
	
	private void process(Member member, Message message) {
		if (member == null || message == null)
			return;
		MessageType type = message.getMessageType();
		logs.info("Receiving '" + type + "' message from: " + message.getMember().getNode());
		
		TimeControl.update(message.getSenderTime());
		member.setHeartbeat(TimeControl.getTime());
		
		if (member.isInGroup()) {
			if (type == MessageType.JOINREQ) {
				Member newMember = message.getMember();
				newMember.setHeartbeat(TimeControl.getTime());
				addNewMember(newMember);
				
				Message messageReponse = new Message(MessageType.JOINREP, member, TimeControl.getTime());
				messageSender.send(messageReponse, newMember);
				logs.info("Introduce node: " + newMember.getNode() + " at time: " + TimeControl.getTime());
			} else if (type == MessageType._GOSSIP)
				updateMembershipList(message.getMembershipList());
		} else if (type == MessageType.JOINREP) {
			member.setInGroup(true);
			dispatcher.start();
			nodeAvailabilityChecker.start();
			addNewMember(message.getMember());
			logs.info("Introduced at time: " + TimeControl.getTime());
		}
	}
	
	private void addNewMember(Member member) {
		onlineMembersList.add(member);
	}
	
	private void updateMembershipList(Collection<Member> list) {
		if (list == null) return;
		for (Member entry : list) {
			if (!entry.equals(member)) {
				Node node = entry.getNode();
				boolean removedBecomeOnline = 
						removedMembersList.isExist(node) && removedMembersList.get(node).getHeartbeat() < entry.getHeartbeat();
				if (!removedMembersList.isExist(node) && (!onlineMembersList.isExist(node) || (onlineMembersList.isExist(node) 
						&& entry.getHeartbeat() > onlineMembersList.get(node).getHeartbeat())) || removedBecomeOnline) {
					onlineMembersList.add(entry);
				}
				if (removedBecomeOnline) {
					removedMembersList.remove(node);
				}
			}
		}
	}
	
	private void startMessageReceiver() {
		new SocketServer(member.getNode(), formatter).start();
	}
}