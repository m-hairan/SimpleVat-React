#!/bin/bash

kubectl get replicationcontroller -o=yaml > replicationcontroller.yaml

# Replace name and selfLink 
sed -i "/^  metadata:/,/^  [a-z]/!b;/\(^    name:\|^    selfLink:\)/!b;s/$/-XXXXXXXX/" replicationcontroller.yaml


# Replace metadata app
sed -i "/^  metadata:/,/^  [a-z]/!b;/^    labels:/,/^    [a-z]/!b;/^      app:/!b;s/$/-XXXXXXXX/" replicationcontroller.yaml


# Replace selector app
sed -i "/^  spec:/,/^  [a-z]/!b;/^    selector:/,/^    [a-z]/!b;/^      app:/!b;s/$/-XXXXXXXX/" replicationcontroller.yaml
