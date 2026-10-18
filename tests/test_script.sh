#!/usr/bin/env bash
cd "$(dirname "$0")/.."

echo "=== script without errors ==="
./run.sh --script tests/scripts/script_ok.txt </dev/null

echo "=== script with error ==="
./run.sh --script tests/scripts/script_error.txt </dev/null

echo "=== script with exit ==="
./run.sh --script tests/scripts/script_exit.txt </dev/null

echo "=== missing script file ==="
./run.sh --script tests/scripts/nofile.txt </dev/null
