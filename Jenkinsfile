pipeline {
    agent any

    tools {
        maven 'maven3'
    }

    environment {
        DOCKERHUB_REPO = 'sourini/temperature-converter'
    }

    stages {
        stage('Test and Build') {
            steps {
                bat 'mvn clean verify'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit 'target/surefire-reports/*.xml'
                jacoco()
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(
                        "${DOCKERHUB_REPO}:${BUILD_NUMBER}"
                    )
                }
            }
        }

        stage('Run Docker Image') {
            steps {
                bat """
                    docker run --rm ^
                    -e DISPLAY=host.docker.internal:0.0 ^
                    ${DOCKERHUB_REPO}:${BUILD_NUMBER}
                """
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry(
                        'https://index.docker.io/v1/',
                        'dockerhub'
                    ) {
                        docker.image(
                            "${DOCKERHUB_REPO}:${BUILD_NUMBER}"
                        ).push()

                        docker.image(
                            "${DOCKERHUB_REPO}:${BUILD_NUMBER}"
                        ).push('latest')
                    }
                }
            }
        }
    }
}