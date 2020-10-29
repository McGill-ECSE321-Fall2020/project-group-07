#! /bin/bash

tmux new-session -d -s app_session "SERVER_PORT=8080 ./gradlew bootRun"