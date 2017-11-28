#!/bin/sh

cd front
npm run build
cp dist/index.html ../backend/src/main/resources/static
cp -r dist/static ../backend/src/main/resources/static/vue-admin/
cd ../backend
mvn clean package -Dmaven.test.skip=true