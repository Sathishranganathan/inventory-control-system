package org.ntu.eee.csn.oosd.jvoter.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;



/**
 * 
 * @author WangDing
 *
 */
public class VoteTest extends TestCase {
	private static Logger LOGGER = Logger.getLogger(VoteTest.class);

	
	/**
	 * Constructor
	 */
	public VoteTest() {
		super();
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("instantiate");
	
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            test name
	 */
	public VoteTest(String name) {
		super(name);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("instantiate");
	}

	/**
	 * Declares the test suite.
	 * 
	 * @return the test suite.
	 */
	public static TestSuite suite() {
		TestSuite testSuite = new TestSuite(VoteTest.class);
		return testSuite;
	}

	public void testVoteTable(){
		
		LOGGER.info("testVoteTable");
		//fail("Not yet implemented");
		Vote tVote = new Vote();
		String voteID = "testVote";
		String desc = "which unit test do you like?";
		String initiator = "junit";
		Date deadline = new Date();
		
		tVote.setVoteID(voteID);
		tVote.setDesc(desc);
		tVote.setInitiator(initiator);
		tVote.setDeadline(deadline);
		
		tVote.add();
		assertEquals(voteID, tVote.select(voteID).getVoteID());
	    tVote.delete();
	}
}
