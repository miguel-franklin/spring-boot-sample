pipeline {
    environment {        
        registryCredential = 'dockerregistry'
    }
    agent {
        dockerfile {
            filename 'Dockerfile.build'
            args '-v /tmp:/tmp -v /root/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock'
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
                sh "docker build -f Dockerfile --build-arg JAR_FILE=target/spring-boot-sample.jar -t miguelfranklin/sample-$version ."
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
            when { tag "release-*" }
            steps {
                withDockerRegistry([ credentialsId: "docker-registry", url: "" ]) {
                    sh "docker push miguelfranklin/sample-$version"                
                }
            }
        }
    }
}
