#! /bin/bash

# refs: http://blog.csdn.net/xinaml/article/details/73176480
cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
echo "Asia/Shanghai" > /etc/timezone
echo "set timezone success"
