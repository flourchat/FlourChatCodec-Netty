pipeline {
    agent any
    
    stages {
        stage('Checking git') {
            steps {
                scmSkip(deleteBuild: true)
            }
        }
        stage('Maven build') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn deploy'
            }
        }

    }
    
    post {
        always {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}
