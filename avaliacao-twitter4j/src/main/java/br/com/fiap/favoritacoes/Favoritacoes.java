package br.com.fiap.favoritacoes;

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

public class Favoritacoes {

	public static void main(String[] args) {

		TwitterFactory factory = new TwitterFactory();
		AccessToken accessToken = loadAccessToken();
		Twitter twitter = factory.getSingleton();
		twitter.setOAuthConsumer("uAz4fNbqHSg4wXd8nZopDtMUL", "LyJKg92TVskYJUyRWp7p1kpc74W6tXkbVqMm5OMQbmFs2bLkXT");
		twitter.setOAuthAccessToken(accessToken);

		try {

			Map<String, Integer> quantidadeDatas = new HashMap<>();
			for(int i = 7; i >= 0; i--) {
				LocalDate dataAtual = LocalDate.now();
				LocalDate dataBusca = dataAtual.minusDays(i);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				dataBusca.format(formatter);
				//System.out.println("dataBusca: " + dataBusca.toString());
				Query query = new Query("#java8");
				query.setSince(dataBusca.toString());
				query.setUntil(dataBusca.toString());
				query.setCount(9999);
				//System.out.println("query:" + query);
				QueryResult result = twitter.search(query);

				if (result.getTweets() != null && !result.getTweets().isEmpty()) {
					for (Status status : result.getTweets()) {
						status.getCreatedAt().setHours(00);
						status.getCreatedAt().setMinutes(00);
						status.getCreatedAt().setSeconds(00);

						quantidadeDatas.put(status.getCreatedAt().toString(), status.getFavoriteCount());
					}
				} else {
					quantidadeDatas.put(dataBusca.toString(), 0);
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
