#!/bin/bash 

container_name=wap-tools
host_ips=$(hostname -I)
config_dir=$(pwd)

echo "re-deploy $container_name"
docker stop $container_name
docker rm $container_name

start_docker() {
		docker run -p 8080:8080 -d \
		--name=$container_name \
    --add-host=db:"${host_ips#* }" \
    -v "$config_dir:/config" \
		xianzhon/wap-tools

    docker exec -it $container_name /bin/bash /config/init-timezone.sh
}

start_docker
# http://localhost:8811/#/links
