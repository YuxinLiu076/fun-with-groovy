def call(Map config=[:]) {
    node {
        stage('SCM') {
            echo 'Gathering code from version control'
            git branch: '${branch}', url: 'git@github.com:YuxinLiu076/fun-with-groovy.git', credentialsId: 'bd5c49a9-f1af-4a08-a079-fdd389446d19'
        }
        stage('Build') {
            try {
                echo 'Building....'
                sh 'dotnet --version'
                sh 'dotnet build ConsoleApp'
                releasenotes(changes: "true")
            } catch (ex) {
                echo 'Something went wrong'
                echo ex.toString()
                currentBuild.result = 'FAILURE'
            } finally {
//             clean up
            }
        }
        stage('Test') {
            echo 'Testing....'
        }
        stage('Deploy') {
            echo 'Deploying....'
        }
    }
}