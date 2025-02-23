import pandas as pd
import matplotlib.pyplot as plt
import matplotlib

from dictionaries import myLaptop, G26D4

matplotlib.use('TkAgg')

# Constantes
scale_up = "up"
scale_out = "out"


# Fonction pour lire un fichier CSV et tracer la courbe du speedup
def plot_speedup(csv_file, avoidMultipleSp, scalabilite):
    # Charger les données
    df = pd.read_csv(csv_file)

    # Calculer la moyenne des temps pour chaque nombre de Nworkers
    df_avg = df.groupby('Nworkers')['Time'].mean().reset_index()

    # Extraire les valeurs utiles
    nWorkers = df_avg['Nworkers']
    durations = df_avg['Time']

    # Calculer le speedup
    t1 = durations.iloc[0]  # Temps d'exécution pour 1 worker
    speedup = t1 / durations

    # Tracer la courbe
    if not avoidMultipleSp:
        if scalabilite == scale_up:
            plt.plot(nWorkers, nWorkers, linestyle='--', color='r', label='Speedup linéaire (Sp=p)')
        elif scalabilite == scale_out:
            plt.plot(nWorkers, [1] * len(nWorkers), linestyle='--', color='r', label='Speedup linéaire (Sp=1)')
            plt.ylim(0, 1.4)
        else:
            print("le paramètre 'scalabilite' mal renseigné")
    plt.plot(nWorkers, speedup, marker='o', linestyle='-', label=f'Speedup mesuré ({csv_file})')


def plot_all_speedups(Dataset, scalabilite):
    for index, csv_file in enumerate(Dataset):
        avoid_multiple_sp = index != 0
        plot_speedup(csv_file, avoid_multiple_sp, scalabilite)


# plot_all_speedups(myLaptop["master_worker"]["out"], "out")

plot_all_speedups(G26D4["master_worker"]["out"], "out")


# Ajouter les détails du graphique
plt.xlabel("Nombre de coeurs")
plt.ylabel("Speedup")
plt.title("Speedup scalabilité faible MasterWorker")
plt.legend()
plt.grid()

# Afficher le graphique
plt.show()
