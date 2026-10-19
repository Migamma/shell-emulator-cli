#!/usr/bin/env bash
cd "$(dirname "$0")/.."

echo "=== stage 4 startup script ==="
./run.sh --vfs tests/vfs/deep.xml --script tests/scripts/stage4.txt </dev/null

echo "=== command errors ==="
printf '%s\n' "ls nope" "ls a b" "cd readme.txt" "cd nope" "cd a b" \
  "tac" "tac tmp" "tac nope" "tac a b" "uname -a" "exit" |
  ./run.sh --vfs tests/vfs/deep.xml
