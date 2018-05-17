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
if [ "$(uname)" == "Darwin" ]; then
    echo "Running iOS Tests..."
    mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/iOS/apps/onescript.xml -DexecutionType=serverside
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    echo "Running Android Tests..."
    echo “mvn started”
mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/apps/onescript.xml -DexecutionType=serverside
#mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/apps/Prod_Suites/CasualUserFlow.xml -DexecutionType=serverside
#mvn clean test -Dsurefire.suiteXmlFiles=TestSuites/mobile/Android/apps/Prod_Suites/CouponForCasualUser.xml -DexecutionType=serverside

fi
echo "Finished Running Tests!"
#cp target/surefire-reports/junitreports/TEST-*.xml TEST-all.xml
cp target/surefire-reports/TEST-*.xml TEST-all.xml
cp -R target/surefire-reports/* output-files/
}

startAppium
executeTests

