package br.com.fiap.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fiap.service.Twitter4jService;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Twitter4jServiceImpl implements Twitter4jService {

	private  AccessToken loadAccessToken(){
		String token = "1021551963580850177-zaqYQ674gUNGtEvwMf4TylpbcphJeR";
		String tokenSecret = "WEqqNXJj6yp9UBkRbPVJrqvMutGhN6UR1jUdetBjD4vcV";
		return new AccessToken(token, tokenSecret);
	}

	public Twitter twitterFactory() {

		TwitterFactory factory = new TwitterFactory();
		AccessToken accessToken = loadAccessToken();
		Twitter twitter = factory.getSingleton();
		twitter.setOAuthConsumer("uAz4fNbqHSg4wXd8nZopDtMUL", "LyJKg92TVskYJUyRWp7p1kpc74W6tXkbVqMm5OMQbmFs2bLkXT");
		twitter.setOAuthAccessToken(accessToken);
		return twitter;
	}

	@Override
	public String buscarTweets(Twitter twitter) {
		String response = "Quantidade por dia de tweets da última semana: \ns";
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

			Map<String, Integer> quantidadeDatas = new HashMap<>();
			for (Status status : result.getTweets()) {
				status.getCreatedAt().setHours(00);
				status.getCreatedAt().setMinutes(00);
				status.getCreatedAt().setSeconds(00);

				//	            Para verificar se a contegem está correta decoemnte estas linhas
				//	            System.out.println(   "@"  + status.getUser().getScreenName() + " : " + status.getText() + " : " + status.getGeoLocation() + "date: " +
				//	            		status.getCreatedAt());
				//	            

				if (!quantidadeDatas.containsKey(status.getCreatedAt().toString())) {

					quantidadeDatas.put(status.getCreatedAt().toString(), 1);
				} else {
					int value = quantidadeDatas.get(status.getCreatedAt().toString()).intValue();
					value = value + 1;
					quantidadeDatas.put(status.getCreatedAt().toString(), value);

				}
			}

			for(Map.Entry<String, Integer> entry : quantidadeDatas.entrySet()) {
				response = response.concat("Data: " +entry.getKey() + " quantidade: " + entry.getValue() + "\n");
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String buscarRetweets(Twitter twitter) {
		String response = "Quantidade de retweets: \n";
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
						//			System.out.println(   "@"  + status.getRetweetedStatus().getUser().getScreenName() + " : " + status.getRetweetedStatus().getText() + " : " + status.getGeoLocation() + "date: " +
						//					status.getRetweetedStatus().getCreatedAt());


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
				response = response.concat("Data: " +entry.getKey() + " quantidade: " + entry.getValue() + "\n");
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String buscarTweetsNomeAutor(Twitter twitter) {
		
		String response = "Ordenar os tweets pelo nome do autor, e exibir o primeiro nome e o último nome.: \n";
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
				response = response.concat("Author: " + status.getUser().getName() + " Tweet:" + status.getText() + "\n");
			}

			response = response + "Primeiro nome: " + listaTweets.get(0).getUser().getName() + "\n";
			response = response + "Último nome: " + listaTweets.get(listaTweets.size()-1).getUser().getName() + "\n";

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String buscarTweetsDatas(Twitter twitter) {

		String response = "Ordenar os tweets por data, e exibir a data mais recente e a menos recente: \n";
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
			
			listaTweets.sort((t1,t2) -> t1.getCreatedAt().compareTo(t2.getCreatedAt()));
			
			for(Status status : listaTweets) {
				response = response + "Data: " + status.getCreatedAt()  + " Author: " + status.getUser().getName() + " Tweet:" + status.getText() + "\n";
			}

			response = response + "Data menos recente: " + listaTweets.get(0).getCreatedAt() + "\n";
			response = response + "Data mais recente: " + listaTweets.get(listaTweets.size()-1).getCreatedAt() + "\n";

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String buscarFavoritacoes(Twitter twitter) {
		
		String response = "Quantidade favoritacões: \n";
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
				query.setCount(9999);
				//System.out.println("query:" + query);
				QueryResult result = twitter.search(query);

				
					for (Status status : result.getTweets()) {
						status.getCreatedAt().setHours(00);
						status.getCreatedAt().setMinutes(00);
						status.getCreatedAt().setSeconds(00);
						
						if (status.isFavorited()) {
							if (!quantidadeDatas.containsKey(status.getCreatedAt().toString())) {

								quantidadeDatas.put(status.getCreatedAt().toString(), 1);
							} else {
								int value = quantidadeDatas.get(status.getCreatedAt().toString()).intValue();
								value = value + 1;
								quantidadeDatas.put(status.getCreatedAt().toString(), value);

							}

						} else {
							quantidadeDatas.put(dataBusca.toString(), 0);
						}
						
					}

			}

			for(Map.Entry<String, Integer> entry : quantidadeDatas.entrySet()) {
				response = response.concat("Data: " +entry.getKey() + " quantidade: " + entry.getValue() + "\n");
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

}
