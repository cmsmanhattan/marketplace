package com.cbsinc.cms.services.openia;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.cbsinc.cms.client.rest.utils.RestTemplateConfig;
import com.cbsinc.cms.services.openia.chatbot.dto.CharbotMessageRequest;
import com.cbsinc.cms.services.openia.chatbot.dto.ChatbotMessageResponse;
import com.cbsinc.cms.services.openia.chatbot.dto.Choice;
import com.cbsinc.cms.services.openia.chatbot.dto.Message;
import com.cbsinc.cms.services.openia.search.dto.RequestAISearch;
import com.cbsinc.cms.services.openia.search.dto.ResponseAISearch;

public class OpenIAIClient {

	private static OpenIAIClient openIASearch = new OpenIAIClient();

	private ResourceBundle resourcesCmsSettings = null;
	private String openipKey;
	private String openipHost;
	private String openipPort;
	private String openipProtocol;

	// help customer find items and buy item , sales man
	private String openipSalesmanModel = "gpt-3.5-turbo";
	// chat to site technical question and navigation
	private String openipTechSupportModel = "gpt-3.5-turbo";
	// manager assistant
	private String openipManagerModel = "gpt-3.5-turbo";
	// chat to product suppliers and shipping and delivery questions and accept
	// pricing
	private String openipMarketingmanModel = "gpt-3.5-turbo";

	private OpenIAIClient() {
		if (resourcesCmsSettings == null)
			resourcesCmsSettings = PropertyResourceBundle.getBundle("appconfig");
		openipKey = resourcesCmsSettings.getString("openip_key").trim();
		openipHost = resourcesCmsSettings.getString("openip_host").trim();
		openipPort = resourcesCmsSettings.getString("openip_port").trim();
		openipProtocol = resourcesCmsSettings.getString("openip_protocol").trim();

		openipSalesmanModel = resourcesCmsSettings.getString("openip_salesman_model").trim();
		openipTechSupportModel = resourcesCmsSettings.getString("openip_tech_support_model").trim();
		openipManagerModel = resourcesCmsSettings.getString("openip_manager_model").trim();
		openipMarketingmanModel = resourcesCmsSettings.getString("openip_marketingman_model").trim();
	}

	public static OpenIAIClient getInstanse() {
		return openIASearch;
	}

	public ResponseAISearch OpenIASearch(String searchText) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		// String endPoint = openip_protocol + "://" + openip_host + ":" + openip_port +
		// "/v1/embeddings";
		String endPoint = openipProtocol + "://" + openipHost + "/v1/embeddings";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + openipKey);

		RequestAISearch requestAISearch = new RequestAISearch();
		requestAISearch.setInput(searchText);
		HttpEntity<RequestAISearch> request = new HttpEntity<>(requestAISearch, headers);
		ResponseAISearch responseRenameUsers = restTemplate.postForObject(endPoint, request, ResponseAISearch.class);
		return responseRenameUsers;
	}

	public ChatbotMessageResponse OpenIAChatBot(String question, String previousQuestion, String iaModel) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		// String endPoint = openip_protocol + "://" + openip_host + ":" + openip_port +
		// "/v1/embeddings";
		String endPoint = openipProtocol + "://" + openipHost + "/v1/chat/completions";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + openipKey);

		CharbotMessageRequest charbotMessageRequest = new CharbotMessageRequest();

		Message[] messages = new Message[] { new Message("system", question), new Message("user", previousQuestion) };
		charbotMessageRequest.setMessages(messages);
		charbotMessageRequest.setModel(iaModel);

		HttpEntity<CharbotMessageRequest> request = new HttpEntity<>(charbotMessageRequest, headers);
		ChatbotMessageResponse responseRenameUsers = restTemplate.postForObject(endPoint, request,
				ChatbotMessageResponse.class);
		return responseRenameUsers;
	}

	public ChatbotMessageResponse OpenIAChatBotManager(String question, String previousQuestion) {
		return OpenIAChatBot(question, previousQuestion, openipManagerModel);
	}

	public ChatbotMessageResponse OpenIAChatBotSalesMan(String question, String previousQuestion) {
		return OpenIAChatBot(question, previousQuestion, openipSalesmanModel);
	}

	public ChatbotMessageResponse OpenIAChatBotMarketingMan(String question, String previousQuestion) {
		return OpenIAChatBot(question, previousQuestion, openipMarketingmanModel);
	}

	public ChatbotMessageResponse OpenIAChatBotTechSupport(String question, String previousQuestion) {
		return OpenIAChatBot(question, previousQuestion, openipTechSupportModel);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		/*
		 * ResponseAISearch responseAISearch =
		 * getInstanse().OpenIASearch(" who are you ? ") ; String test =
		 * responseAISearch.getData()[0].getEmbedding() ;
		 *
		 * byte[] decodedBytes = Base64.getDecoder().decode(test); //test = new
		 * String(decodedBytes, "UTF-8"); test = new String(decodedBytes);
		 * System.out.println("text: " + test ) ;
		 */

		ChatbotMessageResponse chatbotMessageResponse = getInstanse().OpenIAChatBot(" I like meat ",
				"I'm looking for a book on cooking traditional Italian pasta dishes.",
				getInstanse().openipManagerModel);
		Choice[] choices = chatbotMessageResponse.getChoices();
		for (Choice choice : choices) {
			Message message = choice.getMessage();
			message.getContent();
			System.out.println("Who: " + message.getRole() + " text: " + message.getContent());
		}

	}
}
