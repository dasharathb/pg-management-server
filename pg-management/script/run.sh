#!/bin/bash
#
# Startup script for a spring boot project
#
# chkconfig: - 84 16
# description: spring boot project

export JAVA_HOME=/opt/app/java/jdk/jdk180
export PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH

# Source function library.
[ -f "/etc/rc.d/init.d/functions" ] && . /etc/rc.d/init.d/functions
[ -z "$JAVA_HOME" -a -x /etc/profile.d/java.sh ] && . /etc/profile.d/java.sh


# the name of the project, will also be used for the jar file, log file, ...
PROJECT_NAME=pg-management
# the user which should run the service
SERVICE_USER=tomcat
# base directory for the spring boot jar
SPRINGBOOTAPP_HOME=/opt/app/pg-management
export SPRINGBOOTAPP_HOME

# the spring boot war-file
SPRINGBOOTAPP_JAR="$SPRINGBOOTAPP_HOME/$PROJECT_NAME.jar"

# java executable for spring boot app, change if you have multiple jdks installed
SPRINGBOOTAPP_JAVA=$JAVA_HOME/bin/java

# spring boot log-file
LOG="$SPRINGBOOTAPP_HOME/$PROJECT_NAME.log"

LOCK="$SPRINGBOOTAPP_HOME/$PROJECT_NAME.lock"

RETVAL=0

pid_of_spring_boot() {
    pgrep -f "java.*$PROJECT_NAME"
}

start() {
    [ -e "$LOG" ] && cnt=`wc -l "$LOG" | awk '{ print $1 }'` || cnt=1

	pid=`pid_of_spring_boot`
	if [ -n "$pid" ]; then
		echo "$PROJECT_NAME [pid $pid] is running..."
        exit 0 
	fi
	if [ -f "$LOCK" ]; then
        echo $"${base} dead but lock file ($LOCK) exist. Remove file and start again"
        exit 2 
	fi
    echo -n $"Starting $PROJECT_NAME: "

    cd "$SPRINGBOOTAPP_HOME"
    #su $SERVICE_USER -c "nohup $SPRINGBOOTAPP_JAVA -jar \"$SPRINGBOOTAPP_JAR\"  >> \"$LOG\" 2>&1 &"
    nohup $SPRINGBOOTAPP_JAVA -jar $SPRINGBOOTAPP_JAR  >> $LOG 2>&1 &

    #while { pid_of_spring_boot > /dev/null ; } &&
    #    ! { tail --lines=+$cnt "$LOG" | grep -q ' Started \S+ in' ; } ; do
    #    sleep 1
    #done
    sleep 1

    pid_of_spring_boot > /dev/null
    RETVAL=$?
    [ $RETVAL = 0 ] && success $"$STRING" || failure $"$STRING"
    echo

    [ $RETVAL = 0 ] && touch "$LOCK"
}

stop() {
    echo -n "Stopping $PROJECT_NAME: "

    pid=`pid_of_spring_boot`
    [ -n "$pid" ] && kill $pid
    RETVAL=$?
    cnt=10
    while [ $RETVAL = 0 -a $cnt -gt 0 ] &&
        { pid_of_spring_boot > /dev/null ; } ; do
            sleep 1
            ((cnt--))
    done

    [ $RETVAL = 0 ] && rm -f "$LOCK"
    [ $RETVAL = 0 ] && success $"$STRING" || failure $"$STRING"
    echo
}

status() {
    pid=`pid_of_spring_boot`
    if [ -n "$pid" ]; then
        echo "$PROJECT_NAME [pid $pid] is already running..."
        return 0
    fi
    if [ -f "$LOCK" ]; then
        echo $"${base} dead but subsys locked"
        return 2
    fi
    echo "$PROJECT_NAME is stopped"
    return 3
}

# See how we were called.
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    restart)
        stop
        start
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}"
        exit 1
esac

exit $RETVAL
