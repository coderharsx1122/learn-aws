package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;
import software.amazon.awscdk.services.s3.BucketProps;
import software.amazon.awscdk.services.sqs.Queue;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class AwsCdkStack extends Stack {
    public AwsCdkStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public AwsCdkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here

        // example resource
//         final Queue queue = Queue.Builder.create(this, "AwsCdkQueue")
//                 .visibilityTimeout(Duration.seconds(300))
//                 .build();
        Bucket bucket = new Bucket(this, "MyBucket", new BucketProps.Builder()
                .versioned(true)
                .encryption(BucketEncryption.KMS_MANAGED)
                .build());
    }
}
