pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('github-token')
    }

    stages {
        stage('Clone sources') {
            steps {
                git url: 'https://github.com/Kobila49/environmental_sustainability_tracker.git', credentialsId: 'github-token'
            }
        }

        stage('Set permissions') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('SonarQube analysis') {
                    environment {
                        SONAR_TOKEN = credentials('sonar-token')
                    }
                    steps {
                        withSonarQubeEnv('SonarQube') {
                            sh './gradlew sonar -Dsonar.token=$SONAR_TOKEN'
                        }
                    }
                }

        stage('Quality gate') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully.'
        }
        failure {
            echo 'Pipeline execution failed.'
        }
    }
}
