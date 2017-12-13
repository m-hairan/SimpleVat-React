#!/bin/bash
echo " Executing kubectl get replicationcontrollers --namespace=simplevat-dev > rc.txt"
kubectl get replicationcontrollers --namespace=simplevat-dev > rc.txt

i=0
echo "Upgrading replication controllers" 
while read line 
do
  if [ $i != 0 ]
  then
    rc=($line)
    domain=${rc:0:-4}
    echo "Upgrading Replication Controller for $domain"
    
    if [ $domain == "alpha" ]
    then

      ./simplevat-new-release-script.bash "$domain" dev "$1"
      
      echo "Replication Controller for $domain upgraded"

    fi

  fi
  i=$((i+1))
done < rc.txt

echo "All Replication Controller Successfully."
