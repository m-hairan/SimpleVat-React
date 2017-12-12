#!/bin/bash

kubectl get replicationcontroller -o=yaml > replicationcontroller.yaml

# Replace name and selfLink 
sed -i "/^  metadata:/,/^  [a-z]/!b;/\(^    name:\|^    selfLink:\)/!b;s/$/-XXXXXXXX/" replicationcontroller.yaml
