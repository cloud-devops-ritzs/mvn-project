pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-amazon-corretto.x86_64'
        PATH = "${JAVA_HOME}/bin:${PATH}"
        S3_BUCKET = 'ritzs'
        WAR_FILE = 'gameoflife-java21-0.0.1-SNAPSHOT.war'
    }

    stages {

        // ✅ No manual checkout (Jenkins does it automatically)

        stage('Java Check') {
            steps {
                sh 'java -version'
                sh 'javac -version'
                sh 'mvn -version'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
                sh 'ls -ltr target/'
            }
        }

        stage('Upload WAR to S3') {
            steps {
                sh '''
                    aws s3 cp target/$WAR_FILE s3://$S3_BUCKET/
                '''
            }
        }

        stage('Deploy on Slave') {
            agent {
                label 'slave_1'
            }
            steps {
                sh '''
                    # Download WAR from S3
                    aws s3 cp s3://$S3_BUCKET/$WAR_FILE \
                    /mnt/apache-tomcat-10.1.55/webapps/gameoflife.war

                    # Restart Tomcat
                    /mnt/apache-tomcat-10.1.55/bin/shutdown.sh || true
                    sleep 5
                    /mnt/apache-tomcat-10.1.55/bin/startup.sh
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                    echo "Checking application URL..."
                    curl -I http://172.31.47.96:8080/gameoflife || true
                '''
            }
        }
    }
}
