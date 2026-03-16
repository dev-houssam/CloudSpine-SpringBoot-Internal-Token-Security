#!/bin/bash

openssl genpkey -algorithm RSA -out private.pem
openssl rsa -in private.pem -pubout -out public.pem

kubectl create secret generic gateway-private-key \
  --from-file=private.pem \
  --dry-run=client -o yaml | kubectl apply -f -

kubectl create configmap services-public-key \
  --from-file=public.pem \
  --dry-run=client -o yaml | kubectl apply -f -

kubectl rollout restart deployment services
kubectl rollout restart deployment gateway