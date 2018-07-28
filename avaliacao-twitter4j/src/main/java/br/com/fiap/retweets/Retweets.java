package br.com.fiap.retweets;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Retweets {
	public static void main(String[] args) {

		TwitterFactory factory = new TwitterFactory();
		AccessToken accessToken = loadAccessToken();
		Twitter twitter = factory.getSingleton();
		twitter.setOAuthConsumer("uAz4fNbqHSg4wXd8nZopDtMUL", "LyJKg92TVskYJUyRWp7p1kpc74W6tXkbVqMm5OMQbmFs2bLkXT");
		twitter.setOAuthAccessToken(accessToken);

		try {
						LocalDate dataAtual = LocalDate.now();
			LocalDate dataBusca = dataAtual.minusDays(7l);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dataBusca.format(formatter);
			//System.out.println("dataBusca: " + dataBusca.toString());
			Query query = new Query("#java8");
			query.setSince(dataBusca.toString());
			query.setCount(9999);
			//System.out.println("query: " + query);
			QueryResult result = twitter.search(query);

			Map<String, Integer> quantidadeDatas = new HashMap<>();
			for (Status status : result.getTweets()) {
				if (status.getRetweetedStatus() != null) {

					if (status.getRetweetedStatus().getCreatedAt() != null) {


						status.getRetweetedStatus().getCreatedAt().setHours(00);
						status.getRetweetedStatus().getCreatedAt().setMinutes(00);
						status.getRetweetedStatus().getCreatedAt().setSeconds(00);

						//Para verificar se a contegem está correta descoemnte estas linhas
//						System.out.println(   "@"  + status.getRetweetedStatus().getUser().getScreenName() + " : " + status.getRetweetedStatus().getText() + " : " + status.getGeoLocation() + "date: " +
//								status.getRetweetedStatus().getCreatedAt());


						if (!quantidadeDatas.containsKey(status.getRetweetedStatus().getCreatedAt().toString())) {

							quantidadeDatas.put(status.getRetweetedStatus().getCreatedAt().toString(), 1);
						} else {
							int value = quantidadeDatas.get(status.getRetweetedStatus().getCreatedAt().toString()).intValue();
							value = value + 1;
							quantidadeDatas.put(status.getRetweetedStatus().getCreatedAt().toString(), value);

						}
					}
				}
			}

			for(Map.Entry<String, Integer> entry : quantidadeDatas.entrySet()) {
				System.out.println("Data: " +entry.getKey() + " quantidade: " + entry.getValue());
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}


	}

	private static AccessToken loadAccessToken(){
		String token = "1021551963580850177-zaqYQ674gUNGtEvwMf4TylpbcphJeR";
		String tokenSecret = "WEqqNXJj6yp9UBkRbPVJrqvMutGhN6UR1jUdetBjD4vcV";
		return new AccessToken(token, tokenSecret);
	}
}
