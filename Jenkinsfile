#!groovy
/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
/* Used for docker/jib image build and tagging */
library identifier: 'jenkins-sharedlib-bakery-lifecycle@v3.45.4', retriever: modernSCM([$class: 'GitSCMSource',
  remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-bakery-lifecycle.git',
  credentialsId: 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
  /* Used for Zap scan */
library identifier: 'jenkins-sharedlib-zap-scan@v1.0.0', retriever: modernSCM([$class: 'GitSCMSource',
        remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-zap-scan.git',
        credentialsId: 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
/* Used for helm charts deployment */
library identifier: 'jenkins-sharedlib-helm-charts@v1.41.3', retriever : modernSCM([$class: 'GitSCMSource',
 remote : 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-helm-charts.git',
 credentialsId : 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
/* Used for trufflehog scan */
library identifier: 'jenkins-sharedlib-trufflehog@v1.1.0 ', retriever: modernSCM([$class: 'GitSCMSource',
   remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-trufflehog.git',
   credentialsId: 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
/* Used for Sonaqube scan */
library identifier: 'sonarqube@v0.1.1', retriever: modernSCM([$class: 'GitSCMSource',
   remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-sonarQube_multi.git',
   credentialsId: 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
library identifier: 'jenkins-sharedlib-compliance@v1.20.0', retriever: modernSCM([$class: 'GitSCMSource',
  remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-compliance.git',
  credentialsId: 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
/* Used for release creation for RTL */
library identifier: 'jenkins-sharedlib-release@v1.64.0', retriever: modernSCM([$class: 'GitSCMSource',
    remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-release.git',
    credentialsId: 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
/* Used for NexusIq scan */
library identifier: 'jenkins-sharedlib-nexusiq-multi@v0.0.1',retriever: modernSCM([$class: 'GitSCMSource',
  remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-nexusiq-multi.git',
  credentialsId: 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
/* Used for liquibase, feature flag and canary charts */
library identifier: 'jenkins-sharedlib-common@v0.1.7', retriever: modernSCM([$class: 'GitSCMSource',
  remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-common.git',
  credentialsId: 'jenkinsPATCustom'])

/* the git version/tag used to checkout below library is the latest version of master dated March 10 2023 more details are in readme */
/* Used for notification of Jenkins build status */
library identifier: 'jenkins-sharedlib-notifications@v1.6.0', retriever : modernSCM([$class: 'GitSCMSource',
  remote : 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-notifications.git',
  credentialsId: 'jenkinsPATCustom'])

library identifier: 'jenkins-sharedlib-jenkinsinfo@master', retriever: modernSCM([$class: 'GitSCMSource',
  remote: 'https://github.com/lbg-gcp-foundation/jenkins-sharedlib-jenkinsinfo.git',
  credentialsId: 'jenkinsPAT'])

def withSecretEnv(List<Map> varAndPasswordList, Closure closure) {
  wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: varAndPasswordList]) {
    withEnv(varAndPasswordList.collect { "${it.var}=${it.password}" }) {
      closure()
    }
  }
}

properties([
    buildDiscarder(
        logRotator(
            artifactDaysToKeepStr: '5',
            artifactNumToKeepStr: '',
            daysToKeepStr: '3',
            numToKeepStr: '5'
        )
    ),
    disableConcurrentBuilds(),
])

pipeline {

    environment {
        def environmentName = '<environment-name>'
		
        def projectName = '<service-name>'
        def releaseName = '<release-name>'
		def istioProjectName = '<istio-release-name>'
        def istioReleaseName = '<istio-project-name>'
		
		def namespace = "<namespace-name>"
        def version = '<chart-version>'
		
		def projectId = "<gcp-project-id>"
        def workstream = "<workstream>"
		def registryUrl = "eu.gcr.io"
		def registryProjectId= "<GCR GCP project id>"
		def clusterName = '<cluster-name>'
        def clusterRegion = "europe-west2"

        /* Change it to cloudsql for cloudsql db | default is spanner for a multi region setup*/
        def LiquibaseDatabase= 'spanner'
		
		def icpProjectId = "<gcp-icp-project-id>"
        def icpClusterName = "<gcp-icp-cluster-name>"
        def icpClusterRegion = "europe-west2"

        //Backward compatibility check variables
        def specPath = '<path of the spec folder>'
        def liveSpec = '<path of service spec (OAS) already live'
        def candidateSpec = '<path of candidate service spec (OAS) to be live'

    }

    options {
        timestamps()
        ansiColor('xterm')
        skipDefaultCheckout()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '100'))
    }

   /* triggers {
        cron('00 21 * * *')
    }*/

    parameters {
        booleanParam(name: 'FEATUREFLAG_CHART', defaultValue: false, description: 'Run feature-flag chart or not')
        booleanParam(name: 'LIQUIBASE_CHART',   defaultValue: false, description: 'Run liquibase chart or not')
    }

    /*Replace label with respective Domain/Tenants name*/
    agent {
        kubernetes {
            label "<Domain/Tenants>-api-${UUID.randomUUID().toString()}"
            yamlFile "pipelines/podTemplate.yaml"
        }
    }

    stages {
        stage('Workspace clean and Declarative checkout') {
            steps {
                // Cleanup before starting the stage
                cleanWs()
                // Checkout the repository
                checkout scm
		        sh '''
                awk '{ print $1 }' starter_version.txt >> ../version 
                '''
            }
        }

        stage('validate Microservice template version with current version') {
             steps {
                 starterTemplateValidator()
	           }
         }

        stage('README scan') {
		    steps{
                script{
                if(fileExists("README.md")) {
                  println("README file Exists !!!")
           	      readmeScannerValidator()
                }
           	    else error("README.md doesn't exists for this repo. Please add README.md as per template available in Confluence(https://confluence.devops.lloydsbanking.com/display/PTE/Platform+Evolution+%7C+README+template).")
                }
			}
		}

		stage('Environment Setup') {
            steps {
                script {
                     // Use this Commit Message to Skip All Stages if the Commit Message is Chore - i.e a Changelog Update by Jenkins CI after merge
                    env.LAST_COMMIT_MESSAGE = sh (script: 'git log --format=%B -n 1 ${GIT_COMMIT}', returnStdout: true).trim()
                    echo "The LAST_COMMIT_MESSAGE is : ${LAST_COMMIT_MESSAGE}"
                    env.GIT_URL = scm.getUserRemoteConfigs()[0].getUrl()
                    println env.GIT_URL
                    def imageProps = readYaml file : "<path to jib yaml>" //jibImage properties
                    env.LIVE_BRANCH = imageProps.config.liveBranch //Branch to be deployed/released - default should be master
					
					// versions to be deployed for helm release
                    env.SERVICE_VERSION = ""
                    env.DEPLOYMENT_VERSION = ""
                    env.CONFIG_VERSION = ""
                    env.BDD_FAILURE = false
                    dir('helm-overrides') {
                        if (fileExists('release-versions.yaml')) {
                            def props1 = readYaml file: 'release-versions.yaml'
                            env.SERVICE_VERSION = props1.service.version? '-' + props1.service.version : ""
                            env.DEPLOYMENT_VERSION = props1.deployment.version? '-' + props1.deployment.version : ""
                            env.CONFIG_VERSION = props1.config.version? '-' + props1.config.version : ""
                            println env.SERVICE_VERSION
                            println env.DEPLOYMENT_VERSION
                            println env.CONFIG_VERSION
                        }
                    }
                }
            }
        }

       stage('Skip Build for Chore Commit') {
                when {
                    branch "${LIVE_BRANCH}"
                    expression { "${env.LAST_COMMIT_MESSAGE}" =~ "chore: Updated changelog for" }
                }
                steps {
                    echo "************** This build will terminate and not be built as it was triggered by a Chore Commit Message into Master and is a Changelog Update! **************"
                    script {
                        currentBuild.result = 'NOT_BUILT'
                        error("This build will terminate now as it was triggered by a Chore Commit Message!")
                    }
                }
            }

        stage('Initial Check and Scan') {
            parallel {
                stage('Run Repo Scan') {
                    steps {
                        container('trufflehog') {
                            singleRepoScan([branchName: env.BRANCH_NAME])
                        }
                    }
                }

                stage('Validate PR Title changelog') {
                    steps {
                        validatePR([prTitle: env.CHANGE_TITLE])
                    }
                }
				
				stage('Backward Compatilbility check') {
                    steps {
                        dir("${specPath}") {
                            container('diffy') {
                                sh 'openapi-diff "${liveSpec}" "${candidateSpec}" > report.json || true'
                            }
                            script {
                                openapidiff([diff: readFile(file: 'report.json')])
                            }
                        }
                    }
                }
            }
        }

        stage('Sonar and Component Test and Builds with Scan') {
            parallel {
                stage("Sonarqube scan") {
                    steps {
                        container('gradle-jdk') {
							withSonarQubeEnv('SonarQube'){
								runSonar()
								qualityGates()
							}
                        }
                    }
                }

                stage('Unit Test') {
                    steps {
                         container('gradle-jdk') {
                                    echo "*********************** Placeholder for Unit test ***********************************"
                                    //<script to trigger tests>
                                    sh 'gradle test'
                            }
                    }
                    post {
                        success {
                            // Publish Unit Test's Report
                            publishHTML target: [
                                            allowMissing: false,
                                            alwaysLinkToLastBuild: false,
                                            keepAll: true,
                                            reportDir: "${REPORTS_DIR}/tests/test/",
                                            reportFiles: 'index.html',
                                            reportName: 'Unit Test Report'
                                            ]
                        }
                    }
                }


                stage('Component Test') {
                    environment {
                        SPANNER_EMULATOR_HOST = "localhost:9010"
                    }
                    steps {
                        container('gradle-jdk') {
                            echo "*********************** Placeholder for component test ***********************************"
							//<script to trigger tests>
                            sh 'gradle componentTest -x test'
                            archiveArtifacts artifacts: 'build/test-results/**/*.xml'
                        }
                    }
                }

                stage('Build Image') {
                    steps {
                        echo "*********************** Jib Build ***********************************"
                        script {
                            echo "Building container image using Jib..."
                            jibImageBuild([dockerConfig: "pipelines/conf/jibBuild.yaml",
                                           aquaConfig  : "pipelines/conf/jibBuild.yaml"])
                            archiveArtifacts artifacts: "*.html", allowEmptyArchive: true
                        }
                    }
                }

                stage ('Run Nexus IQ') {
                    when {
                        branch "${LIVE_BRANCH}"
                    }
                    steps {
                        container('gradle-jdk') {
                            echo "*********************** Nexus IQ ***********************************"
                            script {
                                runNexusIQ()
                            }
                        }
                    }
                }

                stage('Starting Zap Proxy') {
                    when {
                        branch "${LIVE_BRANCH}"
                    }
                    steps {
                        echo "*********************** Starting ZAP proxy ***********************************"
                        script {
                            container("owasp-zap")  {
                                runZapProxy()
                            }
                        }
                    }
                }
            }
        }

		/* Liquibase Server Configuration and image creation */
		stage('Liquibase: Copy central charts') {
            steps
                {
                    script {
                        container('infra-tools')
                            {
                                def config = [
                                        imageName: "${projectName}"
                                ]
                                  if (LiquibaseDatabase.equals('spanner')) {
                                      releaseLiquibaseSpanner(config)
                                  }
                                  else{
                                        releaseLiquibaseCloudSql(config)
                                  }
                            }
                    }
                }
        }

        /* Liquibase image deployment using helm charts */
        stage('Helm: Liquibase Deployment') {
            when {
                allOf {
                 branch "${LIVE_BRANCH}"
                 /* Deploy liquibase only if branch is 'master' and LIQUIBASE_CHART param is set to true */
                 expression {
                        return params.LIQUIBASE_CHART
                 }
              }
            }
            steps {
                container('infra-tools') {
                    dir('helm-charts') {
                        sh '''
                            echo "****************************** Deploy Liquibase artifact ****************************"
                            gcloud container clusters get-credentials ${clusterName} --region ${clusterRegion} --project ${projectId}
                        '''
                        upgradeReleaseV3(
                                chartType: "liquibase",
                                chartName: "liquibase-${releaseName}",
                                version: "${version}",
                                namespace: "${namespace}",
                                releaseName: "liquibase-${releaseName}",
                                options: "-f ../helm-overrides/liquibase-${releaseName}-${environmentName}-config.yaml",
                                credentialsId: "jenkinsPATCustom")
                        statusReleaseV3(flags: "", releaseName: "liquibase-${releaseName}", namespace: "${namespace}")
                    }
                }
            }
        }

		stage('Helm: Configuration Deployment') {
            when {
                branch "${LIVE_BRANCH}"
            }
            steps {
                container('infra-tools') {
                    dir('helm-charts') {
                        sh '''
                            echo "****************************** Deploy Config artifact ****************************"
                            gcloud container clusters get-credentials ${clusterName} --region ${clusterRegion} --project ${projectId}
                        '''
                        upgradeReleaseV3(
                                chartType: "CONFIG",
                                chartName: "configuration-${projectName}",
                                version: "${version}",
                                namespace: "${namespace}",
                                releaseName: "configuration-${releaseName}${CONFIG_VERSION}",
                                options: " -f ../helm-overrides/configuration-<service-name>-${environmentName}-config.yaml -f ../helm-overrides/release-versions.yaml",
                                credentialsId: "jenkinsPAT"
                        )
                        statusReleaseV3(flags: "", releaseName: "configuration-${releaseName}${CONFIG_VERSION}", namespace: "${namespace}")
                    }
                }
            }
        }

        stage('Helm: Application Deployment') {
            when {
                branch "${LIVE_BRANCH}"
            }
            steps {
                container('infra-tools') {
                    dir('helm-charts') {
                        sh '''
                            echo "****************************** Deploy artifact ****************************"
                            gcloud container clusters get-credentials ${clusterName} --region ${clusterRegion} --project ${projectId}
                        '''
                        upgradeReleaseV3(
                                chartName: "${projectName}",
                                version: "${version}",
                                namespace: "${namespace}",
                                releaseName: "${releaseName}${DEPLOYMENT_VERSION}",
                                options: " --force --set=image.tag=${env.tag} -f ../helm-overrides/<service-name>-${environmentName}-config.yaml -f ../helm-overrides/release-versions.yaml",
                                credentialsId: "jenkinsPAT"
                        )
                        statusReleaseV3(flags: "", releaseName: "${releaseName}${DEPLOYMENT_VERSION}", namespace: "${namespace}")
                    }
                }
            }
        }

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

        stage ('Generate changelog') {
            steps {
                changelogGen([currentBranch: env.BRANCH_NAME])
            }
        }
    }
    post {
		failure {
            script{
                if(env.LIVE_BRANCH == 'master') {
                    if (env.BDD_FAILURE == 'true') {
                        println env.bddFailure
                        def emailConfig = [:]
                        emailConfig.subject = "BDD ${currentBuild.currentResult} | ${environmentName} | ${projectName}"
                        emailConfig.body = "**This is an automated email from Jenkins**\n\n JOB: ${env.JOB_NAME}\n BUILD: ${env.BUILD_NUMBER}\n SERVICE/ARTEFACT NAME: ${projectName}\n ENVIRONMENT: ${environmentName}\n CUCUMBER_REPORT: ${env.BUILD_URL}cucumber-html-reports/overview-features.html\n\n"
                        emailNotify(emailConfig)
                    } else {
                        emailNotify()
                    }
                }
            }
        }

        always {
                    container('infra-tools') {
                        echo "------------------------------------------- Save Jenkins Info - Start -------------------------------------------"
                        script {
                            def config = [infraToolsSlave: "infra-tools"]
                            saveJenkinsInfo(config)
                        }
                        echo "------------------------------------------- Save Jenkins Info - End -------------------------------------------"
                    }
                    echo "------------------------------------------- Workspace Clean - Start -------------------------------------------"
                    cleanWs()
                    dir("${env.WORKSPACE}@libs") {
                        deleteDir()
                    }
                    echo "------------------------------------------- Workspace Clean - End -------------------------------------------"
                }

    }
}
