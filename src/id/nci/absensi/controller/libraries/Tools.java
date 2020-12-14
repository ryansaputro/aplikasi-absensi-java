package id.nci.absensi.controller.libraries;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Locale;

public class Tools {
	public static byte uniteBytes(byte src0,byte src1){
		StringBuilder sb=new StringBuilder();
		sb.append("0x");
		sb.append(new String(new byte[]{src0}));
		byte _b0=(byte)(Byte.decode(sb.toString()).byteValue()<<4);
		StringBuilder sb2=new StringBuilder();
		sb2.append("0x");
		sb2.append(new String(new byte[]{src1}));
		return (byte)(_b0^Byte.decode(sb2.toString()).byteValue());
	}
	public static byte[] hexString2Bytes(String src){
		int len=src.length()/2;
		byte[] ret=new byte[len];
		byte[] tmp=src.getBytes();
		for(int i=0;i<len;i++){
			ret[i]=uniteBytes(tmp[i*2],tmp[(i*2)+1]);
		}
		return ret;
	}
	public static String bytes2HexString(byte[] b,int size){
		String ret="";
		for(int i=0;i<size;i++){
			String hex=Integer.toHexString(b[i]&255);
			if(hex.length()==1){
				StringBuilder sb=new StringBuilder();
				sb.append("0");
				sb.append(hex);
				hex=sb.toString();
			}
			StringBuilder sb2=new StringBuilder();
			sb2.append(ret);
			sb2.append(hex.toUpperCase(Locale.CHINESE));
			ret=sb2.toString();
		}
		return ret;
	}
	public String dec2Hex(int dec,int bit){
		return bytes2HexString(intToByte(dec),bit);
	}
	public String generateCc(String hexString){
		int jum=sumHexaString(hexString);
		String ret="";
		boolean ulang=true;
		int bit=512;
		while(ulang==true){
			if(jum<=bit){
				ret=dec2Hex(bit-jum,1);
				ulang=false;
			}else{
				bit=bit*2;
			}
		}
		return hexString+ret;
	}
	public int sumHexaString(String hexString){
		int ret=0;
		String strHex=null;
		for(int i=0,iLen=hexString.length()/2;i<iLen;i++){
			strHex=hexString.substring((i*2),(i*2)+2);
			ret+=Long.parseLong(strHex,16);
		}
		return ret;
	}
	public String exec(String str,DataInputStream input,DataOutputStream output,int i){
		String ret=null;
		try{
			output.write(hexString2Bytes(str));
			byte[] cache=new byte[i];
			Thread.sleep(100);
			int len=input.read(cache);
			ret=Tools.bytes2HexString(cache,len);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	public String getValue(String str,int i){
		return this.hex2Decimal(str.substring((i*2)-2,i*2));
	}
	public String getValue(String str,int i,int i2){
		return this.hex2Decimal(str.substring((i*2)-2,(i*2)+((i2-1)*2)));
	}
	public String getValueHex(String str,int i){
		return str.substring((i*2)-2,i*2);
	}
	public String getValueHex(String str,int i,int i2){
		return str.substring((i*2)-2,(i*2)+((i2-1)*2));
	}
	public String hex2Decimal(String hex){
		return String.valueOf(Integer.parseInt(hex,16));
	}
	public static byte[] intToByte(int i){
		return new byte[]{(byte)(i&255),(byte)((65280&i)>>8),(byte)((16711680&i)>>16),(byte)((-16777216&i)>>24)};
	}
}
