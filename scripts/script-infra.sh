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

ACR_NAME="jobfitscoreacr"
ACR_SKU="Basic"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

print_step() {
    echo -e "${GREEN}==>${NC} $1"
}

print_error() {
    echo -e "${RED}[ERRO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[AVISO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCESSO]${NC} $1"
}

print_step "Verificando/Criando Resource Group..."
if az group show --name $RESOURCE_GROUP >/dev/null 2>&1; then
    print_success "Resource Group '$RESOURCE_GROUP' já existe"
else
    az group create --name $RESOURCE_GROUP --location $LOCATION
    print_success "Resource Group '$RESOURCE_GROUP' criado"
fi

print_step "Verificando/Criando Azure Container Registry..."
if az acr show --name $ACR_NAME --resource-group $RESOURCE_GROUP >/dev/null 2>&1; then
    print_success "ACR '$ACR_NAME' já existe"
else
    az acr create \
        --resource-group $RESOURCE_GROUP \
        --name $ACR_NAME \
        --sku $ACR_SKU \
        --location $LOCATION \
        --admin-enabled true
    print_success "ACR '$ACR_NAME' criado"
fi

print_step "Obtendo credenciais do ACR..."
ACR_LOGIN_SERVER=$(az acr show --name $ACR_NAME --resource-group $RESOURCE_GROUP --query loginServer -o tsv)
ACR_USERNAME=$(az acr credential show --name $ACR_NAME --resource-group $RESOURCE_GROUP --query username -o tsv)
ACR_PASSWORD=$(az acr credential show --name $ACR_NAME --resource-group $RESOURCE_GROUP --query passwords[0].value -o tsv)

print_success "ACR Login Server: $ACR_LOGIN_SERVER"

print_step "Verificando Container PostgreSQL..."
if az container show --resource-group $RESOURCE_GROUP --name $ACI_DB_NAME >/dev/null 2>&1; then
    print_warning "Container PostgreSQL '$ACI_DB_NAME' já existe. Recriando automaticamente..."

    az container delete \
        --resource-group $RESOURCE_GROUP \
        --name $ACI_DB_NAME \
        --yes

    sleep 5
fi

print_step "Criando Container PostgreSQL..."
az container create \
    --resource-group $RESOURCE_GROUP \
    --name $ACI_DB_NAME \
    --image $POSTGRES_IMAGE \
    --ports $DB_PORT \
    --os-type Linux \
    --cpu 1 --memory 1.5 \
    --dns-name-label "${ACI_DB_NAME}-dns" \
    --ip-address public \
    --environment-variables \
        POSTGRES_DB="$DB_NAME" \
        POSTGRES_USER="$DB_USER" \
        POSTGRES_PASSWORD="$DB_PASSWORD" \
    --restart-policy Always

print_success "Container PostgreSQL criado"

print_step "Aguardando container PostgreSQL inicializar..."
sleep 10

DB_IP=$(az container show --resource-group $RESOURCE_GROUP --name $ACI_DB_NAME --query ipAddress.ip -o tsv)
DB_FQDN=$(az container show --resource-group $RESOURCE_GROUP --name $ACI_DB_NAME --query ipAddress.fqdn -o tsv)

print_step "Configurando permissões do ACR..."

SUBSCRIPTION_ID=$(az account show --query id -o tsv)

echo ""
echo "============================================"
print_success "INFRAESTRUTURA CRIADA COM SUCESSO!"
echo "============================================"
echo ""
echo "Resource Group: $RESOURCE_GROUP ($LOCATION)"
echo ""
echo "PostgreSQL:"
echo "  IP: $DB_IP"
echo "  FQDN: $DB_FQDN"
echo "  Porta: $DB_PORT"
echo "  DB: $DB_NAME"
echo "  Usuário: $DB_USER"
echo ""
echo "Connection String:"
echo "postgresql://$DB_USER:${DB_PASSWORD//#/%23}@$DB_FQDN:$DB_PORT/$DB_NAME"
echo ""
echo "ACR:"
echo "  Nome: $ACR_NAME"
echo "  Login Server: $ACR_LOGIN_SERVER"
echo "  Username: $ACR_USERNAME"
echo "  Password: $ACR_PASSWORD"
echo ""
echo "============================================"