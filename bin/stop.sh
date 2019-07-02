echo "stopping App"

if [ -e ../logs/ENGINE.pid ]; then
    PID_FILE=../logs/ENGINE.pid
else
    echo "App is already stopped - ABORTING!!!"
    exit $ERROR_CODE
fi

if [ -e ../logs/ENGINE.pid ]; then
    {
            read OPID
    } < $PID_FILE
fi

PID=`ps -e | grep $OPID | sed -e 's/^  *//' -e 's/ .*//'`

if [ "${PID}" = "" ]; then
	rm $PID_FILE
    echo "App is not running."
    exit $ERROR_CODE
fi

kill $PID

LOOP=1
while [ $LOOP != 10 ]
do
    PID=`ps -e | grep $OPID | sed -e 's/^  *//' -e 's/ .*//'`
    if [ "${PID}" = "" ]; then
        break
    fi
    LOOP=$(($LOOP + 1))
    sleep 5
done

PID=`ps -e | grep $OPID | sed -e 's/^  *//' -e 's/ .*//'`
if [ "${PID}" = "" ]; then
    rm $PID_FILE
    echo "App is stopped."

    exit 0
else
    echo "App is still running; run stop script again to keep waiting. To force kill the process (may cause data lost), run: kill -9 $PID"
    exit $ERROR_CODE
fi

exit 0
