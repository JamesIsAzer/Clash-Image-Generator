IMAGE_NAME=imageinator
JAR_NAME=executable.jar
PORT=8080

jar:
	./gradlew shadowJar

build: jar
	docker build -t $(IMAGE_NAME) .

run:
	mkdir -p logs
	docker run -d -p $(PORT):$(PORT) --name $(IMAGE_NAME) -v $(PWD)/logs:/app/logs $(IMAGE_NAME)

stop:
	docker rm -f $(IMAGE_NAME)

rebuild: stop build run

logs:
	docker logs -f $(IMAGE_NAME)

clean:
	./gradlew clean