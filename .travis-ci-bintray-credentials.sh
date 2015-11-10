#!/bin/bash
mkdir /home/travis/.bintray/
FILE=/home/travis/.bintray/.credentials
cat <<EOF >$FILE
realm = Bintray API Realm
host = api.bintray.com
user = $BINTRAY_USER
password = $BINTRAY_KEY
EOF
echo "***************** Creating bintray credentials"
echo $BINTRAY_USER
echo "Created ~/.bintray/.credentials file: Here it is: "
ls -la $FILE