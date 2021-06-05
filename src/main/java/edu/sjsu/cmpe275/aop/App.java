
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

        try {
            //tweeter.tweet("arshiya", "0 tweet");
           // tweeter.tweet("alex", "0 first alex tweet");


            //tweeter.follow("saga", "alex");
          //  tweeter.block("alex", "saga");
            //tweeter.tweet("alex", "second  alex  tweet,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
            //tweeter.tweet("alex", "2 alex third tweet");

            //tweeter.follow("bob", "arshiya");
            //tweeter.follow("priya", "arshiya");
           /* tweeter.tweet("arshiya", "2 arshiya second tweet");
            tweeter.follow("ro", "alex");

           tweeter.tweet("alex", "2 alex third tweet");

            tweeter.follow("shruthi", "alex");
            tweeter.follow("shruthi", "arshiya");
            tweeter.follow("arbaaz", "arshiya");
            tweeter.tweet("arshiya", "4 arshiya first tweet");



            tweeter.tweet("priya", "3 priya first tweet");

            tweeter.tweet("arshiya", "4 arshiya last tweetbbbbbbbbb");

            tweeter.block("saga", "apriya");
            tweeter.block("shruthi", "apriya");


            tweeter.block("arshiya", "bob");

*/
            tweeter.follow("bob", "apriya");
            tweeter.follow("", "apriya");
            tweeter.follow("shruthi", "");


            //tweeter.follow("annie", "arshiya");
            //tweeter.tweet("arshiya", "arshiya last ");

            tweeter.block("apriya", "shruthi");
            //tweeter.block("apriya", "saga");
            tweeter.tweet("apriya", "apriya last ");

            tweeter.tweet("ff", "arshiyauuuulasttweetbbbbbbbbbhkhkhkhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhknnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkh");
            tweeter.follow("arshiya", "arshiya");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("Most unpopular follower " + stats.getMostBlockedFollower());
        System.out.println("Most popular message " + stats.getMostPopularMessage());

        ctx.close();
    }
}
