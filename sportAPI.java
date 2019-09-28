package sportAPI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class sportAPI {
  public static void main(String[] args) throws IOException {
    // Set url parameter
	  int count =0;
	  BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("/Users/hechenyan/Desktop/SportAPI.json")));
	  String charset = "UTF-8";
	  for(int teamId=7;;teamId++){
		  if(count<1000){   
		 String Id=Integer.toString(teamId);
		 String url=String.format("http://api.isportsapi.com/sport/football/player?api_key=cnUcAPF9qcQ1AgI8&teamId=%s",Id);
		 String jsonResult = get(url, charset);
		 bwr.write(jsonResult);
		 System.out.println(jsonResult);
		 count++;
		  }else break;
		 }
	  bwr.close();
	  }

  /**
   * @param url
   * @param charset
   * @return return json string
 * @throws IOException 
   */
  @SuppressWarnings("null")
public static String get(String url, String charset) throws IOException {
    BufferedReader reader = null;
    String result = null;
    StringBuffer sbf = new StringBuffer();
    String strRead = null;
    String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
   
      URL newUrl = new URL(url);
      HttpURLConnection connection = (HttpURLConnection)newUrl.openConnection();
      connection.setRequestMethod("GET");
      connection.setReadTimeout(30000);
      connection.setConnectTimeout(30000);
      connection.setRequestProperty("User-agent", userAgent);
      connection.connect();
      InputStream is = connection.getInputStream();
      reader = new BufferedReader(new InputStreamReader(is, charset));
      while ((strRead = reader.readLine()) != null) {
    	  
        sbf.append(strRead);
        sbf.append("\r\n");
      }
      reader.close();
    result = sbf.toString();
 return result;
  }
}

