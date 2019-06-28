

#操作/项目路径(Dockerfile存放的路劲)
BASE_PATH=/data/jump_jump
#docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=jump-api
#容器id
CID=$(docker ps | grep "$SERVER_NAME" | awk '{print $1}')
#镜像id
IID=$(docker images | grep "$SERVER_NAME" | awk '{print $3}')
 
DATE=`date +%Y%m%d%H%M`
 
# 备份jar包
function backup(){
	if [ -f "$BASE_PATH/$SERVER_NAME.jar" ]; then
    		echo "$SERVER_NAME.jar 备份..."
	       		cp $BASE_PATH/$SERVER_NAME.jar $BASE_PATH/backup/$SERVER_NAME-$DATE.jar
	        echo "备份 $SERVER_NAME.jar 完成"
	else
    		echo "$BASE_PATH/$SERVER_NAME.jar不存在，跳过备份"
	fi
}
 
# 构建docker镜像
function build(){
	if [ -n "$IID" ]; then
		echo "存在$SERVER_NAME镜像，IID=$IID，重新构建镜像"
			cd $BASE_PATH
			docker build -t $SERVER_NAME .
	else
		echo "不存在$SERVER_NAME镜像，开始构建镜像"
			cd $BASE_PATH
			docker build -t $SERVER_NAME .
	fi
}
 
# 运行docker容器
function run(){
	backup
	build
	if [ -n "$CID" ]; then
		echo "存在$SERVER_NAME容器，CID=$CID,重启docker容器 ..."
			docker stop $SERVER_NAME
			docker rm $SERVER_NAME
			docker run --name $SERVER_NAME -v $BASE_PATH:$BASE_PATH -d -p 9004:9004 $SERVER_NAME
		echo "$SERVER_NAME容器重启完成"
	else
		echo "不存在$SERVER_NAME容器，docker run创建容器..."
			docker run --name $SERVER_NAME -v $BASE_PATH:$BASE_PATH -d -p 9004:9004 $SERVER_NAME
		echo "$SERVER_NAME容器创建完成"
	fi
}
 
#入口
run



