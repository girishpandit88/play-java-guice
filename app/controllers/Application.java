package controllers;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.S3Service;

import java.util.List;

public class Application extends Controller {

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(Application.class);
//    private static Configuration config = Play.application().configuration();
    @Inject
    private S3Service s3Service;

//    private static Result error() {
//        return internalServerError("s3Service null");
//    }

	public Result index() {
        return ok(views.html.index.render("Welcome to my first app"));
    }

    public Result getBucketContents() throws JSONException {
        JsonNode params = request().body().asJson();
        String bucketName = params.get("bucketName").textValue();
        String didMap = params.get("nestedJSON").toString();

        System.out.println("!#!@!@#!@#----------" + bucketName);
        logger.info("######" + bucketName);
        JSONObject jsonResult = new JSONObject();
        System.out.println("######" + didMap);
        jsonResult.append("didMap from request", new JSONObject(didMap));
        List<S3ObjectSummary> files = s3Service.listBucketContents(bucketName);
        for (S3ObjectSummary file : files) {
            jsonResult.append(file.getKey(), file.getSize());
        }

        return ok(jsonResult.toString());
    }

}
