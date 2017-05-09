package entrance.main;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.UnknownHostException;

import api.reader.nesslab.commands.CloseConnection;
import api.reader.nesslab.commands.DisableBuzzer;
import api.reader.nesslab.commands.EnableContinueMode;
import api.reader.nesslab.commands.ReaderTags;
import api.reader.nesslab.commands.ReaderTagsReset;
import api.reader.nesslab.commands.SetPowerControl;
import api.reader.nesslab.exceptions.SessionFullException;
import api.reader.nesslab.facade.ApiReaderNesslab;
import api.reader.nesslab.interfaces.ApiReaderFacade;
import api.reader.nesslab.utils.OperationUtil;
import entrance.interfaces.RfidReaderSimulate;
import entrance.publisher.Publisher;

public class Main {

	private static RfidReaderSimulate rfid = mock(RfidReaderSimulate.class);
	
	public static void main(String[] args) {
		when(rfid.getJsonRepresentation()).thenReturn("{\"antenna\": \"4\", \"tag\": \"300000000000000000000000053904\"}");
//		try{
//			ApiReaderFacade api = new ApiReaderNesslab("192.168.1.231");
//			
//	//		api.executeAction(new SetScanTime(0L));
//	//		api.executeAction(new RequestStatusScanTime());
//	//		System.out.println(api.getTranslatedResponse());
//			api.executeAction(new DisableBuzzer());		
//			api.executeAction(new SetPowerControl("250"));
//			api.executeAction(new EnableContinueMode());
//			
//			api.clearTemporaryMemory(120);//Clean memory of 2 in 2 minutes.
//			
//			
//			api.executeAction(new ReaderTags());
		 	Publisher publisher = new Publisher("http://smartparkingiot.heroku.br/entrance/car");
		 	publisher.publish(rfid.getJsonRepresentation());
			System.out.println(rfid.getJsonRepresentation());
			
//			while (api.hasResponse()) {
//				/* tags is printed in pattern: Antenna : 9 Tag: 00000002*/
//				try {
//					api.captureTagsObject();
//					if(api.hasNewTag()){
//						System.out.println(api.getTagUniqueJsonRepresentation());
//					}
//					
//				} catch (SessionFullException e) {
//					api.executeAction(new ReaderTagsReset());
//				}
//			}
//			
//			api.executeAction(new CloseConnection());
//	} catch (UnknownHostException e) {
//		System.err.println("Host not found: " + OperationUtil.getIpReaderNesslab());
//		System.exit(1);
//	} catch (IOException e) {
//		System.err.println("Don't possible the conection: "+ OperationUtil.getIpReaderNesslab());
//		System.exit(1);
//	}
//		
	}
}
