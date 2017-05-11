package entrance.entity.car;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Car {
	
	@JsonProperty("field1")
	private String field1;
	
	@JsonProperty("api_key")
	private String apiKey = "NCPY2QYBT8WG3527";
	
	public Car(String returnApi) throws JSONException{
		JSONObject jsonTag = new JSONObject(returnApi);
		setField1(jsonTag.getString("tag"));
	}
	
	public String getField1() {
		return field1;
	}
	
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	public String getApiKey(){
		return apiKey;
	}
}
