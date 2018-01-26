#!/usr/bin/env bash

mvn clean package
jars=$(find . -name '*jar-with-dependencies.jar')
for jar in ${jars}; do
    new_name=$(echo ${jar} | sed 's/-1.0-SNAPSHOT-jar-with-dependencies//')
    mv -v ${jar} ${new_name}
done

extra_jars=$(find . -name '*1.0-SNAPSHOT.jar')
for jar in ${extra_jars}; do
    rm -v ${jar}
done

rm -v jabber-test.zip
find . -name 'jabber-*.jar' | zip -j jabber-test -@
zip -g jabber-test.zip xmpp.png
