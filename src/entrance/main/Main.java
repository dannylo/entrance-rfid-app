package entrance.main;


import java.io.IOException;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import api.reader.nesslab.commands.CloseConnection;
import api.reader.nesslab.commands.DisableBuzzer;
import api.reader.nesslab.commands.EnableContinueMode;
import api.reader.nesslab.commands.ReaderTags;
import api.reader.nesslab.commands.ReaderTagsReset;
import api.reader.nesslab.commands.RequestStatusScanTime;
import api.reader.nesslab.commands.SetPowerControl;
import api.reader.nesslab.commands.SetScanTime;
import api.reader.nesslab.exceptions.SessionFullException;
import api.reader.nesslab.facade.ApiReaderNesslab;
import api.reader.nesslab.interfaces.ApiReaderFacade;
import api.reader.nesslab.utils.OperationUtil;
import api.reader.nesslab.utils.TagAntenna;
import entrance.entity.car.Car;
import entrance.publisher.Publisher;

public class Main {
	
	private static final String URL_THING_SPEAK = "https://api.thingspeak.com/update";
	private static final String API_CHANNEL = "NCPY2QYBT8WG3527";

	//private static final String URL_THING_SPEAK = "https://api.thingspeak.com/apps/thinghttp/send_request";
	//private static final String API_CHANNEL = "AGI0XV5WZMQNR9JV";
	
	public static void main(String[] args) {
		try{
			ApiReaderFacade api = new ApiReaderNesslab("192.168.1.231");
			
			api.executeAction(new SetScanTime(0L));
			api.executeAction(new RequestStatusScanTime());
			System.out.println(api.getTranslatedResponse());
			api.executeAction(new DisableBuzzer());		
			api.executeAction(new SetPowerControl("250"));
			api.executeAction(new EnableContinueMode());
			
			api.clearTemporaryMemory(120);//Clean memory of 2 in 2 minutes.
			
			
			api.executeAction(new ReaderTags());
		 	Publisher publisher = new Publisher(URL_THING_SPEAK);
			
			while (api.hasResponse()) {
				try {
					api.captureTagsObject();
					if(api.hasNewTag()){
						//Envia a tag para o ThingSpeak
						String retornoApi = api.getTagUniqueJsonRepresentation();
						//System.out.println(retornoApi);
						Gson gson = new Gson();
						JSONObject jsonTag = new JSONObject(retornoApi);
						
						Car car = new Car();
						car.setField1(jsonTag.getString("tag"));
						car.setApi_key(API_CHANNEL);
						String retornoThingS = gson.toJson(car);
						System.out.println(retornoThingS);
						publisher.publish((car));
						
					}					
				} catch (SessionFullException e) {
					api.executeAction(new ReaderTagsReset());
				}  catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			api.executeAction(new CloseConnection());
	} catch (UnknownHostException e) {
		System.err.println("Host not found: " + OperationUtil.getIpReaderNesslab());
		System.exit(1);
	} catch (IOException e) {
		System.err.println("Don't possible the conection: "+ OperationUtil.getIpReaderNesslab());
		System.exit(1);
	}
		
	}
}
