import pandas as pd
import matplotlib.pyplot as plt
import matplotlib

matplotlib.use('TkAgg')


# Fonction pour lire un fichier CSV et tracer la courbe du speedup
def plot_speedup(csv_file):
    df = pd.read_csv(csv_file)
    df_avg = df.groupby('Nworkers')['Time'].mean().reset_index()
    nWorkers = df_avg['Nworkers']
    durations = df_avg['Time']
    t1 = durations.iloc[0]
    speedup = t1 / durations
    workload = csv_file.split('_')[-1].split('.')[0]

    plt.plot(nWorkers, [1] * len(nWorkers), linestyle='--', color='r', label='Speedup linéaire (Sp=1)')
    plt.ylim(0, 1.4)
    plt.yticks([0, 0.2, 0.4, 0.6, 0.8, 0.9, 0.95, 1, 1.2, 1.4])
    plt.plot(nWorkers, speedup, marker='o', linestyle='-', label=f'Speedup mesuré pour {workload}/coeurs')
    plt.xlabel("Nombre de coeurs")
    plt.ylabel("Speedup")
    plt.title("Scalabilité faible")
    plt.legend()
    plt.grid()
    plt.show()


# Chemin en dur vers le fichier CSV
csv_file = "data/G26D-4/G26_multiple.csv"
plot_speedup(csv_file)
