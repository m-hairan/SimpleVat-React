#!/bin/bash

kubectl get replicationcontroller > rc.txt

i=0
while read line 
do
  if [ $i != 0 ]
  then
    rc=($line)
    domain=${rc:0:-4}
    echo "$domain"
    
    if [ $domain == "alpha" ]
    then

      ./simplevat-new-release-script.bash "$domain" dev "$1"

    fi

  fi
  i=$((i+1))
done < rc.txt
