#!/bin/bash
# New Subdomain deployment
today=$(date +"%Y%m%d%H%M")
#exec 3>&1 4>&2
#trap 'exec 2>&4 1>&3' 0 1 2 3
#exec 1>"${0:0:-3}_$1_$today.log" 2>&1
# Everything below will go to the file 'log.out':

if [ "$#" -ne 4 ]; then
  echo "Invalid number of arguments"
  exit 1
fi

set -e

echo "Setting contianer credentials"
gcloud beta container clusters get-credentials simplevat-web-app --region europe-west1 --project simplevat-web-app

SUB_DOMAIN="$(sed 's/\(-rep-.*\|-rep$\)//' <<< "$1")"
NAMESPACE_NAME=$2
RELEASE_TAG=$3
OLD_REPLICATION_CONTROLLER_NAME="$1"
REPLICATION_CONTROLLER_NAME="$SUB_DOMAIN-rep"
REPLICATION_CONTROLLER_LABEL="$SUB_DOMAIN-rep"
TOMCAT_CONTAINER_NAME="simplevat-web"
SIMPLEVAT_DB_NAME="$SUB_DOMAIN"_"$NAMESPACE_NAME"
BACKEND_SERVICE_NAME="$SUB_DOMAIN-serv"
BACKEND_SERVICE_LABEL="$SUB_DOMAIN-serv"
SIMPLEVAT_TOKEN=$4
SIMPLEVAT_SUBDOMAIN="$SUB_DOMAIN"
echo "Started to deploy subdomain: $SUB_DOMAIN"

echo "Copying configuration template files"

kubectl get replicationcontroller  --namespace=simplevat-dev -o yaml > ./deploy/rc.yaml

echo "Configuring application deployment yaml"

echo "Set SUB-DOMAIN                        : $SUB_DOMAIN"
echo "Set NAMESPACE-NAME                    : $NAMESPACE_NAME"
echo "Set RELEASE-TAG                       : $RELEASE_TAG"
echo "Set OLD_REPLICATION_CONTROLLER_NAME   : $OLD_REPLICATION_CONTROLLER_NAME"
echo "Set REPLICATION-CONTROLLER-NAME       : $REPLICATION_CONTROLLER_NAME"
echo "Set REPLICATION-CONTROLLER-LABEL      : $REPLICATION_CONTROLLER_LABEL"
echo "Set TOMCAT-CONTAINER-NAME             : $TOMCAT_CONTAINER_NAME"
echo "Set SIMPLEVAT-DB-NAME                 : $SIMPLEVAT_DB_NAME"
echo "Set BACKEND-SERVICE-NAME              : $BACKEND_SERVICE_NAME"
echo "Set BACKEND-SERVICE-LABEL             : $BACKEND_SERVICE_LABEL"
echo "Set SIMPLEVAT-TOKEN                   : $SIMPLEVAT_TOKEN"
echo "Set SIMPLEVAT-SUBDOMAIN               : $SIMPLEVAT_SUBDOMAIN"

sed -i '/- name: SIMPLEVAT_RELEASE/{n;s/value: .*/value: ABC/}' ./deploy/rc.yaml


echo "Rolling-update replicationcontroller: $OLD_REPLICATION_CONTROLLER_NAME with $REPLICATION_CONTROLLER_NAME-$RELEASE_TAG"
echo "Executing : kubectl rolling-update $OLD_REPLICATION_CONTROLLER_NAME -f ./deploy/rc.yaml"
kubectl rolling-update "$OLD_REPLICATION_CONTROLLER_NAME" "$REPLICATION_CONTROLLER_NAME-$RELEASE_TAG" --image=gcr.io/simplevat-dev/simplevat:"$RELEASE_TAG"

echo "Rolling Upgrade complete"
