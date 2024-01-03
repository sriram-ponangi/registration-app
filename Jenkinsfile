pipeline {
    agent any

    environment {
        GITHUB_SSH_CREDENTIALS =  'sriram-ponangi-github-account'
        GITHUB_REPO_SSH_URL = 'git@github.com:sriram-ponangi/registration-app.git'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Checkout the GitHub repository using SSH URL and credentials
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/cicd-jenkins']], // Specify the branch you want to checkout
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [[$class: 'CloneOption', depth: 1, noTags: true, reference: '', shallow: true]],
                        submoduleCfg: [],
                        userRemoteConfigs: [[credentialsId: env.GITHUB_SSH_CREDENTIALS, url: env.GITHUB_REPO_SSH_URL]]
                    ])

                    sh "ls -lrta"
                }
            }
        }
    }
}
