IMAGE_NAME=imageinator
JAR_NAME=executable.jar
PORT=8080

jar:
	./gradlew shadowJar

build: jar
	docker build -t $(IMAGE_NAME) .

run:
	docker run -d -p $(PORT):$(PORT) --name $(IMAGE_NAME) $(IMAGE_NAME)

stop:
	docker rm -f $(IMAGE_NAME)

rebuild: stop build run

logs:
	docker logs -f $(IMAGE_NAME)

clean:
	./gradlew clean