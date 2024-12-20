pipeline {
    agent any

    tools {
        maven "Maven"
    }
    environment{
        DOCKER_HUB_REPO = 'anwar721/smartsense'
    }

    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Nom de la branche à construire')
    }

    stages {
        stage('clone repo'){
            steps{
                git branch: params.BRANCH_NAME, url: 'https://github.com/JavaAura/Barj_Badi_Lamtioui_Bouchehboune_Sprint_4_B3_SmartSenseAPI'
            }
        }
        stage('Build Artifact'){
            steps{
                bat '''
                    cd ./smartsense
                    mvn -B -DskipTests clean package 
                '''
                archiveArtifacts artifacts: 'smartsense/target/*.jar', fingerprint: true
                stash name: 'jar-artifact', includes: 'smartsense/target/*.jar'
            }
        }
        
        stage('Docker Build') {
            steps {
                unstash 'jar-artifact' 
                bat "docker build -t %DOCKER_HUB_REPO%:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-token', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        retry(3) {
                            timeout(time: 10, unit: 'MINUTES') {
                                bat """
                                    docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%
                                    docker push %DOCKER_HUB_REPO%:latest || exit 1
                                """
                            }
                        }
                    }
                }
            }
            post {
                failure {
                    echo 'Docker push failed - checking Docker daemon status'
                    bat 'docker info'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sq1') {
                    withCredentials([string(credentialsId: 'sonar-qub', variable: 'SONAR_TOKEN')]) {
                        bat '''
                            cd ./smartsense
                            mvn clean verify sonar:sonar ^
                                -Dsonar.projectKey=smartsense ^
                                -Dsonar.host.url=http://localhost:9000/ ^
                                -Dsonar.token=%SONAR_TOKEN% ^
                                -Dsonar.java.binaries=target/classes ^
                                -Dsonar.coverage.exclusions=**/dto/**,**/model/**,**/config/**
                                -Dsonar.branch.name=main

                        '''
                    }
                }
            }
        }
    }
}