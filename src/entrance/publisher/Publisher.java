package entrance.publisher;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class Publisher {
	
	private RestTemplate restTemplate;
	private String url;
	
	public Publisher(String url) {
		this.url = url;
		this.restTemplate = new RestTemplate();
	}
	
	public void publish(String json){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		try {
			restTemplate.postForLocation(new URI(url), entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
