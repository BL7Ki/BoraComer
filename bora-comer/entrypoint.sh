#!/bin/sh
echo "Variáveis de ambiente antes de carregar secrets:"
printenv

set -a
source /run/secrets/db_env  # Carregando secrets
set +a

echo "Variáveis de ambiente depois de carregar secrets:"
printenv

exec java -jar bora-comer.jar
