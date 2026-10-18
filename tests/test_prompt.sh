#!/usr/bin/env bash
cd "$(dirname "$0")/.."

echo "=== default prompt ==="
./run.sh </dev/null

echo "=== custom prompt ==="
./run.sh --prompt "emu> " </dev/null
