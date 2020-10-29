#! /bin/bash

sudo tmux new-session -d -s app_session "./gradlew bootRun"