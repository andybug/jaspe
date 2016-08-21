#!/bin/sh

VOLUME=jaspe-m2-repo
WORKDIR=/usr/local/src/jaspe

docker volume create --name $VOLUME
docker run -it --rm \
       -v "$PWD:$WORKDIR" \
       -v "$VOLUME:/root/.m2" \
       -w "$WORKDIR" \
       maven \
       /usr/bin/mvn package
