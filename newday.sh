#!/bin/bash

day=0

if [ $# -ne 1 ]; then
  echo "You forgot the day, my dude."
else
  if [ "$1" -lt 10 ]; then
    day="0$1"
  else
    day=$1
  fi
  echo "I'm generating the code for day $day"
fi

puzzleTestInput="in/puzzle$day.test.in"
puzzleInput="in/puzzle$day.in"

touch "$puzzleInput" "$puzzleTestInput"

puzzleJava="Puzzle${day}.java"
puzzleTestJava="Puzzle${day}Test.java"

cp templates/PuzzleTemplate.java "$puzzleJava"
cp templates/PuzzleTestTemplate.java "$puzzleTestJava"

sed -i .trash "s/__DAY__/$day/g" "$puzzleJava"
sed -i .trash "s/__DAY__/$day/g" "$puzzleTestJava"

mv "$puzzleJava" src/main/java/com/ionutciuta/puzzles
mv "$puzzleTestJava" src/test/java/com/ionutciuta/puzzles

rm *.trash

git add "$puzzleInput" \
  "$puzzleTestInput" \
  "src/main/java/com/ionutciuta/puzzles/$puzzleJava" \
  "src/test/java/com/ionutciuta/puzzles/$puzzleTestJava"

echo "Done!"