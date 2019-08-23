node("slave"){
    ansiColor('xterm') {
        stage 'Checkout'
        checkout scm

        stage 'Test'

            docker.image('maven:3.5.0-jdk-7-alpine').inside {
                    withMaven() {
                        sh "mvn clean package"
                    }
                }
	}
}