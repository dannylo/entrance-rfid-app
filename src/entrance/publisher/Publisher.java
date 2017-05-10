package entrance.publisher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


import entrance.entity.car.Car;

public class Publisher {
	
	private RestTemplate restTemplate;
	private String url;
	
	public Publisher(String url) {
		this.url = url;
		this.restTemplate = new RestTemplate();
	}
	
	public void publish(Car car){
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<Map<String, String>>(buildMap(car), header);
		try {
			restTemplate.postForLocation(url, requestEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> buildMap(Car car) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", String.valueOf(car.getField1()));
		map.put("api_key", String.valueOf(car.getApiKey()));
		
		return map;
	}
}
