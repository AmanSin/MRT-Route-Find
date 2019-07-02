echo "Starting MRT Route Finder..."

mkdir -p ../logs

if [ -e ../logs/ENGINE.pid ]; then
    echo "Application is already running - ABORTING!!!"
    exit 10
fi

exec java -jar -Dspring.config.location=../src/main/resources/application.properties ../target/MRTRouteFinder-1.0-SNAPSHOT.jar >> ../logs/SysOut.log 2>&1 &

echo $! > ../logs/ENGINE.pid

echo "Starting... Please Wait!"

echo "started MRT Route Finder..."
