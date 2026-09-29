pipeline {
    agent any

    environment {
        PATH = "C:\\Users\\scuba\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin;${env.PATH}"
        DOCKERHUB_REPO = 'sourini/temperature-converter'
    }

    stages {
        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Code Coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                jacoco()
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKERHUB_REPO}:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Run Docker Image') {
            steps {
                bat "docker run --rm ${DOCKERHUB_REPO}:${env.BUILD_NUMBER}"
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry(
                        'https://index.docker.io/v1/',
                        'dockerhub-sourini'
                    ) {
                        docker.image("${DOCKERHUB_REPO}:${env.BUILD_NUMBER}").push()
                        docker.image("${DOCKERHUB_REPO}:${env.BUILD_NUMBER}").push('latest')
                    }
                }
            }
        }
    }
}