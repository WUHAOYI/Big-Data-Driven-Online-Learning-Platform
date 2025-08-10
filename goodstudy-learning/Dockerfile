FROM openjdk:17-jdk-alpine

COPY ./target/goodstudy-learning-1.0.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch goodstudy-learning-1.0.0.jar'

ENTRYPOINT ["java", "-jar", "goodstudy-learning-1.0.0.jar"]