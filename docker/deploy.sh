#!/bin/bashm deploy.sh

if [ $# -lt 1 ]; then
	echo "Usage: $0 ServerName(db/tool/all) [ifRepull(y/n)]"
	exit 1
fi

server=$1
repull=$2
if [ -z "$repull" ]; then
	repull=n
fi

db_docker_name=wap-tool-db
docker_name=wap-tool
host_ips=$(hostname -I)
echo "DB Docker Name is $db_docker_name"
echo "Service Docker Name is $docker_name"

docker pull valen/$docker_name
docker tag valen/$docker_name $docker_name

echo "start docker"

: ${SPRING_PROFILES_ACTIVE:=production}
start_db() {
	echo "Stop db Server"
	docker stop $db_docker_name

	if [ "$repull" == y ]; then
		docker rm $db_docker_name
		echo "Pull db docker image"
		docker pull valen/$db_docker_name
		docker tag valen/$db_docker_name $db_docker_name
	fi

	local ap_container_config_dir=/etc/tomcat
	local container_id=$(
		docker run -p 3306:3306 -d \
		--name $db_docker_name \
		--env MYSQL_ROOT_PASSWORD=worksap \
		--env MYSQL_DATABASE=wap_tools \
		--env MYSQL_USER=works \
		--env MYSQL_PASSWORD=worksap \
		$db_docker_name
	) # local container_ip=$(docker inspect -f {{.NetworkSettings.IPAddress}} ${container_id})
}

start_tool() {
	echo "Stop tool Server"
	docker stop $docker_name

	if [ "$repull" = y ]; then
		docker rm $docker_name
		echo "Pull tool docker image"
		docker pull valen/$docker_name
		docker tag valen/$docker_name $docker_name
	fi
	local container_id=$(
		docker run -p 8811:8089 -d \
		--name=$docker_name \
		--link=$db_docker_name:db \
		$docker_name
	) # local container_ip=$(docker inspect -f {{.NetworkSettings.IPAddress}} ${container_id})
}

case "$server" in
"db") start_db ;;
"tool") start_tool ;;
"all")
	echo "WARN the tool may run before the db is ready"
	start_db
	start_tool
	;;
*)
	echo "Unknow Server"
	exit 2
	;;
esac
