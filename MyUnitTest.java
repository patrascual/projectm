/**
 * This tests the java.net.URL APIs.
 * Methods that are implemented in the API and who's functionality is tested: 
 * getFile - Return the file name of URL, or an empty string if one does not exist
 * openConnection - Returns a URLConnection instance that represents a connection to the remote object referred to by the URL
 * Class MalformedURLException - to indicate that a malformed URL has occurred
 * 
 * 
 */
package testNG;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.*;


public class MyUnitTest {
	
	//Provide a number of URLs that can be tested for connectivity purposes
	@DataProvider (name = "files")
	public static String[] list1() {
		return new String[] {"https://docs.oracle.com/javase/7/docs/api/java/net/URL.html"};
	}
	
	@DataProvider (name = "URLs")
	public static String[] list2() {
		return new String[] {"https://www.oracle.com", "http://abc.com"};
				
	}
	
    @Test(dataProvider = "files", enabled = true)
    public static void testgetfile(String webaddress) throws Exception {
        
    	URL url = new URL(webaddress);
    	//result should have only the string "URL", as a result of getBaseName on the getFiel method
        String result = FilenameUtils.getBaseName(url.getFile());
            	
        // check to see if the result matches the expected value
        
        assertEquals(result, "URL");
    }
    
    //Input for testConnection is provided by the dataprovider "URLs"
    //This method tests if a wedaddress is reachable (HTTP 200 OK)
    @Test(priority= 1, dataProvider = "URLs")
    public void testConnection(String webaddress) throws Exception{
    	
    	URL url = new URL(webaddress);
    	
    	try {
    	        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
    	        urlConn.connect();

    	        assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
    	    } catch (IOException e) {
    	        System.err.println("Error accessing the site");
    	        e.printStackTrace();
    	        throw e;
    	    }

    }
    // Tested exception for a malformed URL: wrong protocol
    @Test(expectedExceptions = MalformedURLException.class, description="Test for MalformedURLException - unknown protocol ")
    public void testmalformed() throws Exception{
    
    	    	URL url = new URL("httq://www.google.com");
    			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
    	        urlConn.connect();
    			
    		
    }

}
