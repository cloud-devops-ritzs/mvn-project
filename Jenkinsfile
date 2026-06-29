pipeline {

    agent any

    parameters {
        choice(
            name: 'BRANCH',
            choices: ['q1', 'q2', 'q3'],
            description: 'Select branch to deploy'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                script {
                    git branch: "${params.BRANCH}",
                    url: 'https://github.com/cloud-devops-ritzs/mvn-project.git'
                }
            }
        }

        stage('Create HTTPD') {
            steps {
                sh '''
                docker rm -f httpd-container || true

                docker run -d \
                --name httpd-container \
                -p 8085:80 \
                httpd
                '''
            }
        }

        stage('Deploy HTML') {
            steps {
                sh '''
                docker cp index.html httpd-container:/usr/local/apache2/htdocs/index.html
                docker exec httpd-container chmod 644 /usr/local/apache2/htdocs/index.html
                '''
            }
        }
    }
}
