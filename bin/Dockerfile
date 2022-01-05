FROM ubuntu

COPY . /app
WORKDIR /app

RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get -y install openjdk-8-jdk

ENTRYPOINT ["/bin/bash", "start.sh"]
