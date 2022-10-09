#!/bin/bash

rm -rf /tmp/game_frontend
rm -r ./src/main/resources/public

git clone git@github.com:SvenKuhlmann/rps_frontend.git /tmp/game_frontend
cd /tmp/game_frontend && npm install && ng build --source-map && cd -
mkdir src/main/resources/public
cp /tmp/game_frontend/dist/game_frontend/* ./src/main/resources/public