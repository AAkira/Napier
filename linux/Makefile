.PHONY: run-docker
run-docker:
	docker build -t napier .
	docker run napier

.PHONY: run-local
run-local:
	mkdir -p build
	clang ./work/napier.c -o ./build/napier
	./build/napier

.PHONY: build
build:
	cd .. && \
	./gradlew :mpp-sample:linkDebugSharedLinuxX64 && \
	cp -r mpp-sample/build/bin/linuxX64/debugShared/ linux/work
