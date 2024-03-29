package org.ntu.eee.csn.oosd.jvoter.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.ntu.eee.csn.oosd.jvoter.util.DBUtil;

/**
 * An instance of vote is used to save the vote content when initiating a vote
 * 
 * @author WangYabin G1101031G
 * 
 */
public class Vote implements Serializable {

	/**
	 * Sytem Generated ID, don't remove!
	 */
	private static final long serialVersionUID = -7039854193326114361L;
	
	
	String voteID;

	// Note : by default, there is only no more than 4 options for a vote
	ArrayList<String> options = new ArrayList<String>();

	private String initiator;
	String InitiatorIP;
	String desc;
	private String name; // name represents the vote title
	private boolean isInitiator;
	private boolean isCanceled;
	private boolean isReply = false;

	private Date deadline = new Date();
	
	transient DBUtil db = new DBUtil();
	Connection conn = null;
	Statement stmt=null;
	ResultSet rs = null;
	String sql = null;
	PreparedStatement ps = null;
	
	SimpleDateFormat   format   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");
	
	public Vote(String ID,String nm,String descc, ArrayList<String> op, Date date,String ip,boolean isInitiator,boolean isCal)
	{
		this.voteID = ID;
		this.name=nm;
		this.desc=descc;
		this.options=op;
		this.deadline=date;
		this.InitiatorIP = ip;
		this.isInitiator = isInitiator;
		this.isCanceled = isCal;
	}
	
	public Vote(){
		
		
	}

	public String getUniqueID(){
		return DBUtil.generateGUID();
		
		
	}
	
	public void setVoteID(String voteID) {
		this.voteID = voteID;
	}
	
	public String getVoteID() {
	 
		return voteID;
	}

	public boolean isReply() {
		return isReply;
	}

	public void setReply(boolean isReply) {
		this.isReply = isReply;
	}

	public ArrayList<String> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getInitiatorIP() {
		return InitiatorIP;
	}

	public void setInitiatorIP(String initiatorIP) {
		this.InitiatorIP = initiatorIP;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}


	public boolean getIsInitiator() {
		return isInitiator;
	}

	public void setIsInitiator(boolean isInitiator) {
		this.isInitiator = isInitiator;
	}
	
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanceled() {
		return isCanceled;
	}
	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}
	public void add(){
		
		try {
			sql = "INSERT INTO VOTE(VOTEID, DESC, INITIATORIP, INITIATOR, NAME," +
					"DEADLINE, ISINITIATOR, ISCANCELED, ISREPLY, OPTION1, OPTION2, OPTION3, OPTION4) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, this.voteID);
			ps.setString(2, this.desc);
			ps.setString(3, this.InitiatorIP);
			ps.setString(4, this.initiator);
			ps.setString(5, this.name);
			ps.setString(6, format.format(deadline));
			ps.setBoolean(7, this.isInitiator);
			ps.setBoolean(8, this.isCanceled);
			ps.setBoolean(9, this.isReply);
			ps.setString(10, this.options.get(0));
			ps.setString(11, this.options.get(1));
			ps.setString(12, this.options.get(2));
			ps.setString(13, this.options.get(3));
			ps.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delete(){
		try {
			sql = "delete from vote where voteid=?";
			conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1,this.voteID);
			ps.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vote select(){
		Vote tmpVote = new Vote();
		ArrayList<String> tmpoptions = new ArrayList<String>();
		
		try {
			sql = "select * from vote where voteid=?";
			conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1,this.voteID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				tmpVote.setVoteID(rs.getString("voteid"));
				tmpVote.setDesc(rs.getString("desc"));
				tmpVote.setInitiatorIP(rs.getString("initiatorip"));
				try { 
					tmpVote.setDeadline(format.parse(rs.getString("deadline")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tmpVote.setInitiator(rs.getString("initiator"));
				tmpVote.setName(rs.getString("name"));
				tmpVote.setIsInitiator(rs.getBoolean("isInitiator"));
				tmpVote.setCanceled(rs.getBoolean("iscanceled"));
				tmpVote.setReply(rs.getBoolean("isreply"));
				
				tmpoptions.add(rs.getString("option1"));
				tmpoptions.add(rs.getString("option2"));
				tmpoptions.add(rs.getString("option3"));
				tmpoptions.add(rs.getString("option4"));
				tmpVote.setOptions(tmpoptions);
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmpVote;
	}

	public static ArrayList<Vote> getUnAnsweredVotes(){ 
		ArrayList<Vote> vlist = new ArrayList<Vote>();
		try {
			String sql = "select * from vote where isReply=false";
			DBUtil db = new DBUtil();
			Connection conn = db.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			SimpleDateFormat   format   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");

			while(rs.next()){
				Vote tmpVote = new Vote();
				ArrayList<String> tmpoptions = new ArrayList<String>();
				
				tmpVote.setVoteID(rs.getString("voteid"));
				tmpVote.setDesc(rs.getString("desc"));
				tmpVote.setInitiatorIP(rs.getString("initiatorip"));
				try { 
					tmpVote.setDeadline(format.parse(rs.getString("deadline")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tmpVote.setInitiator(rs.getString("initiator"));
				tmpVote.setName(rs.getString("name"));
				tmpVote.setIsInitiator(rs.getBoolean("isInitiator"));
				tmpVote.setCanceled(rs.getBoolean("iscanceled"));
				tmpVote.setReply(rs.getBoolean("isreply"));
				
				tmpoptions.add(rs.getString("option1"));
				tmpoptions.add(rs.getString("option2"));
				tmpoptions.add(rs.getString("option3"));
				tmpoptions.add(rs.getString("option4"));
				tmpVote.setOptions(tmpoptions);
			
				vlist.add(tmpVote);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vlist;
	}
	
	public static ArrayList<VoteResult> getAllJoinedVotes(){
		ArrayList<VoteResult> rlist = new ArrayList<VoteResult>();
		PreparedStatement psRes = null;
		ResultSet rsRes = null;
		String sql = "select * from vote where isInitiator=false";
		String sqlRes = "select choice, count('choice') cnt from votereply where voteid=? group by choice";
		String voteID = null;
		
		String initiator;
		String InitiatorIP;
		String desc;
		String name; // name represents the vote title
		boolean isInitiator;
		boolean isCanceled;
		boolean isReply;
		Date deadline;
		String option1;
		String option2;
		String option3;
		String option4;
		
		
		try {
			DBUtil db = new DBUtil();
			SimpleDateFormat   format   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");
			
			Connection conn = db.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				
				ArrayList<String> options = new ArrayList<String>();
				ArrayList<Integer> optionRes = new ArrayList<Integer>();
				optionRes.add(0);
				optionRes.add( 0);
				optionRes.add(0);
				optionRes.add(0);
				
				VoteResult tmpVoteResult = new VoteResult();
				voteID = rs.getString("voteid");
				initiator = rs.getString("initiator");
				InitiatorIP = rs.getString("initiatorip");
				desc = rs.getString("desc");
				name = rs.getString("name");
				isInitiator = rs.getBoolean("isinitiator");
				isCanceled = rs.getBoolean("iscanceled");
				isReply = rs.getBoolean("isReply");
				try {
					deadline = format.parse(rs.getString("deadline"));
					tmpVoteResult.setDeadline(deadline);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				option1 = rs.getString("option1");
				option2 = rs.getString("option2");
				option3 = rs.getString("option3");
				option4 = rs.getString("option4");
				options.add(0, option1);
				options.add(1, option2);
				options.add(2, option3);
				options.add(3, option4);
				
				psRes = conn.prepareStatement(sqlRes);
				psRes.setString(1, voteID);
				rsRes = psRes.executeQuery();
				while(rsRes.next()){
					optionRes.set(rsRes.getInt("choice"),rsRes.getInt("cnt"));
				}
				
				tmpVoteResult.setResult(optionRes);
				tmpVoteResult.setCanceled(isCanceled);
				
				tmpVoteResult.setDesc(desc);
				tmpVoteResult.setInitiator(initiator);
				tmpVoteResult.setInitiatorIP(InitiatorIP);
				tmpVoteResult.setIsInitiator(isInitiator);
				tmpVoteResult.setReply(isReply);
				tmpVoteResult.setName(name);
				tmpVoteResult.setOptions(options);
				
				rlist.add(tmpVoteResult);
				
				
				
				
				
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rlist;
	}
	public static ArrayList<VoteResult> getAllVoteResult(){
		ArrayList<VoteResult> rlist = new ArrayList<VoteResult>();
		
		PreparedStatement psRes = null;
		ResultSet rsRes = null;
		String sql = "select * from vote where isInitiator=true";
		String sqlRes = "select choice, count('choice') cnt from votereply where voteid=? group by choice";
		String voteID = null;
		
		String initiator;
		String InitiatorIP;
		String desc;
		String name; // name represents the vote title
		boolean isInitiator;
		boolean isCanceled;
		boolean isReply;
		Date deadline;
		String option1;
		String option2;
		String option3;
		String option4;

		try {
			DBUtil db = new DBUtil();
			SimpleDateFormat   format   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");
			
			Connection conn = db.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				ArrayList<String> options = new ArrayList<String>();
				options.add("");
				options.add("");
				options.add("");
				options.add("");
				ArrayList<Integer> optionRes = new ArrayList<Integer>();
				optionRes.add(0);
				optionRes.add(0);
				optionRes.add(0);
				optionRes.add(0);
				VoteResult tmpVoteResult = new VoteResult();
				voteID = rs.getString("voteid");
				initiator = rs.getString("initiator");
				InitiatorIP = rs.getString("initiatorip");
				desc = rs.getString("desc");
				name = rs.getString("name");
				isInitiator = rs.getBoolean("isinitiator");
				isCanceled = rs.getBoolean("iscanceled");
				isReply = rs.getBoolean("isReply");
				try {
					deadline = format.parse(rs.getString("deadline"));
					tmpVoteResult.setDeadline(deadline);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				option1 = rs.getString("option1");
				option2 = rs.getString("option2");
				option3 = rs.getString("option3");
				option4 = rs.getString("option4");
				options.set(0, option1);
				options.set(1, option2);
				options.set(2, option3);
				options.set(3, option4);	
				
				
				psRes = conn.prepareStatement(sqlRes);
				psRes.setString(1, voteID);
				rsRes = psRes.executeQuery();
				System.out.println("abc"+voteID);
				while(rsRes.next()){
					optionRes.set(rsRes.getInt("choice"),rsRes.getInt("cnt"));
					System.out.println("abc: "+rsRes.getInt("choice")+rsRes.getInt("cnt"));
				}
				
				tmpVoteResult.setResult(optionRes);
				tmpVoteResult.setCanceled(isCanceled);
				
				tmpVoteResult.setDesc(desc);
				tmpVoteResult.setInitiator(initiator);
				tmpVoteResult.setInitiatorIP(InitiatorIP);
				tmpVoteResult.setIsInitiator(isInitiator);
				tmpVoteResult.setReply(isReply);
				tmpVoteResult.setName(name);
				tmpVoteResult.setOptions(options);
				
				rlist.add(tmpVoteResult);
				
				
				
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rlist;
	}
	public void updateReply(){
			try {
				sql = "update vote set isreply=true where voteid=?";
				conn = db.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1,this.voteID);
				ps.executeUpdate();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
}
