./gradlew clean
./gradlew core:assemble
./gradlew stories-ui:assemble
./gradlew processor:shadowJar
./gradlew publish