import virtualbreadboard.vbbmicro.target.VBBMicroUNO; 
import virtualbreadboard.vbbmicro.jni.ADACall;
import virtualbreadboard.vbbmicro.jni.ADACallback;

public class ProofOfLife extends ADACall  
{
	public static native void WaitforEvent();

}

  
public class ProofOfLifeVBB extends ADACallback  
{
	public static final int INPUT = 1;
	public static final int OUTPUT = 0;
	
	public static final int HIGH = 1;
	public static final int LOW = 0;
	
	public static final int PIN_UNLOCKED = 12;
	public static final int PIN_KEYR1 = 2;
	public static final int PIN_KEYR2 = 3;
	public static final int PIN_KEYR3 = 4;
	public static final int PIN_KEYR4 = 5;
	

	private static long markStartTime;

	//Returns the index of the last 
	public static int CaptureKeyEvents(int[] rotatingBuffer){
		
		int len = rotatingBuffer.length;
 
		int recordIndex = 0;
		int totalCount = 0;
	    
		VBBMicroUNO.pinMode(PIN_KEYR1, INPUT);
		VBBMicroUNO.pinMode(PIN_KEYR2, INPUT);
		VBBMicroUNO.pinMode(PIN_KEYR3, INPUT);
		VBBMicroUNO.pinMode(PIN_KEYR4, INPUT);
		VBBMicroUNO.pinMode(PIN_UNLOCKED, INPUT);
	   
	    
		while( VBBMicroUNO.digitalRead(PIN_UNLOCKED) == HIGH );
	      
		//Reset the buffer
		markStartTime = VBBMicroUNO.millis() ;
		for(recordIndex = 0; recordIndex < len; recordIndex++) rotatingBuffer[recordIndex++] = 0;
	    recordIndex = 0;

		while(true){
	            
			while( (VBBMicroUNO.digitalRead(PIN_UNLOCKED) == LOW) &&
				((VBBMicroUNO.digitalRead(PIN_KEYR1) == LOW) ||
				(VBBMicroUNO.digitalRead(PIN_KEYR1) == LOW) ||
				(VBBMicroUNO.digitalRead(PIN_KEYR2) == LOW) ||
				(VBBMicroUNO.digitalRead(PIN_KEYR3) == LOW) )) ;


			if( VBBMicroUNO.millis() - markStartTime > 10000 ){
				//Reset if this is the first keypress in the last 10 seconds - the timeout window
				//Clear the buffer
				for(recordIndex = 0; recordIndex < len; recordIndex++) rotatingBuffer[recordIndex++] = 0;
				markStartTime = VBBMicroUNO.millis() ;
				recordIndex = 0;
			}

			while( (VBBMicroUNO.digitalRead(PIN_UNLOCKED) == 0) &&
				(VBBMicroUNO.digitalRead(PIN_KEYR1) == HIGH) &&
				(VBBMicroUNO.digitalRead(PIN_KEYR1) == HIGH) &&
				(VBBMicroUNO.digitalRead(PIN_KEYR2) == HIGH) &&
				(VBBMicroUNO.digitalRead(PIN_KEYR3) == HIGH) ) ;
                
			totalCount++;
              
			//Truncated to 16 bit integer.
			rotatingBuffer[recordIndex] = (int)(VBBMicroUNO.millis() & 0xFFFFL );
    	     
			//Unlock event has occurred - return the buffer for proof of life analysis   
			//Final sample is the current time        
			if( VBBMicroUNO.digitalRead(PIN_UNLOCKED) == HIGH){
				return recordIndex;
			}

			recordIndex = recordIndex + 1;
    	     
			if( recordIndex == len ) recordIndex = 0;
  
		}
         
	}
}

 
public class VBBMicroApp extends VBBMicroUNO{ 
   
  
	// The setup() method runs once, when the sketch starts  
	public void setup(){   
	 
		System.out.println("Starting Proof of Life!");
	 
	}
 
	// the loop() method runs over and over again, 
	// as long as the Arduino has power 
	public void loop(){

		ProofOfLife.WaitforEvent();
 
	}
}
