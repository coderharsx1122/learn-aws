package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class AwsCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        new AwsCdkStack(app, "AwsCdkStack", StackProps.builder()
                .env(Environment.builder()
                        .account("your account no")
                        .region("ap-south-1")
                        .build())

                // For more information, see https://docs.aws.amazon.com/cdk/latest/guide/environments.html
                .build());

        app.synth();
    }
}

