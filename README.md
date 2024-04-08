# Spring Boot Accelerator Template for Micro Services

This repo is a templated accelerator for Spring Boot based Microservices with wide features & automated CI/CD pipeline. To support enlightning design principles for good microservices, a capable and reliant scaffold for a Java Spring Boot based microservices where one needs to put in the business logic,pick the choose the components of needs and can build and deploy a full-blown micro service code.

# Capabilities
Below capabilities are available:

1. Usage of Gradle Catalog
2. Integrated Actuator & Micrometer
3. JWT authorization using JWT auth library
4. OpenAPIv3 Tools for model creation
5. Image Creation using jib Build
6. Compressed JSON responses
7. JUnit 5
8. Flogger + LoggingFilter (X-Correlation-ID)
9. Spring cloud GCP Support
10. Sphinx (Generate HTML documentations from the README.md file)
11. Integrated Audit Log library
12. DB changeset application with Liquibase
13. Backward compatibility on service specifications
14. Canary charts with release version support
15. Kotlin development support
16. Secret Manager Integration
17. Liveness and Readiness probe
18. BDD framework using cucumber

# How to consume this template to create new Microservice
1. Go to github repo "_https://github.com/lbg-gcp-foundation/lbg-starter-template_"
2. On the current repo, click on the button "_Use this Template_"
3. Then click "_Create a new repository_"
4. After that it will redirect user to new repository creation page where user can enter **name** of the repo, can choose whether repo will be public or private and also there is an option whether you want to include the branches from main starter template repo or not.
5. Then click on "_Create repository from template_" after all settings are done. This will create and publish the new repo in GitHub with the name user entered in step-4.
6. Once the new repo is created, replace all the place holders like ```__PACKAGE_PREFIX__,__CLASS__``` as required for new service requirements.

## Capabilities Configuration

### Gradle Catalog
Gradle catalog offers a version catalog plugin which offers the ability to declare and publish a catalog with desired version of dependencies. Microservice Stater template uses this gradle catalog published and maintained by LBG platform
so the same version of required dependencies shared across all the services using gradle catalog

Below are configuration needed to import the catalog

**settings.gradle**
```
dependencyResolutionManagement {
    repositories {
        maven {
            url 'https://nexus.mgmt-bld.oncp.dev/repository/maven-public/'
        }
        maven {
            url 'https://nexus.sbs-bld.oncp.dev/repository/shared-services/'
        }
    }
    versionCatalogs {
        libs {
            from("com.lbg:lbg-gradle-catalog:1.11.0")
        }
    }
}
```

Sample import of dependencies in **build.gradle**. Here only alias or bundles mentioned in Gradle Catalog are imported and can also override required version

```

dependencies{

    implementation libs.lbg.logging.filter

    implementation(libs.flogger)
    implementation(libs.floggersystem)
    implementation(libs.floggerslf4j)

    implementation libs.bundles.spring.libs
}

```
more details can be found here : [Gradle Catalog documentation](https://confluence.devops.lloydsbanking.com.mcas.ms/display/PTE/Logging+Filter+Library)

### Actuator
Actuator endpoints of /health , /build and /metrics are exposed.
Below configuration is used to expose the endpoints.

**build.gradle**
```

dependencies{
    implementation libs.bundles.spring.libs
}

springBoot {
        buildInfo()
}

```

Expose the health and info endpoints by setting below properties.

**application.properties**
```
management.endpoints.web.exposure.include=metrics,health,refresh,info,prometheus
```

### Custom Metrics to Stackdriver using Micrometer

Micrometer is the instrumentation library powering the delivery of application metrics from Spring. To export the custom metrics using micrometer to Stackdriver below library is included in **gradle.build**

```
    implementation(libs.micrometer.core)
    implementation(libs.micrometer.registry.prometheus)
    implementation(libs.micrometer.registry.stackdriver)

```
**application.properties**: Need to populate below properties with correct values after enabling management.metrics.export.dynatrace.enabled to true.
```
           management.endpoint.metrics.enabled=true
           management.endpoint.refresh.enabled=true
           management.endpoint.prometheus.enabled=true
           management.metrics.export.prometheus.enabled=true
           management.metrics.export.stackdriver.enabled=true
           management.metrics.export.stackdriver.project-id=MY_PROJECT_ID
           management.metrics.export.stackdriver.resource-type=global
           management.metrics.export.stackdriver.step=1m
```

### JWT Authorization
JWT Auth library has been integrated within MS starter template to enable zero trust for an application, where access token is verfied each time for signed token when the request comes in. To use the LBG's JWT Auth library add below library within gradle build.

##### build.gradle
```
dependencies {
    implementation "com.lbg:jwt-validate-starter:1.0.12"
}
```
and configure the below properties 

##### application.properties
```
lbg.jwt.validate.jwks.url= /* Defines JWKS URL to validate the token. It is mandatory */
lbg.jwt.validate.cache.timeout= /* Defines timeout for ehcache.It is mandatory */
```

This JWT Auth library provides 2 ways of authorising token as shown below within this template
1. For just token validation

##### __CLASS__Controller.java
```
Add annotation @JWTValidate on API Controller method to include the JWT Validation for that service 
```

2. For Token validation and claims

Update the below property with below value to use 

##### application.properties
```
jwt.filter.type=JWT_LIB_AUTH
```
which adds **JWTLibraryValidator** filter to each controller request and authenticates the user 

more details can be found here : https://confluence.devops.lloydsbanking.com.mcas.ms/display/PTE/JWT+Authorization

### Open API v3 Tools
Open API v3 tools allow creation of models from API specification. This template has sample yaml for Swagger Customer API and required configuration to generate the model classes.

##### specs/api-specs.yaml
This is the sample API specification. Replace this file with the API specaification file for the API you want to generate.

##### build.gradle
```
openApiGenerate {
    generatorName = "java"
    inputSpec = "$rootDir/specs/api-specs.yaml".toString() // specify the API specs
    outputDir = "$buildDir/generated".toString()
    modelPackage = "__PACKAGE_PREFIX__.request"
    systemProperties = [
            models: "Error,Pet"
    ]
}
```

Use below command/ run the below gradle task to generate the API models
```
gradle openApiGenerate
```

### Jib
Jib allows docker image creation without docker deamon. Refer [jib documentation](https://github.com/GoogleContainerTools/jib/blob/master/README.md) Jib plugin (version '1.7.0') is present in build.gradle.
```
alias(libs.plugins.plugin.gcloud.jib)

```

It is configured to push docker image to local docker registry. This can be fine tuned using following properties. The current settings use [distroless-java11](https://github.com/GoogleContainerTools/distroless/blob/master/README.md) .
```
jib {
    // specify the name of docker image to be built
    to.image = "<service-name>/<app-name>"
    // specify the exact base image to be used. Below is a Java 11 debian image
    //eg: harbor.mgmt-bld.oncp.dev/mve_tools_rtl/sbs-java-wss-distroless:release_v0.8.0
    from.image = "<key your base image>"
    container.user = "nonroot"
}
```
#### Using jib to create an image
Use below command to create a docker image and push it to specified registry.
```
gradle jibDockerBuild
```
Refer [jib-gradle-plugin documentation](https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin) for more details.

### Compressed JSON responses
Below properties are set in the template to enbable compressed JSON responses.
**application.properties**
```
# Enable response compression
server.compression.enabled=true
# The comma-separated list of mime types that should be compressed
server.compression.mime-types=application/json,text/html,text/xml,text/plain,text/css,text/javascript,application/javascript
# Compress the response only if the response size is at least 1KB
server.compression.min-response-size=1024
```


### JUnit 5
The template includes [JUnit 5](https://junit.org/junit5/).

### Flogger + log4j2 + LoggingFilter ##

Apache Log4j2 is the latest logging framework and provides support for SLF4J, automatically reloads your logging configuration, and supports advanced filtering options.
In addition to these features, it also allows lazy evaluation of log statements based on lambda expressions, offers asynchronous loggers for low-latency systems, and provides
a garbage-free mode to avoid any latency caused by garbage collector operations.

In this template, a customized logging library dependency is added for log4J logs and Flogger dependencies are added for Flogger logs. 

```
dependencies {
   ....
    /*
      gradle dependency for the custom logging filter.
    */
    implementation libs.lbg.logging.filter

    /*
      gradle dependency for the flogger api.
    */
    implementation(libs.flogger)
    implementation(libs.floggersystem)
    implementation(libs.floggerslf4j)
   ....
}
```

Log4J Configuration can be found in ```log4j2.xml``` which is located in ```src/main/resources``` directory.

Also, logging-filter library supports following functionality:
* Changing the log level per request. To update log level on the fly for a specific request,
  add a header named ```X-Log-Level``` and set its value to one of the log levels such as ```DEBUG```, ```INFO```, ```TRACES```, ```WARN``` and ```ERROR```.
* Add ```correlation-id``` to ```ThreadContext``` for logging purpose.

more details can be found here : [Logging filter documentation](https://confluence.devops.lloydsbanking.com.mcas.ms/display/PTE/Logging+Filter+Library)

### Build Pipeline
**Jenkinsfile** within repo configures the various stages for standard pipeline stages followed on BLD deployment within LBG GCP platform. On high level, it includes stages for Backward compatibility check, build, Unit test and BDD test, Image build, Quality metrics and report generation(Sonar,NexusIQ and Zap proxy), Liquibase changeset deployment , Helm charts deployment and release tag creation.

### Service-Mesh
This template includes stages within Pipeline which helps to configure Service-mesh using the helm Charts. All the service mesh charts can be seen in Folder
**helm-charts/service-mesh-servicename** and these values can be overriden by **helm-overrides/service-mesh-service-name-<envName>-config.yaml**

below is the stage within Jenkins pipeline for deployment using sharedlib method
```
 stage('Helm: Service Deployment') {
            when {
                branch "${LIVE_BRANCH}"
            }
            steps {
                container('infra-tools') {
                    dir('helm-charts') {
                        sh '''
                            echo "****************************** Deploy Service artifact ****************************"
                            gcloud container clusters get-credentials ${clusterName} --region ${clusterRegion} --project ${projectId}
                        '''
                        upgradeReleaseV3(
                                chartType: "SERVICE",
                                istioFlag: true,
                                chartName: "service-mesh-${projectName}",
                                version: "${version}",
                                namespace: "${namespace}",
                                releaseName: "service-mesh-${releaseName}${SERVICE_VERSION}",
                                options: "  -f ../helm-overrides/service-mesh-<service-name>-${environmentName}-config.yaml -f ../helm-overrides/release-versions.yaml",
                                credentialsId: "jenkinsPAT"
                        )
                        statusReleaseV3(flags: "", releaseName: "service-mesh-${releaseName}${SERVICE_VERSION}", namespace: "${namespace}")
                    }
                }
            }
        }

```

### Spring Cloud GCP Support
Dependencies are already included for Spring Cloud GCP support. Read [this link](https://googlecloudplatform.github.io/spring-cloud-gcp/reference/html/index.html) to learn more. It is disabled as it needs credentials for google cloud.
Follow below steps to enable.

**build.gradle**: Uncomment below lines.
```    
        implementation(libs.google.cloud.libraries.bom)
```

**application.properties**: Update path to the GCP credentials and uncomment below line.
```
        spring.cloud.gcp.credentials.location=file:///application_default_credentials.json
```

To find default credentials use below command (gcloud SDK required) and login using the browser:
```shell script
$ gcloud auth application-default login
Your browser has been opened to visit:

    https://accounts.google.com/o/oauth2/auth?code_challenge=*********



Credentials saved to file: [/Users/application_default_credentials.json]

```

### Automatic document generation using Sphinx

This capability/plugin generates HTML documentations from the README.md file. It requires below config to generate required documents :

* A configuration file called conf.py that defines the theme and other options
* A specific structure of Readme that should be maintained for automated documentation generation.Sample format is available here [README_TEMPLATE.md](documentation/source/README_TEMPLATE.md) .These steps meet compliance and needed for automated documentation ,please follow structure for your readme.
* Additional files such as static files (images etc.), usually in a _static subdirectory.
* Optionally, a customized theme in a subdirectory called _theme

To generate document, below **gradle** task is available

```
./gradlew sphinx
```

Source document and target directory can be defined in **build.gradle** in below task
```groovy
sphinx {
    sourceDirectory = "${projectDir}/documentation/source"
    outputDirectory = "${projectDir}/documentation/target"
}
```

other supported config can be referenced here : [spinx plugin](https://trustin.github.io/sphinx-gradle-plugin/configuration.html)


### Audit Log

To enable Audit log within service, a custom lbg-audit-log library has been integrated within this template.
```groovy
dependencies {
   
    implementation(libs.lbg.audit.lib)
   
   }
```
Update the below properties in **application.properties** to publish audit logs

```

audit.producer.version=v1.0
audit.producer.name=<producer-name>
audit.publisher.domain=<publisher-domain>
audit.context.correlationID=correlation-id
audit.context.brand=brand
audit.personal_data_category_level_1=BEHAVIOURAL

```

Audit log library provides **AuditPublisher** which publishes logs to configured domain. To consume this library helper class is added in __PACKAGE_PREFIX__.auditlog package as **AuditLoggingHelper** along with properties in starter-template.
**AuditEvent** is build using properties mentioned in **application.properties** then AuditEvent is published to **AuditPublisher** with the help of **AuditLoggingHelper**.

more details can be found here : [Audit log documentation](https://confluence.devops.lloydsbanking.com.mcas.ms/display/PTE/Audit+Log+Library)

### DB changeset application with Liquibase

This feature enables to run changeset files (contains sql script) on spanner DB instance.
In order to use this feature there is an override file that needs to be placed in [liquibase_override](helm-overrides/liquibase-servicename-envName-config.yaml)  **service-name** can be replaces by actual micro service name and **envName** can be replaced with environment where it has to be deployed.

This override file have configuration for spanner database and service account.
Sample Liquibase file: [sample_liquibase-servicename-envName-config.yaml](https://github.com/lbg-gcp-foundation/plt-liquibase/blob/master/helm-overrides/liquibase-plt-spanner-bld-config.yaml)

Along with above configuration changes, add changesets in [liquibase-resources/changesets] folder , changeset file name should have **changelog** name in it.

Changesets refers to application’s DDL and DML sqls.

For each sql script change set id should be present, and it should be unique, other key point is to update **"tag"** version in container argument for every deployment otherwise it won't run new changes.
please refer below page for more details: [Database version control](https://confluence.devops.lloydsbanking.com.mcas.ms/pages/viewpage.action?pageId=663468943)

### Backward compatibility

Jenkins stage has been added to check an alert backward compatibility between API specifications
```
    stage('Backward Compatilbility check') {
        steps {
            container('diffy') {
                sh 'openapi-diff "${liveSpec}" "${candidateSpec}" > report.json || true'
            }
            script {
                openapidiff([diff: readFile(file: 'report.json')])
            }
        }
    }

```

### Canary charts
Canary charts and customized helm templates to support deployment of multiple versions of microservice providing a simple input config file as overrides. Helm templates tags deployments and objects with corresponding labels.
It uses ISTIO virtual services, destination rules, header identification with URL re-writing techniques to share load among extising and new deployment based on tags and labels.

These helm charts and templates are packaged within Starter Template in folder **helm-charts/istio-servicename** and can be overriden with **helm-overrides/istio-service-name-<envName>.yaml**

and deployed using Jenkins stage below
```
       stage('Helm: Istio Deployment') {
            when {
                branch "${LIVE_BRANCH}"
            }
            steps {
                container('infra-tools') {
                    dir('helm-charts') {
                        sh '''
                            echo "****************************** Deploy istio VirtualService & Gateway ****************************"
                            gcloud container clusters get-credentials ${icpClusterName} --region ${icpClusterRegion} --project ${icpProjectId}
                        '''
                        upgradeReleaseV3(
                                chartName: "${istioProjectName}",
                                version: "${version}",
                                namespace: "${namespace}",
                                releaseName: "${istioReleaseName}${SERVICE_VERSION}",
                                options: " --force -f ../helm-overrides/istio-<service-name>-${environmentName}-config.yaml -f ../helm-overrides/release-versions.yaml",
                                credentialsId: "jenkinsPAT"
                        )
                        statusReleaseV3(flags: "", releaseName: "${istioReleaseName}${SERVICE_VERSION}", namespace: "${namespace}")

                    }
                }
            }
        }

```

### Kotlin development support
Kotlin language development support has been added, so folder structure can be followed like:
for java : src->main->java and for kotlin: src->main->kotlin

### Secret Manager Integration

Stater template is already integrated with required spring dependencies required to access secret from GCP Secret Manager.


#### Consuming the secret through Springboot App

To consume secret into project, Secret Manager dependency and properties are already included in template.
Enable the secret manager properties in bootstrap.properties by updating below flags to true
```
spring.cloud.gcp.secretmanager.enabled=true
spring.cloud.gcp.secretmanager.bootstrap.enabled=true
```

Give the secret project_id which you created in secret.
```spring.cloud.gcp.secretmanager.project-id=project_ID```


These properties can also be overriden for each environment variables from helm override file **helm-overrides/service-name-<envName>-config.yaml** under **extraEnvVars**.

To access the value of secret key  in application.properties or helm override file. use below format
- ${sm://projects/project_ID/secrets/secret_key/versions/latest}

Example: "${sm://projects/sbs-kms-bld-01-f72f/secrets/sbs-ctl-bld-nebula-db-password/versions/latest}"

Reference doc for springboot integration:
https://docs.spring.io/spring-cloud-gcp/docs/current/reference/html/secretmanager.html

### Liveness and Readiness probe

This feature is disabled by default in service-name-bld-config.yaml , to make it enable comment
out probe section including livenessEnabled , readinessEnabled in service-name-bld-config.yaml.
Probe configuration properties are available in values.yaml file and these can be overriden as per requirement.

### RTL Releases

Stage to create **Release** tags using shared lib on GCP Platform for RTL deployments is already configured within Jenkins Pipeline.

Below are the configuration to stage
```
stage('Release')
        {
            when {
                branch "${LIVE_BRANCH}"
            }
            steps {
                script {
                    def config = [
                        // Must contain imagename:imagetag - this image will be renamed to the generated git tag (e.g v0.1.0) and pushed back to the registry.
                        image: "${registryUrl}/${registryProjectId}/<workstream>/${projectName}:${env.tag}",
                        // Any folder with Chart.yaml will be released as a tarball by default
                        releaseFileList: ["helm-charts/*/*.yaml","helm-overrides/*.yaml"],
                        // If this is omitted, it will release on all branches
                        releaseBranch: "${LIVE_BRANCH}",
                        workstream: "<workstream>",
                        spinnakerPipeline: "Deploy to int-01",
                        spinnakerApp: "<valuestream>-<workstream>-<service-name>-int",
                        pubSubTopic: "projects/mgmt-spi-prd-597c/topics/spi-pst-<valuestream>-<workstream>",
                        infraToolsSlave: "infra-tools"
                    ]
                    releaseRTLHandler(config)
                }
            }
        }

```

### Starter Template Version Update

This template is continuously updated with common capabilities used within GCP Platform. Hence, release tag are created and maintained when there is new addition.

**Jenkinsfile_release** is used generate these tags. If your microservice is created based on this template, service can be updated tto latest template version with below git commands

```
## Add MS starter template as a remote to your new service repo
git remote add template https://github.com/lbg-gcp-foundation/lbg-starter-template.git

## Fetch the latest template
git fetch template or git fetch --all

## Merge Template into your repo branch 
git merge template/<branch-name> --allow-unrelated-histories

Resolve all merge conflicts if any by verifying against template

```

### BDD testing using cucumber

Cucumber is a Behavior Driven Development tool used to develop test cases for the behavior of software's functionality in automated testing.
This uses simple English text called the Gherkin language for the feature file creation. The featureFileName.feature defines the test scenarios to be tested.
And the respective step definition functions are written in the stepdef java file under stepdef directory.
The IntegrationRunner.java is the runner test which executes the test based on the tags specified and generates the cucumber report at the end of the test execution.

Cucumber task has been added in the build.gradle to execute the tests.
```
task cucumber(type: Test) {
dependsOn assemble, compileTestJava, processTestResources
doLast {
javaexec {
main = "cucumber.api.cli.Main"
classpath = configurations.cucumberRuntime + sourceSets.main.output + sourceSets.test.output
args = ['--plugin', 'pretty', '--plugin', 'json:target/cucumber.json', '--tags', '@Regression', '--glue', 'stepDef', 'src/test/resources/features']

        }
    }
}
```

### How to run the BDD tests
The IntegartionRunner.java class is the BDD test runner which executes the service tests based on the specified tags mentioned in the CucumberOptions.
The Runner class configuration for gradle is
```
:cucumber --tests "com.lbg.bdd.IntegrationRunner"

```

While executing the runner test, it starts the Springboot application initially and the tests will be executed following the application startup.

### BDD dependencies
The dependencies for Cucumber and rest-assured and its versions are specified in the lbg-gradle-catalog repo https://github.com/lbg-gcp-foundation/lbg-gradle-catalog in build.gradle file.
```
version('cucumber_java_version', '1.2.5')
version('cucumber_junit_version', '1.2.6')
version('cucumber_spring_version', '1.2.6')
version('json_version','20090211')
version('rest_assured_version','4.1.2')
version('maven_cucumber_reporting_version', '5.3.0')

/*
gradle dependency for the cucumber.
*/
        library('cucumber-java8','info.cukes', 'cucumber-java8').versionRef('cucumber_java_version')
        library('cucumber-junit','info.cukes', 'cucumber-junit').versionRef('cucumber_junit_version')
        library('cucumber-spring','info.cukes', 'cucumber-spring').versionRef('cucumber_spring_version')
        library('maven-cucumber-reporting','net.masterthought', 'maven-cucumber-reporting').versionRef('maven_cucumber_reporting_version')
        library('rest-bdd','io.rest-assured','rest-assured').versionRef('rest_assured_version')
        library('cucumber-json','org.json','json').versionRef('json_version')

```
Those libraries are published in the 'https://nexus.sbs-bld.oncp.dev/repository/shared-services/' repo.
By specifying the lbg-gradle-catalog latest release tag version in settings.gradle file, the bdd dependencies will be downloaded.
```
dependencyResolutionManagement {
    repositories {
        maven {
            url 'https://nexus.mgmt-bld.oncp.dev/repository/maven-public/'
        }
        maven {
            url 'https://nexus.sbs-bld.oncp.dev/repository/shared-services/'
        }
    }
    versionCatalogs {
        libs {
            **from("com.lbg:lbg-gradle-catalog:1.13.0")**
        }
    }
}

```

### BDD and ZAP Stages
BDD stage stage along with the ZAP scan has been added in the jenkinsfile.

The OWASP Zed Attack Proxy (ZAP) is an easy to use integrated penetration testing tool for finding vulnerabilities in the applications.
zap.yaml file in the pipelines directory defines the active zap scan configuration.
```
stage ("Gradle BDD Test with Active Zap Scan") {
when {
branch "${LIVE_BRANCH}"
}
steps{
script{
container("owasp-zap")  {
runZapProxy()
}
container('gradle-bdd') {
dir('bdd') {
sh '''
echo "*********************** Run Integration Test ***********************************"
gradle cucumber -Denv.type=${environmentName}_app-name -Dtags='@Regression'
'''
junit(allowEmptyResults: true, testResults: '**/test-results/**/*.xml')
}
}
}
container("owasp-zap")  {
runAscan()
runGenerateReport()
}
}
}
post {
always {
dir('bdd'){
echo "*********************** Publish Cucumber Report ***********************************"
cucumber 'bdd/target/cucumber.json'
}
}
}
}
```
### BDD Reports
The tests under the @Regression tag will be executed and the cucumber-reports.html and ZAP test report will be generated in the target folder.


## NOTE:
please make sure everytime update the latest version of sharedlibraries which have been used in jenkinsfile

we are maintaining microservice starter template to be latest ,so we have created a stage in jenkinsfile which helps to validate the latest version of starter template
if found that you are not using the latest version of starter template then we recommend to update the version and pull back changes manually.
