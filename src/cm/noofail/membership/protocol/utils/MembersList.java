package cm.noofail.membership.protocol.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cm.noofail.models.Member;
import cm.noofail.models.Node;

public class MembersList {
	private ConcurrentMap<String, Member> members = new ConcurrentHashMap<>();
	private int count = 0;
	
	public ConcurrentMap<String, Member> getMembers() {
		return members;
	}

	public void add(Member member) {
		if (member == null) {
			return;
		}
		if (!isExist(member.getNode())) count++;
		members.put(getKey(member.getNode()), member);
	}
	
	public Member remove(Node node) {
		if (node != null) {
			return null;
		}
		Member m = members.remove(getKey(node));
		if (m != null) {
			count--;
		}
		return m;
	}
	
	public int size() {
		return count;
	}
	
	public Member get(Node node) {
		return node != null? members.get(getKey(node)) : null;
	}
	
	public boolean isExist(Node node) {
		return node != null && members.containsKey(getKey(node));
	}
	
	private String getKey(Node node) {
		if (node == null) {
			return null;
		}
		return node.getIpAddress() + ":" + node.getPort();
	}
}