package SampleS3Bucket;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


// docs -- https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-s3-buckets.html
public class DemoS3 {


    // place access key and secret key in secret file or env file
    private static String awsAccessKey = "your access key";
    private static String awsSecretKey = "";
    private static BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    private static AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);


    private static String region = "AP_SOUTH_1"; // CHOOSE THE REGION IN WHICH YOU ARE
    public static Bucket getBucket(String bucket_name) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    // delete buckets 
    public static void deleteBucket(String bucket_name){
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.AP_SOUTH_1).build();
        System.out.println(" - removing objects from bucket");
        ObjectListing object_listing = s3.listObjects(bucket_name);
        while (true) {
            for (Iterator<?> iterator =
                 object_listing.getObjectSummaries().iterator();
                 iterator.hasNext(); ) {
                S3ObjectSummary summary = (S3ObjectSummary) iterator.next();
                s3.deleteObject(bucket_name, summary.getKey());
            }

            // more object_listing to retrieve?
            if (object_listing.isTruncated()) {
                object_listing = s3.listNextBatchOfObjects(object_listing);
            } else {
                break;
            }
        }
    }

    // create a new bucket
    public static Bucket createBucket(String bucket_name) {
        // conncect with aws s3 using IAM credentials
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.AP_SOUTH_1).build();
        Bucket b = null;
        if (s3.doesBucketExistV2(bucket_name)) {
            System.out.format("Bucket %s already exists.\n", bucket_name);
            b = getBucket(bucket_name);
        } else {
            try {
                b = s3.createBucket(bucket_name);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
        return b;
    }
    private static void ListMyBuckets() {
        final  AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.AP_SOUTH_1).build();
        List<Bucket> buckets = s3.listBuckets();
        System.out.println("My buckets now are:");
        for (Bucket b : buckets) {
            System.out.println(b.getName());
        }
    }
    public static void main(String[] args) {
       ListMyBuckets();

    }
}
