<<<<<<< HEAD
pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "MAVEN_HOME"
    }

    stages {
        stage('Clone') {
            steps {
                timeout(time: 2, unit: 'MINUTES'){
                    git branch: 'main', credentialsId: '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', url: 'https://github.com/JhendersonAaron/test.git'
                }
            }
        }
        stage('Build') {
            steps {
                timeout(time: 8, unit: 'MINUTES'){
                    sh "mvn -DskipTests clean package -f pom.xml"
                }
            }
        }
        stage('Test') {
            steps {
                timeout(time: 10, unit: 'MINUTES'){
                    // Se cambia <test> por <install> para que se genere el reporte de jacoco
                    sh "mvn clean install -f pom.xml"
                }
            }
        }
        stage('Sonar') {
            steps {
                timeout(time: 4, unit: 'MINUTES'){
                    withSonarQubeEnv('sonarqube'){
                        sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Pcoverage -f pom.xml"
                    }
                }
            }
        }
        stage('Quality gate') {
            steps {

                sleep(10) //seconds

                timeout(time: 4, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy') {
            steps {
			    timeout(time: 8, unit: 'MINUTES'){
					// Ejecutar mvn spring-boot:run
					echo "mvn spring-boot:run -f pom.xml"
                }			
                //echo "mvn spring-boot:run -f pom.xml"
            }
        }
    }
=======
pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "MAVEN_HOME"
    }

    stages {
        stage('Clone') {
            steps {
                timeout(time: 2, unit: 'MINUTES'){
                    git branch: 'main', credentialsId: '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', url: 'https://github.com/JhendersonAaron/test.git'
                }
            }
        }
        stage('Build') {
            steps {
                timeout(time: 8, unit: 'MINUTES'){
                    sh "mvn -DskipTests clean package -f pom.xml"
                }
            }
        }
        stage('Test') {
            steps {
                timeout(time: 10, unit: 'MINUTES'){
                    // Se cambia <test> por <install> para que se genere el reporte de jacoco
                    sh "mvn clean install -f pom.xml"
                }
            }
        }
        stage('Sonar') {
            steps {
                timeout(time: 4, unit: 'MINUTES'){
                    withSonarQubeEnv('sonarqube'){
                        sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Pcoverage -f pom.xml"
                    }
                }
            }
        }
        stage('Quality gate') {
            steps {

                sleep(10) //seconds

                timeout(time: 4, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy') {
            steps {
			    timeout(time: 8, unit: 'MINUTES'){
					// Ejecutar mvn spring-boot:run
					echo "mvn spring-boot:run -f pom.xml"
                }			
                //echo "mvn spring-boot:run -f pom.xml"
            }
        }
    }
>>>>>>> 631f409 (corregido)
}