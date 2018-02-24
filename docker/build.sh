#!/bin/sh

cd ../front
npm run build
cp dist/index.html ../backend/src/main/resources/static/index.html
cp -r dist/static ../backend/src/main/resources/static/vue-admin/
cd ../backend
mvn clean package -Dmaven.test.skip=true
cp src/main/resources/db/hsqldb/schema.sql ../docker/db/init.sql
cp target/wap-tools-0.0.1-SNAPSHOT.jar ../docker

cd ../docker
docker build -t wap-tool-db ./db
docker build -t wap-tool .
