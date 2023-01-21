#!/usr/bin/bash

cd src/ || (echo "Error: could not change directory to src/"; exit 1)

# Compile the program
javac Main.java || (echo "Error: could not compile program"; exit 1)

# Run the program on the datasets and save the output to a file
datasets=("car-f-92" "car-s-91" "kfu-s-93" "tre-s-92"  "yor-f-83")
heuristics=("Saturation Degree" "Largest Degree" "Largest Enrollment" "Random Ordering")
inputFile="input.txt"
outputFile="output.out"
true > "$outputFile"

for i in "${!heuristics[@]}"; do
    for j in "${!datasets[@]}"; do
        echo "${datasets[$j]}" > "$inputFile"
        echo $(( i+1 )) >> "$inputFile"

        echo "====Running ${heuristics[$i]} with dataset ${datasets[$j]}====" >> "$outputFile"
        java Main < "$inputFile" >> output.out
        echo "==========================================================" >> "$outputFile"
    done
done

rm "$inputFile"
cd .. || (echo "Error: could not change directory to parent directory"; exit 1)