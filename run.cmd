@echo off
echo "Node.JS Version" > cmdperf.log
node --version >> cmdperf.log
rem node build\js\node_modules\kfsm-samples\kotlin\kfsm-samples.js
node build\compileSync\js\main\productionExecutable\kotlin\kfsm-samples.js >> cmdperf.log
echo "JVM Version" >> cmdperf.log
java -jar build\libs\kfsm-samples-fat-LOCAL-SNAPSHOT.jar >> cmdperf.log
more cmdperf.log
