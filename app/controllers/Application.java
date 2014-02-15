package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.bohan.bohan_dao.domain.Mawbhawb;
import com.bohan.bohan_srv.service.MawbhawbService;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import utils.Global;
import utils.ServiceHelper;
import views.html.*;

public class Application extends Controller {
	private static final Logger logger = Logger.getLogger(Application.class);
	private static final MawbhawbService mawbhawbService = ServiceHelper.getMawbhawbService();

    public static Result index() {
        return ok(index.render());
    }
    
    public static Result query(){
    	logger.info("get query");
    	String k = Global.getParam(request(), "keyword");
    	List<String> keywordList = new ArrayList<String>();
    	if (k!=null) {
			keywordList = Arrays.asList(k.split(","));
		}
    	List<Mawbhawb> resultList = mawbhawbService.getMawbhawbByQuery(keywordList);
    	return ok(list.render(resultList));
    }

}
