pipeline {

    agent any

    stages {

        stage('GIT') {
            steps {
                echo 'Pulling from Git...'
            }
        }

 stage('docker init'){
            steps {
                sh 'docker compose up -d --scale Spring=0'
            }
        }


         stage('COMPILING') {
                    steps {
                        script {
                            // Clean and install dependencies
                            sh 'mvn clean '
                            sh 'mvn compile'

                        }
                    }
         }



        stage('Test') {
            steps {
                script {
                    sh 'mvn test jacoco:report'
                    sh """
                    mvn sonar:sonar  \
                        -Dsonar.projectKey=gestion-station-ski \
                        -Dsonar.projectName="gestion-station-ski" \
                        -Dsonar.host.url=http://192.168.33.10:9000 \
                        -Dsonar.token=sqa_a3dc702fffd50c6ef472bc87e6f1c79201403339 \
                        -Dsonar.exclusions=**/entities/*.java,**/services/*.java
                    """
                    echo 'Test SonarQube terminé'
                }
            }
        }


         stage('nexus Build') {
                    steps {
                        sh 'mvn package -Dmaven.build.finalName=gestion-station-ski'
                    }
         }

         stage('docker Build') {
                     steps {
                         sh 'docker build -t walidmarzouk/gestion-station-ski:1.0 .'
                           }
                                }

         stage('Deploy ') {
                     steps {
                         sh 'mvn deploy'
                         sh 'docker compose up -d Spring'
                     }
                 }


        stage('Push to Docker Hub') {
            steps {
                script {
                    def dockerHubUsername = 'waliiidmrz'
                    def dockerHubPassword = '201JMT3068w@'
                    sh "docker login -u ${dockerHubUsername} -p ${dockerHubPassword}"
                    sh "docker tag walid-devops ${dockerHubUsername}/walid-devops:latest"
                    sh "docker push ${dockerHubUsername}/walid-devops:latest"
                }
            }
        }




    }
}
