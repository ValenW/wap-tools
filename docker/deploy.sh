#/bin/bash

# docker pull mysql:5.7
# docker pull nodejs:8.9.4
# docker pull maven:3.5

HOST_WORKSPACE=$(pwd)

start_mysql() {
	local container_id=$(
		docker run -p 3310:3306 -d --rm \
		# --net="bridge" \
		# --dns="17.26.131.242" \
		--name wap-tool-db \
        --env MYSQL_ROOT_PASSWORD=worksap \
        --env MYSQL_DATABASE=wap_tools \
        --env MYSQL_USER works \
        --env MYSQL_PASSWORD worksap \
        mysql:5.7
	) local container_ip=$(docker inspect -f {{.NetworkSettings.IPAddress}} ${container_id})
}

start_mysql

# preheat result list
# 16.12.99.0006.20170427.0201tkdinterim25
# 17.03.99.0002.20170719.0221litalicointerim
