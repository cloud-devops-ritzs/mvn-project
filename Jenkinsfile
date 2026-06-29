pipeline {
    agent any

    environment {
        BRANCH_NAME = "${env.GIT_BRANCH.replaceAll('origin/', '')}"
        CONTAINER_NAME = "${BRANCH_NAME}"   // Q1-2026, Q2-2026, or Q3-2026
        PORT_MAP = ""
    }

    stages {

        stage('Set Port') {
            steps {
                script {
                    if (BRANCH_NAME == 'Q1-2026') {
                        env.PORT_MAP = "8081:80"
                    } else if (BRANCH_NAME == 'Q2-2026') {
                        env.PORT_MAP = "8082:80"
                    } else if (BRANCH_NAME == 'Q3-2026') {
                        env.PORT_MAP = "8083:80"
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Stop Old Container') {
            steps {
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                """
            }
        }

        stage('Deploy httpd Container') {
            steps {
                sh """
                    docker run -d \
                        --name ${CONTAINER_NAME} \
                        -p ${PORT_MAP} \
                        -v \$(pwd):/usr/local/apache2/htdocs/ \
                        httpd:latest
                """
            }
        }

        stage('Verify') {
            steps {
                sh "docker ps | grep ${CONTAINER_NAME}"
            }
        }
    }

    post {
        success {
            echo "Deployed ${CONTAINER_NAME} successfully!"
        }
        failure {
            echo "Deployment failed for ${CONTAINER_NAME}"
        }
    }
}
