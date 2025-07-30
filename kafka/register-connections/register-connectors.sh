#!/bin/bash

CONNECT_URL="http://localhost:8083/connectors"

echo "Waiting for Kafka Connect REST API to be available..."
until curl -s $CONNECT_URL > /dev/null; do
  sleep 5
done

echo "Registering MySQL source connector..."
curl -X POST -H "Content-Type: application/json" --data @/mysql-connector.json $CONNECT_URL || echo "MySQL connector already registered"

echo "Registering Elasticsearch sink connector..."
curl -X POST -H "Content-Type: application/json" --data @/es-sink.json $CONNECT_URL || echo "Elasticsearch sink connector already registered"

echo "Connector registration completed."
