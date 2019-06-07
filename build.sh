
mvn clean package -U -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dmaven.source.skip=true
docker build -t jump-api . --label="name=jump-api"
docker run -d jump-api