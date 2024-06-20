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
        echo 'Permissions set.'
      }
    }

    stage('Build') {
      steps {
        sh './gradlew clean build'
        echo 'Build completed.'
      }
    }

    stage('SonarQube analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh './gradlew sonar'
          echo 'SonarQube analysis completed.'
        }
      }
    }

    stage('Quality gate') {
      steps {
        waitForQualityGate abortPipeline: true
        echo 'Quality gate completed.'
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
