package com.example.taxi;

public class iaxCallTest {

	   public iaxCallTest() {
	   }
	 
	   /**
	    * @param args the command line arguments
	    */
	   public static void main(String[] args) {
	       // TODO code application logic here
	       iaxConnection ic = new iaxConnection();
	       ic.connect();
	       try {
	           Thread.sleep(10000);
	         } catch (InterruptedException ie) {
	           ie.printStackTrace();
	         }
	       
	       ic.call("999");
	   }

}
