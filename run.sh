#!/bin/sh

echo -e "\033[34m"
echo " ____   ___    _       ____                              _  _   
/ ___| / _ \  / \     / ___|_ __ ___  _   _ _ __   ___  | || |  
\___ \| | | |/ _ \   | |  _| '__/ _ \| | | | '_ \ / _ \ | || |_ 
 ___) | |_| / ___ \  | |_| | | | (_) | |_| | |_) |  __/ |__   _|
|____/ \___/_/   \_\  \____|_|  \___/ \__,_| .__/ \___|    |_|  
                                           |_|                  
"                                                                
echo -e "\033[0m"

echo "running ..."
echo "pensez à avoir vivant le container travel-manager ou travel-manager2 a la fin du run pour voir les messages échoués être relancer!"
i=0
until [ ${i} -eq 20  ]; do
    ./sendInput.sh ./input/input1.json
    ./sendInput.sh ./input/input2.json
    ./sendInput.sh ./input/input3.json

    sleep 3s

    ./sendInput.sh ./input/noFlight.json
    ./sendInput.sh ./input/noCar.json
    ./sendInput.sh ./input/noHotel.json

    sleep 2s

    ./sendInput.sh ./input/input1.json
    ./sendInput.sh ./input/input2.json
    ./sendInput.sh ./input/input3.json
    ./sendInput.sh ./input/noFlight.json
    ./sendInput.sh ./input/noCar.json
    ./sendInput.sh ./input/noHotel.json

    sleep 5s

    ./sendInput.sh ./input/inputUnexisting.json

    sleep 5s

    ./sendInput.sh ./input/inputFail.json

    sleep 1s

    ./sendInput.sh ./input/expense.json
    ./sendInput.sh ./input/expense1.json
    ./sendInput.sh ./input/expense2.json
    ./sendInput.sh ./input/expense3.json

    i=$((i+1))
done

echo ""
echo "all done"
