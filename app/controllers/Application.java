package controllers;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.LoggerFactory;

import play.Configuration;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.S3Service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.google.inject.Inject;

public class Application extends Controller {

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(Application.class);

	@Inject
	private S3Service s3Service;
	private static Configuration config = Play.application().configuration();
	static String S3_BUCKET = config.getString("aws.s3.bucket");

	public Result index() {
		return ok(views.html.index.render("Welcome to my first app"));
	}

	public Result getBucketContents() throws JSONException {
        JsonNode params = request().body().asJson();
        String bucketName = params.get("bucketName").toString();
        String didMap = String.valueOf(params.get("didMap"));
        System.out.println("!#!@!@#!@#----------"+bucketName);
        logger.info("######" + bucketName);
		JSONObject jsonResult = new JSONObject();
        System.out.println("######"+ didMap);
        List<S3ObjectSummary> files = s3Service.listBucketContents(bucketName);
		for (S3ObjectSummary file : files) {
			jsonResult.append(file.getKey(), file.getSize());
		}

		return ok(jsonResult.toString());
	}

	private static Result error() {
		return internalServerError("s3Service null");
	}

}
