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
    --name $ACI_DB_NAME \
    --image $POSTGRES_IMAGE \
    --ports 5432 \
    --os-type Linux \
    --cpu 1 --memory 1.5 \
    --dns-name-label "${ACI_DB_NAME}-dns" \
    --ip-address public \
    --environment-variables \
      POSTGRES_DB="$DB_NAME" \
      POSTGRES_USER="$DB_USER" \
      POSTGRES_PASSWORD="$DB_PASSWORD" \
    --restart-policy Always
fi

echo "Container PostgreSQL rodando em com sucesso!"
echo "Connection string:"
echo "postgresql://rm554874:JobfitScore2025%23@jobfitscore-db-dns.brazilsouth.azurecontainer.io:5432/jobfitscore"