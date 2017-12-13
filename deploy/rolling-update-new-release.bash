#!/bin/bash

# If there is no current context, get one.
if [[ $(kubectl config current-context 2> /dev/null) == "" ]]; then
    cluster=$(gcloud config get-value container/cluster 2> /dev/null)
    zone=$(gcloud config get-value compute/zone 2> /dev/null)
    project=$(gcloud config get-value core/project 2> /dev/null)

    function var_usage() {
        cat <<EOF
No cluster is set. To set the cluster (and the zone where it is found), set the environment variables
  CLOUDSDK_COMPUTE_ZONE=<cluster zone>
  CLOUDSDK_CONTAINER_CLUSTER=<cluster name>
EOF
        exit 1
    }

    [[ -z "$cluster" ]] && var_usage
    [[ -z "$zone" ]] && var_usage

    echo "Running: gcloud container clusters get-credentials --project=\"$project\" --zone=\"$zone\" \"$cluster\""
    gcloud container clusters get-credentials --project="$project" --zone="$zone" "$cluster" || exit
fi

echo "Executing rolling-update-new-release.bash for release $@"

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
