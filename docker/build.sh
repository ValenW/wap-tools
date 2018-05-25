#!/bin/sh

echo "build front-end resources and copy to backend directory"
cd ../front
npm run build
mkdir -p ../backend/src/main/resources/static
mkdir -p ../backend/src/main/resources/static/vue-admin/
cp dist/index.html ../backend/src/main/resources/static/index.html
cp -r dist/static ../backend/src/main/resources/static/vue-admin/

echo "build and package backend"
cd ../backend
mvn clean package -Dmaven.test.skip=true
# cp src/main/resources/db/hsqldb/schema.sql ../docker/db/init.sql
cp target/wap-tools-0.0.1-SNAPSHOT.jar ../docker

echo "docker build"
cd ../docker
# docker build -t wap-tool-db ./db
docker build -t xianzhon/wap-tools .
docker push xianzhon/wap-tools
