#!/usr/bin/env bash
# source ~/.nvm/nvm.sh
echo "Node Version" | tee perf.log
node --version| tee perf.log
/usr/bin/time --output=node-perf.log -v node --max-old-space-size=15 ./build/compileSync/js/main/productionExecutable/kotlin/kfsm-samples.js $* | tee -a perf.log
if [[ -f ./build/native/kfsm-samples ]]
then
    echo "Graal Native Image Version" | tee -a perf.log
    /usr/bin/time --output=graal-native-perf.log -v ./build/native/kfsm-samples -Xmx36m $* | tee -a perf.log
fi
echo "JVM Version" | tee -a perf.log
java $JVM_OPT -version | tee -a perf.log
echo "JVM Interpreted" | tee -a perf.log
/usr/bin/time --output=jvm-int-perf.log -v java  -Xmx36m -Xint -jar ./build/libs/kfsm-samples-fat-LOCAL-SNAPSHOT.jar $* | tee -a perf.log
echo "JVM JIT" | tee -a perf.log
/usr/bin/time --output=jvm-jit-perf.log -v java $JVM_OPT -Xmx36m -jar ./build/libs/kfsm-samples-fat-LOCAL-SNAPSHOT.jar $* | tee -a perf.log
