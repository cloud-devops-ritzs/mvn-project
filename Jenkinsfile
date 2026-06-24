pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-amazon-corretto.x86_64'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: https://github.com/cloud-devops-ritzs/mvn-project.git'
            }
        }

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

        stage('UPLOAD-WAR-TO-S3') {
            steps {
                sh '''
                    aws s3 cp /mnt/Jenkins-Mavem/target/gameoflife-java21-0.0.1-SNAPSHOT.war \
                    s3://ritzs/
                '''
            }
        }

        stage('DEPLOY-ON-SLAVE') {
            agent {
                label 'slave-1'
            }
            steps {
                sh '''
                    aws s3 cp \
                    s3://ritzs/gameoflife-java21-0.0.1-SNAPSHOT.war \
                    /mnt/apache-tomcat-10.1.55/webapps/gameoflife.war
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    scp -o StrictHostKeyChecking=no \
                    target/gameoflife-java21-0.0.1-SNAPSHOT.war \
                    root@172.31.47.96:/mnt/apache-tomcat-10.1.56/webapps/gameoflife.war
                '''
            }
        }
    }
}
