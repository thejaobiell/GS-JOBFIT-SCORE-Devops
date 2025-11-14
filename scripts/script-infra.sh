#!/bin/bash
set -e

RESOURCE_GROUP="jobfitscore-rg"
LOCATION="brazilsouth"

ACR_NAME="jobfitscoreacr"
ACI_DB_NAME="jobfitscore-db"
ACI_APP_NAME="jobfitscore-app"

POSTGRES_IMAGE="postgres:16-alpine"

POSTGRES_DB=$POSTGRES_DB
POSTGRES_USER=$POSTGRES_USER
POSTGRES_PASSWORD=$POSTGRES_PASSWORD
POSTGRES_PORT=$POSTGRES_PORT

echo "Criando Resource Group..."
az group show --name $RESOURCE_GROUP >/dev/null 2>&1 || \
az group create --name $RESOURCE_GROUP --location $LOCATION

echo "Criando Azure Container Registry..."
if ! az acr show --name $ACR_NAME --resource-group $RESOURCE_GROUP >/dev/null 2>&1; then
  az acr create \
    --resource-group $RESOURCE_GROUP \
    --name $ACR_NAME \
    --sku Basic \
    --admin-enabled true
fi

echo "Criando Container do PostgreSQL..."
if ! az container show --resource-group $RESOURCE_GROUP --name $ACI_DB_NAME >/dev/null 2>&1; then
  az container create \
    --resource-group $RESOURCE_GROUP \
    --name $ACI_DB_NAME \
    --image $POSTGRES_IMAGE \
    --ports 5432 \
    --dns-name-label "${ACI_DB_NAME}" \
    --environment-variables \
      POSTGRES_DB=$POSTGRES_DB \
      POSTGRES_USER=$POSTGRES_USER \
      POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
    --cpu 1 --memory 1.5 \
    --ip-address public \
    --restart-policy Always
fi

echo "Infraestrutura criada com sucesso."
