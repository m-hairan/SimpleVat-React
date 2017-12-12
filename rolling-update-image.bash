#!/bin/bash

kubectl get replicationcontroller -o=yaml > replicationcontroller.yaml

# Replace name and selfLink 
sed -i "/^  metadata:/,/^  [a-z]/!b;/\(^    name:\|^    selfLink:\)/!b;s/$/-XXXXXXXX/" replicationcontroller.yaml


# Replace metadata app
sed -i "/^  metadata:/,/^  [a-z]/!b;/^    labels:/,/^    [a-z]/!b;/^      app:/!b;s/$/-XXXXXXXX/" replicationcontroller.yaml


# Replace selector app
sed -i "/^  spec:/,/^  [a-z]/!b;/^    selector:/,/^    [a-z]/!b;/^      app:/!b;s/$/-XXXXXXXX/" replicationcontroller.yaml

# Replace app
sed -i "/^  spec:/,/^  [a-z]/!b;/^    template:/,/^    [a-z]/!b;/^      metadata:/,/^      [a-z]/!b;/^        labels:/,/^        [a-z]/!b;/^          app:/!b;s/$/-XXXXXXXX/" replicationcontroller.yaml

#Update Image
sed -i "s/          image: gcr.io\/simplevat-cluster\/simplevat.*/          image: gcr.io\/simplevat-cluster\/simplevat:$1/g" replicationcontroller.yaml

# Replace release  
sed -i "/^        containers:/,/^        [a-z]/!b;/^        - env:/,/^          [a-z]/!b;/^          - name: SIMPLEVAT_RELEASE/!b;n;s/^            value:.*/            value: $1/" replicationcontroller.yaml
