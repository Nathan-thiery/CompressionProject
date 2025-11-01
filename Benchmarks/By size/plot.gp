set title "Benchmark Compression/Décompression (Compression_2)"
set xlabel "Taille du tableau (nombres d'entiers)"
set ylabel "Temps moyen (ms)"
set grid
set key outside top center

set terminal pngcairo size 1400,800 enhanced font 'Arial,12'
set output "Benchmarks graphs/SecondMethod_AllNumbers.png"

ctype = 2
colors = "blue red green orange violet brown"

plot for [dtype=1:6] \
    sprintf("< awk '$1==%d && $2==%d {print $3, $4}' SecondMethod_AllNumbers.txt | sort -n | awk '{sum[$1]+=$2; count[$1]++} END{for(i in sum) print i, sum[i]/count[i]}' | sort -n", ctype, dtype) \
    using 1:2 with linespoints lw 2 pt 7 lt rgb word(colors, dtype) \
    title sprintf('Type d''entiers %d', dtype)

unset output
print "✅ Graphique généré : SecondMethod_AllNumbers.png"