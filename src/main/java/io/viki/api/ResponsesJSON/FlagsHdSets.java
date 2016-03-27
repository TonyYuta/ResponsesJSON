package io.viki.api.ResponsesJSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

/**
 * Finding sets Flags/Hd
 *
 */
public class FlagsHdSets {
	
    public static void main(String[] args) throws IOException {
    	BasicConfigurator.configure();
    	String json = "";			// variable that will contain page content
    	int i = 1;					// page number
    	List<Boolean> flagsHdSet; 	// list of flags:hd values
    	int flagsHdTrue = 0;		// number of objects flags:hd set to true
    	int flagsHdFalse = 0; 		// number of objects flags:hd set to false
       	boolean isNextPage;			// "more" field
    	URL url;
		url = new URL("http://api.viki.io/v4/videos.json?app=100250a&per_page=10&page=");
		
		// cycle operates while 'more' element in JSON has value 'true' 
		do {	
			json = "";
			url = new URL("http://api.viki.io/v4/videos.json?app=100250a&per_page=10&page=" + i);
			
			// reading content of the current page and saving into variable 'json'
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				 json += strTemp;
			}
	
	    	ReadContext ctx = JsonPath.parse(json);
	
	    	isNextPage = ctx.read("$['more']");		// reading value of 'more' element
	    	flagsHdSet = ctx.read("$..flags.hd");	// flags:hd values	

	    	// counting number of true and false values in the list of 'flags:hd' values
	    	for (Boolean s: flagsHdSet) {
	    		if (s == true) {
	    			flagsHdTrue +=1;
	    		}
	    		if (s == false) {
	    			flagsHdFalse +=1;
	    		}
	    	}
	    	
	    	i++;	// incrementing page number
	    	
	    } while (isNextPage);
    	
		// printing out the results
    	System.out.println("flags:hd set to  true: " + flagsHdTrue);
    	System.out.println("flags:hd set to false: " + flagsHdFalse);
		
    }
}
