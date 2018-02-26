#!/bin/bashtart-mysql.sh

start_db() {
	container_name=mysql

	# backup data before stop
	docker exec $container_name /usr/bin/mysqldump -u works --password=worksap --all-databases > backup/backup-$(date +"%Y-%m-%d-%H%M%S").sql

	docker stop $container_name;
	docker rm $container_name;

	docker run -d --name $container_name -p 3306:3306 \
				-v "$HOME"/data/mysql:/var/lib/mysql \	# store the data in host pc
				-v "$HOME"/docker/utils:/init/ \		# where the init-timezone.sh in
				--env MYSQL_ROOT_PASSWORD=worksap \
				--env MYSQL_DATABASE=wap_tools \
				--env MYSQL_USER=works \
				--env MYSQL_PASSWORD=worksap \
				mysql:5.7

	docker exec -it $container_name /bin/bash /init/init-timezone.sh
}

start_tool() {
	container_name=wap-tools
	host_ip=$(hostname -I | cut -f1 -d' ')
	config_dir=$(pwd)/config

	echo "deploy $container_name"

	docker stop $container_name
	docker rm $container_name

	start_docker() {
		docker run -p 8811:8080 -d \
			--name=$container_name \
			# --add-host=db:"${host_ip}" \		# use --add-host or --link to link the server and db
			--link=mysql:db \
			-v "$HOME/docker/utils:/init/" \	# where the init-timezone.sh in
			-v "$config_dir:/config" \			# where the application.properties in
			wap-tools
		docker exec -it $container_name /bin/bash /init/init-timezone.sh
	}
}

# http://localhost:8811/#/links
