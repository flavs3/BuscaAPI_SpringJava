package com.spring.exercicio10.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController 
public class MoedaController {
	
	
	@GetMapping("/compra")
	public String obterCotacaoCompraDolarReal() {
		return obterCotacaoPorTipo("bid") + " " + obterCotacaoPorTipo("name");
	}
	
	@GetMapping("/venda")
	public String obterCotacaoVendaDolarReal() {
		return obterCotacaoPorTipo("ask");
	}
	
	@GetMapping("/min")
	public String obterCotacaoMinimoDolarReal() {
		return obterCotacaoPorTipo("low");
		
	}
	
	
	@GetMapping("/max")
	public String obterCotacaoMaximoDolarReal() {
		return obterCotacaoPorTipo("high");
		
	}

	@GetMapping("/all")
	public String obterCotacaoTodasDolarReal() {
		return obterCotacaoPorTipo("all");
		
	}
	
	
	private String obterCotacaoPorTipo (String tipo) {
	//Requisição para obtenção do resultado 
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://economia.awesomeapi.com.br/json/last/USD-BRL";
	try {
		//Acessa e retorna o json como objeto string
		String resposta = restTemplate.getForObject(url, String.class);
		
		try {
			if (tipo.equals("all")) {
				return resposta;
			} else {
				
				//Cria objeto para mapear o JSON
				ObjectMapper objectMapper = new ObjectMapper();
				//Lê o JSON e obtem o nó da raiz
				JsonNode rootNode = objectMapper.readTree(resposta);
				JsonNode campoNode = rootNode.get("USDBRL").get(tipo); 
				
				return campoNode.asText();
			}
			
		} catch (Exception e) {  
			e.printStackTrace();
			return "Erro ao obter cotação: " + e.getMessage();
		}
		
		
	} catch (Exception e) { 
		e.printStackTrace();
		return "Erro ao obter cotação: " + e.getMessage();
		
	}
 
	}
}
