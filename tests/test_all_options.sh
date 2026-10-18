#!/usr/bin/env bash
cd "$(dirname "$0")/.."

echo "=== all options ==="
./run.sh --vfs tests/vfs.xml --prompt "emu> " --script tests/scripts/script_ok.txt </dev/null

echo "=== all options in different order ==="
./run.sh --script tests/s#!/usr/bin/env bash

echo "=== all options ==="
./run.sh --vfs tests/vfs.xml --prompt "emu> " --script tests/script_ok.txt </dev/null

echo "=== all options in different order ==="
./run.sh --script tests/scripts/script_ok.txt --vfs tests/vfs.xml --prompt "emu> " --vfs tests/vfs.xml --prompt "emu> " </dev/nullcript_ok.txt </dev/null
