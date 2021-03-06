package model;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class Group implements Serializable{

	private int group_id;
	private String name;
	private Date create_date;
	private String icon;
	private String descr;
	private int limitation;
	private int hbti_id;
	private String challengeContent;
	private int numberOfMem;
	private String leader_id;
	private String leader_name;
	
	public Group() {
		
	}
	
	public Group(int group_id, String name, String descr, String icon, int limitation, int hbti_id) {
		this.group_id = group_id;
		this.name = name;
		this.descr = descr;
		this.icon = icon;
		this.limitation = limitation;
		this.hbti_id = hbti_id;
	}

	public String getLeader_name() {
		return leader_name;
	}
	
	public void setLeader_name(String leader_name) {
		this.leader_name = leader_name;
	}
	
	public String getLeader_id() {
		return leader_id;
	}

	public void setLeader_id(String leader_id) {
		this.leader_id = leader_id;

	}
	
	public int getNumberOfMem() {
		return numberOfMem;
	}

	public void setNumberOfMem(int numberOfMem) {
		this.numberOfMem = numberOfMem;
	}

	public int getLimitation() {
		return limitation;
	}

	public void setLimitation(int limitation) {
		this.limitation = limitation;
	}

	public String getChallengeContent() {
		return challengeContent;
	}

	public void setChallengeContent(String challengeContent) {
		this.challengeContent = challengeContent;
	}
	
	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getHbti_id() {
		return hbti_id;
	}

	public void setHbti_id(int hbti_id) {
		this.hbti_id = hbti_id;
	}

}
