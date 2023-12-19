PID=$1
echo "PID: $PID"
if [ -z "$PID" ]; then
    echo "PID is empty"
    exit 1
fi

echo "Starting server"
cd /game/bin
echo -n '' > /game/stdin
chown -R game:game /game/bin
echo -n 'Get shreked' > /game/stdout
nohup tail -f -s1 /game/stdin 2>&1 | /opt/java/openjdk-18.0.2/bin/java  -Dfile.encoding=UTF-8 -Duser.timezone=Europe/Warsaw -Xms10000M -Xmx10000M -XX:MaxMetaspaceSize=3000M -Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2 -XX:+ParallelRefProcEnabled -XX:+UnlockExperimentalVMOptions -XX:+DisableExplicitGC -XX:-OmitStackTraceInFastThrow -XX:+AlwaysPreTouch -XX:+OptimizeStringConcat   -jar server.jar  nogui > /game/stdout 2>&1 &
echo "Server started"
echo "Killing PID: $PID"
kill -9 $PID
echo "killed"
