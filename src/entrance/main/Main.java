package entrance.main;

import java.io.IOException;
import java.net.UnknownHostException;
import org.json.JSONException;
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
import entrance.entity.car.Car;
import entrance.publisher.Publisher;

public class Main {
	
	private static final String URL_THING_SPEAK = "https://api.thingspeak.com/update";
	
	public static void main(String[] args) {
		try{
			ApiReaderFacade api = new ApiReaderNesslab("192.168.1.231");
			
			api.defaultConfiguration();
			
			api.clearTemporaryMemory(7200);//Clean memory of 2 in 2 hours.
			
			
			api.executeAction(new ReaderTags());
		 	Publisher publisher = new Publisher(URL_THING_SPEAK);
			
			while (api.hasResponse()) {
				try {
					api.captureTagsObject();
					if(api.hasNewTag()){
						//Envia a tag para o ThingSpeak
						String retornoApi = api.getTagUniqueJsonRepresentation();
						Car car = new Car(retornoApi);
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
