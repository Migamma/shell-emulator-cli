#!/usr/bin/env bash

cd "$(dirname "$0")/.."

echo "=== unknown option ==="
./run.sh --foo
echo "exit code: $?"

echo "=== option without value ==="
./run.sh --vfs
echo "exit code: $?"

echo "=== option followed by another option ==="
./run.sh --vfs --prompt "emu> "
echo "exit code: $?"