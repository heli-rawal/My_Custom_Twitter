package edu.sjsu.cmpe275.aop;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TweetTest1 {
	/***
	 * These are dummy test cases. You may add test cases based on your own
	 * need.
	 */
	TweetService tweeter;
	TweetStats stats;
	ClassPathXmlApplicationContext ctx;

	@org.junit.Before
	public void runBeforeTestMethod() {
		ctx = new ClassPathXmlApplicationContext("context.xml");
		tweeter = (TweetService) ctx.getBean("tweetService");
		stats = (TweetStats) ctx.getBean("tweetStats");
		System.out.println("@Before - runBeforeTestMethod");
	}

	// Should rename to @AfterTestMethod
	@org.junit.After
	public void runAfterTestMethod() {
		System.out.println("@After - runAfterTestMethod");
		ctx.close();
	}

	@Test
	public void testLengthOfLongestTweet() {
		try {
			stats.resetStatsAndSystem();
			assertThat(stats.getLengthOfLongestTweet(), is(0));

			tweeter.tweet(null, "first tweet");
			assertThat(stats.getLengthOfLongestTweet(), is(0));

			tweeter.tweet("bob", "first tweet");
			assertThat(stats.getLengthOfLongestTweet(), is(11));

			stats.resetStatsAndSystem();
			assertThat(stats.getLengthOfLongestTweet(), is(0));

			tweeter.tweet("bob", "first tweet");
			assertThat(stats.getLengthOfLongestTweet(), is(11));

			tweeter.tweet("alex",
					"first tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweet");
			assertThat(stats.getLengthOfLongestTweet(), is(297));

			tweeter.tweet("bob", "first tweet");
			assertThat(stats.getLengthOfLongestTweet(), is(297));

		} catch (Exception e) {
			System.out.println("Error in test : testLengthOfLongestTweet");
		}
	}

	@Test
	public void testMostFollowedUser() {
		try {
			stats.resetStatsAndSystem();
			String temp = stats.getMostFollowedUser();
			// assertThat(temp,null);

			tweeter.follow("bob", "alex");
			assertThat(stats.getMostFollowedUser(), is("alex"));

			// Same number alphabet order check
			stats.resetStatsAndSystem();
			tweeter.follow("bob", "alex");
			tweeter.follow("vajid", "alex");
			tweeter.follow("vajid", "bob");
			tweeter.follow("alex", "bob");
			assertThat(stats.getMostFollowedUser(), is("alex"));

			// Different Number check
			stats.resetStatsAndSystem();
			tweeter.follow("bob", "alex");
			tweeter.follow("vajid", "alex");
			tweeter.follow("vajid", "bob");
			tweeter.follow("alex", "bob");
			tweeter.follow("m", "bob");
			assertThat(stats.getMostFollowedUser(), is("bob"));

			// Different Number check same user follow again check
			stats.resetStatsAndSystem();
			tweeter.follow("bob", "alex");
			tweeter.follow("vajid", "alex");
			tweeter.follow("vajid", "bob");
			tweeter.follow("alex", "bob");
			tweeter.follow("alex", "bob");
			assertThat(stats.getMostFollowedUser(), is("alex"));

			// No Difference of block
			stats.resetStatsAndSystem();
			tweeter.follow("bob", "alex");
			tweeter.follow("vajid", "alex");
			tweeter.block("alex", "bob");
			tweeter.block("bob", "alex");
			tweeter.follow("vajid", "bob");
			tweeter.follow("alex", "bob");
			tweeter.follow("alex", "bob");
			assertThat(stats.getMostFollowedUser(), is("alex"));

			// Capital and small letter check
			stats.resetStatsAndSystem();
			tweeter.follow("bob", "alex");
			tweeter.follow("vajid", "alex");
			tweeter.follow("vajid", "Alex");
			tweeter.follow("alex", "Alex");
			tweeter.follow("alex", "bob");
			assertThat(stats.getMostFollowedUser(), is("Alex"));

			// Null or no value checks
			stats.resetStatsAndSystem();
			tweeter.follow("", "alex");
			tweeter.follow("vajid", "alex");
			tweeter.follow("vajid", "bob");
			tweeter.follow("alex", "bob");
			tweeter.follow("alex", "bob");
			assertThat(stats.getMostFollowedUser(), is("bob"));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in test: testMostFollowedUser");
		}
	}

	@Test
	public void testMostPopularMessage() {
		try {
			stats.resetStatsAndSystem();
			tweeter.follow("bob", "alex");
			tweeter.follow("vajid", "alex");
			tweeter.follow("jay", "alex");
			tweeter.follow("jay1", "alex");

			tweeter.block("alex", "vajid");
			tweeter.block("alex", "jay");

			tweeter.follow("alex", "bob");
			tweeter.follow("vajid", "bob");

			tweeter.tweet("alex", "abcd");
			tweeter.tweet("bob", "efgh");
			assertThat(stats.getMostPopularMessage(), is("abcd"));

			// Murtaza Test Case
			stats.resetStatsAndSystem();
			tweeter.follow("Bob", "alex");
			tweeter.follow("Bob1", "alex");
			tweeter.follow("Bob2", "alex");

			tweeter.block("alex", "Bob");
			tweeter.block("alex", "Bob1");

			tweeter.follow("Bobby", "Bob");
			tweeter.follow("Bobby1", "bob");

			tweeter.tweet("alex", "Vajid");
			tweeter.tweet("bob", "Zurtaza");

			assertThat(stats.getMostPopularMessage(), is("Vajid"));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in test : testMostPopularMessage");
		}
	}

	@Test
	public void testMostProductiveUser() {
		try {
			stats.resetStatsAndSystem();
			// assertThat(stats.getMostProductiveUser(), null);
			tweeter.tweet("alex", "hey");
			assertThat(stats.getMostProductiveUser(), is("alex"));

			stats.resetStatsAndSystem();
			tweeter.tweet("alex", "hey");
			tweeter.tweet("bob", "hey");
			tweeter.tweet("bob", "hey");
			assertThat(stats.getMostProductiveUser(), is("bob"));

			stats.resetStatsAndSystem();
			tweeter.tweet("alex",
					"tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweetfirst tweet");
			tweeter.tweet("alex", "hey");
			tweeter.tweet("alex", "hey");
			tweeter.tweet("bob", "hey");
			tweeter.tweet("Bob", "hey");
			tweeter.tweet("Bob", "hey");
			tweeter.tweet("Bob", "hey");
			assertThat(stats.getMostProductiveUser(), is("Bob"));

		} catch (Exception e) {
			System.out.println("Error in test : testLengthOfLongestTweet");
		}
	}

	@Test
	public void testMostBlockedUser() {
		try {
			stats.resetStatsAndSystem();
			// assertThat(stats.getMostBlockedFollower(), null);

			stats.resetStatsAndSystem();
			tweeter.block("bob", "alex");
			tweeter.block("alex", "Bob");
			tweeter.block("vajid", "Bob");
			assertThat(stats.getMostBlockedFollower(), is("Bob"));

			stats.resetStatsAndSystem();
			tweeter.block("bob", "alex");
			tweeter.block("alex1", "alex");
			tweeter.block("vajid", "bob");
			assertThat(stats.getMostBlockedFollower(), is("alex"));

			stats.resetStatsAndSystem();
			tweeter.block("bob", "Bob");
			tweeter.block("bob", "alex");
			tweeter.block("vajid", "alex");
			assertThat(stats.getMostBlockedFollower(), is("alex"));

		} catch (Exception e) {
			System.out.println("Error in test : testLengthOfLongestTweet");
		}
	}

	// assertThat(stats.getMostProductiveUser(), is("bob"));
	// assertThat(stats.getMostFollowedUser(),is("bob"));
	// assertThat(stats.getMostBlockedFollower(),is("bob"));
	// assertThat(stats.getLengthOfLongestTweet(), is(12));
	// assertThat(stats.getMostPopularMessage(),is("first tweet"));
}