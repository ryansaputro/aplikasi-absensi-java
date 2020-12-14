package id.nci.absensi.controller.libraries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.nci.absensi.model.MJsonResult;
import id.nci.absensi.controller.libraries.RFIDReader;

public class RFIDDetect {
	private RFIDReader reader1=null;
	private Map<String,String> mapReaderOld1=new HashMap<String,String>();
	private Map<String,String> mapReader1=new HashMap<String,String>();
	private Map<String,String> mapReader1Allow=new HashMap<String,String>();
	private RFIDReader reader2=null;
	private Map<String,String> mapReaderOld2=new HashMap<String,String>();
	private Map<String,String> mapReader2=new HashMap<String,String>();
	private Map<String,String> mapReader2Allow=new HashMap<String,String>();
	private MJsonResult jsonResult=null;
	private Action action;
	private Thread run=new Thread(new Runnable(){
		@SuppressWarnings({"rawtypes","unchecked"})
		@Override
		public void run(){
			// TODO Auto-generated method stub
			while(reader1.isActive()==true){
				List<HashMap<String,String>> list1=(ArrayList<HashMap<String,String>>)reader1.read().getData();
				List<HashMap<String,String>> list2=(ArrayList<HashMap<String,String>>)reader2.read().getData();
				mapReader1=new HashMap<String,String>();
				mapReader2=new HashMap<String,String>();
				System.out.println("");
				System.out.print("READER 1 -> ");
				String id="";
				for(int i=0,iLen=list1.size();i<iLen;i++){
					id=list1.get(i).get("ID");
					mapReader1.put(id,"Y");
					System.out.print(id+", ");
					if(mapReader2Allow.get(id) != null) {
						mapReader2Allow.remove(id);
					}
				}
				System.out.println("");
				System.out.print("READER 2 -> ");
				for(int i=0,iLen=list2.size();i<iLen;i++){
					id=list2.get(i).get("ID");
					mapReader2.put(id,"Y");
					System.out.print(id+", ");
					if(mapReader1Allow.get(id) != null) {
						mapReader1Allow.remove(id);
					}
				}
				boolean ada = false;
				for(Map.Entry me:mapReader1Allow.entrySet()){
					ada=false;
					for(Map.Entry me2:mapReader1.entrySet()){
						if(me.getKey().equals(me2.getKey())){
							ada=true;
						}
					}
					if(ada==false) {
						action.onMove(me.getKey().toString(),reader1.getName());
						mapReader1Allow.remove(me.getKey().toString());
					}
				}
				for(Map.Entry me:mapReader2Allow.entrySet()){
					ada=false;
					for(Map.Entry me2:mapReader2.entrySet()){
						if(me.getKey().equals(me2.getKey())){
							ada=true;
						}
					}
					if(ada==false) {
						action.onMove(me.getKey().toString(),reader2.getName());
						mapReader2Allow.remove(me.getKey().toString());
					}
				}
				System.out.println("");
				System.out.print("Tidak Ada di READER 1 -> ");
				
				for(Map.Entry me:mapReaderOld1.entrySet()){
					ada=false;
					for(Map.Entry me2:mapReader1.entrySet()){
						if(me.getKey().toString().equals(me2.getKey().toString())){
							ada=true;
						}
					}
					if(ada==false){
						for(Map.Entry me2:mapReader2.entrySet()){
							if(me.getKey().toString().equals(me2.getKey().toString())){
								System.out.print(me.getKey().toString()+", ");
								mapReader2Allow.put(me.getKey().toString(),"Y");
							}
						}
					}
				}
				System.out.println("");
				System.out.print("Tidak Ada di READER 2 -> ");
				for(Map.Entry me:mapReaderOld2.entrySet()){
					ada=false;
					for(Map.Entry me2:mapReader2.entrySet()){
						if(me.getKey().toString().equals(me2.getKey().toString())){
							ada=true;
						}
					}
					if(ada==false){
						for(Map.Entry me2:mapReader1.entrySet()){
							if(me.getKey().toString().equals(me2.getKey().toString())){
								System.out.print(me.getKey().toString()+", ");
								mapReader1Allow.put(me.getKey().toString(),"Y");
							}
						}
					}
				}
				
				mapReaderOld1=mapReader1;
				mapReaderOld2=mapReader2;
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	});
	public RFIDDetect(RFIDReader reader1,RFIDReader reader2){
		if(reader1.isActive()==true&&reader2.isActive()==true){
			setReader1(reader1);
			setReader2(reader2);
		}
	}
	public MJsonResult start(Action action){
		this.action=action;
		jsonResult = new MJsonResult();
		this.reader1.activeRF();
		this.reader2.activeRF();
		action.initVersion((String)reader1.getVersion().getData(),(String)reader2.getVersion().getData());
		action.initSensitive((String)reader1.getSensitive().getData(),(String)reader2.getSensitive().getData());
		action.initIP((String)reader1.getIP().getData(),(String)reader2.getIP().getData());
		action.initName(reader1.getName(),reader2.getName());
		run.start();
		return jsonResult;
	}
	public MJsonResult stop(Action action){
		this.action = action;
		jsonResult = new MJsonResult();
		reader1.closeRF();
		reader1.disconnect();
		reader2.closeRF();
		reader2.disconnect();
		return jsonResult;
	}
	public RFIDReader getReader1(){
		return reader1;
	}
	public void setReader1(RFIDReader reader1){
		this.reader1=reader1;
	}
	public RFIDReader getReader2(){
		return reader2;
	}
	public void setReader2(RFIDReader reader2){
		this.reader2=reader2;
	}
	public interface Action{
		default void onMove(String id,String antenaName){}
		default void initVersion(String reader1,String reader2){}
		default void initIP(String reader1,String reader2){}
		default void initSensitive(String reader1,String reader2){}
		default void initName(String reader1,String reader2){}
	}
}
