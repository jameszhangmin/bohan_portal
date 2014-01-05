/**
 * 
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Request;
import play.mvc.Http.RequestHeader;
import play.mvc.SimpleResult;

/**
 * @author zhangmin
 * 
 */
public class Global extends GlobalSettings {

	private static final Logger logger = Logger.getLogger(Global.class);
	
	public static final boolean IS_TEST = true;

	private static ApplicationContext ac;
	
	/**
	 * 
	 */
	public Global() {
	}
	

	@Override
	public void onStart(Application arg0) {
		logger.info("Application has started.");
		// init spring
		System.out.println("start init spring config");
		ac = new FileSystemXmlApplicationContext(
				"classpath:META-INF/applicationContext.xml");
		System.out.println("ac:" + ac);
		
		
		super.onStart(arg0);
	}

	@Override
	public void onStop(Application app) {
		logger.info("Application shutdown...");
	}

	@Override
	public Promise<SimpleResult> onError(RequestHeader request, Throwable t) {
		String error = "Error occured" + "\r\n\r\n";
		error = error + "host: " + request.host();
		error = error + "uri: " + request.uri() + "\r\n";
		error = error + "params: " + request.queryString() + "\r\n";
		error = error + "methods: " + request.method() + "\r\n";
		error = error + "path: " + request.path() + "\r\n\r\n";
		error = error + "headers: " + "\r\n";
		for (String key : request.headers().keySet()) {
			error = error + key + " = ";
			String[] values = request.headers().get(key);
			if (values != null && values.length > 0) {
				for (String value : values) {
					error = error + value + ",";
				}
			}
			error = error + "\r\n";
		}
		error = error + "\r\n";
		error = error + ExceptionUtils.getStackTrace(t);
		logger.error(error);
//		SendMailService sendMailService = (SendMailService)getBean("sendMailService");
//		sendMailService.sendErrorMail(error, "weixin");
		return super.onError(request, t);
	}

	@Override
	public Action onRequest(Request request, Method arg1) {
		// memory print
		logger.debug("total memory: " + Runtime.getRuntime().totalMemory()
				/ 1024 + "KB");
		logger.debug("free memory: " + Runtime.getRuntime().freeMemory() / 1024
				+ "KB");
		return super.onRequest(request, arg1);
	}

	public static Object getBean(String beanName) {
		return ac.getBean(beanName);
	}

	public static String getParam(Request request, String name) {
		String[] _params = request.queryString().get(name);
		if (_params != null && _params.length > 0) {
			try {
				return java.net.URLDecoder.decode(_params[0], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.info("decode url error!");
			}
		}
		return null;
	}

}
