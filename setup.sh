#!/usr/bin/env bash
set -e

function brewInstall () {
echo "Installing $1"
    if brew ls --versions $1 > /dev/null; then
            echo "- $1 already installed"
    else
            brew install $1
    fi
}

CURRENT_DIR=`pwd`
echo "CURRENT_DIR - " $CURRENT_DIR
echo "ANDROID_HOME - " $ANDROID_HOME

[ -z "$JAVA_HOME" ] && echo "JAVA_HOME is NOT SET AS AN ENVIRONMENT VARIABLE. Set it first then rerun the script" && exit 1;

brewInstall node
brewInstall wget
brewInstall carthage
brewInstall ruby
brewInstall ffmpeg
brewInstall mp4box
brewInstall ideviceinstaller
brewInstall ios-webkit-debug-proxy
brewInstall libimobiledevice
brewInstall ios-deploy

if ! [ -d "$ANDROID_HOME" ] ; then
    mkdir -pv ./temp
    DOWNLOADED_ZIP="./temp/tools.zip"
    echo "ANDROID_HOME ($ANDROID_HOME) directory NOT present. Setting it up now"
    sudo mkdir -pv $ANDROID_HOME
    sudo chmod 777 $ANDROID_HOME
    echo "Downloading android sdk"
    rm -f $DOWNLOADED_ZIP 2> /dev/null

    wget https://dl.google.com/android/repository/sdk-tools-darwin-4333796.zip\?hl\=sk -O $DOWNLOADED_ZIP
    unzip $DOWNLOADED_ZIP -d $ANDROID_HOME
    sleep 5
else
    echo "ANDROID_HOME ($ANDROID_HOME) directory present. already set. IF YOU WANT TO REINSTALL, delete directory - $ANDROID_HOME and run the ./setup.sh script again"
fi

echo "Setup android sdk"
cd $ANDROID_HOME/tools
pwd

echo "Installing tools"
./bin/sdkmanager tools
echo "Installing platform-tools"
./bin/sdkmanager platform-tools
echo "Installing platforms;android-28"
./bin/sdkmanager "platforms;android-28"
echo "Installing build-tools;28.0.0"
./bin/sdkmanager "build-tools;28.0.0"

sleep 5
echo "Done installing android sdk"

cd $CURRENT_DIR
pwd
echo "Installed node version"
node -v
echo "Install node modules - appium"
npm install -g appium@1.8.1
sleep 5
echo "Install node modules - appium-doctor"
npm install -g appium-doctor@1.4.3
sleep 5
echo "Run appium-doctor"
appium-doctor
echo "Install Web Driver"
npm install -g wd

echo "PLEASE ENSURE"
echo "-- ANDROID_HOME is set to $ANDROID_HOME"
echo "-- Update PATH:"
echo "---- 'export PATH=\$PATH:\$ANDROID_HOME/platform-tools:\$ANDROID_HOME/tools'"
pwd