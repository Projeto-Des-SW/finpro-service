#!/bin/bash
set -e

echo "🚀 Deploy FinPro Backend"

# Configurações fixas
PROJECT_ID="finpro-tayan-1757194118"
REGION="southamerica-east1"
SERVICE_NAME="finpro-service"
DB_INSTANCE="finpro-db"
DB_NAME="finpro"
DB_USER="postgres"
DB_PASS="finpro2025"

echo "📋 Projeto: $PROJECT_ID"
echo "🌎 Região: $REGION"

# 1. Definir projeto atual
echo "📦 Usando projeto existente no GCP..."
gcloud config set project $PROJECT_ID

# 2. Habilitar APIs necessárias
echo "🔧 Habilitando APIs necessárias..."
gcloud services enable cloudbuild.googleapis.com
gcloud services enable run.googleapis.com
gcloud services enable sqladmin.googleapis.com

# 3. Criar banco PostgreSQL (se não existir)
if ! gcloud sql instances describe $DB_INSTANCE --project=$PROJECT_ID >/dev/null 2>&1; then
  echo "🗄️ Criando banco PostgreSQL..."
  gcloud sql instances create $DB_INSTANCE \
      --database-version=POSTGRES_13 \
      --tier=db-f1-micro \
      --region=$REGION \
      --storage-size=10GB \
      --backup-start-time=03:00
else
  echo "✅ Instância de banco já existe: $DB_INSTANCE"
fi

# Criar DB (se não existir)
if ! gcloud sql databases describe $DB_NAME --instance=$DB_INSTANCE --project=$PROJECT_ID >/dev/null 2>&1; then
  echo "📝 Criando database $DB_NAME..."
  gcloud sql databases create $DB_NAME --instance=$DB_INSTANCE
else
  echo "✅ Database já existe: $DB_NAME"
fi

# Garantir usuário
gcloud sql users set-password $DB_USER \
  --instance=$DB_INSTANCE \
  --password=$DB_PASS

# Obter connection name
INSTANCE_CONNECTION_NAME=$(gcloud sql instances describe $DB_INSTANCE --format="value(connectionName)")

# 4. Build da aplicação
echo "🏗️ Fazendo build da aplicação..."
gcloud builds submit --tag gcr.io/$PROJECT_ID/$SERVICE_NAME .

# 5. Deploy no Cloud Run
echo "🚀 Fazendo deploy no Cloud Run..."
gcloud run deploy $SERVICE_NAME \
    --image gcr.io/$PROJECT_ID/$SERVICE_NAME \
    --region=$REGION \
    --platform=managed \
    --allow-unauthenticated \
    --add-cloudsql-instances=$INSTANCE_CONNECTION_NAME \
    --set-env-vars="SPRING_PROFILES_ACTIVE=prod,DB_NAME=$DB_NAME,DB_USER=$DB_USER,DB_PASS=$DB_PASS,INSTANCE_CONNECTION_NAME=$INSTANCE_CONNECTION_NAME" \
    --memory=1Gi \
    --cpu=1 \
    --max-instances=10

# 6. Obter resultado
SERVICE_URL=$(gcloud run services describe $SERVICE_NAME --region=$REGION --format="value(status.url)")

echo ""
echo "🎉 DEPLOY CONCLUÍDO COM SUCESSO!"
echo "=================================="
echo "🌐 Backend URL: $SERVICE_URL"
echo "📚 Swagger UI: $SERVICE_URL/swagger-ui/index.html"
echo "🗄️ Database: $DB_INSTANCE (PostgreSQL)"
echo "🔐 DB Password: $DB_PASS"
echo "📋 Project ID: $PROJECT_ID"
echo ""
echo "✅ Teste agora: abra o link do Swagger no navegador!"

# Salvar informações
cat > deploy-info.txt << EOF
FinPro Deploy - $(date)
====================
Project ID: $PROJECT_ID
Backend URL: $SERVICE_URL
Swagger: $SERVICE_URL/swagger-ui/index.html
Database: $DB_INSTANCE
DB Password: $DB_PASS
Region: $REGION
EOF

echo "📄 Informações salvas em: deploy-info.txt"
