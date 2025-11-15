#!/bin/bash
set -e

RESOURCE_GROUP="jobfitscore-rg"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

print_warning() {
    echo -e "${YELLOW}[AVISO]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERRO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[✓]${NC} $1"
}

print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

if ! az account show &> /dev/null; then
    print_error "Você não está logado no Azure"
    echo "Execute: az login"
    exit 1
fi

if ! az group show --name $RESOURCE_GROUP &> /dev/null; then
    print_warning "Resource Group '$RESOURCE_GROUP' não existe"
    exit 0
fi

echo ""
echo "============================================"
print_warning "ATENÇÃO: Você está prestes a DELETAR"
echo "============================================"
echo ""
echo "📦 Resource Group: $RESOURCE_GROUP"
echo ""
print_info "Recursos que serão deletados:"

CONTAINERS=$(az container list --resource-group $RESOURCE_GROUP --query "[].name" -o tsv 2>/dev/null)
if [ ! -z "$CONTAINERS" ]; then
    echo ""
    echo "🐳 Containers:"
    echo "$CONTAINERS" | while read container; do
        echo "   - $container"
    done
fi

ACRS=$(az acr list --resource-group $RESOURCE_GROUP --query "[].name" -o tsv 2>/dev/null)
if [ ! -z "$ACRS" ]; then
    echo ""
    echo "📦 Container Registries:"
    echo "$ACRS" | while read acr; do
        echo "   - $acr"
    done
fi

echo ""
print_info "Deletando Resource Group '$RESOURCE_GROUP'..."
print_info "Isso pode levar alguns minutos..."

az group delete \
    --name $RESOURCE_GROUP \
    --yes \
    --no-wait

echo ""
print_success "Comando de deleção enviado!"
print_info "A deleção está acontecendo em background"
echo ""
echo "Para verificar o status:"
echo "   az group show --name $RESOURCE_GROUP"
echo ""