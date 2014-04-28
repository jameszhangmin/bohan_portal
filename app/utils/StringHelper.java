package utils;

import com.bohan.bohan_dao.domain.Mawbhawb;

public class StringHelper {

	public static Boolean isFangxing(Mawbhawb mawbhawb){
		boolean result = false;
		if (mawbhawb.getDzfx()!=null && mawbhawb.getDzfx().equals("1")
				&& mawbhawb.getBgfx()!=null && mawbhawb.getBgfx().equals("1")
				&& mawbhawb.getKkgj()!=null && mawbhawb.getKkgj().equals("1")
				&& mawbhawb.getDcfx()!=null && mawbhawb.getDcfx().equals("1")
				&& mawbhawb.getSjlf()!=null && mawbhawb.getSjlf().equals("1")) {
			result = true;
		}else if(mawbhawb.getKkgj()!=null && mawbhawb.getKkgj().equals("1")
				|| mawbhawb.getKbfx()!=null && mawbhawb.getKbfx().equals("1")) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}
	
	public static String getEMS(Mawbhawb mawbhawb) {
		String emsStr = mawbhawb.getEMS_dh();
		if (emsStr!=null) {
			if (emsStr.split("_").length>2) {
				return emsStr.split("_")[1];
			}else{
				return emsStr;
			}
		}else {
			return null;
		}
	}
}
