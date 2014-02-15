package utils;

import com.bohan.bohan_srv.service.MawbhawbService;


public class ServiceHelper {
	
	public static MawbhawbService getMawbhawbService(){
		return (MawbhawbService)Global.getBean("mawbhawbService");
	}
	
}
