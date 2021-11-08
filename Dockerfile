FROM openjdk:11-jre-slim

ENV spring.profiles.active=prod
ENV code-grade.moss.user-id=954905329
ENV code-grade.storage.root-path=./storage

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "codegrade.plagiarismchecker.PlagiarismCheckerApplication"]

