#!/bin/bash
lein cljsbuild auto > lein.log &
tail -f lein.log|nodemon server.js
