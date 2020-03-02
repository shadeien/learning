 ./prometheus --web.listen-address="0.0.0.0:19090" &
du -h --max-depth=1
zkcli -server 192.168.6.21:2181
echo stat|nc 192.168.6.21 2181
docker start e0c8d37863f1  
docker exec -it e0c8d37863f1  /bin/bash
 docker-compose up
docker cp e0c8d37863f1:/gfwlist.txt g:/
redis-cli -h 192.168.6.21 -p 6379

egrep -i -r 'killed process' /var/log
git config --global core.autocrlf input

ssh -i key.pem root@192.168.1.1  

netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}' 
netstat -nap |grep :8009|grep CLOSE_WAIT | awk '{print $7}'|awk -F"\/" '{print $1}' |awk '!a[$1]++'  |xargs kill 