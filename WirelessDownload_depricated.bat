@echo off
REM Use this Batch file to wirelessly conenct to to Android Phones through a Router.
REM This assumes that you can connected your phones to the router, but that the
REM IP addresses may change each time they connect (ie: you can't lock them in).  
REM So to allow for this, you need to look at the phones while this scirp is running,
REM and enter each IP address when prompted.
REM For more help, go to www.YouTube.com/user/GEARSinc/playlists
REM
echo --  Starting ADB
adb kill-server
echo --  Make sure the phone is connected to the computer via USB
set /p ok= --  Hit enter when phone is plugged in:
adb usb
Timeout 10
adb tcpip 5556
echo --
echo --  Make sure your Code phone ALSO is connected to the "Robotics Intranet" network.
pause
adb connect 192.168.1.76:5556
adb devices
set /p ok= --  Unplug the phone and hit Enter to see the final connection.
adb connect 192.168.1.76:5556
adb devices
pause