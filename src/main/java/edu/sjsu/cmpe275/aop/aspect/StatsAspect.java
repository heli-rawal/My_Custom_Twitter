import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;

@Aspect
@Order(1)
public class StatsAspect {

	@Autowired TweetStatsImpl stats;

	@Before("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..)) && args(user, message)")
	public void tweetBeforeAdvice(JoinPoint joinPoint, String user, String message) {
		stats.setLengthOfLongestTweet(message);
	}

	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..)) && args(user, message)")
	public void tweetAfterAdvice(JoinPoint joinPoint, String user, String message) {
		stats.recordSuccessfulTweet(user,message);
	}

	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetService.follow(..)) && args(follower, followee)")
	public void followAfterAdvice(JoinPoint joinPoint, String follower, String followee) {
		stats.recordFollowActivity(follower,followee);
	}

	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetService.block(..)) && args(user, follower)")
	public void blockAfterAdvice(JoinPoint joinPoint, String user, String follower) {
		stats.RecordBlockActivity(user,follower);
	}
	
}
