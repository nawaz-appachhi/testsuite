#!/bin/bash

startAppium(){
if [ "$(uname)" == "Darwin" ]; then
startAppiumOSX
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
startAppiumLinux
else
echo "Unknown OS system, exiting..."
exit 1
fi
}

startAppiumOSX(){
if [ -z ${UDID} ] ; then
export UDID=${IOS_UDID}
fi
echo "UDID is ${UDID}"
# Create the screenshots directory, if it doesn't exist'
mkdir -p .screenshots
echo "Starting Appium on Mac..."
export AUTOMATION_NAME="XCUITest"
appium-1.6 -U ${UDID}  --log-timestamp
}

startAppiumLinux(){
# Create the screenshots directory, if it doesn't exist'
#adb shell ps | grep com.android.commands.monkey | awk '{print $2}' | xargs adb shell kill -9
#adb shell ime set com.google.android.googlequicksearchbox/com.google.android.voicesearch.ime.VoiceInputMethodService
#trap 'adb shell ime set com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME' EXIT
mkdir -p .screenshots
echo "Starting Appium on Linux..."
appium-1.7 --log-no-colors --log-timestamp
}

executeTests(){
echo "Extracting tests.zip..."
unzip tests.zip

var0=$(ps -ef | grep appium)
echo "server thread = $var0"
var1=$(ps -ef | grep appium | grep -v grep | tr -s ' ' '|' | cut -d'|' -f2)
echo "server port = $var1"

killAppiumServer $var1 & 
	
if [ "$(uname)" == "Darwin" ]; then
    echo "Running iOS Tests..."
    #mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/iOS/apps/onescript.xml -DexecutionType=serverside
		mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/MobileWeb/CasualUserFlow.xml -DexecutionType=serverside
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    echo "Running Android Tests..."
    echo “mvn started”
#mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/apps/onescript.xml -DexecutionType=serverside

#mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/apps/Prod_Suites/CouponWithCC_DC_Part2.xml -DexecutionType=serverside
#mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/apps/Prod_Suites/PaymentWithCOD_Part1.xml -DexecutionType=serverside
#mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/apps/Prod_Suites/WishlistWithCOD_Part2.xml -DexecutionType=serverside

	mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/MobileWeb/CasualUserFlow.xml -DexecutionType=serverside

fi
echo "Finished Running Tests!"
#cp target/surefire-reports/junitreports/TEST-*.xml TEST-all.xml
cp target/surefire-reports/TEST-*.xml TEST-all.xml
cp -R target/surefire-reports/* output-files/
}

killAppiumServer(){
	echo "killAppiumServer timestamp before=$(date +%T)"
	sleep 560
	echo "killAppiumServer timestamp after sleep=$(date +%T)"
	var2=$(kill -9 $*)
	echo "server killed $var2"
	echo "killAppiumServer timestamp after killing the server=$(date +%T)"
}

startAppium
executeTests

