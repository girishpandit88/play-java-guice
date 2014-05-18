package controllers;

import java.util.List;

import org.slf4j.LoggerFactory;

import play.Configuration;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import services.S3Service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.json.JSONObject;
import com.google.inject.Inject;
public class Application extends Controller {


	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(Application.class);
	
	@Inject
	private S3Service s3Service;
	private static Configuration config = Play.application().configuration();
	static String S3_BUCKET = config.getString("aws.s3.bucket");
	

    public static Result index() {
        return ok();
    }

    public Result getBucketContents(String bucketName){
    	logger.info("######"+bucketName);
    	JSONObject jsonResult = new JSONObject();
    	if(s3Service!=null){
    		List<S3ObjectSummary> files = s3Service.listBucketContents(bucketName);
    		for (S3ObjectSummary file: files){
        		try {
    				jsonResult.append(file.getKey(), file.getSize());
    			} catch (Exception e) {
    				return internalServerError("Error retriving bucket Contents");
    			}
        	}
        	
        	return ok(jsonResult.toString());
    	} else{
    		return error();
    	}
    	
    	
    	
    }

	private static Result error() {
		return internalServerError("s3Service null");
	}

}
