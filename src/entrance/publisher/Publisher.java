package entrance.publisher;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import entrance.entity.car.Car;

public class Publisher {
	
	private RestTemplate restTemplate;
	private String url;
	private ObjectMapper objectMapper;
	
	public Publisher(String url) {
		this.url = url;
		this.restTemplate = new RestTemplate();
	}
	
	private ObjectMapper getObjectMapperInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }
	
	public void publish(Car car) throws JsonProcessingException{
		HttpHeaders header = new HttpHeaders();
		
		String json = getObjectMapperInstance().writeValueAsString(car);
		
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, header);
		try {
			restTemplate.postForLocation(url, requestEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
