#!/usr/bin/env bash

targets="
MetaCoin/MetaCoin
"

for target in ${targets}; do
    dirName=$(dirname $target)
    fileName=$(basename $target)

    cd $dirName
    echo "Generating chain3j bindings"
    chain3j truffle generate \
        build/contracts/${fileName}.json \
        -p org.chain3j.generated \
        -o ../../../../../../integration-tests/src/test/java/ > /dev/null
    echo "Complete"

    cd -
done
