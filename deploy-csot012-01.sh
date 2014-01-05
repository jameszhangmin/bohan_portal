#!/bin/bash

export SERVER_NUMBER=01
export DAO_JAR_LOCATION_HOST=csot004
export DAO_JAR_LOCATION_USER=game
export SERVICE_JAR_LOCATION_HOST=csot004
export SERVICE_JAR_LOCATION_USER=game
export APP_LOCATION_HOST=csot012
export APP_LOCATION_USER=root
export APP_BIN=/usr/local/play-2.2.1/webapps/bohan_portal$SERVER_NUMBER
export SVN_FOLDER=/home/game/git/bohan/bohan_portal

echo '==============================================='
echo 'ENV = PRODUCTION'
echo 'app location: '$APP_BIN
echo 'DAO jar location: ' $DAO_JAR_LOCATION_HOST
echo 'SERVICE jar location: ' $SERVICE_JAR_LOCATION_HOST
echo '==============================================='


echo stop service

export pid=`ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "sed -n 1p $APP_BIN/RUNNING_PID"`
if [ $? -ne 0 ]; then
   exit 1
fi


echo 'running pid: '$pid


echo 'kill '$pid
ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "kill $pid"
if [ $? -ne 0 ]; then
    echo 'kill pid='$pid' failed. continue...'
fi

echo 'rm old bin folder: ssh '$APP_LOCATION_USER@$APP_LOCATION_HOST' "rm -rf '$APP_BIN'"'
#ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "rm -rf $APP_BIN"
ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "rm -rf $APP_BIN;mkdir -p $APP_BIN"

echo 'scp new bin foler: scp -rf '$SVN_FOLDER ' '$APP_LOCATION_USER'@'$APP_LOCATION_HOST':'$APP_BIN
#scp -r $SVN_FOLDER $APP_LOCATION_USER@$APP_LOCATION_HOST:$APP_BIN
cd $SVN_FOLDER
scp -r $(ls | grep -v -e .git) $APP_LOCATION_USER@$APP_LOCATION_HOST:$APP_BIN

echo 'copy dao'
scp /home/game/git/bohan/bohan_dao/bohan/bohan-dao/target/bohan-dao-0.0.1-SNAPSHOT.jar $APP_LOCATION_USER@$APP_LOCATION_HOST:$APP_BIN/lib/

echo 'copy service'
scp /home/game/git/bohan/bohan_dao/bohan/bohan-srv/target/bohan-srv-0.0.1-SNAPSHOT.jar $APP_LOCATION_USER@$APP_LOCATION_HOST:$APP_BIN/lib/

echo 'rm -rf '$APP_BIN'/deploy*'
ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "rm -rf $APP_BIN/deploy*"
echo 'rm -rf '$APP_BIN'/start*'
ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "rm -rf $APP_BIN/start*"
echo 'rm -rf '$APP_BIN'/.git*'
ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "rm -rf $APP_BIN/.git*"
echo 'rm -rf '$APP_BIN'/log4j*'
ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "rm -rf $APP_BIN/log4j*"

echo 'find '$APP_BIN'/ -name .svn | xargs rm -rf'
ssh $APP_LOCATION_USER@$APP_LOCATION_HOST "find $APP_BIN/ -name .svn | xargs rm -rf"


echo 'scp -r '$SVN_FOLDER/'log4j_'$SERVER_NUMBER'.properties '$APP_LOCATION_USER'@'$APP_LOCATION_HOST':'$APP_BIN'/conf/log4j.properties'
scp -r $SVN_FOLDER/log4j_$SERVER_NUMBER.properties $APP_LOCATION_USER@$APP_LOCATION_HOST:$APP_BIN/conf/log4j.properties


echo 'scp -r '$SVN_FOLDER/'start'$SERVER_NUMBER'.sh '$APP_LOCATION_USER'@'$APP_LOCATION_HOST':'$APP_BIN'/start.sh'
scp -r $SVN_FOLDER/start$SERVER_NUMBER.sh $APP_LOCATION_USER@$APP_LOCATION_HOST:$APP_BIN/start.sh

echo 'Please go to '$APP_LOCATION_HOST' to start service, (0), ssh '$APP_LOCATION_USER'@'$APP_LOCATION_HOST'  (1), cd '$APP_BIN'  (2), run ./start.sh'

