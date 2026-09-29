pipeline {
    agent any

    environment {
        PATH = "C:\\Users\\scuba\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin;${env.PATH}"
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
                bat 'docker build -t sourini/temperature-converter:%BUILD_NUMBER% .'
            }
        }
        stage('Run Docker Image') {
            steps {
                bat 'docker run --rm sourini/temperature-converter:%BUILD_NUMBER%'
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-sourini',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_TOKEN'
                )]) {
                    bat '''
@echo off
powershell -NoProfile -Command "[Console]::Out.Write($env:DOCKER_TOKEN)" | docker login -u "%DOCKER_USER%" --password-stdin
if errorlevel 1 exit /b 1
docker push sourini/temperature-converter:%BUILD_NUMBER%
if errorlevel 1 exit /b 1
docker tag sourini/temperature-converter:%BUILD_NUMBER% sourini/temperature-converter:latest
if errorlevel 1 exit /b 1
docker push sourini/temperature-converter:latest
'''
                }
            }
        }
    }
}