pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }   
    }
    stages {
        stage('Configure') {
            version = '1.0.' + env.BUILD_NUMBER
            currentBuild.displayName = version

            properties([
                    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10')),
                    [$class: 'GithubProjectProperty', displayName: '', projectUrlStr: 'https://github.com/miguel-franklin/spring-boot-sample/'],
                    pipelineTriggers([[$class: 'GitHubPushTrigger']])
                ])
        }

        stage('Checkout') {
            git 'https://github.com/miguel-franklin/spring-boot-sample'
        }

        stage('Version') {
            sh "echo \'\ninfo.build.version=\'$version >> src/main/resources/application.properties || true"
            sh "mvn -B -V -U -e versions:set -DnewVersion=$version"
        }

        stage('Build') {
            sh 'mvn -B -V -U -e clean package'
        }

        stage('Archive') {
            junit allowEmptyResults: true, testResults: '**/target/**/TEST*.xml'
        }
    }
}
