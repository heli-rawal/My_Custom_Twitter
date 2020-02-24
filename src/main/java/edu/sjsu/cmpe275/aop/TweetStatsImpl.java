package edu.sjsu.cmpe275.aop;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class TweetStatsImpl implements TweetStats {

	private static int lengthOfLongestTweet;
	private static Map<String,Integer> userMessageLengthMap = new TreeMap<String,Integer>();
	private static Map<String,HashSet<String>> userFollowerMap = new TreeMap<>();
	private static Map<String,HashSet<String>> userFollowerWithoutBlockedMap = new TreeMap<>();
	private static Map<String,Integer> messageFollowerLengthMap = new TreeMap<String, Integer>();
	private static Map<String,HashSet<String>> blockedUserFolloweeMap = new TreeMap<String, HashSet<String>>();


	@Override
	public void resetStatsAndSystem() {
		this.lengthOfLongestTweet = 0;
		this.userMessageLengthMap.clear();
		this.userFollowerMap.clear();
		this.messageFollowerLengthMap.clear();
		this.blockedUserFolloweeMap.clear();
		this.userFollowerWithoutBlockedMap.clear();
	}
    
	@Override
	public int getLengthOfLongestTweet() {

		return this.lengthOfLongestTweet;
	}

	public void setLengthOfLongestTweet(String tweet) {
		int tweetLength = tweet.length();

		if (tweetLength > lengthOfLongestTweet)
			this.lengthOfLongestTweet = tweetLength;
	}

	@Override
	public String getMostFollowedUser() {
		String user = null;
		Integer maxFollowers = 0;
		for(Map.Entry<String,HashSet<String>>entry: userFollowerMap.entrySet()) {

			if (entry.getValue().size() > maxFollowers) {
				maxFollowers = entry.getValue().size();
				user = entry.getKey();
			}
		}

		return user;
	}

	// to get most followed user after follow activity
	public void recordFollowActivity(String follower, String followee) {
		if(follower.equals(followee)|| follower.length()==0 || followee.length()==0){
			return;
		}
		HashSet<String>followerSet;
		if(userFollowerMap.containsKey(followee))
			followerSet= userFollowerMap.get(followee);
		else
			followerSet = new HashSet<String>();
		followerSet.add(follower);
		userFollowerMap.put(followee, followerSet);
		//blocked user map

		HashSet<String>followerWOBlockerSet;
		if(userFollowerWithoutBlockedMap.containsKey(followee))
			followerWOBlockerSet= userFollowerWithoutBlockedMap.get(followee);
		else
			followerWOBlockerSet = new HashSet<String>();
		followerWOBlockerSet.add(follower);
		userFollowerWithoutBlockedMap.put(followee, followerWOBlockerSet);

	}

	// After Successful tweet
	public void recordSuccessfulTweet(String user,String message){
		if( message.length()> 140 || user.length()==0 )
			return;
		setUserMessageLengthMap(user,message);
		setMessageFollowerLengthMap(user,message);
	}
	// to get most productive user after tweet
	public void setUserMessageLengthMap(String user, String message) {
		//setLengthOfLongestTweet(message);
		if(userMessageLengthMap.containsKey(user)){
			Integer total = message.length() + userMessageLengthMap.get(user);
			userMessageLengthMap.put(user, total);
		}
		else userMessageLengthMap.put(user,message.length());
	}

	/**
	 * The most productive user is determined by the total length of all the
	 * messages successfully tweeted since the beginning or last reset. If there
	 * is a tie, return the 1st of such users based on alphabetical order. If no
	 * users successfully tweeted, return null.
	 *
	 * @return the most productive user.
	 */
	@Override
	public String getMostProductiveUser() {
		String user = null;
		Integer maxLength = 0;
		for(Map.Entry<String,Integer>entry : userMessageLengthMap.entrySet()){
			if(entry.getValue()> maxLength){
				maxLength = entry.getValue();
				user = entry.getKey();
				//System.out.println("Most productive user:" + user + "length:"+ maxLength);
			}
		}
		return user;
	}

	// to get most popular message after successful tweet

	public void setMessageFollowerLengthMap(String user, String message) {
		Integer currFollowerLength = 0;
		//get number of followers w/o blockers
		if(userFollowerWithoutBlockedMap.containsKey(user)){
			currFollowerLength = userFollowerWithoutBlockedMap.get(user).size();
		}
		if (messageFollowerLengthMap.containsKey(message)){
			Integer oldFollowerLength = messageFollowerLengthMap.get(message);

			if(currFollowerLength < oldFollowerLength)
				return;
		}
		messageFollowerLengthMap.put(message,currFollowerLength);
	}

	/**
	 * @return the message that has been shared with the biggest number of different
	 *         followers when it is successfully tweaked. If the same message (based
	 *         on string equality) has been tweeted more than once, it is considered
	 *         as different message in each tweeting for this purpose, hence the
	 *         numbers of followers for different tweeting actions wil not be added
	 *         together.
	 */
	@Override
	public String getMostPopularMessage() {
		String message = null;
		int maxLength = 0;
		for(Map.Entry<String,Integer>entry : messageFollowerLengthMap.entrySet()){
			if(entry.getValue()> maxLength){
				maxLength = entry.getValue();
				message = entry.getKey();
			}
		}
		return message;
	}

	//to get most blocked user after Record Activity
	public void RecordBlockActivity(String user, String follower){
		if(follower.equals(user)|| follower.length()==0 || user.length()==0){
			return;
		}
		HashSet<String>followeeSet;
		if(blockedUserFolloweeMap.containsKey(follower))
			followeeSet = blockedUserFolloweeMap.get(follower);
		else
			followeeSet = new HashSet<String>();
		followeeSet.add(user);
		blockedUserFolloweeMap.put(follower,followeeSet);

		if(userFollowerWithoutBlockedMap.containsKey(user)){
			this.userFollowerWithoutBlockedMap.get(user).remove(follower);
		}
	}

	@Override
	public String getMostBlockedFollower() {
		String user = null;
		Integer maxBlockeduser = 0;
		for(Map.Entry<String,HashSet<String>>entry: blockedUserFolloweeMap.entrySet()) {

			if (entry.getValue().size() > maxBlockeduser) {
				maxBlockeduser = entry.getValue().size();
				user = entry.getKey();
			}
		}

		return user;
	}
}



