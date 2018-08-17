package br.com.fiap.main;

import br.com.fiap.service.TwitterFactoryAcess;
import br.com.fiap.service.impl.Twitter4jServiceImpl;
import twitter4j.Twitter;

public class Main {

	public static void main(String[] args) {

		Twitter4jServiceImpl service = new Twitter4jServiceImpl();
		String retorno = "";
		TwitterFactoryAcess access = new TwitterFactoryAcess();
		Twitter twitter = access.twitterFactory();
		retorno = service.buscarTweets(twitter);
		
		retorno = retorno + "--------------------------------- \n";
		
		String retweets = service.buscarRetweets(twitter);
		retorno = retorno + retweets;
		
		String favoritacoes = service.buscarFavoritacoes(twitter);
		retorno = retorno + "--------------------------------- \n";
		retorno = retorno + favoritacoes;
		
		retorno = retorno + "--------------------------------- \n";
		
		String nomeAutor = service.buscarTweetsNomeAutor(twitter);
		retorno = retorno +  nomeAutor;
		
		retorno = retorno + "--------------------------------- \n";
		
		String porData = service.buscarTweetsDatas(twitter);
		retorno = retorno + porData;
		System.out.println(retorno);

	}

	
}
