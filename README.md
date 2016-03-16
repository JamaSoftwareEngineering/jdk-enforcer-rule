# JDK Enforcer Rule

The `jdk-enforcer-rule` is a [(custom) enforcer rule]
(https://maven.apache.org/enforcer/enforcer-api/writing-a-custom-rule.html) to check a certain JDK version.

The enforcer rule looks at system properties to check the following things:

 - Must be "Oracle Corporation"
 - Must be "Java(TM) SE Runtime Environment"

It was originally designed to validate that we were building with the right JDK, as we were having some OpenJDK
sightings in our ecosystem that we (unfortunately) weren't ready to support.
 
Here is a usage example of this enforcer rule:

    <plugin>
        <artifactId>maven-enforcer-plugin</artifactId>
        <executions>
            <execution>
                <goals>
                    <goal>enforce</goal>
                </goals>
                <configuration>
                    <rules>
                        <jdkEnforcerRule implementation="com.jamasoftware.maven.enforcer.jdk.JdkEnforcerRule" />
                    </rules>
                </configuration>
            </execution>
        </executions>
        <dependencies>
            <dependency>
                <groupId>com.jamasoftware.maven.enforcer</groupId>
                <artifactId>jdk-enforcer-rule</artifactId>
                <version>...version...</version>
            </dependency>
        </dependencies>
    </plugin>

Note that this enforcer rule is not concerned with the Java version, as this can be enforced perfectly well with the
`source` and `target` configuration of the Maven Compiler Plugin.

Run the following command to build and then test the JDK enforcer rule:

    mvn install --file pom-all.xml

The JDK enforcer rule is tested by running it on a test project (see `test-project` folder).

## License

This project is licensed under [the Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).

## About Jama

Jama is software for better, faster requirements definition, management, verification and validation, from inception to
production. Our vision is of Modern Product Delivery. Building new products shouldn’t be frustrating and wasteful. It
ought to be enlightening and profitable. We make possible the impossible products of the future. Find more information
on [our web site](http://www.jamasoftware.com/). Jama Software is a fast-growing company, and we are [hiring]
(http://www.jamasoftware.com/company/careers/).
