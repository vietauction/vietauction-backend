#!/usr/bin/env bash

set -euo pipefail

project_root=$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)
env_file="$project_root/.env.local"

if [[ ! -f "$env_file" ]]; then
  echo "Missing $env_file. Copy .env.local.example to .env.local and fill in local credentials." >&2
  exit 1
fi

set -a
# shellcheck disable=SC1090
. "$env_file"
set +a

: "${DB_HOST:?DB_HOST is required}"
: "${DB_PORT:?DB_PORT is required}"
: "${DB_NAME:?DB_NAME is required}"
: "${DB_USERNAME:?DB_USERNAME is required}"
: "${DB_PASSWORD:?DB_PASSWORD is required}"

if [[ -z "${JAVA_HOME:-}" && -x "/opt/homebrew/opt/openjdk@21/bin/java" ]]; then
  export JAVA_HOME="/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"
  export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"
fi

if ! java -version 2>&1 | grep -q 'version "21\.'; then
  echo "JDK 21 is required. Set JAVA_HOME to a JDK 21 installation and try again." >&2
  exit 1
fi

export SPRING_PROFILES_ACTIVE=dev
export SPRING_DATASOURCE_URL="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true&characterEncoding=UTF-8"
export SPRING_DATASOURCE_USERNAME="$DB_USERNAME"
export SPRING_DATASOURCE_PASSWORD="$DB_PASSWORD"

cd "$project_root"
exec ./mvnw spring-boot:run
