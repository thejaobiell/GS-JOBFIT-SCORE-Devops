#!/bin/bash
set -e

DB_NAME="jobfitscore"
DB_USER="rm554874"
DB_PASSWORD="JobfitScore2025#"
DB_PORT="5432"
RESOURCE_GROUP="jobfitscore-rg"
LOCATION="brazilsouth"
ACI_DB_NAME="jobfitscore-db"
POSTGRES_IMAGE="postgres:16-alpine"

echo "Criando resource group..."
az group show --name $RESOURCE_GROUP >/dev/null 2>&1 || \
az group create --name $RESOURCE_GROUP --location $LOCATION

echo "Verificando container PostgreSQL..."
if ! az container show --resource-group $RESOURCE_GROUP --name $ACI_NAME >/dev/null 2>&1; then
  echo "Criando container PostgreSQL..."
  az container create \
    --resource-group $RESOURCE_GROUP \
    --name $ACI_NAME \
    --image $CONTAINER_IMAGE \
    --ports 5432 \
    --os-type Linux \
    --cpu 1 --memory 1.5 \
    --dns-name-label "${ACI_NAME}-dns" \
    --ip-address public \
    --environment-variables \
      POSTGRES_DB="$DB_NAME" \
      POSTGRES_USER="$DB_USER" \
      POSTGRES_PASSWORD="$DB_PASSWORD" \
    --restart-policy Always
fi

echo "Aguardando PostgreSQL disponibilizar IP..."
TIMEOUT=300
ELAPSED=0
DB_IP=""

while [ -z "$DB_IP" ] && [ $ELAPSED -lt $TIMEOUT ]; do
  DB_IP=$(az container show --resource-group $RESOURCE_GROUP --name $ACI_NAME --query ipAddress.ip -o tsv 2>/dev/null || echo "")
  if [ -z "$DB_IP" ]; then
    sleep 5
    ELAPSED=$((ELAPSED + 5))
    echo "Aguardando IP... ($ELAPSED/$TIMEOUT segundos)"
  fi
done

if [ -z "$DB_IP" ]; then
  echo "Erro: Timeout aguardando IP."
  exit 1
fi

echo "IP obtido: $DB_IP"
echo "Validando conectividade com PostgreSQL..."

RETRY=0
while ! nc -z -w 5 $DB_IP 5432 2>/dev/null && [ $RETRY -lt 60 ]; do
  echo "Tentativa $((RETRY + 1))/60..."
  sleep 5
  RETRY=$((RETRY + 1))
done

if [ $RETRY -eq 60 ]; then
  echo "Erro: PostgreSQL não respondeu."
  exit 1
fi

echo "PostgreSQL pronto em $DB_IP:5432"
echo "DB_IP=$DB_IP"
