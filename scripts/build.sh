#!/bin/sh

WORKDIR=/usr/local/src/jaspe

docker run -it --rm -v $PWD:$WORKDIR -v $PWD/.m2:/home/maven/.m2 --name builder jaspe-builder
