#!/bin/bash
# New Subdomain deployment
today=$(date +"%Y%m%d%H%M")
#exec 3>&1 4>&2
#trap 'exec 2>&4 1>&3' 0 1 2 3
#exec 1>"${0:0:-3}_$1_$today.log" 2>&1
# Everything below will go to the file 'log.out':

if [ "$#" -ne 3 ]; then
  echo "Invalid number of arguments"
  exit 1
fi

set -e

echo "Setting contianer credentials"
gcloud container clusters get-credentials simplevat-prod-cluster --zone asia-south1-b --project simplevat-cluster

SUB_DOMAIN=$1
NAMESPACE_NAME=$2
RELEASE_TAG=$3
REPLICATION_CONTROLLER_NAME="$SUB_DOMAIN-rep"
REPLICATION_CONTROLLER_LABEL="$SUB_DOMAIN-rep"
TOMCAT_CONTAINER_NAME="simplevat-web"
SIMPLEVAT_DB_NAME=$SUB_DOMAIN
BACKEND_SERVICE_NAME="$SUB_DOMAIN-serv"
BACKEND_SERVICE_LABEL="$SUB_DOMAIN-serv"
echo "Started to deploy subdomain: $SUB_DOMAIN"

echo "Copying configuration template files"
cp ./deploy/simplevat-service-template.yaml ./"simplevat-service-deploy-$SUB_DOMAIN-$today.yaml"
cp ./deploy/simplevat-replicationcontroller-template.yaml ./"simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml"

echo "Configuring application deployment yaml" 
echo "Set REPLICATION-CONTROLLER-NAME   : $REPLICATION_CONTROLLER_NAME"
echo "Set REPLICATION-CONTROLLER-LABEL  : $REPLICATION_CONTROLLER_LABEL"
echo "Set TOMCAT-CONTAINER-NAME         : $TOMCAT_CONTAINER_NAME"
echo "Set SIMPLEVAT-DB-NAME             : $SIMPLEVAT_DB_NAME"
echo "Set BACKEND-SERVICE-NAME          : $BACKEND_SERVICE_NAME"
echo "Set BACKEND-SERVICE-LABEL         : $BACKEND_SERVICE_LABEL"
echo "Set NAMESPACE-NAME                : $NAMESPACE_NAME"
echo "Set RELEASE-TAG                   : $RELEASE_TAG"

sed -i "s/{RELEASE-TAG}/${RELEASE_TAG}/g" "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml"
sed -i "s/{REPLICATION-CONTROLLER-NAME}/${REPLICATION_CONTROLLER_NAME}-${RELEASE_TAG}/g" "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml" 
sed -i "s/{REPLICATION-CONTROLLER-LABEL}/${REPLICATION_CONTROLLER_LABEL}-${RELEASE_TAG}/g" "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml" 
sed -i "s/{TOMCAT-CONTAINER-NAME}/${TOMCAT_CONTAINER_NAME}/g" "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml" 
sed -i "s/{SIMPLEVAT-DB-NAME}/${SIMPLEVAT_DB_NAME}/g" "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml" 
sed -i "s/{NAMESPACE-NAME}/${NAMESPACE_NAME}/g" "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml"
echo "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml ready"

sed -i "s/{BACKEND-SERVICE-NAME}/${BACKEND_SERVICE_NAME}/g" "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml" 
sed -i "s/{BACKEND-SERVICE-LABEL}/${BACKEND_SERVICE_LABEL}/g" "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml"
sed -i "s/{REPLICATION-CONTROLLER-NAME}/${REPLICATION_CONTROLLER_NAME}-${RELEASE_TAG}/g" "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml" 
sed -i "s/{NAMESPACE-NAME}/${NAMESPACE_NAME}/g" "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml"
echo "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml ready" 

echo "Rolling-update replicationcontroller: $REPLICATION_CONTROLLER_NAME with $REPLICATION_CONTROLLER_NAME-$RELEASE_TAG" 
kubectl rolling-update "$REPLICATION_CONTROLLER_NAME" -f "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml" --namespace=simplevat-dev
echo "Creating service: $BACKEND_SERVICE_NAME" 
kubectl apply -f "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml" --namespace=simplevat-dev

sed -i "s/${REPLICATION_CONTROLLER_NAME}-${RELEASE_TAG}/${REPLICATION_CONTROLLER_NAME}/g" "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml"
sed -i "s/${REPLICATION_CONTROLLER_NAME}-${RELEASE_TAG}/${REPLICATION_CONTROLLER_NAME}/g" "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml" 

echo "Restoring replication controller and service names"

kubectl rolling-update "$REPLICATION_CONTROLLER_NAME-$RELEASE_TAG" -f "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml" --namespace=simplevat-dev
kubectl apply -f "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml" --namespace=simplevat-dev

echo "Rolling Upgrade complete" 

mkdir -p ./deploy/archive-deployment
mv "simplevat-service-deploy-$SUB_DOMAIN-$today.yaml" ./deploy/archive-deployment/
mv "simplevat-replicationcontroller-rolling-update-$SUB_DOMAIN-$today.yaml" ./deploy/archive-deployment/
echo "Configuration files archived"
echo "Application deployment sccess full at http://$SUB_DOMAIN.app.simplevat.com"
#mv "${0:0:-3}_$1_$today.log" ./deploy/archive-deployment/
