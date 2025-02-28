import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.widgets import Button
import matplotlib

from dictionaries import myLaptop, G26D4

matplotlib.use('TkAgg')

# Constantes
scale_up = "up"
scale_out = "out"

# Variables globales pour le dataset et la méthode sélectionnés
current_dataset = "myLaptop"
current_method = "pi"

# Fonction pour lire un fichier CSV et tracer la courbe du speedup
def plot_speedup(ax, csv_file, avoidMultipleSp, scalabilite):
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

    # Raccourcir les noms des fichiers
    workload = csv_file.split('_')[-1].split('.')[0]

    # Tracer la courbe
    if not avoidMultipleSp:
        if scalabilite == scale_up:
            ax.plot(nWorkers, nWorkers, linestyle='--', color='r', label='Speedup linéaire (Sp=p)')
        elif scalabilite == scale_out:
            ax.plot(nWorkers, [1] * len(nWorkers), linestyle='--', color='r', label='Speedup linéaire (Sp=1)')
            ax.set_ylim(0, 1.4)
        else:
            print("le paramètre 'scalabilite' mal renseigné")
    ax.plot(nWorkers, speedup, marker='o', linestyle='-', label=f'Speedup mesuré pour une charge initiale de {workload}/coeurs')

def plot_all_speedups(ax, Dataset, scalabilite):
    for index, csv_file in enumerate(Dataset):
        avoid_multiple_sp = index != 0
        plot_speedup(ax, csv_file, avoid_multiple_sp, scalabilite)

def update_plots():
    ax1.clear()
    ax2.clear()
    if current_dataset == "myLaptop":
        plot_all_speedups(ax1, myLaptop[current_method]["out"], "out")
        plot_all_speedups(ax2, myLaptop[current_method]["up"], "up")
    elif current_dataset == "G26D4":
        plot_all_speedups(ax1, G26D4[current_method]["out"], "out")
        plot_all_speedups(ax2, G26D4[current_method]["up"], "up")
    ax1.set_xlabel("Nombre de coeurs")
    ax1.set_ylabel("Speedup")
    ax1.set_title(f"Scalabilité faible {current_method} ({current_dataset})")
    ax1.legend()
    ax1.grid()
    ax2.set_xlabel("Nombre de coeurs")
    ax2.set_ylabel("Speedup")
    ax2.set_title(f"Scalabilité forte {current_method} ({current_dataset})")
    ax2.legend()
    ax2.grid()
    plt.draw()

def set_dataset(event, dataset):
    global current_dataset
    current_dataset = dataset
    update_plots()

def set_method(event, method):
    global current_method
    current_method = method
    update_plots()

# Configuration initiale
fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12, 6))
plt.subplots_adjust(bottom=0.3)

# Boutons pour sélectionner le dataset
ax_button_myLaptop = plt.axes([0.1, 0.15, 0.2, 0.075])
ax_button_G26D4 = plt.axes([0.4, 0.15, 0.2, 0.075])
btn_myLaptop = Button(ax_button_myLaptop, 'myLaptop')
btn_G26D4 = Button(ax_button_G26D4, 'G26D4')

# Boutons pour sélectionner la méthode
ax_button_pi = plt.axes([0.1, 0.05, 0.2, 0.075])
ax_button_assignment102 = plt.axes([0.4, 0.05, 0.2, 0.075])
ax_button_master_worker = plt.axes([0.7, 0.05, 0.2, 0.075])
btn_pi = Button(ax_button_pi, 'Pi')
btn_assignment102 = Button(ax_button_assignment102, 'Assignment102')
btn_master_worker = Button(ax_button_master_worker, 'MasterWorker')

# Associer les fonctions aux boutons
btn_myLaptop.on_clicked(lambda event: set_dataset(event, "myLaptop"))
btn_G26D4.on_clicked(lambda event: set_dataset(event, "G26D4"))
btn_pi.on_clicked(lambda event: set_method(event, "pi"))
btn_assignment102.on_clicked(lambda event: set_method(event, "assignment102"))
btn_master_worker.on_clicked(lambda event: set_method(event, "master_worker"))

# Afficher les graphes par défaut
update_plots()

plt.show()