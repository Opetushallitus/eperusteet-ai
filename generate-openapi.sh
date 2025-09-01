#!/bin/bash
set -euo pipefail

# Generoi openapi-kuvauksen
openapi() {
  mvn clean compile -P generate-openapi \
    && cp target/openapi/eperusteet-ai.spec.json generated
}

# Dispatch based on argument
case "${1:-}" in
  openapi)
    openapi
    ;;
  *)
    echo "Usage: $0 {gen_openapi}"
    exit 1
    ;;
esac
