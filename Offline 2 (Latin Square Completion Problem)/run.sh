#!/usr/bin/bash
# dataSet={"data/d-10-01.txt"}
out="output.txt"
temp="temp.txt"
dataSet=("d-10-01" "d-10-06" "d-10-07" "d-10-08" "d-10-09" "d-15-01")
backtracks=("backtracking" "forward checking")
# heuristics=("Minimum Remaining Value")
heuristics=("Minimum Remaining Value" "Maximum Degree" "Minimum Remaining Value + Maximum Degree" "Minimum Remaining Value / Maximum Degree" "Random Unassigned Variable")
> "$out"
for (( k = 0; k < ${#dataSet[@]}; k++ )); do   
    data=${dataSet[$k]}
    echo "===============$data:==============" >> "$out"
    
    
    for (( i = 0; i < ${#backtracks[@]}; i++ )); do
        backtrack=${backtracks[$i]}
       echo '===================================' >> "$out"
       
        echo "$backtrack:" >> "$out"
        
        for (( j = 0; j < ${#heuristics[@]}; j++ )); do
            heuristic=${heuristics[$j]}
            echo '-----------------------------------' >> "$out"
            echo "$heuristic:" >> "$out"

            > "$temp"
            cat "data/$data.txt" >> "$temp"
            backtrackValue=$(($i+1))
            heuristicValue=$(($j+1))
            # heuristicValue=4
            echo >> "$temp"
            echo $backtrackValue >> "$temp"
            echo $heuristicValue >> "$temp"
            cd Latin\ Square\ Completion\ Problem\ Solver/out/production/Latin\ Square\ Completion\ Problem\ Solver/
            java "solver.latinsquare.Main" < ../../../../"$temp" >> ../../../../"$out"
            cd ../../../../
        done
    done
done
# cat "$dataDir" >> temp.txt
# echo "2 4 " >> temp.txt
# cd Latin\ Square\ Completion\ Problem\ Solver/out/production/Latin\ Square\ Completion\ Problem\ Solver/
# java "solver.latinsquare.Main" < ../../../../temp.txt