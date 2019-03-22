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
        stage('Version') {
            steps {
                sh "echo \'\ninfo.build.version=\'$version >> src/main/resources/application.properties || true"
                sh "mvn -B -V -e versions:set -DnewVersion=$version"
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -V -DskipTests -e clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Archive') {
            steps {
                junit allowEmptyResults: true, testResults: '**/target/**/TEST*.xml'
            }
        }
        stage('Deploy') {
            when { tag "release-*"
            steps {
                sh 'docker build -t miguel-franklin/sample-$version .'
                sh 'docker push miguel-franklin/sample-$version'
            }
        }
    }
}
