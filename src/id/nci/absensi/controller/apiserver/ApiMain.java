package id.nci.absensi.controller.apiserver;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTextArea;

import org.json.JSONArray;
import org.json.JSONObject;


public class ApiMain {
	
	private static HttpURLConnection connection;
	static ArrayList<String>DataTagsList;
	
	public static void GET_tag(final JTextArea taView) throws IOException {
		BufferedReader reader;
		String line;
		StringBuffer response = new StringBuffer();
		
		try {
			URL url = new URL(ApiUrl.Get_Tags);
			connection = (HttpURLConnection) url.openConnection();
			
			//Request setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status_resp = connection.getResponseCode();
			if(status_resp > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();	
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();	
			}
			
			// print result			
			JSONObject dataObj = new JSONObject(response.toString());
			
			String status = dataObj.getString("status");
			String message = dataObj.getString("message");
			
			DataTagsList = new ArrayList<String>();
			
			if(status.equals("200")) {
				JSONArray jsonArray = dataObj.getJSONArray("records");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject data = jsonArray.getJSONObject(i);

                    String tag_id = data.getString("tag_id");
                    DataTagsList.add(tag_id);
                    
                }
                
                System.out.println("==== Data GET ====");
    			System.out.println("Status : "+status.toString());
    			System.out.println("Message : "+message.toString());
    			System.out.println("TagId : "+DataTagsList.toString()+"\n\n");
    			
    			taView.append("==== Data GET ===="+"\n");
    			taView.append("Status : "+status.toString()+"\n");
    			taView.append("Message : "+message.toString()+"\n");
    			taView.append("TagId : "+DataTagsList.toString()+"\n\n");
    			
			}else if(status.equals("204")) {
				
				System.out.println("==== Data GET ====");
    			System.out.println("Status : "+status.toString());
    			System.out.println("Message : "+message.toString()+"\n\n");
    			
				taView.append("==== Data GET ===="+"\n");
    			taView.append("Status : "+status.toString()+"\n");
    			taView.append("Message : "+message.toString()+"\n\n");
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
	public static boolean POST_tags_baru(final String tag_id,final String gate,final String timeStamp) throws IOException {
//		public static void POST_tags_baru(final TextArea taView,final String tag_id,final String gate,final String timeStamp) throws IOException {	
		String POST_PARAMS = "tag="+tag_id+"&date="+timeStamp+"&id_gate="+gate;
		System.out.println(tag_id);
		URL obj = new URL(ApiUrl.Post_Tags_baru);
		connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("POST");

		// For POST only - START
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = connection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result	
//			System.out.println("response : "+response.toString());
			JSONObject dataObj = new JSONObject(response.toString());
			
			
			String status = dataObj.getString("status");
			String message = dataObj.getString("message");
			
			System.out.println("==== Data POST ====");
			System.out.println("Status : "+status.toString());
			System.out.println("Message : "+message.toString()+"\n\n");
//			taView.append("Tag terdaftar : "+tag_id+"\n");
//			taView.append("Tag "+tag_id+" tercatat pada  "+timeStamp+ " di  Antena "+gate+"\n");
			return true;
		} else {
			System.out.println("POST request not worked : " + responseCode);
//			taView.append("Tag "+tag_id+" gagal dicatat ke sistem. sistem error "+responseCode+"\n");
			return false;
		}
		
	}

}
