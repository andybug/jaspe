FROM maven
ARG UID
ARG GID
RUN groupadd -g $GID maven
RUN useradd -m -s /bin/bash -u $UID -g $GID maven
USER maven
WORKDIR /usr/local/src/jaspe
CMD ["/usr/bin/mvn", "package"]
