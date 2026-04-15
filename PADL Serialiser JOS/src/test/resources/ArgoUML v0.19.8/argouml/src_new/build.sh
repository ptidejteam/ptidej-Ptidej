#! /bin/sh
# $Id: build.sh,v 1.11 2005/09/13 17:07:19 linus Exp $
#

# OS specific support.
darwin=false

case "`uname`" in
		Darwin*) darwin=true;;
esac

# +-------------------------------------------------------------------------+
# | Verify and Set Required Environment Variables                           |
# +-------------------------------------------------------------------------+
if [ "$JAVA_HOME" = "" ] ; then
	if $darwin; then
		# Set Java Home automatically
        JAVA_HOME=/Library/Java/Home
		export JAVA_HOME
	else
		echo "***************************************************************"
		echo "  ERROR: JAVA_HOME environment variable not found."
		echo ""
		echo "  Please set JAVA_HOME to the Java JDK installation directory."
		echo "***************************************************************"
		exit 1
	fi
fi

#
# build.sh always calls the version of ant distributed with ArgoUML
#
ANT_HOME=../tools/ant-1.6.2

echo ANT_HOME is: $ANT_HOME
echo
echo Starting Ant...
echo

$ANT_HOME/bin/ant $*

#exit
