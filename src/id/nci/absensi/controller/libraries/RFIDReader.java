package id.nci.absensi.controller.libraries;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import id.nci.absensi.controller.libraries.MsgError;
import id.nci.absensi.model.MJsonResult;
import id.nci.absensi.controller.libraries.Tools;

public class RFIDReader {
	private String ip=null;
	private String name;
	private int port=0;
	private Socket socket=null;
	private DataOutputStream output=null;
	private DataInputStream input=null;
	private boolean active=false;
	private Tools tools = new Tools();
	private MsgError msgerror=new MsgError();
	private MJsonResult jsonResult=null;
	public String PARAMETER_ADDRESS="A3";
	
	public RFIDReader(String ip,int port,String name){
		try{
			setActive(true);
			socket = new Socket(ip,port);
			input=new DataInputStream(socket.getInputStream());
			output=new DataOutputStream(socket.getOutputStream());
			this.setName(name);
		}catch(UnknownHostException e){
			// TODO Auto-generated catch block
			setActive(false);
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			setActive(false);
			e.printStackTrace();
			String error = e.toString();
			System.out.print("catch "+error);
		}
	}
	
	public MJsonResult activeRF(){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			jsonResult.setResult(tools.getValueHex(tools.exec(tools.generateCc("0AFE0290"),input,output,5),4));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult reset(){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			jsonResult.setResult(tools.getValueHex(tools.exec(tools.generateCc("0AFF0221"),input,output,5),4));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult closeRF(){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			jsonResult.setResult(tools.getValueHex(tools.exec(tools.generateCc("0AFF0291"),input,output,5),4));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult setSensitive(int value){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			jsonResult.setResult(tools.getValueHex(
					tools.exec(tools.generateCc("0AFF04236F"+tools.dec2Hex(value,1)),input,output,5),4));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult getVersion(){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			String res = tools.exec(tools.generateCc("0AFF0222"),input,output,7);
			jsonResult.setResult(tools.getValueHex(res,4));
			jsonResult.setData("V"+tools.getValue(res,5)+"."+tools.getValue(res,6));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult setIP(String ip,int port,String subnetMask,String defaultGateway){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		String[] splitIp=ip.split("\\.");
		String[] splitSubnetMask=subnetMask.split("\\.");
		String[] splitDefaultGateway=defaultGateway.split("\\.");
		if(isActive()==true){
			String strHex="0AFF102C";
			strHex+=tools.dec2Hex(Integer.parseInt(splitIp[0]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitIp[1]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitIp[2]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitIp[3]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitSubnetMask[0]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitSubnetMask[1]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitSubnetMask[2]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitSubnetMask[3]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitDefaultGateway[0]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitDefaultGateway[1]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitDefaultGateway[2]),1);
			strHex+=tools.dec2Hex(Integer.parseInt(splitDefaultGateway[3]),1);
			String strPort = tools.dec2Hex(port,2);
			if(strPort.substring(2,4).equals("00")){
				strPort="00"+strPort.substring(0,2);
			}
			strHex+=strPort;
			jsonResult.setResult(tools.getValueHex(tools.exec(tools.generateCc(strHex),input,output,5),4));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult getIP(){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			String res = tools.exec(tools.generateCc("0AFF022B"),input,output,19);
			jsonResult.setResult(tools.getValueHex(res,4));
			jsonResult.setData(tools.getValue(res,5)+"."+tools.getValue(res,6)+"."+tools.getValue(res,7)+"."
					+tools.getValue(res,8)+":"+tools.getValue(res,18)+" "+tools.getValue(res,9)+"."
					+tools.getValue(res,10)+"."+tools.getValue(res,11)+"."+tools.getValue(res,12)+" "
					+tools.getValue(res,13)+"."+tools.getValue(res,14)+"."+tools.getValue(res,15)+"."
					+tools.getValue(res,16));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult getSensitive(){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			String res=tools.exec(tools.generateCc("0AFF03246F"),input,output,6);
			jsonResult.setResult(tools.getValueHex(res,4));
			jsonResult.setData(tools.getValue(res,5));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult getParameter(String data){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			String res=tools.exec(tools.generateCc("0A790324"+data),input,output,6);
			jsonResult.setResult(tools.getValueHex(res,4));
			jsonResult.setData(res);
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult setParameter(String data,int value){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			jsonResult.setResult(tools.getValueHex(
					tools.exec(tools.generateCc("0AFF0423"+data+tools.dec2Hex(value,1)),input,output,5),4));
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public MJsonResult read(){
		jsonResult = new MJsonResult();
		jsonResult.setResult(msgerror.ERR_ACTIVE);
		if(isActive()==true){
			String res = tools.exec(tools.generateCc("0AFF029A"),input,output,1024);
			String data = res.substring(10,(10+(Integer.parseInt(tools.getValue(res,5))*20)));
			String dataDetail=null;
			HashMap<String,String> map=null;
			ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
			boolean ada=false;
			for(int i=0,iLen=Integer.parseInt(tools.getValue(res,5));i<iLen;i++){
				map=new HashMap<String,String>();
				dataDetail=data.substring(i*20,(i*20)+20);
				map.put("TYPE",tools.getValue(dataDetail,1));
				map.put("ID",tools.getValueHex(dataDetail,2,4));
				map.put("DATA",tools.getValueHex(dataDetail,6,5));
				ada=false;
				for(int j=0,jLen=list.size();j<jLen;j++){
					if(list.get(j).get("ID").equals(tools.getValueHex(dataDetail,2,4))){
						ada=true;
					}
				}
				if(ada==false){
					list.add(map);
				}
			}
			jsonResult.setResult(tools.getValueHex(res,4));
			jsonResult.setData(list);
		}
		jsonResult.setMessage(msgerror.getMessage(jsonResult.getResult()));
		return jsonResult;
	}
	public String disconnect(){
		String ret = msgerror.ERR_ACTIVE;
		if(isActive()==true){
			try{
				active=false;
				ret = msgerror.ERR_NONE;
				Thread.sleep(100);
				input.close();
				output.close();
				socket.close();
			}catch(IOException e){
				e.printStackTrace();
				ret = msgerror.ERR_UNDEFINED;
			}catch(InterruptedException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret = msgerror.ERR_UNDEFINED;
			}
		}
		return ret;
	}
	@SuppressWarnings("static-access")
	public String exec(String str,int i){
		String ret=null;
		try{
			output.write(tools.hexString2Bytes(str));
			byte[] cache=new byte[i];
			Thread.sleep(100);
			int len=input.read(cache);
			ret=tools.bytes2HexString(cache,len);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	public boolean isActive(){
		return active;
	}
	public void setActive(boolean active){
		this.active=active;
	}
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip=ip;
	}
	public int getPort(){
		return port;
	}
	public void setPort(int port){
		this.port=port;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
}
