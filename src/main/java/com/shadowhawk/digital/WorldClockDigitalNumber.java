package com.shadowhawk.digital;

public class WorldClockDigitalNumber {
	
	
	private int DIGIT;
	
	boolean seg1, seg2, seg3, seg4, seg5, seg6, seg7;
	/*
	 *    1111
	 *   2    3
	 *   2    3
	 *    4444
	 *   5    6
	 *   5    6
	 *    7777
	 * 
	 * */
	
	
	
	public void setDigit(int digit)
	{
		DIGIT = digit;
	}

	public void updateSegments()
	{
	 	switch(DIGIT)
	 	{
	 		case 0 :
	 			seg1 = true;
	 			seg2 = true;
	 			seg3 = true;
	 			seg4 = false;
	 			seg5 = true;
	 			seg6 = true;
	 			seg7 = true;
	 			break;
	 		case 1 :
	 			seg1 = false;
	 			seg2 = false;
	 			seg3 = true;
	 			seg4 = false;
	 			seg5 = false;
	 			seg6 = true;
	 			seg7 = false;
	 			break;
	 		case 2 :
	 			seg1 = true;
	 			seg2 = false;
	 			seg3 = true;
	 			seg4 = true;
	 			seg5 = true;
	 			seg6 = false;
	 			seg7 = true;
	 			break;
	 		case 3 :
	 			seg1 = true;
	 			seg2 = false;
	 			seg3 = true;
	 			seg4 = true;
	 			seg5 = false;
	 			seg6 = true;
	 			seg7 = true;
	 			break;
	 		case 4 :
	 			seg1 = false;
	 			seg2 = true;
	 			seg3 = true;
	 			seg4 = true;
	 			seg5 = false;
	 			seg6 = true;
	 			seg7 = false;
	 			break;
	 		case 5 :
	 			seg1 = true;
	 			seg2 = true;
	 			seg3 = false;
	 			seg4 = true;
	 			seg5 = false;
	 			seg6 = true;
	 			seg7 = true;
	 			break;
	 		case 6 :
	 			seg1 = true;
	 			seg2 = true;
	 			seg3 = false;
	 			seg4 = true;
	 			seg5 = true;
	 			seg6 = true;
	 			seg7 = true;
	 			break;
	 		case 7 :
	 			seg1 = true;
	 			seg2 = false;
	 			seg3 = true;
	 			seg4 = false;
	 			seg5 = false;
	 			seg6 = true;
	 			seg7 = false;
	 			break;
	 		case 8 :
	 			seg1 = true;
	 			seg2 = true;
	 			seg3 = true;
	 			seg4 = true;
	 			seg5 = true;
	 			seg6 = true;
	 			seg7 = true;
	 			break;
	 		case 9 :
	 			seg1 = true;
	 			seg2 = true;
	 			seg3 = true;
	 			seg4 = true;
	 			seg5 = false;
	 			seg6 = true;
	 			seg7 = true;
	 			break;
	 		default : //Optional
	 			seg1 = false;
	 			seg2 = false;
	 			seg3 = false;
	 			seg4 = false;
	 			seg5 = false;
	 			seg6 = false;
	 			seg7 = false;
	 	}
		
	}

}