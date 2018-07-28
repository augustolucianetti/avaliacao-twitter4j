package br.com.fiap.nomeautor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class NomeAutor {

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
			query.since(dataBusca.toString());
			query.setCount(9999);
			//System.out.println("query:" + query);
			QueryResult result = twitter.search(query);

			List<Status> listaTweets = new ArrayList<>();
			for (Status status : result.getTweets()) {

				listaTweets.add(status);

			}

			listaTweets.sort((t1,t2) -> t1.getUser().getName().compareTo(t2.getUser().getName()));
			
			for(Status status : listaTweets) {
				System.out.println("Author: " + status.getUser().getName() + " Tweet:" + status.getText());
			}

			System.out.println("Primeiro nome: " + listaTweets.get(0).getUser().getName());
			System.out.println("Último nome: " + listaTweets.get(listaTweets.size()-1).getUser().getName());

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
