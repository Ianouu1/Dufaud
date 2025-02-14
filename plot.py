import pandas as pd
import matplotlib.pyplot as plt
import matplotlib

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


# Pi
up_pi = ["data/myLaptop/Pi/scale_up_Pi_myLaptop_12x10e6.csv",
         "data/myLaptop/Pi/scale_up_Pi_myLaptop_12x10e7.csv",
         "data/myLaptop/Pi/scale_up_Pi_myLaptop_12x10e8.csv"]

out_pi = ["data/myLaptop/Pi/scale_out_Pi_myLaptop_12x10e6.csv",
          "data/myLaptop/Pi/scale_out_Pi_myLaptop_12x10e7.csv", ]

# Assignement 102
up_assignment102 = ["data/myLaptop/Assignment102/scale_up_Ass102_myLaptop_12x10e5.csv",
                    "data/myLaptop/Assignment102/scale_up_Ass102_myLaptop_12x10e6.csv",
                    "data/myLaptop/Assignment102/scale_up_Ass102_myLaptop_12x10e7.csv"]
out_assignment102 = ["data/myLaptop/Assignment102/scale_out_Ass102_myLaptop_12x10e6.csv",
                     "data/myLaptop/Assignment102/scale_out_Ass102_myLaptop_12x10e5.csv"]

# Master / Worker

up_MW = ["data/myLaptop/MasterWorker/scale_up_MW_myLaptop_12x10e5.csv",
         "data/myLaptop/MasterWorker/scale_up_MW_myLaptop_12x10e6.csv",
         "data/myLaptop/MasterWorker/scale_up_MW_myLaptop_12x10e7.csv"]

out_MW = ["data/myLaptop/MasterWorker/scale_out_MW_myLaptop_12x10e5.csv",
          "data/myLaptop/MasterWorker/scale_out_MW_myLaptop_12x10e6.csv",
          "data/myLaptop/MasterWorker/scale_out_MW_myLaptop_12x10e7.csv"]

# PI
# plot_all_speedups(up_pi, "up")
# plot_all_speedups(out_pi, "out")

# Assignment 102
# plot_all_speedups(up_assignment102, "up")
# plot_all_speedups(out_assignment102, "out")

# Master / Worker
# plot_all_speedups(up_MW, "up")
plot_all_speedups(out_MW, "out")


# Ajouter les détails du graphique
plt.xlabel("Nombre de coeurs")
plt.ylabel("Speedup")
plt.title("Speedup scalabilité faible MasterWorker")
plt.legend()
plt.grid()

# Afficher le graphique
plt.show()
