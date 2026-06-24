pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-amazon-corretto.x86_64'
        MAVEN_HOME = '/mnt/build_tool/apache-maven-3.9.16'
        PATH = "${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${PATH}"
        S3_BUCKET = 'ritzs'
        WAR_FILE = 'gameoflife-java21-0.0.1-SNAPSHOT.war'
    }

    stages {

        stage('Java Check') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Debug Workspace') {
            steps {
                sh 'pwd'
                sh 'ls -ltr'
            }
        }

        stage('Build') {
            steps {
                dir('mvn-project') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Upload WAR to S3') {
            steps {
                sh 'aws s3 cp mvn-project/target/$WAR_FILE s3://$S3_BUCKET/'
            }
        }

        stage('Deploy on Slave') {
            agent {
                label 'slave_1'
            }
            steps {
                sh '''
                    aws s3 cp s3://$S3_BUCKET/$WAR_FILE \
                    /mnt/apache-tomcat-10.1.55/webapps/gameoflife.war

                    /mnt/apache-tomcat-10.1.55/bin/shutdown.sh || true
                    sleep 5
                    /mnt/apache-tomcat-10.1.55/bin/startup.sh
                '''
            }
        }
    }
}
