package SampleS3Bucket;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CloudFormationDemo {
    public static void main(String[] args) {
        String accessKeyId = "your access key";
        String secretAccessKey = "your secret key";
        String dir = System.getProperty("user.dir");
        String templateFilePath = Paths.get(dir, "src", "main", "java", "SampleS3Bucket", "Demo.yaml").toString();
        String stackname = "mystackname"+ System.currentTimeMillis();
        String template = "";
        try{
            template = new String(Files.readAllBytes(Paths.get(templateFilePath)));
            System.out.println(template);
        }
        catch (IOException e){
            System.out.println("error"+e.getMessage());
            System.out.println(e.getCause());
        }
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awsCreds);

        AmazonCloudFormation cloudFormation = AmazonCloudFormationClientBuilder.standard().withCredentials(credentialsProvider).build();

        CreateStackRequest createRequest = new CreateStackRequest()
                .withStackName(stackname)
                .withTemplateBody(template);
        cloudFormation.createStack(createRequest);
        System.out.println("Stack creation request sent.");
        System.out.println("Resource created successfully");

    }
}
