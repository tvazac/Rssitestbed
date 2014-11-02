#!/bin/bash

rssi_app_dir="RssiTestbed"

motes=(
	/dev/ttyUSB0
	/dev/ttyUSB1
)

currentDir=`pwd`

motesCount=2
motesIndex=0

#chmod 666
while [ $motesIndex -lt $motesCount ]; do
	echo -n "chmod 666 to ${motes[$motesIndex]}..."
	sudo chmod 666 ${motes[$motesIndex]}
	echo "[OK]"
	let motesIndex=motesIndex+1
done

#deploy
cd $rssi_app_dir

motesIndex=0
while [ $motesIndex -lt $motesCount ]; do
	echo -n "Deploying to ${motes[$motesIndex]} with ID = $[$motesIndex+1]..."
	make telosb install,$[$motesIndex+1] bsl,${motes[$motesIndex]}
	echo "[OK]"
	let motesIndex=motesIndex+1
done

cd $currentDir
