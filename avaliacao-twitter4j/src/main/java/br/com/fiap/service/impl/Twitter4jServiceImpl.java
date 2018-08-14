package br.com.fiap.service.impl;

import java.text.SimpleDateFormat;
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

public class Twitter4jServiceImpl implements Twitter4jService {

	@Override
	public String buscarTweets(Twitter twitter) {
		String response = "Tweets: \n";
		try {

			LocalDate dataAtual = LocalDate.now();
			LocalDate dataBusca = dataAtual.minusDays(7l);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dataBusca.format(formatter);
			// System.out.println("dataBusca: " + dataBusca.toString());
			Query query = new Query("#java8");
			query.since(dataBusca.toString());
			query.setCount(9999);
			// System.out.println("query:" + query);
			QueryResult result = twitter.search(query);

			Map<String, Integer> quantidadeDatas = new HashMap<>();
			for (Status status : result.getTweets()) {
				status.getCreatedAt().setHours(00);
				status.getCreatedAt().setMinutes(00);
				status.getCreatedAt().setSeconds(00);

				// Para verificar se a contegem estÃ¡ correta decoemnte estas linhas
				// System.out.println( "@" + status.getUser().getScreenName() + " : " +
				// status.getText() + " : " + status.getGeoLocation() + "date: " +
				// status.getCreatedAt());
				//
				if (!status.isRetweet()) {
					SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
					if (!quantidadeDatas.containsKey(fmt.format(status.getCreatedAt()).toString())) {

						quantidadeDatas.put(fmt.format(status.getCreatedAt()).toString(), 1);
					} else {
						int value = quantidadeDatas.get(fmt.format(status.getCreatedAt()).toString()).intValue();
						value = value + 1;
						quantidadeDatas.put(fmt.format(status.getCreatedAt()).toString(), value);

					}
				}
			}

			for (Map.Entry<String, Integer> entry : quantidadeDatas.entrySet()) {
				response = response.concat("Data: " + entry.getKey() + " Quant: " + entry.getValue() + "\n");

			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String buscarRetweets(Twitter twitter) {
		String response = "Retweets: \n";
		try {
			LocalDate dataAtual = LocalDate.now();
			LocalDate dataBusca = dataAtual.minusDays(7l);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dataBusca.format(formatter);
			// System.out.println("dataBusca: " + dataBusca.toString());
			Query query = new Query("#java8");
			query.setSince(dataBusca.toString());
			query.setCount(9999);
			// System.out.println("query: " + query);
			QueryResult result = twitter.search(query);

			Map<String, Integer> quantidadeDatas = new HashMap<>();
			for (Status status : result.getTweets()) {
				if (status.getRetweetedStatus() != null) {

					if (status.getRetweetedStatus().getCreatedAt() != null) {

						status.getRetweetedStatus().getCreatedAt().setHours(00);
						status.getRetweetedStatus().getCreatedAt().setMinutes(00);
						status.getRetweetedStatus().getCreatedAt().setSeconds(00);

						// Para verificar se a contegem estÃ¡ correta descoemnte estas linhas
						// System.out.println( "@" +
						// status.getRetweetedStatus().getUser().getScreenName() + " : " +
						// status.getRetweetedStatus().getText() + " : " + status.getGeoLocation() +
						// "date: " +
						// status.getRetweetedStatus().getCreatedAt());

						SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
						if (!quantidadeDatas.containsKey(fmt.format(status.getRetweetedStatus().getCreatedAt()).toString())) {

							quantidadeDatas.put(fmt.format(status.getRetweetedStatus().getCreatedAt()).toString(), 1);
						} else {
							int value = quantidadeDatas.get(fmt.format(status.getRetweetedStatus().getCreatedAt()).toString()).intValue();
							value = value + 1;
							quantidadeDatas.put(fmt.format(status.getRetweetedStatus().getCreatedAt()).toString(),	value);

						}
					}
				}
			}

			for (Map.Entry<String, Integer> entry : quantidadeDatas.entrySet()) {
				response = response.concat("Data: " + entry.getKey() + " Quant: " + entry.getValue() + "\n");
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String buscarTweetsNomeAutor(Twitter twitter) {

		String response = "Ordenar os tweets pelo nome do autor, e exibir o primeiro nome e o Último nome.: \n";
		try {

			LocalDate dataAtual = LocalDate.now();
			LocalDate dataBusca = dataAtual.minusDays(7l);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dataBusca.format(formatter);
			// System.out.println("dataBusca: " + dataBusca.toString());
			Query query = new Query("#java8");
			query.since(dataBusca.toString());
			query.setCount(9999);
			// System.out.println("query:" + query);
			QueryResult result = twitter.search(query);

			List<Status> listaTweets = new ArrayList<>();
			List<String> listaResponseSend = new ArrayList<>();
			for (Status status : result.getTweets()) {
				if (!status.isRetweet()) {
					listaTweets.add(status);
				}

			}

			listaTweets.sort((t1, t2) -> t1.getUser().getName().compareTo(t2.getUser().getName()));

			response = response + "Primeiro nome: " + listaTweets.get(0).getUser().getName() + "\n";
			response = response + "Último nome: " + listaTweets.get(listaTweets.size() - 1).getUser().getName() + "\n";

			for (Status status : listaTweets) {
				response = response
						.concat("Author: " + status.getUser().getName() + " Tweet:" + status.getText() + "\n");
				listaResponseSend.add("Author: " + status.getUser().getName() + " Tweet:" + status.getText() + "\n");
			}

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
			// System.out.println("dataBusca: " + dataBusca.toString());
			Query query = new Query("#java8");
			query.since(dataBusca.toString());
			query.setCount(9999);
			// System.out.println("query:" + query);
			QueryResult result = twitter.search(query);

			List<Status> listaTweets = new ArrayList<>();
			List<String> listaResponseSend = new ArrayList<>();

			for (Status status : result.getTweets()) {
				if (!status.isRetweet()) {
					listaTweets.add(status);
				}

			}

			listaTweets.sort((t1, t2) -> t1.getCreatedAt().compareTo(t2.getCreatedAt()));

			response = response + "Data menos recente: " + listaTweets.get(0).getCreatedAt() + "\n";
			response = response + "Data mais recente: " + listaTweets.get(listaTweets.size() - 1).getCreatedAt() + "\n";

			for (Status status : listaTweets) {
				response = response + "Data: " + status.getCreatedAt() + " Author: " + status.getUser().getName()
						+ " Tweet:" + status.getText() + "\n";
				listaResponseSend.add("Data: " + status.getCreatedAt() + " Author: " + status.getUser().getName()
						+ " Tweet:" + status.getText() + "\n");
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String buscarFavoritacoes(Twitter twitter) {

		String response = "Favoritações: \n";
		try {
			Map<String, Integer> quantidadeDatas = new HashMap<>();
			LocalDate dataAtual = LocalDate.now();
			LocalDate dataBusca = dataAtual.minusDays(7l);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dataBusca.format(formatter);

			Query query = new Query("#java8");
			query.since(dataBusca.toString());
			query.setCount(9999);

			QueryResult result = twitter.search(query);
			for (Status status : result.getTweets()) {
				status.getCreatedAt().setHours(00);
				status.getCreatedAt().setMinutes(00);
				status.getCreatedAt().setSeconds(00);

				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
				int valueFavoritos = status.getFavoriteCount();
				int value = 0;
				if (valueFavoritos != 0) {
					if (!quantidadeDatas.containsKey(fmt.format(status.getCreatedAt()).toString())) {
						quantidadeDatas.put(fmt.format(status.getCreatedAt()).toString(), valueFavoritos);
					} else {
						value =  quantidadeDatas.get(fmt.format(status.getCreatedAt()).toString()).intValue();
						value = value + valueFavoritos;
						quantidadeDatas.put(fmt.format(status.getCreatedAt()).toString(), value);

					}
				}
			}

			for (Map.Entry<String, Integer> entry : quantidadeDatas.entrySet()) {
				response = response.concat("Data: " + entry.getKey() + " Quant: " + entry.getValue() + "\n");
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return response;
	}

}
