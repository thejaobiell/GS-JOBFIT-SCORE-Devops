#!/bin/bash
set -e

# Se as variáveis vierem da pipeline, ótimo.
# Se não vierem (rodando local), elas são setadas aqui.
export DB_NAME="${DB_NAME:-jobfitscore}"
export DB_USER="${DB_USER:-rm554874}"
export DB_PASSWORD="${DB_PASSWORD:-JobfitScore2025#}"
export DB_PORT="${DB_PORT:-5432}"

RESOURCE_GROUP="jobfitscore-rg"
LOCATION="brazilsouth"
ACI_DB_NAME="jobfitscore-db"
POSTGRES_IMAGE="postgres:16-alpine"

echo "Usando variáveis:"
echo "DB_NAME=$DB_NAME"
echo "DB_USER=$DB_USER"
echo "DB_PORT=$DB_PORT"

echo "Criando Resource Group..."
az group show --name $RESOURCE_GROUP >/dev/null 2>&1 || \
az group create --name $RESOURCE_GROUP --location $LOCATION

echo "Criando Container PostgreSQL..."
if ! az container show --resource-group $RESOURCE_GROUP --name $ACI_DB_NAME >/dev/null 2>&1; then
  az container create \
    --resource-group $RESOURCE_GROUP \
    --name $ACI_DB_NAME \
    --image $POSTGRES_IMAGE \
    --os-type Linux \
    --ports $DB_PORT \
    --dns-name-label $ACI_DB_NAME \
    --environment-variables \
      POSTGRES_DB=$DB_NAME \
      POSTGRES_USER=$DB_USER \
      POSTGRES_PASSWORD=$DB_PASSWORD \
    --cpu 1 \
    --memory 1.5 \
    --ip-address public \
    --restart-policy Always
else
  echo "Container já existe."
fi

echo "Infraestrutura criada com sucesso."
echo "Detalhes do Container:"
az container show --resource-group $RESOURCE_GROUP --name $ACI_DB_NAME --output table
