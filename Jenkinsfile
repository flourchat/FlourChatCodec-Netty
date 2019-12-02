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
                sh 'mvn deploy -Dregistry=https://maven.pkg.github.com/flourchat -Dtoken=$GITHUB_PUBLISH_TOKEN'
            }
        }

    }
    
    post {
        always {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}
