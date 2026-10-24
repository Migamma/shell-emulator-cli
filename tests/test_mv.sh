#!/usr/bin/env bash
cd "$(dirname "$0")/.."

echo "=== stage 5 startup script ==="
./run.sh --vfs tests/vfs/deep.xml --script tests/scripts/stage5.txt < /dev/null

echo "=== mv errors ==="
printf '%s\n' "mv" "mv a" "mv a b c" "mv nope tmp" "mv / tmp" \
    "mv home home/user" "mv home home/new" \
    "mv /home/user/todo.txt /home/user/docs/notes.txt" \
    "mv readme.txt nope/x.txt" "mv readme.txt readme.txt/x" "exit" \
    | ./run.sh --vfs tests/vfs/deep.xml