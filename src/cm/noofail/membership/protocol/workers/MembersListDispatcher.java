package cm.noofail.membership.protocol.workers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cm.noofail.logs.Logs;
import cm.noofail.membership.protocol.utils.OnlineMembersList;
import cm.noofail.membership.protocol.utils.Process;
import cm.noofail.messages.MessageSender;
import cm.noofail.messages.MessageType;
import cm.noofail.models.Member;
import cm.noofail.models.Message;
import cm.noofail.time.TimeControl;

public class MembersListDispatcher extends Process {
	private static String name = "Dispatcher";
	
	Logs logs = new Logs(this.getClass());
	
	private OnlineMembersList membershipList;
	private MessageSender messageSender;
	private Member member;
	
	public MembersListDispatcher(OnlineMembersList membershipList, 
			MessageSender messageSender, Member member) {
		super(name);
		this.membershipList = membershipList;
		this.messageSender = messageSender;
		this.member = member;
	}
	
	public void run() {
		logs.info("Memberlist dispatcher started");
		while (member.isInGroup()) {
			List<Member> randomMember = getRandomNode(membershipList);
			if (randomMember != null && randomMember.size() > 0)
				dispatch(randomMember, membershipList);
			customWait(500);
		}
	}
	
	private void dispatch(List<Member> randomMembers, OnlineMembersList membershipList) {
		if (randomMembers == null || membershipList == null) {
			return;
		}
		logs.info("Sending membershiplist to somes nodes...");
		
		TimeControl.increment();
		member.setHeartbeat(TimeControl.getTime());
		
		List<Member> members = toList(membershipList.getMembers().values());
		members.add(member);
		int count = members.size();
		
		Message messageRequest = new Message(MessageType._GOSSIP, member, TimeControl.getTime());
		messageRequest.setMembershipList(members);
		logs.info("Sending " + count + " node's informations");
		
		for (Member randomMember : randomMembers) {
			logs.info("to node: " + randomMember.getNode().getIpAddress() + ":" + randomMember.getNode().getPort());
			messageSender.send(messageRequest, randomMember.getNode());
		}
	}
	
	private List<Member> toList(Collection<Member> members) {
		if (members == null) {
			return Collections.emptyList();
		}
		return new ArrayList<>(members);
	}
	
	public List<Member> getRandomNode(OnlineMembersList membershipList) {
		if (membershipList == null || membershipList.size() == 0) {
			return Collections.emptyList();
		}
		
		List<Member> membersList = new ArrayList<>(membershipList.getMembers().values());
		int size = membersList.size();
		
		Random random = new Random();
		int randomNodeIndex = random.nextInt(size);
		
		List<Member> randomNodes = new ArrayList<>();
		randomNodes.add(membersList.get(randomNodeIndex));
		
		if (size <= 3) {
			return randomNodes;
		}
		
		if (randomNodeIndex + 1 < size) {
			randomNodes.add(membersList.get(randomNodeIndex + 1));
			if (randomNodeIndex + 2 < size) {
				randomNodes.add(membersList.get(randomNodeIndex + 2));
			} else {
				randomNodes.add(membersList.get(0));
			}
		} else {
			randomNodes.add(membersList.get(0));
			randomNodes.add(membersList.get(1));
		}
		
		return randomNodes;
	}
}