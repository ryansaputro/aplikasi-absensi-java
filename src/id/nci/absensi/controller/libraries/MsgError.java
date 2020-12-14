package id.nci.absensi.controller.libraries;

public class MsgError {
	
	public String ERR_NONE="00";
	public String ERR_GENERAL_ERR="01";
	public String ERR_PAR_SET_FAILED="02";
	public String ERR_PAR_GET_FAILED="03";
	public String ERR_NO_TAG="04";
	public String ERR_READ_FAILED="05";
	public String ERR_WRITE_FAILED="06";
	public String ERR_LOCK_FAILED="07";
	public String ERR_ERASE_FAILED="08";
	public String ERR_CMD_ERR="FE";
	public String ERR_UNDEFINED="FF";
	public String ERR_ACTIVE="ERR_ACTIVE";
	
	public String getMessage(String code){
		String ret="No Message Command";
		if(code.equals(this.ERR_NONE)){
			ret="The command completed successfully";
		}else if(code.equals(this.ERR_GENERAL_ERR)){
			ret="General errors";
		}else if(code.equals(this.ERR_PAR_SET_FAILED)){
			ret="Parameter setting failed";
		}else if(code.equals(this.ERR_PAR_GET_FAILED)){
			ret="Get Parameter failed";
		}else if(code.equals(this.ERR_NO_TAG)){
			ret="No tag";
		}else if(code.equals(this.ERR_READ_FAILED)){
			ret="Tag read failed";
		}else if(code.equals(this.ERR_WRITE_FAILED)){
			ret="Tag writing failed";
		}else if(code.equals(this.ERR_LOCK_FAILED)){
			ret="Tag lock failed";
		}else if(code.equals(this.ERR_ERASE_FAILED)){
			ret="Tag erase failed";
		}else if(code.equals(this.ERR_CMD_ERR)){
			ret="Command not supported or parameter out of range";
		}else if(code.equals(this.ERR_UNDEFINED)){
			ret="Undefined error";
		}else if(code.equals(this.ERR_ACTIVE)){
			ret="Reader Not Active";
		}
		return ret;
	}
}
