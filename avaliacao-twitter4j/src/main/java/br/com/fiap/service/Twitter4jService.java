package br.com.fiap.service;

import twitter4j.Twitter;

public interface Twitter4jService {
	
	public String buscarTweets(Twitter twitter);
	
	public String buscarRetweets(Twitter twitter);
	
	public String buscarFavoritacoes(Twitter twitter);
	
	public String buscarTweetsNomeAutor(Twitter twitter);
	
	public String buscarTweetsDatas(Twitter twitter);
}
