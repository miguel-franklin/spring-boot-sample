pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }   
    }
    stages {
        stage('Configure') {
            steps {
                script {
                    version = '1.0.' + env.BUILD_NUMBER
                    currentBuild.displayName = version
                }
            }
        }

        stage('Checkout') {
            steps {
                git 'https://github.com/miguel-franklin/spring-boot-sample'
            }
        }

        stage('Version') {
            steps {
                sh "echo \'\ninfo.build.version=\'$version >> src/main/resources/application.properties || true"
                sh "mvn -B -V -U -e versions:set -DnewVersion=$version"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -V -U -e clean package'
            }
        }

        stage('Archive') {
            steps {
                junit allowEmptyResults: true, testResults: '**/target/**/TEST*.xml'
            }
        }
    }
}
