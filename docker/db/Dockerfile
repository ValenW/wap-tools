FROM mysql:5.7

ENV MYSQL_DATABASE=wap_tools \
    MYSQL_ROOT_PASSWORD=worksap \
    MYSQL_USER=works \
    MYSQL_PASSWORD=worksap

ADD init.sql /docker-entrypoint-initdb.d

EXPOSE 3306