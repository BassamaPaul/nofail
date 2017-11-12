package cm.noofail.membership.protocol.workers;

import java.util.ArrayList;

import cm.noofail.logs.Logs;
import cm.noofail.membership.protocol.config.Configs;
import cm.noofail.membership.protocol.utils.FailedMembersList;
import cm.noofail.membership.protocol.utils.OnlineMembersList;
import cm.noofail.membership.protocol.utils.Process;
import cm.noofail.membership.protocol.utils.RemovedMembersList;
import cm.noofail.models.Member;
import cm.noofail.time.TimeControl;

public class MembersListVerifier extends Process {
	private static String name = "NodeChecker";
	
	Logs logs = new Logs(this.getClass());
	
	private OnlineMembersList onlineMembersList;
	private RemovedMembersList removedMembersList;
	private FailedMembersList failedMembersList;
	private Member member;
	
	public MembersListVerifier(OnlineMembersList membershipList, RemovedMembersList removedMembersList, 
			FailedMembersList failedMembersList, Member member) {
		super(name);
		this.onlineMembersList = membershipList;
		this.removedMembersList = removedMembersList;
		this.failedMembersList = failedMembersList;
		this.member = member;
	}
	
	@Override
	public void run() {
		logs.info("Node availability checker started");
		while (member.isInGroup()) {
			checkMembers(onlineMembersList, removedMembersList, failedMembersList);
			customWait(500);
		}
	}
	
	private void checkMembers(OnlineMembersList onlineMembersList, RemovedMembersList removedMembersList, 
			FailedMembersList failedMembersList) {
		if (onlineMembersList == null || onlineMembersList.size() <= 0)
			return;
		for (Member member : new ArrayList<>(onlineMembersList.getMembers().values())) {
			if (member.getHeartbeat() < TimeControl.getTime() - Configs.REMOVED) {
				onlineMembersList.remove(member.getNode());
				removedMembersList.add(member);
				logs.info("Node: " + member.getNode() + " has been removed.");
			} else if (member.getHeartbeat() < TimeControl.getTime() - Configs.FAILED_TIME) {
				failedMembersList.add(member);
				logs.info("Node: " + member.getNode() + " may be failed.");
			}
		}
	}
}