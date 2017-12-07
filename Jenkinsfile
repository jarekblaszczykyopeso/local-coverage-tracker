pipeline {
   agent{
      docker(image 'maven:3.5.2-jdk-8') {
   }
   stages{
      stage('Build') {
         // Run the maven build
         sh "mvn clean verify -Dapplication.version.number=${BUILD_NUMBER} -Dapplication.version.git_branch=${BRANCH_NAME} -Dapplication.version.git_hash=${GIT_COMMIT}"
         archiveArtifacts 'target/*.jar'
      }
      stage('Publish tests') {
         junit 'target/surefire-reports/*.xml'
         jacoco(execPattern: 'target/jacoco-unit/jacoco.exec')
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