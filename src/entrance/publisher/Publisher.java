package entrance.publisher;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
		HttpEntity<Car> requestEntity = new HttpEntity<Car>(car, header);
		try {
			Map<String, String> uriVariables = new HashMap<String, String>();
			/*uriVariables.put("api_key", car.getApi_key());
			uriVariables.put("field_tag", car.getField1());
			uriVariables.put("field_id", "1");*/
			//url += "?api_key=" + car.getApi_key() + "&field_tag=" + car.getField1();
			//ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, uriVariables);
			restTemplate.postForLocation(url, requestEntity);
			//System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, ?> buildMap(Car car) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", String.valueOf(car.getField1()));
		map.put("api_key", String.valueOf(car.getApi_key()));
		
		return map;
	}
}
