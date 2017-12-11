pipeline {
   agent {
      docker {
         image 'maven:3.5.2-jdk-8'
         args '-v $HOME/.m2:/root/.m2'
       }
    }
//          docker.image('maven:3.5.2-jdk-8').withRun("-v /home/jenkins/.m2:/root/.m2")
   stages{
      stage('Build') {
         steps {
            // Run the maven build
            sh "mvn clean verify -Dapplication.version.number=${BUILD_NUMBER} -Dapplication.version.git_branch=${BRANCH_NAME} -Dapplication.version.git_hash=${GIT_COMMIT}"
            archiveArtifacts 'target/*.jar'
          }
       }
       stage('Publish tests') {
          steps {
             junit 'target/surefire-reports/*.xml'
             jacoco(execPattern: 'target/jacoco-unit/jacoco.exec')
             sh "pwd"
             sh "ls"
          }
       }
    }
}
//docker {
//   insideContainer('maven:3.5.2-jdk-8') {
//      stage('Build') {
//         // Run the maven build
//         sh "mvn clean verify -Dapplication.version.number=${BUILD_NUMBER} -Dapplication.version.git_branch=${BRANCH_NAME} -Dapplication.version.git_hash=${GIT_COMMIT}"
//         archiveArtifacts 'target/*.jar'
//      }
//      stage('Publish tests') {
//         junit 'target/surefire-reports/*.xml'
//         jacoco(execPattern: 'target/jacoco-unit/jacoco.exec')
//      }
//   }
//}