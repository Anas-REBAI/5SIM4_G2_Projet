pipeline {

    agent any

    stages {

        stage('GIT') {
            steps {
                echo 'Pulling from Git...'
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

                            sh """
                            mvn sonar:sonar  \
                            -Dsonar.projectKey=gestion-station-ski \
                            -Dsonar.projectName="gestion-station-ski" \
                            -Dsonar.host.url=http://192.168.33.10:9000 \
                            -Dsonar.token=sqa_a3dc702fffd50c6ef472bc87e6f1c79201403339
                            """
                            echo 'Test SonarQube terminé'


                                 }
                             }
         }





    }

}
