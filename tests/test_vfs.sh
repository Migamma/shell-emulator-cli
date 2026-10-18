#!/usr/bin/env bash
cd "$(dirname "$0")/.."

echo "=== minimal VFS ==="
./run.sh --vfs tests/vfs/minimal.xml < /dev/null

echo "=== VFS with several files ==="
./run.sh --vfs tests/vfs/several_files.xml < /dev/null

echo "=== VFS with 3+ levels ==="
./run.sh --vfs tests/vfs/deep.xml < /dev/null

echo "=== missing VFS file ==="
./run.sh --vfs tests/vfs/nofile.xml < /dev/null
echo "exit code: $?"

echo "=== broken XML ==="
./run.sh --vfs tests/vfs/broken.xml < /dev/null
echo "exit code: $?"

echo "=== wrong VFS format ==="
./run.sh --vfs tests/vfs/wrong.xml < /dev/null
echo "exit code: $?"

echo "=== startup script with VFS ==="
./run.sh --vfs tests/vfs/deep.xml --script tests/scripts/stage3.txt < /dev/null