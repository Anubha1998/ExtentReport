pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Install dependencies') {
            steps {
                sh 'mvn clean install'
            }
        }
        
        stage('Run tests') {
            steps {
                sh 'mvn test -D suite=single.xml'
            }
        }
    }
}
