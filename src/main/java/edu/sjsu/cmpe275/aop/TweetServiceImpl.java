
import java.io.IOException;
import java.util.Random;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is a dummy implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
     */

	@Override
    public void tweet(String user, String message) throws IllegalArgumentException, IOException {
		if (message.length() > 140)
			throw new IllegalArgumentException("Message length more than 140");

    	System.out.printf("User %s tweeted message: %s\n", user, message);
		//throw new IOException("Network Error");
    }

	@Override
    public void follow(String follower, String followee) throws IllegalArgumentException,IOException {
		//if(follower.equals(followee)|| follower.length()==0 || followee.length()==0){
		//	throw new IllegalArgumentException("follow failed");
		//}

		Random rand = new Random();
		int i = rand.nextInt(5) + 1;
		if (i == 1) {
			throw new IOException(follower + " following " + followee + " failed!");
		}
		else
       	System.out.printf("User %s followed user %s \n", follower, followee);
    }

	@Override
	public void block(String user, String follower) throws IllegalArgumentException,IOException {
		//if(follower.equals(user)|| follower.length()==0 || user.length()==0){
		//	throw new IllegalArgumentException("block failed!!\n");
		//}
		Random rand = new Random();
		int i = rand.nextInt(5) + 1;
		if (i == 1) {
			throw new IOException(user + " blocking " + follower + " failed!");
		}
		else
			System.out.printf("User %s blocked user %s \n", user, follower);
	}

}
