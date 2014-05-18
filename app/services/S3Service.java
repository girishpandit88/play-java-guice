package services;

import java.util.List;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public interface S3Service {
	List<S3ObjectSummary> listBucketContents(String bucket);
}
