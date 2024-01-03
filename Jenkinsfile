job("registration-app-ci-job") {
  description("This job build the registration-app, upload the code to nexus")

  stage('Checkout project') {
        steps {
            git branch: 'cicd-jenkins',
                credentialsId: 'sriram-ponangi-github-account',
                url: 'git@github.com:sriram-ponangi/registration-app.git'

            sh "ls -lrat"
        }
    }
}
