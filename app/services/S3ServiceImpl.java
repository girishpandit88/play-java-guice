package services;

import java.util.List;

import org.slf4j.LoggerFactory;

import play.Application;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.inject.Singleton;
@Singleton
public class S3ServiceImpl implements S3Service {


	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(Application.class);
	public static final String AWS_ACCESS_KEY = "aws.access.key";
	public static final String AWS_SECRET_KEY = "aws.secret.key";
	private String secretKey;
	private String accessKey;

	public static AmazonS3 amazonS3;

	public S3ServiceImpl(Application application) {
		accessKey = application.configuration()
				.getString(AWS_ACCESS_KEY);
		secretKey = application.configuration()
				.getString(AWS_SECRET_KEY);

		if ((accessKey != null) && (secretKey != null)) {
			AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,
					secretKey);
			amazonS3 = new AmazonS3Client(awsCredentials);
		}
	}

	public List<S3ObjectSummary> listBucketContents(String bucket)
			throws AmazonClientException {
//		logger.info(accessKey + "***" + secretKey);
		ObjectListing listing = amazonS3.listObjects(bucket);
		List<S3ObjectSummary> summaries = listing.getObjectSummaries();

		while (listing.isTruncated()) {
			listing = amazonS3.listNextBatchOfObjects(listing);
			summaries.addAll(listing.getObjectSummaries());
		}
		return summaries;

	}

}